package order;

import buyer.Buyer;
import buyer.BuyerController;
import exceptions.AgeNegativeException;
import exceptions.IdException;
import exceptions.NameIsEmptyException;
import exceptions.TittleIsEmptyException;
import product.Product;
import product.ProductController;

import java.util.*;

public class OrderController {
    private final int NUMBER_OF_FIELD_IN_PRODUCT = 4;
    private final int FIRST_INDEX_QUANTITY_IN_LINE = 14;
    private List<Order> orders;
    private Order order;

    public OrderController(List<Order> orders) {
        this.orders = orders;
    }

    public OrderController() {
        this(new ArrayList<>());
    }

    private Order createOrder(int id, Buyer buyer, Holidays holiday, OrderStatus orderStatus,
                             Map<Product, Integer> products) throws
            IdException{
        if (id<1) throw  new IdException(id,"Order id must be > 0");
        order = new Order(id, buyer, holiday, orderStatus, products);
        return order;
    }
    public List<Order> getOrders() {
        return orders;
    }

    public Order parse(String[] line, BuyerController buyerController,
                       ProductController productController) {
        Map<Product,Integer> map = new HashMap<>();
        int quantity;
        List<Product> products = new ArrayList<>();
        Buyer buyer;
        int orderId;
        Holidays holidays = Holidays.AN_ORDINARY_DAY;
        OrderStatus orderStatus = OrderStatus.AT_WORK;
        try {
            orderId = Integer.parseInt(line[1]);
            if (line[8].equalsIgnoreCase("new_year"))
                holidays = Holidays.NEW_YEAR;
            else if (line[8].equalsIgnoreCase("WOMANS_DAY"))
                holidays = Holidays.WOMANS_DAY;
            else if (line[8].equalsIgnoreCase("MANS_DAY"))
                holidays = Holidays.MANS_DAY;
            if (line[9].equalsIgnoreCase("COMPLETED"))
                orderStatus = OrderStatus.COMPLETED;
            buyer = buyerController.parse(Arrays.copyOfRange(line,2,8));
            if (line.length>=FIRST_INDEX_QUANTITY_IN_LINE)
                for (int i = FIRST_INDEX_QUANTITY_IN_LINE; i < line.length ;i+=5) {
                    quantity = Integer.parseInt(line[i]);
                    String[] lineProduct = new String[NUMBER_OF_FIELD_IN_PRODUCT+1];
                    for (int j=0; j<NUMBER_OF_FIELD_IN_PRODUCT; j++)
                        lineProduct[j] = line[i-(NUMBER_OF_FIELD_IN_PRODUCT-j)];
                    map.put(productController.parse(lineProduct),quantity);
                }
            return this.createOrder(orderId, buyer, holidays, orderStatus, map);
        } catch (IdException e){
            System.out.println(Arrays.toString(e.getStackTrace()));
        }

        System.out.println("The line" + Arrays.toString(line) + "could not be processed");
        return null;
    }
}
