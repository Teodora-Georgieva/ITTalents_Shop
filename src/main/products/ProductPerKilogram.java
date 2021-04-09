package main.products;

public class ProductPerKilogram extends Product {
    private double availableKilograms;

    public ProductPerKilogram(String name, double price, double availableKilograms) {
        super(name, price);
        if(availableKilograms > 0){
            this.availableKilograms = availableKilograms;
        }
        else{
            this.availableKilograms = 5;
        }
    }

    @Override
    public Type getType() {
        return Type.PerKilogram;
    }

    public void reduceKilograms(double kilograms){
        this.availableKilograms -= kilograms;
    }

    public void increaseKilograms(double kilograms){
        this.availableKilograms+=kilograms;
    }

    @Override
    public String toString() {
        return super.toString() + " availableKilograms = " + availableKilograms;
    }

    public double getAvailableKilograms() {
        return availableKilograms;
    }
}