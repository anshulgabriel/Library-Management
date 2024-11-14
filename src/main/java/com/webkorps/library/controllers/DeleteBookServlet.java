package com.webkorps.library.controllers;

import com.webkorps.library.dao.BookDao;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/deleteBook")
public class DeleteBookServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("bookId");
        int bookId = id.isBlank() ? 0 : Integer.parseInt(id);

        forwardWithStatus(request, response, BookDao.deleteBook(bookId) ? "success" : "success");
    }

    private void forwardWithStatus(HttpServletRequest request, HttpServletResponse response, String status)
            throws ServletException, IOException {
        request.setAttribute("status", status);
        request.getRequestDispatcher("viewIndexBooks").forward(request, response);
    }

}
