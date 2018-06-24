package nogui;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Salon {
    final int ilosc_uslug;
    final int pojemnosc_poczekalni;
    final int liczba_foteli;
    int liczba_czekajacych;
    Semaphore mutex = new Semaphore(1);
    Semaphore[] klient;
    Semaphore[] fryzjer;
    Semaphore fotel;

    Random random;


    public Salon(int pojemnosc_poczekalni, int liczba_foteli, int ilosc_uslug) {
        this.pojemnosc_poczekalni = pojemnosc_poczekalni;
        this.liczba_foteli = liczba_foteli;
        this.liczba_czekajacych = 0;
        this.ilosc_uslug = ilosc_uslug;
        fotel = new Semaphore(liczba_foteli);
        random = new Random();

        klient = new Semaphore[ilosc_uslug];
        fryzjer = new Semaphore[ilosc_uslug];
        for (int i = 0; i < ilosc_uslug; i++) {
            klient[i] = new Semaphore(0);
            fryzjer[i] = new Semaphore(0);
        }
    }

    @Override
    public String toString() {
        return String.format("Czeka %d", liczba_czekajacych);
    }

    public void strzyzenie(String kto, int fryzura) {
        System.out.println(String.format("%s > %d", kto, fryzura));
        try {
            switch (fryzura) {
                case 0:
                    Thread.sleep(500);
                    break;
                case 1:
                    Thread.sleep(3000);
                    break;
                case 2:
                    Thread.sleep(1000);
                    break;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(String.format("%s < %d", kto, fryzura));
    }
}
