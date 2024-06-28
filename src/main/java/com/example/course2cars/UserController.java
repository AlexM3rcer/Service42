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
public class UserController {
    @FXML
    private Label incorrectData;
    @FXML
    private Button auth;
    @FXML
    private Button reg;
    @FXML
    private TextField loginField;
    @FXML
    private TextField passField;
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
        DataBase db = DataBase.getDB(); // Объект класса базы данных для регистрации

        // Делаем заготовку для записи в базу данных
        Owner owner = new Owner(regPhone.getText(), regAddress.getText(), regName.getText(), regLogin.getText(), regPassword.getText());

        db.registerUser(owner); // при нажатии на кнопку regFinish заносим запись в базу данных

        regComplete.setText("Complete!");
    }

    boolean authorize() {
        DataBase db = DataBase.getDB();
        User user = new User();
        user.setLogin(loginField.getText()); // Присваиваем текст из поля логина
        user.setPassword(passField.getText()); // Присваиваем текст из поля пароля
        try {
            ResultSet resultSet = db.getStaff(user); // Присваиваем resultSet итог выборки данных (пользователя)
            if (resultSet.next()) {
                Staff staff = new Staff(user);
                Config.currentStaff = staff;

                staff.setName(resultSet.getString(DBnames.STAFF_NAME));
                staff.setId(resultSet.getInt(DBnames.STAFF_ID));
                staff.setAddress(resultSet.getString(DBnames.STAFF_ADDRESS));
                staff.setAssembles(db.getAssembles());
                Config.staffController = new StaffController();

                return true;
            } else {

                resultSet = db.getOwner(user);

                if (resultSet.next()) { // Если есть пользователь - возвращается true
//                Config.setDbUser("user");
                    Owner owner = new Owner(user);
                    Config.currentOwner = owner;

                    owner.setName(resultSet.getString(DBnames.OWNER_NAME));
                    owner.setAddress(resultSet.getString(DBnames.OWNER_ADDRESS));
                    owner.setPhone(resultSet.getString(DBnames.OWNER_PHONE));
                    owner.setId(resultSet.getInt(DBnames.OWNER_ID));
                    owner.setCars(db.getOwnerCars());
                    owner.setAssembles(db.getAssembles());
                    Config.ownerController = new OwnerController();

                    return true;
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        incorrectData.setText("INCORRECT DATA");
        return false;
    }

    @FXML
    private void changeWindow(ActionEvent event) throws Exception { // Для смены окна
        Stage stage;
        Parent root;
        Button button = (Button) event.getSource(); // Получаем кнопку, которая произвела нажатие
        String newWindow = "";

        // Проверяем, какая кнопка была нажата
        // и ставим разные окна в зависимости от этого
        if (button == reg) { // переход на окно регистрации
            newWindow = "reg.fxml";
        } else if (button == auth && authorize()) { // Попытка авторизации
            newWindow = "main_page.fxml";
        } else if (button == authorization) {
            newWindow = "view.fxml";
        } else {
            return; // Если не получилось - метод заканчивает работу (во избежание ошибок в консоли)
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource(newWindow));
        if (Config.currentUser().equals(Config.currentStaff)) {
            loader.setController(Config.staffController);
        } else {
            loader.setController(Config.ownerController);
        }
        stage = (Stage) button.getScene().getWindow(); // получаем окно этой кнопки
        root = loader.load();
        Scene scene = new Scene(root); // Получаем новое окно
        stage.setScene(scene); // Ставим новое окно вместо старого
        stage.show();
    }
}