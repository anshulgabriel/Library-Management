package com.webkorps.library.models;

public class BookDetails {

    private long id;
    private String bookApproved;
    private String issueDate;
    private String returnDate;
    private String returnedDate;
    private String memberId;
    private long bookId;
    private String isBookRenewed;

    public BookDetails() {
    }

    public BookDetails(long id, String bookApproved, String issueDate, String returnDate, String returnedDate, String memberId, long bookId, String isBookRenewed) {
        this.id = id;
        this.bookApproved = bookApproved;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
        this.returnedDate = returnedDate;
        this.memberId = memberId;
        this.bookId = bookId;
        this.isBookRenewed = isBookRenewed;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBookApproved() {
        return bookApproved;
    }

    public void setBookApproved(String bookApproved) {
        this.bookApproved = bookApproved;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(String returnedDate) {
        this.returnedDate = returnedDate;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public String getIsBookRenewed() {
        return isBookRenewed;
    }

    public void setIsBookRenewed(String isBookRenewed) {
        this.isBookRenewed = isBookRenewed;
    }

    @Override
    public String toString() {
        return "BookDetails{" + "id=" + id + ", bookApproved=" + bookApproved + ", issueDate=" + issueDate + ", returnDate=" + returnDate + ", returnedDate=" + returnedDate + ", memberId=" + memberId + ", bookId=" + bookId + ", isBookRenewed=" + isBookRenewed + '}';
    }

}