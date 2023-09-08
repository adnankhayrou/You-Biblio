import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class UserController {
    Connection con;

    //New User
    public User newUser(User newUser){
        con = DbConnection.createDbConnection();
        if (con != null) {
            String query = "insert into user(first_name,last_name,member_number,created_at) values(?,?,?,?)";

            try {
                PreparedStatement pstm = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
                pstm.setString(1, newUser.getFirstName());
                pstm.setString(2, newUser.getLastName());
                pstm.setString(3, newUser.getMemberNumber());

                // Set the created_at timestamp to the current time
                java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(System.currentTimeMillis());
                pstm.setTimestamp(4, currentTimestamp);

                int cnt = pstm.executeUpdate();

                // Check if the insertion was successful
                if (cnt == 1) {
                    // Retrieve the generated ID
                    ResultSet generatedKeys = pstm.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        newUser.setId(generatedId);
                    }
                }


            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return newUser;

    }
}
