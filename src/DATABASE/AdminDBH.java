package DATABASE;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.jdbc.CallableStatement;

public class AdminDBH extends DataBaseHandler{
	
	
	 public List<String> getAllUsernames() {
	        List<String> usernames = new ArrayList<>();
	        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
	             CallableStatement stmt = (CallableStatement) conn.prepareCall("{CALL GetAllUsernames()}")) {

	            ResultSet rs = stmt.executeQuery();
	            while (rs.next()) {
	                usernames.add(rs.getString("username"));
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return usernames;
	    }
	 
	 public boolean deleteProfileByUsername(String username) {
		    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
		         CallableStatement stmt = (CallableStatement) conn.prepareCall("{CALL DeleteProfileByUsername(?)}")) {

		        stmt.setString(1, username);
		        stmt.executeUpdate();
		        return true;
		    } catch (SQLException e) {
		        e.printStackTrace();
		        return false;
		    }
		}

	 public boolean deleteUserByUsername(String username) {
		    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
		         CallableStatement stmt = (CallableStatement) conn.prepareCall("{CALL deleteUserByUsername(?)}")) {

		        stmt.setString(1, username);
		        stmt.executeUpdate();
		        return true;
		    } catch (SQLException e) {
		        e.printStackTrace();
		        return false;
		    }
		}
	 public boolean deleteUserCourses(String username) {
		    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
		         CallableStatement stmt = (CallableStatement) conn.prepareCall("{CALL DeleteUserCourses(?)}")) {

		        stmt.setString(1, username);
		        stmt.executeUpdate();
		        return true;
		    } catch (SQLException e) {
		        e.printStackTrace();
		        return false;
		    }
		}

}
