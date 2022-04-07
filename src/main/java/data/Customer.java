package data;

public class Customer extends Thread {
    BarberShop barberShop;
    String name;

    public Customer(BarberShop barberShop) {
        this.barberShop = barberShop;
    }

    //Enters the array if available seats, leaves if not.
    public void enterShop() {

    }

    //Run by barber to stop customer thread.
    synchronized public void getHairCut() throws InterruptedException {
        barberShop.cutCustomerHair(this);
    }

    public void giveName(String name) {
        this.name = name;
    }

    public String returnName() {
        return name;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            barberShop.enterShop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.run();
    }
}
