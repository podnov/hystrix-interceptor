# hystrix-interceptor

![Build Status](https://travis-ci.org/podnov/hystrix-interceptor.svg?branch=master)

Automatically intercept and wrap method invocations in a [Hystrix](https://github.com/Netflix/Hystrix) command using [Java EE interceptors](http://docs.oracle.com/javaee/6/tutorial/doc/gkeed.html).

```
@HystrixIntercept(groupKey = "PeopleGroup")
@Stateless
public class PersonDAO {

    public List<Person> getAllPeople() {
        ...
    }
    
    public Person getPersonByName(String name) {
        ...
    }

}

@Stateless
public class OrganizationsDAO {

    @HystrixIntercept(groupKey = "OrganizationsGroup")
    public List<Organization> getAllOrganizations() {
        ...
    }
    
    @HystrixIntercept(groupKey = "OrganizationsWithPeopleGroup")
    public Organization getOrganizationByNameWithPeople(String name) {
        ...
    }
    
    @HystrixIntercept(groupKey = "OrganizationsGroup", commandKey = "getOrganizationByName")
    public Organization terribleMethodName(String name) {
        ...
    }

}
```