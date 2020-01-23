Caché
---
### Objetivo
* Reducir el tiempo de respuesta del servicio de películas utilizando una caché para guardar las películas mejor puntuadas.

### Caching
Spring permite implementar una caché de una forma bastante transparente, sin necesidad de agregar lógica particular en las clases de la aplicación. Para comenzar necesitamos agregar la dependencia:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-cache</artifactId>
</dependency>
```

Con la dependencia instalada, para que Spring configure automáticamente la caché necesitamos agregarle a la aplicación el annotation @EnableCaching:

```java
@SpringBootApplication
@EnableScheduling
@EnableCaching
public class MoviesApplication {

  public static void main(String[] args) {
    SpringApplication.run(MoviesApplication.class, args);
  }
}
```

Para implementar su uso podemos agregar un annotation en un método, que guardará el objeto que retorne:
```java
  @Cacheable("test")
  public Test get(String id) {
  }
```
En este caso se va a utilizar la cache "test", y la key se va a formar automáticamente con los valores de todos los parámetros. Este mismo annotation permite configurar qué key utilizar, así como bajo qué condiciones debería guardarse:
```java
  @Cacheable(value = "test", key = "#result.id", condition = "#id > 20")
  public Movie get(int id) {
  }
```
Este método va a guardar en la cache bajo la key correspondiente al id del resultado, siempre y cuando el id pasado como parámetro sea mayor a 20.


### Implementaciones
La implementación default de Spring es un ConcurrentHashMap, algo que nos permite hacer pruebas pero que no es lo suficientemente útil para una aplicación productiva. Una buena implementación de caché debería permitirnos configurar:
* Las cachés a utilizar.
* Dónde se guardan los datos (heap, disco, etc).
* El tamaño de cada una.
* Cuánto tiempo guardar los datos.
* Qué hacer cuando se llena.

Por eso Spring permite implementar la abstracción de caché con, entre otros, los siguientes proveedores:

* JCache
* Redis
* Hazelcast
* Couchbase

Para esta aplicación vamos a utilizar ehcache 3, que cumple con la especificación JSR-107 (JCache).

### Ehcache
Ehcache es una librería open source que nos va a permitir configurar muchas de las funcionalidades que la implementación default no nos permitía. 

Para hacerlo tenemos que indicarle a Spring dónde buscar la configuración en el application.properties:
```
spring.cache.jcache.config=classpath:ehcache.xml
```
 
Y agregar un archivo ehcache.xml en resources:
```xml
<config xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns="http://www.ehcache.org/v3"
        xmlns:jsr107="http://www.ehcache.org/v3/jsr107"
        xsi:schemaLocation="
            http://www.ehcache.org/v3 http://www.ehcache.org/schema/ehcache-core-3.0.xsd
            http://www.ehcache.org/v3/jsr107 http://www.ehcache.org/schema/ehcache-107-ext-3.0.xsd">

    <cache alias="test">
        <key-type>java.lang.String</key-type>
        <expiry>
            <ttl unit="seconds">120</ttl>
        </expiry>
        <resources>
            <heap unit="MB">200</heap>
        </resources>
    </cache>

</config>
```

Este ejemplo definimos:
* Una cache con el alias "tests".
* Que se va a guardar en la heap. La librería también permite guardar off-heap y en disco.
* Con un tamaño de 200 MB. Este tamaño también puede indicarse en cantidad de objetos.
* Una expiración de dos minutos.
* LRU como eviction policy. Esto no es configurable sino que viene dado por dónde se guarda, exitiendo Least Recently Used (LRU), Least Frequently Used (LFU) y First In First Out (FIFO).
* La key va a ser de tipo String.

De esta forma queda configurada la cache sin tener que tocar código en la aplicación.


---

[Siguiente >>](https://github.com/gamestoy/checkout-spring-tutorial/tree/11_mongodb)

[<< Anterior](https://github.com/gamestoy/das-spring-boot/tree/09_aspects)

[[Índice]](https://github.com/gamestoy/das-spring-boot#%C3%ADndice)

