Thread local
---
## Objetivo
* Generar un id para la request llamada UOW, que puede venir mediante el header X-UOW o se debe generar en forma aleatoria en caso contrario.
* Propagar a toda llamada de un servicio externo los headers que tengan el prefijo XMOVIE y la UOW.
* Agregar a toda línea de log el nombre de la instancia en la que se ejecuta y la UOW.

## Thread local
La clase ThreadLocal nos permite tener variables únicas por thread que pueden ser accedidas y modificadas desde cualquier parte de la aplicación. Por ejemplo:
```java
public class Test {

  public static void main(String[] args) throws InterruptedException {
    var t1 = new Thread(() -> Context.setContext("TEST"));
    t1.start();
    var t2 = new Thread(() -> System.out.println(Context.getContext()));
    Thread.sleep(1000);
    t2.start();
  }

  static class Context {
    private static final ThreadLocal<String> context = ThreadLocal.withInitial(() -> "DEFAULT");

    public static String getContext() {
      return context.get();
    }

    public static void setContext(String value) {
      context.set(value);
    }

  }
}
```
Al ejecutar el código anterior lo que se imprime por pantalla es "DEFAULT", porque el contexto se modifica en un thread diferente al que se accede.

## Logback
Para poder agregar el nombre del host y la UOW a cada línea del log, tenemos que primero disponibilizarlos en el contexto de logback: MDC. Esta clase permite agregar y obtener valores en un contexto compartido por cada thread, al igual que un thread local. Los valores se agregan bajo la modalidad clave-valor:
```java
MDC.put("test", 1);
MDC.get("test");
```

Una vez agregadas al contexto, se pueden acceder desde el log especificándolo en el pattern de log, indicando %X{nombre_variable}:
```xml
<property name="CONSOLE_LOG_PATTERN" value="%d{ISO8601} %-4p [%t] [%X{test}] %logger{0} - %msg%n"/>
```
---
[Siguiente >>](https://github.com/gamestoy/checkout-spring-tutorial/tree/07_concurrency)

[<< Anterior](https://github.com/gamestoy/checkout-spring-tutorial/tree/05_interceptors)

[[Índice]](https://github.com/gamestoy/das-spring-boot#%C3%ADndice)
