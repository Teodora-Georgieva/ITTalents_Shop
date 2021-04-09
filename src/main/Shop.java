package main;

import main.buyers.Buyer;
import main.products.Product;
import main.products.ProductPerKilogram;
import main.products.ProductPerPiece;
import main.utils.Validator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class Shop {
    private String name;
    private String address;
    private double money;
    private HashSet<ProductPerKilogram> productsPerKilogram;
    private HashMap<String, HashSet<ProductPerPiece>> productsPerPiece;

    public Shop(String name, String address, double money){
        if(Validator.isValidString(name)){
            this.name = name;
        }
        else{
            this.name = "Shop";
        }

        if(Validator.isValidString(address)){
            this.address = address;
        }
        else{
            this.address = "Sofia";
        }

        if(money >= 0){
            this.money = money;
        }
        else{
            this.money = 0;
        }

        this.productsPerKilogram = new HashSet<>();
        this.productsPerPiece = new HashMap<>();
    }

    public void removeProduct(Product p){
        //TODO
    }

    public void stockShop(HashSet<Product> products){
        for(Product p : products){
            if(p.getType() == Product.Type.PerKilogram) {
                this.productsPerKilogram.add((ProductPerKilogram) p);
            }
            else{
                if(!this.productsPerPiece.containsKey(p.getName())){
                    this.productsPerPiece.put(p.getName(), new HashSet<>());
                }
                this.productsPerPiece.get(p.getName()).add((ProductPerPiece) p);
            }
        }
    }

    public void returnBackToShopPerKilogram(ProductPerKilogram p, double kilograms){
        String productName = p.getName();
        boolean productExists = false;
        for(ProductPerKilogram productPerKilogram : this.productsPerKilogram){
            if(productPerKilogram.getName().equals(productName)){
                productExists = true;
                productPerKilogram.increaseKilograms(kilograms);
            }
        }

        if(!productExists){
            this.productsPerKilogram.add(p);
        }
    }

    public void returnBackToShopPerPiece(HashSet<ProductPerPiece> products){
        for(ProductPerPiece p : products){
            this.productsPerPiece.get(p.getName()).add(p);
        }
    }

    public void addMoneyToShop(double money){
        if(money > 0){
            this.money+=money;
        }
    }

    public void showStocks(){
        System.out.println("Stocks per kilogram:");
        for(ProductPerKilogram p : this.productsPerKilogram){
            System.out.println(p);
        }

        System.out.println();
        System.out.println("Stocks per piece:");
        for(Map.Entry<String, HashSet<ProductPerPiece>> e : this.productsPerPiece.entrySet()){
            System.out.println(e.getKey() + ": " + e.getValue().size() + " pieces");
        }
        System.out.println();
    }

    public void sellProductPerKilogram(String name, double kilograms, Buyer buyer) {
        boolean stockExists = false;

        for(ProductPerKilogram p : this.productsPerKilogram){
            if(p.getName().equals(name)){
                if(kilograms <= p.getAvailableKilograms()){
                    stockExists = true;
                    ProductPerKilogram productToSell = new ProductPerKilogram(p.getName(), p.getPrice(), kilograms);
                    p.reduceKilograms(kilograms);
                    buyer.addToCart(productToSell);
                }

                if(p.getAvailableKilograms() == 0){
                    this.productsPerKilogram.remove(p);
                }
            }
        }

        if(!stockExists){
            System.out.println("The product " + name + " is not available or the amount is insufficient!");
        }
    }

    public void sellProductPerPiece(String name, int pieces, Buyer buyer) {
        boolean productExists = false;

        for(String prodName : this.productsPerPiece.keySet()){
            if(prodName.equals(name)){
                HashSet<ProductPerPiece> productsPerPiece = this.productsPerPiece.get(prodName);
                if(productsPerPiece.size() >= pieces){
                    productExists = true;
                    int counter = 0;
                    for (Iterator<ProductPerPiece> it = productsPerPiece.iterator(); it.hasNext(); ){
                        ProductPerPiece productPerPiece = it.next();
                        buyer.addToCart(productPerPiece);
                        it.remove();
                        counter++;
                        if(counter == pieces){
                            break;
                        }
                    }
                }
            }
        }

        if(!productExists){
            System.out.println("The product " + name + " doesn't exists or the quantity is insufficient!");
        }
    }

    public double getMoney() {
        return money;
    }
}