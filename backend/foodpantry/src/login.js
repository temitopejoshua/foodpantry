// Handle form submission
document.getElementById("loginForm").addEventListener("submit", function(event) {
    event.preventDefault(); // Stop default form action
  
    const username = document.getElementById("username").value.trim();
    const password = document.getElementById("password").value.trim();
    const errorMessage = document.getElementById("errorMessage");
  
    // Check for empty fields
    if (!username || !password) {
      errorMessage.textContent = "Both fields are required.";
      return;
    }
  
    // Simulate server validation
    fetch("/api/validate-login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({ username, password })
    })
    .then(response => {
      if (!response.ok) {
        throw new Error("Invalid credentials or server error");
      }
      return response.json();
    })
    .then(data => {
      // Handle successful login
      if (data.success) {
        window.location.href = data.redirectUrl; // Redirect user
      } else {
        errorMessage.textContent = "Invalid username or password.";
      }
    })
    .catch(error => {
      console.error("Error during login:", error);
      errorMessage.textContent = "An error occurred. Please try again.";
    });
  });
  
