Aspectos
---
### Objetivo
* Poder agregar al log el tiempo de ejecución de cualquier método con el annotation @Performance.

### Aspectos
Un aspecto es la modularización de una funcionalidad que se utiliza en forma cross en la aplicación pero que no puede encapsularse completamente en un módulo, como por ejemplo el logging o la seguridad. La programación orientada a aspectos tiene estos conceptos centrales:
* Join point: es un punto durante la ejecución de una aplicación, por ejemplo la llamada a un método. 
* Advice: es la acción tomada por un aspecto en un join point determinado. Los advices que más se utilizan son @Around, @Before y @After.
* Pointcut: es un predicado que selecciona join points.

Por ejemplo, supongamos que queremos loguear cada vez que se llama a un método de un controller, antes de que se ejecute el método:
```java
    @Before("within(com.dasboot.controller..*)")
    public void trackExecutionTime(JoinPoint joinPoint)
      throws Throwable {
      var name = joinPoint.getSignature().toShortString();
      logger.info("Calling " + name);
    }
    
```

#### Pointcut
Los pointcuts más utilizados son:
* **execution**: ejecuta el aspecto en la llamada a un método, en este ejemplo cualquier método de la clase TestController.
```java
@Pointcut("execution(* com.dasboot.controller.TestController.*(..))")
```

* **this**: ejecuta el aspecto si la clase es de cierto tipo o implementa cierta interfaz.<sup>1</sup>
```java
@Pointcut("this(* com.dasboot.controller.TestInterface)")
```

* **within**: permite limitar la ejecución a un contexto, por ejemplo limitar la ejecución a una clase o a un paquete. 
```java
@Before("within(com.dasboot.controller.TestController)")

@After("within(com.dasboot.controller..*)")
```

* **@annotation**: se ejecuta en todo join point que utilice esa annotation
```java
@Log
public void test() {}

@Before("@annotation(log)")
public void logExecution() {
  logger.info("hi!");
}
```

#### Advices
* **After**: ejecuta el aspecto luego del join point.
```java
@Aspect
public class AfterAspect {

    @After("within(com.dasboot.controller.TestController)")
    public void after() {
        logger.info("After execution");
    }

}
```
* **Before**: ejecuta el aspecto antes del join point.
```java
@Aspect
public class BeforeAspect {

    @Before("within(com.dasboot.controller.TestController)")
    public void before() {
        logger.info("Before execution");
    }

}
```

* **Around**: da control sobre cuándo ejecutar el join point, permitiendo ejecutar código antes y después.
```java
@Aspect
public class AroundAspect {

    @Around("within(com.dasboot.controller.TestController)")
    public Object test(ProceedingJoinPoint pjp) throws Throwable {
        logger.info("Before execution");
        Object returnValue = pjp.proceed();
        logger.info("After execution");
        return returnValue;
    }

}
```

### Más información
* [Spring AOP](https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#aop)

<sup>1</sup> Hay cierta diferencia entre this() y target() que no vale la pena desarrollar en este momento.

---

[Siguiente >>](https://github.com/gamestoy/checkout-spring-tutorial/tree/10_cache)

[<< Anterior](https://github.com/gamestoy/das-spring-boot/tree/08_snapshot)

[[Índice]](https://github.com/gamestoy/das-spring-boot#%C3%ADndice)

