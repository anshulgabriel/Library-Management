package com.webkorps.library.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/loginFilter")
public class LoginFilter implements Filter {

    public void destroy() {

    }

    public LoginFilter() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if ((username != null && !username.trim().equals("")) && (password != null && !password.trim().equals(""))) {
            RequestDispatcher rd = req.getRequestDispatcher("loginServlet");
            rd.forward(request, response);
        } else {
            request.setAttribute("status", "failed");
            resp.sendRedirect("login.jsp");
        }
    }
}
