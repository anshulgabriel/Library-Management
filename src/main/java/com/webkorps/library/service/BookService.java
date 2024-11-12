package com.webkorps.library.service;

import com.webkorps.library.dao.BookDao;
import com.webkorps.library.models.Book;
import com.webkorps.library.util.CompressMyImage;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Optional;
import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

public class BookService {

    private static final String SAVE_DIR = "bookimg"; // folder name
    private static final String SUCCESS_STATUS = "success";
    private static final String FAILED_STATUS = "failed";
    private static final String JSP_PAGE = "addbook.jsp";
    private static final String JSP_PAGE1 = "editbook.jsp";

    public void AddBook(HttpServletRequest request, HttpServletResponse response, String bookName,
            String bookAuthor, String bookEdition, int bookQuantity, Part bookImage, String savePath)
            throws IOException, ServletException {
        String bookImageName = bookImage.getSubmittedFileName();

        File dir = new File(savePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file;
        if (bookImage.getSize() > 0) {
            file = new File(savePath + File.separator + bookImageName);
            try (InputStream inputStream = bookImage.getInputStream()) {
                BufferedImage originalImage = ImageIO.read(inputStream);
                BufferedImage compressedImage = CompressMyImage.compressImage(originalImage, bookImage);

                try (OutputStream outputStream = Files.newOutputStream(file.toPath())) {
                    ImageIO.write(compressedImage, "jpg", outputStream);
                }
            } catch (IOException ex) {
                forwardWithStatus(request, response, FAILED_STATUS, JSP_PAGE);
                return;
            }
        } else {
            file = new File(savePath + File.separator + "DefaultBookImage.jpg");
            bookImageName = "DefaultBookImage.jpg";
        }

        int saveBook = BookDao.saveBook(bookName, bookAuthor, bookEdition, bookQuantity, file.toPath().toString(), bookImageName);
        String status = (saveBook > 0) ? SUCCESS_STATUS : FAILED_STATUS;
        forwardWithStatus(request, response, status, JSP_PAGE);
    }

    public void forwardWithStatus(HttpServletRequest request, HttpServletResponse response, String status, String page)
            throws ServletException, IOException {
        request.setAttribute("status", status);
        RequestDispatcher rd = request.getRequestDispatcher(page);
        rd.forward(request, response);
    }

    public void updateBook(HttpServletRequest request, HttpServletResponse response, long bookIntId,
            String bookName, String bookAuthor, String bookEdition, long bookQuantity, Part bookImage,
            String savePath, Optional<Book> savedBook) throws ServletException, IOException {
        Part bookImageUpdated = bookImage;
        String bookImageName = bookImageUpdated.getSubmittedFileName();
        if (bookName.trim().isBlank()) {
            bookName = savedBook.get().getBookName();
        }
        if (bookAuthor.trim().isBlank()) {
            bookAuthor = savedBook.get().getBookAuthor();
        }
        if (bookEdition.trim().isBlank()) {
            bookEdition = savedBook.get().getBookEdition();
        }
        if (bookQuantity <= 0) {
            bookQuantity = savedBook.get().getQuantity();
        }
        File dir = new File(savePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file;
        if (bookImage.getSize() <= 0) {
            bookImageName = savedBook.get().getBookImageName();
            file = new File(savedBook.get().getPath());
        } else {
            file = new File(savePath + File.separator + bookImageName);
            try (InputStream inputStream = bookImage.getInputStream()) {
                BufferedImage originalImage = ImageIO.read(inputStream);
                BufferedImage compressedImage = CompressMyImage.compressImage(originalImage, bookImage);

                try (OutputStream outputStream = Files.newOutputStream(file.toPath())) {
                    ImageIO.write(compressedImage, "jpg", outputStream);
                }
            } catch (IOException ex) {
                forwardWithStatus(request, response, FAILED_STATUS, JSP_PAGE1);
                return;
            }
        }
        int saveBook = BookDao.updateBook(bookName, bookAuthor, bookEdition, (int) bookQuantity, file.toPath().toString(), bookImageName, (int)bookIntId);
        String status = (saveBook > 0) ? SUCCESS_STATUS : FAILED_STATUS;
        forwardWithStatus(request, response, status, JSP_PAGE1);
    }

}
