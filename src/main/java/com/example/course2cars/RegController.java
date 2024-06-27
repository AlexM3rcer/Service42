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
