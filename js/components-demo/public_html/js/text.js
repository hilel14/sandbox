var TextComponent = {
    render: function () {
        $("#main-container").load("html/text.html", TextComponent.setup);
    },
    setup: function () {
        $("#text").text("One two tree...");
    }
};