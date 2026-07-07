document.addEventListener("DOMContentLoaded", async () => {
    await loadDashboard();
});

async function loadDashboard() {
    try {
        const alumnos = await apiGet("/alumnos");
        const cursos = await apiGet("/cursos");
        const materias = await apiGet("/materias");
        const docentes = await apiGet("/docentes");
        const inscripciones = await apiGet("/inscripciones");

        document.getElementById("alumnosCount").textContent = alumnos.length;
        document.getElementById("cursosCount").textContent = cursos.length;
        document.getElementById("materiasCount").textContent = materias.length;
        document.getElementById("docentesCount").textContent = docentes.length;
        document.getElementById("inscripcionesCount").textContent = inscripciones.length;
    } catch (error) {
        console.error(error);
    }
}
