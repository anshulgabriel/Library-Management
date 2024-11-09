package com.webkorps.library.filters;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import javax.servlet.annotation.WebFilter;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();

        // Get the request URI
        String requestURI = httpRequest.getRequestURI();

        // Allow access to login page and other excluded pages
        if (requestURI.endsWith("/index.jsp") || requestURI.endsWith("/login.jsp")
                || requestURI.endsWith("/signup.jsp") || isResourceRequest(requestURI)
                || requestURI.endsWith("/loginFilter")
                || requestURI.endsWith("/registerFilter")
                || requestURI.endsWith("/viewIndexBooks")
                || requestURI.endsWith("/welcome.jsp")) {
            chain.doFilter(request, response); // Continue processing
            return;
        }

        boolean loggedIn = (session != null && session.getAttribute("user") != null);
        if (loggedIn) {
            chain.doFilter(request, response); // User is logged in, continue
        } else {
            httpResponse.sendRedirect("index.jsp"); // Redirect to login page
        }
    }

    private boolean isResourceRequest(String uri) {
        return uri.endsWith(".css") || uri.endsWith(".js")
                || uri.endsWith(".png") || uri.endsWith(".jpg")
                || uri.endsWith(".jpeg") || uri.endsWith(".gif")
                || uri.endsWith(".woff") || uri.endsWith(".woff2")
                || uri.endsWith(".ttf") || uri.endsWith(".svg")
                || uri.startsWith("/static/"); // Adjust path as necessary
    }

    @Override
    public void destroy() {
        // Cleanup if needed
    }
}
