function toggleTheme() {
  const htmlElement = document.documentElement;
  const themeToggleIcon = document.getElementById("themeToggleIcon");
  const currentTheme = htmlElement.getAttribute("data-bs-theme");
  const newTheme = currentTheme === "dark" ? "light" : "dark";

  htmlElement.setAttribute("data-bs-theme", newTheme);
  localStorage.setItem("theme", newTheme);

  // Update icon based on theme
  if (newTheme === "dark") {
    themeToggleIcon.classList.remove("bi-sun");
    themeToggleIcon.classList.add("bi-moon-stars-fill");
  } else {
    themeToggleIcon.classList.remove("bi-moon-stars-fill");
    themeToggleIcon.classList.add("bi-sun");
  }
}

document.addEventListener("DOMContentLoaded", () => {
  const savedTheme = localStorage.getItem("theme") || "light";
  const htmlElement = document.documentElement;
  const themeToggleIcon = document.getElementById("themeToggleIcon");

  htmlElement.setAttribute("data-bs-theme", savedTheme);

  // Set initial icon
  if (savedTheme === "dark") {
    themeToggleIcon.classList.remove("bi-sun");
    themeToggleIcon.classList.add("bi-moon-stars-fill");
  } else {
    themeToggleIcon.classList.remove("bi-moon-stars-fill");
    themeToggleIcon.classList.add("bi-sun");
  }
});
