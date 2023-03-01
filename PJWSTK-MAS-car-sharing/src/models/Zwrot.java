package models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Zwrot implements Serializable {
    private Date dataZwrotu;
    private String miejsceZwrotu;
    private double kwotaKary = Double.NaN;
    private int stanLicznika;
    private String opisStanuSamochodu;
    private Wypozyczenie wypozyczenie;

    private static List<Zwrot> extent = new ArrayList<>();

    /**
     * Konstruktor klasy Zwrot nieprzewidujący kary za nieregulaminowy zwrot pojazdu
     * @param wypozyczenie klasa wypozyczenie, której dotyczy zwort
     * @param dataZwrotu data zwrotu pojazdu
     * @param miejsceZwrotu miejsce zwrotu pojazdu
     * @param stanLicznika stan licznika pojazdu
     * @param opisStanuSamochodu opis stanu samochodu po zwrocie
     */
    private Zwrot(Wypozyczenie wypozyczenie, Date dataZwrotu, String miejsceZwrotu, int stanLicznika, String opisStanuSamochodu) {
        this.dataZwrotu = dataZwrotu;
        this.miejsceZwrotu = miejsceZwrotu;
        this.stanLicznika = stanLicznika;
        this.wypozyczenie = wypozyczenie;
        this.opisStanuSamochodu = opisStanuSamochodu;
        extent.add(this);
    }

    /**
     * Konstruktor klasy Zwrot przewidujący karę za nieregulaminowy zwrot
     * @param wypozyczenie klasa wypozyczenie, której dotyczy zwort
     * @param dataZwrotu data zwrotu pojazdu
     * @param miejsceZwrotu miejsce zwrotu pojazdu
     * @param stanLicznika stan licznika pojazdu
     * @param opisStanuSamochodu opis stanu samochodu po zwrocie
     * @param kwotaKary nałożona kwota kary za nieregulaminowy zwrot pojazdu
     */
    private Zwrot(Wypozyczenie wypozyczenie, Date dataZwrotu, String miejsceZwrotu, int stanLicznika, String opisStanuSamochodu, double kwotaKary) {
        this.dataZwrotu = dataZwrotu;
        this.miejsceZwrotu = miejsceZwrotu;
        this.stanLicznika = stanLicznika;
        this.wypozyczenie = wypozyczenie;
        this.opisStanuSamochodu = opisStanuSamochodu;
        this.kwotaKary = kwotaKary;
        extent.add(this);
    }

    /**
     * Metoda wywołująca konstruktor prywatny - tworząca nowy zwrot.
     * @param wypozyczenie klasa wypozyczenie, której dotyczy zwort
     * @param dataZwrotu data zwrotu pojazdu
     * @param miejsceZwrotu miejsce zwrotu pojazdu
     * @param stanLicznika stan licznika pojazdu
     * @param opisStanuSamochodu opis stanu samochodu po zwrocie
     * @return stworzony obiekt typu zwort
     * @throws Exception próba stworzenia zwrotu bez istniejącego wypożyczenia
     */
    public static Zwrot stworzZwrot(Wypozyczenie wypozyczenie, Date dataZwrotu, String miejsceZwrotu, int stanLicznika, String opisStanuSamochodu) throws Exception {
        if(wypozyczenie == null)    throw   new Exception("Nie można stworzyć zwrotu bez wypożyczenia");
        Zwrot zwrot = new Zwrot(wypozyczenie, dataZwrotu, miejsceZwrotu, stanLicznika, opisStanuSamochodu);
        wypozyczenie.zwrocPojazd(zwrot);
        return zwrot;
    }

    /**
     * Metoda wywołująca konstruktor prywatny - tworząca nowy zwrot.
     * Metoda przeiduje karę za nieregulaminowy zwrot pojazdu
     * @param wypozyczenie klasa wypozyczenie, której dotyczy zwort
     * @param dataZwrotu data zwrotu pojazdu
     * @param miejsceZwrotu miejsce zwrotu pojazdu
     * @param stanLicznika stan licznika pojazdu
     * @param opisStanuSamochodu opis stanu samochodu po zwrocie
     * @param kwotaKary nałożona kwota kary za nieregulaminowy zwrot pojazdu
     * @return stworzony obiekt typu zwort
     * @throws Exception próba stworzenia zwrotu bez istniejącego wypożyczenia
     */
    public static Zwrot stworzZwrot(Wypozyczenie wypozyczenie, Date dataZwrotu, String miejsceZwrotu, int stanLicznika, String opisStanuSamochodu, double kwotaKary) throws Exception {
        if(wypozyczenie == null)    throw   new Exception("Nie można stworzyć zwrotu bez wypożyczenia");
        Zwrot zwrot = new Zwrot(wypozyczenie, dataZwrotu, miejsceZwrotu, stanLicznika, opisStanuSamochodu, kwotaKary);
        wypozyczenie.zwrocPojazd(zwrot);
        return zwrot;
    }

    /**
     * Metoda sprawdzająca nowy stan licznika po zwrocie pojazdu
     * @return stan licznika po zwrocie pojazdu
     */
    public int getStanLicznika()    {
        return stanLicznika;
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
        extent = (ArrayList<Zwrot>) stream.readObject();
    }
    public static List<Zwrot> getExtent()    {
        return extent;
    }
}
