$(document).ready(function () {
    $("#text-button").click(function () {
        TextModule.load();
    });
    $("#image-button").click(function () {
        ImageModule.load();
    });
    $("#video-button").click(function () {
        VideoModule.load();
    });
});