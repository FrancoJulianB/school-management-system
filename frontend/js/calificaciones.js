let calificacionesCache = [];
let inscripcionesCache = [];
let cursosMateriasCache = [];

document.addEventListener("DOMContentLoaded", async () => {
    await loadReferenceData();
    await loadCalificaciones();

    document.getElementById("calificacionForm").addEventListener("submit", async (event) => {
        event.preventDefault();
        await saveCalificacion();
    });

    document.getElementById("searchInput").addEventListener("input", (event) => {
        renderCalificaciones(filterCalificaciones(event.target.value));
    });
});

async function loadReferenceData() {
    inscripcionesCache = await apiGet("/inscripciones");
    cursosMateriasCache = await apiGet("/cursos-materias");

    fillSelect(
        "inscripcionId",
        inscripcionesCache,
        inscripcion => `${inscripcion.alumnoNombre} ${inscripcion.alumnoApellido} - ${inscripcion.cursoNombre} (${inscripcion.anio})`
    );

    fillSelect(
        "cursoMateriaId",
        cursosMateriasCache,
        cursoMateria => `${cursoMateria.cursoNombre} - ${cursoMateria.materiaNombre} (${cursoMateria.docenteNombre} ${cursoMateria.docenteApellido})`
    );
}

function fillSelect(elementId, items, labelResolver) {
    const select = document.getElementById(elementId);
    select.innerHTML = `<option value="">Seleccione</option>`;

    items.forEach(item => {
        const option = document.createElement("option");
        option.value = item.id;
        option.textContent = labelResolver(item);
        select.appendChild(option);
    });
}

async function loadCalificaciones() {
    try {
        calificacionesCache = await apiGet("/calificaciones");
        renderCalificaciones(calificacionesCache);
    } catch (error) {
        showMessage(error.message, true);
    }
}

function renderCalificaciones(calificaciones) {
    const table = document.getElementById("calificacionesTable");
    table.innerHTML = "";

    calificaciones.forEach(calificacion => {
        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${calificacion.id}</td>
            <td>${calificacion.alumnoNombre} ${calificacion.alumnoApellido}</td>
            <td>${calificacion.materia}</td>
            <td>${calificacion.docente}</td>
            <td>${calificacion.nota}</td>
            <td>${calificacion.tipoEvaluacion || "-"}</td>
            <td>${formatDate(calificacion.fecha)}</td>
            <td>
                <div class="actions">
                    <button class="btn-secondary btn-icon" onclick="openEditCalificacionModal(${calificacion.id})">✏️</button>
                    <button class="btn-danger btn-icon" onclick="deleteCalificacion(${calificacion.id})">🗑️</button>
                </div>
            </td>
        `;

        table.appendChild(row);
    });
}

function filterCalificaciones(searchText) {
    const value = searchText.toLowerCase();

    return calificacionesCache.filter(calificacion =>
        `${calificacion.alumnoNombre} ${calificacion.alumnoApellido}`.toLowerCase().includes(value) ||
        calificacion.materia.toLowerCase().includes(value) ||
        calificacion.docente.toLowerCase().includes(value)
    );
}

function openCreateCalificacionModal() {
    resetCalificacionForm();
    document.getElementById("modalTitle").textContent = "Nueva Calificación";
    document.getElementById("idDisplayGroup").style.display = "none";
    openModal("calificacionModal");
}

async function openEditCalificacionModal(id) {
    try {
        const calificacion = await apiGet(`/calificaciones/${id}`);

        document.getElementById("modalTitle").textContent = "Editar Calificación";
        document.getElementById("idDisplayGroup").style.display = "block";

        document.getElementById("calificacionId").value = calificacion.id;
        document.getElementById("idDisplay").value = calificacion.id;
        document.getElementById("inscripcionId").value = calificacion.inscripcionId;
        document.getElementById("cursoMateriaId").value = calificacion.cursoMateriaId;
        document.getElementById("nota").value = calificacion.nota;
        document.getElementById("tipoEvaluacion").value = calificacion.tipoEvaluacion || "";
        document.getElementById("fecha").value = calificacion.fecha || "";
        document.getElementById("observaciones").value = calificacion.observaciones || "";

        openModal("calificacionModal");
    } catch (error) {
        showMessage(error.message, true);
    }
}

function closeCalificacionModal() {
    closeModalById("calificacionModal");
    resetCalificacionForm();
}

async function saveCalificacion() {
    const id = document.getElementById("calificacionId").value;

    const calificacion = {
        inscripcionId: Number(document.getElementById("inscripcionId").value),
        cursoMateriaId: Number(document.getElementById("cursoMateriaId").value),
        nota: Number(document.getElementById("nota").value),
        tipoEvaluacion: document.getElementById("tipoEvaluacion").value,
        fecha: document.getElementById("fecha").value || null,
        observaciones: document.getElementById("observaciones").value
    };

    try {
        if (id) {
            await apiPut(`/calificaciones/${id}`, calificacion);
            showMessage("Calificación actualizada correctamente");
        } else {
            await apiPost("/calificaciones", calificacion);
            showMessage("Calificación creada correctamente");
        }

        closeCalificacionModal();
        await loadCalificaciones();
    } catch (error) {
        showMessage(error.message, true);
    }
}

async function deleteCalificacion(id) {
    if (!confirm("¿Desea eliminar esta calificación?")) return;

    try {
        await apiDelete(`/calificaciones/${id}`);
        showMessage("Calificación eliminada correctamente");
        await loadCalificaciones();
    } catch (error) {
        showMessage(error.message, true);
    }
}

function resetCalificacionForm() {
    document.getElementById("calificacionForm").reset();
    document.getElementById("calificacionId").value = "";
    document.getElementById("idDisplay").value = "";
}
