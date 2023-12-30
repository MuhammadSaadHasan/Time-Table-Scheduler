package application;


import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import DATABASE.CourseDBH;
import Tables.ClassesTable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.web.WebView;
import java.nio.file.Files;
import java.nio.file.Paths;


import java.io.IOException;
import Users.Student;
import Users.Admin;

public class MainSceneController implements Initializable {

    @FXML private StackPane mainStackPane;
    @FXML private VBox mainMenu;
    @FXML private VBox registrationPage;
    @FXML private VBox loginPage;
    @FXML private VBox studentInfoPage;
    @FXML private VBox excelDisplay;
    @FXML private VBox userprofile;
    @FXML private VBox editstudentprofilePage;
    @FXML private VBox editstudentInfoPage;
    @FXML private VBox editStudentUsernamePage;
    @FXML private VBox editStudentPasswordPage;
    @FXML private VBox TimeTablePage;
    @FXML private VBox darkThemeUserProfile;
    
    @FXML private TextField regUsername;
    @FXML private PasswordField regPassword;
    @FXML private TextField loginUsername;
    @FXML private PasswordField loginPassword;
    @FXML private Label usernameErrorLabel;
    @FXML private TextField nameField;
    @FXML private TextField degreeField;
    @FXML private TextField batchField;
    @FXML private TextField rollNumberField;
    
    @FXML private TextField editnameField;
    @FXML private TextField editdegreeField;
    @FXML private TextField editbatchField;
    @FXML private TextField editrollNumberField;
    @FXML private TextField editusernameField;
    @FXML private TextField editpasswordField; 
    
    @FXML private TabPane tabPane;
    @FXML private TabPane TimetableTabPane;
    @FXML private Label nameLabel;
    @FXML private Label	rollNumberLabel;
    @FXML private Label	batchLabel;
    @FXML private Label	degreeLabel;
    @FXML private Label	usernameLabel;
    private Student stu;
    private Admin daman;
    private ClassesTable classesTable;
    boolean adminlogged = false;
    public CourseDBH TheCoursesBeingTaken = new CourseDBH();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	mainMenu.setVisible(true);         // Only the main menu is visible initially
        registrationPage.setVisible(false); // Hide registration page
        loginPage.setVisible(false);        // Hide login page
        studentInfoPage.setVisible(false);
        userprofile.setVisible(false);
    }

    public void handleLoginAccount() {
    	mainMenu.setVisible(false);
        studentInfoPage.setVisible(false);
        registrationPage.setVisible(false);
        loginPage.setVisible(true);
    }

    public void handleRegisterAccount() {
    	   mainMenu.setVisible(false);
           studentInfoPage.setVisible(false);
           loginPage.setVisible(false);
           registrationPage.setVisible(true);
    }
    
    
    private void showStudentInfoPage() {
        registrationPage.setVisible(false);
        loginPage.setVisible(false);
        mainMenu.setVisible(false);
        studentInfoPage.setVisible(true);
    }
    
    
    public void showMainMenu() {
        registrationPage.setVisible(false);
        loginPage.setVisible(false);
        studentInfoPage.setVisible(false);
        mainMenu.setVisible(true);
    }

    
    public void showCourseListPage()
    {
    	registrationPage.setVisible(false);
        loginPage.setVisible(false);
        studentInfoPage.setVisible(false);
        mainMenu.setVisible(false);
        excelDisplay.setVisible(true);
        
    	
    }
    
    

    public void handleShareAction() 
    {
        System.out.println("Text file was created successfully.");
    }

    
   
    public void HandleGenerateTimeTable()
    {
    	mainMenu.setVisible(true);         // Only the main menu is visible initially
        registrationPage.setVisible(false); // Hide registration page
        loginPage.setVisible(false);        // Hide login page
        studentInfoPage.setVisible(false);
        userprofile.setVisible(false);
        TimeTablePage.setVisible(true);
        
    }
    

  
    public void SubmitGenerateTimeTable() {
        if (classesTable == null) {
            String timeTablePath = "C:\\Users\\muham\\eclipse-workspace\\TimeTableSchedular\\ExcelData\\TimeTable.xlsx";
            classesTable = new ClassesTable(timeTablePath, this.TimetableTabPane); // Use the FXML TabPane
        }

	    List<String> courses = TheCoursesBeingTaken.getCoursesForUser(stu.getUsername());

	    // Check if the courses list is not empty
	    if (courses != null && !courses.isEmpty()) {
	        System.out.println("Courses for " + stu.getUsername() + ":");
	        for (String course : courses) {
	            System.out.println(course);
	        }
	    } else {
	        System.out.println("No courses found for " + stu.getUsername());
	    }
        
        
        // Load and display the timetable
        classesTable.loadExcelData(classesTable.getTablePath(), courses);

        // Make sure the TabPane (or its container) is visible
        TimeTablePage.setVisible(true); // Assuming TimeTablePage is the container of the tabPane
    }

    
    
    public void displayuserprofile()
    {
    	mainMenu.setVisible(true);         // Only the main menu is visible initially
        registrationPage.setVisible(false); // Hide registration page
        loginPage.setVisible(false);        // Hide login page
        studentInfoPage.setVisible(false);
        userprofile.setVisible(true);
    	
    }
    
    public void showeditprofile()
    {
    	editstudentprofilePage.setVisible(true);
    }
    
    
    public void showeditinfo()
    {
    	editstudentInfoPage.setVisible(true);
    }
    
    public void showeditpassword()
    {
    	editStudentPasswordPage.setVisible(true);
    }
    public void showeditusername()
    {
    	editStudentUsernamePage.setVisible(true);
    }
    

    public void submitRegistration() {
    	usernameErrorLabel.setVisible(false);
    	
    	
        String username = regUsername.getText();
        String password = regPassword.getText();
        System.out.println("Registration - Username: " + username + ", Password: " + password);
        // Additional registration logic here
        stu = new Student();
        if (  !stu.signup(username, password, "student") )
        {
        	System.out.println("USER ALREADY EXISTS");
        }
        else
        {
        	showStudentInfoPage();
        	System.out.println("USER REGISTERD");
        }
        

    }

    
    
    public void submitLogin() {
        String username = loginUsername.getText();
        String password = loginPassword.getText();
        System.out.println("Login - Username: " + username + ", Password: " + password);
        // Additional login logic here
        stu = new Student();
        daman = new Admin();
        if (stu.login(username, password, "student") )
        {
        	loginPage.setVisible(false);
        	userprofile.setVisible(true);
        	stu.getdetails(stu.getUsername());
        	// this doesnt work
        	Platform.runLater(() -> {
        		nameLabel.setText(stu.Name); 				nameLabel.setStyle("-fx-text-fill: black;");  		nameLabel.setVisible(true); 
                rollNumberLabel.setText(stu.Rollnumber); 	rollNumberLabel.setStyle("-fx-text-fill: black;");	rollNumberLabel.setVisible(true);
                batchLabel.setText(stu.Batch); 				batchLabel.setStyle("-fx-text-fill: black;");		batchLabel.setVisible(true);
                degreeLabel.setText(stu.Degree);			degreeLabel.setStyle("-fx-text-fill: black;");		degreeLabel.setVisible(true);
                
        	});
        	System.out.println("USER LOGGED IN");
        }
        else if (daman.login(username, password, "admin") )  
        {
        	loginPage.setVisible(false);
        	// show admin page
        	System.out.println("ADMIN LOGGED IN");
        }
        else
        {
        	System.out.println("LOGIN FAILED");
        }
    }
    
    public void submitSelectedCourses()
    {
    	stu.getCourses().handleSubmitSelectedCourses();
    	stu.getCourses().insertcoursestodb(stu.getUsername());
    }
    public void submitStudentInfo() {
    	
    
        String name = nameField.getText();
        String degree = degreeField.getText();
        String batch = batchField.getText();
        String rollNumber = rollNumberField.getText();
        stu.setName(name);
    	stu.setDegree(degree);
    	stu.setBatch(batch);
    	stu.setRollnumber(rollNumber);
    	//submissionLabel.setVisible(true);
    	stu.enterdetails(stu.getUsername(),stu.getPassword());
    	System.out.println("Student Info - Name: " + name + " Year: " + degree + " Batch: " + batch + " Roll Number: " + rollNumber);
    	stu.entercourses(stu.getUsername());
    	if (stu != null) {
            if (stu.getCourses() != null) {
                stu.getCourses().setTabPane(tabPane); // Set tabPane
                stu.getCourses().handleReadExcel();
            }
        }	
    	showCourseListPage();
    }
    public void userchangeusername()
    {
    	String newusername = editusernameField.getText();
    	String oldusername = stu.getUsername();
    	stu.changeusername(oldusername, newusername);
    }
    public void userchangepassword()
    {
    	stu.changepassword(stu.getUsername(), editpasswordField.getText());
    }
    public void userchangeprofile()
    {
    	stu.setName( editnameField.getText()); 
    	stu.setDegree(editdegreeField.getText()); 
    	stu.setBatch( editbatchField.getText()); 
    	stu.setRollnumber(editrollNumberField.getText()); 
    	stu.updateprofile(stu.getUsername());
    }
    
   

    public void displayHtmlContent() {
        try {
            WebView webView = new WebView();

            // Load HTML content from file
            String htmlContent = new String(Files.readAllBytes(Paths.get("C:\\Users\\muham\\eclipse-workspace\\TimeTableSchedular\\src\\GetAnyQRcodeForURL.html")));
            webView.getEngine().loadContent(htmlContent);

            // Clear the stack pane and add the web view
            mainStackPane.getChildren().clear();
            mainStackPane.getChildren().add(webView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


   
    @FXML
    private void toggleDarkMode() {
        URL url = getClass().getResource("DarkMode.css");
        if (url == null) {
            System.out.println("DarkMode.css file not found");
            return;
        }

        String darkModeCss = url.toExternalForm();
        Scene scene = mainStackPane.getScene();

        if (scene.getStylesheets().contains(darkModeCss)) {
            scene.getStylesheets().remove(darkModeCss);
            userprofile.getStyleClass().remove("dark-theme");
        } else {
            scene.getStylesheets().add(darkModeCss);
            userprofile.getStyleClass().add("dark-theme");
        }
    }


    
    
}
