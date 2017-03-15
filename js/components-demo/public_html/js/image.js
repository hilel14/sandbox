var ImageComponent = {
    render: function () {
        $("#main-container").load("html/image.html", ImageComponent.setup);
    },
    setup: function () {
        $("#image").attr("src", "resources/gnu.png");
    }
};