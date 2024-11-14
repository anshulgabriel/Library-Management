package com.webkorps.library.controllers;

import com.webkorps.library.dao.BookDao;
import com.webkorps.library.models.Book;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/issueBooks")
public class IssueBooks extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Map<String, List<Book>> getBooks = getAllBooks("false");
        request.setAttribute("booksWithUser", getBooks);

        request.getRequestDispatcher("issuebooks.jsp").forward(request, response);
    }

    private Map<String, List<Book>> getAllBooks(String booleanValue) {
        return BookDao.getNonApprovedBooksFromBookDetails(booleanValue);
    }

}
