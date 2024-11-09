package com.webkorps.library.controllers;

import com.webkorps.library.dao.BookDao;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
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
        int bookId = 0;
        if (!id.isBlank()) {
            bookId = Integer.parseInt(id);
        }

        if (BookDao.deleteBook(bookId)) {
            forwardWithStatus(request, response, "success");
        } else {
            forwardWithStatus(request, response, "success");
        }
    }

    private void forwardWithStatus(HttpServletRequest request, HttpServletResponse response, String status)
            throws ServletException, IOException {
        request.setAttribute("status", status);
        RequestDispatcher rd = request.getRequestDispatcher("viewIndexBooks");
        rd.forward(request, response);
    }

}
