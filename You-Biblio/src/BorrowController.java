import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;

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

            //String query = "SELECT * FROM book WHERE isbn = ? AND status = 'available'";
            //PreparedStatement pstm = con.prepareStatement(query);
            //pstm.setString(1, isbn);
            //ResultSet result = pstm.executeQuery();

            System.out.println("test");

            }catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    //Return Book
    public void returnBook(){

    }
}
