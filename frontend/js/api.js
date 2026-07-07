const API_URL = "http://localhost:8080/api";

async function apiGet(endpoint) {
    const response = await fetch(`${API_URL}${endpoint}`);
    return handleResponse(response);
}

async function apiPost(endpoint, data) {
    const response = await fetch(`${API_URL}${endpoint}`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data)
    });

    return handleResponse(response);
}

async function apiPut(endpoint, data) {
    const response = await fetch(`${API_URL}${endpoint}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data)
    });

    return handleResponse(response);
}

async function apiDelete(endpoint) {
    const response = await fetch(`${API_URL}${endpoint}`, {
        method: "DELETE"
    });

    if (response.status === 204) return null;
    return handleResponse(response);
}

async function handleResponse(response) {
    if (!response.ok) {
        let errorMessage = "Error en la operación";

        try {
            const error = await response.json();
            errorMessage = error.message || errorMessage;
        } catch (_) {}

        throw new Error(errorMessage);
    }

    return response.json();
}
