package Controllers;

import Controllers.BookController;
import Models.Borrows;
import Models.User;
import database.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class BorrowController {
    Connection con = DbConnection.createDbConnection();
    Scanner sc= new Scanner(System.in);
    BookController book = new BookController();

    //Borrow Models.Book
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

            UserController creatUser = new UserController();
            creatUser.checkUser(newUser);

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

                // Check if the user has already borrowed the book
                if (hasUserBorrowedBook(userID, bookId)) {
                    //newBorrow.setBookId(bookId);
                    //newBorrow.setUserId(userID);

                    String query = "INSERT INTO borrows(user_id, book_id, book_copy_id, date) VALUES (?, ?, ?, ?)";
                    PreparedStatement pstm = con.prepareStatement(query);
                    pstm.setInt(1, newBorrow.getUserId());
                    pstm.setInt(2, newBorrow.getBookId());
                    pstm.setInt(3, newBorrow.getBookCopyId());
                    java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(System.currentTimeMillis());
                    pstm.setTimestamp(4, currentTimestamp);

                    int cnt = pstm.executeUpdate();
                    if (cnt != 0) {
                        System.out.println("Book Borrowed successfully.");
                    }
                } else {
                    System.out.println("User has already borrowed this book.");
                }
                updateBookCopyStatus(bookCopyId);

            }catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public int[] getBookAndBookCopyId(String isbn) {
        int[] ids = new int[2]; //array to push (book ID and book copy ID)

        if (con != null) {
            String query = "SELECT book.id AS book_id, bookcopy.id AS bookcopy_id " +
                    "FROM book " +
                    "JOIN bookcopy ON book.id = bookcopy.book_id " +
                    "WHERE book.isbn = ? AND bookcopy.status = 'Available'";

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

    public void updateBookCopyStatus(int BookCopyId) {
        if (con != null) {
            String query = "UPDATE `bookcopy` SET `status`=? WHERE `id`=? AND `status` = 'Available' ORDER BY `id`";
            try {

                PreparedStatement preparedStatement = con.prepareStatement(query);
                preparedStatement.setString(1, "Borrowed");
                preparedStatement.setInt(2, BookCopyId);
                preparedStatement.executeUpdate();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public boolean hasUserBorrowedBook(int userID, int bookId) {
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


    //Return Models.Book
    public void returnBook(String isbn){
        boolean check = book.checkBookExists(isbn);
        if(!check){
            System.out.println("\nThe Book is Not Available.\n");
        }else {
            try {

                System.out.print("Enter Member Number : ");
                String memberNumber = sc.next();

                int bookCopyId = getBookCopyId(isbn,memberNumber);
                ReturnBookCopy(bookCopyId);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    public int getBookCopyId(String isbn, String memberNumber) {
        int id = 0;
        if (con != null) {
            String query = "SELECT borrows.book_copy_id " +
                    "FROM borrows " +
                    "JOIN book ON borrows.book_id = book.id " +
                    "JOIN user ON borrows.user_id = user.id " +
                    "WHERE book.isbn = ? AND user.member_number = ?";

            try {
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setString(1, isbn);
                stmt.setString(2, memberNumber);
                ResultSet data = stmt.executeQuery();

                if (data.next()) {
                    int bookCopyId = data.getInt("book_copy_id");
                    System.out.println(bookCopyId);
                    id = bookCopyId;
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return id;
    }

    public void ReturnBookCopy(int BookCopyId) {
        if (con != null) {
            String query = "UPDATE `bookcopy` SET `status`=? WHERE `id`=? ";
            try {
                PreparedStatement preparedStatement = con.prepareStatement(query);
                preparedStatement.setString(1, "Available");
                preparedStatement.setInt(2, BookCopyId);
                int count = preparedStatement.executeUpdate();
                if (count != 0) {
                    System.out.println("Book Returned Successfully");
                }

                removeTheBorrow(BookCopyId);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void removeTheBorrow(int bookCopyId){
        if (con != null) {
            String query = "delete from borrows where book_copy_id = ? ";
            try {
                PreparedStatement pstm = con.prepareStatement(query);
                pstm.setInt(1, bookCopyId);
                pstm.executeUpdate();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


    public void showBorrowedBooks(){
        if (con != null) {
            String query = "SELECT book.title, user.first_name, user.member_number, borrows.date " +
                    "FROM borrows " +
                    "JOIN book ON borrows.book_id = book.id " +
                    "JOIN user ON borrows.user_id = user.id";
            System.out.println("Books Details : ");
            System.out.format("%s\t%s\t%s\t%s\n", "Book", "Member Name", "Member Number", "Date");
            try {
                Statement stmt = con.createStatement();
                ResultSet data = stmt.executeQuery(query);
                while (data.next()) {
                    System.out.format("%s\t%s\t%s\t%tF\n",
                    data.getString("title"),
                    data.getString("first_name"),
                    data.getString("member_number"),
                    data.getDate("date"));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


}
