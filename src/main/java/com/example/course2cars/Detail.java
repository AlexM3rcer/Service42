package com.example.course2cars;

import java.net.PortUnreachableException;
import java.util.ArrayList;

public class Detail {
    private String number;
    private int price;
    private String category;
    private int amount;
    private ArrayList<String> models = new ArrayList<>();

    public Detail(String number, int price, String category, int amount) {
        this.number = number;
        this.price = price;
        this.category = category;
        this.amount = amount;
    }

    public ArrayList<String> getModels() {
        return models;
    }

    public void setModels(ArrayList<String> models) {
        this.models = models;
    }

    public void addModel(String model) {
        models.add(model);
    }

    public Detail() {

    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
