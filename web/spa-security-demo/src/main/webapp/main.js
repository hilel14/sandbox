'use strict';

$(document).ready(function () {
    $("#login-form").hide();
    $("#public-button").on("click", function (e) {
        getPublicInfo(displayResults, displayResults);
    });
    $("#secret-button").on("click", function (e) {
        getSecret(displayResults, displayResults);
    });
    $("#login-button").on("click", function (e) {
        e.preventDefault();
        var form = $("#login-form")[0];
        var formData = new FormData(form);
        login(formData, displayResults, displayResults);
    });
});

var displayResults = function (data) {
    $("#status-message").text(data);
};

var login = function (formData, success, error) {
    var url = "webresources/generic/login";
    $.ajax(url, {
        method: 'PUT',
        data: formData,
        //contentType: "multipart/form-data",
        contentType: false, // NEEDED, DON'T OMIT THIS (requires jQuery 1.6+)
        processData: false, // NEEDED, DON'T OMIT THIS
        success: function (data, textStatus, jqXHR) {
            success("Success: " + JSON.stringify(data));
            sessionStorage.setItem('token', data);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(jqXHR);
            error(textStatus + " : " + errorThrown);
        }
    });
};

var getPublicInfo = function (success, error) {
    var url = "webresources/generic/public";
    $.ajax(url, {
        method: 'GET',
        dataType: "json",
        success: function (data, textStatus, jqXHR) {
            success("Success: " + JSON.stringify(data));
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(jqXHR);
            error(textStatus + " : " + errorThrown);
        }
    });
};

var getSecret = function (success, error) {
    // Authorization header (using the Bearer scheme)
    var token = sessionStorage.getItem('token');
    if (token === null) {
        $("#login-form").show();
        return;
    }
    var url = "webresources/generic/secret";
    var headers = {"authorization": token};
    $.ajax(url, {
        //method: 'GET',
        //dataType: "json",
        //contentType: "text/plain; charset=utf-8",
        //data: data,
        headers: headers,        
        success: function (data, textStatus, jqXHR) {
            success("Success: " + JSON.stringify(data));
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(jqXHR);
            error(textStatus + " : " + errorThrown);
        }
    });
};

