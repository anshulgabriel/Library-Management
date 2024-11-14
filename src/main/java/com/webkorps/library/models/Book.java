package com.webkorps.library.models;

import java.io.Serializable;
import java.util.Date;

public class Book implements Serializable {

    private long bookId;
    private String bookName;
    private String bookAuthor;
    private String bookEdition;
    private long quantity;
    private String path;
    private long reservedQuantity;
    private Date createdDate;
    private String bookImageName;

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookEdition() {
        return bookEdition;
    }

    public void setBookEdition(String bookEdition) {
        this.bookEdition = bookEdition;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getReservedQuantity() {
        return reservedQuantity;
    }

    public void setReservedQuantity(long reservedQuantity) {
        this.reservedQuantity = reservedQuantity;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getBookImageName() {
        return bookImageName;
    }

    public void setBookImageName(String bookImageName) {
        this.bookImageName = bookImageName;
    }

    @Override
    public String toString() {
        return "Book{" + "bookId=" + bookId + ", bookName=" + bookName + ", bookAuthor=" + bookAuthor + ", bookEdition=" + bookEdition + ", quantity=" + quantity + ", path=" + path + ", reservedQuantity=" + reservedQuantity + ", createdDate=" + createdDate + ", bookImageName=" + bookImageName + '}';
    }

}
