const API_BASE = "http://localhost:8081/api/users";

function showToast(message, isError = false) {
    const toast = document.getElementById("toast");
    if (!toast) return;

    toast.innerText = message;
    toast.style.backgroundColor = isError ? "#ef4444" : "#22c55e";
    toast.className = "show";

    setTimeout(() => {
        toast.className = toast.className.replace("show", "");
    }, 3000);
}

document.getElementById('signup-form').addEventListener('submit', async (e) => {
    e.preventDefault();

    const username = document.getElementById('username').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const confirm = document.getElementById('confirm-password').value;

    if (password !== confirm) {
        showToast("Passwords do not match!", true);
        return;
    }

    try {
        const response = await fetch(`${API_BASE}/register`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username,
                email,
                password
            })
        });

        const data = await response.text();
        if (response.ok) {
            showToast("Successfully registered!", false);
            setTimeout(() => {
                window.location.href = "signin.html";
            }, 2000);
        } else {
            showToast(data, true);
        }
    } catch (error) {
        showToast("Server connection failed.", true);
        console.error("Fetch error:", error);
    }
});
