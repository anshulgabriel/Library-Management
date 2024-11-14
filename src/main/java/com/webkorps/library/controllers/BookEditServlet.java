package com.webkorps.library.controllers;

import com.webkorps.library.dao.BookDao;
import com.webkorps.library.models.Book;
import com.webkorps.library.service.BookService;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/bookEdit")
@MultipartConfig
public class BookEditServlet extends HttpServlet {

    private static final String SAVE_DIR = "bookimg";
    private static final String FAILED_STATUS = "failed";
    private static final String JSP_PAGE1 = "editbook.jsp";

    BookService bookService = new BookService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String bookId = request.getParameter("bookId");
        String bookName = request.getParameter("bookName");
        String bookAuthor = request.getParameter("bookAuthor");
        String bookEdition = request.getParameter("bookEdition");
        String Quantity = request.getParameter("bookQuantity");
        Part bookImage = request.getPart("bookImage");

        if (areFieldsEmpty(bookName, bookAuthor, bookEdition, Quantity, bookImage)) {
            request.setAttribute("failed_message", "Update Failed, please update at least one field");
            request.setAttribute("status", "failed");
            bookService.forwardWithStatus(request, response, FAILED_STATUS, JSP_PAGE1);
            return;
        }

        long bookQuantity;
        int bookIntId;
        try {
            bookIntId = (bookId != null && !bookId.isEmpty()) ? Integer.parseInt(bookId) : 0;
            bookQuantity = (Quantity != null && !Quantity.isEmpty()) ? Integer.parseInt(Quantity) : 10;
        } catch (NumberFormatException ex) {
            bookService.forwardWithStatus(request, response, FAILED_STATUS, JSP_PAGE1);
            return;
        }

        String applicationPath = getServletContext().getRealPath("");
        String savePath = applicationPath + "static" + File.separator + SAVE_DIR;

        Optional<Book> savedBook = BookDao.getBookUsingId(bookIntId);

        if (savedBook.isPresent()) {
            bookService.updateBook(request, response, bookIntId, bookName, bookAuthor, bookEdition, bookQuantity, bookImage, savePath, savedBook);
            return;
        }
        bookService.forwardWithStatus(request, response, FAILED_STATUS, JSP_PAGE1);
    }

    private boolean areFieldsEmpty(String bookName, String bookAuthor, String bookEdition, String quantity, Part bookImage) {
        return isBlank(bookName) && isBlank(bookAuthor) && isBlank(bookEdition) && isBlank(quantity) && (bookImage == null || bookImage.getSize() <= 0);
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
