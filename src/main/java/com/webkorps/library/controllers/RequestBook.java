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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String bookId = request.getParameter("bookId");
        String memberId = request.getParameter("memberId");

        int bookIdInt = Integer.parseInt(bookId);
        Optional<BookDetails> existingBookDetails = BookDao.bookWithSameUserExist(bookId, memberId);
        boolean saveSuccessful = processBookRequest(existingBookDetails, bookIdInt, memberId, startDate, endDate);

        String message = saveSuccessful
                ? "Book Requested. Soon Will Be Approved By Admin."
                : (existingBookDetails.isPresent()
                ? "You Can Request The Same Book Only Once."
                : "This Book Is Not Available Anymore.");

        sendJsonResponse(response, saveSuccessful, message);
    }

    private boolean processBookRequest(Optional<BookDetails> existingBookDetails, int bookIdInt, String memberId, String startDate, String endDate) {
        if (existingBookDetails.isPresent()) {
            return handleExistingBookRequest(existingBookDetails.get(), bookIdInt, memberId, startDate, endDate);
        }
        return handleNewBookRequest(bookIdInt, memberId, startDate, endDate);
    }

    private boolean handleExistingBookRequest(BookDetails bookDetails, int bookIdInt, String memberId, String startDate, String endDate) {
        Book book = BookDao.getBookUsingId((int) bookDetails.getBookId()).orElse(null);
        return (book != null && book.getQuantity() - book.getReservedQuantity() > 0
                && !"Not Returned".equalsIgnoreCase(bookDetails.getReturnedDate()))
                && BookDao.saveBookDetails(memberId, bookIdInt, startDate, endDate);
    }

    private boolean handleNewBookRequest(int bookIdInt, String memberId, String startDate, String endDate) {
        Book book = BookDao.getBookUsingId(bookIdInt).orElse(null);
        BookDetails renewBookCheck = BookDao.RenewBook(bookIdInt);
        if (book == null) {
            return false;
        }

        int availableQuantity = (int)(book.getQuantity() - book.getReservedQuantity());
        return (availableQuantity > 0 && BookDao.saveBookDetails(memberId, bookIdInt, startDate, endDate))
                || (renewBookCheck != null && BookDao.BookConfirmedByAnotherUser(renewBookCheck)
                && BookDao.saveBookDetails(memberId, bookIdInt, "Not Given", "Not Given"));
    }

    private void sendJsonResponse(HttpServletResponse response, boolean success, String message) throws IOException {
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            out.print("{\"success\": " + success + ", \"message\": \"" + message + "\"}");
        }
    }
}
