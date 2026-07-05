# Requerimientos

## Requerimientos Funcionales

### Gestión de alumnos

- RF01. El sistema deberá permitir registrar alumnos.
- RF02. El sistema deberá permitir modificar la información de un alumno.
- RF03. El sistema deberá permitir consultar la información de un alumno.
- RF04. El sistema deberá permitir registrar uno o más responsables legales para cada alumno.

### Gestión académica

- RF05. El sistema deberá permitir registrar ciclos lectivos.
- RF06. El sistema deberá permitir administrar cursos.
- RF07. El sistema deberá permitir asignar alumnos a un curso mediante una inscripción correspondiente a un ciclo lectivo.
- RF08. El sistema deberá permitir administrar materias.
- RF09. El sistema deberá permitir asignar docentes a las materias y cursos.

### Asistencia y calificaciones

- RF10. El sistema deberá permitir registrar la asistencia diaria de los alumnos.
- RF11. El sistema deberá permitir registrar calificaciones.
- RF12. El sistema deberá permitir consultar el historial académico de un alumno.
- RF13. El sistema deberá generar boletines por ciclo lectivo.

### Administración

- RF14. El sistema deberá administrar usuarios.
- RF15. El sistema deberá administrar roles y permisos.
- RF16. El sistema deberá generar reportes académicos básicos.

---

## Requerimientos No Funcionales

- RNF01. El sistema deberá requerir autenticación para acceder.
- RNF02. Cada usuario visualizará únicamente la información permitida por su rol.
- RNF03. La información deberá almacenarse en una base de datos relacional.
- RNF04. La aplicación deberá ejecutarse mediante contenedores Docker.
- RNF05. El sistema deberá mantener la integridad de la información almacenada.
- RNF06. La interfaz deberá ser intuitiva y de fácil utilización.
- RNF07. El sistema deberá registrar la fecha de creación y modificación de los registros principales.
- RNF08. El sistema deberá permitir su ejecución dentro de una red local.