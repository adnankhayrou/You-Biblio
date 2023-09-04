import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLOutput;
import java.sql.Statement;
import java.util.Scanner;

public class youBiblio {

    public static void main(String[] args){
        System.out.println("welcome to YouBiblio application");

        Scanner sc=new Scanner(System.in);
        do {
            System.out.println("1. Add Book\n"+
                    "2. Show All Books\n"+
                    "3. Search For a Book\n"+
                    "4. Update Book\n"+
                    "5. Delete Book\n"+
            ":");
            int ch=sc.nextInt();
            switch (ch){
                case 1:
                    System.out.println("good");
                    break;
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
