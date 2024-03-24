package product;

import exceptions.IdException;
import exceptions.ProductPriceException;
import exceptions.TittleIsEmptyException;

import java.util.ArrayList;
import java.util.Arrays;

public class ProductController {
    private ArrayList<Product> products;
    private Product product;


    public ProductController() {
        this.products = new ArrayList<>();
    }

    public ProductController(ArrayList<Product> products) {
        this.products = products;
    }

    public boolean addProduct(Product product){
        if (products.contains(product)) return false;
        if (product.getId()<1) {
            product.setId(newProductId());
            products.add(product);
            return true;
        }
        return false;
    }

    public Product createProduct(int productId, String title, int price)
            throws ProductPriceException, TittleIsEmptyException, IdException {
        if (productId<1) throw new IdException(productId,"Product Id must be more then 0");
        if (price<=0) throw new ProductPriceException(price);
        if (title.isEmpty()) throw  new TittleIsEmptyException(
                "The tittle should not be empty."
        );
        for (Product product: products) {
            if (productId == product.getId()) {
                if (title.equalsIgnoreCase(product.getTitle())) {
                    if (product.getPrice()==price) {
                        System.out.println("Product already EXIST");
                        return product;
                    } else {
                        System.out.println("The price of the product" +
                                product.getTitle() + "has been changed from " +
                                product.getPrice() + " to " + price);
                        product.setPrice(price);
                        return product;
                    }
                } else {
                    System.out.println("Product with id = " + productId +
                            " is already exist. Id was changed to " +
                            + (productId = newProductId()));
                }
            }
        }
        product = new Product(productId,title,price);
        products.add(product);
        System.out.println("Product " + product.getTitle() + " add to controller.");
        System.out.println("There are" + products.size() + " products in the controller.");
        return product;
    }

    public Product createProduct(String title, int price)
            throws ProductPriceException, TittleIsEmptyException, IdException {
        return createProduct(newProductId(),title,price);
    }

    private int newProductId(){
        if (this.products.isEmpty()) return 1;
        int newProductId = 1;
        for (Product product: products) {
            if (newProductId<=product.getId()) newProductId=product.getId();
        }
        return newProductId+1;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }


    public Product parse(String[] line) {
        try {
            int productId = Integer.parseInt(line[3]);
            String title = line[1];
            int price = Integer.parseInt(line[2]);
            return createProduct(productId,title,price);
        } catch (NumberFormatException e ){
            System.out.println(Arrays.toString(e.getStackTrace()));
        } catch (ProductPriceException e){
            System.out.println(Arrays.toString(e.getStackTrace()));
        } catch (TittleIsEmptyException e){
            System.out.println(Arrays.toString(e.getStackTrace()));
        } catch (IdException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        System.out.println("The line" + Arrays.toString(line) + "could not be processed");
        return null;
    }
}
