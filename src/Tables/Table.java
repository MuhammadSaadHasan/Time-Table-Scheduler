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
import java.util.Iterator;
import java.util.ResourceBundle;

public abstract class Table implements Initializable {

    @FXML
    private TabPane tabPane;
    private String tablepath;

    public Table(String path) {
        this.tablepath = path;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadExcelData(tablepath);
    }

    protected abstract void initializeTable(String path);

    private void loadExcelData(String path) {
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

    private void processSheet(Sheet sheet, TableView<ObservableList<String>> tableView) {
        Iterator<Row> rowIterator = sheet.iterator();

        if (rowIterator.hasNext()) {
            createTableColumns(tableView, rowIterator.next());
        }

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            ObservableList<String> rowData = FXCollections.observableArrayList();

            for (Cell cell : row) {
                rowData.add(getCellData(cell));
            }

            tableView.getItems().add(rowData);
        }
    }

    private void createTableColumns(TableView<ObservableList<String>> tableView, Row headerRow) {
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