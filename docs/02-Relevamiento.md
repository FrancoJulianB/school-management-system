# Relevamiento

## Descripción

Se realizó un relevamiento del dominio del problema a partir del análisis del funcionamiento habitual de un establecimiento educativo y de los procesos administrativos necesarios para la gestión académica.

El objetivo del relevamiento fue identificar los principales actores, procesos y reglas del negocio que intervienen durante el ciclo de vida académico de un alumno.

---

## Actores

- Alumno
- Responsable Legal
- Docente
- Personal Administrativo
- Director
- Sistema

---

## Procesos identificados

- Registro de alumnos.
- Registro de responsables legales.
- Registro de docentes.
- Administración de materias.
- Administración de cursos.
- Administración de ciclos lectivos.
- Inscripción de alumnos.
- Registro de asistencia.
- Registro de calificaciones.
- Consulta de historial académico.
- Emisión de boletines.
- Administración de usuarios y permisos.
- Emisión de facturas de matrícula.
- Emisión de facturas por cuotas mensuales.
- Registro de pagos.
- Validación administrativa de inscripción según estado de matrícula.

---

## Información relevante

Durante el relevamiento se identificó la necesidad de conservar el historial académico de cada alumno.

Para ello, se decidió modelar la inscripción como una entidad independiente, permitiendo registrar la relación entre un alumno, un curso y un ciclo lectivo sin perder información histórica.

Asimismo, se identificó la necesidad de administrar usuarios con distintos niveles de permisos para garantizar la seguridad de la información.

También se identificó que el proceso de inscripción no depende únicamente de criterios académicos, sino también de condiciones administrativas. Por este motivo, se incorporó la facturación de matrícula y cuotas como parte del sistema, permitiendo que una inscripción pueda quedar activa o condicional según el estado de pago correspondiente.