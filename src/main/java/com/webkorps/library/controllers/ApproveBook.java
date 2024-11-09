package com.webkorps.library.controllers;

import com.webkorps.library.dao.BookDao;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/approveBooks")
public class ApproveBook extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String bookId = request.getParameter("bookId");
        String memberId = request.getParameter("memberId");

        int bookIdInt = 0;
        if (bookId != null) {
            bookIdInt = Integer.parseInt(bookId);
        }

        BookDao.approveUserBook(bookIdInt, memberId);
        
        request.setAttribute("status", "success");

        request.getRequestDispatcher("issueBooks").forward(request, response);
    }

}
