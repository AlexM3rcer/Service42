package com.example.course2cars;

public class Car {
    private String model;
    private String stamp;
    private String color;
    private String number;
    private int mileage;
    private int owner_id;

    public Car(String model, String stamp, String color, String number, int owner_id, int mileage) {
        this.model = model;
        this.stamp = stamp;
        this.color = color;
        this.number = number;
        this.owner_id = owner_id;
        this.mileage = mileage;
    }

    public Car() {

    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getStamp() {
        return stamp;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public boolean infoFailed() {
        return number.isEmpty() || model.isEmpty() || stamp.isEmpty() || color.isEmpty();
    }
}
