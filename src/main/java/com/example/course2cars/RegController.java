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

import java.util.Objects;

// Класс-контроллер для окна reg.fxml
// Здесь указаны все элементы и методы
// для этих элементов (кнопки, текстовые поля и т.д.)
public class RegController {
    @FXML
    private TextField regLogin;
    @FXML
    private TextField regPassword;
    @FXML
    private Label regComplete;
    @FXML
    private Button regFinish;
    @FXML
    private Button authorization;
    @FXML
    private TextField regName;
    @FXML
    private TextField regPhone;
    @FXML
    private TextField regAddress;
    @FXML
    void register() { // Регистрация пользователя
        DataBase db = new DataBase(); // Создаем объект класса базы данных для регистрации

        // Делаем заготовку для записи в базу данных
        Owner owner = new Owner(regPhone.getText(), regAddress.getText(), regName.getText(), regLogin.getText(), regPassword.getText());

        db.registerUser(owner); // при нажатии на кнопку regFinish заносим запись в базу данных

        regComplete.setText("Complete!");
    }
    @FXML
    private void changeWindow (ActionEvent event) throws Exception { // Для смены окна
        Stage stage;
        Parent root;
        Button button = (Button) event.getSource(); // Получаем кнопку, которая произвела нажатие
        stage = (Stage) button.getScene().getWindow(); // получаем окно этой кнопки
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("view.fxml")));
        Scene scene = new Scene(root); // Получаем новое окно
        stage.setScene(scene); // Ставим новое окно вместо старого
        stage.show();
    }
}
