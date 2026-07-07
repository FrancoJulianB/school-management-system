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
        const asistencias = await apiGet("/asistencias");
        const calificaciones = await apiGet("/calificaciones");

        document.getElementById("alumnosCount").textContent = alumnos.length;
        document.getElementById("cursosCount").textContent = cursos.length;
        document.getElementById("materiasCount").textContent = materias.length;
        document.getElementById("docentesCount").textContent = docentes.length;
        document.getElementById("inscripcionesCount").textContent = inscripciones.length;

        renderLatestInscripciones(inscripciones);
        renderLatestAsistencias(asistencias);
        renderLatestCalificaciones(calificaciones);
    } catch (error) {
        console.error(error);
    }
}

function renderLatestInscripciones(inscripciones) {
    const table = document.getElementById("latestInscripciones");
    table.innerHTML = "";

    inscripciones.slice(-5).reverse().forEach(item => {
        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${item.alumnoNombre} ${item.alumnoApellido}</td>
            <td>${item.cursoNombre}</td>
            <td>${item.anio}</td>
            <td>${item.estado || "-"}</td>
        `;

        table.appendChild(row);
    });
}

function renderLatestAsistencias(asistencias) {
    const table = document.getElementById("latestAsistencias");
    table.innerHTML = "";

    asistencias.slice(-5).reverse().forEach(item => {
        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${item.alumnoNombre} ${item.alumnoApellido}</td>
            <td>${formatDate(item.fecha)}</td>
            <td>${item.presente ? "Sí" : "No"}</td>
        `;

        table.appendChild(row);
    });
}

function renderLatestCalificaciones(calificaciones) {
    const table = document.getElementById("latestCalificaciones");
    table.innerHTML = "";

    calificaciones.slice(-5).reverse().forEach(item => {
        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${item.alumnoNombre} ${item.alumnoApellido}</td>
            <td>${item.materia}</td>
            <td>${item.nota}</td>
        `;

        table.appendChild(row);
    });
}
