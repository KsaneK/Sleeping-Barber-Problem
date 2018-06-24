package nogui;

import java.util.Random;

public class Fryzjer extends Thread {
    private final Salon salon;
    Random random;
    int specjalizacja;

    public Fryzjer(String name, Salon salon, int specjalizacja) {
        super(name);
        this.salon = salon;
        this.specjalizacja = specjalizacja;
        random = new Random();
    }

    public void run() {
        while(true) {
            try {
                salon.klient[specjalizacja].acquire();
                salon.fotel.acquire();
                salon.mutex.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            salon.liczba_czekajacych--;
            salon.fryzjer[specjalizacja].release();
            salon.mutex.release();
            salon.strzyzenie(currentThread().getName(), specjalizacja);
            salon.fotel.release();
            chwila_przerwy();
        }
    }

    private void chwila_przerwy() {
        try {
            Thread.sleep(random.nextInt(5) + 1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
