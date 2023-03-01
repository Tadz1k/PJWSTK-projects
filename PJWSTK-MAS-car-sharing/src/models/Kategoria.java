package models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Kategoria implements Serializable {
    private String nazwaKategorii;
    private double wynajemJedenDzien;
    private double wynajemSiedemDni;
    private double wynajemPowyzejTygodnia;
    private List<Pojazd> pojazdy = new ArrayList<>();

    private static List<Kategoria> extent = new ArrayList<>();

    /**
     * Konstruktor towrzący nową kategorię pojazdu
     * @param nazwaKategorii nazwa kategorii - z ograniczeniem
     * @param wynajemJedenDzien cena za wynajem za jeden dzień
     * @param wynajemSiedemDni cena za ynajem powyżej jednego dnia, poniżej siedmiu
     * @param wynajemPowyzejTygodnia cena za wynajem powyżej 7 dni
     * @throws Exception
     */
    public Kategoria(String nazwaKategorii, double wynajemJedenDzien, double wynajemSiedemDni, double wynajemPowyzejTygodnia) throws Exception{
        if(!nazwaPoprawna(nazwaKategorii)) throw new Exception("Niepoprawna nazwa kategorii");
        this.nazwaKategorii = nazwaKategorii;
        this.wynajemJedenDzien = wynajemJedenDzien;
        this.wynajemSiedemDni = wynajemSiedemDni;
        this.wynajemPowyzejTygodnia = wynajemPowyzejTygodnia;
        extent.add(this);
    }

    /**
     * Metoda reverse connection odpowiedzialna za dodanie pojazdu
     * do listy asocjacyjnej i wywołanie metody
     * uzupełniającej dane o asocjacji w klasie powiązanej
     * @param pojazd obiekt Pojazd
     */
    public void dodajPojazd(Pojazd pojazd)   {
        if(this.pojazdy.contains(pojazd))   {
            pojazdy.add(pojazd);
            pojazd.przypiszKategorie(this);
        }
    }

    /**
     * Metoda zmieniająca ceny wypożyczenia pojazdu w obecnej kategorii
     * @param cena1 cena za 1 dzień
     * @param cena2 cena poniżej 7 dni, powyżej 1 dnia
     * @param cena3 cena powyżej 7 dni
     */
    public void zmienCeny(double cena1, double cena2, double cena3) {
        this.wynajemJedenDzien = cena1;
        this.wynajemSiedemDni = cena2;
        this.wynajemPowyzejTygodnia = cena3;
    }

    /**
     * Metoda zwracająca odpowiednio spreparowany ciąg znaków
     * na potrzeby reprezentacji danych w systemie
     * @return ciąg znaków String
     */
    @Override
    public String toString()    {
        return "models.Kategoria : " + nazwaKategorii  + " ceny : " + wynajemJedenDzien + " " + wynajemSiedemDni + " " + wynajemPowyzejTygodnia;
    }

    /**
     * Metoda zwracająca nazwę kategorii
     * @return ciąg znaków przechowujący nazwę kategorii
     */
    public String getNazwa(){
        return nazwaKategorii;
    }

    /**
     * Metoda zwracająca tablicę cen za wynajem pojazdu
     * w danej kategorii
     * @return trzyelemementowa tablica typu double przechowująca cennik
     */
    public double[] getCena() {
        return new double[]{wynajemJedenDzien, wynajemSiedemDni, wynajemPowyzejTygodnia};
    }

    /**
     * Metoda usuwająca obiekt pojazdu z listy asocjacyjnej
     * @param pojazd obiekt typu Pojazd, który usuwamy z listy
     */
    public void usunPojazd(Pojazd pojazd)    {
        pojazdy.remove(pojazd);
    }

    /**
     * Metoda sprawdzająca, czy zaproponowana nazwa kategorii
     * istnieje w ramach ograniczenia nazwy. (A,B,C,D,Premium,Van)
     * @param nazwa Ciąg znaków przechowujący proponowaną nazwę
     * @return boolean - czy nazwa jest poprawna
     */
    private boolean nazwaPoprawna(String nazwa)  {
        NazwyKategorii[] dostepneNazwyKategorii = NazwyKategorii.values();
        for(NazwyKategorii nk : dostepneNazwyKategorii) {
            if(nk.toString().equals(nazwa)) {
                return true;
            }
        }
        return false;
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
        extent = (ArrayList<Kategoria>) stream.readObject();
    }

    public static List<Kategoria> getExtent()    {
        return extent;
    }
}

/**
 * Typ wyliczeniowy ograniczający nazwy dla kategorii
 */
enum NazwyKategorii implements Serializable{
    A,
    B,
    C,
    D,
    Van,
    Premium
}
