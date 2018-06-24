package projekt;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Krzeslo extends ImageView {
    final int num;
    volatile boolean zajete;
    static final int width = 100;
    static final int height = 100;
    public Krzeslo(int num) {
        zajete = false;
        this.num = num;
        setImage(new Image(getClass().getClassLoader().getResource("krzeslo.png").toString()));
        setFitWidth(width);
        setFitHeight(height);
    }
}
