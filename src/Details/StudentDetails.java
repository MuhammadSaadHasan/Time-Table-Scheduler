package Details;

import java.util.List;

import DATABASE.DetailsDBH;

public class StudentDetails {
	public String fname;
    public String batch;
    public String degree;
    public String rollnumber;
    public DetailsDBH dbhandler = new DetailsDBH();


    public boolean getUserProfile(String username) {
       List<String>temp = dbhandler.getUserProfile(username);
    	if(temp == null)
    	{return false;}
    	fname = temp.get(0);
    	batch = temp.get(1);
    	degree = temp.get(2);
    	rollnumber = temp.get(3);
    	return true;
    }
    public boolean enterdetails(String username)
    {
    	if(dbhandler.enterdetails(username, fname, batch, degree, rollnumber))
    	{
    		return true;
    	}
    	return false;
    }

    public boolean updateUserProfile(String Username)
    {
    	
    	if(dbhandler.updateUserProfile(Username, fname, batch, degree, rollnumber))
    	{
    		return true;
    	}
    	return false;
    }
    
    // Getters for the variables
    public String getFname() {
        return fname;
    }

    public String getBatch() {
        return batch;
    }

    public String getDegree() {
        return degree;
    }

    public String getRollNumber() {
        return rollnumber;
    }
}
