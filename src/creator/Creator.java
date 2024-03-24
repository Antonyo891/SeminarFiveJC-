package creator;

import buyer.Buyer;
import buyer.BuyerController;
import buyer.Gender;
import exceptions.*;
import product.Product;
import product.ProductController;

import java.util.ArrayList;
import java.util.Arrays;

public class Creator {
    private ProductController productController;
    private BuyerController buyerController;

    public Creator() {
        this.productController = new ProductController();
        this.buyerController = new BuyerController();
    }

    public void createProducts() {
        try {
            productController.createProduct("Milk", 89);
            productController.createProduct("Bread", 26);
            productController.createProduct("Cheese", 125);
            System.out.println("Products was created successful");
        }
        catch (ProductPriceException e){
            System.out.println(e.getPrice() + " - unacceptable price");
            System.out.println(Arrays.toString(e.getStackTrace()));
        } catch (TittleIsEmptyException e){
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        } catch (IdException e){
            System.out.println(e.getMessage());
        }

    }

    public void createBuyer() {
        try {
            buyerController.createBuyer("Tom", 45, "11111", Gender.MAN);
            buyerController.createBuyer("Liss", 26, "22222", Gender.WOMAN);
            buyerController.createBuyer("Jim", 53, "33333", Gender.MAN);
            buyerController.createBuyer("John", 40, "44444", Gender.MAN);
            System.out.println("Buyers was created successful");
        }
        catch (AgeNegativeException e){
            System.out.println(e.getAge() + " - unacceptable age.");
            System.out.println(Arrays.toString(e.getStackTrace()));
        }catch (NameIsEmptyException e){
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));}
    }

    public ArrayList<Product> getProducts() {
        return this.productController.getProducts();
    }

    public ArrayList<Buyer> getBuyers() {
        return this.buyerController.getBuyers();
    }
}
