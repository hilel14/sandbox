// https://stackoverflow.com/questions/21044798/how-to-use-formdata-for-ajax-file-upload

$(document).ready(function () {
    setup();
});

var setup = function () {
    console.log('setup');
    $("#upload").click(function (e) {
        e.preventDefault();
        upload();
    });

};

function upload() {
    $("#status").text("uploading...");
    var form = $("#form1")[0]; // You need to use standard javascript object here
    var formData = new FormData(form);
    var url = "webresources/generic/upload";
    $.ajax(url, {
        data: formData,
        type: 'POST',
        //contentType: "multipart/form-data",
        contentType: false, // NEEDED, DON'T OMIT THIS (requires jQuery 1.6+)
        processData: false, // NEEDED, DON'T OMIT THIS
        success: function (data, textStatus, jqXHR) {
            $("#status").text("OK");
        },
        error: function (jqXHR, textStatus, errorThrown) {
            // console.log(JSON.stringify(jqXHR)); // long html error message
            console.log(textStatus);
            console.log(errorThrown);
            $("#status").text(errorThrown);
        }
    });
}