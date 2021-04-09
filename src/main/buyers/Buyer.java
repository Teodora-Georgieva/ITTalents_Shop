package main.buyers;

import main.Shop;
import main.products.Product;
import main.products.ProductPerKilogram;
import main.products.ProductPerPiece;

import java.util.HashSet;
import java.util.Iterator;

public class Buyer {
    private Shop shop;
    private double money;
    private int maxCountOfProducts;
    private HashSet<Product> shoppingCart;

    public Buyer(Shop shop, double money, int maxCountOfProducts){
        this.shop = shop;
        if(money > 0){
            this.money = money;
        }
        else{
            this.money = 100;
        }

        if(maxCountOfProducts > 0){
            this.maxCountOfProducts = maxCountOfProducts;
        }
        else{
            this.maxCountOfProducts = 10;
        }

        this.shoppingCart = new HashSet<>();
    }

    public void buyProductPerKilogram(String name, double kilograms){
        shop.sellProductPerKilogram(name, kilograms, this);
    }

    public void addToCart(Product p){
        if(this.shoppingCart.size() < this.maxCountOfProducts){
            this.shoppingCart.add(p);
        }
        else{
            System.out.println("You have reached the max count of products you can buy!");
        }
    }

    public void buyProductPerPiece(String name, int pieces){
        shop.sellProductPerPiece(name, pieces, this);
    }

    public void removeProductPerKilogram(String name, double kilograms){
        boolean existsInCart = false;
        ProductPerKilogram productPerKilogram = null;
        for(Product p : this.shoppingCart){
            if(p.getType().equals(Product.Type.PerKilogram) && p.getName().equals(name)){
                existsInCart = true;
                productPerKilogram = (ProductPerKilogram) p;
            }
        }

        if(!existsInCart){
            System.out.println("You want to remove product that you have not bought!");
            return;
        }

        if(kilograms > productPerKilogram.getAvailableKilograms()){
            System.out.println("You want to return more kilograms from " + productPerKilogram.getName() +
                                " than you have bought!");
            return;
        }
        ProductPerKilogram backToShop = new ProductPerKilogram(productPerKilogram.getName(),
                                                               productPerKilogram.getPrice(),
                                                               kilograms);

        ProductPerKilogram backToBuyer = new ProductPerKilogram(productPerKilogram.getName(),
                                                                productPerKilogram.getPrice(),
                                                 productPerKilogram.getAvailableKilograms() - kilograms);
        shop.returnBackToShopPerKilogram(backToShop, kilograms);
        this.shoppingCart.remove(productPerKilogram);
        this.addToCart(backToBuyer);
    }

    public void removeProductPerPiece(String name, int pieces){
        int countOfPiecesInCart = 0;
        for(Product p : this.shoppingCart){
            if(p.getName().equals(name)){
                countOfPiecesInCart++;
            }
        }

        if(pieces > countOfPiecesInCart){
            System.out.println("You want to remove more pieces of " + name + " than you have bought!");
            return;
        }

        HashSet<ProductPerPiece> toReturn = new HashSet<>();
        int counter = 0;
        for(Iterator<Product> it = shoppingCart.iterator(); it.hasNext(); ){
            Product p = it.next();
            if(p.getType().equals(Product.Type.PerPiece) && p.getName().equals(name)){
                toReturn.add((ProductPerPiece) p);
                it.remove();
                counter++;
            }
            if(counter == pieces){
                break;
            }
        }

        this.shop.returnBackToShopPerPiece(toReturn);
    }

    public void payForProducts(){
        double moneyToPay = 0;
        for(Product p : this.shoppingCart){
            moneyToPay+=p.getPrice();
        }

        if(moneyToPay > this.money){
            System.out.println("You don't have enough money to pay, so you can remove something from the cart, if you want.");
            return;
        }

        this.money -= moneyToPay;
        this.shop.addMoneyToShop(moneyToPay);
        this.shoppingCart.clear();
    }

    public void showProductsInCart(){
        System.out.println("Showing products in shopping cart...");
        if(this.shoppingCart.size() == 0){
            System.out.println("The shopping cart is empty!");
            return;
        }

        for(Product p : this.shoppingCart){
            System.out.println(p);
            System.out.println();
        }
    }
}
