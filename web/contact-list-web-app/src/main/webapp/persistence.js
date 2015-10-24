/*  
 http://www.restapitutorial.com/lessons/httpmethods.html
 Create (Post, Insert), 
 Read (Get, Select)
 Update (Put)
 Delete
 */

var contactList = [
    {
        id: 101,
        firstName: "John",
        lastName: "Smith",
        phone: "111 2222",
        email: "john.smith@example.com"
    }
];

function createContact(input) {
    var contactId = contactList.length > 0 ? contactList[contactList.length - 1].id + 1 : 101;
    var contact = {
        id: contactId,
        firstName: input.firstName,
        lastName: input.lastName,
        phone: input.phone,
        email: input.email
    };
    contactList.push(contact);
    return contactId;
}

function readAllContacts(fillRowsCallback) {
    fillRowsCallback(contactList);
}

function updateContact(input) {
    // find contact by id
    var i;
    for (i = 0; i < contactList.length; i++) {
        if (contactList[i].id == input.id) {
            break;
        }
    }
    // update selected contact
    contactList[i].firstName = input.firstName;
    contactList[i].lastName = input.lastName;
    contactList[i].phone = input.phone;
    contactList[i].email = input.email;
}

function deleteContact(contactId) {
    for (i = 0; i < contactList.length; i++) {
        if (contactList[i].id == contactId) {
            contactList.splice(i, 1);
            return;
        }
    }
}
