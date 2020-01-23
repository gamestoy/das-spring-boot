Paralelismo
---
## Objetivo
Para obtener la información de una película consultamos cuatro servicios diferentes: la información general de la película, el elenco, las reviews y las películas similares. En un primer momento lo hicimos en forma secuencial, pero para mejorar la performance vamos a hacerlo en forma paralela, así que las tareas a realizar son:
* Paralelizar las llamadas.
* Configurar un thread pool.
* Mantener el funcionamiento del ThreadLocal.

## Thread pools
Al implementar el ThreadLocal vimos un poco de threads, pero generalmente la forma de utilizarlos no es crear programáticamente cada thread que vamos a utilizar, sino que Java nos brinda varias abstracciones para el manejo de los mismos. Por ejemplo, nos permite crear un pool de threads para tener más control sobre cuántos threads vamos a utilizar y la forma en que se van a reutilizar:
```
var fixedExecutor = Executors.newFixedThreadPool(10);
var cachedExecutor = Executors.newCachedThreadPool();
var scheduler = Executors.newScheduledThreadPool(1);
```
En este caso se crean tres tipos de thread pool diferentes:
* Fixed: este thread pool usa una cantidad fija de threads. 
* Cached: crea threads a medida que los necesita, y los reutiliza siempre que estén disponibles.
* Scheduled: este thread pool permite ejecutar tareas en forma periódica o dentro de cierto tiempo.

De esta forma se pueden crear thread pools de forma fácil pero bastante restrictiva, en [este artículo](https://jrebel.com/rebellabs/java-executors-for-background-tasks/) se desarrolla un poco más cómo configurar funcionalidades como, por ejemplo, qué estrategia tomar cuando todos los threads del pool están en uso.

Los thread pools permiten ejecutar tareas de dos tipos, [las que devuelven un valor al ejecutarse (Callable) y las que no (Runnable)](https://www.baeldung.com/thread-pool-java-and-guava).
```
// Se ejecuta y se espera un resultado
Future<Response> future = executor.submit(() -> getMovieInfo());

// Se ejecuta y no se espera un resultado.
executor.execute(() -> System.out.println("Hi!"));
``` 

## Future
Un objeto de tipo Future reprensenta el resultado de una ejecución que se realizó en forma asincrónica. La clase brinda varios métodos para conocer el estado de la ejecución, pero para este caso vamos a usar simplemente la que permite obtener el resultado de esa ejecución:
```
// Bloquea el thread principal hasta que se resuelva la ejecución
Response resp = future.get();

// Bloquea el thread principal hasta que se resuelva la ejecución o pasen 100 ms
Response resp = future.get(100, TimeUnit.MILLISECONDS);
```

También desde la versión 1.8 existe la clase CompletableFuture, que nos permite realizar más operaciones sobre los Futures, incluso combinarlos. Para lanzar una tarea de este tipo, podemos hacerlo a través de dos métodos:
```
//Ejecuta una tarea que devuelve un valor
CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "string", executor);

//Simplemente ejecuta una tarea
CompletableFuture<Void> runnable = CompletableFuture.runAsync(() -> System.out.println("Hi!"), executor);

future.get();
runnable.get();
```
El CompletableFuture nos posibilita realizar más operaciones sin necesidad de usar el método get, o sea, te permite acceder a los valores de ese future sin bloquear el thread principal.

Por ejemplo realizar otra acción en cuanto se complete la tarea:
```
CompletableFuture<String> completableFuture = ...

completableFuture.thenApply(s -> s.toLowerCase());

```

O combinar el resultado de dos futures:
```
CompletableFuture<String> completableFuture = ...
CompletableFuture<String> anotherCompletableFuture = ...

completableFuture.thenCombine(anotherCompletableFuture, (s1, s2) -> s1 + s2);
```

O encadenar la ejecución de dos futures:
```
CompletableFuture<String> completableFuture = ...
completableFuture.thenCompose(s -> CompletableFuture.supplyAsync(() -> s + "string"));
```

El resultado de todas las acciones anteriores será un CompletableFuture, lo que indica que eventualmente tendremos que esperar por el resultado de esas operaciones bloqueando el thread principal.


## Spring
Spring brinda una abstracción sobre los thread pools, el ThreadPoolTaskExecutor. Esta clase expone campos para configurar el thread pool:
```
ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
pool.setCorePoolSize(corePoolSize);
pool.setMaxPoolSize(maxPoolSize);
pool.setQueueCapacity(queue);
pool.initialize();
```

En este caso estamos configurando el corePoolSize, el maxPoolSize y el tamaño de la cola. Este threadpool inicia con 1 thread en el pool, y ante cada tarea que le llega para ejecutar crea un nuevo thread. Eso sucede hasta que la cantidad de threads es mayor o igual al corePoolSize, en ese caso agrega la tarea a la cola. En cuanto la cola alcanza su capacidad máxima y la cantidad de threads es menor a maxPoolSize, se crea un nuevo thread. En cuanto la cantidad de threads es igual a maxPoolSize, se rechazan las nuevas tareas (o la política que se configure).

Spring nos permite también configurar un TaskDecorator, que nos permite agregar funcionalidad antes y después de ejecutar una tarea:
```
public class CustomTaskDecorator implements TaskDecorator {
  private static final Logger logger = LoggerFactory.getLogger(CustomTaskDecorator.class);

  @Override
  public Runnable decorate(Runnable runnable) {
    logger.info("Running task");
    runnable.run();
    logger.info("Task finished");
  }

}
```
Luego se puede agregar a un ThreadPoolTaskExecutor:
```
pool.setTaskDecorator(new CustomTaskDecorator());
```

---

[Siguiente >>](https://github.com/gamestoy/das-spring-boot/tree/08_snapshot)

[<< Anterior](https://github.com/gamestoy/das-spring-boot/tree/06_thread-local)

[[Índice]](https://github.com/gamestoy/das-spring-boot#%C3%ADndice)
