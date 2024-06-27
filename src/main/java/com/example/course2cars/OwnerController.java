package com.example.course2cars;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
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
    public Button addCarConfirm;

    private Label carFromat(Label label) {
        label.setWrapText(true);
        label.setMinWidth(carPane.getPrefWidth() / 4);
        label.setMaxWidth(carPane.getPrefWidth() / 4);
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

            carContent.getChildren().add(hBox);
        }
    }

    @FXML
    private void initialize() {
        if (idShower != null) {
            writeInfo();
        } else if (carContent != null) {
            writeCars();
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
                addCarColor.getText(), addCarNum.getText(), Config.currentOwner.getId());
        try {
            db.addCar(car);
            Config.currentOwner.addCar(car);
        } catch (Exception e){;}

        Button button = (Button) event.getSource();
        button.getScene().getWindow().hide();
    }

    @FXML
    private void changeInfo(ActionEvent event) {
        DataBase db = DataBase.getDB();
        db.updateOwner(new Owner(phone.getText(), address.getText(), name.getText(), login.getText(), password.getText()));
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

        stage = (Stage) button.getScene().getWindow(); // получаем окно этой кнопки
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(newWindow)));
        Scene scene = new Scene(root); // Получаем новое окно
        stage.setScene(scene); // Ставим новое окно вместо старого
        stage.show();
    }
}
