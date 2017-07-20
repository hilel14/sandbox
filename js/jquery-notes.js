$("#my-button").click(function () {
    console.log("click...");
});

$("#my-container").on("click", "#my-button", function (e) {
    console.log("click...");
});