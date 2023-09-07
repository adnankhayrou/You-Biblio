import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLOutput;
import java.sql.Statement;
import java.util.Scanner;

public class youBiblio {

    public static void main(String[] args){
        boolean check = false;
        String bookIsbn;

        BookController bookController = new BookController();

        System.out.println("welcome to YouBiblio application : \n");
        Scanner sc=new Scanner(System.in);

        do {
            System.out.println("1. Add Book\n"+
                    "2. Show All Books\n"+
                    "3. Search For a Book\n"+
                    "4. Update Book\n"+
                    "5. Delete Book\n");
            System.out.println("Enter your choice : ");

            int ch=sc.nextInt();
            
            switch (ch){
                case 1:
                    System.out.println("Enter Title : ");
                    String title=sc.next();
                    System.out.println("Enter Author : ");
                    String author=sc.next();
                    System.out.println("Enter ISBN : ");
                    String isbn=sc.next();
                    System.out.println("Enter quantity : ");
                    int quantity= sc.nextInt();
                    System.out.println("Enter Status : ");
                    String status= sc.next();

                    Book newbook = new Book();
                    newbook.setTitle(title);
                    newbook.setAuthor(author);
                    newbook.setIsbn(isbn);
                    newbook.setQuantity(quantity);
                    newbook.setStatus(status);
                    bookController.addBook(newbook);
                    break;
                case 2:
                    bookController.showAllBooks();
                    break;
                case 3:
                    System.out.print("Enter Book Title or Book Author : ");
                    String searchInput = sc.next();
                    bookController.bookSearchWithTitleOrAuthor(searchInput);
                    break;
                case 4:
                    // update a book
                    System.out.print("Enter the ISBN to find your Book : ");
                    bookIsbn = sc.next();
                    // check if the book exists
                     check = bookController.checkBookExists(bookIsbn);
                    if(check != true){
                        System.out.println("Book does not exist.");
                    }else {
                        System.out.print("Enter the new title of the book: ");
                        String titleUpdate = sc.next();
                        System.out.print("\nEnter the new author of the book: ");
                        String authorUpdate = sc.next();
                        //System.out.print("\nEnter the new isbn of the book: ");
                        //String isbnUpdate = sc.next();
                        System.out.print("\nEnter the new status of the book: ");
                        String statusUpdate = sc.next();


                        Book updateBook = new Book();
                        updateBook.setTitle(titleUpdate);
                        updateBook.setAuthor(authorUpdate);
                        updateBook.setIsbn(bookIsbn);
                        updateBook.setStatus(statusUpdate);
                        bookController.updateBook(updateBook);
                    }
                    break;
                case 5:
                    System.out.print("Enter Book ISBN : ");
                    bookIsbn = sc.next();
                    bookController.deleteBookWithISBN(bookIsbn);
                    break;
                case 0:
                    System.out.println("Thank you for using our Application.");
                    System.exit(0);
                default:
                    System.out.println("Enter Valid choice !");
                    break;
            }
        }while (true);

        /*Connection connection = DbConnection.createDbConnection();
        if (connection != null) {
            try {
                // Create a statement
                Statement statement = connection.createStatement();

                // Execute a sample SQL query
                ResultSet resultSet = ((java.sql.Statement) statement).executeQuery("SELECT 'Database connection is working!' AS message");

                // Display the result
                if (resultSet.next()) {
                    String message = resultSet.getString("message");
                    System.out.println(message);
                }

                // Close the statement and connection
                resultSet.close();
                ((java.sql.Statement) statement).close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Failed to establish a database connection.");
        }*/
    }
}
