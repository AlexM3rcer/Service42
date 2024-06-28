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
import java.sql.Time;
import java.util.ArrayList;
import java.util.Objects;

public class StaffController {
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
    public VBox assembleContent;
    public ScrollPane assemblePane;
    public Label userCabPhone;
    public ChoiceBox endAssembleNum;
    public TextField endAssembleWork;
    public Button endAssembleConfirmButton;
    public Button addAssembleButton;
    public Label successfulChange;

    private void writeInfo() {
        idShower.setText("ID: " + Config.currentStaff.getId());
        login.setText(Config.currentStaff.getLogin());
        password.setText(Config.currentStaff.getPassword());
        address.setText(Config.currentStaff.getAddress());
        name.setText(Config.currentStaff.getName());
    }

    @FXML
    private void initialize() throws SQLException {
        if (auto != null)
            auto.setVisible(false);
        if (idShower != null) {
            writeInfo();
            phone.setVisible(false);
            userCabPhone.setVisible(false);
        } else if (assemblePane != null) {
            writeAssembles();
            addAssembleButton.setText("Завершить сборку");
        } else if (endAssembleConfirmButton != null) {
            try {
                endAssembleNum.setItems(FXCollections.observableArrayList(getAssemblesId()));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    private void writeAssembles() {
        assembleContent.getChildren().clear();
        for (Assemble assemble : Config.currentStaff.getAssembles()) {
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

    @FXML
    private void changeInfo(ActionEvent event) {
        DataBase db = DataBase.getDB();
        db.updateStaff(new Staff(address.getText(), name.getText(), login.getText(), password.getText()));
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
        } else {
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource(newWindow));
        loader.setController(Config.staffController);
        stage = (Stage) button.getScene().getWindow(); // получаем окно этой кнопки
        root = loader.load();
        Scene scene = new Scene(root); // Получаем новое окно
        stage.setScene(scene); // Ставим новое окно вместо старого
        stage.show();
    }

    @FXML
    private void addAssemble() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("end_assemble.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 600, 150);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Завершение сборки");
        stage.setScene(scene);
        stage.setOnHiding(event -> writeAssembles());
        stage.show();
    }

    private ArrayList<String> getAssemblesId() throws SQLException, ClassNotFoundException {
        ArrayList<String> ids = new ArrayList<>();
        ArrayList<Assemble> assembles = Config.currentStaff.getAssembles();
        for (Assemble assemble : assembles) {
            if (assemble.getEnd_date() == null)
                ids.add("" + assemble.getId());
        }
        return ids;
    }

    @FXML
    private void endAssembleConfirm(ActionEvent event) throws SQLException, ClassNotFoundException {
        Assemble assemble = new Assemble();
        int id = Integer.parseInt((String) endAssembleNum.getSelectionModel().getSelectedItem());
        String strTime = endAssembleWork.getText();

        Time time = new Time(getTimeString(strTime));
        assemble.setWorking_time(time);
        assemble.setId(id);
        assemble.setEnd_date(new Date(System.currentTimeMillis()));

        try {
            DataBase.getDB().updateAssemble(assemble);
            Config.currentStaff.updateAssemble(assemble);
        } catch (Exception e) {;}

        Button button = (Button) event.getSource();
        button.getScene().getWindow().hide();
    }

    private long getTimeString(String str) {
        float time = 0;
        time = (Float.parseFloat(str));
        time *= 3600000;
        time -= 3600*3000;
//        for (int i = 0; i < str.length(); i++) {
//            if (str.charAt(i) == '.') {
//                String first = str.substring(0, i) + "000";
//                String second = str.substring(i);
//                str = first + second;
//                break;
//            }
//        }
        return (long)time;
    }
}
