package DATABASE;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.jdbc.CallableStatement;

import Encryption.Encrypter;

public class UserDBH extends DataBaseHandler {

	public boolean login(String username, String password, String type) {
	    try {
	        // Establish a connection
	        Connection conn = DriverManager.getConnection(URL, USER, PASS);

	        // Prepare a SQL statement to check if user exists with the given user name and password
	        String sql = "SELECT * FROM users WHERE username = ? AND password = ? AND usertype = ?";
	        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        	Encrypter encryptpasswerd = new Encrypter();
	        	
	            pstmt.setString(1, username);
	            pstmt.setString(2,encryptpasswerd.EncryptString(password));
	            pstmt.setString(3, type);

	            // Execute the query
	            ResultSet rs = pstmt.executeQuery();

	            // Check if user exists
	            if (rs.next()) {
	                return true; // User exists and password is correct
	            } else {
	                return false; // User doesn't exist or password is incorrect
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false; // SQL error
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false; // Connection error
	    }
	}
	
	public boolean signup(String username, String password, String type) {
	    String URL = "jdbc:mysql://localhost:3306/sdaproject";
	    String USER = "root";
	    String PASS = "password";

	    try {
	        // Establish a connection
	        Connection conn = DriverManager.getConnection(URL, USER, PASS);

	        // Prepare a call to the addUser procedure
	        String sql = "{CALL addUser(?, ?, ?)}"; // SQL for calling the stored procedure
	        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            // Set the values for the IN parameters
	        	Encrypter encryptpasswerd = new Encrypter();
	            pstmt.setString(1, username);
	            pstmt.setString(2, encryptpasswerd.EncryptString(password));
	            pstmt.setString(3, type);

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
	
	 public void updatePassword(String username, String newPassword) {
	        String query = "{CALL updatePassword(?, ?)}";

	        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
	             CallableStatement stmt = (CallableStatement) conn.prepareCall(query)) {
	        	Encrypter encryptpasswerd = new Encrypter();
	            stmt.setString(1, username);
	            stmt.setString(2, encryptpasswerd.EncryptString(newPassword));

	            stmt.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	            // Handle any SQL exceptions here
	        }
	    }
	 public void updateUsername(String oldUsername, String newUsername) {
		    String query = "{CALL updateUsername(?, ?)}";

		    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
		         CallableStatement stmt = (CallableStatement) conn.prepareCall(query)) {

		        stmt.setString(1, oldUsername);
		        stmt.setString(2, newUsername);

		        stmt.executeUpdate();
		    } catch (SQLException e) {
		        e.printStackTrace();
		        // Handle any SQL exceptions here
		    }
		}
	
    
	
}
