# people-rest-api
A sample CRUD using Spring Rest API to manage people

# Installation

1. Clone this repository

```
git clone https://github.com/flormariani/people-rest-api.git
```

2. Compile the sources and run the application

```
cd rest-people-api
mvn clean spring-boot:run
```

# Rest Methods

* GET /personas --> List all people
* GET /personas/id --> Get information from given persona
* POST /personas --> Create new persona
* PUT /personas/id --> Update all data for given people id
* DELETE /personas/id --> Delete for given people id
* Get /estadisticas --> Get statistics
* Get /relaciones

# User attributes

* id: autonumeric
* tipoDoc: String
* nroDoc: numeric
* pais: String
* sexo: String. Possible values: 'F' or 'M'
* fechaNacimiento: Date format "dd/MM/yyyy"
* idPadre: Long.


# Swagger access

Api documented with Swagger. You can access to the UI on http://localhost:8081/swagger-ui.html