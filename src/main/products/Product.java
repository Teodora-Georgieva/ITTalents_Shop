package main.products;

import main.utils.Validator;

import java.util.Objects;

public abstract class Product {
    public enum Type{
        PerKilogram, PerPiece
    }

    private static int id = 1;
    private int productId;
    private String name;
    private double price;
    private Type type;

    Product(String name, double price){
        this.productId = id++;
        this.type = getType();
        if(Validator.isValidString(name)){
            this.name = name;
        }
        else{
            this.name = "product";
        }

        if(price > 0){
            this.price = price;
        }
        else{
            this.price = 5;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productId == product.productId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }

    public abstract Type getType();

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "productId = " + productId + ", name = " + name + ", price = " + price + ", type = " + type;
    }

    public double getPrice() {
        return price;
    }
}