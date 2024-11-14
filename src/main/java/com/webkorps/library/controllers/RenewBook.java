package com.webkorps.library.controllers;

import com.webkorps.library.models.User;
import com.webkorps.library.service.UserService;
import java.io.IOException;
import java.text.ParseException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/renewBook")
public class RenewBook extends HttpServlet {

    private static final String FAILED_STATUS = "failed";
    private static final String JSP_PAGE = "requestedbooks.jsp";

    UserService userService = null;

    public RenewBook() {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String bookId = request.getParameter("bookId");

        User user = (User)request.getSession().getAttribute("user");
        String memberId = (user !=null) ? user.getMemberId() : "";

        if (bookId == null || memberId == null) {
            userService.forwardWithStatus(request, response, FAILED_STATUS, JSP_PAGE);
            return;
        }

        try {
            int bookIdInt = Integer.parseInt(bookId);
            userService.updateRenewBooks(request, response, bookIdInt, memberId);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }

}
