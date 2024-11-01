# Rapid Ticket API Documentation

## Contexto

Rapid Ticket es una plataforma online de venta de tickets para conciertos artísticos. Con el objetivo de escalar y aumentar su base de clientes, esta API permite gestionar la venta de tickets para diferentes espectáculos en teatros, estadios y otros lugares, facilitando la reserva de localidades para funciones específicas.

## Tabla de Contenidos

- [Características de la API](#características-de-la-api)
- [Tecnologías Utilizadas](#tecnologías-utilizadas)
- [Modelo Relacional](#modelo-relacional)
- [Endpoints de la API](#endpoints-de-la-api)
- [Control de Concurrencia](#control-de-concurrencia)
- [Pruebas](#pruebas)
- [Despliegue en la Nube](#despliegue-en-la-nube)
- [Instrucciones para Ejecutar](#instrucciones-para-ejecutar)
- [Repositorio](#repositorio)

## Características de la API

- **Listar Shows y Funciones**: Permite obtener la lista de shows disponibles junto con sus características.
- **Consultar Disponibilidad de Butacas**: Para cada función, se pueden listar las butacas disponibles y sus precios.
- **Reservar Butacas**: Los usuarios pueden realizar reservas, especificando su DNI, nombre y las butacas seleccionadas.
- **Búsqueda Avanzada**: Posibilidad de buscar shows basados en criterios como fechas, rango de precios y orden de resultados.
- **Control de Concurrencia**: Garantiza que no se puedan realizar reservas simultáneas de la misma localidad.

## Tecnologías Utilizadas

- **Backend**: Node.js con Express
- **Base de Datos**: PostgreSQL
- **Caché**: Redis
- **Autenticación**: JWT
- **Infraestructura en la Nube**: AWS o GCP
- **Orquestación**: Kubernetes

## Modelo Relacional

La base de datos está estructurada en varias tablas:

- **Usuario**: Información sobre los usuarios (administradores y clientes).
- **Lugar**: Datos de los lugares donde se realizan los shows.
- **Sección**: Información sobre las secciones dentro de cada lugar.
- **Show**: Datos sobre los espectáculos.
- **Función**: Detalles de las funciones programadas de cada show.
- **Butaca**: Información sobre las butacas disponibles.
- **Reserva**: Registro de las reservas realizadas por los usuarios.
- **Reserva_Butaca**: Relación entre reservas y butacas.

## Endpoints de la API

En los siguientes EndPoints se define la documentación de los recursos web expuestos para resolver la iniciativa o prueba técnica:

- http://localhost:8080/meli/swagger-ui/index.html
- http://localhost:8080/meli/api-docs

### 1. Listar Shows

**GET** `/shows`

**Parámetros de consulta**:
- `fechaInicio`
- `fechaFin`
- `precioMin`
- `precioMax`
- `orden`

### 2. Consultar Disponibilidad de Butacas

**GET** `/seats/available/{showId}/{funcionId}`

**Respuesta**:
- ID de butaca
- Disponibilidad
- Precio

### 3. Realizar Reserva

**POST** `/reservations`

**Cuerpo de la solicitud**:
```json
{
    "dni": "12345678",
    "nombre": "Nombre del Espectador",
    "showId": 1,
    "funcionId": 1,
    "seats": [1, 2, 3]
}
```

**Respuesta**: Ticket de reserva confirmando la transacción.

## Control de Concurrencia

Si una butaca ya fue reservada, la API devolverá un error de conflicto (`HTTP 409`).

## Pruebas

Para asegurar la calidad del código, se recomienda usar:

- **Pruebas Unitarias**: Mockito

## Despliegue en la Nube

La API debe ser desplegada utilizando contenedores Docker en render con una capa gratuita.

## Instrucciones para Ejecutar

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/josmejia2401/spring-boot-meli.git
   cd spring-boot-meli
   ```

2. Instalar dependencias:
   ```bash
   mvn clean install
   ```

3. Configurar variables de entorno:
 - Crear un archivo `.env` basado en el `.env.example`.

4. Iniciar la aplicación:
   ```bash
   java -jar target/spring-boot-meli.jar
   ```

5. Ejecutar pruebas:
   ```bash
   mvn -Dtest=SeosSdkAutomationApplicationTests test
   mvn exec:java -Dexec.mainClass="com.josmejia2401.Application" -Dexec.classpathScope=test
   ```

## Repositorio

El código fuente y la documentación adicional se pueden encontrar en el siguiente enlace:

[Repositorio de Rapid Ticket](https://github.com/josmejia2401/spring-boot-meli.git)

---

Este archivo `README.md` proporciona una visión general completa de la API de Rapid Ticket, incluyendo sus características, tecnologías utilizadas y cómo ejecutar y probar el proyecto.



