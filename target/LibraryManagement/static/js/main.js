
/* global Swal */

function toggleSwitch() {
    const switchElement = document.getElementById('toggleSwitch');
    const userType = document.getElementById('userType');
    const adminFields = document.getElementById('adminFields');

    if (switchElement.classList.contains('admin')) {
        switchElement.classList.remove('admin');
        userType.textContent = "User";
        adminFields.style.display = "none";
    } else {
        switchElement.classList.add('admin');
        userType.textContent = "Admin";
        adminFields.style.display = "block";
    }
}

function showAlertAndRedirect(alertText, redirectUrl) {
    Swal.fire({
        title: 'success',
        text: alertText,
        icon: 'success',
        confirmButtonText: 'OK'
    }).then((result) => {
        if (result.isConfirmed) {
            window.location.href = redirectUrl;
        }
    });
}

function showAlertAndRedirectCancel(alertText, redirectUrl) {
    Swal.fire({
        title: 'success',
        text: alertText,
        icon: 'success',
        confirmButtonText: 'OK'
    }).then((result) => {
        if (result.isConfirmed) {
            window.location.href = redirectUrl;
        }
    });
}

function searchBooks() {
    var query = document.getElementById('searchQuery').value;

    var noQueryDiv = document.getElementById('noQueryDiv');
    var resultsDiv = document.getElementById('searchResults');

    if (query.trim() !== "") {
        noQueryDiv.style.display = 'none';
        resultsDiv.style.display = 'block';

        console.log(query);

        $.ajax({
            url: 'SearchBooksServlet', // The servlet that will handle the search
            type: 'GET', // HTTP method (GET for search query)
            data: {search: query}, // Send the search query to the servlet
            success: function (response) {
                // On success, display the results in the 'searchResults' div
                document.getElementById('searchResults').innerHTML = response;
            },
            error: function (xhr, status, error) {
                console.error("Error: " + error);
            }
        });
    } else {
        noQueryDiv.style.display = 'block';
        resultsDiv.style.display = 'none';

        document.getElementById('searchResults').innerHTML = '';
    }
}

function searchBooks1() {
    var query = document.getElementById('searchQuery').value;
    if (query.trim() !== "") {
        console.log(query);
        $.ajax({
            url: 'bookRequestedByUser',
            type: 'GET',
            data: {search: query},
            success: function (response) {
                document.getElementById('searchResults').innerHTML = response;
            },
            error: function (xhr, status, error) {
                console.error("Error: " + error);
            }
        });
    } else {
        document.getElementById('searchResults').innerHTML = '';
    }
}

function clearError(elementId) {
    document.getElementById(elementId).innerHTML = "";
}

function validation() {
    var flag = true;

    var user_name = document.register_form.name.value;
    var user_email = document.register_form.email.value;
    var user_password = document.register_form.pass.value;
    var user_repassword = document.register_form.re_pass.value;
    var user_agree = document.register_form["agree-term"].checked;
    var library_name = document.register_form.library_name.value;
    var address = document.register_form.address.value;

    var name_pattern = /^[a-zA-Z ]{3,30}$/;
    var email_pattern = /^[a-zA-Z0-9]+([a-zA-Z0-9._+-])*[a-zA-Z0-9]+@[a-zA-Z0-9-]+(\.[a-zA-Z]{2,4})+$/;
    var password_pattern = /^(?=.*\d)(?=.*[!@#$%^&*])(?=.*[A-Z])(?=.*[a-z]).{8,}$/;

    document.getElementById("name_error").innerHTML = "";
    document.getElementById("email_error").innerHTML = "";
    document.getElementById("password_error").innerHTML = "";
    document.getElementById("repassword_error").innerHTML = "";
    document.getElementById("agree_error").innerHTML = "";
    document.getElementById("library_name_error").innerHTML = "";
    document.getElementById("address_error").innerHTML = "";

    if (user_name === "" || !name_pattern.test(user_name)) {
        document.getElementById("name_error").innerHTML = "Please enter a valid name (3-30 characters).";
        flag = false;
    }

    if (user_email === "" || !email_pattern.test(user_email)) {
        document.getElementById("email_error").innerHTML = "Please enter a valid email address.";
        flag = false;
    }

    if (user_password === "" || !password_pattern.test(user_password)) {
        document.getElementById("password_error").innerHTML = "Password must contain at least one number, one uppercase letter, and one special character and min length 8.";
        flag = false;
    }

    if (user_repassword !== user_password) {
        document.getElementById("repassword_error").innerHTML = "Passwords do not match.";
        flag = false;
    }

    if (!user_agree) {
        document.getElementById("agree_error").innerHTML = "You must agree to the terms of service.";
        flag = false;
    }

    if (document.getElementById("userType").innerHTML === "Admin") {
        if (library_name === "" || library_name === null) {
            document.getElementById("library_name_error").innerHTML = "Library name cannot be empty.";
            flag = false;
        }

        if (address === "" || address === null) {
            document.getElementById("address_error").innerHTML = "Address cannot be empty.";
            flag = false;
        }
    }

    return flag;
}

function loginValidation() {
    var username = document.getElementById("username").value.trim();
    var password = document.getElementById("password").value.trim();
    var isValid = true;

    clearError('name_error');
    clearError('password_error');

    if (username === "") {
        document.getElementById('name_error').textContent = "Username is required.";
        isValid = false;
    }

    if (password === "") {
        document.getElementById('password_error').textContent = "Password is required.";
        isValid = false;
    }

    return isValid;
}

function clearError(errorId) {
    document.getElementById(errorId).textContent = "";
}

function toggleSubmitButton() {
    var username = document.getElementById("username").value.trim();
    var password = document.getElementById("password").value.trim();
    var submitButton = document.getElementById("signin");

    if (username !== "" && password !== "") {
        submitButton.disabled = false;
    } else {
        submitButton.disabled = true;
    }
}

