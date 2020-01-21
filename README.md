Consumir una API
---
## Objetivo
Ahora vamos a reemplazar los mocks de películas por llamados a [la API de TMDB](https://developers.themoviedb.org/3). La información que tenemos que obtener es:
* Información de una película: el id, el título, el título original, el idioma original, una sinópsis, fecha de salida, revenue, puntaje promedio y géneros. También tiene que incluir reviews, cinco películas similares (con id, título y fecha de salida), los diez actores principales, el director y los guionistas. 
* Búsqueda de una película: debe devolver una lista de películas con su id, título y fecha de salida.

El connector que desarrollemos para obtener los datos de la API tendrá que:
* Poder configurar por properties los timeouts de lectura y establecimiento de conexión.
* Devolver excepciones tipificadas exclusivamente para esas llamadas, en lugar de las que devuelva la implementación de Spring.

## RestTemplate
Esta es una abstracción que nos proporciona Spring para consumir servicios REST, es una API que por debajo utiliza uno de varios clientes HTTP: Apache HttpComponents HttpClient, OKHttp o la default, usando directamente la JDK.

La API es bastante sencilla, en este caso vamos a buscar como String el html la Google:
```java
var restTemplate = new RestTemplate();
restTemplate.getForObject("https://www.google.com", String.class);
```
 
 
### Métodos
En el ejemplo anterior primero se crea el rest template con su implementación default, y luego se llama a Google y se convierte la respuesta a un String. También podríamos convertirlo directamente a un objeto de nuestro modelo:
```java
restTemplate.getForObject("https://api.example.com/movies/1", Movie.class);
```

Para realizar un POST, se crea un HttpEntity con el body del mensaje:
```java
var request = new HttpEntity<>(review);
restTemplate.postForObject("https://api.example.com/reviews", request, Review.class);
```

Para realizar un PUT:
```java
var entity = new HttpEntity<>(review);
restTemplate.exchange("https://api.example.com/reviews/1", HttpMethod.PUT, entity, Review.class);
```

Cuando alguna de estas llamadas falla, por default lanza una RestClientException según lo especificado en el DefaultResponseErrorHandler. 

También se puede tener más control sobre la respuesta, hay métodos que devuelven un ResponseEntity con el status code de la respuesta:
```java
var entity = new HttpEntity<>(review);
restTemplate.postForEntity("https://api.example.com/reviews", review, Review.class);
assertThat(response.getStatusCode(), is(201));
```

### Implementar el cliente
El RestTemplate permite cambiar la implementación, en este caso vamos a utilizar HTTPClient. Para poder usarlo necesitamos importar la librería:
```xml
<dependency>
    <groupId>org.apache.httpcomponents</groupId>
    <artifactId>httpclient</artifactId>
    <version>4.5.10</version>
</dependency>
```


Luego tenemos que instanciar el cliente y utilizar el factory de RestTemplate para HTTPClient, HttpComponentsClientHttpRequestFactory:
```java
var config = RequestConfig.custom().build();
var client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
var factory = new HttpComponentsClientHttpRequestFactory(client);
var restTemplate = new RestTemplate(factory);
```

El cliente es configurable, podemos agregarles diferentes timeouts:
```java
RequestConfig.custom()
             .setConnectTimeout(1000)
             .setSocketTimeout(1000)
             .build();
```
El connection timeout es el límite de tiempo de espera para establecer una conexión, mientras que el socket timeout es el máximo límite de inactividad entre dos paquetes consecutivos.

### Error handling
El RestTemplate usa para el manejo de errores la clase DefaultResponseErrorHandler, que ante status codes de error lanza una RestClientException. Sin embargo este comportamiento se puede sobrescribir, basta con implementar la interfaz ResponseErrorHandler:
```java
@Component
public class ErrorHandler implements ResponseErrorHandler {
  @Override
  public boolean hasError(ClientHttpResponse response) throws IOException {
    // TODO: implement
  }

  @Override
  public void handleError(ClientHttpResponse response) throws IOException {
    switch(response.getRawStatusCode()) {
      // TODO: implement
    }
  }
}
```

Luego se agrega al rest template:
```java
restTemplate.setErrorHandler(errorHandler);
```

## Configuración
Vimos anteriormente que el archivo application.properties sirve para configurar la aplicación. Toda combinación de clave valor que agreguemos ahí puede ser injectada a un componente. Por ejemplo, suponiendo que hubiéramos agregado la clave tmdb.endpoint:

```java
@Component
public class MovieClient {
  @Autowired
  public MovieClient(@Value("${tmdb.endpoint}") String endpoint) {}

}
``` 

Pero en lugar de agregar toda configuración en el mismo archivo se puede separar la configuración, según los criterios que creamos convenientes, en varios archivos. Para esto vamos a agrupar toda la configuración de los servicios externos en endpoints.properties. 

Una forma de tipificar estos valores es crear una clase de configuración. Esta clase es un simple objeto anotado con @Component, para que pueda ser inyectado, al cual vamos a indicarle dónde obtener la nueva información y el prefijo utilizado en las claves:

```java
@Component
@PropertySource("classpath:endpoints.properties")
@ConfigurationProperties(prefix = "tmdb")
public class TMDBConfig {
    private String host;

    public String getHost() {}

    public void setHost(String host) {}

}
``` 
De esta forma va a buscar en nuestro endpoints.properties los valores de las claves que comiencen con el prefijo tmdb y se correspondan con los campos del objeto. Con @PropertySource indicamos dónde encontrar la configuración y con @ConfigurationProperties indicamos el prefijo, de esta forma se inyectará automáticamente el valor indicado en la clave tmdb.host.

Luego podemos utilizar ese objeto de configuración para agregar el host al template:
```java
restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(tmdbConfig.getHost()));
```

## Scopes
Un componente en Spring puede tener diferentes scopes según la funcionalidad que se quiera implementar. Los más utilizados son:
* Singleton: existe un solo componente en la aplicación.
```java
@Bean
@Scope("singleton")
public class SingletonTest {
    //TODO: implementation.
}
```
* Prototype: crea una nueva instancia cada vez que es requerida.
```java
@Bean
@Scope("prototype")
public class PrototypeTest {
    //TODO: implementation.
}
```
* Request: este scope, que es exclusivamente para web, mantiene la instancia del objeto durante la request del usuario.
```java
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RequestScope {
    Map<String, String> requestMap;
 
    public String addValue(String key, String value) {
        requestMap.put(key, value);
    }
}
```
* Session: este scope, que también es para web, mantiene la instancia durante una sesión HTTP.
```java
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RequestScope {
    Map<String, String> requestMap;
 
    public String addValue(String key, String value) {
        requestMap.put(key, value);
    }
}
```
---
[Siguiente >>](https://github.com/gamestoy/checkout-spring-tutorial/tree/04_logging)

[<< Anterior](https://github.com/gamestoy/das-spring-boot/tree/02_rest-API)

[[Índice]](https://github.com/gamestoy/das-spring-boot#%C3%ADndice)
