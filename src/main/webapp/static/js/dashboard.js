const token = localStorage.getItem("jwtToken");

if (!token) {
    window.location.href = "sign-in";
}

// ================= JWT =================

function isTokenExpired(token) {
    const payload = JSON.parse(atob(token.split('.')[1]));
    return payload.exp * 1000 < Date.now();
}

function authHeader() {
    if (!token || isTokenExpired(token)) {
        logout();
        return {};
    }
    return {
        "Authorization": "Bearer " + token
    };
}

// show logged user
const payload = JSON.parse(atob(token.split('.')[1]));
$("#loggedUser").text(payload.sub);

// ================= LOAD USERS =================

let allUsers = [];
let currentPage = 1;
const pageSize = 5;

$(document).ready(function () {
    loadUsers();
});

function loadUsers() {
    $.ajax({
        url: "http://localhost:8080/allUsers",
        type: "GET",
        headers: authHeader(),
        success: function (users) {
            allUsers = users;
            renderTable();
        },
        error: function (err) {
            console.error("Error loading users", err);
        }
    });
}

// ================= SEARCH =================

$("#searchBox").on("keyup", function () {
    currentPage = 1;
    renderTable();
});

// ================= RENDER TABLE =================

function renderTable() {
    const search = $("#searchBox").val().toLowerCase();

    const filtered = allUsers.filter(u =>
        u.userName.toLowerCase().includes(search) ||
        u.role.toLowerCase().includes(search)
    );

    const start = (currentPage - 1) * pageSize;
    const pageUsers = filtered.slice(start, start + pageSize);

    let rows = "";
    pageUsers.forEach(user => {
        rows += `
        <tr>
            <td>${user.userId}</td>
            <td>${user.userName}</td>
            <td>${user.role}</td>
            <td>
                <button class="btn btn-sm btn-primary" onclick="editUser(${user.userId})">Edit</button>
                <button class="btn btn-sm btn-danger" onclick="deleteUser(${user.userId})">Delete</button>
            </td>
        </tr>`;
    });

    $("#userTable tbody").html(rows);
    renderPagination(filtered.length);
}

// ================= PAGINATION =================

function renderPagination(total) {
    const pages = Math.ceil(total / pageSize);
    let html = "";

    for (let i = 1; i <= pages; i++) {
        html += `
        <li class="page-item ${i === currentPage ? 'active' : ''}">
            <a class="page-link" href="#" onclick="gotoPage(${i})">${i}</a>
        </li>`;
    }

    $("#pagination").html(html);
}

function gotoPage(page) {
    currentPage = page;
    renderTable();
}

// ================= EDIT USER =================

function editUser(id) {
    $.ajax({
        url: `http://localhost:8080/${id}`,
        type: "GET",
        headers: authHeader(),
        success: function (user) {
            $("#editUserId").val(user.userId);
            $("#editUserName").val(user.userName);
            $("#editRole").val(user.role);

            new bootstrap.Modal(
                document.getElementById("editUserModal")
            ).show();
        }
    });
}

function updateUser() {
    const id = $("#editUserId").val();

    $.ajax({
        url: `http://localhost:8080/update/${id}`,
        type: "POST",
        headers: authHeader(),
        contentType: "application/json",
        data: JSON.stringify({
            userName: $("#editUserName").val(),
            role: $("#editRole").val()
        }),
        success: function () {
            bootstrap.Modal.getInstance(
                document.getElementById("editUserModal")
            ).hide();
            loadUsers();
        }
    });
}

// ================= DELETE =================

function deleteUser(id) {
    if (!confirm("Are you sure?")) return;

    $.ajax({
        url: `http://localhost:8080/delete/${id}`,
        type: "POST",
        headers: authHeader(),
        success: function () {
            loadUsers();
        }
    });
}

// ================= GLOBAL ERROR =================

$(document).ajaxError(function (event, jqxhr) {
    if (jqxhr.status === 401) {
        alert("Session expired. Please login again.");
        logout();
    }
});

// ================= LOGOUT =================

function logout() {
    localStorage.removeItem("jwtToken");
    window.location.href = "sign-in";
}
