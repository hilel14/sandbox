var VideoModule = (
        function () {
            var setup = function () {
                $("#main-container").load("html/video.html", populate);
            };
            var populate = function () {
                $("#video > source").attr("src", "resources/song.ogv");
            };
            return {
                load: setup
            };
        }
)();