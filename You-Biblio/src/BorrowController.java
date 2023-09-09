import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;
import java.sql.SQLException;

public class BorrowController {
    Connection con;
    Scanner sc=new Scanner(System.in);

    //Borrow Book
    public void borrowBook(String isbn){
        BookController book = new BookController();
        boolean check = book.checkBookExists(isbn);
        if(!check){
            System.out.println("\nBook does not exist.\n");
        }else {
            try {

            System.out.print("Enter Member First Name : ");
            String memberFirstName = sc.next();
            System.out.print("Enter Member Last Name : ");
            String memberLastName = sc.next();
            System.out.print("Enter Member Number : ");
            String memberNumber = sc.next();

            User newUser = new User();
            newUser.setFirstName(memberFirstName);
            newUser.setLastName(memberLastName);
            newUser.setMemberNumber(memberNumber);
            newUser.setCreatedAt(new Date());

            UserController creatUser = new UserController();
            creatUser.newUser(newUser);

            //System.out.println(newUser.getId());
            int userID = newUser.getId();
            int bookId = getBookId(isbn);
            //System.out.println(id);
            Borrows newBorrow = new Borrows();
                newBorrow.setBookId(bookId);
                newBorrow.setUserId(userID);
                newBorrow.setStatus("Borrowed");

                String query = "insert into borrows(user_id,book_id,date,status) values(?,?,?,?)";
                PreparedStatement pstm = con.prepareStatement(query);
                pstm.setInt(1, newBorrow.getUserId());
                pstm.setInt(2, newBorrow.getBookId());
                java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(System.currentTimeMillis());
                pstm.setTimestamp(3, currentTimestamp);
                pstm.setString(4, newBorrow.getStatus());

                int cnt = pstm.executeUpdate();
                if (cnt != 0)
                System.out.println("Book Borrowed successfully.");

            }catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public int getBookId(String isbn) {
        con = DbConnection.createDbConnection();
        if (con != null) {
            String query = "SELECT id FROM book WHERE isbn = ? AND status = 'available'";
            try {
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setString(1, isbn);
                ResultSet data = stmt.executeQuery();

                if (data.next()) {
                    //System.out.println(data.getString("id"));
                    return data.getInt("id");
                }else {
                    System.out.println("The Book is Not Available");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return -1;
    }

    //Return Book
    public void returnBook(){

    }
}
