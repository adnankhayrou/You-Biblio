import Controllers.BookController;
import Controllers.BorrowController;
import Models.Book;

import java.util.Scanner;

public class youBiblio {

    public static void main(String[] args){
        String bookIsbn;

        BookController bookController = new BookController();
        BorrowController borrowBook = new BorrowController();

        System.out.println("welcome to YouBiblio application : \n");
        Scanner sc=new Scanner(System.in);

        do {
            System.out.println("1.  Add Book\n"+
                    "2.  Show All Books\n"+
                    "3.  Search For a Book\n"+
                    "4.  Update Book\n"+
                    "5.  Delete Book\n"+
                    "6.  Borrow a Book\n"+
                    "7.  Return a Book\n"+
                    "8.  Show Borrowed Books\n"+
                    "9.  Show Books Statistics\n"+
                    "10. Check For Lost Books\n"+
                    "0.  Exit Application\n");
            System.out.println("Enter your choice : ");

            int choice=sc.nextInt();

            switch (choice){
                case 1:
                    System.out.println("Enter Title : ");
                    String title=sc.next();
                    System.out.println("Enter Author : ");
                    String author=sc.next();
                    System.out.println("Enter ISBN : ");
                    String isbn=sc.next();
                    System.out.println("Enter quantity : ");
                    int quantity= sc.nextInt();

                    Book newbook = new Book();
                    newbook.setTitle(title);
                    newbook.setAuthor(author);
                    newbook.setIsbn(isbn);
                    newbook.setQuantity(quantity);
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
                    System.out.print("Enter the ISBN to find your Book : \n");
                    bookIsbn = sc.next();
                    // check if the book exists
                    boolean check = bookController.checkBookExists(bookIsbn);
                    if(!check){
                        System.out.println("\nThe Book is Not Available.\n");
                    }else {
                        System.out.print("Enter the new title of the book: ");
                        String titleUpdate = sc.next();
                        System.out.print("\nEnter the new author of the book: ");
                        String authorUpdate = sc.next();


                        Book updateBook = new Book();
                        updateBook.setTitle(titleUpdate);
                        updateBook.setAuthor(authorUpdate);
                        updateBook.setIsbn(bookIsbn);
                        bookController.updateBook(updateBook);
                    }
                    break;
                case 5:
                    System.out.print("Enter Book ISBN : ");
                    bookIsbn = sc.next();
                    bookController.deleteBookWithISBN(bookIsbn);
                    break;
                case 6:
                    System.out.print("Enter Book ISBN : ");
                    bookIsbn = sc.next();
                    borrowBook.borrowBook(bookIsbn);
                    break;
                case 7:
                    System.out.print("Enter Book ISBN : ");
                    bookIsbn = sc.next();
                    borrowBook.returnBook(bookIsbn);
                    break;
                case 8:
                    borrowBook.showBorrowedBooks();
                    break;
                case 9:
                    bookController.showBookStatistics();
                    break;
                case 10:
                    bookController.checkAndMarkLostBooks();
                    break;
                case 0:
                    System.out.println("Thank you for using our Application.");
                    System.exit(0);
                default:
                    System.out.println("Enter Valid Choice !");
                    break;
            }
        }while (true);
    }
}
