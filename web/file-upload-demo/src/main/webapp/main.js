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
    console.log('uploading...');
    $("#status").text("uploading...");
    var form = $('form')[0]; // You need to use standard javascript object here
    var formData = new FormData(form);
    for (var key of formData.keys()) {
        console.log(key);
    }
    /*
     var file = $("#file-input").files[0];
     var formData = new FormData();
     formData.append("file", file);
     */

    var url = "webresources/generic/upload";
    $.ajax(url, {
        data: formData,
        type: 'POST',
        //contentType: "multipart/form-data",
        contentType: false, // NEEDED, DON'T OMIT THIS (requires jQuery 1.6+)
        processData: false, // NEEDED, DON'T OMIT THIS
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(JSON.stringify(jqXHR));
            console.log(textStatus);
            console.log(errorThrown);
        }
    });
}

function uploadAlert() {
    var selectedFile = $("#upload-input")[0].files[0];
    var fileName = selectedFile.name;
    var fileSize = selectedFile.size;
    alert("Uploading: " + fileName + " @ " + fileSize + "bytes");
}

function uploadFile() {
    console.log('uploading...');
    var file = $("#upload-input")[0].files[0];
    var reader = new FileReader();
    reader.onload = function () {
        postFile(reader.result);
        //console.log(reader.result);
        //console.log(reader.result.byteLength);
    };
    reader.readAsDataURL(file);
    //reader.readAsArrayBuffer(file);                                                                                                                                                                                                                                                                                                   
    console.log('done');
}

var postFile = function (dataUrl) {
    console.log(JSON.stringify(dataUrl));
    var url = "http://localhost:8080/archie-ws/rest/files/uploadUrlEncoded";
    $.ajax(url, {
        type: "POST",
        //dataType: "json",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        //contentType: "multipart/form-data",
        data: dataUrl,
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(JSON.stringify(jqXHR));
            console.log(textStatus);
            console.log(errorThrown);
        }
    });
};