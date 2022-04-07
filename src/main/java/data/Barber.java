package data;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Barber extends Thread {

    BarberShop barberShop;

    //Constructor that assigns barbershop to barber.
    public Barber(BarberShop barberShop) {
        this.barberShop = barberShop;
    }

    //Cut one customers hair, for x amount of time.
    public void cutHair(Customer customer) {
    }


    @Override
    public void run() {
        try {
            barberShop.work();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
