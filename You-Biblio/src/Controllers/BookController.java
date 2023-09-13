package Controllers;

import Models.Book;
import database.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class BookController {
    Connection con = DbConnection.createDbConnection();
    public void addBook(Book newBook){
        if (con != null) {
            String query = "insert into book(title,author,isbn,quantity) values(?,?,?,?)";

            try {
                PreparedStatement pstm = con.prepareStatement(query);
                pstm.setString(1, newBook.getTitle());
                pstm.setString(2, newBook.getAuthor());
                pstm.setString(3, newBook.getIsbn());
                pstm.setInt(4, newBook.getQuantity());

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
        if (con != null) {
            String query = "select * from book";
            System.out.println("\nBooks Details : ");
            System.out.format("%s\t%s\t%s\t%s\t%s\t%s", "ID", "Title", "Author", "Isbn", "Quantity", "Status");
            try {
                Statement stmt = con.createStatement();
                ResultSet data = stmt.executeQuery(query);
                while (data.next()) {
                    System.out.format("\n%d\t%s\t%s\t%s\t\t%d\t\t",
                    data.getInt(1),
                    data.getString(2),
                    data.getString(3),
                    data.getString(4),
                    data.getInt(5));
                    int count = data.getInt(5);
                    if (count >= 1){
                        System.out.println("Available\n");
                    }else {
                        System.out.println("Not Available\n");
                    }

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    //show book based on title or author
    public void bookSearchWithTitleOrAuthor(String word){
        if (con != null) {
            String query = "select * from book where book.title LIKE ? OR book.author LIKE ? ";
            try {
                PreparedStatement pstm = con.prepareStatement(query);
                pstm.setString(1, "%"+word+"%");
                pstm.setString(2, "%"+word+"%");

                ResultSet result = pstm.executeQuery();
                if (!result.isBeforeFirst()) {
                    System.out.println("\nNo book found with this word : " + word + "!\n");
                }else {
                    System.out.println("\nResult : ");
                    System.out.format("%s\t%s\t%s\t%s\t%s\t%s\n", "ID", "Title", "Author", "Isbn", "Quantity", "Status");
                    while (result.next()){
                        System.out.format("%s\t%s\t%s\t%s\t\t%d\t\t",
                        result.getString(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4),
                        result.getInt(5));
                        int count = result.getInt(5);
                        if (count >= 1){
                            System.out.println("Available\n");
                        }else {
                            System.out.println("Not Available\n");
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }


    // check if book exists
    public boolean checkBookExists(String isbn) {
        if (con != null) {
            String query = "select * from book where isbn = ? AND quantity >= 1";
            try {
                PreparedStatement pstm = con.prepareStatement(query);
                pstm.setString(1, isbn);
                ResultSet result = pstm.executeQuery();
                if (!result.isBeforeFirst()){
                    return false;
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return true;
    }


    public void updateBook(Book updateBook) {
        if (con != null) {
            String query = "UPDATE `book` SET `title`=?, `author`=? WHERE `isbn`=? ";
            try {
                PreparedStatement preparedStatement = con.prepareStatement(query);
                preparedStatement.setString(1, updateBook.getTitle());
                preparedStatement.setString(2, updateBook.getAuthor());
                preparedStatement.setString(3, updateBook.getIsbn());

                int count = preparedStatement.executeUpdate();
                if (count != 0) {
                    System.out.println("Book Updated Successfully");
                } else {
                    System.out.println("Something went wrong");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    //delete book
    public void deleteBookWithISBN(String bookIsbn){
        if (con != null) {
            boolean check = checkBorrowedBook(bookIsbn);
            if (check) {
                System.out.println("\nYou can't Delete a Borrowed Models.Book\n");
            }else{
            String query = "delete from book where isbn = ? ";
            try {
                PreparedStatement pstm = con.prepareStatement(query);
                pstm.setString(1, bookIsbn);

                int cnt = pstm.executeUpdate();
                if (cnt != 0) {
                    System.out.println("\nBook Deleted successfully.\n");
                } else {
                    System.out.println("\nNo book found with this ISBN : " + bookIsbn + "!\n");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        }
    }

    public boolean checkBorrowedBook(String isbn) {
        if (con != null) {
            String query = "SELECT * FROM book " +
                    "JOIN borrows ON book.id = borrows.book_id " +
                    "WHERE book.isbn = ?";
            try {
                PreparedStatement pstm = con.prepareStatement(query);
                pstm.setString(1, isbn);
                ResultSet result = pstm.executeQuery();
                if (!result.isBeforeFirst()){
                    return false;
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return true;
    }

    public void checkAndMarkLostBooks(){
        if (con != null) {
            try {
                String query = "SELECT id, book_copy_id FROM borrows WHERE date < DATE_SUB(NOW(), INTERVAL 1 MINUTE)";
                PreparedStatement stmt = con.prepareStatement(query);
                ResultSet result = stmt.executeQuery();

                String updateQuery = "UPDATE bookcopy SET status = 'Lost' WHERE id = ?";
                PreparedStatement updateStatement = con.prepareStatement(updateQuery);

                while (result.next()) {
                    int bookCopyId = result.getInt("book_copy_id");
                    updateStatement.setInt(1, bookCopyId);
                    updateStatement.executeUpdate();
                    System.out.println("\nBook copy with ID " + bookCopyId + " marked as 'Lost'.\n");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


    public void showBookStatistics(){
        int available = countBooksByStatus("Available");
        int borrowed = countBooksByStatus("Borrowed");
        int lost = countBooksByStatus("Lost");

        try {
            System.out.println("");
            System.out.println("Available books: " + available);
            System.out.println("Borrowed books: " + borrowed);
            System.out.println("Lost books: " + lost);
            System.out.println("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    public int countBooksByStatus(String status){
        if(con != null){
            String query = "SELECT COUNT(*) FROM bookcopy WHERE status = ?";
            try {
                PreparedStatement preparedStatement = con.prepareStatement(query);
                preparedStatement.setString(1, status);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return 0;
    }
}
