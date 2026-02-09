<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Login</title>


    <!-- jQuery -->
<%--    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>--%>
    <script src="https://code.jquery.com/jquery-2.2.4.min.js"></script>



    <!-- FormValidation plugin (old version) -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/formvalidation/0.8.1/css/formValidation.min.css"/>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/formvalidation/0.8.1/js/formValidation.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/formvalidation/0.8.1/js/framework/bootstrap.min.js"></script>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">


    <link rel="stylesheet" type="text/css" href="static/css/login.css">
</head>
<body>

<div class="login-container">
    <h2>Login</h2>

    <form id="loginForm" onsubmit="event.preventDefault(); login();">
        <label for="username">Username</label>
        <input type="text" id="username" placeholder="Username" required><br><br>

        <label for="password">Password</label>
        <input type="password" id="password" placeholder="Password" required><br><br>

        <button type="submit">Login</button>

        <p id="msg"></p>
    </form>
</div>

<script>
    function login() {
        console.log($("#username").val(), $("#password").val());

        $.ajax({
            url: "http://localhost:8080/login",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({
                userName: $("#username").val(),
                password: $("#password").val()
            }),
            success: function (token) {
                localStorage.setItem("jwtToken", token);
                window.location.href = "Dashboard";
            },
            error: function () {
                $("#msg").text("Invalid credentials!");
            }
        });
    }


</script>

</body>
</html>
