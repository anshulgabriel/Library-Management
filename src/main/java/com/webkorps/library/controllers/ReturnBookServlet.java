package com.webkorps.library.controllers;

import com.webkorps.library.models.User;
import com.webkorps.library.service.UserService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/returnBookList")
public class ReturnBookServlet extends HttpServlet {

    UserService userService = null;

    public ReturnBookServlet() {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pageName = request.getParameter("pageName");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        String memberId = (user != null) ? user.getMemberId() : "";

        if (pageName != null && pageName.equalsIgnoreCase("renew_books")) {
            userService.RenewBooks(request, response, memberId);
        } else {
            userService.returnBooksListAfterReturningRenew(request, response, memberId);
        }
    }
}
