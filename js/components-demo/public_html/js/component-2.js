var Component2 = {
    setup: function () {
        $("#component-2-container").load("html/component-2.html");
        $("#component-2-container").on("click", "button[data-type='click']", function () {
            console.log('click 2');
            $("#component-2 > p[data-type='result']").text("click 2");
        });
    }
};