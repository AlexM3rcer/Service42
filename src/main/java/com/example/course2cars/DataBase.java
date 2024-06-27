package com.example.course2cars;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Класс для соединения с базой данных
public class DataBase extends Config {
    Connection dbConnection;

    private DataBase() {
    }

    private static class SingletonHolder {
        public static final DataBase DATA_BASE = new DataBase();
    }

    public static DataBase getDB() {
        return SingletonHolder.DATA_BASE;
    }

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {

        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName; // "ссылка" для соединения

        Class.forName("com.mysql.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPassword); // Вход по "ссылке"

        return dbConnection;
    }

    public void registerUser(Owner owner) {
        try {
            String insert = "INSERT INTO " + DBnames.OWNER_TABLE + "(" + //sql-запрос для ввода данных
                DBnames.OWNER_NAME + "," + DBnames.OWNER_ADDRESS + "," +
                DBnames.OWNER_PHONE + "," + DBnames.OWNER_LOGIN + "," + DBnames.OWNER_PASSWORD + ")" + "VALUES(?, ?, ?, ?, ?)";

            PreparedStatement ps = getDbConnection().prepareStatement(insert); // Указание заготовки для подготовки к записи в базу данных
            ps.setString(1, owner.getName());
            ps.setString(2, owner.getAddress());
            ps.setString(3, owner.getPhone());
            ps.setString(4, owner.getLogin());
            ps.setString(5, owner.getPassword());
            ps.executeUpdate(); // Запись
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet getOwner(User owner) { // Получаем пользователя для авторизации
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + DBnames.OWNER_TABLE + " WHERE " + // sql-запрос для выборки данных
                DBnames.OWNER_LOGIN + "= ? AND " + DBnames.OWNER_PASSWORD + "= ?";

        PreparedStatement ps;
        try {
            ps = getDbConnection().prepareStatement(select); // Указание заготовки для подготовки к чтению
            ps.setString(1, owner.getLogin());
            ps.setString(2, owner.getPassword());
            resultSet = ps.executeQuery(); // Получение данных из запроса
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    public ResultSet getStaff(User staff) { // Получаем пользователя для авторизации
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + DBnames.STAFF_TABLE + " WHERE " + // sql-запрос для выборки данных
                DBnames.STAFF_LOGIN + "= ? AND " + DBnames.STAFF_PASSWORD + "= ?";

        PreparedStatement ps;
        try {
            ps = getDbConnection().prepareStatement(select); // Указание заготовки для подготовки к чтению
            ps.setString(1, staff.getLogin());
            ps.setString(2, staff.getPassword());
            resultSet = ps.executeQuery(); // Получение данных из запроса
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    public void updateOwner(Owner owner) {
        String update = "UPDATE " + DBnames.OWNER_TABLE +
                " SET " + DBnames.OWNER_NAME + "= ? " +
                " SET " + DBnames.OWNER_ADDRESS + "= ? " +
                " SET " + DBnames.OWNER_PHONE + "= ? " +
                " SET " + DBnames.OWNER_LOGIN + "= ? " +
                " SET " + DBnames.OWNER_PASSWORD +"= ? WHERE "
                + DBnames.OWNER_ID + " = " + currentOwner.getId();
        try {
            PreparedStatement ps = getDbConnection().prepareStatement(update);
            ps.setString(1, owner.getName());
            ps.setString(2, owner.getAddress());
            ps.setString(3, owner.getPhone());
            ps.setString(4, owner.getLogin());
            ps.setString(5, owner.getPassword());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    // Запросы на обновление данных по отдельности, оказались не нужны
//
//    public void updateOwnerPhone(String phone) {
//        String update = "UPDATE " + DBnames.OWNER_TABLE + " SET " + DBnames.OWNER_PHONE + "= ? WHERE "
//                + DBnames.OWNER_LOGIN + " = '" + currentOwner.getLogin() + "'";
//        try {
//            PreparedStatement ps = getDbConnection().prepareStatement(update);
//            ps.setString(1, phone);
//            ps.executeUpdate();
//            ps.close();
//        } catch (SQLException | ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public void updateOwnerName(String name) {
//        String update = "UPDATE " + DBnames.OWNER_TABLE + " SET " + DBnames.OWNER_NAME + "= ? WHERE "
//                + DBnames.OWNER_LOGIN + " = '" + currentOwner.getLogin() + "'";
//        try {
//            PreparedStatement ps = getDbConnection().prepareStatement(update);
//            ps.setString(1, name);
//            ps.executeUpdate();
//            ps.close();
//        } catch (SQLException | ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public void updateOwnerLogin(String login) {
//        String update = "UPDATE " + DBnames.OWNER_TABLE + " SET " + DBnames.OWNER_LOGIN + "= ? WHERE "
//                + DBnames.OWNER_LOGIN + " = '" + currentOwner.getLogin() + "'";
//        try {
//            PreparedStatement ps = getDbConnection().prepareStatement(update);
//            ps.setString(1, login);
//            ps.executeUpdate();
//            ps.close();
//        } catch (SQLException | ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public void updateOwnerPassword(String password) {
//        String update = "UPDATE " + DBnames.OWNER_TABLE + " SET " + DBnames.OWNER_PASSWORD + "= ? WHERE "
//                + DBnames.OWNER_LOGIN + " = '" + currentOwner.getLogin() + "'";
//        try {
//            PreparedStatement ps = getDbConnection().prepareStatement(update);
//            ps.setString(1, password);
//            ps.executeUpdate();
//            ps.close();
//        } catch (SQLException | ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    public void updateOwnerAddress(String address) {
//        String update = "UPDATE " + DBnames.OWNER_TABLE + " SET " + DBnames.OWNER_ADDRESS + "= ? WHERE "
//                + DBnames.OWNER_LOGIN + " = '" + currentOwner.getLogin() + "'";
//        try {
//            PreparedStatement ps = getDbConnection().prepareStatement(update);
//            ps.setString(1, address);
//            ps.executeUpdate();
//            ps.close();
//        } catch (SQLException | ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public ArrayList<Car> getOwnerCars() throws SQLException, ClassNotFoundException {
        return new Cars(getDbConnection()).getOwnerCars();
    }

    public ArrayList<Car> getAllCars() throws SQLException, ClassNotFoundException {
        return new Cars(getDbConnection()).getAllCars();
    }
}
