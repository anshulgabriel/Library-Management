//register page coding starts here

//    getting values from the input form using name attribute
function validation() {

    var flag = true;

    var user_name = document.register_form.name.value;
    var user_email = document.register_form.user_email.value;
    var user_gender = document.register_form.gender1.value;
    var user_field = document.getElementsByName('field1');
    var user_city = document.register_form.user_city.value;
    var user_password = document.register_form.user_password.value;
    
    
}

//    var field = [];
//    for (var i = 0; i < user_field.length; i++) {
//        if (user_field[i].checked === true) {
//            field.push(user_field[i].value);
//        }
//    }
//
//
//    //creating RegEx pattern
//    var name_pattern = /^[a-zA-Z ]{3,30}$/;
//    var email_pattern = /^([a-zA-Z0-9])(([a-zA-Z0-9])*([\._\+-])*([a-zA-Z0-9]))*@(([a-zA-Z0-9\-])+(\.))+([a-zA-Z]{2,4})+$/;
//    var password_pattern = /(?=.*\d)(?=.*[!@#$%^&*]+)(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$/;
//
//
//    if (user_name === "" || user_name === null) {
////        alert("Name cannot be empty!");
//        document.getElementById("name_error").style.color = "red";
//        document.getElementById("name_error").innerHTML = "Name cannot be empty!";
//        flag = false;
//
//    } else if (user_name.match(/([0-9])*([!@#$%^&*()-=+_])/)) {
//        document.getElementById("name_error").style.color = "red";
//        document.getElementById("name_error").innerHTML = "Name cannot contain numbers or special charaters";
//    } else if (!(user_name.match(name_pattern))) {
//        document.getElementById("name_error").style.color = "red";
//        document.getElementById("name_error").innerHTML = "Name should be more than 3 characters";
//        flag = false;
//    }
//
//    if (user_email === "" || user_email === null) {
////        alert("Email cannot be empty!");
//        document.getElementById("email_error").style.color = "red";
//        document.getElementById("email_error").innerHTML = "Email cannot be empty!";
//        flag = false;
////        return false;
//    } else if (!(user_email.match(email_pattern))) {
//        document.getElementById("email_error").style.color = "red";
//        document.getElementById("email_error").innerHTML = "Email is not entered correctly!";
//        flag = false;
//    }
//
//    if (user_gender === "" || user_gender === null) {
////        alert("Please select gender!");
//        document.getElementById("gender_error").style.color = "red";
//        document.getElementById("gender_error").innerHTML = " &nbsp;&nbsp;Please select gender!";
//        flag = false;
////        return false;
//    }
//
//    if (field === "" || field === null || field.length === 0) {
////        alert("Please select atleast one field!");
////        return false;
//        document.getElementById("field_error").style.color = "red";
//        document.getElementById("field_error").innerHTML = " Please select atleast one field!";
//        flag = false;
//
//    }
//    if (user_city === "Select City" || user_city === null) {
////        alert("Please select your city!");
////        return false;
//        document.getElementById("city_error").style.color = "red";
//        document.getElementById("city_error").innerHTML = "Please select your city!";
//        flag = false;
//    }
//    if (user_password === "" || user_password === null) {
////        alert("Password cannot be empty!");
////        return false;
//        document.getElementById("password_error").style.color = "red";
//        document.getElementById("password_error").innerHTML = "Password cannot be empty!";
//        flag = false;
//    } else if (user_password.length < 6) {
//        document.getElementById("password_error").style.color = "red";
//        document.getElementById("password_error").innerHTML = "Password should contain atleast 6 characters!";
//        flag = false;
//    } else if (!(user_password.match(password_pattern))) {
//        document.getElementById("password_error").style.color = "red";
//        document.getElementById("password_error").innerHTML = "Please include atleast one number, one special character and one uppercase character!";
//        flag = false;
//    }
//
//    return flag;
//
//
//}
//
//function removeSpan(e) {
//    document.getElementById(e).innerHTML = "";
//}
//
//function removeSpan1(e) {
//
//    var user_gender = document.getElementById("user_gender");
//    var user_gender1 = document.getElementById("user_gender1");
//
//    var user_field1 = document.getElementById("user_field1");
//    var user_field2 = document.getElementById("user_field2");
//    var user_field3 = document.getElementById("user_field3");
//
//    var user_city = document.getElementById("user_city1");
//
//
//    if (user_gender.checked || user_gender1.checked) {
//        document.getElementById(e).innerHTML = "";
//    }
//
//    if (user_field1.checked || user_field2.checked || user_field3.checked) {
//        document.getElementById(e).innerHTML = "";
//    }
//
//    if (!(user_city.value === "Select City")) {
//        document.getElementById(e).innerHTML = "";
//    }
//}
//
////register page coding ends here
//
//
////profile page coding starts here
//
//function deleteExperience(id1) {
//
//    var confirming = confirm("Do you really want to delete this");
//
//    if (confirming === true) {
//        window.location = "deletexperience?id=" + id1 + "";
//    } else {
//        return false;
//    }
//
//}
//
//function deleteEducation(id) {
//
//    var confirming = confirm("Do you really want to delete this");
//
//    if (confirming === true) {
//        window.location = "deleteeducation?id=" + id + "";
//    } else {
//        return false;
//    }
//
//}
//
////search jobs by job profile
//function val(job_profile) {
//    
//    var obj;
//    
//    //1. create an XMLHttpRequest object
//    if(window.XMLHttpRequest) {
//        
//        obj = new XMLHttpRequest();
//        
//    } else {
//        
//        obj = new ActiveXObject("Microsoft.XMLHTTP");
//        
//    }
//    
//    //2. specify the object
//    obj.open("POST", "SimpleJobSearch?technology="+job_profile, true);
//    
//    //3. sending the request to the server
//    obj.send();
//    
//    //4. calls the function when the readystate propery changes
//    obj.onreadystatechange = function() 
//    {
//        if(obj.readyState === 4 && obj.status === 200) 
//        {
//            if(job_profile === "" || job_profile === null) {
//                
//                document.getElementById("respregenerated").innerHTML = "";
//                
//            } else {
//                
//                document.getElementById("respregenerated").innerHTML = obj.responseText;
//            }
//            
//        }
//    };
//}
//
////search jobs by location
//function searchJobsByLocation(location) {
//    
//    var obj;
//    
//    //1. create an XMLHttpRequest object
//    if(window.XMLHttpRequest) {
//        
//        obj = new XMLHttpRequest();
//        
//    } else {
//        
//        obj = new ActiveXObject("Microsoft.XMLHTTP");
//        
//    }
//    
//    //2. specify the object
//    obj.open("POST", "SearchJobsByLocation?location="+location, true);
//    
//    //3. sending the request to the server
//    obj.send();
//    
//    //4. calls the function when the readystate propery changes
//    obj.onreadystatechange = function() 
//    {
//        if(obj.readyState === 4 && obj.status === 200) 
//        {
//            if(location === "" || location === null) {
//                
//                document.getElementById("respregenerated").innerHTML = "";
//                
//            } else {
//                
//                document.getElementById("respregenerated").innerHTML = obj.responseText;
//            }
//            
//        }
//    };
//}
//
//
////profile page coding ends here
//
