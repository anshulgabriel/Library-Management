package com.webkorps.library.controllers;

import com.webkorps.library.dao.BookDao;
import com.webkorps.library.models.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/bookRequestedByUser")
public class BookRequestedByUser extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("user");

        String searchQuery = request.getParameter("search");
        response.setContentType("text/html");
        BookDao.searchRequestedBookQuery(request, response, user.getMemberId(), "false", searchQuery);
    }

}
