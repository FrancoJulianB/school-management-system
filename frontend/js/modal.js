function openModal(modalId) {
    document.getElementById(modalId).classList.add("show");
}

function closeModalById(modalId) {
    document.getElementById(modalId).classList.remove("show");
}

window.addEventListener("click", (event) => {
    if (event.target.classList.contains("modal")) {
        event.target.classList.remove("show");
    }
});
