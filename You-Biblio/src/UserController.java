import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class UserController {
    Connection con;

    //New User

    public User newUser(User newUser) {
        con = DbConnection.createDbConnection();
        if (con != null) {
            try {
                // Check if the user with the same member number already exists
                String checkQuery = "SELECT * FROM user WHERE member_number = ?";
                PreparedStatement checkStmt = con.prepareStatement(checkQuery);
                checkStmt.setString(1, newUser.getMemberNumber());
                ResultSet existingUser = checkStmt.executeQuery();

                if (existingUser.next()) {
                    // User already exists
                    int existingUserId = existingUser.getInt("id");
                    String existingFirstName = existingUser.getString("first_name");
                    String existingLastName = existingUser.getString("last_name");
                    newUser.setId(existingUserId);
                    newUser.setFirstName(existingFirstName);
                    newUser.setLastName(existingLastName);
                } else {
                    // insert a new user
                    String insertQuery = "INSERT INTO user (first_name, last_name, member_number) VALUES (?, ?, ?)";
                    PreparedStatement insertStmt = con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                    insertStmt.setString(1, newUser.getFirstName());
                    insertStmt.setString(2, newUser.getLastName());
                    insertStmt.setString(3, newUser.getMemberNumber());

                    int cnt = insertStmt.executeUpdate();

                    // Check if the insertion was successful
                    if (cnt == 1) {
                        // Retrieve the generated ID
                        ResultSet generatedKeys = insertStmt.getGeneratedKeys();
                        if (generatedKeys.next()) {
                            int generatedId = generatedKeys.getInt(1);
                            newUser.setId(generatedId);
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return newUser;
    }

}
