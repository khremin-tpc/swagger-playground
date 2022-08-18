# Example of integration SpringDoc into Spring Boot app

## How to run using Gradle

```
./gradlew bootRun
```

## How to build a runnable jar

```
./gradlew bootJar
```

The fat jar will be at

```
build/libs/
```

To run this jar simply

```
java -jar build/libs/com.khremin.springdoc-0.0.1-SNAPSHOT.jar
```

## After start

After start Swagger UI will be available at [http://127.0.0.1:8080/swagger-ui/](http://127.0.0.1:8080/swagger-ui/)

## Default credentials

```json
{
  "email": "root@khremin.com",
  "password": "q1w2e3r4"
}
```