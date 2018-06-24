package projekt;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Symulacja {
    private int ile_miejsc_w_poczekalni;
    private int ile_foteli;
    private int ile_uslug;
    private int ile_klientow;
    private int ile_fryzjerow;
    private int[] ile_fryzjerow_spec;
    private int dlugosc_animacji;

    Fryzjer[] fryzjerzy;
    Klient[] klienci;

    public Symulacja(int poj_pocz, int klient, int fryz, int fot, int usl, int[] fr_us, int dl_animacji) {
        this.ile_miejsc_w_poczekalni = poj_pocz;
        this.ile_foteli = fot;
        this.ile_uslug = usl;
        this.ile_klientow = klient;
        this.ile_fryzjerow = fryz;
        this.ile_fryzjerow_spec = fr_us;
        this.dlugosc_animacji = dl_animacji;
    }
    
    
    public void initStage() {
        Stage stage = new Stage();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                for(int i = 0; i < klienci.length; i++)
                    klienci[i].stopThread();
                for(int i = 0; i < fryzjerzy.length; i++)
                    fryzjerzy[i].stopThread();
            }
        });
        Pane root = new Pane();
        GridPane krzesla = new GridPane();
        GridPane fotele = new GridPane();
        fotele.setTranslateX(300);
        fotele.setTranslateY(200);
        root.getChildren().add(fotele);
        root.getChildren().add(krzesla);
        stage.setTitle("Salon Fryzjerski");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();

        Salon salon = new Salon(ile_miejsc_w_poczekalni, ile_foteli, ile_uslug);

        for (int i = 0; i < ile_miejsc_w_poczekalni; i++) {
            krzesla.add(new Krzeslo(i), 0, i);
        }
        for (int i = 0; i < ile_foteli; i++) {
            fotele.add(new Fotel(i), i, 0);
        }

        fryzjerzy = new Fryzjer[ile_fryzjerow];
        int fryz_index = 0;
        for (int i = 0; i < ile_fryzjerow_spec.length; i++) {
            for (int j = 0; j < ile_fryzjerow_spec[i]; j++) {
                fryzjerzy[fryz_index] = new Fryzjer("F-" + (fryz_index + 1), salon, i, fryz_index++, dlugosc_animacji);
            }
        }
        klienci = new Klient[ile_klientow];
        for (int i = 0; i < ile_klientow; i++)
            klienci[i] = new Klient("K-" + (i + 1), salon, i, dlugosc_animacji);


        for (int i = 0; i < ile_fryzjerow; i++) {
            root.getChildren().add(fryzjerzy[i].getImg());
            fryzjerzy[i].setFryzjerzy(fryzjerzy);
            fryzjerzy[i].start();
        }
        for (int i = 0; i < ile_klientow; i++) {
            root.getChildren().add(klienci[i].getImg());
            klienci[i].setKlienci(klienci);
            klienci[i].start();
        }
    }
}
