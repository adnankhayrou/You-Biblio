import java.util.Date;
import java.util.Scanner;

public class Borrows {
    private int userId;
    private int bookId;

    private int bookCopyId;
    private Date date;
    private String status;


    public Borrows() {
    }

    public Borrows(int userId, int bookId, int bookCopyId, Date date, String status) {
        this.userId = userId;
        this.bookId = bookId;
        this.bookCopyId = bookCopyId;
        this.date = date;
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public int getBookCopyId() {
        return bookCopyId;
    }

    public void setBookCopyId(int bookCopyId) {
        this.bookCopyId = bookCopyId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
