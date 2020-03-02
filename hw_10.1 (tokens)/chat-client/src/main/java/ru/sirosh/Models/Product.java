package ru.sirosh.Models;

public class Product {
    private Product(){}
    public long id;
    public String name;
    public String description;
    public double cost;
    public boolean isDeleted;

    public Product(String name, String description, double cost) {
        this.name = name;
        this.description = description;
        this.cost = cost;
    }

    public Product(long id, String name, String description, double cost) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cost = cost;
    }
    public Product(long id, String name, String description, double cost,boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isDeleted=isDeleted;
        this.cost = cost;

    }

}
