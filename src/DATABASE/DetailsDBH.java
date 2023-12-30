package DATABASE;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DetailsDBH extends DataBaseHandler{
	
	
	
public boolean enterdetails(String username ,String Name, String Batch, String Degree, String Rollnumber)
	{
			if (username == null || username.isEmpty() || 
		        Name == null || Name.isEmpty() ||
		        Batch == null || Batch.isEmpty() ||
		        Degree == null || Degree.isEmpty() ||
		        Rollnumber == null || Rollnumber.isEmpty()) {
		        System.out.println("Input parameters cannot be null or empty.");
		        return false;
		    }
		    try {
		        // Establish a connection
		        Connection conn = DriverManager.getConnection(URL, USER, PASS);

		        // Prepare a call to the addUser procedure
		        String sql = "{CALL AddUserProfile(?, ?, ?, ?, ?)}"; // SQL for calling the stored procedure
		        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
		            // Set the values for the IN parameters
		            pstmt.setString(1, username);
		            pstmt.setString(2, Name);
		            pstmt.setString(3, Batch);
		            pstmt.setString(4, Degree);
		            pstmt.setString(5, Rollnumber);
		            // Execute the stored procedure
		            pstmt.executeUpdate();
		            return true; // User successfully added
		        } catch (SQLException e) {
		            e.printStackTrace();
		            return false; // User could not be added
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		        return false; // Connection or other SQL error
		    }
	}
	
public boolean updateUserProfile(String username, String name, String batch, String degree, String rollNumber) {
        String query = "{CALL UpdateUserProfile(?, ?, ?, ?, ?)}";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             CallableStatement stmt = conn.prepareCall(query)) {

            stmt.setString(1, username);
            stmt.setString(2, name);
            stmt.setString(3, batch);
            stmt.setString(4, degree);
            stmt.setString(5, rollNumber);

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
	
public List<String> getUserProfile(String username) {
		
		List<String> profile = new ArrayList<String>();
		
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             CallableStatement stmt = (CallableStatement) conn.prepareCall("{CALL GetUserProfile(?)}")) {
             
            // Set the input parameter
            stmt.setString(1, username);

            // Execute the stored procedure
            ResultSet rs = stmt.executeQuery();

            // Process the result set
            if (rs.next()) {
            	profile.add(rs.getString("fname"));
            	profile.add(rs.getString("batch"));
            	profile.add(rs.getString("degree"));
            	profile.add(rs.getString("rollnumber"));
                return profile; // Data found
            }
            return null; // Data not found
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


//end class
}
