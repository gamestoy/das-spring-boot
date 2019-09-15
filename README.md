Spring Boot
----

En este tutorial vamos a crear una API que permita buscar películas, ver el detalle, guardarlas como vistas, calificarlas y crear listas temáticas. Para esto vamos a usar la API de [The Movie Database](https://developers.themoviedb.org/3/getting-started/introduction) para obtener la información necesaria.

La aplicación tiene que:
* Permitir buscar películas
* Permitir obtener información de una película, con el detalle de los actores principales, género, reviews, etc.
* Permitir marcar una película como vista con cierto puntaje.
* Permitir armar listas temáticas con películas.

# Índice
## Capítulo I
* Crear default application con intellij o http://start.spring.io/
* Explicar annotations usadas
* Explicar server embebido
* Usa Tomcat
* Se puede cambiar por otro, ejemplo Jetty:
```
<properties>
	<servlet-api.version>3.1.0</servlet-api.version>
</properties>
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-web</artifactId>
	<exclusions>
		<!-- Exclude the Tomcat dependency -->
		<exclusion>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-tomcat</artifactId>
		</exclusion>
	</exclusions>
</dependency>
<!-- Use Jetty instead -->
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-jetty</artifactId>
</dependency>
```
## Capítulo II
* REST Services: crear un servicio de prueba
* annotations
* Diseño API: determinar qué servicios se van a exponer

## Capítulo II.2
* Consumir una API

## Capítulo III
* Guardado de datos en sesión
* Búsqueda de datos en secuencia

## Capítulo IV
* Logging

## Capítulo V
* Filtros
* Interceptors

## Capítulo VI
* Medir a mano tiempos
* Paralelización
* Thread local

## Capítulo VI.2
* Snapshots. Género es snapshoteable

## Capítulo VII
* Reemplazar sesión
* H2 - guardado en base de datos en memoria

## Capítulo VIII
*Aspectos: logging, performance.

## Anexo I
* Explicar annotations en general y crear un ejemplo de cómo levantar annotations y hacer algo

## Anexo II
* Thread pools

## Anexo III
* Web server, sockets
