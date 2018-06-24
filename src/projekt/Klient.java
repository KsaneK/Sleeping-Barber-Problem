package projekt;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class Klient extends Ludzik {
    Random random;
    int fryzura;
    boolean stop = false;

    static Image starting_img = new Image(Klient.class.getClassLoader().getResource("ludzik_nieostrzyzony.png").toString());
    static Image[] img_before = {
            new Image(Klient.class.getClassLoader().getResource("ludzik_nieostrzyzony1.png").toString()),
            new Image(Klient.class.getClassLoader().getResource("ludzik_nieostrzyzony2.png").toString()),
            new Image(Klient.class.getClassLoader().getResource("ludzik_nieostrzyzony3.png").toString()),
            new Image(Klient.class.getClassLoader().getResource("ludzik_nieostrzyzony4.png").toString()),
            new Image(Klient.class.getClassLoader().getResource("ludzik_nieostrzyzony5.png").toString())
    };
    static Image[] img_after = {
            new Image(Klient.class.getClassLoader().getResource("ludzik1.png").toString()),
            new Image(Klient.class.getClassLoader().getResource("ludzik2.png").toString()),
            new Image(Klient.class.getClassLoader().getResource("ludzik3.png").toString()),
            new Image(Klient.class.getClassLoader().getResource("ludzik4.png").toString()),
            new Image(Klient.class.getClassLoader().getResource("ludzik5.png").toString())
    };

    Klient[] klienci;

    static ReentrantLock lock = new ReentrantLock();

    public void setKlienci(Klient[] klienci) {
        this.klienci = klienci;
    }

    public Klient(String name, Salon salon, int i, int dlugosc_animacji) {
        super(name, salon, i, dlugosc_animacji);
        random = new Random();
        img = new ImageView();
        img.setImage(starting_img);
        img.setFitWidth(width);
        img.setFitHeight(height);
        img.setTranslateX(200 + (i / 2) * width);
        img.setTranslateY(600 - height - (i % 2) * height);
    }

    public void run() {
        while (!stop) {
            wybierz_fryzure();
            try {
                salon.mutex.acquire(); // blokujemy dostęp do poczekalni
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (salon.liczba_czekajacych < salon.pojemnosc_poczekalni) { // sprawdzamy czy są wolne miejsca
                salon.liczba_czekajacych++; // wchodzimy
                salon.mutex.release(); // odblokowujemy dostęp do poczekalni
                idz_do_poczekalni(); // animacja
                try {
                    salon.mutex.acquire(); // blokujemy dostęp do powiadomienia fryzjera
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                salon.klient[fryzura].release(); // powiadamiamy fryzjera
                salon.mutex.release(); // odblokowujemy dostęp do powiadomienia fryzjera

                try {
                    salon.fryzjer[fryzura].acquire();
                    idz_do_fotela();
                    salon.strzyzenie(currentThread().getName(), fryzura);
                    wyjdz_z_salonu();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                salon.mutex.release();
            }
        }
    }

    private void wyjdz_z_salonu() {
        destX = 200 + num / 2 * width;
        destY = 600 - height - (num % 2) * height;
        img.setImage(img_after[fryzura]);
        transition(destX, destY, moveTime);
    }

    private void idz_do_fotela() {
        lock.lock();
        destX = salon.miejsca.get(fryzura).remove(0);
        destY = 200;
        lock.unlock();
        transition(destX, destY, moveTime);
    }

    private void idz_do_poczekalni() {
        lock.lock();
        int posY = 0;
        boolean zajete = true;
        while(zajete) {
            zajete = false;
            for(int i = 0; i < klienci.length; i++) {
                if (klienci[i].destY == posY && klienci[i].destX == 0) {
                    posY += Krzeslo.height;
                    zajete = true;
                    break;
                }
            }
        }
        img.setImage(img_before[fryzura]);
        destX = 0;
        destY = posY;
        lock.unlock();
        transition(destX, destY, moveTime);
    }

    private void wybierz_fryzure() {
        fryzura = random.nextInt(salon.ilosc_uslug);
        switch (fryzura) {
            case 0:
                img.setStyle("-fx-border-style: solid; -fx-border-color: green; -fx-border-width:1");
                break;
            case 1:
                img.setStyle("-fx-border-style: solid; -fx-border-color: red; -fx-border-width:1");
                break;
            case 2:
                img.setStyle("-fx-border-style: solid; -fx-border-color: blue; -fx-border-width:1");
                break;
        }
        try {
            Thread.sleep(random.nextInt(100) + 200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stopThread() {
        this.stop = true;
    }
}
