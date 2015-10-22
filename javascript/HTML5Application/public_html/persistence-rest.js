/*  
 http://www.restapitutorial.com/lessons/httpmethods.html
 Create (Post, Insert), 
 Read (Get, Select)
 Update (Put)
 Delete
 */

//var url = "http://localhost:8080/jersey-quickstart-webapp-demo/webapi/myresource";
var url = "http://localhost:8080/tomcat-rest-demo/webresources/generic";

function createContact(input) {
}

function readAllContacts() {
    /*
     $.getJSON(url, function (result) {
     alert(result);
     //return result;
     });
     alert(url);
     */

    $.get(url, function (data, status) {
        //$("#myName").text(data.name);
        //alert(data.name);
    });

}

function updateContact(input) {
}

function deleteContact(contactId) {
}
