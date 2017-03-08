$(document).ready(function () {
    $("#main-container > button[data-type='click']").click(function () {
        console.log('click main');
        $("#main-container > p[data-type='result']").text("click main");
    });
    Component1.setup();
    Component2.setup();
});