package Tables;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;

import org.apache.poi.ss.usermodel.*;

import java.net.URL;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class ClassesTable implements Initializable {

    @FXML
    private TabPane tabPane;
    private String tablepath = "C:\\Users\\muham\\eclipse-workspace\\TimeTableSchedular\\ExcelData\\TimeTable.xlsx";
    private List<String> CoursesBeingTaken;
    private TableView<ObservableList<String>> tableView; // Declare tableView as a class attribute


    public void processSheet(Sheet sheet) {
        
        tableView = new TableView<>(); // Initialize tableView here


    }
    public TableView<ObservableList<String>> getTableView() {
        return tableView;
    }
    public ClassesTable(String path, TabPane tabPane) {
        this.tablepath = path;
        this.tabPane = tabPane; // Initialize with passed TabPane
        this.CoursesBeingTaken = new ArrayList<>(); // Initialize the list

        
    }
    
    public void saveAllTabsToTxt(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {

            // Iterate through each tab in the TabPane
            for (Tab tab : tabPane.getTabs()) {
                TableView<?> tableView = (TableView<?>) tab.getContent();

                // Write the tab name as a section header
                writer.write("Tab: " + tab.getText() + "\n");

                // Writing Headers
                for (int j = 0; j < tableView.getColumns().size(); j++) {
                    writer.write(tableView.getColumns().get(j).getText() + "\t");
                }
                writer.write("\n");

                // Writing Data
                for (Object rowObject : tableView.getItems()) {
                    ObservableList<?> row = (ObservableList<?>) rowObject;
                    for (Object cell : row) {
                        writer.write(cell.toString() + "\t");
                    }
                    writer.write("\n");
                }

                writer.write("\n\n"); // Extra lines for separation between tables
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to create text file.");
        }
    }

    
   

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //loadExcelData(tablepath, courses);
    }

    public String getTablePath() {
        return this.tablepath;
    }
    public void loadExcelData(String path, List<String> courses) {
 
        this.CoursesBeingTaken = courses; 
      	
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
            // Implement more sophisticated error handling
            e.printStackTrace();
        }
    }

    public void processSheet(Sheet sheet, TableView<ObservableList<String>> tableView) {
    	int numRows = 50; // Number of rows
    	int numColumns = 18; // Number of columns
    	char[][] checkArray = new char[numRows][numColumns];

    	// Initialize all elements to '0'
    	for (int i = 0; i < numRows; i++) {
    	    for (int j = 0; j < numColumns; j++) {
    	        checkArray[i][j] = '0';
    	    }
    	}


    	// Now CheckArray has '1' at index 0 and '0' at all other indices

        Iterator<Row> rowIterator = sheet.iterator();

        if (rowIterator.hasNext()) {
            createTableColumns(tableView, rowIterator.next());
        }

        List<List<String>> tableData = new ArrayList<>();

        int rowIndex = 0;
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            ObservableList<String> rowData = FXCollections.observableArrayList();
            List<String> rowList = new ArrayList<>();

            int colIndex = 0;
            for (Cell cell : row) {
                String cellData = getCellData(cell);
                String firstWordOfCellData = cellData.split("\\s+")[0]; // Extract first word from cell data

                
                boolean isCourseMatched = false;
                for (String course : CoursesBeingTaken) {
                    String firstWordOfCourse = course.split("\\s+")[0]; // Extract first word from course
                    if (firstWordOfCellData.equals(firstWordOfCourse)) {
                        isCourseMatched = true;
                        
                        if(checkArray[rowIndex][colIndex] != 'e')
                        {
                        	checkArray[rowIndex][colIndex] = '1';                        	
                        }
                        
                        for(int t = rowIndex + 1 ; t < numRows;t++)
                        {
                        	checkArray[t][colIndex] = 'e';
                        }
                        
                        checkArray[rowIndex][0] = '1';
                        break;
                    }
                }

//                if (checkArray[rowIndex][colIndex] == '1') {
//                    rowData.add(cellData);
//                    rowList.add(cellData);
//                } 
//                else {
//                    rowData.add("");
//                    rowList.add("");
//                }
                colIndex++;
            }
            rowIndex++;

//            tableView.getItems().add(rowData);
//            tableData.add(rowList);
        }
        //tableData.get(rowIndex).get(columnIndex)

       
        
        //start coding from here
        int countRowsWithOne = 0;
        for (int i = 0; i < numRows; i++) {
            boolean oneFoundInRow = false;
            for (int j = 0; j < numColumns; j++) {
                if (checkArray[i][j] == '1') {
                    oneFoundInRow = true;
                    break; // Break the inner loop as we found a '1' in this row
                }
            }
            if (oneFoundInRow) {
                countRowsWithOne++;
            }
        }
        
        System.out.println(countRowsWithOne);
        
        
        
     // Reinitialize the iterator for the sheet
        rowIterator = sheet.iterator(); 

        if (rowIterator.hasNext()) {
            rowIterator.next(); // Skip the header row
        }

        int r = 0;
        while (rowIterator.hasNext()) {
            ObservableList<String> rowData = FXCollections.observableArrayList();
            Row row = rowIterator.next();

            boolean isRowEmpty = true; // Flag to check if the entire row is empty

            int c = 0;
            for (Cell cell : row) {
                String cellData = getCellData(cell);
                if (checkArray[r][c] == '1') {
                    rowData.add(cellData); // Add cell data if checkArray entry is '1'
                    isRowEmpty = false; // Row is not empty since we have some data
                } else {
                    rowData.add(""); // Add empty string otherwise
                }
                c++;
            }

            if (!isRowEmpty) {
                tableView.getItems().add(rowData); // Add the processed row data to the tableView only if it's not empty
            }
            r++;
        }       
        
        
        //this.saveTableViewToTxt("C:\\Users\\muham\\Downloads\\YourTimeTable.txt");
        this.saveAllTabsToTxt("C:\\Users\\muham\\Downloads\\CompleteTimeTable.txt");

    }



    
    
    


    public void createTableColumns(TableView<ObservableList<String>> tableView, Row headerRow) {
        for (Cell headerCell : headerRow) {
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(headerCell.getStringCellValue());
            int columnIndex = headerCell.getColumnIndex();

            column.setCellValueFactory(cellData -> {
                ObservableList<String> data = cellData.getValue();
                return columnIndex >= data.size() ? new SimpleStringProperty("") : new SimpleStringProperty(data.get(columnIndex));
            });

            tableView.getColumns().add(column);
        }
    }

    public String getCellData(Cell cell) {
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

    public void saveTableViewToTxt(String filePath) {
        
    }



}

