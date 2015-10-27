/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var allPanels = ["edit-panel", "contacts-panel"];
var allButtons = ["all-button", "cancel-button", "create-button", "save-button", "delete-button"];
var masterRow = undefined;

/*
 * Event listeners
 */

$(document).ready(function () {
    // add mouse click event listeners
    $("#all-button").click(function () {
        allButtonOnClick();
    });
    $("#cancel-button").click(function () {
        cancelButtonOnClick();
    });
    $("#create-button").click(function () {
        createButtonOnClick();
    });
    $("#save-button").click(function () {
        saveButtonOnClick();
    });
    $("#delete-button").click(function () {
        deleteButtonOnClick();
    });
    // clone first row of contact table
    masterRow = document.getElementById("master-row").cloneNode(true);
    // start with all-contacts view
    populateContactTable();
    showSelectedPanel("contacts-panel");
    showSelectedButtons(["all-button", "create-button"]);
    showStatusMessage("Wellcom. Create new contact or Edit existing one.");
});

function allButtonOnClick() {
    populateContactTable();
    showSelectedPanel("contacts-panel");
    showSelectedButtons(["all-button", "create-button"]);
    showStatusMessage("Done loading all contacts");
}

function cancelButtonOnClick() {
    showSelectedPanel("contacts-panel");
    showSelectedButtons(["all-button", "create-button"]);
    showStatusMessage("Operation canceled");
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
    showStatusMessage("Save new contact or Cancel");
}

function saveButtonOnClick() {
    var form = document.getElementById("contact-details-form");

    var contact = {
        firstName: form.elements["firstName"].value,
        lastName: form.elements["lastName"].value,
        phone: form.elements["phone"].value,
        email: form.elements["email"].value
    };

    var contactId = form.elements["contactId"].value;
    if (contactId === "") {
        contactId = createContact(contact);
        var row = masterRow.cloneNode(true);
        fillOneRow(row, contact);
        $('#contact-table-body').append(row);
        showStatusMessage("Contact number " + contactId + " created successfully");
    } else {
        contact.id = contactId;
        updateContact(contact);

        $('#c' + contactId).find('td').eq(1).text(contact.firstName);
        $('#c' + contactId).find('td').eq(2).text(contact.lastName);
        $('#c' + contactId).find('td').eq(3).text(contact.phone);
        $('#c' + contactId).find('td').eq(4).text(contact.email);

        showStatusMessage("Contact number " + contactId + " updated successfully");
    }

    //populateContactTable();
    showSelectedPanel("contacts-panel");
    showSelectedButtons(["all-button", "create-button"]);
}

function deleteButtonOnClick() {
    var form = document.getElementById("contact-details-form");
    var contactId = form.elements["contactId"].value;
    deleteContact(contactId);

    //var tbody = document.getElementById("contact-table-body");
    /*
     $('#contact-table-body tr').each(function () {
     $(this).find('td').each(function (td) {
     console.log(td);
     });
     });
     */
    $('#c' + contactId).remove();

    //populateContactTable();
    showSelectedPanel("contacts-panel");
    showSelectedButtons(["all-button", "create-button"]);
    showStatusMessage("Contact number " + contactId + " deleted");
}

function editButtonOnClick(contact) {
    var form = document.getElementById("contact-details-form");
    form.elements["contactId"].value = contact.id;
    form.elements["firstName"].value = contact.firstName;
    form.elements["lastName"].value = contact.lastName;
    form.elements["phone"].value = contact.phone;
    form.elements["email"].value = contact.email;

    showSelectedPanel("edit-panel");
    showSelectedButtons(["cancel-button", "save-button", "delete-button"]);
    showStatusMessage("Update contact details or Cancel");
}

/*
 * DOM manipulation functions
 */

function showSelectedPanel(selectedPanel) {
    //document.getElementById("save-contact-button").style.visibility = "hidden";
    // hide all panels
    for (var i in allPanels) {
        document.getElementById(allPanels[i]).style.display = "none";
    }
    // show selected panel
    document.getElementById(selectedPanel).style.display = "block";
}

function showSelectedButtons(selectedButtons) {
    // hide all buttons
    for (var i in allButtons) {
        document.getElementById(allButtons[i]).style.display = "none";
    }
    // show selected buttons
    for (var i in selectedButtons) {
        document.getElementById(selectedButtons[i]).style.display = "inline";
    }
}

function showStatusMessage(message) {
    $("#status-message-date").text(new Date());
    $("#status-message").text(message);
}

function populateContactTable() {
    //console.log($("#contact-table-body").length);
    $("#contact-table-body").empty();
    readAllContacts(fillRows);
}

function fillRows(data) {
    var tbody = document.getElementById("contact-table-body");
    for (var i in data) {
        var row = masterRow.cloneNode(true);
        fillOneRow(row, data[i]);
        tbody.appendChild(row);
    }
    var total = tbody.getElementsByTagName("tr").length;
    $("#total-contacts").text(total);
}

function fillOneRow(row, input) {
    row.id = "c" + input.id;
    var cells = row.getElementsByTagName("td");
    cells[0].innerHTML = input.id;
    cells[1].innerHTML = input.firstName;
    cells[2].innerHTML = input.lastName;
    cells[3].innerHTML = input.phone;
    cells[4].innerHTML = input.email;
    cells[5].addEventListener("click", function () {
        editButtonOnClick(input);
    });
}