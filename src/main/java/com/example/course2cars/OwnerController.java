package com.example.course2cars;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

    @FXML
    private void initialize() {
        idShower.setText(Integer.toString(Config.currentOwner.getId()));
        login.setText(Config.currentOwner.getLogin());
        password.setText(Config.currentOwner.getPassword());
        address.setText(Config.currentOwner.getAddress());
        name.setText(Config.currentOwner.getName());
        phone.setText(Config.currentOwner.getPhone());
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
