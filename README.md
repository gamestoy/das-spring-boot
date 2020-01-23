Filtros e interceptores
---
## Objetivo
* Agregar log a toda llamada a servicios externos usando un interceptor: loggear el método HTTP y la url de todas las llamadas.
* Agregar log a todas las requests entrantes usando un interceptor. Se deben loggear la url y los headers.
* Usando un filtro, devolver un error 400 cuando el cliente no nos envía el header X-Client.

Toda esta funcionalidad que queremos agregar es transversal a muchas de las clases que creamos. Vamos a ver ciertas herramientas que nos van a servir para implementarla.

## Controller interceptors
Con Spring MVC se realiza un mapeo entre la url y un método de una clase, particularmente de un controller, y un interceptor puede ejecutarse antes o después de ese controller. Para crearlo es necesario implementar la interfaz HandlerInterceptor, que tiene tres métodos:
* preHandle: se ejecuta una vez que se determinó el handler, el método a ejecutar, y permite detener la ejecución del mismo. Devuelve un boolean que indica si se debe o no continuar con la ejecución.
* postHandle: se ejecuta después que el handler, pero antes del render de la vista.
* afterCompletion: se ejecuta después del render de la vista.

```java
public class IrrelevantInterceptor implements HandlerInterceptor {

  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    throws Exception {
    return true;
  }
}
```

Para configurarlo es necesario crear una configuración para Spring MVC y sobrescribir el método addInterceptors:
```
@Configuration
public class MVCConfig implements WebMvcConfigurer {

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new IrrelevantInterceptor());
  }

}
```

## Rest Template interceptors
Para crear un interceptor para el rest template tenemos que implementar la interfaz ClientHttpRequestInterceptor e implementar el método correspondiente:

```java
public class AnotherIrrelevantInterceptor implements ClientHttpRequestInterceptor {

  @Override
  public ClientHttpResponse intercept(
    HttpRequest request, byte[] bytes, ClientHttpRequestExecution execution) throws IOException {
    return execution.execute(request, bytes);
  }
}
```

Esto nos permite ejecutar código antes y después de ejecutar la request, ya sea para logging, para agregar algún header, etc. Luego, para agregar el interceptor al rest template:
```java
restTemplate.setInterceptors(Arrays.asList(new LoggingInterceptor()));
```

## Filters
Un filtro también permite realizar acciones antes y después de ejecutar el código de una request. En Spring Boot un filtro es un componente que implementa javax.servlet.Filter:
```java
@Component
@Order(1)
public class TestFilter implements Filter {
  private static final Logger logger = LoggerFactory.getLogger(TestFilter.class);
  
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
    throws IOException, ServletException {
      logger.info("before");
      chain.doFilter(request, response);
      logger.info("after");
    }

}
```  
La implementación tiene algunos detalles:
* Indica un orden (@Order) de ejecución ya que pueden ejecutarse más de un filtro.
* Requiera la implementación del método doFilter, que nos da acceso a la request, el response y a la cadena de filtros. Para continuar con la ejecución de la aplicación, se debe llamar al método doFilter de la cadena, para que prosiga con la ejecución del siguiente filtro o le de el control a Spring nuevamente. 


---  
[Siguiente >>](https://github.com/gamestoy/checkout-spring-tutorial/tree/06_thread-local)

[<< Anterior](https://github.com/gamestoy/checkout-spring-tutorial/tree/04_logging)

[[Índice]](https://github.com/gamestoy/das-spring-boot#%C3%ADndice)
