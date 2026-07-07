let inscripcionesCache = [];
let alumnosCache = [];
let cursosCache = [];
let ciclosCache = [];

document.addEventListener("DOMContentLoaded", async () => {
    await loadReferenceData();
    await loadInscripciones();

    document.getElementById("inscripcionForm").addEventListener("submit", async (event) => {
        event.preventDefault();
        await saveInscripcion();
    });

    document.getElementById("searchInput").addEventListener("input", (event) => {
        renderInscripciones(filterInscripciones(event.target.value));
    });
});

async function loadReferenceData() {
    alumnosCache = await apiGet("/alumnos");
    cursosCache = await apiGet("/cursos");
    ciclosCache = await apiGet("/ciclos-lectivos");

    fillSelect("alumnoId", alumnosCache, alumno => `${alumno.nombre} ${alumno.apellido} - DNI ${alumno.dni}`);
    fillSelect("cursoId", cursosCache, curso => `${curso.nombre} - ${curso.nivel}`);
    fillSelect("cicloLectivoId", ciclosCache, ciclo => `${ciclo.anio} - ${ciclo.estado || ""}`);
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

async function loadInscripciones() {
    try {
        inscripcionesCache = await apiGet("/inscripciones");
        renderInscripciones(inscripcionesCache);
    } catch (error) {
        showMessage(error.message, true);
    }
}

function renderInscripciones(inscripciones) {
    const table = document.getElementById("inscripcionesTable");
    table.innerHTML = "";

    inscripciones.forEach(inscripcion => {
        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${inscripcion.id}</td>
            <td>${inscripcion.alumnoNombre} ${inscripcion.alumnoApellido}</td>
            <td>${inscripcion.cursoNombre}</td>
            <td>${inscripcion.anio}</td>
            <td>${formatDate(inscripcion.fechaInscripcion)}</td>
            <td>${inscripcion.estado || "-"}</td>
            <td>
                <div class="actions">
                    <button class="btn-secondary btn-icon" onclick="openEditInscripcionModal(${inscripcion.id})">✏️</button>
                    <button class="btn-danger btn-icon" onclick="deleteInscripcion(${inscripcion.id})">🗑️</button>
                </div>
            </td>
        `;

        table.appendChild(row);
    });
}

function filterInscripciones(searchText) {
    const value = searchText.toLowerCase();

    return inscripcionesCache.filter(inscripcion =>
        `${inscripcion.alumnoNombre} ${inscripcion.alumnoApellido}`.toLowerCase().includes(value) ||
        inscripcion.cursoNombre.toLowerCase().includes(value) ||
        String(inscripcion.anio).includes(value)
    );
}

function openCreateInscripcionModal() {
    resetInscripcionForm();
    document.getElementById("modalTitle").textContent = "Nueva Inscripción";
    document.getElementById("idDisplayGroup").style.display = "none";
    openModal("inscripcionModal");
}

async function openEditInscripcionModal(id) {
    try {
        const inscripcion = await apiGet(`/inscripciones/${id}`);

        document.getElementById("modalTitle").textContent = "Editar Inscripción";
        document.getElementById("idDisplayGroup").style.display = "block";

        document.getElementById("inscripcionId").value = inscripcion.id;
        document.getElementById("idDisplay").value = inscripcion.id;
        document.getElementById("alumnoId").value = inscripcion.alumnoId;
        document.getElementById("cursoId").value = inscripcion.cursoId;
        document.getElementById("cicloLectivoId").value = inscripcion.cicloLectivoId;
        document.getElementById("fechaInscripcion").value = inscripcion.fechaInscripcion || "";
        document.getElementById("estado").value = inscripcion.estado || "ACTIVA";

        openModal("inscripcionModal");
    } catch (error) {
        showMessage(error.message, true);
    }
}

function closeInscripcionModal() {
    closeModalById("inscripcionModal");
    resetInscripcionForm();
}

async function saveInscripcion() {
    const id = document.getElementById("inscripcionId").value;

    const inscripcion = {
        alumnoId: Number(document.getElementById("alumnoId").value),
        cursoId: Number(document.getElementById("cursoId").value),
        cicloLectivoId: Number(document.getElementById("cicloLectivoId").value),
        fechaInscripcion: document.getElementById("fechaInscripcion").value || null,
        estado: document.getElementById("estado").value
    };

    try {
        if (id) {
            await apiPut(`/inscripciones/${id}`, inscripcion);
            showMessage("Inscripción actualizada correctamente");
        } else {
            await apiPost("/inscripciones", inscripcion);
            showMessage("Inscripción creada correctamente");
        }

        closeInscripcionModal();
        await loadInscripciones();
        await loadReferenceData();
    } catch (error) {
        showMessage(error.message, true);
    }
}

async function deleteInscripcion(id) {
    if (!confirm("¿Desea eliminar esta inscripción?")) return;

    try {
        await apiDelete(`/inscripciones/${id}`);
        showMessage("Inscripción eliminada correctamente");
        await loadInscripciones();
    } catch (error) {
        showMessage(error.message, true);
    }
}

function resetInscripcionForm() {
    document.getElementById("inscripcionForm").reset();
    document.getElementById("inscripcionId").value = "";
    document.getElementById("idDisplay").value = "";
}
