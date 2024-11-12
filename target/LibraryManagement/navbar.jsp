<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");%>
<!doctype html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <link rel="stylesheet" href="static/css/mystyle.css">
    </head>
    <body>
        <header class="p-2 text-white" style="background-color: #57467B;">
            <div class="container">
                <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
                    <a href="http://localhost:8081/LibraryManagement/viewIndexBooks" class="d-flex align-items-center mb-2 pe-2 mb-lg-0 text-white text-decoration-none">
                        <img class="nav-home-icon" src="static/icons/home10.png" alt="Home" height="50">
                    </a>

                    <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                        <c:choose>
                            <c:when test="${not empty user and user.role == 'admin'}">
                                <li><a href="addbook.jsp" class="nav-link px-2 text-white">Add Books</a></li>
                                <li><a href="issueBooks" class="nav-link px-2 text-white">Issue Books</a></li>
                                </c:when>
                                <c:when test="${not empty user and user.role == 'user'}">
                                <li><a href="bookIssued?source=books_issued" class="nav-link px-2 text-white">Books Issued</a></li>
                                <li><a href="returnBookList?pageName=renew_books" class="nav-link px-2 text-white">Renew Books</a></li>
                                <li><a href="returnBookList?" class="nav-link px-2 text-white">Return Books</a></li>
                                <li><a href="requestedBooks" class="nav-link px-2 text-white">Cancel Book Request</a></li>
                                <li><a href="bookrequested.jsp" class="nav-link px-2 text-white">Books Requested</a></li>
                                </c:when>
                                <c:otherwise>
                                </c:otherwise>
                            </c:choose>
                    </ul>

                    <c:choose>
                        <c:when test="${not empty user}">
                            <div>
                                <div class="text-end text-center">
                                    <img class="nav-home-icon" src="static/icons/man.png" alt="Home" height="50">
                                </div>
                                <div>
                                    <span style="font-family: sans-serif; color: #ffffff;">${user.memberId} </span><span style="color: darkgray;">(${user.role.toUpperCase()})</span>
                                </div>
                            </div>
                            <a href="logoutFilter"><button type="button" class="btn btn-warning ms-5">Sign-out</button></a>
                        </c:when>
                        <c:otherwise>
                            <div class="text-end">
                                <a href="login.jsp"><button type="button" class="btn btn-outline-light me-2">Login</button></a>
                                <a href="signup.jsp"><button type="button" class="btn btn-warning">Sign-up</button></a>
                            </div>
                        </c:otherwise>
                    </c:choose>

                </div>
            </div>
        </header>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    </body>
</html>