let facturasCache = [];
let alumnosCache = [];

document.addEventListener("DOMContentLoaded", async () => {
    await loadReferenceData();
    await loadFacturas();

    document.getElementById("facturaForm").addEventListener("submit", async (event) => {
        event.preventDefault();
        await saveFactura();
    });

    document.getElementById("searchInput").addEventListener("input", (event) => {
        renderFacturas(filterFacturas(event.target.value));
    });
});

async function loadReferenceData() {
    alumnosCache = await apiGet("/alumnos");

    const select = document.getElementById("alumnoId");
    select.innerHTML = `<option value="">Seleccione</option>`;

    alumnosCache.forEach(alumno => {
        const option = document.createElement("option");
        option.value = alumno.id;
        option.textContent = `${alumno.nombre} ${alumno.apellido} - DNI ${alumno.dni}`;
        select.appendChild(option);
    });
}

async function loadFacturas() {
    try {
        facturasCache = await apiGet("/facturas");
        renderFacturas(facturasCache);
    } catch (error) {
        showMessage(error.message, true);
    }
}

function renderFacturas(facturas) {
    const table = document.getElementById("facturasTable");
    table.innerHTML = "";

    facturas.forEach(factura => {
        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${factura.id}</td>
            <td>${factura.alumnoNombre} ${factura.alumnoApellido}</td>
            <td>${formatTipoFactura(factura.tipoFactura)}</td>
            <td>${renderEstado(factura.estado)}</td>
            <td>${formatDate(factura.fechaEmision)}</td>
            <td>${formatDate(factura.fechaVencimiento)}</td>
            <td>€${Number(factura.total).toFixed(2)}</td>
            <td>
                <div class="actions">
                    <button class="btn-secondary btn-icon" onclick="openEditFacturaModal(${factura.id})">✏️</button>
                    ${factura.estado !== "PAGADA"
                        ? `<button class="btn-primary btn-icon" onclick="openPagoModal(${factura.id})">💳</button>`
                        : ""
                    }
                    <button class="btn-danger btn-icon" onclick="deleteFactura(${factura.id})">🗑️</button>
                </div>
            </td>
        `;

        table.appendChild(row);
    });
}

function filterFacturas(searchText) {
    const value = searchText.toLowerCase();

    return facturasCache.filter(factura =>
        `${factura.alumnoNombre} ${factura.alumnoApellido}`.toLowerCase().includes(value) ||
        factura.tipoFactura.toLowerCase().includes(value) ||
        factura.estado.toLowerCase().includes(value)
    );
}

function openCreateFacturaModal() {
    resetFacturaForm();

    document.getElementById("facturaModalTitle").textContent = "Nueva Factura";
    document.getElementById("idDisplayGroup").style.display = "none";

    const today = new Date();
    today.setDate(today.getDate() + 10);
    document.getElementById("fechaVencimiento").value = today.toISOString().split("T")[0];

    openModal("facturaModal");
}

async function openEditFacturaModal(id) {
    try {
        const factura = await apiGet(`/facturas/${id}`);

        document.getElementById("facturaModalTitle").textContent = "Editar Factura";
        document.getElementById("idDisplayGroup").style.display = "block";

        document.getElementById("facturaId").value = factura.id;
        document.getElementById("idDisplay").value = factura.id;
        document.getElementById("alumnoId").value = factura.alumnoId;
        document.getElementById("tipoFactura").value = factura.tipoFactura;
        document.getElementById("fechaVencimiento").value = factura.fechaVencimiento || "";

        const firstDetail = factura.detalles && factura.detalles.length > 0 ? factura.detalles[0] : null;
        document.getElementById("concepto").value = firstDetail ? firstDetail.concepto : "";
        document.getElementById("monto").value = firstDetail ? firstDetail.monto : "";

        openModal("facturaModal");
    } catch (error) {
        showMessage(error.message, true);
    }
}

function closeFacturaModal() {
    closeModalById("facturaModal");
    resetFacturaForm();
}

async function saveFactura() {
    const id = document.getElementById("facturaId").value;

    const factura = {
        alumnoId: Number(document.getElementById("alumnoId").value),
        tipoFactura: document.getElementById("tipoFactura").value,
        fechaVencimiento: document.getElementById("fechaVencimiento").value,
        detalles: [
            {
                concepto: document.getElementById("concepto").value,
                monto: Number(document.getElementById("monto").value)
            }
        ]
    };

    try {
        if (id) {
            await apiPut(`/facturas/${id}`, factura);
            showMessage("Factura actualizada correctamente");
        } else {
            await apiPost("/facturas", factura);
            showMessage("Factura creada correctamente");
        }

        closeFacturaModal();
        await loadFacturas();
    } catch (error) {
        showMessage(error.message, true);
    }
}

async function deleteFactura(id) {
    if (!confirm("¿Desea eliminar esta factura?")) return;

    try {
        await apiDelete(`/facturas/${id}`);
        showMessage("Factura eliminada correctamente");
        await loadFacturas();
    } catch (error) {
        showMessage(error.message, true);
    }
}

async function openPagoModal(id) {
    try {
        const factura = await apiGet(`/facturas/${id}`);

        document.getElementById("pagoFacturaId").value = factura.id;
        document.getElementById("pagoFacturaDisplay").textContent = `#${factura.id} - ${formatTipoFactura(factura.tipoFactura)}`;
        document.getElementById("pagoAlumnoDisplay").textContent = `${factura.alumnoNombre} ${factura.alumnoApellido}`;
        document.getElementById("pagoTotalDisplay").textContent = Number(factura.total).toFixed(2);

        openModal("pagoModal");
    } catch (error) {
        showMessage(error.message, true);
    }
}

function closePagoModal() {
    closeModalById("pagoModal");
    document.getElementById("pagoFacturaId").value = "";
}

async function payFactura() {
    const id = document.getElementById("pagoFacturaId").value;

    const pago = {
        medioPago: document.getElementById("medioPago").value
    };

    try {
        await apiPost(`/facturas/${id}/pagar`, pago);

        showMessage("Pago realizado correctamente");
        closePagoModal();
        await loadFacturas();
    } catch (error) {
        showMessage(error.message, true);
    }
}

function resetFacturaForm() {
    document.getElementById("facturaForm").reset();
    document.getElementById("facturaId").value = "";
    document.getElementById("idDisplay").value = "";
}

function formatTipoFactura(tipo) {
    if (tipo === "MATRICULA") return "Matrícula";
    if (tipo === "CUOTA") return "Cuota";
    return tipo;
}

function renderEstado(estado) {
    const labels = {
        PENDIENTE: "Pendiente",
        PAGADA: "Pagada",
        VENCIDA: "Vencida",
        ANULADA: "Anulada"
    };

    const cssClass = {
        PENDIENTE: "badge-warning",
        PAGADA: "badge-success",
        VENCIDA: "badge-danger",
        ANULADA: "badge-muted"
    };

    return `<span class="badge ${cssClass[estado] || "badge-muted"}">${labels[estado] || estado}</span>`;
}
