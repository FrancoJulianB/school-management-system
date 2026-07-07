package com.franco.school_management_system.config;

import com.franco.school_management_system.entity.*;
import com.franco.school_management_system.entity.enums.*;
import com.franco.school_management_system.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final AlumnoRepository alumnoRepository;
    private final ResponsableLegalRepository responsableLegalRepository;
    private final AlumnoResponsableRepository alumnoResponsableRepository;
    private final CursoRepository cursoRepository;
    private final CicloLectivoRepository cicloLectivoRepository;
    private final DocenteRepository docenteRepository;
    private final MateriaRepository materiaRepository;
    private final CursoMateriaRepository cursoMateriaRepository;
    private final InscripcionRepository inscripcionRepository;
    private final AsistenciaRepository asistenciaRepository;
    private final CalificacionRepository calificacionRepository;
    private final FacturaRepository facturaRepository;
    private final PagoRepository pagoRepository;

    @Override
    public void run(String... args) {
        if (alumnoRepository.count() > 0) {
            return;
        }

        CicloLectivo ciclo2026 = crearCicloLectivo(2026);

        Alumno alumno1 = crearAlumno("Juan", "Pérez", "40111222", "juan.perez@test.com", LocalDate.of(2010, 5, 12));
        Alumno alumno2 = crearAlumno("Sofía", "Gómez", "42111333", "sofia.gomez@test.com", LocalDate.of(2011, 8, 3));
        Alumno alumno3 = crearAlumno("Martín", "López", "43111444", "martin.lopez@test.com", LocalDate.of(2009, 11, 22));
        Alumno alumno4 = crearAlumno("Valentina", "Fernández", "44111555", "valentina.fernandez@test.com", LocalDate.of(2012, 2, 14));

        ResponsableLegal resp1 = crearResponsable("María", "Pérez", "30111222", "maria.perez@test.com");
        ResponsableLegal resp2 = crearResponsable("Carlos", "Gómez", "29111333", "carlos.gomez@test.com");
        ResponsableLegal resp3 = crearResponsable("Laura", "López", "28111444", "laura.lopez@test.com");

        vincularResponsable(alumno1, resp1, Parentesco.MADRE);
        vincularResponsable(alumno2, resp2, Parentesco.PADRE);
        vincularResponsable(alumno3, resp3, Parentesco.MADRE);
        vincularResponsable(alumno4, resp1, Parentesco.TUTOR);

        Curso curso1A = crearCurso("1° A", NivelEducativo.SECUNDARIO, 1, "A", 30);
        Curso curso2A = crearCurso("2° A", NivelEducativo.SECUNDARIO, 2, "A", 28);
        Curso curso6B = crearCurso("6° B", NivelEducativo.PRIMARIO, 6, "B", 25);

        Docente docente1 = crearDocente("Carlos", "Rodríguez", "25111222", "carlos.rodriguez@test.com");
        Docente docente2 = crearDocente("Ana", "Martínez", "26111333", "ana.martinez@test.com");
        Docente docente3 = crearDocente("Diego", "Suárez", "27111444", "diego.suarez@test.com");

        Materia matematica = crearMateria("Matemática", "Materia de matemática del ciclo básico.");
        Materia lengua = crearMateria("Lengua", "Prácticas del lenguaje y literatura.");
        Materia historia = crearMateria("Historia", "Historia argentina y mundial.");
        Materia ingles = crearMateria("Inglés", "Idioma extranjero.");

        CursoMateria cm1 = asignarMateria(curso1A, matematica, docente1);
        CursoMateria cm2 = asignarMateria(curso1A, lengua, docente2);
        CursoMateria cm3 = asignarMateria(curso2A, matematica, docente1);
        CursoMateria cm4 = asignarMateria(curso2A, historia, docente3);
        CursoMateria cm5 = asignarMateria(curso6B, ingles, docente2);

        Inscripcion insc1 = crearInscripcion(alumno1, curso1A, ciclo2026, EstadoInscripcion.ACTIVA);
        Inscripcion insc2 = crearInscripcion(alumno2, curso1A, ciclo2026, EstadoInscripcion.CONDICIONAL);
        Inscripcion insc3 = crearInscripcion(alumno3, curso2A, ciclo2026, EstadoInscripcion.ACTIVA);
        Inscripcion insc4 = crearInscripcion(alumno4, curso6B, ciclo2026, EstadoInscripcion.ACTIVA);

        crearAsistencia(insc1, LocalDate.now().minusDays(2), true, "Presente.");
        crearAsistencia(insc1, LocalDate.now().minusDays(1), true, "Presente.");
        crearAsistencia(insc2, LocalDate.now().minusDays(2), false, "Ausente con aviso.");
        crearAsistencia(insc3, LocalDate.now().minusDays(1), true, "Presente.");
        crearAsistencia(insc4, LocalDate.now().minusDays(1), true, "Presente.");

        crearCalificacion(insc1, cm1, "8.50", "Parcial", "Buen desempeño.");
        crearCalificacion(insc1, cm2, "7.00", "Trabajo práctico", "Entrega correcta.");
        crearCalificacion(insc3, cm3, "9.00", "Parcial", "Muy buen desempeño.");
        crearCalificacion(insc3, cm4, "6.50", "Oral", "Debe reforzar algunos temas.");
        crearCalificacion(insc4, cm5, "8.00", "Evaluación escrita", "Buen nivel.");

        Factura f1 = crearFactura(alumno1, TipoFactura.MATRICULA, EstadoFactura.PAGADA,
                LocalDate.now().minusDays(30), LocalDate.now().minusDays(15),
                "Matrícula anual 2026", "350.00");

        crearPago(f1, MedioPago.TARJETA);

        crearFactura(alumno2, TipoFactura.MATRICULA, EstadoFactura.PENDIENTE,
                LocalDate.now().minusDays(5), LocalDate.now().plusDays(10),
                "Matrícula anual 2026", "350.00");

        Factura f3 = crearFactura(alumno3, TipoFactura.MATRICULA, EstadoFactura.PAGADA,
                LocalDate.now().minusDays(40), LocalDate.now().minusDays(25),
                "Matrícula anual 2026", "350.00");

        crearPago(f3, MedioPago.TRANSFERENCIA);

        Factura f4 = crearFactura(alumno3, TipoFactura.CUOTA, EstadoFactura.PAGADA,
                LocalDate.of(2026, 3, 1), LocalDate.of(2026, 3, 10),
                "Cuota marzo 2026", "180.00");

        crearPago(f4, MedioPago.TRANSFERENCIA);

        crearFactura(alumno3, TipoFactura.CUOTA, EstadoFactura.PENDIENTE,
                LocalDate.of(2026, 4, 1), LocalDate.of(2026, 4, 10),
                "Cuota abril 2026", "180.00");

        crearFactura(alumno4, TipoFactura.CUOTA, EstadoFactura.VENCIDA,
                LocalDate.of(2026, 3, 1), LocalDate.of(2026, 3, 10),
                "Cuota marzo 2026", "180.00");

        crearFactura(alumno4, TipoFactura.CUOTA, EstadoFactura.VENCIDA,
                LocalDate.of(2026, 4, 1), LocalDate.of(2026, 4, 10),
                "Cuota abril 2026", "180.00");

        System.out.println("Datos iniciales cargados correctamente.");
    }

    private Alumno crearAlumno(String nombre, String apellido, String dni, String email, LocalDate fechaNacimiento) {
        Alumno alumno = new Alumno();
        alumno.setNombre(nombre);
        alumno.setApellido(apellido);
        alumno.setDni(dni);
        alumno.setEmail(email);
        alumno.setTelefono("1122334455");
        alumno.setFechaNacimiento(fechaNacimiento);
        alumno.setActivo(true);
        return alumnoRepository.save(alumno);
    }

    private ResponsableLegal crearResponsable(String nombre, String apellido, String dni, String email) {
        ResponsableLegal responsable = new ResponsableLegal();
        responsable.setNombre(nombre);
        responsable.setApellido(apellido);
        responsable.setDni(dni);
        responsable.setEmail(email);
        responsable.setTelefono("1199887766");
        return responsableLegalRepository.save(responsable);
    }

    private void vincularResponsable(Alumno alumno, ResponsableLegal responsable, Parentesco parentesco) {
        AlumnoResponsable relacion = new AlumnoResponsable();
        relacion.setAlumno(alumno);
        relacion.setResponsableLegal(responsable);
        relacion.setParentesco(parentesco);
        alumnoResponsableRepository.save(relacion);
    }

    private Curso crearCurso(String nombre, NivelEducativo nivel, Integer gradoAnio, String division, Integer capacidad) {
        Curso curso = new Curso();
        curso.setNombre(nombre);
        curso.setNivel(nivel);
        curso.setGradoAnio(gradoAnio);
        curso.setDivision(division);
        curso.setCapacidad(capacidad);
        return cursoRepository.save(curso);
    }

    private CicloLectivo crearCicloLectivo(Integer anio) {
        CicloLectivo ciclo = new CicloLectivo();
        ciclo.setAnio(anio);
        ciclo.setFechaInicio(LocalDate.of(anio, 3, 1));
        ciclo.setFechaFin(LocalDate.of(anio, 12, 15));
        ciclo.setEstado(EstadoCicloLectivo.ACTIVO);
        return cicloLectivoRepository.save(ciclo);
    }

    private Docente crearDocente(String nombre, String apellido, String dni, String email) {
        Docente docente = new Docente();
        docente.setNombre(nombre);
        docente.setApellido(apellido);
        docente.setDni(dni);
        docente.setTelefono("1133445566");
        docente.setEmail(email);
        return docenteRepository.save(docente);
    }

    private Materia crearMateria(String nombre, String descripcion) {
        Materia materia = new Materia();
        materia.setNombre(nombre);
        materia.setDescripcion(descripcion);
        return materiaRepository.save(materia);
    }

    private CursoMateria asignarMateria(Curso curso, Materia materia, Docente docente) {
        CursoMateria cursoMateria = new CursoMateria();
        cursoMateria.setCurso(curso);
        cursoMateria.setMateria(materia);
        cursoMateria.setDocente(docente);
        return cursoMateriaRepository.save(cursoMateria);
    }

    private Inscripcion crearInscripcion(Alumno alumno, Curso curso, CicloLectivo ciclo, EstadoInscripcion estado) {
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setAlumno(alumno);
        inscripcion.setCurso(curso);
        inscripcion.setCicloLectivo(ciclo);
        inscripcion.setFechaInscripcion(LocalDate.now().minusDays(20));
        inscripcion.setEstado(estado);
        return inscripcionRepository.save(inscripcion);
    }

    private void crearAsistencia(Inscripcion inscripcion, LocalDate fecha, Boolean presente, String observaciones) {
        Asistencia asistencia = new Asistencia();
        asistencia.setInscripcion(inscripcion);
        asistencia.setFecha(fecha);
        asistencia.setPresente(presente);
        asistencia.setObservaciones(observaciones);
        asistenciaRepository.save(asistencia);
    }

    private void crearCalificacion(Inscripcion inscripcion, CursoMateria cursoMateria, String nota, String tipo, String observaciones) {
        Calificacion calificacion = new Calificacion();
        calificacion.setInscripcion(inscripcion);
        calificacion.setCursoMateria(cursoMateria);
        calificacion.setNota(new BigDecimal(nota));
        calificacion.setTipoEvaluacion(tipo);
        calificacion.setFecha(LocalDate.now().minusDays(3));
        calificacion.setObservaciones(observaciones);
        calificacionRepository.save(calificacion);
    }

    private Factura crearFactura(
            Alumno alumno,
            TipoFactura tipoFactura,
            EstadoFactura estado,
            LocalDate fechaEmision,
            LocalDate fechaVencimiento,
            String concepto,
            String monto
    ) {
        Factura factura = new Factura();
        factura.setAlumno(alumno);
        factura.setTipoFactura(tipoFactura);
        factura.setEstado(estado);
        factura.setFechaEmision(fechaEmision);
        factura.setFechaVencimiento(fechaVencimiento);
        factura.setTotal(new BigDecimal(monto));

        DetalleFactura detalle = new DetalleFactura();
        detalle.setFactura(factura);
        detalle.setConcepto(concepto);
        detalle.setMonto(new BigDecimal(monto));

        factura.getDetalles().add(detalle);

        return facturaRepository.save(factura);
    }

    private void crearPago(Factura factura, MedioPago medioPago) {
        Pago pago = new Pago();
        pago.setFactura(factura);
        pago.setFechaPago(LocalDateTime.now().minusDays(5));
        pago.setMonto(factura.getTotal());
        pago.setMedioPago(medioPago);
        pagoRepository.save(pago);
    }
}