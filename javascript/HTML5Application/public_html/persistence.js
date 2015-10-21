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
        first_name: "John",
        last_name: "Smith",
        phone: "111 2222",
        email: "john.smith@example.com"
    }
];

function createContact(input) {
    var contactId = contactList.length > 0 ? contactList[contactList.length - 1].id + 1 : 101;
    var contact = {
        id: contactId,
        first_name: input.first_name,
        last_name: input.last_name,
        phone: input.phone,
        email: input.email
    };
    contactList.push(contact);
}

function readAllContacts() {
    return contactList;
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
    contactList[i].first_name = input.first_name;
    contactList[i].last_name = input.last_name;
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
