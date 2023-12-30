package Users;

import Details.StudentDetails;
import Details.Courses;
import Details.StudentDetails;
import Encryption.Encrypter;
import Tables.ClassesTable;
import javafx.scene.control.TabPane;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.io.File;
import java.util.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DATABASE.CourseDBH;
import DATABASE.DataBaseHandler;


public class Student extends User {

public	String Name;
public	String Rollnumber;
public	String Degree;
public	String Batch;
public 	StudentDetails Details = new StudentDetails();
private Courses courses = new Courses();
public CourseDBH TheCoursesBeingTaken = new CourseDBH();
private ClassesTable test;  // Correctly declare the ClassesTable instance
public String TimeTablePath = "C:\\Users\\muham\\eclipse-workspace\\TimeTableSchedular\\ExcelData\\TimeTable.xlsx";


	public Student()
		{
			super();  // Call the parent class constructor
			this.username = "";
			this.password ="";
			Name="";
			Rollnumber="";
			Degree="";
			Batch="";
	        // ... other initializations ...
			TabPane tabPaneForClassesTable = new TabPane(); // Create a new TabPane
	        this.test = new ClassesTable(TimeTablePath, tabPaneForClassesTable); // Pass the TabPane along with the path
	    }
	public boolean setName(String Name) { 
		    if (Name == null || Name.equals("")) {
		        return false;
		    }
		    Details.fname=Name;
		    this.Name = Name;
		    System.out.println(this.Name);
		    return true;
		}

	public boolean setRollnumber(String Rollnumber) { 
		    if (Rollnumber == null || Rollnumber.equals("")) {
		        return false;
		    }
		    Details.rollnumber = Rollnumber;
		    this.Rollnumber = Rollnumber;
		    System.out.println(this.Rollnumber);
		    return true;
		}

	public boolean setDegree(String Degree) { 
		    if (Degree == null || Degree.equals("")) {
		        return false;
		    }
		    Details.degree = Degree;
		    this.Degree = Degree;
		    System.out.println(this.Degree);
		    return true;
		}

	public boolean setBatch(String Batch) {
		    if (Batch == null || Batch.equals("")) {
		        return false;
		    }
		    Details.batch=Batch;
		    this.Batch = Batch;
		    System.out.println(this.Degree); // Check if this should be this.Batch instead
		    return true;
		}

	  public Courses getCourses() {
	        return courses;
	    }
	 
	public boolean updateprofile(String username)
	{
		if(Details.updateUserProfile(username))
		{return true;}
		return false;
	}
	 
	public boolean getcourses(String username)
	{
		if(courses.getcoursesfromdb(username))
		{
			return true;
		}
		return false;
	}

	public boolean entercourses(String username)
	{	
		courses.insertcoursestodb(username);
		return false;
	}
	
	void printdetails()
	{
		System.out.println("VAUE OF VARS BEFORE QUERY : "+Name+" "+Rollnumber+" "+Degree+" "+Batch);
	}
	
		
	public boolean enterdetails(String username , String password)
	{	
		printdetails();
		if(Details.enterdetails(username))
		{return true;}
		return false;
	}
				
	
	public boolean getdetails(String username)
	{
			if(Details.getUserProfile(username) )
			{
				this.Name = Details.getFname();
				this.Batch = Details.getBatch();
				this.Degree = Details.getDegree();
				this.Rollnumber = Details.getRollNumber();
				return true;
			}
			return false;
	}
		
	
	
//	public void GenerateTimeTable() {
//	    System.out.println("Generating timetable...");
//
//	   
//	    
//	  test.loadExcelData(TimeTablePath);
//	    
//	    
//	    
//	}


	
	
	
}