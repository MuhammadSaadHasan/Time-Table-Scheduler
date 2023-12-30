package Users;
import DATABASE.UserDBH;


	abstract class User {
	String username;
	String password;
	UserDBH dbhandler =  new UserDBH();

	public String getUsername() {return username;}
	public String getPassword() {return password;}
	
	public boolean login(String username,String password,String type)
	{
		
		if(dbhandler.login(username, password, type))
		{
			this.username = username;
			this.password = password;
			return true;
		}
		return false;
	}
	
	public boolean signup(String username, String password ,String type)
	{
		if(dbhandler.signup(username, password, type))
		{
			this.username = username;
			this.password = password;
			return true;
		}
		return false;
	}
	
	public  void changeusername(String oldusername,String newusername){
		
		dbhandler.updateUsername(oldusername, newusername);
    }
	public void changepassword(String username,String newpassword){
		dbhandler.updatePassword(username, newpassword);
	}
       
		
		
	
}