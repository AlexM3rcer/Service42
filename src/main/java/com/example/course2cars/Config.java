package com.example.course2cars;

//  Данные для входа в базу данных
public class Config {
    static String dbHost = "localhost"; // Хост базы данных
    static String dbPort = "3306"; //  Порт базы данных (по умолчанию стоит такой)
    static String dbName = "service42"; // Название базы данных
    static String dbUser = "root"; // Имя пользователя. root - по умолчанию
    static String dbPassword = "368952"; // Пароль пользователя
    static Owner currentOwner;
}
