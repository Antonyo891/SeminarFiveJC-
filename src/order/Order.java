package order;

import buyer.Buyer;
import product.Product;

import java.util.HashMap;
import java.util.Map;

public class Order implements Comparable<Order> {

    private int id;
    private Buyer buyer;
    private Holidays holiday;
    private OrderStatus orderStatus;
    private Map<Product, Integer> products;

    public Order(int id, Buyer buyer, Holidays holiday, OrderStatus orderStatus,
                 Map<Product, Integer> products) {
        this.id = id;
        this.buyer = buyer;
        this.holiday = holiday;
        this.orderStatus = orderStatus;
        this.products = products;
    }

    public Order(int id, Buyer buyer, Holidays holiday) {
        this(id,buyer,holiday, OrderStatus.AT_WORK, new HashMap<>());
    }
    Order(int id,Buyer buyer) {
        this(id, buyer, Holidays.AN_ORDINARY_DAY);
    }

    Order(Order order) {
        this(order.getId(), order.getBuyer(), order.getHoliday(),
        order.getOrderStatus(), order.getProducts());
    }

    public int getId() {
        return id;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void add (Product product, int quantity) {
        products.put(product, quantity);
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public Holidays getHoliday() {
        return holiday;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    @Override
    public int compareTo(Order o) {
        return this.id - o.getId();
    }

    @Override
    public String toString() {
        return "Order {" +
                id +
                ", " + buyer +
                ", " + holiday +
                ", " + orderStatus +
                ", " + products +
                '}';
    }
}
