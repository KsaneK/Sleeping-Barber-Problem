package projekt;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Fotel extends ImageView {
    final int num;
    volatile boolean zajete;
    static final int width = 81;
    static final int height = 126;
    public Fotel(int num) {
        this.num = num;
        zajete = false;
        setImage(new Image(getClass().getClassLoader().getResource("fotel.png").toString()));
        setFitWidth(width);
        setFitHeight(height);
    }
}
