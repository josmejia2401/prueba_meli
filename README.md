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

### 1. Listar Shows

**GET** `/shows`

**Parámetros de consulta**:
- `fechaInicio`
- `fechaFin`
- `precioMin`
- `precioMax`
- `orden`

### 2. Consultar Disponibilidad de Butacas

**GET** `/shows/{showId}/functions/{funcionId}/seats`

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

La API implementa **bloqueos optimistas** para asegurar que dos clientes no puedan reservar la misma localidad. Si una butaca ya fue reservada, la API devolverá un error de conflicto (`HTTP 409`).

## Pruebas

Para asegurar la calidad del código, se recomienda usar:

- **Pruebas Unitarias**: Jest (Node.js)
- **Pruebas de Integración**: Jest (Node.js)
- **Pruebas de Carga**: Artillery o Gatling

## Despliegue en la Nube

La API debe ser desplegada utilizando contenedores Docker y orquestación con Kubernetes, lo que permite escalar horizontalmente según la demanda.

## Instrucciones para Ejecutar

1. Clonar el repositorio:
   ```bash
   git clone [URL del repositorio]
   cd rapid-ticket
   ```

2. Instalar dependencias:
   ```bash
   npm install
   ```

3. Configurar variables de entorno:
 - Crear un archivo `.env` basado en el `.env.example`.

4. Iniciar la aplicación:
   ```bash
   npm start
   ```

5. Ejecutar pruebas:
   ```bash
   npm test
   ```

## Repositorio

El código fuente y la documentación adicional se pueden encontrar en el siguiente enlace:

[Repositorio de Rapid Ticket](https://github.com/tu_usuario/rapid-ticket)

---

Este archivo `README.md` proporciona una visión general completa de la API de Rapid Ticket, incluyendo sus características, tecnologías utilizadas y cómo ejecutar y probar el proyecto.