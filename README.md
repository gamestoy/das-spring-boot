Crear el proyecto
----

### Instalar Spring Boot

#### Spring Initializr
Se puede utilizar [Spring Initializr](https://start.spring.io/) para el bootstrap de la aplicación. Hay que seleccionar un proyecto Maven, en Java y con alguna versión estable de Spring Boot:
<p align="center">
    <img src="https://github.com/gamestoy/checkout-spring-tutorial/blob/01_create_project/img/spring_initializr_main.png?raw=true" />
</p>

Es importante agregar las dependencias web y devtool para poder empezar el proyecto:
<p align="center">
    <img width="600" src="https://github.com/gamestoy/checkout-spring-tutorial/blob/01_create_project/img/spring_initializr_dependencies.png?raw=true" />
</p>

#### Intellij IDEA
En Intellij IDEA primero hay que activar el plugin de Spring Boot:
<p align="center">
    <img width="400" src="https://github.com/gamestoy/checkout-spring-tutorial/blob/01_create_project/img/intellij_spring_boot_plugin.png?raw=true" />
</p>

Luego crear un proyecto de Spring Boot:
<p align="center">
    <img src="https://github.com/gamestoy/checkout-spring-tutorial/blob/01_create_project/img/intellij_spring_initializr_init.png?raw=true" />
</p>

Al momento de seleccionar las dependencias:
* Developer Tools -> Spring Boot DevTools
* Web -> Spring Web

### Annotations
El proyecto se crea automáticamente con una clase cuyo main inicializa la aplicación:
```java
@SpringBootApplication
public class MoviesApplication {

  public static void main(String[] args) {
    SpringApplication.run(MoviesApplication.class, args);
  }
}
```
Con el annotation @SpringBootApplication le estamos indicando al contexto de Spring en forma implícita que busque en el classpath componentes y configuraciones. Internamente @SpringBootApplication implementa @EnableAutoConfiguration y @ComponentScan, el primero configura automáticamente la aplicación en base a los jar que se encuentran en las dependencias, mientras que el segundo indica que se busquen componentes en el paquete y subpaquetes y se agreguen al contexto. Si bien todo eso es modificable, esta configuración por default nos es suficiente para comenzar el proyecto. 

Para este proyecto vamos a utilizar particularmente estas annotations de Spring para la generación de componentes: @Component, @Service, @RestController y @Configuration. A medida que vayamos avanzando con el proyecto las vamos a ver con mayor detalle.

### Levantar el servidor
Si estás usando Intellij IDEA, el plugin de Spring Boot te permite levantar fácilmente la aplicación con un Run/Debug configuration específico.

Para levantarla con maven, basta con correr este comando:
```
$ mvn spring-boot:run
```

Para probarlo:
```
$ curl localhost:8080
```

Que debería devolver este json:
```
{"timestamp":"2019-09-20T17:45:21.108+0000","status":404,"error":"Not Found","message":"No message available","path":"/"}
```
 
 ---
[Siguiente >>](https://github.com/gamestoy/checkout-spring-tutorial/tree/02_rest-API)

[[Índice]](https://github.com/gamestoy/das-spring-boot#%C3%ADndice) 


