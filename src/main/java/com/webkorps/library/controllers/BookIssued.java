package com.webkorps.library.controllers;

import com.webkorps.library.dao.BookDao;
import com.webkorps.library.models.Book;
import com.webkorps.library.models.BookDetails;
import com.webkorps.library.models.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/bookIssued")
public class BookIssued extends HttpServlet {

    private static final int PAGE_SIZE = 12;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pageName = request.getParameter("source");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        Map<List<BookDetails>, List<Book>> getAllBooksWIthMap = getAllBooks(user.getMemberId(), "true");

        List<BookDetails> allBookDetails = new ArrayList<>();
        List<Book> getAllBooks = new ArrayList<>();

        for (Map.Entry<List<BookDetails>, List<Book>> entry : getAllBooksWIthMap.entrySet()) {
            allBookDetails.addAll(entry.getKey());
            getAllBooks.addAll(entry.getValue());
        }

        int bookSize = getAllBooks.size();
        int totalPages = bookSize / PAGE_SIZE + 1;
        
        int currentPage = Optional.ofNullable(request.getParameter("page"))
                .map(Integer::parseInt)
                .filter(page -> page > 0)
                .orElse(1);

        currentPage = Math.max(1, Math.min(currentPage, totalPages));

        int startIndex = (currentPage - 1) * PAGE_SIZE;
        int endIndex = Math.min(startIndex + PAGE_SIZE, bookSize);
        List<Book> paginatedBooks = getAllBooks.subList(startIndex, endIndex);

        request.setAttribute("MapBookWithDetails", getAllBooksWIthMap);
        request.setAttribute("bookDetails", allBookDetails);
        request.setAttribute("bookList", paginatedBooks);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("page_status", pageName);

        request.getRequestDispatcher("requestedbooks.jsp").forward(request, response);

    }

    private Map<List<BookDetails>, List<Book>> getAllBooks(String memberId, String booleanValue) {
        return BookDao.getUserFromBookDetailsUsingId2(memberId, booleanValue);
    }
}
