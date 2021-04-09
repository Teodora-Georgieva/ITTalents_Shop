package main;

import main.buyers.Buyer;
import main.products.Product;
import main.products.ProductPerKilogram;
import main.products.ProductPerPiece;
import main.utils.Generator;

import java.util.ArrayList;
import java.util.HashSet;

public class Demo {
    public static void main(String[] args) {
        Shop shop = new Shop("Shop", "Sofia", 0);
        HashSet<Product> products = new HashSet();
        products.add(new ProductPerKilogram("Meat", 10.50, 15.5));
        products.add(new ProductPerKilogram("Cheese", 13, 20));
        products.add(new ProductPerKilogram("Fish", 15.50, 10));
        for (int i = 0; i < 7; i++) {
            products.add(new ProductPerPiece("Beer", 5));
        }

        for (int i = 0; i < 5; i++) {
            products.add(new ProductPerPiece("Book", 25));
        }

        for (int i = 0; i < 3; i++) {
            products.add(new ProductPerPiece("Chair", 50.99));
        }

        System.out.println("Stocking shop...");
        shop.stockShop(products);
        shop.showStocks();

        ArrayList<Buyer> buyers = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            buyers.add(new Buyer(shop, Generator.generateRandomNumber(50, 500), Generator.generateRandomNumber(1, 15)));
        }

        Buyer buyer1 = buyers.get(0);
        buyer1.buyProductPerKilogram("Cheese", 3.5);
        buyer1.buyProductPerPiece("Chair", 2);
        buyer1.showProductsInCart();
        shop.showStocks();

        Buyer buyer2 = buyers.get(1);
        buyer2.buyProductPerKilogram("Meat", 3);
        buyer2.buyProductPerPiece("book", 2);
        buyer2.buyProductPerPiece("Book", 2);
        buyer2.buyProductPerPiece("Beer", 5);
        buyer2.showProductsInCart();
        shop.showStocks();

        Buyer buyer3 = buyers.get(2);
        buyer3.buyProductPerPiece("Beer", 2);
        buyer3.buyProductPerKilogram("Meet", 4);
        buyer3.buyProductPerKilogram("Fish", 40);
        buyer3.showProductsInCart();
        shop.showStocks();

        /*
        for(Buyer b : buyers){
            b.payForProducts();
        }

         */

        buyer1.removeProductPerPiece("Chair", 1);
        buyer1.showProductsInCart();
        shop.showStocks();

        buyer2.removeProductPerPiece("Book", 2);
        buyer2.removeProductPerKilogram("Meat", 0.3);
        buyer2.showProductsInCart();
        shop.showStocks();

        for(Buyer b : buyers){
            b.payForProducts();
        }

        System.out.println("Shop money balance:");
        System.out.println(shop.getMoney());
    }
}