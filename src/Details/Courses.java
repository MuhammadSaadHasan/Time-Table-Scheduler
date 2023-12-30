package Details;

import DATABASE.CourseDBH;
import org.apache.poi.ss.usermodel.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.Node;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableRow;
import javafx.scene.input.MouseEvent;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.ResourceBundle;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.mysql.cj.jdbc.CallableStatement;

import java.util.*;

public class Courses {
	private CourseDBH dbhandler = new CourseDBH();
	public List<String> selectedCoursesList = new ArrayList<>();
//    @FXML private VBox mainMenu;
//    @FXML private VBox registrationPage;
//    @FXML private VBox loginPage;
//    @FXML private VBox studentInfoPage;
    //@FXML private VBox excelDisplay;
    @FXML private TabPane tabPane;
    
	public Courses()
	{
		selectedCoursesList = new ArrayList<>();
	}
	public void setTabPane(TabPane tabPane) {
        this.tabPane = tabPane;
    }
    public List<String> getSelectedCourses() {
        return selectedCoursesList;
    }
    public void printSelectedCourses() {
        if (selectedCoursesList.isEmpty()) {
            System.out.println("No courses selected.");
        } else {
            System.out.println("Selected Courses:");
            for (String course : selectedCoursesList) {
                System.out.println(course);
            }
        }
    }
	
	public void handleReadExcel() {
        System.out.println("Reading Excel data");
        // Hide other UI components and show excelDisplay


        String path = "C:\\Users\\muham\\eclipse-workspace\\TimeTableSchedular\\ExcelData\\CourseAllocation.xlsx";

        try (FileInputStream file = new FileInputStream(path)) {
            Workbook workbook = WorkbookFactory.create(file);

            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);
                TableView<ObservableList<String>> tableView = new TableView<>();

                processSheet(sheet, tableView);

                Tab tab = new Tab(sheet.getSheetName(), tableView);
                tabPane.getTabs().add(tab);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Implement more sophisticated error handling
        }
    }

    private void processSheet(Sheet sheet, TableView<ObservableList<String>> tableView) {
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Add mouse click event handler for non-contiguous row selection
        tableView.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            Node node = event.getPickResult().getIntersectedNode();

            // Loop to find the cell that was clicked
            while (node != null && node != tableView && !(node instanceof TableRow)) {
                node = node.getParent();
            }

            if (node instanceof TableRow) {
                // Clicked on a row
                event.consume();
                TableRow<?> row = (TableRow<?>) node;
                int index = row.getIndex();

                if (row.isSelected()) {
                    tableView.getSelectionModel().clearSelection(index);
                } else if (index < tableView.getItems().size()) {
                    tableView.getSelectionModel().select(index);
                }
            }
        });

        Iterator<Row> rowIterator = sheet.iterator();

        if (rowIterator.hasNext()) {
            createTableColumns(tableView, rowIterator.next());
        }

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            ObservableList<String> rowData = FXCollections.observableArrayList();

            Cell cell = row.getCell(2);
            if (cell != null && !getCellData(cell).equals("")) { // Check if the 3rd column cell is not null and not an empty string
                rowData.add(getCellData(cell)); // Add only the data from the 3rd column
                tableView.getItems().add(rowData); // Add rowData to tableView only if cell has data
            }
        }
    }
    
    public boolean getcoursesfromdb(String username)
    {
    	List<String> temp= selectedCoursesList;
    	selectedCoursesList =dbhandler.getCoursesForUser(username);
    	if(selectedCoursesList.equals(null))
    	{
    		selectedCoursesList = temp;
    		return false;
    	}
    	return true;
    }
    public void insertcoursestodb(String username)
    {
    	dbhandler.insertStudentCoursesList(username, selectedCoursesList);
    }
    
    
    @FXML
    public void handleSubmitSelectedCourses() {
        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
        selectedCoursesList.clear(); // Clear existing data

        if (selectedTab != null) {
            TableView<ObservableList<String>> coursesTable = (TableView<ObservableList<String>>) selectedTab.getContent();

            for (ObservableList<String> course : coursesTable.getSelectionModel().getSelectedItems()) {
                selectedCoursesList.add(course.get(0)); // Add course data to the list
            }

            for (String courseName : selectedCoursesList) {
                System.out.println(courseName); // Printing each selected course name
            }
        } else {
            System.out.println("No tab is selected.");
        }
		return;
    }
    
    private void createTableColumns(TableView<ObservableList<String>> tableView, Row headerRow) {
        Cell headerCell = headerRow.getCell(2); // Get only the header cell for the 3rd column

        if (headerCell != null) {
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(headerCell.getStringCellValue());
            column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(0)));
            tableView.getColumns().add(column);
        }
    }

    private String getCellData(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return Double.toString(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return Boolean.toString(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
        
    }
	
	
    
    
	
	
}