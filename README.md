MongoDB
---

### Objetivo
Actualmente estamos usando un mapa en memoria para guardar la información de las listas de películas de cada usuario. Se debe reemplazar ese mapa por una base de datos para persistir las listas ante una caída o reinicio de la aplicación y para centralizar la fuente de datos. En este caso vamos a usar MongoDB, no porque sea la opción más adecuada para el caso de uso, sino porque es más fácil para configurar.
Por lo tanto, en este capítulo vamos a:
* Configurar localmente MongoDB con una colección para la lista de películas.
* Guardar información de la lista: nombre, fecha de creación, usuario y lista de películas. A su vez, la lista de películas debe tener un id y fecha en que se agregó a la lista.
* El servicio para obtener listas deberá devolver nombre, usuario, fecha de creación y la lista de películas. La lista de películas deberá incluir id, nombre y fecha de estreno.
* La actualización del listado de películas debe usar las operaciones sobre arrays que permite MongoDB.

### Configurar la base de datos
Primero debe [instalarse MongoDB](https://docs.mongodb.com/manual/installation/#mongodb-community-edition-installation-tutorials) en el sistema operativo correspondiente. Una vez instalado, para usar el shell de MongoDB debe ejecutarse:
```
> mongo
```

Para empezar a usarlo, debemos crear la base de datos. Para crearla, por ejemplo una llamada "spring-boot", hay que ejecutar el siguiente comando:
```
> use spring-boot
```

Luego hay que crear la colección donde guardar los datos:
```javascript
db.createCollection("test")
```

Como prueba, insertaremos un documento en la colección:
```javascript
db.test.insert({field1: "value1", field2: "value2"})
```

Para ver si efectivamente se insertó, listamos todos los documentos de la collección:
```javascript
db.test.find()
```

Para borrar el documento insertado, debemos tener en cuenta el campo "_id" del mismo y ejecutar:
```javascript
db.test.deleteOne({_id: ObjectId(<id>)})
```

### Configurar Spring
Para configurar MongoDB en una aplicación de Spring Boot lo primero que tenemos que hacer es agregar la dependencia en Maven:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>
```

Una vez descargada, indicamos en el archivo application.properties dónde se encuentra la base de datos:
```
spring.data.mongodb.uri=mongodb://localhost/<db name>
```

### Crear las entidades
Para empezar a guardar datos, lo primero que vamos a hacer es definir qué entidades vamos a guardar. Por ejemplo, si quisiéramos guardar objetos de tipo Person con nombre y apellido, simplemente podríamos definirlo de esta forma:
  ```java
  @Document("customers")
  class Person {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    
    //Boilerplate as usual...
  }
  ```
Estas entidades son simples POJOs con algunas particularidades: las annotations @Document y @Id. 

La primera sirve para indicar el nombre de la colección en la que se va a guardar. Y en caso de no indicar alguna, se va a guardar en la colección formada por el nombre de la clase en minúscula, que en este caso sería "person". La segunda, un poco más obvia, indica cuál es el campo que va a ser el id del documento.

### MongoTemplate
Habiendo creado nuestras entidades, es momento de empezar a operar sobre la colección. Para hacerlo se utiliza una instancia de MongoTemplate que nos permite realizar las operaciones más comunes sobre las entidades.

#### Buscar por Id
Por ejemplo, para buscar la persona con id "1":
```java
MongoTemplate template = ...
template.findById("1", Person.class);
```

#### Buscar
Para buscar una persona por nombre:
```java
MongoTemplate template = ...
var query = Query.query(Criteria.where("firstName").is("Rigoberto"));
template.find(query, Person.class);
```
En este caso estamos creando un objeto de tipo Query, que recibe otro objeto de tipo Criteria el cual indica que voy a buscar todas las personas que se llamen Rigoberto.

#### Crear
Para crear un documento:
```java
MongoTemplate template = ...
var person = new Person("Ricky", "Fort");
template.insert(person);
```

#### Actualizar 
Para actualizar un documento se utiliza el método updateFirst. Por ejemplo, cambiamos el primer documento cuyo nombre sea Rigoberto por Hugo:
```java
MongoTemplate template = ...
var query = Query.query(Criteria.where("firstName").is("Rigoberto"));
var update = new Update().set("firstName", "Hugo");
template.updateFirst(query, update, Person.class);
```
También existe la posibilidad de actualizar múltiples documentos utilizando updateMulti.

### MongoRepository 
Spring nos da otra posibilidad de realizar estas operaciones comunes de una forma mucho más fácil utilizando MongoRepository. Esta interfaz sólo requiere que le indiquemos sobre qué entidad debe operar y cuál es el tipo del campo Id. En nuestro caso, Person es la entidad y el id es de tipo String:
```java
public interface CustomerRepository extends MongoRepository<Person, String> {
  
}
```
Spring detecta automáticamente las implementaciones de MongoRepository en el proyecto y nos permite realizar varias operaciones una vez inyectada:
```java
@Service
class CustomerService {
  @Autowired
  private CustomerRepository repository;
  
  void test() {
    var person = new Person("Moria", "Casán");
    repository.findById("1");
    repository.findAll();
    repository.insert(person);
    repository.save(person);
    repository.deleteById("1");
  }
}
```

También podemos realizar operaciones más flexibles, indicándolas como métodos en la interfaz que definimos. La implementación de los mismos se hace automáticamente y estará dada por el nombre del método o por la annotation @Query:
```java
public interface CustomerRepository extends MongoRepository<Person, String> {
 // Lo que se encuentra después del By es el campo por el que se quiere buscar
 List<Person> findByFirstName(String firstName); 
 
 // Este método hace lo mismo que el anterior
 @Query("{ firstName: ?0 }")
 List<Person> findByFirstNameAnnotated(String firstName);
 
 // Lo que se encuentra después del By es el campo por el que se quiere buscar
 List<Person> findFirstNameLike(String regex); 
  
 // Este método hace lo mismo que el anterior
 @Query("{ firstName: { $regex: ?0 } }")
 List<Person> findFirstNameLikeAnnotated(String regex);
 
 // Busca por dos campos
  @Query("{ firstName: ?0, lastName: ?1 }")
  List<Person> findByFullName(String firstName, String lastName);
 
}
```

Cuando necesitamos cierta flexibilidad en las operaciones, algo que MongoRepository no nos da, podemos implementarlas con MongoTemplate y agregarlas a la implementación:
```java
public interface CustomOperations {
  public void customOperation(String id);
}
public class CustomerRepositoryImpl implements CustomOperations {
  @Autowired
  MongoTemplate template;
  
  @Override
  public void customOperation(String id) {
    // do stuff
  }
}
```
La magia de Spring hace que al llamar la clase igual que la interfaz original agregándole el sufijo "Impl", al inyectarla de la misma forma que hicimos antes nos permite utilizar el nuevo método:
```java
@Service
class CustomerService {
  @Autowired
  private CustomerRepository repository;
  
  void test() {
    repository.customOperation("1");
  }
}
```

[<< Anterior](https://github.com/gamestoy/das-spring-boot/tree/10_cache)

[[Índice]](https://github.com/gamestoy/das-spring-boot#%C3%ADndice)

