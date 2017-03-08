var Component1 = {
    setup: function () {
        $("#component-1-container").load("html/component-1.html");
        $("#component-1-container").on("click", "button[data-type='click']", function () {
            console.log('click 1');
            $("#component-1 > p[data-type='result']").text("click 1");
            Component1.task1();
        });
    },
    task1: function () {
        console.log('component 1 task 1');
    }
};