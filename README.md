# Proyecto Final: Arquitectura de Microservicios para la Gestión de Publicaciones

Aquí se ha implementado una plataforma completa para la gestión de publicaciones académicas usando una arquitectura de microservicios.

## 📝 Descripción General

El objetivo de este proyecto es construir una **plataforma distribuida** para gestionar el **ciclo de vida completo de publicaciones** como artículos y libros. Un autor puede subir un borrador, este pasa por un proceso de revisión, un editor lo aprueba y, finalmente, se publica en un catálogo público.

La clave es que no es una aplicación monolítica (todo en un solo bloque), sino que está construida con **microservicios desacoplados**. Cada parte del sistema (autenticación, publicaciones, notificaciones, etc.) es una pequeña aplicación independiente que se comunica con las demás a través de la red.

---

## 🏛️ Arquitectura del Sistema

El sistema sigue un patrón de microservicios estándar con varios componentes clave que trabajan en conjunto:

* **API Gateway**: Es la única puerta de entrada para todas las solicitudes externas. Se encarga de la seguridad y de redirigir cada petición al microservicio correcto.
* **Service Discovery (Eureka)**: Actúa como un directorio telefónico. Cada microservicio se registra aquí al arrancar, permitiendo que los demás se encuentren dinámicamente.
* **Comunicación Síncrona (REST)**: Para peticiones que necesitan una respuesta inmediata.
* **Comunicación Asíncrona (RabbitMQ)**: Para notificar eventos importantes entre servicios sin que tengan que esperarse entre sí. Por ejemplo, cuando se aprueba una publicación, se emite un evento para que el servicio de catálogo y el de notificaciones actúen.
* **Base de Datos Distribuida (CockroachDB)**: Todos los servicios persisten sus datos en un clúster de CockroachDB, pero cada uno tiene su propio esquema lógico para mantener la independencia.


---

## 🚀 Microservicios Principales

* **Auth Service**: Gestiona usuarios, roles y la emisión/validación de tokens de seguridad JWT con OAuth2. Publica eventos de `user.registered` y `user.login`.
* **Publicaciones Service**: El corazón del sistema. Gestiona la creación, edición, versionado y los estados del ciclo de vida de las publicaciones (`BORRADOR`, `EN_REVISION`, `APROBADO`, etc.). Publica eventos de dominio como `publication.submitted` o `publication.approved`.
* **Catalogo Service**: Se suscribe a los eventos de publicaciones aprobadas, las indexa y las expone en un catálogo público con endpoints de consulta.
* **Notificaciones Service**: Escucha eventos de toda la plataforma (nuevos usuarios, publicaciones aprobadas, cambios solicitados) y envía alertas por diferentes canales.
* **Gateway Service**: El punto de entrada que enruta las peticiones y aplica filtros de seguridad, como la validación de JWT.
* **Eureka Service**: El registro de servicios que permite el descubrimiento dinámico.

---

## 💻 Tecnologías Utilizadas

* **Backend**: Java 21, Spring Boot 3, Spring Cloud
* **Base de Datos**: CockroachDB
* **Mensajería**: RabbitMQ
* **Contenerización**: Docker y Docker Compose
* **Seguridad**: Spring Security, OAuth2, JWT
* **Resiliencia**: Resilience4j (Circuit Breaker)

---

## 🛠️ Instrucciones de Despliegue Local

Para levantar todo el entorno en tu máquina local, solo necesitas tener instalados **Docker** y **Docker Compose**.

1.  **Clona este repositorio:**
    ```bash
    git clone <URL_DEL_REPOSITORIO>
    cd <NOMBRE_DEL_REPOSITORIO>
    ```

2.  **Construye y levanta todos los servicios:**
    Desde la raíz del proyecto (donde se encuentra el archivo `docker-compose.yml`), ejecuta el siguiente comando:
    ```bash
    docker compose up -d --build
    ```
    Este comando construirá las imágenes de Docker para cada microservicio y los iniciará en segundo plano. Puede tardar unos minutos la primera vez mientras se descargan las dependencias.

3.  **¡Listo!** El sistema estará accesible a través del Gateway en `http://localhost`.
    * **Eureka Dashboard**: `http://localhost:8761`
    * **RabbitMQ Management**: `http://localhost:15672` (user: `admin`, pass: `admin`)
    * **CockroachDB UI**: `http://localhost:8080`

---

