# Documentation

### How to use this library

This library contains 2 interfaces: `CrudRepository<T, ID>` and `CrudService<T, ID>`, which contains contracts for repository and service respectively,
and abstract class `AbstractCrudService<T, ID, R extends CrudRepository<T, ID>>`, which contains an implementation of CRUD methods.
* T - type of your entity.
* ID - type of ID of your entity
* R - type of your repository, which extends `CrudRepository<T, ID>` to ensure contract.
---
To create your own CRUD service, you must do the following:
1. Create repository for your entity as usual (for example, by extending `JpaRepository`).
2. Make it extend `CrudRepository` from this library. Make sure to specify entity and ID types.
3. Create interface for your service, make it extend `CrudService`. Make sure to specify entity and ID types, they should be the same as in the previous step.
4. Create service implementation class.
5. Make it extend `AbstractCrudService<T, ID, R extends CrudRepository<T, ID>>`. Third type must be a repository you created at step 1.
6. Also, make it implement an interface you created at step 3.
7. Create a constructor in service implementation. It should have one parameter - the repository you created at step 1.
Call the super constructor in body and pass the parameter there.
---
### Example

If you have an entity `Person` with id of type `Long`, then your code should look like this:

**Repository (step 1 and 2)**
```java
@Repository
public interface PersonRepository extends JpaRepository<Person, Long>, CrudRepository<Person, Long> {

}
```
**Service interface (step 3)**
```java
public interface PersonService extends CrudService<Person, Long> {
    
}
```
**Service implementation (steps 4-7)**
```java
@Service
public class PersonServiceImpl extends AbstractCrudService<Person, Long, PersonRepository> implements PersonService {
    public PersonServiceImpl(PersonRepository repository) {
        super(repository);
    }
}
```