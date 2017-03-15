var TextModule = (
        function () {
            var setup = function () {
                $("#main-container").load("html/text.html", populate);
            };
            var populate = function () {
                $("#text").text("One two tree...");
            };
            return {
                load: setup
            };
        }
)();
