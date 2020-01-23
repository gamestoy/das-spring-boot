Snapshot
---
## Objetivo
Para obtener la información de una película consultamos cuatro servicios diferentes: la información general de la película, el elenco, las reviews y las películas similares. En un primer momento lo hicimos en forma secuencial, pero para mejorar la performance vamos a hacerlo en forma paralela, así que las tareas a realizar son:
* Devolver un flag que indique si la película integra la lista de mejor puntuadas.
* Crear un snapshot con las películas mejor puntuadas para mejorar la performance.
* Crear una uow para identificar los llamados del scheduler.

## Scheduled
Para la ejecución de un método cada cierto período de tiempo, Spring nos da la annotation @Scheduled. 
```java
  @Scheduled(fixedDelay = 10000)
  public void refresh() {
    System.out.println("executing...");
  }
```
Esta annotation está parametrizada, así que podemos utilizar algunos de los siguientes parámetros:
* initialDelay: tiempo a esperar antes de ejecutar la primer tarea.
* fixedRate: indica en milisegundos el tiempo que debe pasar entre ejecuciones.
* fixedDelay: indica en milisegundos el tiempo que debe pasar entre la finalización de la una ejecución y la siguiente.
* cron: usa un [cron expressions](https://en.wikipedia.org/wiki/Cron#CRON_expression) para indicar el intervalo de ejecución.

Para activarlo se debe agregar un annotation en la aplicación:
```java
@SpringBootApplication
@EnableScheduling
public class TestApplication {

  public static void main(String[] args) {
    SpringApplication.run(TestApplication.class, args);
  }
}
```

---
[Siguiente >>](https://github.com/gamestoy/das-spring-boot/tree/09_aspects)

[<< Anterior](https://github.com/gamestoy/das-spring-boot/tree/07_concurrency)

[[Índice]](https://github.com/gamestoy/das-spring-boot#%C3%ADndice)
