import java.sql.Connection;
import java.sql.PreparedStatement;

public class Book {
    Connection con;
    private String title;
    private String author;
    private String isbn;
    private int quantity;
    private String status;

    public Book(String title, String author, String isbn, int quantity, String status) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.quantity = quantity;
        this.status = status;
    }

    public Book() {


    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getStatus() {
        return status;
    }


    //add book
    public void addBook(){
        con = DbConnection.createDbConnection();
        String query="insert into book(title,author,isbn,quantity,status) values(?,?,?,?,?)";

        try {
            PreparedStatement pstm= con.prepareStatement(query);
            pstm.setString(1, getTitle());
            pstm.setString(2, getAuthor());
            pstm.setString(3, getIsbn());
            pstm.setInt(4, getQuantity());
            pstm.setString(5, getStatus());
            int cnt = pstm.executeUpdate();
            if (cnt!=0)
                System.out.println("Book Inserted successfully.");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    //show all books
    public void allBooks(){

    }
    //show book based on title or author
    public void showBook(){

    }
    //update book
    public void updateBook(){

    }
    //delete book
    public void deleteBook(){

    }
}
