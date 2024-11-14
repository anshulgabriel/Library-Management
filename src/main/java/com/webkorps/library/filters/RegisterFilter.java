package com.webkorps.library.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter("/registerFilter")
public class RegisterFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("pass");
        String rePassword = request.getParameter("re_pass");
        String checkTerms = request.getParameter("agree-term");

        boolean isValidRegistration = isValid(name) && isValid(email) && isValid(password)
                && isValid(rePassword) && password.trim().equals(rePassword.trim()) && checkTerms != null;

        if (isValidRegistration) {
            request.getRequestDispatcher("registerServlet").forward(request, response);
        } else {
            request.setAttribute("status", "failed");
            request.setAttribute("register_message", "Please enter proper details. Thank you!");
            request.getRequestDispatcher("signup.jsp").forward(request, response);
        }
    }

    private boolean isValid(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
