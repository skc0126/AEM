function toggleDescription(button) {
    const description = button.nextElementSibling;
    if (description.classList.contains("hidden")) {
      description.classList.remove("hidden");
      button.textContent = "See Less";
    } else {
      description.classList.add("hidden");
      button.textContent = "See More";
    }
  }
  