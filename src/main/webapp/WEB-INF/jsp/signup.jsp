<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Signup</title>

    <!-- jQuery (MUST be first) -->
    <script src="https://code.jquery.com/jquery-2.2.4.min.js"></script>

    <!-- Bootstrap 3 (REQUIRED for FormValidation 0.8.1) -->
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">

    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

    <!-- FormValidation 0.6.2 (jQuery plugin version) -->
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/formvalidation/0.6.2/css/formValidation.min.css">

    <script src="${pageContext.request.contextPath}/static/formValidation.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/bootstrap.min1.js"></script>


    <style>
        body {
            background: #f4f6f9;
        }
        .signup-container {
            width: 400px;
            margin: 80px auto;
            background: #fff;
            padding: 25px;
            border-radius: 6px;
            box-shadow: 0 0 10px rgba(0,0,0,.1);
        }
    </style>
</head>

<body>

<div class="signup-container">
    <h3 class="text-center">Signup</h3>

    <form id="signupForm">

        <div class="form-group">
            <label for="username">Username</label>
            <input type="text"
                   class="form-control"
                   id="username"
                   name="username"
                   placeholder="Enter username">
        </div>

        <div class="form-group">
            <label for="password">Password</label>
            <input type="password"
                   class="form-control"
                   id="password"
                   name="password"
                   placeholder="Enter password">
        </div>

        <button type="submit" class="btn btn-primary btn-block">
            Signup
        </button>

        <p id="msg" class="text-center" style="margin-top:10px;"></p>
    </form>
</div>

<script>
    function signup() {
        $.ajax({
            url: "http://localhost:8080/signup",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({
                userName: $("#username").val(),
                password: $("#password").val()
            }),
            success: function () {
                $("#msg").css("color", "green").text("Signup successful!");
                $("#signupForm")[0].reset();
            },
            error: function () {
                $("#msg").css("color", "red").text("Signup failed!");
            }
        });
    }

    $(document).ready(function () {
        $('#signupForm').formValidation({
            framework: 'bootstrap',
            icon: null,
            fields: {
                username: {
                    validators: {
                        notEmpty: {
                            message: 'Username is required'
                        },
                        stringLength: {
                            min: 3,
                            message: 'At least 3 characters'
                        },
                        remote: {
                            message: 'Username already exists',
                            url: 'http://localhost:8080/check-username',
                            type: 'GET',
                            delay: 500,
                            data: function (validator, $field, value) {
                                return {
                                    username: value
                                };
                            }
                        }
                    }
                },
                password: {
                    validators: {
                        notEmpty: {
                            message: 'Password is required'
                        },
                        stringLength: {
                            min: 3,
                            message: 'At least 3 characters'
                        }
                    }
                }
            }
        }).on('success.form.fv', function (e) {
            e.preventDefault();
            signup();
        });
    });
</script>

</body>
</html>
