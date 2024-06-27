package com.example.course2cars;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Cars {
    Connection connection;

    public Cars(Connection connection) {
        this.connection = connection;
    }

    public ArrayList<Car> getOwnerCars() throws SQLException {
        ArrayList<Car> cars = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + DBnames.CARS_TABLE +
                    " WHERE " + DBnames.CARS_OWNER_ID + " = " + Config.currentOwner().getId());
            while (resultSet.next()) {
                String model = resultSet.getString(DBnames.CARS_MODEL);
                String stamp = resultSet.getString(DBnames.CARS_STAMP);
                String color = resultSet.getString(DBnames.CARS_COLOR);
                String number = resultSet.getString(DBnames.CARS_NUMBER);
                int owner_id = resultSet.getInt(DBnames.CARS_OWNER_ID);
                Car car = new Car(model, stamp, color, number, owner_id);
                cars.add(car);
            }
        }
        return cars;
    }

    public ArrayList<Car> getAllCars() throws SQLException {
        ArrayList<Car> cars = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + DBnames.CARS_TABLE);
            while (resultSet.next()) {
                String model = resultSet.getString(DBnames.CARS_MODEL);
                String stamp = resultSet.getString(DBnames.CARS_STAMP);
                String color = resultSet.getString(DBnames.CARS_COLOR);
                String number = resultSet.getString(DBnames.CARS_NUMBER);
                int owner_id = resultSet.getInt(DBnames.CARS_OWNER_ID);
                Car car = new Car(model, stamp, color, number, owner_id);
                cars.add(car);
            }
        }
        return cars;
    }
}
