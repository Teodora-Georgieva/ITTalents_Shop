package main.products;

public class ProductPerPiece extends Product{

    public ProductPerPiece(String name, double price) {
        super(name, price);
    }

    @Override
    public Type getType() {
        return Type.PerPiece;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}