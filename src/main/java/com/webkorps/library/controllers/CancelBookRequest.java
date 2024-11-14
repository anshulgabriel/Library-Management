package com.webkorps.library.controllers;

import com.webkorps.library.dao.BookDao;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/cancelBookRequest")
public class CancelBookRequest extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String memberId = request.getParameter("memberId");
        String bookId = request.getParameter("bookId");

        int bookIdInt = Integer.parseInt(bookId);
        
        forwardWithStatus(request, response, BookDao.deleteBookDetails(memberId, bookIdInt) ? "nothing" : "success");
    }

    private void forwardWithStatus(HttpServletRequest request, HttpServletResponse response, String status)
            throws ServletException, IOException {
        request.setAttribute("status", status);
        request.getRequestDispatcher("requestedBooks").forward(request, response);
    }
}
