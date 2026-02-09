<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Product page</title>

    <!-- jQuery -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

    <!-- DataTables -->
    <link href="https://cdn.datatables.net/1.13.6/css/dataTables.bootstrap5.min.css" rel="stylesheet">
    <script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.13.6/js/dataTables.bootstrap5.min.js"></script>
</head>

<body class="bg-light">

<jsp:include page="sidebar.jsp"></jsp:include>
<!-- NAVBAR -->
<nav class="navbar navbar-dark bg-dark px-3">
    <span class="navbar-text text-white">
        Welcome, <b id="loggedUser"></b>
    </span>
    <button class="btn btn-danger btn-sm" onclick="logout()">Logout</button>
</nav>

<!-- CONTENT -->
<div class="container mt-4">
    <h3>Product Management</h3>

    <table id="productTable" class="table table-striped table-bordered">
        <thead class="table-dark">
        <tr>
            <th>#</th>
            <th>Name</th>
            <th>Price</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody></tbody>
    </table>
</div>



<jsp:include page="sidebar-end.jsp"/>

<!-- EDIT USER MODAL -->
<div class="modal fade" id="editProductModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">

            <div class="modal-header">
                <h5 class="modal-title">Edit User</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>

            <div class="modal-body">
                <input type="hidden" id="editProductId">

                <div class="mb-3">
                    <label>Product</label>
                    <input type="text" id="editProductName" class="form-control">
                </div>

                <div class="mb-3">
                    <label>Price</label>
                    <input type="text" id="editProductPrice" class="form-control">
                </div>


            </div>

            <div class="modal-footer">
                <button class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                <button class="btn btn-primary" onclick="updateUser()">Update</button>
            </div>

        </div>
    </div>
</div>

<script>

    let table;
    $(document).ready(function (){
        const token = localStorage.getItem("jwtToken");
        if(!token){
            window.location.href = "sign-in";
            return;
        }

        //show logged-in username
        const payload = JSON.parse(atob(token.split('.')[1]));
        const loggedInUser = payload.sub;
        $("#loggedUser").text(payload.sub);

        //initialize DataTable
        table = $('#productTable').DataTable({
            ajax:{
                url:'product/allProduct',
                type: 'GET',
                headers:{
                    'Authorization': 'Bearer ' + token
                },
                dataSrc: ""
            },
            columns:[
                {
                    //serial number
                    data:null,
                    render:function (data,type,row,meta){
                        return meta.row + 1;
                    },

                },
                {
                    data: 'productName'
                },
                {
                    data:'productPrice', defaultContent:'-'
                },
                {
                    //for the action button
                    data:null,
                    orderable: false,
                    render: function (data,type,row){
                        var editBtn =   "<button class=\"btn btn-sm btn-primary\" " +
                            "onclick='openEditModal(" + JSON.stringify(row) + ")'>" +
                            "Edit" +
                            "</button> " ;

                        var deleteBtn = '<button class="btn btn-sm btn-danger" '+' onclick="deleteUser('+ row.userId +')" >Delete</button>';

                        return editBtn +' '+deleteBtn;

                    }
                }
            ]
        })

    })


    // ================= EDIT =================

    function openEditModal(user) {

        $("#editUserId").val(user.userId);   // REAL DB ID (hidden)
        $("#editUserName").val(user.userName);
        $("#editRole").val(user.role);

        new bootstrap.Modal(
            document.getElementById("editUserModal")
        ).show();
    }

    function updateUser() {

        const token = localStorage.getItem("jwtToken");
        const id = $("#editUserId").val();

        $.ajax({
            url: "http://localhost:8080/update/" + id,
            type: 'POST',
            headers: {
                'Authorization': 'Bearer ' + token,
                'Content-Type': 'application/json'
            },
            data: JSON.stringify({
                userName: $("#editUserName").val(),
                role: $("#editRole").val()
            }),
            success: function () {
                bootstrap.Modal.getInstance(
                    document.getElementById("editUserModal")
                ).hide();

                table.ajax.reload(null, false);
            }
        });
    }

    // ================= DELETE =================

    function deleteUser(id) {
        if (!confirm("Are you sure you want to delete this user?")) return;

        const token = localStorage.getItem("jwtToken");

        $.ajax({
            url: '/delete/'+id,
            type: 'POST',
            headers: {
                'Authorization': 'Bearer ' + token
            },
            success: function () {
                table.ajax.reload(null, false);
            }
        });
    }

    // ================= LOGOUT =================

    function logout() {
        localStorage.removeItem("jwtToken");
        window.location.href = "sign-in";
    }
</script>

</body>
</html>
