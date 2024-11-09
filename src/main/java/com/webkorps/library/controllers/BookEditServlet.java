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

    private static final String SAVE_DIR = "bookimg"; // folder name
    private static final String SUCCESS_STATUS = "success";
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
        
        if(bookName.trim().isBlank() && bookAuthor.trim().isBlank() && bookEdition.trim().isBlank() 
                && Quantity.trim().isBlank() && bookImage.getSize() <= 0) {
            request.setAttribute("failed_message", "Update Failed, please update alteast one field");
            request.setAttribute("status", "failed");
            bookService.forwardWithStatus(request, response, FAILED_STATUS, JSP_PAGE1);
            return;
        }

        
        long bookQuantity;
        int bookIntId = 0;
        try {
            if(!bookId.equals("")) {
                bookIntId = Integer.parseInt(bookId);
            }
            
            if (!Quantity.equals("")) {
                bookQuantity = Integer.parseInt(request.getParameter("bookQuantity"));
            } else {
                bookQuantity = 10;
            }
        } catch (NumberFormatException ex) {
            bookService.forwardWithStatus(request, response, FAILED_STATUS, JSP_PAGE1);
            return;
        }

        // Construct the path to save the file
        String applicationPath = getServletContext().getRealPath("");
        String savePath = applicationPath + "static" + File.separator + SAVE_DIR;

        Optional<Book> savedBook = BookDao.getBookUsingId(bookIntId);

        if (savedBook.isPresent()) {
            bookService.updateBook(request, response, bookIntId, bookName, bookAuthor, bookEdition, bookQuantity, bookImage, savePath, savedBook);
        } else {
            bookService.forwardWithStatus(request, response, FAILED_STATUS, JSP_PAGE1);
        }

    }

}
