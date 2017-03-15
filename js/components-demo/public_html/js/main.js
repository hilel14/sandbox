$(document).ready(function () {
    $("#text-button").click(function () {
        TextComponent.render();
    });
    $("#image-button").click(function () {
        ImageComponent.render();
    });
    $("#video-button").click(function () {
        VideoComponent.render();
    });
});