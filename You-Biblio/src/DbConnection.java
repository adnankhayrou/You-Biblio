import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnection {
    static Connection con;

    public static Connection createDbConnection(){
        try {
            // Load connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Get connection
            String url = "jdbc:mysql://localhost:3306/you-biblio";
            String username = "root";
            String password = "";
            con = DriverManager.getConnection(url, username, password);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return con;
    }
}
