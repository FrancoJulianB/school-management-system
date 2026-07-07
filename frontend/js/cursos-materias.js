let cursosMateriasCache = [];
let cursosCache = [];
let materiasCache = [];
let docentesCache = [];

document.addEventListener("DOMContentLoaded", async () => {
    await loadReferenceData();
    await loadCursosMaterias();

    document.getElementById("cursoMateriaForm").addEventListener("submit", async (event) => {
        event.preventDefault();
        await saveCursoMateria();
    });

    document.getElementById("searchInput").addEventListener("input", (event) => {
        renderCursosMaterias(filterCursosMaterias(event.target.value));
    });
});

async function loadReferenceData() {
    cursosCache = await apiGet("/cursos");
    materiasCache = await apiGet("/materias");
    docentesCache = await apiGet("/docentes");

    fillSelect("cursoId", cursosCache, curso => `${curso.nombre} - ${curso.nivel}`);
    fillSelect("materiaId", materiasCache, materia => materia.nombre);
    fillSelect("docenteId", docentesCache, docente => `${docente.nombre} ${docente.apellido}`);
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

async function loadCursosMaterias() {
    try {
        cursosMateriasCache = await apiGet("/cursos-materias");
        renderCursosMaterias(cursosMateriasCache);
    } catch (error) {
        showMessage(error.message, true);
    }
}

function renderCursosMaterias(items) {
    const table = document.getElementById("cursosMateriasTable");
    table.innerHTML = "";

    items.forEach(item => {
        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${item.id}</td>
            <td>${item.cursoNombre}</td>
            <td>${item.materiaNombre}</td>
            <td>${item.docenteNombre} ${item.docenteApellido}</td>
            <td>
                <div class="actions">
                    <button class="btn-secondary btn-icon" onclick="openEditCursoMateriaModal(${item.id})">✏️</button>
                    <button class="btn-danger btn-icon" onclick="deleteCursoMateria(${item.id})">🗑️</button>
                </div>
            </td>
        `;

        table.appendChild(row);
    });
}

function filterCursosMaterias(searchText) {
    const value = searchText.toLowerCase();

    return cursosMateriasCache.filter(item =>
        item.cursoNombre.toLowerCase().includes(value) ||
        item.materiaNombre.toLowerCase().includes(value) ||
        `${item.docenteNombre} ${item.docenteApellido}`.toLowerCase().includes(value)
    );
}

function openCreateCursoMateriaModal() {
    resetCursoMateriaForm();
    document.getElementById("modalTitle").textContent = "Nueva Asignación";
    document.getElementById("idDisplayGroup").style.display = "none";
    openModal("cursoMateriaModal");
}

async function openEditCursoMateriaModal(id) {
    try {
        const item = await apiGet(`/cursos-materias/${id}`);

        document.getElementById("modalTitle").textContent = "Editar Asignación";
        document.getElementById("idDisplayGroup").style.display = "block";

        document.getElementById("cursoMateriaId").value = item.id;
        document.getElementById("idDisplay").value = item.id;
        document.getElementById("cursoId").value = item.cursoId;
        document.getElementById("materiaId").value = item.materiaId;
        document.getElementById("docenteId").value = item.docenteId;

        openModal("cursoMateriaModal");
    } catch (error) {
        showMessage(error.message, true);
    }
}

function closeCursoMateriaModal() {
    closeModalById("cursoMateriaModal");
    resetCursoMateriaForm();
}

async function saveCursoMateria() {
    const id = document.getElementById("cursoMateriaId").value;

    const data = {
        cursoId: Number(document.getElementById("cursoId").value),
        materiaId: Number(document.getElementById("materiaId").value),
        docenteId: Number(document.getElementById("docenteId").value)
    };

    try {
        if (id) {
            await apiPut(`/cursos-materias/${id}`, data);
            showMessage("Asignación actualizada correctamente");
        } else {
            await apiPost("/cursos-materias", data);
            showMessage("Asignación creada correctamente");
        }

        closeCursoMateriaModal();
        await loadCursosMaterias();
    } catch (error) {
        showMessage(error.message, true);
    }
}

async function deleteCursoMateria(id) {
    if (!confirm("¿Desea eliminar esta asignación?")) return;

    try {
        await apiDelete(`/cursos-materias/${id}`);
        showMessage("Asignación eliminada correctamente");
        await loadCursosMaterias();
    } catch (error) {
        showMessage(error.message, true);
    }
}

function resetCursoMateriaForm() {
    document.getElementById("cursoMateriaForm").reset();
    document.getElementById("cursoMateriaId").value = "";
    document.getElementById("idDisplay").value = "";
}
