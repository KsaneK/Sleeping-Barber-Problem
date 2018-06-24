package projekt;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class Fryzjer extends Ludzik {
    Random random;
    int specjalizacja;
    static final int width = 70;
    static final int height = 70;
    ReentrantLock lock = new ReentrantLock();

    Fryzjer[] fryzjerzy;

    static Image[] images = {
            new Image(Klient.class.getClassLoader().getResource("fryzjer1.png").toString()),
            new Image(Klient.class.getClassLoader().getResource("fryzjer2.png").toString()),
            new Image(Klient.class.getClassLoader().getResource("fryzjer3.png").toString()),
            new Image(Klient.class.getClassLoader().getResource("fryzjer4.png").toString()),
            new Image(Klient.class.getClassLoader().getResource("fryzjer5.png").toString())
    };
    boolean stop;

    public Fryzjer(String name, Salon salon, int specjalizacja, int i, int dlugosc_animacji) {
        super(name, salon, i, dlugosc_animacji);
        this.specjalizacja = specjalizacja;
        random = new Random();
        img = new ImageView();
        img.setImage(images[specjalizacja]);
        img.setFitWidth(width);
        img.setFitHeight(height);
        img.setTranslateX(200 + num * width);
        img.setTranslateY(0);
    }

    public void setFryzjerzy(Fryzjer[] fryzjerzy) {
        this.fryzjerzy = fryzjerzy;
    }

    public void run() {
        while(!stop) {
            try {
                salon.klient[specjalizacja].acquire(); // czeka, aż klient go obudzi
                salon.fotel.acquire(); // czeka na wolny fotel
                salon.mutex.acquire(); // blokuje obsługę innym fryzjerom
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            salon.liczba_czekajacych--;
            znajdz_fotel();
            salon.miejsca.get(specjalizacja).add(destX);
            salon.fryzjer[specjalizacja].release();
            salon.mutex.release(); // odblokowuje obsługę innym fryzjerom
            idz_do_klienta();
            salon.strzyzenie(currentThread().getName(), specjalizacja);
            posprzataj();
            salon.fotel.release();
            powrot();
        }
    }

    private void posprzataj() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        destX = 200 + num * width;
        destY = 0;
    }

    synchronized public void znajdz_fotel() {
        lock.lock();
        int posX = 300;
        destY = 150;
        boolean obsluzone = true;
        while(obsluzone) {
            obsluzone = false;
            for (Fryzjer f : fryzjerzy) {
                if (f.destX == posX && f.destY == destY) {
                    posX += Fotel.width;
                    obsluzone = true;
                    break;
                }
            }
        }
        destX = posX;
        lock.unlock();
    }

    public void idz_do_klienta() {
        transition(destX, destY, moveTime);
    }

    private void powrot() {
        transition(destX, destY, moveTime);
    }
    
    public void stopThread() {
        this.stop = true;
    }
}
