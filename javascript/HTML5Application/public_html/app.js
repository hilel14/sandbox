/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
}

function fillOneRow(row, i) {
    //window.alert(i);
    var cells = row.getElementsByTagName("td");
    cells[0].innerHTML = addressList[i].id;
    cells[1].innerHTML = addressList[i].first_name;
    cells[2].innerHTML = addressList[i].last_name;
    cells[3].innerHTML = addressList[i].phone;
    cells[4].innerHTML = addressList[i].email;
    cells[5].innerHTML = new Date();
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
        first_name: "John",
        last_name: "Smith",
        phone: "333 4444",
        email: "john.smith@example.org"
    },
    {
        id: 103,
        first_name: "John",
        last_name: "Smith",
        phone: "555 6666",
        email: "john.smith@example.net"
    }
];