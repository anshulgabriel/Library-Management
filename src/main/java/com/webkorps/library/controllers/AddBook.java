package com.webkorps.library.controllers;

import com.webkorps.library.service.BookService;
import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/addBook")
@MultipartConfig
public class AddBook extends HttpServlet {

    private static final String SAVE_DIR = "bookimg"; // folder name
    private static final String SUCCESS_STATUS = "success";
    private static final String FAILED_STATUS = "failed";
    private static final String JSP_PAGE = "addbook.jsp";
    
    BookService bookService = new BookService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String bookName = request.getParameter("book_name");
        String bookAuthor = request.getParameter("book_author");
        String bookEdition = request.getParameter("book_edition");
        Part bookImage = request.getPart("book_image");

        // Validate inputs
        if (bookName == null || bookName.equals("")
                || bookAuthor == null || bookAuthor.equals("")
                || bookEdition == null || bookEdition.equals("")) {
            bookService.forwardWithStatus(request, response, FAILED_STATUS, JSP_PAGE);
            return;
        }
        String Quantity = request.getParameter("book_quantity");
        int bookQuantity;
        try {
            if (Quantity.length() > 0) {
                bookQuantity = Integer.parseInt(request.getParameter("book_quantity"));
            } else {
                bookQuantity = 10;
            }
        } catch (NumberFormatException ex) {
            bookService.forwardWithStatus(request, response, FAILED_STATUS, JSP_PAGE);
            return;
        }

        // Construct the path to save the file
        String applicationPath = getServletContext().getRealPath("");
        String savePath = applicationPath + "static" + File.separator + SAVE_DIR;
        
        bookService.AddBook(request, response, bookName, bookAuthor, bookEdition, bookQuantity, bookImage, savePath);

    }
}