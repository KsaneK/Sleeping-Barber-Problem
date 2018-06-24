package nogui;

public class Main {
    Salon salon;
    private static int poj_pocz = 6;
    private static int licz_fot = 3;
    private static int licz_klientow = 8;
    private static int ilosc_uslug = 3;



    public static void main(String[] args) {
        Salon salon = new Salon(poj_pocz, licz_fot, ilosc_uslug);

        Fryzjer[] fryzjerzy = new Fryzjer[4];
        fryzjerzy[0] = new Fryzjer("F-1", salon, 0);
        fryzjerzy[1] = new Fryzjer("F-2", salon, 0);
        fryzjerzy[2] = new Fryzjer("F-3", salon, 1);
        fryzjerzy[3] = new Fryzjer("F-4", salon, 2);

        Klient[] klienci = new Klient[licz_klientow];
        for(int i = 0; i < licz_klientow; i++)
            klienci[i] = new Klient("K-" + (i+1), salon, i);

        for(int i = 0; i < 4; i++)
            fryzjerzy[i].start();
        for(int i = 0; i < licz_klientow; i++)
            klienci[i].start();
    }
}
