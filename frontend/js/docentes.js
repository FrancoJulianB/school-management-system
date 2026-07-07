let docentesCache = [];

document.addEventListener("DOMContentLoaded", () => {
    loadDocentes();

    document.getElementById("docenteForm").addEventListener("submit", async (event) => {
        event.preventDefault();
        await saveDocente();
    });

    document.getElementById("searchInput").addEventListener("input", (event) => {
        renderDocentes(filterDocentes(event.target.value));
    });
});

async function loadDocentes() {
    try {
        docentesCache = await apiGet("/docentes");
        renderDocentes(docentesCache);
    } catch (error) {
        showMessage(error.message, true);
    }
}

function renderDocentes(docentes) {
    const table = document.getElementById("docentesTable");
    table.innerHTML = "";

    docentes.forEach(docente => {
        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${docente.id}</td>
            <td>${docente.nombre} ${docente.apellido}</td>
            <td>${docente.dni}</td>
            <td>${docente.email || "-"}</td>
            <td>
                <div class="actions">
                    <button class="btn-secondary btn-icon" onclick="openEditDocenteModal(${docente.id})">✏️</button>
                    <button class="btn-danger btn-icon" onclick="deleteDocente(${docente.id})">🗑️</button>
                </div>
            </td>
        `;

        table.appendChild(row);
    });
}

function filterDocentes(searchText) {
    const value = searchText.toLowerCase();

    return docentesCache.filter(docente =>
        `${docente.nombre} ${docente.apellido}`.toLowerCase().includes(value) ||
        docente.dni.toLowerCase().includes(value)
    );
}

function openCreateDocenteModal() {
    resetDocenteForm();
    document.getElementById("modalTitle").textContent = "Nuevo Docente";
    document.getElementById("idDisplayGroup").style.display = "none";
    openModal("docenteModal");
}

async function openEditDocenteModal(id) {
    try {
        const docente = await apiGet(`/docentes/${id}`);

        document.getElementById("modalTitle").textContent = "Editar Docente";
        document.getElementById("idDisplayGroup").style.display = "block";

        document.getElementById("docenteId").value = docente.id;
        document.getElementById("idDisplay").value = docente.id;
        document.getElementById("nombre").value = docente.nombre;
        document.getElementById("apellido").value = docente.apellido;
        document.getElementById("dni").value = docente.dni;
        document.getElementById("telefono").value = docente.telefono || "";
        document.getElementById("email").value = docente.email || "";

        openModal("docenteModal");
    } catch (error) {
        showMessage(error.message, true);
    }
}

function closeDocenteModal() {
    closeModalById("docenteModal");
    resetDocenteForm();
}

async function saveDocente() {
    const id = document.getElementById("docenteId").value;

    const docente = {
        nombre: document.getElementById("nombre").value,
        apellido: document.getElementById("apellido").value,
        dni: document.getElementById("dni").value,
        telefono: document.getElementById("telefono").value,
        email: document.getElementById("email").value
    };

    try {
        if (id) {
            await apiPut(`/docentes/${id}`, docente);
            showMessage("Docente actualizado correctamente");
        } else {
            await apiPost("/docentes", docente);
            showMessage("Docente creado correctamente");
        }

        closeDocenteModal();
        await loadDocentes();
    } catch (error) {
        showMessage(error.message, true);
    }
}

async function deleteDocente(id) {
    if (!confirm("¿Desea eliminar este docente?")) return;

    try {
        await apiDelete(`/docentes/${id}`);
        showMessage("Docente eliminado correctamente");
        await loadDocentes();
    } catch (error) {
        showMessage(error.message, true);
    }
}

function resetDocenteForm() {
    document.getElementById("docenteForm").reset();
    document.getElementById("docenteId").value = "";
    document.getElementById("idDisplay").value = "";
}
