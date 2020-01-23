Diseñar la API
---
Lo primero que tenemos que hacer es definir qué información vamos a devolver en nuestros servicios. Recordando, nuestra aplicación tiene que:

1. Permitir buscar películas: este servicio debería recibir  un conjunto de palabras y un número de página, y devolver un listado de películas.
2. Permitir obtener el detalle de una película: este servicio debería recibir un id y devolver el detalle de una película. La misma en principio debería tener un id, un nombre, una descripción y una lista de géneros.
3. Permitir crear y borrar una lista temática de películas: debería poder crear o borrar una lista para un usuario, y permitir agregar o eliminar una serie de películas de una lista.

### @Controller
Esta annotation es una especialización de **@Component**, indicando que esa clase es un controller y permitiendo la utilización de otras funcionalidades relacionadas con el routing y la serialización de las respuestas.

Supongamos que queremos crear un controller que nos permita crear, obtener, borrar y buscar usuarios, Para crear un controller simplemente se le agrega **@Controller** a una clase:

```
@Controller
public Class UserController {

}
```

Pero esto de por sí no agrega ninguna funcionalidad. Lo primero que tenemos que hacer es agregar un método y los mapeos correspondientes, tanto de routing como de parámetros:
```java
@Controller
public Class UserController {

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public @ResponseBody User getUser(@PathVariable String id) {
        // retrive user.
        return user;
    }

}
```

Para eso agregamos tres nuevos annotations:
* RequestMapping: recibe dos parámetros, value con el path en el que ese método va a responder, y method, con el método HTTP correspondiente.
* PathVariable: en el path indicado en @RequestMapping estamos indicando un nombre de variable entre llaves ({}), este annotation permite asignar a la variable correspondiente al valor indicado en el path.
* ResponseBody: indica que la respuesta del método tiene que enviarse en el body de la respuesta. También se puede incluir a nivel de clase si todos los métodos la van a implementar.

Agreguemos el resto de los métodos:

```java
@Controller
@ResponseBody 
public Class UserController {

    @GetMapping(value = "/users/{id}")
    public User getUser(@PathVariable String id) {
        // retrieve user.
        return user;
    }

    @GetMapping(value = "/users")
    public List<User> searchUser(@RequestParam(value = "q") String query, @RequestParam Optional<Integer> page) {
        // search users.
        return users;
    }
    
    @PostMapping(value = "/users")
    public User createUser(@RequestBody User user) {
        // create user.
        return user;
    }

    @DeleteMapping(value = "/users/{id}")
    public void deleteUser(@PathVariable String id) {
        // delete user.
    }

}
```

En este ejemplo hicimos un cambio en las annotations usadas: además de llevar el ResponseBody al scope de clase, cambiamos los @RequestMapping, en los que se debía especificar el método HTTP, por **@GetMapping**, **@PostMapping** y **@DeleteMapping**. 

También se incluyen dos nuevas annotations: @RequestParam y @RequestBody. RequestParam asigna un parámetro del query string a una variable, y tiene dos parámetros: value, que  y required. En el ejemplo de la búsqueda estamos usando dos, uno que asigna el valor del parámetro "q" a la variable "query", mientras que el otro parámetro define una variable opcional "page" que busca en la request una parámetro con el mismo nombre. RequestBody se utiliza para mappear el contenido del body a un objeto Java, asignándoselo a una variable. 

### @RestController
Este annotation combina @Controller y @ResponseBody, por lo que el controller quedaría:
```java
@RestController
public Class UserController {

    @GetMapping(value = "/users/{id}")
    public User getUser(@PathVariable String id) {
        // retrieve user.
        return user;
    }

    @GetMapping(value = "/users")
    public List<User> searchUser(@RequestParam(value = "q") String query, @RequestParam Optional<Integer> page) {
        // search users.
        return users;
    }
    
    @PostMapping(value = "/users")
    public User createUser(@RequestBody User user) {
        // create user.
        return user;
    }

    @DeleteMapping(value = "/users/{id}")
    public void deleteUser(@PathVariable String id) {
        // delete user.
    }

}
```

### Compresión
Una mejora de performance que podemos realizar es comprimir la respuesta de nuestra API. Spring Boot nos permite configurarlo simplemente agregando al application.properties, el archivo default de configuración, los siguientes valores:
```properties
server.compression.enabled=true
server.compression.min-response-size=2048
```

### Más información
* [API Design - Microsoft](https://docs.microsoft.com/en-us/azure/architecture/best-practices/api-design)
* [Undisturbed REST](https://www.mulesoft.com/lp/ebook/api/restbook)
 
 
--- 
[Siguiente >>](https://github.com/gamestoy/checkout-spring-tutorial/tree/03_webservices)

[<< Anterior](https://github.com/gamestoy/das-spring-boot/tree/01_create_project)

[[Índice]](https://github.com/gamestoy/das-spring-boot#%C3%ADndice)
