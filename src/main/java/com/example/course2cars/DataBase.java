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
                DBnames.OWNER_PHONE + "," + DBnames.OWNER_LOGIN + "," + DBnames.OWNER_PASSWORD + ") VALUES(?, ?, ?, ?, ?)";

            PreparedStatement ps = getDbConnection().prepareStatement(insert); // Указание заготовки для подготовки к записи в базу данных
            ps.setString(1, owner.getName());
            ps.setString(2, owner.getAddress());
            ps.setString(3, owner.getPhone());
            ps.setString(4, owner.getLogin());
            ps.setString(5, owner.getPassword());
            ps.executeUpdate(); // Запись
            ps.close();
            dbConnection.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void addCar(Car car) {
        try {
            String insert = "INSERT INTO " + DBnames.CARS_TABLE + "(" +
                    DBnames.CARS_NUMBER + ", " + DBnames.CARS_COLOR + ", " +
                    DBnames.CARS_STAMP + ", " + DBnames.CARS_MODEL + ", " +
                    DBnames.CARS_OWNER_ID + ", " + DBnames.CARS_MILEAGE +") VALUES(?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = getDbConnection().prepareStatement(insert);
            ps.setString(1, car.getNumber());
            ps.setString(2, car.getColor());
            ps.setString(3, car.getStamp());
            ps.setString(4, car.getModel());
            ps.setInt(5, car.getOwner_id());
            ps.setInt(6, car.getMileage());
            ps.executeUpdate();
            ps.close();
            dbConnection.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void addAssemble(Assemble assemble) {
        try {
            String insert = "INSERT INTO " + DBnames.INSTALLATIONS_TABLE + "(" +
                    DBnames.INSTALLATIONS_START + ", " +
                    DBnames.INSTALLATIONS_END + ", " + DBnames.INSTALLATIONS_CAR + ", " +
                    DBnames.INSTALLATIONS_DETAIL + ", " + DBnames.INSTALLATIONS_WORKING + ", " +
                    DBnames.INSTALLATIONS_STAFF + ") VALUES(?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = getDbConnection().prepareStatement(insert);
            ps.setDate(1, assemble.getStart_date());
            ps.setDate(2, assemble.getEnd_date());
            ps.setString(3, assemble.getCar_number());
            ps.setString(4, assemble.getDetail_number());
            ps.setTime(5, assemble.getWorking_time());
            ps.setInt(6, assemble.getStaff_id());
            ps.executeUpdate();

            removeDetail(assemble.getDetail_number());

            ps.close();
            dbConnection.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeDetail(String detailNum) throws SQLException, ClassNotFoundException {
        String update = "UPDATE " + DBnames.DETAILS_TABLE + " SET " +
                DBnames.DETAILS_AMOUNT + " = " + DBnames.DETAILS_AMOUNT + "-1" +
                " WHERE " + DBnames.DETAILS_NUMBER + " = ?";
        PreparedStatement ps = getDbConnection().prepareStatement(update);
        ps.setString(1, detailNum);
        ps.executeUpdate();
        ps.close();
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

    public ArrayList<Staff> getAllStaff() throws SQLException {
        ResultSet resultSet = null;
        ArrayList<Staff> allStaff = new ArrayList<>();

        String select = "SELECT * FROM " + DBnames.STAFF_TABLE;

        PreparedStatement ps;
        try {
            ps = getDbConnection().prepareStatement(select); // Указание заготовки для подготовки к чтению
            resultSet = ps.executeQuery(); // Получение данных из запроса
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (resultSet.next()) {
            Staff staff = new Staff(resultSet.getString(DBnames.STAFF_NAME), resultSet.getInt(DBnames.STAFF_ID));
            allStaff.add(staff);
        }
        resultSet.close();
        ps.close();
        dbConnection.close();
        return allStaff;
    }

    public void updateOwner(Owner owner) {
        String update = "UPDATE " + DBnames.OWNER_TABLE +
                " SET " + DBnames.OWNER_NAME + "= ?, "
                + DBnames.OWNER_ADDRESS + "= ?, "
                + DBnames.OWNER_PHONE + "= ?, "
                + DBnames.OWNER_LOGIN + "= ?, "
                + DBnames.OWNER_PASSWORD +"= ? WHERE "
                + DBnames.OWNER_ID + " = " + currentOwner.getId();
        try {
            PreparedStatement ps = getDbConnection().prepareStatement(update);
            ps.setString(1, owner.getName());
            ps.setString(2, owner.getAddress());
            ps.setString(3, owner.getPhone());
            ps.setString(4, owner.getLogin());
            ps.setString(5, owner.getPassword());
            ps.executeUpdate();
            dbConnection.close();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void updateStaff(Staff staff) {
        String update = "UPDATE " + DBnames.STAFF_TABLE +
                " SET " + DBnames.STAFF_NAME + "= ?, "
                + DBnames.STAFF_ADDRESS + "= ?, "
                + DBnames.STAFF_LOGIN + "= ?, "
                + DBnames.STAFF_PASSWORD +"= ? WHERE "
                + DBnames.STAFF_ID + " = " + currentStaff.getId();
        try {
            PreparedStatement ps = getDbConnection().prepareStatement(update);
            ps.setString(1, staff.getName());
            ps.setString(2, staff.getAddress());
            ps.setString(3, staff.getLogin());
            ps.setString(4, staff.getPassword());
            ps.executeUpdate();
            ps.close();
            dbConnection.close();
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

    public ArrayList<Assemble> getAssembles() throws SQLException, ClassNotFoundException {
        return new Assembles(getDbConnection()).getAssembles();
    }

    public ArrayList<Detail> getDetails() throws SQLException, ClassNotFoundException {
        return new Details(getDbConnection()).getDetails();
    }

    public String getModel(String num) throws SQLException, ClassNotFoundException {
        String select = "SELECT " + DBnames.CARS_MODEL + " FROM " +
                DBnames.CARS_TABLE + " WHERE " + DBnames.CARS_NUMBER + " = ?";
        PreparedStatement ps = DataBase.getDB().getDbConnection().prepareStatement(select);
        ps.setString(1, num);
        ResultSet resultSet1 = ps.executeQuery();
        resultSet1.next();
        String model = resultSet1.getString(DBnames.CARS_MODEL);
        resultSet1.close();
        return model;
    }

    public void updateAssemble(Assemble assemble) {
        try {
            String update = "UPDATE " + DBnames.INSTALLATIONS_TABLE + " SET " +
                    DBnames.INSTALLATIONS_END + " = ?, " + DBnames.INSTALLATIONS_WORKING + " = ? WHERE " +
                    DBnames.INSTALLATIONS_ID + " = " + assemble.getId();
            PreparedStatement ps = getDbConnection().prepareStatement(update);
            ps.setDate(1, assemble.getEnd_date());
            ps.setTime(2, assemble.getWorking_time());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public int getLastAssembleId() {
        try {
            String select = "SELECT " + DBnames.INSTALLATIONS_ID + " FROM " + DBnames.INSTALLATIONS_TABLE +
                    " ORDER BY "+ DBnames.INSTALLATIONS_ID + " DESC LIMIT 1";
            PreparedStatement ps = getDbConnection().prepareStatement(select);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            return resultSet.getInt(DBnames.INSTALLATIONS_ID);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
