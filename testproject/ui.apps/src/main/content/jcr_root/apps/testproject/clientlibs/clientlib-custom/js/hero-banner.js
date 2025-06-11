document.addEventListener("DOMContentLoaded", function () {
    const slides = document.querySelectorAll(".slide");
    let currentIndex = 0;

    function showNextSlide() {
        currentIndex = (currentIndex + 1) % slides.length;
        document.querySelector(".hero-slider").style.transform = `translateX(-${currentIndex * 100}%)`;
    }

    setInterval(showNextSlide, 5000);
});