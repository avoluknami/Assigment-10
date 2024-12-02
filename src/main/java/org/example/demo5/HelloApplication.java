package org.example.demo5;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.demo5.Employee;
import org.example.demo5.FullTimeEmployee;
import org.example.demo5.PartTimeEmployee;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;


public class HelloApplication extends Application {

    private TableView<Employee> tableView;
    private ObservableList<Employee> employees;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Employee Management");

        employees = FXCollections.observableArrayList();
        tableView = new TableView<>(employees);

        setupTableView();

        VBox layout = new VBox(10, tableView, createInputForm());
        Scene scene = new Scene(layout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setupTableView() {
        TableColumn<Employee, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));

        TableColumn<Employee, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getClass().getSimpleName()));

        TableColumn<Employee, Double> salaryColumn = new TableColumn<>("Salary");
        salaryColumn.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().calculateSalary()).asObject());

        tableView.getColumns().addAll(nameColumn, typeColumn, salaryColumn);
    }

    private GridPane createInputForm() {
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);

        TextField nameField = new TextField();
        ComboBox<String> typeComboBox = new ComboBox<>(FXCollections.observableArrayList("FullTime", "PartTime", "Contractor"));
        TextField hourlyRateField = new TextField();
        TextField hoursField = new TextField();
        TextField annualSalaryField = new TextField();
        Button addButton = new Button("Add Employee");

        form.add(new Label("Name:"), 0, 0);
        form.add(nameField, 1, 0);
        form.add(new Label("Type:"), 0, 1);
        form.add(typeComboBox, 1, 1);
        form.add(new Label("Hourly Rate:"), 0, 2);
        form.add(hourlyRateField, 1, 2);
        form.add(new Label("Hours:"), 0, 3);
        form.add(hoursField, 1, 3);
        form.add(new Label("Annual Salary:"), 0, 4);
        form.add(annualSalaryField, 1, 4);
        form.add(addButton, 1, 5);

        addButton.setOnAction(e -> {
            String name = nameField.getText();
            String type = typeComboBox.getValue();

            try {
                Employee employee = null;
                if ("FullTime".equals(type)) {
                    double salary = Double.parseDouble(annualSalaryField.getText());
                    employee = new FullTimeEmployee(name, salary);
                } else if ("PartTime".equals(type)) {
                    double rate = Double.parseDouble(hourlyRateField.getText());
                    int hours = Integer.parseInt(hoursField.getText());
                    employee = new PartTimeEmployee(name, rate, hours);
                } else if ("Contractor".equals(type)) {
                    double rate = Double.parseDouble(hourlyRateField.getText());
                    int hours = Integer.parseInt(hoursField.getText());
                    employee = new Constractor(name, rate, hours);
                }

                if (employee != null) {
                    employees.add(employee);
                    clearFields(nameField, hourlyRateField, hoursField, annualSalaryField);
                }
            } catch (Exception ex) {
                showAlert("Invalid input. Please check the fields.");
            }
        });

        return form;
    }

    private void clearFields(TextField... fields) {
        for (TextField field : fields) {
            field.clear();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.showAndWait();
    }
}
