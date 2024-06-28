package com.example.course2cars;

import java.sql.*;
import java.util.ArrayList;

public class Details {
    Connection connection;

    public Details(Connection connection) {
        this.connection = connection;
    }

    public ArrayList<Detail> getDetails() {
        ArrayList<Detail> details = new ArrayList<>();
        try (Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + DBnames.DETAILS_TABLE);
            while (resultSet.next()) {
                Detail detail = new Detail(resultSet.getString(DBnames.DETAILS_NUMBER),
                        resultSet.getInt(DBnames.DETAILS_PRICE), resultSet.getString(DBnames.DETAILS_CATEGORY),
                        resultSet.getInt(DBnames.DETAILS_AMOUNT));
                String select = "SELECT " + DBnames.MODEL_DETAILS_MODEL + " FROM " +
                        DBnames.MODEL_DETAILS_TABLE + " WHERE " + DBnames.MODEL_DETAILS_DETAIL + " = ?";
                PreparedStatement ps = DataBase.getDB().getDbConnection().prepareStatement(select);
                ps.setString(1, detail.getNumber());
                ResultSet resultSet1 = ps.executeQuery();
                while (resultSet1.next()) {
                    detail.addModel(resultSet1.getString(DBnames.MODEL_DETAILS_MODEL));
                }
                details.add(detail);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return details;
    }
}
