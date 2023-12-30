package Users;

import java.util.List;

import DATABASE.AdminDBH;
public class Admin extends User {

	AdminDBH dbhandler;
	public List<String> getallusers()
	{
		return dbhandler.getAllUsernames();
	}
	public void deleteuser(String username)
	{
		dbhandler.deleteUserCourses(username);
		dbhandler.deleteProfileByUsername(username);
		dbhandler.deleteUserByUsername(username);
	}
	
}
