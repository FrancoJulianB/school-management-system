let asistenciasCache = [];
let inscripcionesCache = [];

document.addEventListener("DOMContentLoaded", async () => {
    await loadReferenceData();
    await loadAsistencias();

    document.getElementById("asistenciaForm").addEventListener("submit", async (event) => {
        event.preventDefault();
        await saveAsistencia();
    });

    document.getElementById("searchInput").addEventListener("input", (event) => {
        renderAsistencias(filterAsistencias(event.target.value));
    });
});

async function loadReferenceData() {
    inscripcionesCache = await apiGet("/inscripciones");

    const select = document.getElementById("inscripcionId");
    select.innerHTML = `<option value="">Seleccione</option>`;

    inscripcionesCache.forEach(inscripcion => {
        const option = document.createElement("option");
        option.value = inscripcion.id;
        option.textContent = `${inscripcion.alumnoNombre} ${inscripcion.alumnoApellido} - ${inscripcion.cursoNombre} (${inscripcion.anio})`;
        select.appendChild(option);
    });
}

async function loadAsistencias() {
    try {
        asistenciasCache = await apiGet("/asistencias");
        renderAsistencias(asistenciasCache);
    } catch (error) {
        showMessage(error.message, true);
    }
}

function renderAsistencias(asistencias) {
    const table = document.getElementById("asistenciasTable");
    table.innerHTML = "";

    asistencias.forEach(asistencia => {
        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${asistencia.id}</td>
            <td>${asistencia.alumnoNombre} ${asistencia.alumnoApellido}</td>
            <td>${formatDate(asistencia.fecha)}</td>
            <td>${asistencia.presente ? "Sí" : "No"}</td>
            <td>${asistencia.observaciones || "-"}</td>
            <td>
                <div class="actions">
                    <button class="btn-secondary btn-icon" onclick="openEditAsistenciaModal(${asistencia.id})">✏️</button>
                    <button class="btn-danger btn-icon" onclick="deleteAsistencia(${asistencia.id})">🗑️</button>
                </div>
            </td>
        `;

        table.appendChild(row);
    });
}

function filterAsistencias(searchText) {
    const value = searchText.toLowerCase();

    return asistenciasCache.filter(asistencia =>
        `${asistencia.alumnoNombre} ${asistencia.alumnoApellido}`.toLowerCase().includes(value) ||
        asistencia.fecha.includes(value)
    );
}

function openCreateAsistenciaModal() {
    resetAsistenciaForm();
    document.getElementById("modalTitle").textContent = "Nueva Asistencia";
    document.getElementById("idDisplayGroup").style.display = "none";
    openModal("asistenciaModal");
}

async function openEditAsistenciaModal(id) {
    try {
        const asistencia = await apiGet(`/asistencias/${id}`);

        document.getElementById("modalTitle").textContent = "Editar Asistencia";
        document.getElementById("idDisplayGroup").style.display = "block";

        document.getElementById("asistenciaId").value = asistencia.id;
        document.getElementById("idDisplay").value = asistencia.id;
        document.getElementById("inscripcionId").value = asistencia.inscripcionId;
        document.getElementById("fecha").value = asistencia.fecha || "";
        document.getElementById("presente").value = String(asistencia.presente);
        document.getElementById("observaciones").value = asistencia.observaciones || "";

        openModal("asistenciaModal");
    } catch (error) {
        showMessage(error.message, true);
    }
}

function closeAsistenciaModal() {
    closeModalById("asistenciaModal");
    resetAsistenciaForm();
}

async function saveAsistencia() {
    const id = document.getElementById("asistenciaId").value;

    const asistencia = {
        inscripcionId: Number(document.getElementById("inscripcionId").value),
        fecha: document.getElementById("fecha").value,
        presente: document.getElementById("presente").value === "true",
        observaciones: document.getElementById("observaciones").value
    };

    try {
        if (id) {
            await apiPut(`/asistencias/${id}`, asistencia);
            showMessage("Asistencia actualizada correctamente");
        } else {
            await apiPost("/asistencias", asistencia);
            showMessage("Asistencia creada correctamente");
        }

        closeAsistenciaModal();
        await loadAsistencias();
    } catch (error) {
        showMessage(error.message, true);
    }
}

async function deleteAsistencia(id) {
    if (!confirm("¿Desea eliminar esta asistencia?")) return;

    try {
        await apiDelete(`/asistencias/${id}`);
        showMessage("Asistencia eliminada correctamente");
        await loadAsistencias();
    } catch (error) {
        showMessage(error.message, true);
    }
}

function resetAsistenciaForm() {
    document.getElementById("asistenciaForm").reset();
    document.getElementById("asistenciaId").value = "";
    document.getElementById("idDisplay").value = "";
}
