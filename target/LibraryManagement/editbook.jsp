<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> 
<%@ include file="navbar.jsp" %>
<c:if test="${not empty user and user.role == 'user'}">
    <jsp:forward page="viewIndexBooks" />
</c:if>
<%response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <link rel="stylesheet" href="static/css/mystyle.css">
        <link rel="stylesheet" href="alert/dist/sweetalert.css">
    </head>
    <body>
        <input type="hidden" id="status" value="${status}">
        <input type="hidden" id="failed_message" value="${failed_message}">
         <c:if test="${empty sessionScope.bookId}">
            <c:set var="bookId" value="${param.bookId}" scope="session" />
            <c:set var="bookName" value="${param.bookName}" scope="session" />
            <c:set var="bookAuthor" value="${param.bookAuthor}" scope="session" />
            <c:set var="bookEdition" value="${param.bookEdition}" scope="session" />
            <c:set var="bookQuantity" value="${param.bookQuantity}" scope="session" />
        </c:if>
        <div class="container-fluid m-3" style="min-height: 500px;">
            <div class="container mt-5" style="max-width: 500px;">
                <div class="card">
                    <div class="card-body">
                        <h1 class="card-title text-center mb-4">Edit Book</h1>
                        <form method="POST" action="bookEdit" enctype="multipart/form-data">
                            <div class="form-label">
                                <label for="bookName" class="form-label">Change Book Name</label>
                                <input type="text" class="form-control" name="bookName" id="bookName" placeholder="${sessionScope.bookName}" minlength="3">
                            </div>
                            <div class="form-label">
                                <label for="authorName" class="form-label">Change Book Author</label>
                                <input type="text" class="form-control" name="bookAuthor" id="authorName" placeholder="${sessionScope.bookAuthor}" minlength="3">
                            </div>
                            <div class="form-label">
                                <label for="edition" class="form-label">Change Book Edition (ex:- 1st Edition) </label>
                                <input type="text" class="form-control" name="bookEdition" id="edition" placeholder="${sessionScope.bookEdition}" minlength="3">
                            </div>
                            <div class="form-label">
                                <label for="quantity" class="form-label">Change Book Quantity</label>
                                <input type="number" class="form-control" name="bookQuantity" id="quantity" placeholder="${sessionScope.bookQuantity}" min="1" max="100">
                            </div>
                            <div class="form-label">
                                <input type="hidden" name="bookId" value="${sessionScope.bookId}" />
                            </div>
                            <div class="form-label">
                                <label for="imageUpload" class="form-label">Change Book Image</label>
                                <input type="file" class="form-control" name="bookImage" id="imageUpload" accept="image/*" >
                            </div>
                            <div class="form-label text-center mt-4">
                                <button type="submit" class="btn btn-primary btn-block">Update Book</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script> 
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
        <script type="text/javascript">
            var status = document.getElementById("status").value;
            console.log(status);
            if (status === "success" && !localStorage.getItem('alertShown')) {
                swal("Congratulations", "Book Updated Successfully", "success")
                        .then(() => {
                            // Redirect after the alert is closed
                            window.location.href = "http://localhost:8081/LibraryManagement/viewIndexBooks";
                        });
                localStorage.setItem('alertShown', 'true');
            } else if (status === "failed") {
                if (failed_message.length !== 0) {
                    swal("Sorry", "${failed_message}", "error")
                            .then(() => {
                                // Redirect after the alert is closed
                                window.location.href = "http://localhost:8081/LibraryManagement/editbook.jsp";
                            });
                } else {
                    swal("Sorry", "Unable To Update Book. Please Try Again", "error").then(() => {
                        // Redirect after the alert is closed
                        window.location.href = "http://localhost:8081/LibraryManagement/editbook.jsp";
                    });
                }

            }
            localStorage.removeItem('alertShown');

        </script>
        <%@ include file="footer.jsp" %>
    </body>
</html>
