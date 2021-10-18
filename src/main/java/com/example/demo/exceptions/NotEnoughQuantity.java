package com.example.demo.exceptions;

public class NotEnoughQuantity extends Exception {

    private String name;

    public NotEnoughQuantity(String name) {
        super(String.format("There is not enough quantity of %s available!", name));
        this.name = name;
    }

    public String getName() {
        return name;
    }
}