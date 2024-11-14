package com.webkorps.library.controllers;

import com.webkorps.library.dao.LoginDao;
import com.webkorps.library.models.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = LoginDao.getUser(username, password);

        if (user != null) {
            request.getSession().setAttribute("user", user);
            response.sendRedirect("viewIndexBooks");
            return;
        }

        request.setAttribute("status", "failed");
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}
