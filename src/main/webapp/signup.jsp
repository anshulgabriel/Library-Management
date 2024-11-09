<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Sign Up</title>

        <link rel="stylesheet"href="static/fonts/material-icon/css/material-design-iconic-font.min.css">
        <link rel="stylesheet" href="static/css/style.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <link rel="stylesheet" href="static/css/mystyle.css">
        <link rel="stylesheet" href="alert/dist/sweetalert.css">
    </head>
    <body>
        <input type="hidden" id="status" value="${status}">
        <input type="hidden" id="register_message" value="${register_message}">

        <div class="main">
            <section class="signup">
                <div class="container" style="border:1px solid #bcbebf;">
                    <div class="signup-content">
                        <div class="signup-form signup-text ">
                            <h1 class="form-title" style="display:inline-block; padding-right: 10px;">Sign Up</h1>
                            <a href="http://localhost:8081/LibraryManagement/index.jsp" class="btn btn-warning btn-lg login-home">Home</a>
                            <div class="toggle-switch ms-2" id="toggleSwitch" onclick="toggleSwitch()">
                                <span id="toggleText"></span>
                            </div>
                            <span id="userType" name="user_type">User</span>
                            <form method="post" action="registerFilter" class="register-form" id="register-form" name="register_form" onsubmit="return validation();">
                                <div class="form-group">
                                    <label for="name"><i class="zmdi zmdi-account material-icons-name"></i></label>
                                    <input type="text" name="name" id="name" placeholder="Your Name" onkeyup="clearError('name_error')"/>
                                    <span style="color:red" id="name_error"></span>
                                </div>

                                <div id="adminFields" style="display: none;">
                                    <div class="form-group">
                                        <label for="library_name"><i class="zmdi zmdi-library material-icons-name"></i></label>
                                        <input type="text" name="library_name" id="library_name" placeholder="Library Name"  onkeyup="clearError('library_name_error')"/>
                                        <span style="color:red" id="library_name_error"></span>
                                    </div>
                                    <div class="form-group">
                                        <label for="address"><i class="zmdi zmdi-home material-icons-name" style="margin-bottom: 25px;"></i></label>
                                        <input type="text" name="address" id="address" placeholder="Address" onkeyup="clearError('address_error')"/>
                                        <span style="color:red" id="address_error"></span><br/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="email"><i class="zmdi zmdi-email"></i></label>
                                    <input type="email" name="email" id="email" placeholder="Your Email" onkeyup="clearError('email_error')"/>
                                    <span style="color:red" id="email_error"></span>
                                </div>

                                <div class="form-group">
                                    <label for="pass"><i class="zmdi zmdi-lock"></i></label>
                                    <input type="password" name="pass" id="pass" placeholder="Password" onkeyup="clearError('password_error')"/>
                                    <span style="color:red" id="password_error"></span>
                                </div>

                                <div class="form-group">
                                    <label for="re_pass"><i class="zmdi zmdi-lock-outline"></i></label>
                                    <input type="password" name="re_pass" id="re_pass" placeholder="Repeat your password" onkeyup="clearError('repassword_error')" />
                                    <span style="color:red" id="repassword_error"></span>
                                </div>

                                <div class="form-group">
                                    <input type="checkbox" name="agree-term" id="agree-term" class="agree-term" onchange="clearError('agree_error')" />
                                    <label for="agree-term" class="label-agree-term">
                                        <span><span></span></span>I agree to all statements in 
                                        <a href="#" class="term-service">Terms of Service</a>
                                    </label><br/>
                                    <span style="color:red" id="agree_error"></span>
                                </div>

                                <div class="form-group form-button">
                                    <input type="submit" name="signup" id="signup" class="form-submit" value="Register" />
                                </div>
                            </form>
                        </div>
                        <div class="signup-image">
                            <figure>
                                <img src="static/img/signup-image.jpg" alt="sing up image">
                            </figure>
                            <br/><a href="login.jsp" class="signup-image-link"><h3>I am already
                                    member</h3></a>
                        </div>
                    </div>
                </div>
            </section>


        </div>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
        <script src="static/js/main.js"></script>
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
        <script type="text/javascript">
                                        var status = document.getElementById("status").value;
                                        var register_message = document.getElementById("register_message").value;
                                        console.log(status);
                                        if (status === "success" && !localStorage.getItem('alertShown')) {
                                            swal("Congratulations", register_message, "success")
                                                    .then(() => {
                                                        // Redirect after the alert is closed
                                                        window.location.href = "http://localhost:8081/LibraryManagement/index.jsp";
                                                    });
                                            localStorage.setItem('alertShown', 'true');
                                        } else if (status === "failed") {
                                            swal("Sorry", register_message, "error");
                                        }
                                        localStorage.removeItem('alertShown');

        </script>
    </body>
</html>