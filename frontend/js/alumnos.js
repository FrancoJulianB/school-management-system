let alumnosCache = [];

document.addEventListener("DOMContentLoaded", () => {
    loadAlumnos();

    document.getElementById("alumnoForm").addEventListener("submit", async (event) => {
        event.preventDefault();
        await saveAlumno();
    });

    document.getElementById("searchInput").addEventListener("input", (event) => {
        renderAlumnos(filterAlumnos(event.target.value));
    });
});

async function loadAlumnos() {
    try {
        alumnosCache = await apiGet("/alumnos");
        renderAlumnos(alumnosCache);
    } catch (error) {
        showMessage(error.message, true);
    }
}

function renderAlumnos(alumnos) {
    const table = document.getElementById("alumnosTable");
    table.innerHTML = "";

    alumnos.forEach(alumno => {
        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${alumno.id}</td>
            <td>${alumno.nombre} ${alumno.apellido}</td>
            <td>${alumno.dni}</td>
            <td>${alumno.email || "-"}</td>
            <td>
                <div class="actions">
                    <button class="btn-secondary btn-icon" onclick="openEditModal(${alumno.id})">✏️</button>
                    <button class="btn-danger btn-icon" onclick="deleteAlumno(${alumno.id})">🗑️</button>
                </div>
            </td>
        `;

        table.appendChild(row);
    });
}

function filterAlumnos(searchText) {
    const value = searchText.toLowerCase();

    return alumnosCache.filter(alumno =>
        `${alumno.nombre} ${alumno.apellido}`.toLowerCase().includes(value) ||
        alumno.dni.toLowerCase().includes(value)
    );
}

function openCreateModal() {
    resetForm();

    document.getElementById("modalTitle").textContent = "Nuevo Alumno";
    document.getElementById("idDisplayGroup").style.display = "none";
    document.getElementById("alumnoModal").classList.add("show");
}

async function openEditModal(id) {
    try {
        const alumno = await apiGet(`/alumnos/${id}`);

        document.getElementById("modalTitle").textContent = "Editar Alumno";
        document.getElementById("idDisplayGroup").style.display = "block";

        document.getElementById("alumnoId").value = alumno.id;
        document.getElementById("idDisplay").value = alumno.id;
        document.getElementById("nombre").value = alumno.nombre;
        document.getElementById("apellido").value = alumno.apellido;
        document.getElementById("dni").value = alumno.dni;
        document.getElementById("fechaNacimiento").value = alumno.fechaNacimiento || "";
        document.getElementById("telefono").value = alumno.telefono || "";
        document.getElementById("email").value = alumno.email || "";

        document.getElementById("alumnoModal").classList.add("show");
    } catch (error) {
        showMessage(error.message, true);
    }
}

function closeModal() {
    document.getElementById("alumnoModal").classList.remove("show");
    resetForm();
}

async function saveAlumno() {
    const id = document.getElementById("alumnoId").value;

    const alumno = {
        nombre: document.getElementById("nombre").value,
        apellido: document.getElementById("apellido").value,
        dni: document.getElementById("dni").value,
        fechaNacimiento: document.getElementById("fechaNacimiento").value || null,
        telefono: document.getElementById("telefono").value,
        email: document.getElementById("email").value,
        activo: true
    };

    try {
        if (id) {
            await apiPut(`/alumnos/${id}`, alumno);
            showMessage("Alumno actualizado correctamente");
        } else {
            await apiPost("/alumnos", alumno);
            showMessage("Alumno creado correctamente");
        }

        closeModal();
        await loadAlumnos();
    } catch (error) {
        showMessage(error.message, true);
    }
}

async function deleteAlumno(id) {
    if (!confirm("¿Desea eliminar este alumno?")) return;

    try {
        await apiDelete(`/alumnos/${id}`);
        showMessage("Alumno eliminado correctamente");
        await loadAlumnos();
    } catch (error) {
        showMessage(error.message, true);
    }
}

function resetForm() {
    document.getElementById("alumnoForm").reset();
    document.getElementById("alumnoId").value = "";
    document.getElementById("idDisplay").value = "";
}