# Préstamo CRUD

Este proyecto implementa un **CRUD de Préstamos** con manejo de intereses utilizando **Java 21** y **Spring Boot**, cumpliendo con los requisitos del examen teórico-práctico para programadores senior. Maneja préstamos con estados (`PENDIENTE`, `PAGADO`) y calcula intereses según el tipo de cliente (`VIP`: 5%, `REGULAR`: 10%). Usa almacenamiento en memoria, un contrato OpenAPI 2.0, pruebas unitarias, logging, y código limpio con genéricos, `var`, y lambdas.

## Características
- **Java 21**: Uso de `Pattern Matching`, `var`, y lambdas.
- **Spring Boot**: API RESTful con endpoints para operaciones CRUD y cálculo de intereses.
- **OpenAPI 2.0**: Documentación de la API en `src/main/resources/openapi.yaml`.
- **Pruebas unitarias**: Cobertura completa de operaciones CRUD y cálculo de intereses usando JUnit 5 y Mockito.
- **Logging**: Implementado con SLF4J para registrar operaciones.
- **Genéricos**: Uso de `GenericRepository` y `GenericService` para reusabilidad.

## Requisitos
- **Java 21** (JDK 21)
- **Maven** (3.6 o superior)
- **IDE** (opcional): IntelliJ IDEA, Eclipse, o similar
- **Herramientas para pruebas**: Postman o cURL para probar la API

## Estructura del proyecto
```
prestamo-crud
├── src/main/java/com/example/prestamo
│   ├── config/OpenApiConfig.java
│   ├── controller/PrestamoController.java
│   ├── model/Prestamo.java
│   ├── model/EstadoPrestamo.java
│   ├── model/Cliente.java
│   ├── model/TipoCliente.java
│   ├── repository/GenericRepository.java
│   ├── repository/PrestamoRepository.java
│   ├── repository/ClienteRepository.java
│   ├── service/GenericService.java
│   ├── service/PrestamoService.java
│   ├── service/ClienteService.java
│   ├── PrestamoApplication.java
├── src/main/resources
│   └── openapi.yaml
├── src/test/java/com/example/prestamo
│   └── service/PrestamoServiceTest.java
├── pom.xml
├── README.md
```

## Instalación y ejecución
1. **Clonar el repositorio**:
   ```bash
   git clone <URL_DEL_REPOSITORIO>
   cd prestamo-crud
   ```

2. **Construir el proyecto**:
   ```bash
   mvn clean install
   ```

3. **Ejecutar la aplicación**:
   ```bash
   mvn spring-boot:run
   ```
   La aplicación estará disponible en `http://localhost:8080`.

4. **Acceder a la documentación de la API**:
   - El contrato OpenAPI está en `src/main/resources/openapi.yaml`.
   - Si usas `springdoc-openapi`, accede a Swagger UI en: `http://localhost:8080/swagger-ui.html`.

## Endpoints de la API
| Método | Endpoint                     | Descripción                             |
|--------|------------------------------|-----------------------------------------|
| GET    | `/api/prestamos`             | Obtiene todos los préstamos activos     |
| GET    | `/api/prestamos/{id}`        | Obtiene un préstamo por ID              |
| POST   | `/api/prestamos`             | Crea un nuevo préstamo                  |
| PUT    | `/api/prestamos/{id}`        | Actualiza un préstamo por ID            |
| DELETE | `/api/prestamos/{id}`        | Elimina un préstamo por ID              |
| GET    | `/api/prestamos/{id}/total`  | Calcula el monto total con intereses    |

**Ejemplo de cuerpo para POST/PUT**:
```json
{
  "id": "123",
  "monto": 1000.0,
  "clienteId": "1",
  "fecha": "2025-06-21",
  "estado": "PENDIENTE"
}
```

## Pruebas unitarias
Para ejecutar las pruebas unitarias:
```bash
mvn test
```
- Los tests cubren todas las operaciones CRUD y el cálculo de intereses (5% para VIP, 10% para REGULAR).
- Ubicación: `src/test/java/com/example/prestamo/service/PrestamoServiceTest.java`.

## Logging
- Se usa SLF4J para registrar operaciones CRUD y errores.
- Los logs se pueden configurar en `src/main/resources/application.properties`:
  ```properties
  logging.level.com.example.prestamo=DEBUG
  ```

## Notas
- El almacenamiento es en memoria usando una `List<Prestamo>` y `List<Cliente>`.
- El cálculo de intereses usa `Pattern Matching` para determinar la tasa según el tipo de cliente.
- El proyecto utiliza genéricos para evitar duplicación de código.
- Compatible con Java 21, aprovechando características modernas.

## Solución de problemas
- **Error de dependencias**: Asegúrate de que el `pom.xml` incluye todas las dependencias necesarias y ejecuta `mvn clean install`.
- **Bean no encontrado**: Verifica que todos los componentes estén anotados con `@Repository`, `@Service`, o `@Controller` y estén en el paquete correcto (`com.example.prestamo.*`).
- **API no responde**: Confirma que el puerto `8080` está libre.

Para más detalles, consulta el contrato OpenAPI en `openapi.yaml` o contacta al desarrollador.