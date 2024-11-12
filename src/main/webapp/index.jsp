<%@page import="com.webkorps.library.models.User"%>
<%@page import="com.webkorps.library.models.Book"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="navbar.jsp" %>

<%response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");%>
<!doctype html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <link rel="stylesheet" href="static/css/mystyle.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css">
        <title>Library Management System</title>
    </head>
    <body>
        <% User user = (User) session.getAttribute("user");%>
        <%
            if (user != null) {
        %>
        <div class="jumbotron pt-5 jumbotron-fluid text-center jumbotron-main">
            <div class="container">
                <h1 class="display-4">Search Your Books Here</h1>
                <p class="lead">Nothing is pleasanter than exploring a library.</p>
                <div class="container full-height w-50 justify-content-center align-items-center mt-3 mb-3">
                    <div class="d-flex justify-content-center">
                        <div class="input-group">
                            <input type="text" class="form-control" id="searchQuery" placeholder="Search by book name, author..."  onkeyup="searchBooks()">
                        </div>
                    </div>
                </div>
            </div>
            <hr class="custom-hr">
        </div>

        <div id="searchResults" style="width: 100%;"></div>

        <div class=" jumbotron jumbotron-fluid text-center" id="noQueryDiv">
            <c:if test="${not empty bookList}">

                <c:forEach var="book" items="${bookList}">
                    <div class="card fixed-card">
                        <img src="${pageContext.request.contextPath}/static/bookimg/${book.getBookImageName()}" class="card-img-top" alt="..." style="height:200px;">
                        <div class="card-body" style="height:150px; width: 18rem;">
                            <h5 class="card-title text-center" style="margin-top:-5px;">${book.getBookName()}</h5>
                            <p class="card-text justify-content-center" style="margin-top:-6px;">
                            <p><b>Author:</b> ${book.getBookAuthor()}<br/>
                                <b>Edition: </b> ${book.getBookEdition()}
                                <c:if test="${not empty user and user.role == 'user'}">
                                    <br/> <b>Available Books: </b> ${book.getQuantity() - book.getReservedQuantity()}
                                </c:if>
                            </p>
                            </p>
                        </div>
                        <c:if test="${empty user}">
                            <div class="text-center">
                                <a href="login.jsp" class="btn btn-dark">View Book</a>
                            </div>
                        </c:if>
                        <c:if test="${not empty user and user.role == 'user'}">
                            <button type="button" class="btn btn-warning" data-bs-toggle="modal" data-bs-target="#bookModal" onclick="showBookDetails('${book.getBookId()}', '${param.user.userId()}')">Request This Book</button>
                            <div class="modal fade" id="bookModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="exampleModalLabel">Select Issue and Return Date (10 Days Max)</h5>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body">
                                            <form id="dateForm" method="POST" action="requestBook">
                                                <label for="startDate">Book Issue Date</label>
                                                <input type="text" class="form-control" id="startDate" placeholder="DD/JAN/YYYY" autocomplete="off" required>
                                                <label for="endDate">Book Return Date</label>
                                                <input type="text" class="form-control" id="endDate" placeholder="DD/JAN/YYYY" autocomplete="off" required>
                                            </form>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                            <button type="button" id="submitDatesBtn" class="btn btn-primary">Submit Dates</button>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </c:if>
                        <c:if test="${not empty user and user.role == 'admin'}">
                            <div class="text-center">
                                <a href="deleteBook?bookId=${book.getBookId()}" class="btn btn-primary ">Delete Book</a>
                                &nbsp;<a href="editbook.jsp?bookId=${book.getBookId()}&bookName=${book.getBookName()}&bookAuthor=${book.getBookAuthor()}&bookEdition=${book.getBookEdition()}&bookQuantity=${book.getQuantity()}" class="btn btn-primary ">Edit Book</a>
                            </div>
                        </c:if>
                    </div>
                </c:forEach>
            </c:if>

            <c:if test="${empty bookList}">
                <!--<img src="${pageContext.request.contextPath}/static/bookimg/BEST-LIBRARY-AUTOMATION-SOFTWARE-min.png" style="width: 100%;">-->
            </c:if>                    
            <nav aria-label="Page navigation">
                <ul class="pagination justify-content-center mt-5">
                    <li class="page-item">
                        <c:if test="${currentPage > 1}">
                            <a class="page-link" href="viewIndexBooks?page=${currentPage - 1}" aria-label="Previous">
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
                                    <a class="page-link" href="viewIndexBooks?page=${page}">${page}</a>
                                </c:otherwise>
                            </c:choose>
                        </li>
                    </c:forEach>
                    <li class="page-item">
                        <c:if test="${currentPage < totalPages}">
                            <a class="page-link" href="viewIndexBooks?page=${currentPage + 1}" aria-label="Next">
                                <span>Next</span>
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </c:if>
                    </li>
                </ul>
            </nav>
        </div>
        <%
        } else if (user == null) {
        %><%@include file="extra.jsp" %>
        <%
            }
            %>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

        <script type="text/javascript">
                                let bookId, memberId;// Declare variables to store book details
                                memberId = "${sessionScope.user.memberId}";

                                function showBookDetails(id) {
                                    bookId = id;
                                    console.log(id);
                                    console.log(memberId);
                                }

                                flatpickr("#startDate", {
                                    allowInput: true,
                                    dateFormat: "d/M/Y",
                                });

                                flatpickr("#endDate", {
                                    allowInput: true,
                                    dateFormat: "d/M/Y",
                                });

                                function parseDate(dateString) {
                                    const parts = dateString.split('/');
                                    if (parts.length !== 3)
                                        return null; // Invalid format

                                    const day = parseInt(parts[0], 10);
                                    const monthStr = parts[1].toUpperCase();
                                    const year = parseInt(parts[2], 10);

                                    const months = {
                                        JAN: 0, FEB: 1, MAR: 2, APR: 3, MAY: 4,
                                        JUN: 5, JUL: 6, AUG: 7, SEP: 8, OCT: 9,
                                        NOV: 10, DEC: 11
                                    };

                                    const month = months[monthStr];
                                    if (month === undefined || day < 1 || day > 31 || year < 1900)
                                        return null;

                                    return new Date(year, month, day);
                                }

                                document.getElementById('submitDatesBtn').addEventListener('click', function () {
                                    const startDateValue = document.getElementById('startDate').value;
                                    const endDateValue = document.getElementById('endDate').value;

                                    const startDate = parseDate(startDateValue);
                                    const endDate = parseDate(endDateValue);

                                    if (!startDate || !endDate) {
                                        Swal.fire({
                                            icon: 'error',
                                            title: 'Invalid Date',
                                            text: 'Please enter valid dates in the format DD/JAN/YYYY.'
                                        });
                                        return;
                                    }

                                    let today = new Date();

                                    if (startDate < today)
                                    {
                                        Swal.fire({
                                            icon: 'error',
                                            title: 'Invalid Date Range',
                                            text: 'The Issue Date Should Be Of Tomorrow Or The Day After.'
                                        });
                                        return;
                                    }

                                    if (endDate < startDate) {
                                        Swal.fire({
                                            icon: 'error',
                                            title: 'Invalid Date Range',
                                            text: 'The return date cannot be before the issue date.'
                                        });
                                        return;
                                    }

                                    const timeDifference = endDate - startDate;
                                    const dayDifference = timeDifference / (1000 * 3600 * 24);

                                    if (dayDifference > 10) {
                                        Swal.fire({
                                            icon: 'error',
                                            title: 'Invalid Date Range',
                                            text: 'The return date cannot be more than 10 days max.'
                                        });
                                        return;
                                    }

                                    console.log("Start Date:", startDate);
                                    console.log("End Date:", endDate);

                                    sendDataToServlet(startDateValue, endDateValue);

                                });

                                function sendDataToServlet(startDate, endDate) {
                                    $.ajax({
                                        url: 'requestBook',
                                        type: 'POST',
                                        data: {
                                            bookId: bookId,
                                            startDate: startDate,
                                            endDate: endDate,
                                            memberId: memberId
                                        },
                                        success: function (response) {
                                            if (response.success) {
                                                Swal.fire({
                                                    icon: 'success',
                                                    title: 'This Book Has Been Requested ',
                                                    text: response.message
                                                }).then((result) => {
                                                    if (result.isConfirmed) {
                                                        window.location.href = 'viewIndexBooks';
                                                    }
                                                });
                                            } else {
                                                Swal.fire({
                                                    icon: 'error',
                                                    title: 'Book Not Available',
                                                    text: response.message
                                                }).then((result) => {
                                                    if (result.isConfirmed) {
                                                        // Redirect to another page
                                                        window.location.href = 'viewIndexBooks';
                                                    }
                                                });
                                            }


                                            const modal = bootstrap.Modal.getInstance(document.getElementById('bookModal'));
                                            modal.hide();
                                        },
                                        error: function (xhr, status, error) {
                                            Swal.fire({
                                                icon: 'error',
                                                title: 'Error',
                                                text: 'There was an issue submitting your book request.'
                                            });
                                            console.error('Error:', error);
                                        }
                                    });
                                }
        </script>
        <script src="static/js/main.js"type="text/javascript"></script>
    </body>
</html>
<%@ include file="footer.jsp" %>