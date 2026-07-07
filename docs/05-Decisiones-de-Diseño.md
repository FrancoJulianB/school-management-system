# Decisiones de Diseño

## DD01 - Relación entre Curso, Materia y Docente

Se decidió incorporar la entidad intermedia CursoMateria para representar la asignación de una materia a un curso junto con el docente responsable.

Esta decisión permite reflejar con mayor precisión la realidad del establecimiento, ya que una misma materia puede dictarse en distintos cursos y por distintos docentes.

Además, esta entidad permite asociar calificaciones a una materia específica dentro de un curso determinado.

---

## DD02 - Historial académico mediante Inscripción

Se decidió modelar la inscripción como una entidad independiente entre Alumno y Curso.

Esta decisión permite conservar el historial académico de cada alumno a lo largo de los distintos ciclos lectivos, evitando sobrescribir información al momento de cambiar de curso.

Además, facilita la asociación de asistencias, calificaciones y boletines a un período académico específico.

---

## DD03 - Facturación mediante cabecera y detalle

Se decidió modelar la facturación utilizando una estructura de cabecera y detalle.

La entidad Factura representa la cabecera del comprobante e incluye alumno, tipo de factura, estado, fecha de emisión, vencimiento y total.

La entidad DetalleFactura representa los conceptos facturados, como matrícula anual o cuota mensual.

Esta decisión permite extender el sistema a futuro incorporando descuentos, recargos u otros conceptos sin modificar la estructura principal de facturación.

---

## DD04 - Inscripción condicional por estado de pago

Se decidió permitir que una inscripción pueda quedar en estado condicional cuando el alumno no posee la matrícula paga.

Esta decisión refleja un proceso administrativo real: el alumno puede iniciar el trámite de inscripción, pero su confirmación definitiva depende de regularizar el pago correspondiente.