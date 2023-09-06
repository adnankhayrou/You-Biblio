import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

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
        if (con != null) {
            String query = "insert into book(title,author,isbn,quantity,status) values(?,?,?,?,?)";

            try {
                PreparedStatement pstm = con.prepareStatement(query);
                pstm.setString(1, getTitle());
                pstm.setString(2, getAuthor());
                pstm.setString(3, getIsbn());
                pstm.setInt(4, getQuantity());
                pstm.setString(5, getStatus());
                int cnt = pstm.executeUpdate();
                if (cnt != 0)
                    System.out.println("Book Inserted successfully.");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    //show all books
    public void showAllBooks(){
        //System.out.println("test");
        con = DbConnection.createDbConnection();
        if (con != null) {
            String query = "select * from book";
            System.out.println("Books Details : ");
            System.out.format("%s\t%s\t%s\t%s\t%s\t%s\n", "ID", "Title", "Author", "Isbn", "Quantity", "Status");
            try {
                Statement stmt = con.createStatement();
                ResultSet data = stmt.executeQuery(query);
                while (data.next()) {
                    System.out.format("%s\t%s\t%s\t%s\t%d\t%s\n",
                            data.getString(1),
                            data.getString(2),
                            data.getString(3),
                            data.getString(4),
                            data.getInt(5),
                            data.getString(6));

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    //show book based on title or author
    public void bookSearchWithTitleOrAuthor(String word){
        con = DbConnection.createDbConnection();
        if (con != null) {
            String query = "select * from book where book.title LIKE ? OR book.author LIKE ? ";
            try {
                PreparedStatement pstm = con.prepareStatement(query);
                pstm.setString(1, "%"+word+"%");
                pstm.setString(2, "%"+word+"%");

                ResultSet result = pstm.executeQuery();
                if (!result.isBeforeFirst()) {
                    System.out.println("No book found with this word : " + word + "!");
                }else {
                    System.out.println("Result : ");
                    System.out.format("%s\t%s\t%s\t%s\t%s\t%s\n", "ID", "Title", "Author", "Isbn", "Quantity", "Status");
                    while (result.next()){
                        System.out.format("%s\t%s\t%s\t%s\t%d\t%s\n",
                                result.getString(1),
                                result.getString(2),
                                result.getString(3),
                                result.getString(4),
                                result.getInt(5),
                                result.getString(6));

                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }
    //update book
    public void updateBook(){

    }
    
    //delete book
    public void deleteBookWithISBN(String bookIsbn){
        con = DbConnection.createDbConnection();
        if (con != null) {
            String query = "delete from book where isbn = ? ";
            try {
                PreparedStatement pstm = con.prepareStatement(query);
                pstm.setString(1, bookIsbn);

                int cnt = pstm.executeUpdate();
                if (cnt != 0){
                    System.out.println("Book Deleted successfully.");
                }else {
                    System.out.println("No book found with this ISBN : " + bookIsbn + "!");
            }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
