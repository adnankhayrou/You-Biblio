import java.util.Scanner;

public class youBiblio {

    public static void main(String[] args){
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
                    System.out.print("Enter the ISBN to find your Book : \n");
                    bookIsbn = sc.next();
                    // check if the book exists
                    boolean check = bookController.checkBookExists(bookIsbn);
                    if(!check){
                        System.out.println("\nBook does not exist.\n");
                    }else {
                        System.out.print("Enter the new title of the book: ");
                        String titleUpdate = sc.next();
                        System.out.print("\nEnter the new author of the book: ");
                        String authorUpdate = sc.next();
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
                    System.out.println("Enter Valid Choice !");
                    break;
            }
        }while (true);
    }
}
