package data;

import java.util.concurrent.ArrayBlockingQueue;

public class BarberShop {

    public final ArrayBlockingQueue<Customer> seatsAvailable;

    public BarberShop(int seatsAvailable) {
        this.seatsAvailable = new ArrayBlockingQueue<>(5);
    }


    //If available seats in the array, add customer to list.
    public void seatCustomer(Customer customer) {
        if (seatsAvailable.remainingCapacity() > 0) {
            seatsAvailable.add(customer);
        } else {
            System.out.println("No available seats, potential customer has left");
        }

        synchronized (this) {
            notifyAll();
        }
    }

    /*
    Starts working through array of customers, using cutHair method for each.
    If no customers in chairs, wait.
    */
    synchronized public void work() throws InterruptedException {
        System.out.println("Barber is sleeping");

        while (true) {
            while (seatsAvailable.isEmpty()) {
                wait();
            }
            sitInBarberChair();
            }
    }

    public void sitInBarberChair() throws InterruptedException{

        while (!seatsAvailable.isEmpty()) {
            seatsAvailable.poll().getHairCut();
        }
    }

    /**
     * When a customer is next in line this method is run for the barber to do the actual cutting of his hair
     *
     * @param customer being called by the thread
     */
    synchronized public void cutCustomerHair(Customer customer) {
        try {
            System.out.println("The barber begins cutting " + customer.returnName() + "s hair");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (seatsAvailable.size() == 0) {
            System.out.println("The barber is done cutting " + customer.returnName() + " hair and goes back to sleep");
        } else {
            System.out.println("The barber is done with " + customer.returnName());
        }
    }

    //Called when a customer enters the shop. Wakes up the barber if he is sleeping.
    public void enterShop() {
        Customer customer = (Customer) Thread.currentThread();

        if (seatsAvailable.remainingCapacity() > 0) {
            seatsAvailable.add(customer);
        }
        synchronized (this) {
            notifyAll();
        }
    }
}

