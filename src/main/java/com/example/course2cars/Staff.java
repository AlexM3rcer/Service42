package com.example.course2cars;

public class Staff extends User {

    public Staff(User user) {
        setLogin(user.getLogin());
        setPassword(user.getPassword());
    }
}
