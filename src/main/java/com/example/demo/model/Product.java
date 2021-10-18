package com.example.demo.model;

import org.dizitart.no2.objects.Id;

public class Product {

    @Id
    private String code;
    private String name;
    private String category;
    private Integer quantity;
    private Integer price;

    public Product() {
    }


    public Product(String name, String category, String code, Integer quantity, Integer price) {
        this.name = name;
        this.category = category;
        this.code = code;
        this.quantity = quantity;
        this.price = price;
    }

    public Product(String name, Integer quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public Product(String name, Integer quantity, Integer price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() { return category;}
    public void setCategory(String category) { this.category = category;}

    public String getCode() { return code; }
    public void setCode(String code) {this.code = code;}

    public int getQuantity() { return quantity;}
    public void setQuantity(int quantity) { this.quantity = quantity;}

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (!name.equals(product.name)) return false;
        return quantity<product.quantity;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + category.hashCode();
        result = 31 * result + code.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", code='" + code + '\'' +
                ", quantity=" + quantity +
                '}';
    }

}
