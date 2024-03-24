import buyer.Buyer;
import buyer.BuyerController;
import order.Order;
import order.OrderController;
import product.Product;
import product.ProductController;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileWorker {
    private final File PRODUCTS_FILE =new File("D:\\Программирование\\2023\\JavaCore\\" +
            "SeminarFourJC\\src\\files\\Products.txt");
    private final File BUYERS_FILE = new File("D:\\Программирование\\2023\\JavaCore\\" +
            "SeminarFourJC\\src\\files\\Buyers.txt");
    private final File ORDERS_FILE = new File("D:\\Программирование\\2023\\JavaCore\\" +
            "SeminarFourJC\\src\\files\\Orders.txt");
    private Boolean ADDITIONAL_WRITE_FILE = false;
    private ProductController productController;
    private BuyerController buyerController;
    private OrderController orderController;
    private List<Product> products;
    private List<Order> orders;
    private List<Buyer> buyers;

    public FileWorker(ProductController productController, BuyerController buyerController, OrderController orderController) {
        this.productController = productController;
        this.buyerController = buyerController;
        this.orderController = orderController;
        orders = new ArrayList<>();
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public List<Buyer> getBuyers() {
        return buyers;
    }

    public Boolean writeProduct(List<Product> products){
        try(FileWriter fileWriter = new FileWriter(PRODUCTS_FILE, ADDITIONAL_WRITE_FILE)) {
            for (Product product: products) {
                fileWriter.write(product.toString()+"\n");
            }
            fileWriter.flush();
            return true;
        } catch (IOException e){
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        return false;
    }

    public Boolean writeBuyers(List<Buyer> buyers){
        try (FileWriter fileWriter = new FileWriter(BUYERS_FILE,ADDITIONAL_WRITE_FILE)) {
            for (Buyer buyer: buyers){
                fileWriter.write(buyer.toString() + "\n");
            }
            fileWriter.flush();
            return true;
        } catch (IOException e){
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        return false;
    }

    public Boolean writeOrders(List<Order> orders){
        try (FileWriter fileWriter = new FileWriter(ORDERS_FILE,ADDITIONAL_WRITE_FILE)) {
            for (Order order: orders) {
                fileWriter.write(order.toString() + "\n");
                fileWriter.flush();
            }
        } catch (IOException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        return false;
    }

    public List<Product> readProduct(){
        String line;
        products = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(PRODUCTS_FILE))) {
            while ((line = bufferedReader.readLine())!=null){
                line = line.replace("{","").replace("}","").
                        replace(",","");
                products.add(productController.parse(line.split(" ")));
            }
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        return products;
    }

    public List<Buyer> readBuyers(){
        String line;
        buyers = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(BUYERS_FILE))) {
            while ((line = bufferedReader.readLine())!=null){
                line = line.replace("{","").replace("}","").
                        replace(",","");
                buyers.add(buyerController.parse(line.split(" ")));
            }
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        return buyers;
    }


    public List<Order> readOrder(){
        String line;
        orders = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(ORDERS_FILE))) {
            while ((line = bufferedReader.readLine())!=null){
                line = line.replace("{","").replace("}","").
                        replace(",","").replace("="," ");
                System.out.println(Arrays.toString(line.split(" ")));
                orders.add(orderController.parse(line.split(" "),buyerController,productController));
            }
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        return orders;
    }


}
