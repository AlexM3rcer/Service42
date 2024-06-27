package com.example.course2cars;

// Константы с названиями таблиц базы данных и их полей
public class DBnames {
    public static final String OWNER_TABLE = "owners";
    public static final String OWNER_NAME = "name";
    public static final String OWNER_PHONE = "phone";
    public static final String OWNER_PASSWORD = "password";
    public static final String OWNER_LOGIN = "login";
    public static final String OWNER_ADDRESS = "address";
    public static final String OWNER_ID = "id";

    public static final String STAFF_TABLE = "staff";
    public static final String STAFF_NAME = "name";
    public static final String STAFF_ID = "id";
    public static final String STAFF_ADDRESS = "address";
    public static final String STAFF_LOGIN = "login";
    public static final String STAFF_PASSWORD = "password";

    public static final String MODEL_DETAILS_TABLE = "model_details";
    public static final String MODEL_DETAILS_DETAIL = "detail_number";
    public static final String MODEL_DETAILS_MODEL = "model";

    public static final String CARS_TABLE = "cars";
    public static final String CARS_COLOR = "color";
    public static final String CARS_MODEL = "model";
    public static final String CARS_NUMBER = "number";
    public static final String CARS_OWNER_ID = "owner_id";
    public static final String CARS_STAMP = "stamp";

    public static final String DETAILS_TABLE = "details";
    public static final String DETAILS_AMOUNT = "amount";
    public static final String DETAILS_CATEGORY = "category";
    public static final String DETAILS_NUMBER = "number";
    public static final String DETAILS_PRICE = "price";

    public static final String INSTALLATIONS_TABLE = "installations";
    public static final String INSTALLATIONS_CAR = "installations";
    public static final String INSTALLATIONS_DETAIL = "installations";
    public static final String INSTALLATIONS_END = "installations";
    public static final String INSTALLATIONS_START = "installations";
    public static final String INSTALLATIONS_MILEAGE = "installations";
    public static final String INSTALLATIONS_STAFF = "installations";
    public static final String INSTALLATIONS_WORKING = "installations";
}