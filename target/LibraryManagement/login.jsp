<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Log in</title>

        <link rel="stylesheet"
              href="static/fonts/material-icon/css/material-design-iconic-font.min.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <link rel="stylesheet" href="static/css/mystyle.css">
        <link rel="stylesheet" href="static/css/style.css">
        <link rel="stylesheet" href="alert/dist/sweetalert.css">

    </head>
    <body>
        <input type="hidden" id="status" value="${status}">
        <div class="main">
            <section class="sign-in">
                <div class="container"style="border:1px solid #bcbebf;">
                    <div class="signin-content">
                        <div class="signin-image">
                            <figure>
                                <img src="static/img/signin-image.jpg" alt="sing up image">
                            </figure>
                            <a href="signup.jsp" class="signup-image-link">Create an
                                account</a>
                        </div>

                        <div class="signin-form">
                            <h2 class="form-title" style="display: inline-block;">Log In</h2>
                            <a href="http://localhost:8081/LibraryManagement/viewIndexBooks" class="btn btn-warning btn-lg  mt-0 login-home">Home</a>
                            <form method="post" action="loginFilter" class="register-form"
                                  id="login_form" onsubmit="return loginValidation();">
                                <div class="form-group">
                                    <label for="username"><i
                                            class="zmdi zmdi-account"></i></label> <input
                                        type="text" name="username" id="username"
                                        placeholder="Username" onkeyup="clearError('name_error')"/>
                                    <span style="color:red" id="name_error"></span>
                                </div>
                                <div class="form-group">
                                    <label for="password"><i class="zmdi zmdi-lock"></i></label> <input
                                        type="password" name="password" id="password"
                                        placeholder="Password" onkeyup="clearError('password_error')"/>
                                    <span style="color:red" id="password_error"></span>
                                </div>
                                <div class="form-group form-button">
                                    <input type="submit" name="signin" id="signin"
                                           class="form-submit" value="Log in" />                                                                 
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </section>
        </div>

        <script src="vendor/jquery/jquery.min.js"></script>
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
        <script type="text/javascript">
                                            var status = document.getElementById("status").value;
                                            console.log(status);
                                            if (status == "success")
                                            {
                                                swal("Congratulations", "Account Created Successfully", "success");
                                            } else if (status == "failed")
                                            {
                                                swal("Sorry", "Email or Password Doesn't Match. Please Try Again", "error");
                                            };
        </script>
        <script src="static/js/main.js" type="text/javascript"></script>
    </body>
</html>