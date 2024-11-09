package com.webkorps.library.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/registerFilter")
public class RegisterFilter implements Filter {

    HttpServletRequest req = null;
    HttpServletResponse resp = null;

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        req = (HttpServletRequest) request;
        resp = (HttpServletResponse) response;
        String name = req.getParameter("name");
        String libraryName = req.getParameter("library_name");
        String address = req.getParameter("address");
        String email = req.getParameter("email");
        String password = req.getParameter("pass");
        String rePassword = req.getParameter("re_pass");
        String checkTerms = req.getParameter("agree-term");

        if ((name != null && !name.trim().equals(""))
                && (email != null && !email.trim().equals(""))
                && (password != null && !password.trim().equals(""))
                && (rePassword != null && !rePassword.trim().equals(""))
                && password.trim().equals(rePassword.trim()) && checkTerms != null) {

            RequestDispatcher rd = request.getRequestDispatcher("registerServlet");
            rd.forward(request, response);

        } else {
            request.setAttribute("status", "failed");
            request.setAttribute("register_message", "Please enter proper details. Thank you!");
            RequestDispatcher rd = request.getRequestDispatcher("signup.jsp");
            rd.forward(request, response);
        }
    }
}
