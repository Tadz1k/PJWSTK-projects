package models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PracownikMyciePojazdu implements Serializable {
    private Osoba pracownik;
    private Pojazd pojazd;
    private Date data;
    private String czas;

    private static List<PracownikMyciePojazdu> extent = new ArrayList<>();

    /**
     * Konstruktor tworzący nowy obiekt pośredni w ramach
     * asocjacji z atrybutem Osoba - MyciePojazdu
     * @param osoba osoba myjąca pojazd
     * @param myciePojazdu kat mycia pojazdu
     * @param data data mycia pojazdu
     */
    public PracownikMyciePojazdu(Osoba osoba, Pojazd pojazd, Date data, int czas) {
        this.pracownik = osoba;
        this.pojazd = pojazd;
        this.data = data;
        ustawPojazd(pojazd);
        ustawPracownika(osoba);
        extent.add(this);
    }

    //Reverse connection
    public void ustawPojazd(Pojazd pojazd) {
        this.pojazd = pojazd;
        pojazd.ustawMycie(this);
    }
    //Reverse connection
    public void ustawPracownika(Osoba osoba) {
        this.pracownik = osoba;
        osoba.ustawMycie(this);
    }

    /**
     * Metoda zwracająca informacje, kto mył pojazd
     * @return obiekt typu Osoba
     */
    public Osoba getOsoba() {
        return this.pracownik;
    }
    
    /**
     * Metoda zwracająca datę mycia pojazdu
     * @return obiekt typu Date
     */
    public Date getData()   {
        return this.data;
    }

    /**
     * Metoda zwracająca informacje o koszcie poniesionym podczas
     * mycia pojazdu
     * @return double - koszt mycia pojazdu
     */
    public double getCena() {
        return Integer.parseInt(czas) * 2.0;
    }

    /**
     * Zapis ekstensji do pliku
     * @param stream obiekt ObjectOutputStream zawierający dane o docelowym pliku
     * @throws IOException brak pliku
     */
    public static void writeExtent(ObjectOutputStream stream) throws IOException {
        stream.writeObject(extent);
    }
    /**
     * Odczyt ekstensji z pliku do listy
     * @param stream obiekt ObjectInputStream zawierający dane o pliku źródłowym
     * @throws IOException brak pliku
     * @throws ClassNotFoundException brak klasy docelowej
     */
    public static void readExtent(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        extent = (ArrayList<PracownikMyciePojazdu>) stream.readObject();
    }

    public static List<PracownikMyciePojazdu> getExtent()    {
        return extent;
    }
}
