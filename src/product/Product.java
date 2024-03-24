package product;

public class Product {
    private String title;
    private int price;

    private int id;

    Product(int id, String title, int price) {
        this.title = title;
        this.price = price;
        this.id = id;
    }

    Product(Product product) {
        this(product.getId(), product.getTitle(), product.getPrice());
    }

    public String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    void setPrice(int price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    void setId(int newId){
        this.id = newId;
    }

    @Override
    public String toString() {
        return "Product {" +
                title +
                ", " + price +
                ", " + id +
                '}';
    }
}
