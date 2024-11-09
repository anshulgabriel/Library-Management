<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="com.webkorps.library.models.Book"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="navbar.jsp" %>
<%response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");%>
<c:if test="${not empty user and user.role == 'user'}">
    <jsp:forward page="index.jsp" />
</c:if>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <link rel="stylesheet" href="static/css/mystyle.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11.4.9/dist/sweetalert2.min.css">
    </head>
    <body>
        <input type="hidden" id="status" value="${status}">
        <div class="container-fluid m-3 text-center" style="min-height: 500px;">
            <h1>Books To Be Issued</h1>
            <c:if test="${not empty booksWithUser}">
                <!-- Iterate over the map (get key-value pairs) -->
                <c:forEach var="entry" items="${booksWithUser}">
                    <!-- Extract the key (e.g., user/member) and the list of books -->
                    <c:set var="key" value="${entry.key}" />
                    <c:set var="books" value="${entry.value}" />

                    <!-- Iterate over the list of books -->
                    <c:forEach var="book" items="${books}">
                        <!-- Print the key along with the book title -->
                        <div class="container-fluid m-3 text-center" style="min-height: 500px; display: inline;">
                            <div class="card fixed-card">
                                <!-- Check if book has an image and display it -->
                                <img src="${pageContext.request.contextPath}/static/bookimg/${book.bookImageName}" 
                                     class="card-img-top" alt="..." style="height:200px;">

                                <div class="card-body" style="height:140px; width: 18rem;">
                                    <h5 class="card-title">${book.bookName}</h5> <!-- Book Name -->
                                    <p class="card-text">
                                        <b>Author:</b> ${book.bookAuthor} <br/>
                                        <b>Edition:</b> ${book.bookEdition}<br/>
                                        <b>User Member Id: </b>${key}
                                    </p>
                                </div>
                                <div class="text-center mt-2">
                                    <button class="btn btn-primary" onclick="showAlertAndRedirect('Thank you for approving this book!', 'approveBooks?bookId=${book.bookId}&memberId=${key}')">Approve Book</button>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:forEach>
            </c:if>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script> 
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.4.9/dist/sweetalert2.all.min.js"></script>
        <script src="static/js/main.js"type="text/javascript"></script>
    </body>
</html>
<%@ include file="footer.jsp" %>
