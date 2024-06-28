package com.example.course2cars;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class OwnerController {
    @FXML
    public Button auto;
    @FXML
    public Button saveNewInfo;
    @FXML
    public TextField address;
    @FXML
    public TextField phone;
    @FXML
    public TextField name;
    @FXML
    public Button userCab;
    @FXML
    public Button assemble;
    public TextField login;
    public TextField password;
    public Label idShower;
    public VBox carContent;
    public ScrollPane carPane;
    public TextField addCarNum;
    public TextField addCarStamp;
    public TextField addCarModel;
    public TextField addCarColor;
    public Button addCarConfirmButton;
    public VBox assembleContent;
    public ScrollPane assemblePane;
    public TextField addCarMileage;
    public ChoiceBox addAssembleCar;
    public ChoiceBox addAssembleStaff;
    public ChoiceBox addAssembleDetail;
    public Button addAssembleConfirmButton;
    public Label successfulChange;

    private Label carFromat(Label label) {
        label.setWrapText(true);
        label.setMinWidth(carPane.getPrefWidth() / 5);
        label.setMaxWidth(carPane.getPrefWidth() / 5);
        return label;
    }

    private void writeInfo() {
        idShower.setText("ID: " + Config.currentOwner.getId());
        login.setText(Config.currentOwner.getLogin());
        password.setText(Config.currentOwner.getPassword());
        address.setText(Config.currentOwner.getAddress());
        name.setText(Config.currentOwner.getName());
        phone.setText(Config.currentOwner.getPhone());
    }

    private void writeCars() {
        carContent.getChildren().clear();
        for (Car car : Config.currentOwner.getCars()) {
            HBox hBox = new HBox();

            hBox.getChildren().add(carFromat(new Label("Номер: " + car.getNumber())));
            hBox.getChildren().add(carFromat(new Label("Модель: " + car.getModel())));
            hBox.getChildren().add(carFromat(new Label("Марка: " + car.getStamp())));
            hBox.getChildren().add(carFromat(new Label("Цвет: " + car.getColor())));
            hBox.getChildren().add(carFromat(new Label("Прокат: " + car.getMileage())));

            carContent.getChildren().add(hBox);
        }
    }

    @FXML
    private void initialize() throws SQLException {
        if (idShower != null) {
            writeInfo();
        } else if (carPane != null) {
            writeCars();
        } else if (assemblePane != null) {
            writeAssembles();
        } else if (addAssembleCar != null) {
            setAssembleItems();
        }
    }

    @FXML
    private void addCar() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add_car.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 400, 200);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Новый автомобиль");
        stage.setScene(scene);
        stage.setOnHiding(event -> writeCars());
        stage.show();
    }

    @FXML
    private void addCarConfirm(ActionEvent event) {
        DataBase db = DataBase.getDB();
        Car car = new Car(addCarModel.getText(), addCarStamp.getText(),
                addCarColor.getText(), addCarNum.getText(), Config.currentOwner.getId(),
                Integer.parseInt(addCarMileage.getText()));
        try {
            db.addCar(car);
            Config.currentOwner.addCar(car);
        } catch (Exception e){;}

        Button button = (Button) event.getSource();
        button.getScene().getWindow().hide();
    }

    @FXML
    private void writeAssembles() {
        assembleContent.getChildren().clear();
        for (Assemble assemble : Config.currentOwner.getAssembles()) {
            HBox hBox = new HBox();

            hBox.getChildren().add(assembleFormat(new Label("" + assemble.getId())));
            hBox.getChildren().add(assembleFormat(new Label(assemble.getCar_number())));
            hBox.getChildren().add(assembleFormat(new Label(assemble.getDetail_number())));
            hBox.getChildren().add(assembleFormat(new Label("" + assemble.getStaff_id())));
            hBox.getChildren().add(assembleFormat(new Label("" + assemble.getStart_date())));
            hBox.getChildren().add(assembleFormat(new Label("" + assemble.getEnd_date())));
            hBox.getChildren().add(assembleFormat(new Label("" + assemble.getWorking_time())));

            assembleContent.getChildren().add(hBox);
        }
    }

    private Label assembleFormat(Label label) {
        label.setWrapText(true);
        label.setMinWidth(assemblePane.getPrefWidth() / 7);
        label.setMaxWidth(assemblePane.getPrefWidth() / 7);
        return label;
    }

    private ArrayList<String> carNums(){
        ArrayList<String> names = new ArrayList<>();
        for (Car car : Config.currentOwner.getCars()) {
            names.add(car.getNumber());
        }
        return names;
    }

    private ArrayList<String> staffNums() throws SQLException {
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Staff> staffs = DataBase.getDB().getAllStaff();
        for (Staff staff : staffs) {
            names.add(staff.getId() + staff.getName());
        }
        return names;
    }

    private ArrayList<String> detailNums(String carNum) throws SQLException, ClassNotFoundException {
        String carModel = DataBase.getDB().getModel(carNum);
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Detail> details = DataBase.getDB().getDetails();
        for (Detail detail : details) {
            if (detail.getAmount() == 0)
                continue;
            for (String model : detail.getModels()) {
                if (model.equals(carModel))
                    names.add(detail.getNumber());
            }
        }
        return names;
    }

    @FXML
    private void addAssemble() throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add_assemble.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 600, 250);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Новая сборка");
        stage.setScene(scene);
        stage.setOnHiding(event -> writeAssembles());
        stage.show();
    }

    private void setAssembleItems() throws SQLException {
        ArrayList<String> carNums = carNums();
        ArrayList<String> staffNames = staffNums();
        addAssembleCar.setItems(FXCollections.observableArrayList(carNums));
        addAssembleStaff.setItems(FXCollections.observableArrayList(staffNames));
    }

    @FXML
    private void setDetails() {
        try {
            addAssembleDetail.setItems(FXCollections.observableArrayList(detailNums((String)
                    addAssembleCar.getSelectionModel().getSelectedItem())));
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void addAssembleConfirm(ActionEvent event) {
        DataBase db = DataBase.getDB();
        Assemble assemble = new Assemble();
        String car = (String) addAssembleCar.getSelectionModel().getSelectedItem();
        int staff = Integer.parseInt(((String) addAssembleStaff.getSelectionModel().getSelectedItem()).substring(0, 1));
        String detail = ((String) addAssembleDetail.getSelectionModel().getSelectedItem());
        assemble.setCar_number(car);
        assemble.setDetail_number(detail);
        assemble.setStaff_id(staff);
        assemble.setStart_date(new Date(System.currentTimeMillis()));
        try {
            db.addAssemble(assemble);
            assemble.setId(db.getLastAssembleId());
            Config.currentOwner.addAssemble(assemble);
        } catch (Exception e) {;}

        Button button = (Button) event.getSource();
        button.getScene().getWindow().hide();
    }

    @FXML
    private void changeInfo(ActionEvent event) {
        DataBase db = DataBase.getDB();
        db.updateOwner(new Owner(phone.getText(), address.getText(), name.getText(), login.getText(), password.getText()));
        successfulChange.setText("Успешно");
    }

    @FXML
    private void changeWindow(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        Button button = (Button) event.getSource(); // Получаем кнопку, которая произвела нажатие
        String newWindow = "";

        // Проверяем, какая кнопка была нажата
        // и ставим разные окна в зависимости от этого
        if (button == userCab) {
            newWindow = "user_cabine.fxml";
        } else if (button == assemble) {
            newWindow = "assembles.fxml";
        } else if (button == auto) {
            newWindow = "cars.fxml";
        } else {
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource(newWindow));
        if (!(newWindow.equals("cars.fxml")))
            loader.setController(Config.ownerController);

        stage = (Stage) button.getScene().getWindow(); // получаем окно этой кнопки
        root = loader.load();
        Scene scene = new Scene(root); // Получаем новое окно
        stage.setScene(scene); // Ставим новое окно вместо старого
        stage.show();
    }
}
