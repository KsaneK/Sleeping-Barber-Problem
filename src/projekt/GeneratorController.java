package projekt;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class GeneratorController {
    @FXML
    private TextField liczba_klientow;
    @FXML
    private TextField liczba_fryzjerow;
    @FXML
    private TextField liczba_foteli;
    @FXML
    private TextField liczba_uslug;
    @FXML
    private TextField miejsca_w_poczekalni;
    @FXML
    private TextField dlugosc_animacji;
    @FXML
    private VBox specjalizacje;
    @FXML
    private Text zostalo;

    int ile_klientow, ile_fryzjerow, ile_foteli, ile_uslug, ile_miejsc_w_poczekalni, dl_animacji;

    @FXML
    private void dalej(MouseEvent mouseEvent) {
        ile_klientow = Integer.parseInt(liczba_klientow.getText());
        ile_fryzjerow = Integer.parseInt(liczba_fryzjerow.getText());
        ile_foteli = Integer.parseInt(liczba_foteli.getText());
        ile_uslug = Integer.parseInt(liczba_uslug.getText());
        ile_miejsc_w_poczekalni = Integer.parseInt(miejsca_w_poczekalni.getText());
        dl_animacji = Integer.parseInt(dlugosc_animacji.getText());
        zostalo.setText("Pozostało: " + ile_fryzjerow);
        specjalizacje.getChildren().clear();
        for (int i = 0; i < ile_uslug; i++) {
            HBox hBox = new HBox();
            Button minus = new Button("-");
            Text text = new Text("0");
            Button plus = new Button("+");

            minus.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (Integer.parseInt(text.getText()) > 0) {
                        int x = Integer.parseInt(text.getText());
                        x--;
                        text.setText(String.format("%d", x));
                        ile_fryzjerow++;
                        zostalo.setText("Pozostało: " + ile_fryzjerow);
                    }
                }
            });
            plus.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (ile_fryzjerow > 0) {
                        int x = Integer.parseInt(text.getText());
                        x++;
                        text.setText(String.format("%d", x));
                        ile_fryzjerow--;
                        zostalo.setText("Pozostało: " + ile_fryzjerow);
                    }
                }
            });

            hBox.getChildren().add(minus);
            hBox.getChildren().add(text);
            hBox.getChildren().add(plus);
            specjalizacje.getChildren().add(hBox);
        }
    }

    @FXML
    private void generuj(MouseEvent mouseEvent) {
        int[] fryzjerzy = new int[ile_uslug];
        int ilosc = 0;
        for (int i = 0; i < specjalizacje.getChildren().size(); i++) {
            HBox hBox = (HBox) specjalizacje.getChildren().get(i);
            String txt = ((Text) hBox.getChildren().get(1)).getText();
            fryzjerzy[i] = Integer.parseInt(txt);
            ilosc += fryzjerzy[i];
        }
        new Symulacja(ile_miejsc_w_poczekalni, ile_klientow, ilosc, ile_foteli, ile_uslug, fryzjerzy, dl_animacji).initStage();
    }
}
