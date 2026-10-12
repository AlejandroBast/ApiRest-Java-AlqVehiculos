# API REST – Sistema de alquiler de vehículos
## By Nicolas Bastidas,  Michael Lagos,  Willian Inzandara

API hecha con Java 17, Spring Boot, Maven y PostgreSQL. Controla los errores del negocio (vehículo ocupado, licencia vencida, devolución tardía, vehículo inexistente) con excepciones personalizadas.

Estos son los pasos que se siguieron para construirla. Sirven para replicar la misma estructura con cualquier caso de estudio: solo hay que cambiar `Vehiculo`, `Cliente`, `Alquiler`, etc. por los nombres del dominio.

## 0. Analizar el caso de estudio (antes de programar)
| Se busca en el enunciado… | Se convierte en… | En este proyecto |
|---|---|---|
| Sustantivos principales | **Entidades** | `Vehiculo`, `Cliente`, `Alquiler` |
| Sus datos | **Atributos** | placa, marca, modelo, documento, licencia, fechas |
| "Un X tiene muchos Y" | **Relaciones** `@ManyToOne` / `@OneToMany` | un `Alquiler` pertenece a un `Vehiculo` y a un `Cliente` |
| "Hay tipos de X" | **Herencia** | no aplica en esta versión |
| Estados | **Enums** | `EstadoVehiculo` (DISPONIBLE, ALQUILADO), `EstadoAlquiler` (ACTIVO, FINALIZADO) |
| Reglas ("no se puede…") | **Métodos y validaciones dentro de la entidad** | `Vehiculo.alquilar()`, `Cliente.validarLicencia()`, `Alquiler.devolver()` |
| Verbos (alquilar, devolver…) | **Endpoints** | `POST /api/alquileres`, `POST /api/alquileres/{id}/devolver` |

Las cuatro excepciones del enunciado:

| Excepción | Cuándo se lanza | HTTP |
|---|---|---|
| `VehiculoNoDisponibleException` | el vehículo ya está alquilado | 409 |
| `LicenciaInvalidaException` | el cliente no tiene licencia o está vencida | 400 |
| `DevolucionTardiaException` | se devuelve después de la fecha pactada (la devolución queda registrada) | 409 |
| `VehiculoNoEncontradoException` | el vehículo buscado no existe | 404 |

## 1. Crear el proyecto
Por consola con el generador de [start.spring.io](https://start.spring.io): Maven, Java 17 y las dependencias **Spring Web, Spring Data JPA, Validation y PostgreSQL Driver**.

```bash
curl https://start.spring.io/starter.zip -d type=maven-project -d language=java -d javaVersion=17 \
  -d groupId=com.alquiler -d artifactId=vehiculos -d packageName=com.alquiler.vehiculos \
  -d dependencies=web,data-jpa,validation,postgresql -o proyecto.zip
```

## 2. Configurar la BD
Crear la base en PostgreSQL:

```sql
CREATE DATABASE alquiler_vehiculos;
```

Y revisar `src/main/resources/application.properties` (la clave se lee de la variable `DB_PASSWORD`, por defecto `postgres`):
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/alquiler_vehiculos
spring.datasource.username=postgres
spring.datasource.password=${DB_PASSWORD:postgres}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

## 3. Paquetes
```
domain/  repository/  dto/request/  dto/response/  mapper/  service/  controller/  exception/
```

## 4. Domain: enums y entidades
Las reglas de negocio viven dentro de la entidad. Ejemplo (`Vehiculo`):
```java
public void alquilar() {
    if (estado != EstadoVehiculo.DISPONIBLE) throw new VehiculoNoDisponibleException(placa);
    estado = EstadoVehiculo.ALQUILADO;
}
```
`Alquiler` valida la licencia, marca el vehículo como alquilado y al devolver detecta el retraso.

## 5. Repository (uno por entidad)
```java
public interface VehiculoRepository extends JpaRepository<Vehiculo, UUID> {
    Optional<Vehiculo> findByPlaca(String placa);
    boolean existsByPlaca(String placa);
}
```

## 6. DTOs
Un `record` por cada request y response:
```java
public record CrearVehiculoRequest(@NotBlank String placa, @NotBlank String marca, @NotBlank String modelo) {}
public record VehiculoResponse(UUID id, String placa, String marca, String modelo, String estado) {}
```

## 7. Mapper (entidad → DTO)
A mano, con un método estático:
```java
public static VehiculoResponse toResponse(Vehiculo v) {
    return new VehiculoResponse(v.getId(), v.getPlaca(), v.getMarca(), v.getModelo(), v.getEstado().name());
}
```

## 8. Excepciones
Las cuatro del negocio más `NotFoundException`, y un `GlobalExceptionHandler` (`@RestControllerAdvice`) que las convierte en respuestas `{"error": "mensaje"}` con su código HTTP.

## 9. Service (siempre el mismo patrón)
Buscar → crear o modificar → guardar → mapear.
```java
@Transactional
public AlquilerResponse crear(CrearAlquilerRequest r) {
    Vehiculo vehiculo = vehiculoRepo.findById(r.vehiculoId())
        .orElseThrow(() -> new VehiculoNoEncontradoException(r.vehiculoId().toString()));
    Cliente cliente = clienteRepo.findById(r.clienteId())
        .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));
    return AlquilerMapper.toResponse(repo.save(new Alquiler(vehiculo, cliente, r.fechaDevolucion())));
}
```

## 10. Controller
| Recurso | Acción | Método y ruta |
|---|---|---|
| Vehículos | Crear | `POST /api/vehiculos` |
| | Listar | `GET /api/vehiculos` |
| | Ver uno | `GET /api/vehiculos/{id}` |
| | Buscar por placa | `GET /api/vehiculos/placa/{placa}` |
| | Actualizar | `PUT /api/vehiculos/{id}` |
| | Eliminar | `DELETE /api/vehiculos/{id}` |
| Clientes | Crear | `POST /api/clientes` |
| | Listar | `GET /api/clientes` |
| | Ver uno | `GET /api/clientes/{id}` |
| | Actualizar | `PUT /api/clientes/{id}` |
| | Eliminar | `DELETE /api/clientes/{id}` |
| Alquileres | Alquilar | `POST /api/alquileres` |
| | Listar | `GET /api/alquileres` |
| | Ver uno | `GET /api/alquileres/{id}` |
| | Devolver | `POST /api/alquileres/{id}/devolver` |

## 11. Probar
Ejecutar la app (Hibernate crea las tablas solo):

```bash
./mvnw spring-boot:run
```

1. Crear un vehículo (`POST /api/vehiculos`) y un cliente (`POST /api/clientes`) y copiar sus `id`.
   ```json
   { "placa": "ABC123", "marca": "Toyota", "modelo": "Corolla" }
   { "nombre": "Ana Perez", "documento": "1001", "licenciaVence": "2030-01-01" }
   ```
2. Alquilar con esos `id` (`POST /api/alquileres`):
   ```json
   { "vehiculoId": "...", "clienteId": "...", "fechaDevolucion": "2030-01-10" }
   ```
3. Probar los errores:
   - Alquilar el mismo vehículo otra vez → **409** `VehiculoNoDisponibleException`.
   - Cliente sin licencia o con licencia vencida → **400** `LicenciaInvalidaException`.
   - `GET /api/vehiculos/placa/ZZZ999` o un id falso → **404** `VehiculoNoEncontradoException`.
   - Devolver un alquiler con la fecha pactada ya pasada → **409** `DevolucionTardiaException` (la devolución queda guardada).
   - Dato vacío (`"placa": ""`) → **400**.

**Orden de trabajo por cada entidad:** Entidad → Repository → DTOs → Mapper → Service → Controller → Probar. Se termina una entidad completa antes de pasar a la siguiente.
