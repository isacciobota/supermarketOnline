package com.example.demo.exceptions;

public class CartIsEmptyException extends Exception {

    private String mesaj;

    public CartIsEmptyException() {
        super(String.format("Cart is empty!"));
    }

    public String getMesaj() {
        return mesaj;
    }
}