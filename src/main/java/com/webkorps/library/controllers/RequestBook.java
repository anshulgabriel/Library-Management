package com.webkorps.library.controllers;

import com.webkorps.library.dao.BookDao;
import com.webkorps.library.models.Book;
import com.webkorps.library.models.BookDetails;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/requestBook")
public class RequestBook extends HttpServlet {

    private static final String SUCCESS_STATUS = "success";
    private static final String FAILED_STATUS = "failed";
    private static final String JSP_PAGE = "index.jsp";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String bookId = request.getParameter("bookId");
        String memberId = request.getParameter("memberId");

        int bookIdInt = Integer.parseInt(bookId);

        Optional<BookDetails> bookDetails = BookDao.bookWithSameUserExist(bookId, memberId);
        String failedMessage = "This Book Has Been Requested By Another User Already.";
        boolean saveBookDetails = false;
        if (!bookDetails.isPresent()) {
            Optional<Book> book = BookDao.getBookUsingId(bookIdInt);
            BookDetails CheckRenewBook = BookDao.RenewBook(bookIdInt);
            if (book.get().getQuantity() - book.get().getReservedQuantity() > 0) {
                saveBookDetails = BookDao.saveBookDetails(memberId, bookIdInt, startDate, endDate);
            } else if (book.get().getQuantity() - book.get().getReservedQuantity() <= 0 && CheckRenewBook != null) {
                if (BookDao.BookConfirmedByAnotherUser(CheckRenewBook)) {
                    if (BookDao.saveBookDetails(memberId, bookIdInt, "Not Given", "Not Given")) {
                        saveBookDetails = true;
                    }
                }
            }
            failedMessage = "This Book Is Not Available Anymore.";
        } else {
            Optional<Book> book = BookDao.getBookUsingId((int) bookDetails.get().getBookId());
            if (book.get().getQuantity() - book.get().getReservedQuantity() > 0 && !bookDetails.get().getReturnedDate().equalsIgnoreCase("Not Returned")) {
                saveBookDetails = BookDao.saveBookDetails(memberId, bookIdInt, startDate, endDate);
            }
            failedMessage = "You Can Request The Same Book Only Once.";
        }

        boolean success = saveBookDetails;
        String message = success ? "Book Requested. Soon Will Be Approved By Admin." : failedMessage;

        response.setContentType(
                "application/json");
        PrintWriter out = response.getWriter();

        out.print(
                "{\"success\": " + success + ", \"message\": \"" + message + "\"}");
        out.flush();
    }
}
