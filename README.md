### Урок 5. Тонкости работы (код на основании проекта после урока 4)
Написать функцию, создающую резервную копию всех файлов в директории(без поддиректорий) во вновь созданную папку ./backup

Решить задачи из презентации к семинару

### либо

### Дописать сохранение объектов магазина в файлы, соответсвующие типам объектов.
Создал класс [FileWorker](https://github.com/Antonyo891/SeminarFiveJC-/blob/SeminarFiveHW/src/FileWorker.java) для работы с файлами.
Добавил методы parse() в контроллеры [покупателей](https://github.com/Antonyo891/SeminarFiveJC-/blob/SeminarFiveHW/src/buyer/BuyerController.java), [продуктов](https://github.com/Antonyo891/SeminarFiveJC-/blob/SeminarFiveHW/src/product/ProductController.java) и [заказов](https://github.com/Antonyo891/SeminarFiveJC-/blob/SeminarFiveHW/src/order/OrderController.java).
С заказами все оказалось непросто так как там упоминаются и продукты и покупатели...или я перемудрил...
```
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
```
При записи можно
### а) перезаписывать файл полностью
б) * дописывать новые объекты в тот же файл

Любые доработки и улучшения на Ваше усмотрение.
