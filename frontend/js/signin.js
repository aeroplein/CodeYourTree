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

document.getElementById('login-form').addEventListener('submit', async (event) => {
    event.preventDefault();
    
    const username = document.getElementById('username-input').value;
    const password = document.getElementById('password-input').value;

    try {
        const response = await fetch(`${API_BASE}/login`, {
            method: 'POST',
            headers: { 
                'Content-Type': 'application/json' 
            },
            body: JSON.stringify({
                username: username,
                password: password
            })
        });

        const data = await response.text();
        if (response.ok) {
            showToast("Successfully logged in!", false);
            localStorage.setItem("loggedUser", data);
            setTimeout(() => {
                window.location.href = "app.html";
            }, 1500);
        } else {
            showToast(data || "Wrong password or username.", true);
        }
    } catch (error) {
        showToast("Server connection failed.", true);
        console.error("Fetch error:", error);
    }
});
