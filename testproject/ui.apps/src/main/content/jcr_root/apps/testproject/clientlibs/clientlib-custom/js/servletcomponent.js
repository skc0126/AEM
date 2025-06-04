function myButtonFunction(status) {

  // Get the servlet path from the data attribute of the button component
  const defaultServletPath = $(".my-button-component").data("current-resource");

  // Set up the AJAX request with the dynamic status flag
  var settings = {
      url: defaultServletPath + ".buttoncomponent.html",
      data: {
          statusFlag: status,
      },
      method: "POST",
  };

  // Execute the AJAX request
  $.ajax(settings)
      .done(function (response) {
          console.log("Success");
      })
      .fail((response) => {
          console.log("Failure");
      });
}

