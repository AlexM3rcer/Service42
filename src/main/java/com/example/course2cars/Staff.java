package com.example.course2cars;

public class Staff extends User {

    public Staff(String Address, String name, String login, String password) {
        setAddress(Address);
        setName(name);
        setLogin(login);
        setPassword(password);
    }

    public Staff(User user) {
        setLogin(user.getLogin());
        setPassword(user.getPassword());
    }
}
