var TextComponent = {
    render: function () {
        $("#main-container").load("html/text.html", TextComponent.setup);
    },
    setup: function () {
        $("#text").text("One two tree...");
    }
};

var TextModule = (function () {
    var render = function () {
        $("#main-container").load("html/text.html", setup);
    };
    var setup = function () {
        $("#text").text("One two tree...");
    };
    return {
        render: render
    };
})();

