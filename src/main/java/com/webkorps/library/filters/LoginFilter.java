package com.webkorps.library.filters;

import java.io.IOException;
import java.util.Optional;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/loginFilter")
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        String username = servletRequest.getParameter("username");
        String password = servletRequest.getParameter("password");

        Optional.ofNullable(username)
                .filter(u -> !u.isBlank())
                .flatMap(u -> Optional.ofNullable(password).filter(p -> !p.isBlank()))
                .ifPresentOrElse(
                        u -> {
                            try {
                                request.getRequestDispatcher("loginServlet").forward(request, response);
                            } catch (ServletException | IOException e) {
                                e.printStackTrace();
                            }
                        },
                        () -> {
                            request.setAttribute("status", "failed");
                            try {
                                servletResponse.sendRedirect("login.jsp");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                );

    }
}
