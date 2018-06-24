package nogui;

import java.util.Random;

public class Klient extends Thread {

    private Salon salon;
    Random random;
    int fryzura;


    public Klient(String name, Salon salon, int i) {
        super(name);
        this.salon = salon;
        random = new Random();
    }

    public void run() {
        while(true) {
            wybierz_fryzure();
            try {
                salon.mutex.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (salon.liczba_czekajacych < salon.pojemnosc_poczekalni) {
                salon.liczba_czekajacych++;
                salon.klient[fryzura].release();
                salon.mutex.release();
                try {
                    salon.fryzjer[fryzura].acquire();
                    salon.strzyzenie(currentThread().getName(), fryzura);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                salon.mutex.release();
            }
        }
    }

    private void wybierz_fryzure() {
        fryzura = random.nextInt(salon.ilosc_uslug);
        try {
            Thread.sleep(random.nextInt(10) + 1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
