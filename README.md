# Servicio detector de mutantes 

### Objetivo

Servicio que detecta si un humano es mutante basándose en su secuencia de ADN.

Se recibe como parámetro un array de Strings que representan cada fila de una tabla de (NxN) con la secuencia del ADN. 
Las letras de los Strings solo pueden ser: (A,T,C,G), las cuales representa cada base nitrogenada del ADN.


### Descripción

Microservicio RestFul desarrollado con tecnología Java (JDK 11), utilizando arquitectura hexagonal.



&nbsp;&nbsp;

---
![alt text](https://reflectoring.io/assets/img/posts/spring-hexagonal/hexagonal-architecture.png)




El proyecto tiene la siguiente estructura:


**../applications** <br />
**&nbsp;&nbsp;../configuration** <br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;UseCaseConfig.java <br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;SwaggerConfiguration.java <br />

**../domain** <br />
**&nbsp;&nbsp;../model** <br />
**&nbsp;&nbsp;&nbsp;../gateway** <br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;DnaRepository.java <br />
&nbsp;&nbsp;&nbsp;&nbsp;Dna.java <br />
&nbsp;&nbsp;&nbsp;&nbsp;DnaStats.java <br />
**&nbsp;&nbsp;../usecase** <br />
**&nbsp;&nbsp;&nbsp;../identify** <br />
**&nbsp;&nbsp;&nbsp;&nbsp;../analyze** <br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;AnalyzeSequencesUseCaseImpl.java <br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;DnaUseCaseImpl.java <br />
**&nbsp;&nbsp;&nbsp;&nbsp;../stats** <br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;StatsUseCaseImpl.java <br />

**../infrastructure** <br />
**&nbsp;../adapters** <br />
**&nbsp;&nbsp;../database** <br />
&nbsp;&nbsp;&nbsp;&nbsp;DnaData.java <br />
&nbsp;&nbsp;&nbsp;&nbsp;DnaDataRepository.java <br />
&nbsp;&nbsp;&nbsp;&nbsp;DnaRepositoryAdapter.java <br />
&nbsp;&nbsp;&nbsp;&nbsp;MutantsCount.java <br />
**&nbsp;../entrypoints** <br />
**&nbsp;&nbsp;../receivers** <br />
**&nbsp;&nbsp;&nbsp;../web** <br />
**&nbsp;&nbsp;&nbsp;&nbsp;../dna** <br />
**&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;../identify** <br />
**&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;../dto** <br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;DnaRequest.java <br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;DnaResponse.java <br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;DnaController.java <br />
**&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;../stats** <br />
**&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;../dto** <br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;StatsResponse.java <br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;StatsController.java <br />
**MainApplication.java** <br />

---

## Especificaciones técnicas

- JDK 11
- Maven   
- Spring Boot 2.3
- Spring Web Flux (Reactor)  
- H2 database
- Lombok
- Junit 5
- Cobertura de código: 80% (pom.xml)

```
  <limit>
    <counter>INSTRUCTION</counter>
    <value>COVEREDRATIO</value>
    <minimum>0.8</minimum>
  </limit>

```



## Compilar, verificar y ejecutar

Realizar los siguientes pasos:

***Compilar***
```
mvn clean compile
```

***Verificar***
```
mvn clean verify
```

***Ejecutar***
```
mvn spring-boot:run -Dspring.profiles.active=h2
```

El servicio utiliza el puerto 9100 (configurable)


## Servicio

El servicio tiene la siguiente dirección:
```
http://localhost:9100
```


***Contratos disponibles***

Utilizando la siguiente dirección se pueden consultar los contratos disponibles:
```
http://localhost:9100/swagger-ui.html
```
- **POST**
  /api/v1/mutants/verify
  
  verify DNA sequence for discovery MUTANTS


- **GET**
  /api/v1/mutant/stats
  
  Get current stats


***Pruebas***

&nbsp;&nbsp;Para realizar las pruebas correspondientes, puede utilizar los siguientes comandos:


&nbsp;&nbsp;**Secuencia correcta para mutante:**

- **POST**
  /api/v1/mutants/verify

```
curl --location --request POST 'http://localhost:9100/api/v1/mutants/verify' \
--header 'Content-Type: application/json' \
--data-raw '{
"dna":["TTGCGA","TAGTGC","TTATGT","AGAAGG","cCCCTA","TCACTG"]
}'
```

&nbsp;**Respuesta:**  HTTP 200 ok

```
{
  "isMutant": true
}
```

&nbsp;&nbsp;**Secuencia incorrecta para mutante( ¡Es humano! ):**

- **POST**
  /api/v1/mutants/verify

```
curl --location --request POST 'http://localhost:9100/api/v1/mutants/verify' \
--header 'Content-Type: application/json' \
--data-raw '{
"dna":["TTGCGA","TAGTGC","TTATGT","AGAAGG","TCCCTA","TCACTG"]
}'
```

&nbsp;**Respuesta:** HTTP 403 Forbidden

```
{
"timestamp": "2021-02-01T17:05:33.231+00:00",
"status": 403,
"error": "Forbidden",
"message": "Not found MUTANT sequences.",
"path": "/api/v1/mutants/verify"
}
```

- **GET**
  /api/v1/mutant/stats
```
curl --location --request GET 'http://localhost:9100/api/v1/mutants/stats'
```

&nbsp;**Respuesta:** HTTP 200 Ok
```
{
"countMutantDna": 4,
"countHumanDna": 10,
"ratio": 0.4
}
```

## Infraestructura

**Docker**

&nbsp;
Ejecute los siguientes pasos para construir y ejecutar una imagen utilizando DOCKER:

&nbsp;**Construir imágen:**
&nbsp;
- docker build -t mdt/mutant .

&nbsp;**Ejecutar imágen**
&nbsp;
- docker run -p 9100:9100 -e "SPRING_PROFILES_ACTIVE=h2" mdt/mutant:latest


## Consideraciones


- Tener en cuenta que la API puede recibir fluctuaciones agresivas de tráfico (Entre 100 y 1
millón de peticiones por segundo).

Con la consideración anterior, se determinaron las siguientes especificaciones de desarrollo:
&nbsp;
- Uso de arquitectura hexagonal: servicio con bajo acoplamiento, orientado al dominio 
&nbsp;
- Spring Web Flux: peticiones no bloqueantes, servicio responsivo, resiliente y elástico. Ver
[Manifiesto Sistemas Reactivo](https://www.reactivemanifesto.org/)
  

# Servicio en producción

Las siguientes consideraciones para desplegar el servicio en ambiente productivo:

- Utilizar una base de datos NoSQL, por ejemplo: MongoDb
- Utilizar un proveedor de servicios en la nube como Amazon AWS, Google App Service o Azure
- Utilizar API Gateway considerando el uso y tráfico.
- Para conocer el número de instancias -del servicio- necesarias y máximas a utilizar (auto escalables), se recomienda
  pruebas de rendimiento utilizando la herramienta JMeter (o similiar, puede ser un robot utilizando BDD) tomando en 
  cuenta los valores de tráfico.
  
