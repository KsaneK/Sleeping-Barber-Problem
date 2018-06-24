package projekt;

import java.util.*;
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

    Map<Integer, List<Integer>> miejsca = new HashMap<>();

    public Salon(int pojemnosc_poczekalni, int liczba_foteli, int ilosc_uslug) {
        this.pojemnosc_poczekalni = pojemnosc_poczekalni;
        this.liczba_foteli = liczba_foteli;
        this.liczba_czekajacych = 0;
        this.ilosc_uslug = ilosc_uslug;
        fotel = new Semaphore(liczba_foteli);

        klient = new Semaphore[ilosc_uslug];
        fryzjer = new Semaphore[ilosc_uslug];
        for (int i = 0; i < ilosc_uslug; i++) {
            klient[i] = new Semaphore(0);
            fryzjer[i] = new Semaphore(0);
            miejsca.put(i, new ArrayList<>());
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
                    Thread.sleep(1300);
                    break;
                case 1:
                    Thread.sleep(1000);
                    break;
                case 2:
                    Thread.sleep(2000);
                    break;
                case 3:
                    Thread.sleep(800);
                    break;
                case 4:
                    Thread.sleep(1500);
                    break;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(String.format("%s < %d", kto, fryzura));
    }
}
