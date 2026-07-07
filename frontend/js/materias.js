let materiasCache = [];

document.addEventListener("DOMContentLoaded", () => {
    loadMaterias();

    document.getElementById("materiaForm").addEventListener("submit", async (event) => {
        event.preventDefault();
        await saveMateria();
    });

    document.getElementById("searchInput").addEventListener("input", (event) => {
        renderMaterias(filterMaterias(event.target.value));
    });
});

async function loadMaterias() {
    try {
        materiasCache = await apiGet("/materias");
        renderMaterias(materiasCache);
    } catch (error) {
        showMessage(error.message, true);
    }
}

function renderMaterias(materias) {
    const table = document.getElementById("materiasTable");
    table.innerHTML = "";

    materias.forEach(materia => {
        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${materia.id}</td>
            <td>${materia.nombre}</td>
            <td>${materia.descripcion || "-"}</td>
            <td>
                <div class="actions">
                    <button class="btn-secondary btn-icon" onclick="openEditMateriaModal(${materia.id})">✏️</button>
                    <button class="btn-danger btn-icon" onclick="deleteMateria(${materia.id})">🗑️</button>
                </div>
            </td>
        `;

        table.appendChild(row);
    });
}

function filterMaterias(searchText) {
    const value = searchText.toLowerCase();

    return materiasCache.filter(materia =>
        materia.nombre.toLowerCase().includes(value)
    );
}

function openCreateMateriaModal() {
    resetMateriaForm();
    document.getElementById("modalTitle").textContent = "Nueva Materia";
    document.getElementById("idDisplayGroup").style.display = "none";
    openModal("materiaModal");
}

async function openEditMateriaModal(id) {
    try {
        const materia = await apiGet(`/materias/${id}`);

        document.getElementById("modalTitle").textContent = "Editar Materia";
        document.getElementById("idDisplayGroup").style.display = "block";

        document.getElementById("materiaId").value = materia.id;
        document.getElementById("idDisplay").value = materia.id;
        document.getElementById("nombre").value = materia.nombre;
        document.getElementById("descripcion").value = materia.descripcion || "";

        openModal("materiaModal");
    } catch (error) {
        showMessage(error.message, true);
    }
}

function closeMateriaModal() {
    closeModalById("materiaModal");
    resetMateriaForm();
}

async function saveMateria() {
    const id = document.getElementById("materiaId").value;

    const materia = {
        nombre: document.getElementById("nombre").value,
        descripcion: document.getElementById("descripcion").value
    };

    try {
        if (id) {
            await apiPut(`/materias/${id}`, materia);
            showMessage("Materia actualizada correctamente");
        } else {
            await apiPost("/materias", materia);
            showMessage("Materia creada correctamente");
        }

        closeMateriaModal();
        await loadMaterias();
    } catch (error) {
        showMessage(error.message, true);
    }
}

async function deleteMateria(id) {
    if (!confirm("¿Desea eliminar esta materia?")) return;

    try {
        await apiDelete(`/materias/${id}`);
        showMessage("Materia eliminada correctamente");
        await loadMaterias();
    } catch (error) {
        showMessage(error.message, true);
    }
}

function resetMateriaForm() {
    document.getElementById("materiaForm").reset();
    document.getElementById("materiaId").value = "";
    document.getElementById("idDisplay").value = "";
}
