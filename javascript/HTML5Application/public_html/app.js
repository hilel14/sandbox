/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function showAllContacts() {
    document.getElementById("address-list-panel").style.display = "block";
    document.getElementById("edit-contact-panel").style.display = "none";
    document.getElementById("status-panel").style.display = "none";
    allContactsButtons();
    fillAddressList();
}

function cancel() {
    document.getElementById("address-list-panel").style.display = "block";
    document.getElementById("edit-contact-panel").style.display = "none";
    document.getElementById("status-panel").style.display = "none";
    allContactsButtons();
}

function createNewContact() {
    document.getElementById("address-list-panel").style.display = "none";
    document.getElementById("edit-contact-panel").style.display = "block";
    document.getElementById("status-panel").style.display = "none";
    newContactButtons();

    var form = document.getElementById("contact-details-form");
    form.elements["contactId"].value = "";
    form.elements["firstName"].value = "";
    form.elements["lastName"].value = "";
    form.elements["phone"].value = "";
    form.elements["email"].value = "";
}

function saveContact() {
    var form = document.getElementById("contact-details-form");
    var contactId = form.elements["contactId"].value;
    if (contactId === "") {
        contactId = findNextContactId();
        //alert("contactId = " + contactId);
        // create and push new object to addressList
        var newContact = {id: contactId, first_name: "", last_name: "", phone: "", email: ""};
        addressList.push(newContact);
    }
    var selectedContact = findContactById(contactId);
    selectedContact.first_name = form.elements["firstName"].value;
    selectedContact.last_name = form.elements["lastName"].value;
    selectedContact.phone = form.elements["phone"].value;
    selectedContact.email = form.elements["email"].value;
    /*
     document.write(addressList.length);
     document.write("<br>");
     document.write(addressList[0]);
     document.write("<br>");
     document.write(addressList[0].email);
     */
    showAllContacts();
}

function showMessage(msg) {
    document.getElementById("address-list-panel").style.display = "none";
    document.getElementById("edit-contact-panel").style.display = "none";
    document.getElementById("status-panel").style.display = "block";
    document.getElementById("status-message").innerHTML = msg;
    allContactsButtons();
}

function findNextContactId() {
    //alert("addressList.length = " + addressList.length);
    if (addressList.length > 0) {
        return addressList[addressList.length - 1].id + 1;
    }
    return 101;
}

function editContact(contactId) {
    var form = document.getElementById("contact-details-form");
    form.elements["contactId"].value = contactId;
    //document.getElementById("formContactId").innerHTML = contactId;
    var contact = findContactById(contactId)
    //document.getElementById("formFirstName").innerHTML = contact.first_name;
    form.elements["firstName"].value = contact.first_name;
    form.elements["lastName"].value = contact.last_name;
    form.elements["phone"].value = contact.phone;
    form.elements["email"].value = contact.email;

    document.getElementById("address-list-panel").style.display = "none";
    document.getElementById("edit-contact-panel").style.display = "block";
    editContactButtons();
}

function deleteContact() {
    if (addressList.length < 2) {
        showMessage("Can't delete the last address");
        return;
    }
    var form = document.getElementById("contact-details-form");
    var contactId = form.elements["contactId"].value;
    for (i = 0; i < addressList.length; i++) {
        if (addressList[i].id == contactId) {
            //alert(i);
            addressList.splice(i, 1);
            break;
        }
    }
    showAllContacts();
}

function fillAddressList() {
    //"use strict";
    //window.alert(new Date());
    var table = document.getElementById("address-list-table");
    var tbody = table.getElementsByTagName("tbody")[0];
    var master = tbody.getElementsByTagName("tr")[0].cloneNode(true);

    for (i = tbody.rows.length; i > 0; i--) {
        tbody.deleteRow(i - 1);
    }

    var n = addressList.length;
    for (i = 0; i < n; i++) {
        var row = master.cloneNode(true);
        fillOneRow(row, i);
        tbody.appendChild(row);
    }

    var tfoot = table.getElementsByTagName("tfoot")[0];
    var td2 = tfoot.getElementsByTagName("tr")[0].getElementsByTagName("td")[1];
    td2.innerHTML = new Date();
}

function allContactsButtons() {
    //document.getElementById("save-contact-button").style.visibility = "hidden";
    document.getElementById("show-address-list").style.display = "inline";
    document.getElementById("cancel-button").style.display = "none";
    document.getElementById("create-new-contact").style.display = "inline";
    document.getElementById("save-contact-button").style.display = "none";
    document.getElementById("delete-contact-button").style.display = "none";
}

function newContactButtons() {
    document.getElementById("show-address-list").style.display = "none";
    document.getElementById("cancel-button").style.display = "inline";
    document.getElementById("create-new-contact").style.display = "none";
    document.getElementById("save-contact-button").style.display = "inline";
    document.getElementById("delete-contact-button").style.display = "none";
}

function editContactButtons() {
    document.getElementById("show-address-list").style.display = "none";
    document.getElementById("cancel-button").style.display = "inline";
    document.getElementById("create-new-contact").style.display = "none";
    document.getElementById("save-contact-button").style.display = "inline";
    document.getElementById("delete-contact-button").style.display = "inline";
}

function fillOneRow(row, i) {
    //window.alert(i);
    var cells = row.getElementsByTagName("td");
    cells[0].innerHTML = addressList[i].id;
    cells[0].addEventListener("click", function () {
        editContact(addressList[i].id);
    });
    cells[1].innerHTML = addressList[i].first_name;
    cells[2].innerHTML = addressList[i].last_name;
    cells[3].innerHTML = addressList[i].phone;
    cells[4].innerHTML = addressList[i].email;
}

function findContactById(contactId) {
    for (i = 0; i < addressList.length; i++) {
        if (addressList[i].id == contactId) {
            return addressList[i];
        }
    }
}

var addressList = [
    {
        id: 101,
        first_name: "John",
        last_name: "Smith",
        phone: "111 2222",
        email: "john.smith@example.com"
    },
    {
        id: 102,
        first_name: "Johnny",
        last_name: "Boy",
        phone: "333 4444",
        email: "johnny.boy@example.org"
    },
    {
        id: 103,
        first_name: "Johanna",
        last_name: "Jones",
        phone: "555 6666",
        email: "johanna.jones@example.net"
    }
];