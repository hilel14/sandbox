var VideoComponent = {
    render: function () {
        $("#main-container").load("html/video.html", VideoComponent.setup);
    },
    setup: function () {
        $("#video > source").attr("src", "resources/song.ogv");
    }
};