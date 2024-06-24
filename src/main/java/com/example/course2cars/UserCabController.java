package com.example.course2cars;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class UserCabController {
    @FXML
    public Button auto;
    @FXML
    public Button saveAddress;
    @FXML
    public TextField address;
    @FXML
    public Button savePhone;
    @FXML
    public TextField phone;
    @FXML
    public Button saveName;
    @FXML
    public TextField name;
    @FXML
    public Button userCab;
    @FXML
    public Button assemble;
    public Button saveLogin;
    public Button savePassword;
    public TextField login;
    public TextField password;

    @FXML
    private void changeInfo(ActionEvent event) {
        DataBase db = new DataBase();
        if (event.getSource() == saveName) {

            db.updateOwnerName(name.getText());

            Config.currentOwner.setName(name.getText());

        } else if (event.getSource() == savePhone) {

            db.updateOwnerPhone(phone.getText());

            Config.currentOwner.setPhone(phone.getText());

        } else if (event.getSource() == saveAddress) {

            db.updateOwnerAddress(address.getText());

            Config.currentOwner.setAddress(address.getText());

        } else if (event.getSource() == savePassword) {

            db.updateOwnerPassword(password.getText());

            Config.currentOwner.setPassword(password.getText());

        } else if (event.getSource() == saveLogin) {

            db.updateOwnerLogin(login.getText());

            Config.currentOwner.setLogin(login.getText());
        }
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
