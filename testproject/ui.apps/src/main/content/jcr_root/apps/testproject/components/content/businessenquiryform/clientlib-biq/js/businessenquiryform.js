document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById("enquiryForm");
    const responseDiv = document.getElementById("formResponse");

    form.addEventListener("submit", function(e) {
        e.preventDefault();
        const formData = new FormData(form);

        fetch(form.action, {
            method: "POST",
            body: formData
        })
        .then(res => res.json())
        .then(data => {
            responseDiv.innerHTML = `<p>${data.message}</p>`;
            form.reset();
        })
        .catch(err => {
            responseDiv.innerHTML = `<p style="color:red;">Error submitting form</p>`;
        });
    });
});
