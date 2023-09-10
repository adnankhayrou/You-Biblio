import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;

public class BorrowController {
    Connection con;
    Scanner sc=new Scanner(System.in);
    BookController book = new BookController();

    //Borrow Book
    public void borrowBook(String isbn){

        boolean check = book.checkBookExists(isbn);
        if(!check){
            System.out.println("\nThe Book is Not Available.\n");
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
            //int bookId = getBookId(isbn);
                int[] result = getBookAndBookCopyId(isbn);
                int bookId = result[0];
                int bookCopyId = result[1];
            //System.out.println(id);
            Borrows newBorrow = new Borrows();
                newBorrow.setBookId(bookId);
                newBorrow.setBookCopyId(bookCopyId);
                newBorrow.setUserId(userID);
                newBorrow.setStatus("Borrowed");

                // Check if the user has already borrowed the book
                if (hasUserBorrowedBook(userID, bookId)) {
                    newBorrow.setBookId(bookId);
                    newBorrow.setUserId(userID);
                    newBorrow.setStatus("Borrowed");

                    String query = "INSERT INTO borrows(user_id, book_id, book_copy_id, date, status) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement pstm = con.prepareStatement(query);
                    pstm.setInt(1, newBorrow.getUserId());
                    pstm.setInt(2, newBorrow.getBookId());
                    pstm.setInt(3, newBorrow.getBookCopyId());
                    java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(System.currentTimeMillis());
                    pstm.setTimestamp(4, currentTimestamp);
                    pstm.setString(5, newBorrow.getStatus());

                    int cnt = pstm.executeUpdate();
                    if (cnt != 0) {
                        System.out.println("Book Borrowed successfully.");
                    }
                } else {
                    System.out.println("User has already borrowed this book.");
                }
                updateBookCopyStqtus(bookCopyId);

            }catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public int[] getBookAndBookCopyId(String isbn) {
        con = DbConnection.createDbConnection();
        int[] ids = new int[2]; //array to push (book ID and book copy ID)

        if (con != null) {
            String query = "SELECT book.id AS book_id, bookcopy.id AS bookcopy_id " +
                    "FROM book " +
                    "JOIN bookcopy ON book.id = bookcopy.book_id " +
                    "WHERE book.isbn = ? AND bookcopy.status = 'available'";

            try {
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setString(1, isbn);
                ResultSet data = stmt.executeQuery();

                if (data.next()) {
                    ids[0] = data.getInt("book_id");
                    ids[1] = data.getInt("bookcopy_id");
                } else {
                    System.out.println("The Book is Not Available");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return ids;
    }

    public void updateBookCopyStqtus(int BookCopyId) {
        con = DbConnection.createDbConnection();
        if (con != null) {
            String query = "UPDATE `bookcopy` SET `status`=? WHERE `id`=? ";
            try {
                PreparedStatement preparedStatement = con.prepareStatement(query);
                preparedStatement.setString(1, "Borrowed");
                preparedStatement.setInt(2, BookCopyId);



                int count = preparedStatement.executeUpdate();
                //if (count != 0) {
                    //System.out.println("Book Updated Successfully");
                //} else {
                    //System.out.println("Something went wrong");
                //}
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public boolean hasUserBorrowedBook(int userID, int bookId) {
        con = DbConnection.createDbConnection();
        if (con != null) {
            try {
                String query = "SELECT * FROM borrows WHERE user_id = ? AND book_id = ?";
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setInt(1, userID);
                pstmt.setInt(2, bookId);

                ResultSet result = pstmt.executeQuery();

                if (!result.isBeforeFirst()) {

                    return true;
                }
            }catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return false;
    }


    //Return Book
    public void returnBook(String isbn){
        boolean check = book.checkBookExists(isbn);
        if(!check){
            System.out.println("\nThe Book is Not Available.\n");
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


            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

}
