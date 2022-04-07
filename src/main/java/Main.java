import data.Barber;
import data.BarberShop;
import data.Customer;

public class Main {
    private static final int sleepTime = 5;
    private static final String[] customers = {"Jonas", "Kåre", "Gunnar", "Larse", "Thomas", "Frederik", "Stian", "Daniel", "Lise", "Kari", "Marie", "Madeleine", "Anna", "Marte", "Matilde", "Erlend", "Isak", "Eirik", "Andreas","Andrea","Frank"};


    public static void main(String[] args) {

        //Fields
        Object mutex = new Object(); //lock used to synchronize threads.
        BarberShop barberShop = new BarberShop(5);
        Barber barber = new Barber(barberShop);

        barber.setName("Barber");
        barber.start();

        // For-loop that sends new customers into the shop, if there are no waiting seats they leave.

        //TODO: Common key for loop and work thread? Synchronization issues
        Customer newCustomer;

        synchronized (mutex) {
            for (int i = 1; i < 100; i++) {
                int random = (int) Math.round((Math.random() * 2) * 10);
                //Customer sits down in chair if one is available, and starts its thread.
                try {
                    newCustomer = new Customer(barberShop);
                    barberShop.seatsAvailable.add(newCustomer);
                    newCustomer.giveName("Customer" + i);
                    newCustomer.giveName(customers[random]);

                    if (barberShop.seatsAvailable.isEmpty()) {
                        System.out.println(newCustomer.returnName() + " wakes the barber");
                    } else {
                        System.out.println(newCustomer.returnName() + " has been seated in the waiting room");
                    }
                    newCustomer.start();
                }
                //Array is full.
                catch (IllegalStateException e) {
                    System.out.println("No available seat, customer has left");
                }

                try {
                    Thread.sleep(sleepTime * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
