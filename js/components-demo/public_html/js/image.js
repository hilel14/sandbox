var ImageModule = (
        function () {
            var setup = function () {
                $("#main-container").load("html/image.html", populate);
            };
            var populate = function () {
                $("#image").attr("src", "resources/gnu.png");
            };
            return {
                load: setup
            };
        }
)();