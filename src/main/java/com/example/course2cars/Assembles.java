package com.example.course2cars;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class Assembles {
    Connection connection;

    public Assembles(Connection connection) {
        this.connection = connection;
    }

    public ArrayList<Assemble> getAssembles() throws SQLException {
        ArrayList<Assemble> assembles = new ArrayList<>();
        ResultSet resultSet = null;
        try (Statement statement = connection.createStatement()) {
            if (Config.currentOwner != null) {
                resultSet = statement.executeQuery("SELECT " +
                        DBnames.INSTALLATIONS_CAR + ", " + DBnames.INSTALLATIONS_DETAIL + ", " +
                        DBnames.INSTALLATIONS_END + ", " + DBnames.INSTALLATIONS_START + ", "
                        + DBnames.INSTALLATIONS_STAFF + ", " + DBnames.INSTALLATIONS_WORKING +
                        " FROM " + DBnames.INSTALLATIONS_TABLE + " JOIN " + DBnames.CARS_TABLE +
                        " ON " + DBnames.INSTALLATIONS_CAR + " = " + DBnames.CARS_NUMBER +
                        " WHERE " + DBnames.CARS_OWNER_ID + " = " + Config.currentOwner.getId());
            } else {
                resultSet = statement.executeQuery("SELECT * FROM " +
                        DBnames.INSTALLATIONS_TABLE +
                        " WHERE " + DBnames.STAFF_ID + " = " + Config.currentStaff.getId());
            }
            while (resultSet.next()) {
                String car_number = resultSet.getString(DBnames.INSTALLATIONS_CAR);
                String detail_number = resultSet.getString(DBnames.INSTALLATIONS_DETAIL);
                Date end_date = resultSet.getDate(DBnames.INSTALLATIONS_END);
                Date start_date = resultSet.getDate(DBnames.INSTALLATIONS_START);
                int staff_id = resultSet.getInt(DBnames.INSTALLATIONS_STAFF);
                Time working_time = resultSet.getTime(DBnames.INSTALLATIONS_WORKING);
                Assemble assemble = new Assemble(car_number, detail_number, end_date, start_date, staff_id, working_time);
                assembles.add(assemble);
            }
        }
        return assembles;
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
                int mileage = resultSet.getInt(DBnames.CARS_MILEAGE);
                Car car = new Car(model, stamp, color, number, owner_id, mileage);
                cars.add(car);
            }
        }
        return cars;
    }
}
