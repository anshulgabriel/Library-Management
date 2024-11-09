<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="com.webkorps.library.models.Book"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="navbar.jsp" %>
<%response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");%>
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
        <div class="jumbotron pt-5 jumbotron-fluid text-center jumbotron-main">
            <div class="container">
                <h1 class="display-4">Search Your Requested Books Here</h1>
                <p class="lead">Please search using the Author's name.</p>
                <div class="container full-height w-50 justify-content-center align-items-center mt-3 mb-3">
                    <div class="d-flex justify-content-center">
                        <div class="input-group">
                            <input type="text" class="form-control" id="searchQuery" placeholder="Search for books that you've requested..."  onkeyup="searchBooks1()">
                        </div>
                    </div>
                </div>
            </div>
            <hr class="custom-hr">
        </div>
        <div id="searchResults" style="width: 100%; min-height: 500px;"></div>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script> 
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script src="static/js/main.js"type="text/javascript"></script>
    </body>
</html>
<%@ include file="footer.jsp" %>

