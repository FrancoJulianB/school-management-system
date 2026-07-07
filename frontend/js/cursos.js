let cursosCache = [];

document.addEventListener("DOMContentLoaded", () => {
    loadCursos();

    document.getElementById("cursoForm").addEventListener("submit", async (event) => {
        event.preventDefault();
        await saveCurso();
    });

    document.getElementById("searchInput").addEventListener("input", (event) => {
        renderCursos(filterCursos(event.target.value));
    });
});

async function loadCursos() {
    try {
        cursosCache = await apiGet("/cursos");
        renderCursos(cursosCache);
    } catch (error) {
        showMessage(error.message, true);
    }
}

function renderCursos(cursos) {
    const table = document.getElementById("cursosTable");
    table.innerHTML = "";

    cursos.forEach(curso => {
        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${curso.id}</td>
            <td>${curso.nombre}</td>
            <td>${curso.nivel}</td>
            <td>${curso.gradoAnio}</td>
            <td>${curso.division}</td>
            <td>${curso.capacidad || "-"}</td>
            <td>
                <div class="actions">
                    <button class="btn-secondary btn-icon" onclick="openEditCursoModal(${curso.id})">✏️</button>
                    <button class="btn-danger btn-icon" onclick="deleteCurso(${curso.id})">🗑️</button>
                </div>
            </td>
        `;

        table.appendChild(row);
    });
}

function filterCursos(searchText) {
    const value = searchText.toLowerCase();

    return cursosCache.filter(curso =>
        curso.nombre.toLowerCase().includes(value) ||
        curso.nivel.toLowerCase().includes(value) ||
        curso.division.toLowerCase().includes(value)
    );
}

function openCreateCursoModal() {
    resetCursoForm();
    document.getElementById("modalTitle").textContent = "Nuevo Curso";
    document.getElementById("idDisplayGroup").style.display = "none";
    openModal("cursoModal");
}

async function openEditCursoModal(id) {
    try {
        const curso = await apiGet(`/cursos/${id}`);

        document.getElementById("modalTitle").textContent = "Editar Curso";
        document.getElementById("idDisplayGroup").style.display = "block";

        document.getElementById("cursoId").value = curso.id;
        document.getElementById("idDisplay").value = curso.id;
        document.getElementById("nombre").value = curso.nombre;
        document.getElementById("nivel").value = curso.nivel;
        document.getElementById("gradoAnio").value = curso.gradoAnio;
        document.getElementById("division").value = curso.division;
        document.getElementById("capacidad").value = curso.capacidad || "";

        openModal("cursoModal");
    } catch (error) {
        showMessage(error.message, true);
    }
}

function closeCursoModal() {
    closeModalById("cursoModal");
    resetCursoForm();
}

async function saveCurso() {
    const id = document.getElementById("cursoId").value;

    const curso = {
        nombre: document.getElementById("nombre").value,
        nivel: document.getElementById("nivel").value,
        gradoAnio: Number(document.getElementById("gradoAnio").value),
        division: document.getElementById("division").value,
        capacidad: document.getElementById("capacidad").value ? Number(document.getElementById("capacidad").value) : null
    };

    try {
        if (id) {
            await apiPut(`/cursos/${id}`, curso);
            showMessage("Curso actualizado correctamente");
        } else {
            await apiPost("/cursos", curso);
            showMessage("Curso creado correctamente");
        }

        closeCursoModal();
        await loadCursos();
    } catch (error) {
        showMessage(error.message, true);
    }
}

async function deleteCurso(id) {
    if (!confirm("¿Desea eliminar este curso?")) return;

    try {
        await apiDelete(`/cursos/${id}`);
        showMessage("Curso eliminado correctamente");
        await loadCursos();
    } catch (error) {
        showMessage(error.message, true);
    }
}

function resetCursoForm() {
    document.getElementById("cursoForm").reset();
    document.getElementById("cursoId").value = "";
    document.getElementById("idDisplay").value = "";
}
