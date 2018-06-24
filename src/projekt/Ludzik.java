package projekt;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public abstract class Ludzik extends Thread {
    static final int width = 70;
    static final int height = 70;
    int moveTime = 1000;
    final int num;
    protected Salon salon;
    ImageView img;
    int destX = -1, destY = -1;

    public Ludzik(String nazwa, Salon salon, int num, int moveTime) {
        super(nazwa);
        this.num = num;
        this.salon = salon;
        this.moveTime = moveTime;
    }

    public void transition(int posX, int posY, int time) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                TranslateTransition transition = new TranslateTransition(Duration.millis(time), img);
                transition.setToX(posX);
                transition.setToY(posY);
                transition.play();
            }
        });
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public ImageView getImg() {
        return img;
    }
}
