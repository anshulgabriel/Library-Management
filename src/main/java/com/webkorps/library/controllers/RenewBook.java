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
import javax.servlet.http.HttpSession;

@WebServlet("/renewBook")
public class RenewBook extends HttpServlet {

    private static final String SUCCESS_STATUS = "success";
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

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String memberId = user.getMemberId();

        int bookIdInt = 0;
        if (bookId != null && memberId != null) {
            bookIdInt = Integer.parseInt(bookId);
        } else {
            userService.forwardWithStatus(request, response, FAILED_STATUS, JSP_PAGE);
        }

        try {
            userService.updateRenewBooks(request, response, bookIdInt, memberId);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }

}
