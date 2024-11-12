package com.webkorps.library.controllers;

import com.webkorps.library.dao.LoginDao;
import com.webkorps.library.models.User;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = LoginDao.getUser(username, password);
        
        if (user != null) {

            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            response.sendRedirect("viewIndexBooks");

        } else {
            request.setAttribute("status", "failed");
            RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
            rd.forward(request, response);
        }
    }
}
