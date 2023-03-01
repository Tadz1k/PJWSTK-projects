import models.*;
import services.Dane;
import services.Gui;

public class Main {

    public static void main(String[] args) {
        Dane.wczytajDane();
        Gui gui = new Gui();
        gui.menuGlowne();


        }
}
