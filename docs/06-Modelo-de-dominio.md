# Modelo de Dominio

## Introducción

El modelo de dominio representa las principales entidades del negocio identificadas durante el relevamiento, junto con sus responsabilidades dentro del sistema.

Su objetivo es describir conceptualmente los elementos que intervienen en la gestión de un establecimiento educativo, independientemente de su implementación técnica.

---

## Entidades del Dominio

### Alumno

Representa a un estudiante registrado en el establecimiento educativo.

**Responsabilidades**

- Almacenar la información personal del alumno.
- Mantener la relación con uno o más responsables legales.
- Mantener el historial de inscripciones.

---

### Responsable Legal

Representa a la persona responsable de uno o más alumnos.

**Responsabilidades**

- Almacenar la información personal del responsable.
- Mantener la relación con los alumnos a su cargo.

---

### Inscripción

Representa la inscripción de un alumno a un curso durante un ciclo lectivo.

Esta entidad permite conservar el historial académico del alumno a lo largo de los distintos años.

**Responsabilidades**

- Asociar un alumno con un curso.
- Asociar la inscripción a un ciclo lectivo.
- Permitir el registro de asistencias.
- Permitir el registro de calificaciones.
- Mantener el historial académico correspondiente al ciclo lectivo.
- Mantener el estado de inscripción según la situación académica y administrativa del alumno.
- Quedar condicional cuando la matrícula no se encuentre paga.

---

### Curso

Representa un curso perteneciente al establecimiento educativo.

Ejemplo:

- 1° A
- 3° B
- 5° A

**Responsabilidades**

- Agrupar alumnos.
- Asociar materias.
- Gestionar la disponibilidad de vacantes.

---

### Ciclo Lectivo

Representa un período académico.

Ejemplos:

- 2026
- 2027

**Responsabilidades**

- Organizar las inscripciones.
- Delimitar el período académico de asistencias y calificaciones.

---

### Materia

Representa una asignatura dictada por el establecimiento.

Ejemplos:

- Matemática
- Lengua
- Historia

**Responsabilidades**

- Identificar una asignatura.
- Asociarse a uno o varios cursos.
- Asociarse a un docente responsable.

---

### Docente

Representa a un profesor del establecimiento.

**Responsabilidades**

- Dictar una o varias materias.
- Registrar asistencias.
- Registrar calificaciones.

---

### Asistencia

Representa el registro de asistencia correspondiente a un alumno en una fecha determinada.

**Responsabilidades**

- Registrar la asistencia diaria.
- Mantener el historial de asistencias.

---

### Calificación

Representa una nota obtenida por un alumno en una materia.

**Responsabilidades**

- Registrar las evaluaciones.
- Mantener el historial académico.

---

### Factura

Representa un comprobante administrativo emitido al alumno por conceptos como matrícula anual o cuotas mensuales.

**Responsabilidades**

- Asociarse a un alumno.
- Registrar fecha de emisión y vencimiento.
- Mantener el estado administrativo de la deuda.
- Agrupar uno o más detalles de facturación.
- Calcular el total facturado.

---

### DetalleFactura

Representa cada concepto incluido dentro de una factura.

**Responsabilidades**

- Describir el concepto facturado.
- Registrar el monto correspondiente.
- Permitir que una factura tenga uno o más conceptos.

---

### Pago

Representa el registro de pago de una factura.

**Responsabilidades**

- Asociarse a una factura.
- Registrar el medio de pago.
- Registrar la fecha y monto abonado.
- Permitir actualizar el estado de la factura.
---

## Observaciones

Se decidió modelar la entidad **Inscripción** como intermediaria entre **Alumno** y **Curso** con el objetivo de conservar el historial académico correspondiente a cada ciclo lectivo.
