package services;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import models.DodatkoweOpcje;
import models.Kategoria;
import models.Osoba;
import models.Pojazd;
import models.PracownikMyciePojazdu;
import models.Wypozyczenie;
import models.Zgloszenie;
import models.Zwrot;

public class Dane {
    final static String extentFile = "data\\mas-extent.ser";

    /**
     * Metoda wczytująca dane z pliku ekstensji (data/mas-extent.ser) do list ekstensji
     */
    public static void wczytajDane()    {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(extentFile));
            DodatkoweOpcje.readExtent(in);
            Kategoria.readExtent(in);
            Osoba.readExtent(in);
            Pojazd.readExtent(in);
            PracownikMyciePojazdu.readExtent(in);
            Wypozyczenie.readExtent(in);
            Wypozyczenie.readExtent(in);
            Zgloszenie.readExtent(in);
            Zwrot.readExtent(in);
            in.close();
        }   catch(Exception e)  {
            e.printStackTrace();
        }
    }
    /**
     * Metoda zapisująca dane do pliku ekstensji
     */
    public static void zapiszDane() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(extentFile));
            DodatkoweOpcje.writeExtent(out);
            Kategoria.writeExtent(out);
            Osoba.writeExtent(out);
            Pojazd.writeExtent(out);
            PracownikMyciePojazdu.writeExtent(out);
            Wypozyczenie.writeExtent(out);
            Wypozyczenie.writeExtent(out);
            Zgloszenie.writeExtent(out);
            Zwrot.writeExtent(out);
            out.close();

        }   catch(Exception e)  {
            e.printStackTrace();
        }
    }
    
}
