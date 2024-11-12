package com.webkorps.library.dao;

import com.webkorps.library.models.Book;
import com.webkorps.library.models.BookDetails;
import com.webkorps.library.models.User;
import com.webkorps.library.util.DbUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static java.util.stream.Collectors.toList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BookDao {

    private static final String SQL_BOOKSAVE = "INSERT INTO books (name_of_book, book_author, book_edition, quantity, path, book_created_date, image_name) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_SINGLEBOOK = "SELECT * FROM books WHERE book_id=?";
    private static final String SQL_TOTALBOOKS = "SELECT * FROM books";
    private static final String SQL_UPDATEBOOK_BOOKREQUEST = "UPDATE books SET reserved_quantity=? WHERE book_id=?";
    private static final String SQL_SEARCHQUERY = "SELECT * FROM books WHERE name_of_book LIKE ? OR book_author LIKE ?";
    private static final String SQL_DELETEBOOK = "DELETE FROM books WHERE book_id=?";
    private static final String SQL_UPDATEBOOK = "UPDATE books SET name_of_book=?, book_author=?, book_edition=?, quantity=?, path=?, image_name=? WHERE book_id=?";
    
    private static final String SQL_USERDETAILS = "SELECT * FROM book_details where memberId=? and book_id=?";
    private static final String SQL_SAVEBOOKDETAILS = "INSERT INTO book_details(memberId, book_id, issue_date, return_date, book_approved, returned_dated, is_book_renewed) VALUES(?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_REQUESTEDBOOKS = "SELECT * FROM book_details where memberId=? and book_approved=?";
    private static final String SQL_FETCHBOOK_AFTERRETURN = "SELECT * FROM book_details where memberId=? and book_approved=? and returned_dated=?";
    private static final String SQL_REQUESTEDBOOKSADMIN = "SELECT * FROM book_details where book_approved=?";
    private static final String SQL_DELETEBOOKDETAILS = "DELETE FROM book_details WHERE memberId=? and book_id=?";
    private static final String SQL_APPROVEBOOKS = "UPDATE book_details SET book_approved=?, approved_date=? WHERE book_id=? and memberId=?";    
    private static final String SQL_RETURNBOOK = "UPDATE book_details SET returned_dated=? WHERE book_id=? and memberId=?";
    private static final String SQL_RENEWBOOK = "SELECT * FROM book_details WHERE book_id=? and memberId=? and book_approved=? and returned_dated=? and is_book_renewed=?";
    private static final String SQL_RENEWBOOK2 = "select * from book_details where book_id=? and book_approved=? and returned_dated=? and is_book_renewed=? LIMIT 1";
    private static final String SQL_RENEWBOOK_OFUSER = "UPDATE book_details SET return_date=?, is_book_renewed=? WHERE book_id=? and memberId=? and book_approved=? and returned_dated=?";    
    private static final String SQL_UPDATE_BOOKDETAILS = "UPDATE book_details SET is_book_renewed=? WHERE book_id=? and memberId=? and book_approved=? and returned_dated=?";

    public static int saveBook(String bookName, String bookAuthor, String bookEdition, int bookQuantity, String path, String bookImageName) {
        try (Connection con = DbUtil.getDataSource().getConnection(); PreparedStatement statement = con.prepareStatement(SQL_BOOKSAVE)) {

            con.setAutoCommit(false);

            statement.setString(1, bookName);
            statement.setString(2, bookAuthor);
            statement.setString(3, bookEdition);
            statement.setInt(4, bookQuantity);
            statement.setString(5, path);
            statement.setString(6, LocalDate.now().toString());
            statement.setString(7, bookImageName);

            int savedBook = statement.executeUpdate();
            if (savedBook > 0) {
                con.commit();
                return 1;
            }

        } catch (SQLException ex) {
            System.out.println("Transaction rolled back.");
            ex.printStackTrace();
        }
        return -1;
    }

    public static int updateBook(String bookName, String bookAuthor, String bookEdition, int bookQuantity, String path, String bookImageName, int bookId) {
        try (Connection con = DbUtil.getDataSource().getConnection(); PreparedStatement statement = con.prepareStatement(SQL_UPDATEBOOK)) {
            con.setAutoCommit(false);

            statement.setString(1, bookName);
            statement.setString(2, bookAuthor);
            statement.setString(3, bookEdition);
            statement.setInt(4, bookQuantity);
            statement.setString(5, path);
            statement.setString(6, bookImageName);
            statement.setInt(7, bookId);

            int savedBook = statement.executeUpdate();
            if (savedBook > 0) {
                con.commit();
                return 1;
            }

        } catch (SQLException ex) {
            System.out.println("Transaction rolled back.");
            ex.printStackTrace();
        }
        return -1;
    }

    public static List<Book> getBooks() {
        List<Book> booksList = new ArrayList<>();
        try (Connection con = DbUtil.getDataSource().getConnection(); PreparedStatement statement = con.prepareStatement(SQL_TOTALBOOKS)) {

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setBookName(rs.getString("name_of_book"));
                book.setBookAuthor(rs.getString("book_author"));
                book.setBookEdition(rs.getString("book_edition"));
                book.setQuantity(rs.getInt("quantity"));
                book.setPath(rs.getString("path"));
                book.setReservedQuantity(rs.getInt("reserved_quantity"));
                book.setCreatedDate(rs.getDate("book_created_date"));
                book.setBookImageName(rs.getString("image_name"));
                booksList.add(book);
            }
            return booksList;

        } catch (SQLException ex) {
            System.out.println("Transaction rolled back.");
            ex.printStackTrace();
        }
        return booksList;
    }

    public static Optional<Book> getBookUsingId(int id) {
        try (Connection con = DbUtil.getDataSource().getConnection(); PreparedStatement statement = con.prepareStatement(SQL_SINGLEBOOK)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            Book book = null;
            if (rs.next()) {
                book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setBookName(rs.getString("name_of_book"));
                book.setBookAuthor(rs.getString("book_author"));
                book.setBookEdition(rs.getString("book_edition"));
                book.setQuantity(rs.getInt("quantity"));
                book.setPath(rs.getString("path"));
                book.setReservedQuantity(rs.getInt("reserved_quantity"));
                book.setCreatedDate(rs.getDate("book_created_date"));
                book.setBookImageName(rs.getString("image_name"));
                

                return Optional.of(book);
            }

        } catch (SQLException ex) {
            System.out.println("Transaction rolled back.");
            ex.printStackTrace();
        }
        return Optional.empty();
    }

    public static boolean deleteBook(int bookId) {
        try (Connection con = DbUtil.getDataSource().getConnection(); PreparedStatement statement = con.prepareStatement(SQL_DELETEBOOK)) {
            statement.setInt(1, bookId);

            int executeUpdate = statement.executeUpdate();
            return (executeUpdate > 0);

        } catch (SQLException ex) {
            System.out.println("Transaction rolled back.");
            ex.printStackTrace();
        }
        return false;
    }

    public static Optional<BookDetails> bookWithSameUserExist(String bookId, String memberId) {

        try (Connection con = DbUtil.getDataSource().getConnection(); PreparedStatement statement = con.prepareStatement(SQL_USERDETAILS)) {
            statement.setString(1, memberId);
            statement.setString(2, bookId);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                BookDetails bookDetails = new BookDetails();
                bookDetails.setId(rs.getInt("id"));
                bookDetails.setMemberId(rs.getString("memberId"));
                bookDetails.setBookId(rs.getInt("book_id"));
                bookDetails.setReturnedDate(rs.getString("returned_dated"));

                return Optional.ofNullable(bookDetails);
            }

        } catch (SQLException ex) {
            System.out.println("Transaction rolled back.");
            ex.printStackTrace();
        }
        return Optional.empty();
    }

    public static boolean saveBookDetails(String memberId, int bookId, String issueDate, String returnDate) {

        try (Connection con = DbUtil.getDataSource().getConnection(); PreparedStatement statement = con.prepareStatement(SQL_SAVEBOOKDETAILS)) {
            statement.setString(1, memberId);
            statement.setInt(2, bookId);
            statement.setString(3, issueDate);
            statement.setString(4, returnDate);
            statement.setString(5, "false");
            statement.setString(6, "Not Returned");
            statement.setString(7, "NO");

            if (statement.executeUpdate() > 0 && IncrementReservedQuantityInBooks(bookId) > 0) {
                return true;
            }

        } catch (SQLException ex) {
            System.out.println("Transaction rolled back.");
            ex.printStackTrace();
        }

        return false;
    }

    public static int IncrementReservedQuantityInBooks(int bootId) {

        Book bookUsingId = getBookUsingId(bootId).get();
        int quantity = (int) bookUsingId.getReservedQuantity();
        quantity = quantity + 1;
        try (Connection con = DbUtil.getDataSource().getConnection(); PreparedStatement statement = con.prepareStatement(SQL_UPDATEBOOK_BOOKREQUEST)) {
            statement.setInt(1, quantity);
            statement.setInt(2, bootId);

            return statement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Transaction rolled back.");
            ex.printStackTrace();
        }

        return -1;
    }

    public static int DecrementReservedQuantityInBooks(int bootId) {

        Book bookUsingId = getBookUsingId(bootId).get();
        int quantity = (int) bookUsingId.getReservedQuantity();
        quantity = quantity - 1;
        try (Connection con = DbUtil.getDataSource().getConnection(); PreparedStatement statement = con.prepareStatement(SQL_UPDATEBOOK_BOOKREQUEST)) {

            statement.setInt(1, quantity);
            statement.setInt(2, bootId);

            return statement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Transaction rolled back.");
            ex.printStackTrace();
        }

        return -1;
    }

    public static List<Book> getUserFromBookDetailsUsingId(String memberId, String booleanValue) {
        try (Connection con = DbUtil.getDataSource().getConnection(); 
            PreparedStatement statement = con.prepareStatement(SQL_REQUESTEDBOOKS)) {
            statement.setString(1, memberId);
            statement.setString(2, booleanValue);

            ResultSet rs = statement.executeQuery();
            List<Integer> bookIds = new ArrayList<>();

            while (rs.next()) {
                bookIds.add(rs.getInt("book_id"));
            }

            return bookIds.stream().map(id -> {
                return getBookUsingId(id).get();
            }).collect(toList());

        } catch (SQLException ex) {
            System.out.println("Transaction rolled back.");
            ex.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static Map<List<BookDetails>, List<Book>> getUserFromBookDetailsUsingId2(String memberId, String booleanValue) {
        try (Connection con = DbUtil.getDataSource().getConnection(); PreparedStatement statement = con.prepareStatement(SQL_REQUESTEDBOOKS)) {
            statement.setString(1, memberId);
            statement.setString(2, booleanValue);

            ResultSet rs = statement.executeQuery();
            List<Integer> bookIds = new ArrayList<>();
            List<BookDetails> bookDetails = new ArrayList<>();

            while (rs.next()) {
                bookIds.add(rs.getInt("book_id"));
                BookDetails bookDetail = new BookDetails();
                bookDetail.setId(rs.getInt("id"));
                bookDetail.setMemberId(rs.getString("memberId"));
                bookDetail.setBookId(rs.getInt("book_id"));
                bookDetail.setIssueDate(rs.getString("issue_date"));
                bookDetail.setReturnDate(rs.getString("return_date"));
                bookDetail.setReturnedDate(rs.getString("returned_dated"));

                bookDetails.add(bookDetail);

            }

            List<Book> BookList = bookIds.stream().map(id -> {
                return getBookUsingId(id).get();
            }).collect(toList());

            Map<List<BookDetails>, List<Book>> bookListAndDetails = new HashMap<>();
            bookListAndDetails.put(bookDetails, BookList);

            return bookListAndDetails;

        } catch (SQLException ex) {
            System.out.println("Transaction rolled back.");
            ex.printStackTrace();
        }
        return new HashMap<>();
    }

    public static Map<String, List<Book>> getNonApprovedBooksFromBookDetails(String booleanValue) {
        try (Connection con = DbUtil.getDataSource().getConnection(); PreparedStatement statement = con.prepareStatement(SQL_REQUESTEDBOOKSADMIN)) {
            statement.setString(1, booleanValue);

            ResultSet rs = statement.executeQuery();
            Map<String, List<Integer>> issueBooksDetails = new HashMap<>();

            while (rs.next()) {
                String memberId = rs.getString("memberId");
                Integer bookId = rs.getInt("book_id");

                issueBooksDetails.computeIfAbsent(memberId, k -> new ArrayList<>()).add(bookId);
            }

            Map<String, List<Book>> finalMap = new HashMap<>();
            for (Map.Entry<String, List<Integer>> entry : issueBooksDetails.entrySet()) {

                List<Book> booksForThisTitle = new ArrayList<>();

                for (Integer bookId : entry.getValue()) {
                    Optional<Book> bookOptional = getBookUsingId(bookId);
                    bookOptional.ifPresent(booksForThisTitle::add); 
                }

                if (!booksForThisTitle.isEmpty()) {
                    finalMap.put(entry.getKey(), booksForThisTitle);
                } else {
                    System.out.println("No books found for title: " + entry.getKey());
                }
            }
            return finalMap;
        } catch (SQLException ex) {
            System.out.println("Transaction rolled back.");
            ex.printStackTrace();
        }
        return new HashMap<>();
    }

    public static boolean deleteBookDetails(String memberId, int bookId) {
        try (Connection con = DbUtil.getDataSource().getConnection(); PreparedStatement statement = con.prepareStatement(SQL_DELETEBOOKDETAILS)) {
            statement.setString(1, memberId);
            statement.setInt(2, bookId);

            int executeUpdate = statement.executeUpdate();

            if (executeUpdate > 0 && DecrementReservedQuantityInBooks(bookId) > 0) {
                return (executeUpdate > 0);
            }

        } catch (SQLException ex) {
            System.out.println("Transaction rolled back.");
            ex.printStackTrace();
        }
        return false;
    }

    public static boolean ReturnedBook(int bookId, String memberId) {
        try (Connection con = DbUtil.getDataSource().getConnection(); PreparedStatement statement = con.prepareStatement(SQL_RETURNBOOK)) {
            statement.setString(1, LocalDate.now().toString());
            statement.setInt(2, bookId);
            statement.setString(3, memberId);

            if (statement.executeUpdate() > 0 && DecrementReservedQuantityInBooks(bookId) > 0) {
                return true;
            }

        } catch (SQLException ex) {
            System.out.println("Transaction rolled back.");
            ex.printStackTrace();
        }

        return false;
    }

    public static List<Book> fetchBooksListAfterReturning(String memberId, String booleanValue) {
        try (Connection con = DbUtil.getDataSource().getConnection(); PreparedStatement statement = con.prepareStatement(SQL_FETCHBOOK_AFTERRETURN)) {
            statement.setString(1, memberId);
            statement.setString(2, booleanValue);
            statement.setString(3, "Not Returned");

            ResultSet rs = statement.executeQuery();
            List<Integer> bookIds = new ArrayList<>();

            while (rs.next()) {
                bookIds.add(rs.getInt("book_id"));
            }

            return bookIds.stream().map(id -> {
                return getBookUsingId(id).get();
            }).collect(toList());

        } catch (SQLException ex) {
            System.out.println("Transaction rolled back.");
            ex.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static boolean approveUserBook(int bookId, String memberId) {
        try (Connection con = DbUtil.getDataSource().getConnection(); PreparedStatement statement = con.prepareStatement(SQL_APPROVEBOOKS)) {
            statement.setString(1, "true");
            statement.setString(2, LocalDate.now().toString());
            statement.setInt(3, bookId);
            statement.setString(4, memberId);

            int executeUpdate = statement.executeUpdate();
            return (executeUpdate > 0);

        } catch (SQLException ex) {
            System.out.println("Transaction rolled back.");
            ex.printStackTrace();
        }
        return false;
    }

    public static BookDetails fetchBookTorRenew(int bookId, String memberId) {
        try (Connection con = DbUtil.getDataSource().getConnection(); PreparedStatement statement = con.prepareStatement(SQL_RENEWBOOK)) {
            statement.setInt(1, bookId);
            statement.setString(2, memberId);
            statement.setString(3, "true");
            statement.setString(4, "Not Returned");
            statement.setString(5, "NO");
            

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                BookDetails bookDetails = new BookDetails();
                bookDetails.setBookId(rs.getInt("book_id"));
                bookDetails.setMemberId(rs.getString("memberId"));
                bookDetails.setIssueDate(rs.getString("issue_date"));
                bookDetails.setReturnDate(rs.getString("return_date"));
                bookDetails.setReturnedDate(rs.getString("returned_dated"));
                return bookDetails;
            }

        } catch (SQLException ex) {
            System.out.println("Transaction rolled back.");
            ex.printStackTrace();
        }
        return new BookDetails();
    }

    public static boolean renewBookOfUser(int bookId, String memberId, String returnDate) {
        try (Connection con = DbUtil.getDataSource().getConnection(); PreparedStatement statement = con.prepareStatement(SQL_RENEWBOOK_OFUSER)) {
            statement.setString(1, returnDate);
            statement.setString(2, "YES");
            statement.setInt(3, bookId);
            statement.setString(4, memberId);
            statement.setString(5, "true");
            statement.setString(6, "Not Returned");

            return statement.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("Transaction rolled back.");
            ex.printStackTrace();
        }
        return false;
    }

    public static void searchBookQuery(HttpServletRequest request, HttpServletResponse response, String searchQuery, User user) throws IOException, SQLException, ServletException {

        PrintWriter out = response.getWriter();
        try (Connection con = DbUtil.getDataSource().getConnection(); PreparedStatement statement = con.prepareStatement(SQL_SEARCHQUERY)) {

            statement.setString(1, "%" + searchQuery + "%");
            statement.setString(2, "%" + searchQuery + "%");

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                out.println("<div class=\"container-fluid m-3 text-center\" style=\"min-height: 500px;\">");
                do {
                    String imageName = rs.getString("image_name");
                    out.println("<div class=\"card fixed-card\" style=\"width:18rem; display: inline-table;\">");
                    out.println("<img src=\"" + request.getContextPath() + "/static/bookimg/" + imageName + "\" class=\"card-img-top\" alt=\"...\">");
                    out.println("<div class=\"card-body\">");
                    out.println("<h5 class=\"card-title\">" + rs.getString("name_of_book") + "</h5>");
                    out.println("<p class=\"card-text\"><b>Author:</b>" + rs.getString("book_author") + "<br/>\n"
                            + "                                    <b>Edition: </b> " + rs.getString("book_author") + "</p></p>");
                    out.println("</div>");
                    if (user != null && user.getRole().equalsIgnoreCase("admin")) {
                        out.println("<div class=\"text-center\">");
                        out.println("<a href=\"#\" class=\"btn btn-primary\">Delete Book</a>");
                        out.println("&nbsp;<a href=\"#\" class=\"btn btn-primary\">Edit Book</a>");
                        out.println("</div>");
                    } else if (user != null && user.getRole().equalsIgnoreCase("user")) {
                        out.println("<div class=\"text-center\">");
                        out.println("<a href=\"#\" class=\"btn btn-warning\">Return This Book</a>");
                        out.println("</div>");
                    } else {
                        out.println("<div class=\"text-center\">");
                        out.println("<a href=\"#\" class=\"btn btn-dark disabled\">View Book</a>");
                        out.println("</div>");
                    }
                    out.println("</div>");
                } while (rs.next());
                out.println("</div>");
            } else {
                out.println("No results found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            out.println("Database error: " + e.getMessage());
        }
    }

    public static List<Integer> getUserFromBookDetailsUsingId3(String memberId, String booleanValue) {
        try (Connection con = DbUtil.getDataSource().getConnection(); PreparedStatement statement = con.prepareStatement(SQL_REQUESTEDBOOKS)) {
            statement.setString(1, memberId);
            statement.setString(2, booleanValue);

            ResultSet rs = statement.executeQuery();
            List<Integer> bookIds = new ArrayList<>();

            while (rs.next()) {
                bookIds.add(rs.getInt("book_id"));
            }

            return bookIds;

        } catch (SQLException ex) {
            System.out.println("Transaction rolled back.");
            ex.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static void searchRequestedBookQuery(HttpServletRequest request, HttpServletResponse response,
            String memberId, String booleanValue, String searchQuery) throws ServletException, IOException {
            
        List<Integer> bookIds = getUserFromBookDetailsUsingId3(memberId, "false");

        PrintWriter out = response.getWriter();

        StringBuilder query = new StringBuilder("SELECT * FROM books WHERE book_id IN (");

        for (int i = 0; i < bookIds.size(); i++) {
            query.append("?");
            if (i < bookIds.size() - 1) {
                query.append(", ");
            }
        }
        query.append(") AND book_author LIKE ?");
        try (Connection con = DbUtil.getDataSource().getConnection(); PreparedStatement statement = con.prepareStatement(query.toString());) {

            for (int i = 0; i < bookIds.size(); i++) {
                statement.setLong(i + 1, bookIds.get(i));
            }

            statement.setString(bookIds.size() + 1, "%" + searchQuery + "%");

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                out.println("<div class=\"container-fluid m-3 text-center\" style=\"min-height: 500px;\">");
                do {
                    String imageName = rs.getString("image_name");
                    out.println("<div class=\"card fixed-card\" style=\"width:18rem; display: inline-table;\">");
                    out.println("<img src=\"" + request.getContextPath() + "/static/bookimg/" + imageName + "\" class=\"card-img-top\" alt=\"...\">");
                    out.println("<div class=\"card-body\">");
                    out.println("<h5 class=\"card-title\">" + rs.getString("name_of_book") + "</h5>");
                    out.println("<p class=\"card-text\"><b>Author:</b>" + rs.getString("book_author") + "<br/>\n"
                            + "                                    <b>Edition: </b> " + rs.getString("book_author") + "</p></p>");
                    out.println("</div>");
                    out.println("<div class=\"text-center\">");
                    out.print("<a href=\"#\" class=\"btn btn-success\">Requested Book</a>");
                    out.println("</div>");
                    out.println("</div>");
                } while (rs.next());
                out.println("</div>");
            } else {
                out.println("No results found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            out.println("No Books Found");
        }
    }

    public static BookDetails RenewBook(int bookId) {
        try (Connection con = DbUtil.getDataSource().getConnection(); PreparedStatement statement = con.prepareStatement(SQL_RENEWBOOK2)) {
            statement.setInt(1, bookId);
            statement.setString(2, "true");
            statement.setString(3, "Not Returned");
            statement.setString(4, "NO");

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                BookDetails bookDetails = new BookDetails();
                bookDetails.setBookId(rs.getInt("book_id"));
                bookDetails.setMemberId(rs.getString("memberId"));
                bookDetails.setIssueDate(rs.getString("issue_date"));
                bookDetails.setReturnDate(rs.getString("return_date"));
                bookDetails.setBookApproved(rs.getString("book_approved"));
                bookDetails.setReturnedDate(rs.getString("returned_dated"));
                bookDetails.setIsBookRenewed(rs.getString("is_book_renewed"));
                return bookDetails;
            }

        } catch (SQLException ex) {
            System.out.println("Transaction rolled back.");
            ex.printStackTrace();
        }
        return new BookDetails();
    }

    public static boolean BookConfirmedByAnotherUser(BookDetails bookDetails) {
        try (Connection con = DbUtil.getDataSource().getConnection(); PreparedStatement statement = con.prepareStatement(SQL_UPDATE_BOOKDETAILS)) {
            statement.setString(1, "YES");
            statement.setInt(2, (int) bookDetails.getBookId());
            statement.setString(3, bookDetails.getMemberId());
            statement.setString(4, "true");
            statement.setString(5, "Not Returned");

            return statement.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("Transaction rolled back.");
            ex.printStackTrace();
        }
        return false;
    }
}
