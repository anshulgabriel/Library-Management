<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="navbar.jsp" %>
<c:if test="${not empty user and user.role == 'user'}">
    <jsp:forward page="index.jsp" />
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
        <div class="container-fluid m-3" style="min-height: 500px;">
            <div class="container mt-5" style="max-width: 500px;">
                <div class="card">
                    <div class="card-body">
                        <h1 class="card-title text-center mb-4">Add Book</h1>
                        <form method="Post" action="addBook" enctype="multipart/form-data">
                            <div class="form-label">
                                <label for="bookName" class="form-label">Book Name* (50 Characters Max)</label>
                                <input type="text" class="form-control" name="book_name" id="bookName" placeholder="Mention book name" minlength="3" required>
                            </div>
                            <div class="form-label">
                                <label for="authorName" class="form-label">Book Author* (20 Characters Max)</label>
                                <input type="text" class="form-control" name="book_author" id="authorName" placeholder="Mention book's author" minlength="3" required>
                            </div>
                            <div class="form-label">
                                <label for="edition" class="form-label">Book Edition* (ex:- 1st Edition) (20 Characters Max) </label>
                                <input type="text" class="form-control" name="book_edition" id="edition" placeholder="Mention book's edition" required minlength="3">
                            </div>
                            <div class="form-label">
                                <label for="quantity" class="form-label">Book Quantity</label>
                                <input type="number" class="form-control" name="book_quantity" id="quantity" placeholder="Mention book's quantity" min="1" max="100">
                            </div>
                            <div class="form-label">
                                <label for="imageUpload" class="form-label">Upload Book Image</label>
                                <input type="file" class="form-control" name="book_image" id="imageUpload" accept="image/*" >
                            </div>
                            <div class="form-label text-center mt-4">
                                <button type="submit" class="btn btn-primary btn-block">Add Book</button>
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
                swal("Congratulations", "Book Added Successfully", "success")
                        .then(() => {
                            // Redirect after the alert is closed
                            window.location.href = "http://localhost:8081/LibraryManagement/addbook.jsp";
                        });
                localStorage.setItem('alertShown', 'true');
            } else if (status === "failed") {
                swal("Sorry", "Something Went Wrong. Please Try Again", "error");
            }
            localStorage.removeItem('alertShown');

        </script>
    </body>
</html>
<%@ include file="footer.jsp" %>
