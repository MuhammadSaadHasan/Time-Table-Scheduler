package DATABASE;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDBH extends DataBaseHandler{
	
	public void insertStudentCoursesList(String username,List<String> courses)
	{
		for(String item : courses )
		{
			insertStudentCourse(username,item);
		}
		
	}
	
	 public void insertStudentCourse(String username, String course) {
	        String query = "{CALL InsertStudentCourse(?, ?)}";

	        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
	             CallableStatement stmt = conn.prepareCall(query)) {

	            stmt.setString(1, username);
	            stmt.setString(2, course);

	            stmt.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	 public List<String> getCoursesForUser(String username) {
	        List<String> courses = new ArrayList<>();
	        String query = "{CALL GetCoursesForUser(?)}";

	        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
	             CallableStatement stmt = conn.prepareCall(query)) {

	            stmt.setString(1, username);
	            
	            try (ResultSet rs = stmt.executeQuery()) {
	                while (rs.next()) {
	                    courses.add(rs.getString("course")); // Assuming the column name in the result set is 'course'
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            // Handle any SQL exceptions here
	        }
	        return courses;
	    }
	 
	 public void deleteSpecificUserCourse(String username, String course) {
	        String query = "{CALL DeleteSpecificUserCourse(?, ?)}";

	        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
	             CallableStatement stmt = conn.prepareCall(query)) {

	            stmt.setString(1, username);
	            stmt.setString(2, course);

	            stmt.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	            // Handle any SQL exceptions here
	        }
	    }
	 
	 
}
