package com.webkorps.library.controllers;

import com.webkorps.library.service.UserService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/returnBook")
public class ReturnBook extends HttpServlet {

    private static final String SUCCESS_STATUS = "success";
    private static final String FAILED_STATUS = "failed";
    private static final String JSP_PAGE = "requestedbooks.jsp";

    UserService userService = null;

    public ReturnBook() {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String bookId = request.getParameter("bookId");
        String memberId = request.getParameter("memberId");

        int bookIdInt = 0;
        if (bookId != null && memberId != null) {
            bookIdInt = Integer.parseInt(bookId);
        } else {
            userService.forwardWithStatus(request, response, FAILED_STATUS, JSP_PAGE);
        }

        userService.returnBooks(request, response, bookIdInt, memberId);

    }

}
