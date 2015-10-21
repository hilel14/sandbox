/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var allPanels = ["status-panel", "edit-panel", "list-panel"];
var allButtons = ["all-button", "cancel-button", "create-button", "save-button", "delete-button"];

/*
 * Event listeners
 */

window.addEventListener("load", function () {
    // add mouse click event listeners
    document.getElementById("all-button").addEventListener("click", function () {
        allButtonOnClick();
    });
    document.getElementById("cancel-button").addEventListener("click", function () {
        cancelButtonOnClick();
    });
    document.getElementById("create-button").addEventListener("click", function () {
        createButtonOnClick();
    });
    document.getElementById("save-button").addEventListener("click", function () {
        saveButtonOnClick();
    });
    document.getElementById("delete-button").addEventListener("click", function () {
        deleteButtonOnClick();
    });
    // start with all-contacts view
    populateContactTable();
    showSelectedPanel("list-panel");
    showSelectedButtons(["all-button", "create-button"]);
});

function allButtonOnClick() {
    populateContactTable();
    showSelectedPanel("list-panel");
    showSelectedButtons(["all-button", "create-button"]);
}

function cancelButtonOnClick() {
    showSelectedPanel("list-panel");
    showSelectedButtons(["all-button", "create-button"]);
}

function createButtonOnClick() {
    var form = document.getElementById("contact-details-form");
    form.elements["contactId"].value = "";
    form.elements["firstName"].value = "";
    form.elements["lastName"].value = "";
    form.elements["phone"].value = "";
    form.elements["email"].value = "";

    showSelectedPanel("edit-panel");
    showSelectedButtons(["cancel-button", "save-button"]);
}

function saveButtonOnClick() {
    var form = document.getElementById("contact-details-form");

    var contact = {
        first_name: form.elements["firstName"].value,
        last_name: form.elements["lastName"].value,
        phone: form.elements["phone"].value,
        email: form.elements["email"].value
    };

    var contactId = form.elements["contactId"].value;
    if (contactId === "") {
        createContact(contact);
    } else {
        contact.id = contactId;
        updateContact(contact);
    }

    populateContactTable();
    showSelectedPanel("list-panel");
    showSelectedButtons(["all-button", "create-button"]);
}

function deleteButtonOnClick() {
    var form = document.getElementById("contact-details-form");
    var contactId = form.elements["contactId"].value;
    deleteContact(contactId);

    populateContactTable();
    showSelectedPanel("list-panel");
    showSelectedButtons(["all-button", "create-button"]);
}

function editButtonOnClick(contact) {
    var form = document.getElementById("contact-details-form");
    form.elements["contactId"].value = contact.id;
    form.elements["firstName"].value = contact.first_name;
    form.elements["lastName"].value = contact.last_name;
    form.elements["phone"].value = contact.phone;
    form.elements["email"].value = contact.email;

    showSelectedPanel("edit-panel");
    showSelectedButtons(["cancel-button", "save-button", "delete-button"]);
}

/*
 * Content manipulation functions
 */

function showSelectedPanel(selectedPanel) {
    //document.getElementById("save-contact-button").style.visibility = "hidden";
    // hide all panels
    for (i in allPanels) {
        document.getElementById(allPanels[i]).style.display = "none";
    }
    // show selected panel
    document.getElementById(selectedPanel).style.display = "block";
}

function showSelectedButtons(selectedButtons) {
    // hide all buttons
    for (i in allButtons) {
        document.getElementById(allButtons[i]).style.display = "none";
    }
    // show selected buttons
    for (i in selectedButtons) {
        document.getElementById(selectedButtons[i]).style.display = "inline";
    }
}

function showMessage(msg) {
    document.getElementById("status-message").innerHTML = msg;
    showSelectedPanel("status-panel");
    showSelectedButtons(["all-button"]);
}

function populateContactTable() {
    //"use strict";
    //window.alert(new Date());
    var table = document.getElementById("contact-table");
    var tbody = table.getElementsByTagName("tbody")[0];
    var master = tbody.getElementsByTagName("tr")[0].cloneNode(true);

    for (i = tbody.rows.length; i > 0; i--) {
        tbody.deleteRow(i - 1);
    }

    var contactList = readAllContacts();
    var n = contactList.length;

    for (i = 0; i < n; i++) {
        var row = master.cloneNode(true);
        fillOneRow(row, contactList[i]);
        tbody.appendChild(row);
    }

    var tfoot = table.getElementsByTagName("tfoot")[0];
    var td2 = tfoot.getElementsByTagName("tr")[0].getElementsByTagName("td")[1];
    td2.innerHTML = new Date();
}

function fillOneRow(row, input) {
    //window.alert(i);
    var cells = row.getElementsByTagName("td");
    cells[0].innerHTML = input.id;
    cells[0].addEventListener("click", function () {
        editButtonOnClick(input);
    });
    cells[1].innerHTML = input.first_name;
    cells[2].innerHTML = input.last_name;
    cells[3].innerHTML = input.phone;
    cells[4].innerHTML = input.email;
}