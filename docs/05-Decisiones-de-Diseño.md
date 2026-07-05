# Decisiones de Diseño

## DD01 - Relación entre Materias y Docentes

Para este proyecto se asumió que cada materia posee un único docente responsable.

Si bien un sistema de mayor complejidad podría incorporar una entidad intermedia que relacione Curso, Materia, Docente y Ciclo Lectivo, se decidió no implementarla debido a que excede el alcance definido para esta primera versión del sistema.

Esta decisión permite simplificar el modelo de datos sin afectar los requerimientos funcionales establecidos.

---

## DD02 - Historial académico mediante Inscripción

Se decidió modelar la inscripción como una entidad independiente entre Alumno y Curso.

Esta decisión permite conservar el historial académico de cada alumno a lo largo de los distintos ciclos lectivos, evitando sobrescribir información al momento de cambiar de curso.

Además, facilita la asociación de asistencias, calificaciones y boletines a un período académico específico.