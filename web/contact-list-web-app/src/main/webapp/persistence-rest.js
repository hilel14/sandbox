/*  
 http://www.restapitutorial.com/lessons/httpmethods.html
 Create (Post, Insert), 
 Read (Get, Select)
 Update (Put)
 Delete
 */

var url = "http://localhost:8080/contact-list-web-app/webresources/generic";

function createContact(input) {
    $.ajax({
        type: "POST",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        url: url,
        data: JSON.stringify(input),
        success: function (data) {
            console.log("createContact result: " + data.id);
            //return data.id;
        }
    });
}

function readAllContacts(fillRowsCallback) {
    $.getJSON(url, fillRowsCallback);
}

function updateContact(input) {
    $.ajax({
        type: "PUT",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        url: url,
        data: JSON.stringify(input)
    });
}

function deleteContact(contactId) {
    $.ajax({
        type: "DELETE",
        dataType: "json",
        contentType: "text/plain; charset=utf-8",
        url: url,
        data: contactId
    });
}
