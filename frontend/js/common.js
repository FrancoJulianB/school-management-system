function showMessage(message, isError = false) {
    const messageElement = document.getElementById("message");

    if (!messageElement) return;

    messageElement.textContent = message;
    messageElement.style.color = isError ? "#dc2626" : "#16a34a";

    setTimeout(() => {
        messageElement.textContent = "";
    }, 3000);
}

function formatDate(date) {
    if (!date) return "-";
    return date;
}
