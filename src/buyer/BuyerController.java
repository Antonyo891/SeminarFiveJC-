package buyer;

import exceptions.*;

import java.util.ArrayList;
import java.util.Arrays;

public class BuyerController {
private Buyer buyer;
private ArrayList<Buyer> buyers;

    public BuyerController(ArrayList<Buyer> buyers) {
        this.buyers = buyers;
    }

    public BuyerController() {
        this.buyers = new ArrayList<>();
    }

    public boolean addBuyer(Buyer buyer)
            throws AgeNegativeException, NameIsEmptyException {
        if (buyers.contains(buyer)) {
            System.out.println("Buyer already in the Market");
            return false;
        }
        if (addBuyer(buyer.getId(),buyer.getName(),
                buyer.getAge(),buyer.getPhone(),buyer.getGender())==null) return false;
        return true;
    }

    public Buyer addBuyer(int idBuyer, String name, int age, String phone,Gender gender)
            throws AgeNegativeException, NameIsEmptyException {
        if (age<0) throw new AgeNegativeException(age);
        if (name.isEmpty()) throw  new NameIsEmptyException(
                "The name should not be empty.\nThere are still " +
                        buyers.size() + " buyers in the Market."
        );
        for (Buyer buyer: buyers) {
            if (idBuyer == buyer.getId()) {
                System.out.println("Buyer with Id " + idBuyer +
                        " already EXIST");
                return buyer;
            }
        }
        buyer = new Buyer(idBuyer, name, age, phone, gender);
        buyers.add(buyer);
        return buyer;
    }

    public Buyer createBuyer(String name, int age, String phone, Gender gender)
            throws AgeNegativeException, NameIsEmptyException {
        int buyerId = newBuyerId();
        addBuyer(buyerId,name,age,phone, gender);
        return getBuyer(buyerId);
    }

    private int newBuyerId(){
        if (this.buyers.isEmpty()) return 1;
        int newBuyerId = 1;
        for (Buyer buyer: buyers) {
            if (newBuyerId<=buyer.getId()) newBuyerId=buyer.getId();
        }
        return newBuyerId+1;
    }

    public ArrayList<Buyer> getBuyers() {
        return buyers;
    }

    public void setGender(int buyerId, Gender gender){
        Buyer buyer = getBuyer(buyerId);
        if (buyer.getGender()==Gender.UNSPECIFIED)
            buyer.setGender(gender);
        else System.out.println("Gender for " +
                buyer.getName() + " already set by nature("+
                buyer.getGender() + ").");
    }

    public Buyer getBuyer(int buyerId){
        for (Buyer buyer: buyers) {
            if (buyer.getId()== buyerId)
                return buyer;
        }
        System.out.println("Buyer is not in the Market");
        return null;
    }

    public Buyer parse(String[] line) {
        Gender gender = Gender.MAN;
        try {
            if (line[3].equalsIgnoreCase("woman")) gender = Gender.WOMAN;
            int buyertId = Integer.parseInt(line[4]);
            String name = line[1];
            int age = Integer.parseInt(line[2]);
            String phoneNumber = line[5];
            return addBuyer(buyertId,name,age,phoneNumber,gender);
        } catch (NumberFormatException e ){
            System.out.println(Arrays.toString(e.getStackTrace()));
        } catch (TittleIsEmptyException e){
            System.out.println(Arrays.toString(e.getStackTrace()));
        } catch (NameIsEmptyException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        } catch (AgeNegativeException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        System.out.println("The line" + Arrays.toString(line) + "could not be processed");
        return null;
    }
}
