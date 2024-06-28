package com.example.course2cars;

import java.util.ArrayList;

public class Owner extends User{
    private String phone;
    private ArrayList<Car> cars;

    public Owner(String phone, String address, String name, String login, String password) {
        this.phone = phone;
        setAddress(address);
        setName(name);
        setLogin(login);
        setPassword(password);
    }

    public Owner(User user) {
        setAddress(user.getAddress());
        setName(user.getName());
        setLogin(user.getLogin());
        setPassword(user.getPassword());
    }

    public Owner() {

    }

    public ArrayList<Car> getCars() {
        return cars;
    }

    public void setCars(ArrayList<Car> cars) {
        this.cars = cars;
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void delCar(String number) {
        for (Car car : cars) {
            if (car.getNumber().equals(number)) {
                cars.remove(car);
                break;
            }
        }
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean infoFailed() {
        return getLogin().isEmpty() || getPassword().isEmpty() || getPhone().isEmpty() || getAddress().isEmpty() || getName().isEmpty();
    }
}
