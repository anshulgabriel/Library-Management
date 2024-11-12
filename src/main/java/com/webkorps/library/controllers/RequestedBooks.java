package com.webkorps.library.controllers;

import com.webkorps.library.dao.BookDao;
import com.webkorps.library.models.Book;
import com.webkorps.library.models.User;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/requestedBooks")
public class RequestedBooks extends HttpServlet {

    private static final int PAGE_SIZE = 12;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        StringBuffer requestURL = request.getRequestURL();
        
        List<Book> getAllBooks = getAllBooks(user.getMemberId(), "false");

        int bookSize = getAllBooks.size();
        int totalPages = bookSize / PAGE_SIZE + 1;

        int currentPage = 1;
        if (request.getParameter("page") != null) {
            currentPage = Integer.parseInt(request.getParameter("page"));
        }

        if (currentPage < 1) {
            currentPage = 1;
        }
        if (currentPage > totalPages) {
            currentPage = totalPages;
        }

        int startIndex = (currentPage - 1) * PAGE_SIZE;
        int endIndex = Math.min(startIndex + PAGE_SIZE, bookSize);
        List<Book> paginatedBooks = getAllBooks.subList(startIndex, endIndex);

        request.setAttribute("bookList", paginatedBooks);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("page_status", "true");

        request.getRequestDispatcher("requestedbooks.jsp").forward(request, response);

    }

    private List<Book> getAllBooks(String memberId, String booleanValue) {
        return BookDao.getUserFromBookDetailsUsingId(memberId, booleanValue);
    }
}
