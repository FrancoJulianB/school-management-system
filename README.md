# School Management System

Proyecto desarrollado para la materia **Ingeniería de Software**.

Sistema de gestión escolar para administrar alumnos, responsables legales, docentes, cursos, materias, ciclos lectivos, inscripciones, asistencias y calificaciones.

---

## Estado del proyecto

🚧 En desarrollo.

---

## Tecnologías

### Backend

- Java 21
- Spring Boot 4.1.0
- Spring Data JPA
- Bean Validation
- Lombok
- MySQL
- Swagger / OpenAPI

### Frontend

- Pendiente de implementación

### Infraestructura

- Docker
- Docker Compose

---

## Estructura del repositorio

```text
.
├── backend/
├── frontend/
├── database/
├── docs/
├── src/
├── compose.yaml
├── pom.xml
└── README.md
```

---

## Documentación

- [O.L.A.](docs/01-OLA.md)
- [Relevamiento](docs/02-Relevamiento.md)
- [Requerimientos](docs/03-Requerimientos.md)
- [Reglas de Negocio](docs/04-Reglas-de-Negocio.md)
- [Decisiones de Diseño](docs/05-Decisiones-de-Diseño.md)
- [Modelo de Dominio](docs/06-Modelo-de-Dominio.md)
- [DER](docs/07-DER.md)

---

## Cómo ejecutar el proyecto

### 1. Levantar MySQL

```bash
docker compose up -d
```

La base queda disponible en:

```text
localhost:3307
```

Credenciales:

```text
database: school_management
user: root
password: root
```

---

### 2. Ejecutar backend

Desde IntelliJ, correr la clase principal:

```text
SchoolManagementSystemApplication
```

O desde terminal:

```bash
./mvnw spring-boot:run
```

En Windows:

```bash
mvnw.cmd spring-boot:run
```

---

## Swagger

Con la aplicación levantada, acceder a:

```text
http://localhost:8080/swagger-ui.html
```

o:

```text
http://localhost:8080/swagger-ui/index.html
```

---

## Endpoints principales

```text
GET    /api/alumnos
POST   /api/alumnos
GET    /api/alumnos/{id}
PUT    /api/alumnos/{id}
DELETE /api/alumnos/{id}

GET    /api/responsables-legales
GET    /api/docentes
GET    /api/cursos
GET    /api/materias
GET    /api/ciclos-lectivos
GET    /api/inscripciones
GET    /api/cursos-materias
GET    /api/asistencias
GET    /api/calificaciones
```

---

## Datos de prueba

El proyecto incluye un `DataSeeder` que carga información inicial para facilitar la demostración del sistema.

Datos cargados:

- Alumno
- Responsable legal
- Curso
- Ciclo lectivo
- Docente
- Materia
- Asignación curso-materia
- Inscripción
- Asistencia
- Calificación

---

## Próximos pasos

- Implementar frontend.
- Agregar documentación de arquitectura.
- Mejorar validaciones de negocio.
- Incorporar autenticación y autorización.
- Preparar presentación final.