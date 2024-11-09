package com.webkorps.library.service;

import com.webkorps.library.dao.BookDao;
import com.webkorps.library.models.Book;
import com.webkorps.library.models.BookDetails;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserService {

    private static final int PAGE_SIZE = 12;

    private static final String SUCCESS_STATUS = "success";
    private static final String FAILED_STATUS = "failed";
    private static final String JSP_PAGE = "requestedbooks.jsp";

    public void returnBooks(HttpServletRequest request, HttpServletResponse response, int bookId, String memberId) throws ServletException, IOException {
        boolean isBookReturned = BookDao.ReturnedBook(bookId, memberId);
        if (isBookReturned) {
            List<Book> fetchBooksListAfterReturning = BookDao.fetchBooksListAfterReturning(memberId, "true");
            paginationMethod(request, response, fetchBooksListAfterReturning, JSP_PAGE, JSP_PAGE);
        } else {
            forwardWithStatus(request, response, FAILED_STATUS, JSP_PAGE);
        }
    }

    public void forwardWithStatus(HttpServletRequest request, HttpServletResponse response, String status, String page)
            throws ServletException, IOException {
        request.setAttribute("status", status);
        RequestDispatcher rd = request.getRequestDispatcher(page);
        rd.forward(request, response);
    }

    public void paginationMethod(HttpServletRequest request, HttpServletResponse response,
            List<Book> getAllBooks, String pageName, String page) throws ServletException, IOException {
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
        request.setAttribute("page_status", pageName);

        request.getRequestDispatcher(page).forward(request, response);
    }

    public void returnBooksListAfterReturning(HttpServletRequest request, HttpServletResponse response, String memberId) throws ServletException, IOException {
        List<Book> fetchBooksListAfterReturning = BookDao.fetchBooksListAfterReturning(memberId, "true");
        request.setAttribute("status", "success");
        paginationMethod(request, response, fetchBooksListAfterReturning, "requestedbooks.jsp", JSP_PAGE);
    }

    public void returnBooksListAfterReturningRenew(HttpServletRequest request, HttpServletResponse response, String memberId) throws ServletException, IOException {
        List<Book> fetchBooksListAfterReturning = BookDao.fetchBooksListAfterReturning(memberId, "true");
        paginationMethod(request, response, fetchBooksListAfterReturning, JSP_PAGE, "returnbooks.jsp");
    }

    public void RenewBooks(HttpServletRequest request, HttpServletResponse response, String memberId) throws ServletException, IOException {
        List<Book> fetchBooksListAfterReturning = BookDao.fetchBooksListAfterReturning(memberId, "true");
        paginationMethod(request, response, fetchBooksListAfterReturning, "renew_books.jsp", JSP_PAGE);
    }

    public void updateRenewBooks(HttpServletRequest request, HttpServletResponse response, int bookId, String memberId) throws ServletException, IOException, ParseException {
        BookDetails bookDetailsToRenew = BookDao.fetchBookTorRenew(bookId, memberId);
        if (bookDetailsToRenew != null && bookDetailsToRenew.getReturnedDate() != null
                && bookDetailsToRenew.getReturnedDate().equals("Not Returned")) {
            String returnDate = bookDetailsToRenew.getReturnDate();

            if (checkRenewRequestedDate(returnDate)) {
                String renewedDate = increaseDate(returnDate);
                boolean bookRenewed = BookDao.renewBookOfUser(bookId, memberId, renewedDate);
                if (bookRenewed) {
                    BookDetails bookDetails = new BookDetails();
                    bookDetails.setBookId(bookId);
                    bookDetails.setMemberId(memberId);
                    if (BookDao.BookConfirmedByAnotherUser(bookDetails)) {
                        forwardWithStatus(request, response, SUCCESS_STATUS, "returnBookList?pageName=renew_books");
                    } else {
                        forwardWithStatus(request, response, FAILED_STATUS, "returnBookList?pageName=renew_books");
                    }
                } else {
                    forwardWithStatus(request, response, FAILED_STATUS, "returnBookList?pageName=renew_books");
                }
            } else {
                forwardWithStatus(request, response, FAILED_STATUS, "returnBookList?pageName=renew_books");
            }
        } else {
            forwardWithStatus(request, response, FAILED_STATUS, "returnBookList?pageName=renew_books");
        }
    }

    public String increaseDate(String date) {
        String renewedDateString = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy");
            Date parsedDate = dateFormat.parse(date);
            long milliseconds = parsedDate.getTime();
            long tenDaysInMillis = 10 * 24 * 60 * 60 * 1000L;  // 10 days in milliseconds
            Date renewedDate = new Date(milliseconds + tenDaysInMillis);
            renewedDateString = dateFormat.format(renewedDate);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        return renewedDateString;
    }

    public boolean checkRenewRequestedDate(String date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy");
            Date parsedDate = dateFormat.parse(date);
            long milliseconds = parsedDate.getTime();
            long MinusTwoDays = 2 * 24 * 60 * 60 * 1000L;  // 2 days in milliseconds
            Date renewedDate = new Date(milliseconds - MinusTwoDays);
            Date todayDate = new Date();

            if (renewedDate.compareTo(todayDate) == 0 || renewedDate.compareTo(todayDate) < 0) {
                return true;
            }
            return false;

        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        return false;
    }
}
