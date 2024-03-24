import buyer.Buyer;
import buyer.Gender;
import exceptions.ProductNotFoundException;
import exceptions.QuantityIsNegativeException;
import exceptions.UserNotFoudException;
import order.Holidays;
import order.Order;
import order.OrderStatus;
import product.Product;

import java.util.*;

public class Market {
    final static int THE_PROBABILITY_OF_A_HOLIDAY = 10;// (>=1)the higher the value, the less likely it is
    final static int NEW_YEAR_DISCOUNT = 20;
    final static int MANS_WOMANS_DISCOUNT = 15;
    private List<Buyer> buyers;
    private List<Product> products;
    private List<Order> orders;

    public Market(List<Buyer> buyers, List<Product> products, List<Order> orders) {
        this.buyers = buyers;
        this.products = products;
        this.orders = orders;
    }

    public Market(List<Buyer> buyers, List<Product> products) {
        this(buyers,products,new ArrayList<>());
    }

    public Market() {
        this(new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
    }


    public int createOrder(Buyer buyer) throws UserNotFoudException {
        if (!buyers.contains(buyer)) throw new UserNotFoudException("user not found, " + buyer);
        int orderId = newOrderId();
        Order order = new Order(orderId,buyer,getHolidays());
        orders.add(order);
        return orderId;
    }

    private int newOrderId(){
        if (orders.isEmpty()) return 1;
        int newId = 1;
        for (Order order:orders) {
            if (order.getId()>=newId) newId=order.getId();
        }
        return newId+1;
    }

    private Holidays getHolidays(){
        int random = new Random().nextInt(THE_PROBABILITY_OF_A_HOLIDAY);
        if (random == 1) return Holidays.NEW_YEAR;
        if (random == 4) return Holidays.MANS_DAY;
        if (random == 5) return  Holidays.WOMANS_DAY;
        return Holidays.AN_ORDINARY_DAY;
    }
    public void addProductToOrder(int orderId, Product product, int quantity)
            throws ProductNotFoundException, QuantityIsNegativeException {
        if (!products.contains(product)) throw new ProductNotFoundException("product not found");
        if (quantity <= 0) throw new QuantityIsNegativeException("quantity of product is negative");
        getOrder(orderId).add(product,quantity);
    }

    public Map<Product,Integer> buy(int orderId){
        if (orders.isEmpty()) {
            System.out.println("List of orders is empty");
            return null;
        }
        Order order;
        double totalSum;
        if (!((order = getOrder(orderId)) ==null)) {
            order.setOrderStatus(OrderStatus.COMPLETED);
            System.out.print(order.getBuyer().getName() +
                    " ready to complete the order id= " +
                    + order.getId() + ". ");
            System.out.println(order);
            totalSum = getTotalPrice(order);
            System.out.println("The buy was completed successfully." +
                    " Total SUM = " + totalSum +
                    ". ");
            return new HashMap<>(order.getProducts());
            }
        System.out.println("This order was not found");
        return null;
    }

    public  double getPrice(Order order){
        double totalPrice = 0;
        for (Map.Entry<Product,Integer> products: order.getProducts().entrySet()
             ) {
            totalPrice = totalPrice +
                    products.getKey().getPrice()*products.getValue();
        }
        return totalPrice;
    }
    public List<Buyer> getBuyers() {
        return buyers;
    }

    public double getTotalPrice(Order order){
        double totalPrice = getPrice(order);
        if (order.getHoliday()==Holidays.AN_ORDINARY_DAY) return totalPrice;
        if (order.getHoliday()==Holidays.NEW_YEAR) {
            System.out.println("HAPPY NEW YEAR. YOUR "+ NEW_YEAR_DISCOUNT +
                    "% discount% = " + totalPrice*(NEW_YEAR_DISCOUNT*1.00/100));
            return totalPrice * (1 - NEW_YEAR_DISCOUNT*1.00/100);
        }
        if ((order.getHoliday()==Holidays.MANS_DAY)&&(order.getBuyer().getGender()== Gender.MAN)) {

            System.out.println("HAPPY MANS DAY. YOUR" + MANS_WOMANS_DISCOUNT + "% discount% = " +
                    totalPrice*(MANS_WOMANS_DISCOUNT*1.00/100));
            return totalPrice*(1 -MANS_WOMANS_DISCOUNT*1.00/100);
        }
        if ((order.getHoliday()==Holidays.WOMANS_DAY)&&(order.getBuyer().getGender()== Gender.WOMAN)) {

            System.out.println("HAPPY WOMANS DAY. YOUR " + MANS_WOMANS_DISCOUNT + "% discount% = " +
                    totalPrice*(MANS_WOMANS_DISCOUNT*1.00/100));
            return totalPrice*(1 -MANS_WOMANS_DISCOUNT*1.00/100);
        }
        return totalPrice;
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public Order getOrder(int orderId){
        for (Order order:orders) {
            if(order.getId()==orderId) return order;
        }
        System.out.println("This order are not exist");
        return null;
    }

    @Override
    public String toString() {
        return "Market{" +
                "buyers=" + buyers +
                ", products=" + products +
                ", orders=" + orders +
                '}';
    }
}
