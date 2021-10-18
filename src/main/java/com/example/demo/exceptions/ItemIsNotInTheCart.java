package com.example.demo.exceptions;

public class ItemIsNotInTheCart extends Exception {

    private String name;

    public ItemIsNotInTheCart(String name) {
        super(String.format("Item %s is not in the cart!", name));
        this.name = name;
    }

    public String getName() {
        return name;
    }
}