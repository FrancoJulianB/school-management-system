package com.franco.school_management_system.config;

import com.franco.school_management_system.entity.*;
import com.franco.school_management_system.entity.enums.EstadoCicloLectivo;
import com.franco.school_management_system.entity.enums.EstadoInscripcion;
import com.franco.school_management_system.entity.enums.NivelEducativo;
import com.franco.school_management_system.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final AlumnoRepository alumnoRepository;
    private final ResponsableLegalRepository responsableLegalRepository;
    private final CursoRepository cursoRepository;
    private final CicloLectivoRepository cicloLectivoRepository;
    private final DocenteRepository docenteRepository;
    private final MateriaRepository materiaRepository;
    private final CursoMateriaRepository cursoMateriaRepository;
    private final InscripcionRepository inscripcionRepository;
    private final AsistenciaRepository asistenciaRepository;
    private final CalificacionRepository calificacionRepository;

    @Override
    public void run(String... args) {
        System.out.println(">>>>>>>> DATA SEEDER EJECUTADO <<<<<<<<");

        if (alumnoRepository.count() > 0) {
            return;
        }

        Alumno alumno = new Alumno();
        alumno.setNombre("Juan");
        alumno.setApellido("Pérez");
        alumno.setDni("40111222");
        alumno.setFechaNacimiento(LocalDate.of(2010, 5, 12));
        alumno.setTelefono("1122334455");
        alumno.setEmail("juan.perez@test.com");
        alumno.setActivo(true);
        alumnoRepository.save(alumno);

        ResponsableLegal responsable = new ResponsableLegal();
        responsable.setNombre("María");
        responsable.setApellido("Gómez");
        responsable.setDni("30111222");
        responsable.setTelefono("1199887766");
        responsable.setEmail("maria.gomez@test.com");
        responsableLegalRepository.save(responsable);

        Curso curso = new Curso();
        curso.setNombre("1° A");
        curso.setNivel(NivelEducativo.SECUNDARIO);
        curso.setGradoAnio(1);
        curso.setDivision("A");
        curso.setCapacidad(30);
        cursoRepository.save(curso);

        CicloLectivo ciclo = new CicloLectivo();
        ciclo.setAnio(2026);
        ciclo.setFechaInicio(LocalDate.of(2026, 3, 1));
        ciclo.setFechaFin(LocalDate.of(2026, 12, 15));
        ciclo.setEstado(EstadoCicloLectivo.ACTIVO);
        cicloLectivoRepository.save(ciclo);

        Docente docente = new Docente();
        docente.setNombre("Carlos");
        docente.setApellido("Rodríguez");
        docente.setDni("25111222");
        docente.setTelefono("1133445566");
        docente.setEmail("carlos.rodriguez@test.com");
        docenteRepository.save(docente);

        Materia materia = new Materia();
        materia.setNombre("Matemática");
        materia.setDescripcion("Materia de matemática del ciclo básico.");
        materiaRepository.save(materia);

        CursoMateria cursoMateria = new CursoMateria();
        cursoMateria.setCurso(curso);
        cursoMateria.setMateria(materia);
        cursoMateria.setDocente(docente);
        cursoMateriaRepository.save(cursoMateria);

        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setAlumno(alumno);
        inscripcion.setCurso(curso);
        inscripcion.setCicloLectivo(ciclo);
        inscripcion.setFechaInscripcion(LocalDate.now());
        inscripcion.setEstado(EstadoInscripcion.ACTIVA);
        inscripcionRepository.save(inscripcion);

        Asistencia asistencia = new Asistencia();
        asistencia.setInscripcion(inscripcion);
        asistencia.setFecha(LocalDate.now());
        asistencia.setPresente(true);
        asistencia.setObservaciones("Presente en clase.");
        asistenciaRepository.save(asistencia);

        Calificacion calificacion = new Calificacion();
        calificacion.setInscripcion(inscripcion);
        calificacion.setCursoMateria(cursoMateria);
        calificacion.setNota(new BigDecimal("8.50"));
        calificacion.setTipoEvaluacion("Parcial");
        calificacion.setFecha(LocalDate.now());
        calificacion.setObservaciones("Buen desempeño.");
        calificacionRepository.save(calificacion);
    }
}
