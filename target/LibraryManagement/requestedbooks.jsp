<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="com.webkorps.library.models.Book"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="navbar.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");%>
<c:if test="${not empty user and user.role == 'admin'}">
    <jsp:forward page="viewIndexBooks" />
</c:if>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <link rel="stylesheet" href="static/css/mystyle.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css">
    </head>
    <body>
        <input type="hidden" id="status" value="${status}">
        <div class="container-fluid m-3 text-center" style="min-height: 500px;">
            <h1 class="text-center">Books List</h1>
            <c:if test="${not empty bookList}">
                <c:forEach var="book" items="${bookList}">
                    <c:if test="${not empty user and user.role == 'user'}">
                        <div class="card fixed-card">
                            <img src="${pageContext.request.contextPath}/static/bookimg/${book.getBookImageName()}" class="card-img-top" alt="..." style="height:200px;">
                            <div class="card-body " style="height:140px; width: 18rem;">
                                <h5 class="card-title" >${book.getBookName()}</h5>
                                <p class="card-text">
                                <p><b>Author:</b> ${book.getBookAuthor()}<br/>
                                    <b>Edition: </b> ${book.getBookEdition()}</p>
                                </p>
                            </div>
                            <c:if test="${not empty page_status and page_status == 'requestedbooks.jsp'}">  
                                <div class="text-center">
                                    <button class="btn btn-warning" onclick="showAlertAndRedirect('Thank You For Returning This Book!', 'returnBook?bookId=${book.getBookId()}&memberId=${user.memberId}')">Return This Book</button>
                                </div>
                            </c:if>
                            <c:if test="${not empty page_status and page_status == 'renew_books.jsp'}"> 
                                <div class="mt-2 mb-4">
                                    <label>Renew this book 2 days before the return date.</label>
                                </div>
                                <div class="text-center mb-4">
                                    <label></label>
                                    <a href="renewBook?bookId=${book.getBookId()}" class="btn btn-warning">Renew This Book</a>
                                </div>
                            </c:if>
                            <c:if test="${not empty page_status and page_status == 'true'}">  
                                <div class="text-center">
                                    <button class="btn btn-warning" onclick="showAlertAndRedirect('We Have Cancel The Request Of This Book!', 'cancelBookRequest?bookId=${book.getBookId()}&memberId=${sessionScope.user.memberId}')">Cancel Request</button>
                                </div>
                            </c:if>
                            <c:if test="${not empty page_status and page_status == 'books_issued'}">  
                                <div class="text-center ">
                                    <c:forEach var="details" items="${bookDetails}">
                                        <c:if test="${details.bookId == book.bookId}">
                                            <div>
                                                <label><b>Issue Date:</b> ${details.issueDate}</label><br/>
                                                <label><b>Return Date:</b> ${details.returnDate}</label>
                                                <c:if test="${details.returnedDate != 'Not Returned'}">
                                                    <label><b>Returned Date:</b>
                                                        <fmt:parseDate value="${details.returnedDate}" pattern="yyyy-MM-dd" var="parsedDate" />
                                                        <fmt:formatDate value="${parsedDate}" pattern="dd/MMM/yyyy" />
                                                    </label>
                                                </c:if>
                                            </div>
                                        </c:if>
                                    </c:forEach>
                                </div>
                                <div class="text-center mt-3 mb-3">
                                    <a href="#" class="btn btn-success disabled">Approved</a>
                                </div>
                            </c:if>
                        </c:if>
                    </div>
                </c:forEach>
            </c:if>

            <c:if test="${empty bookList}">
                <p>No books available.</p>
            </c:if>                    
            <nav aria-label="Page navigation">
                <ul class="pagination justify-content-center mt-5">
                    <li class="page-item">
                        <c:if test="${currentPage > 1}">
                            <a class="page-link" href="viewBooks?page=${currentPage - 1}" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                                <span>Previous</span>
                            </a>
                        </c:if>
                    </li>
                    <c:forEach begin="1" end="${totalPages}" var="page">
                        <li class="page-item ${page == currentPage ? 'active' : ''}">
                            <c:choose>
                                <c:when test="${page == currentPage}">
                                    <span class="page-link">${page}</span>
                                </c:when>
                                <c:otherwise>
                                    <a class="page-link" href="viewBooks?page=${page}">${page}</a>
                                </c:otherwise>
                            </c:choose>
                        </li>
                    </c:forEach>
                    <li class="page-item">
                        <c:if test="${currentPage < totalPages}">
                            <a class="page-link" href="viewBooks?page=${currentPage + 1}" aria-label="Next">
                                <span>Next</span>
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </c:if>
                    </li>
                </ul>
            </nav>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script> 
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script type="text/javascript">

                                        var status = document.getElementById("status").value;
                                        console.log(status);

                                        if (status === "success" && !localStorage.getItem('alertShown')) {
                                            // Show the success alert with SweetAlert2
                                            Swal.fire({
                                                title: "Congratulations",
                                                text: "Book Renewed Successfully",
                                                icon: "success",
                                                confirmButtonText: "Ok"
                                            }).then(() => {
                                                // Redirect after the alert is closed
                                                window.location.href = "http://localhost:8081/LibraryManagement/returnBookList?pageName=renew_books";
                                            });
                                            // Set the flag to ensure the alert isn't shown again
                                            localStorage.setItem('alertShown', 'true');
                                        } else if (status === "failed") {
                                            // Show the error alert with SweetAlert2
                                            Swal.fire({
                                                title: "Sorry",
                                                text: "Cannot Renew This Book",
                                                icon: "error",
                                                confirmButtonText: "Ok"
                                            });
                                        }

        </script>
        <script src="static/js/main.js"type="text/javascript"></script>
    </body>
</html>
<%@ include file="footer.jsp" %>
