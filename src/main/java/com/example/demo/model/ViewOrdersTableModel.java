package com.example.demo.model;

import org.dizitart.no2.objects.Id;

import java.util.Objects;

public class ViewOrdersTableModel {
    @Id
    private int number;
    private Integer pretTotal;
    private String user;

    public ViewOrdersTableModel(int number, int pretTotal, String user) {
        this.number = number;
        this.pretTotal = pretTotal;
        this.user = user;
    }

    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }

    public Integer getPretTotal() {
        return pretTotal;
    }
    public void setPretTotal(Integer pretTotal) {
        this.pretTotal = pretTotal;
    }

    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ViewOrdersTableModel that = (ViewOrdersTableModel) o;
        return number == that.number && pretTotal == that.pretTotal && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, pretTotal, user);
    }
}
