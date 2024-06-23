package com.example.course2cars;

import java.sql.*;

// Класс для соединения с базой данных
public class DataBase extends Config {
    Connection dbConnection;

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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet getOwner(Owner owner) { // Получаем пользователя для авторизации
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + DBnames.OWNER_TABLE + " WHERE " + // sql-запрос для выборки данных
                DBnames.OWNER_LOGIN + "= ? AND " + DBnames.OWNER_PASSWORD + "= ?";

        PreparedStatement ps;
        try {
            ps = getDbConnection().prepareStatement(select); // Указание заготовки для подготовки к чтению
            ps.setString(1, owner.getLogin());
            ps.setString(2, owner.getPassword());
            resultSet = ps.executeQuery(); // Получение данных из запроса
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    public ResultSet getCatalogs() {
        return null;
    }
}
