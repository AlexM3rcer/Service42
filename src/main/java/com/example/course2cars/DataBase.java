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
            String insert = "INSERT INTO " + DBnames.KLIENT_TABLE + "(" + //sql-запрос для ввода данных
                DBnames.KLIENT_NAME + "," + DBnames.KLIENT_PHONE + "," +
                DBnames.KLIENT_VISA + ")" + "VALUES(?, ?, ?)";

            PreparedStatement ps = getDbConnection().prepareStatement(insert); // Указание заготовки для подготовки к записи в базу данных
            ps.setString(1, owner.getName());
            ps.setString(2, owner.getPhone());
            ps.setString(3, owner.getAddress());
            ps.setString(4, owner.getLogin());
            ps.setString(5, owner.getPassword());
            ps.executeUpdate(); // Запись6
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet getUser(Owner owner) { // Получаем пользователя для авторизации
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + DBnames.KLIENT_TABLE + " WHERE " + // sql-запрос для выборки данных
                DBnames.KLIENT_PHONE + "=? AND " + DBnames.KLIENT_PASSPORT + "=?";

        PreparedStatement ps;
        try {
            ps = getDbConnection().prepareStatement(select); // Указание заготовки для подготовки к чтению
            ps.setString(1, user.getPhone());
            ps.setString(2, user.getPassport());
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
