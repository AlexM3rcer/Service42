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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

// Класс-контроллер для окна view.fxml
// Здесь указаны все элементы и методы
// для этих элементов (кнопки, текстовые поля и т.д.)
public class AuthController {
    @FXML
    private Label welcomeText;
    @FXML
    private Label incorrectData;
    @FXML
    private Button authorization;
    @FXML
    private Button reg;
    @FXML
    private Button requests;
    @FXML
    private Button user;
    @FXML
    private Button tourCatalogs;
    @FXML
    private TextField loginField;
    @FXML
    private TextField passField;

    boolean authorize() {
        DataBase db = new DataBase();
        User user = new User();
        user.setPhone(loginField.getText()); // Присваиваем user текст из поля логина
        user.setPassport(passField.getText()); // Присваиваем user текст из поля пароля
        ResultSet resultSet = db.getUser(user); // Присваиваем resultSet итог выборки данных (пользователя)
        try {
            if (resultSet.next()) { // Если есть пользователь - возвращается true
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        incorrectData.setText("INCORRECT DATA");
        return false;
    }

    @FXML
    private void handleButtonAction (ActionEvent event) throws Exception { // Для смены окна
        Stage stage;
        Parent root;
        Button button = (Button) event.getSource(); // Получаем кнопку, которая произвела нажатие
        String newWindow = "";

        // Проверяем, какая кнопка была нажата
        // и ставим разные окна в зависимости от этого
        if (button == reg) { // переход на окно регистрации
            newWindow = "reg.fxml";
        } else if (button == authorization && authorize()) { // Попытка авторизации
            newWindow = "main_page.fxml";
        } else {
            return; // Если не получилось авторизоваться - метод заканчивает работу (во избежание ошибок в консоли)
        }
        stage = (Stage) button.getScene().getWindow(); // получаем окно этой кнопки
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(newWindow)));
        Scene scene = new Scene(root); // Получаем новое окно
        stage.setScene(scene); // Ставим новое окно вместо старого
        stage.show();
    }
}