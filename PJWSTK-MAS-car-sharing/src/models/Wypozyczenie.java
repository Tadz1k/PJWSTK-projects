package models;

import services.Dane;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Wypozyczenie implements Serializable {
    private List<DodatkoweOpcje> dodatkoweOpcje = new ArrayList<>();
    private Zwrot zwrot;
    private static List<Zwrot> wszystkieZwroty = new ArrayList<>();
    private Date dataWypozyczenia;
    private Date dataZwrotu;
    private String miejsceOdbioru;
    private String miejsceZwrotu;
    private int stanLicznika;
    private Osoba osobaWypozyczajaca;
    private Pojazd pojazd = null;
    private boolean zwrocony;

    private static List<Wypozyczenie> extent = new ArrayList<>();

    /**
     * Konstruktor tworzący obiekt typu Wypożyczenie
     * @param dodatkoweOpcje lista dodatkowych opcji wybranych podczas rejestracji wypożyczenia
     * @param dataWypozyczenia data początku wypożyczenia
     * @param dataZwrotu data zwrotu pojazdu
     * @param miejsceOdbioru miejsce odbioru pojazdu
     * @param miejsceZwrotu miejsce zwrotu pojazdu
     * @param stanLicznika stan licznika przed wydaniem pojazdu
     * @param pojazd pojazd, który ma być wypożyczony
     * @param osoba osoba wypożyczająca pojazd
     */
    public Wypozyczenie(List<DodatkoweOpcje> dodatkoweOpcje, Date dataWypozyczenia, Date dataZwrotu, String miejsceOdbioru, String miejsceZwrotu, int stanLicznika, Pojazd pojazd, Osoba osoba) {
        this.dataWypozyczenia = dataWypozyczenia;
        this.dataZwrotu = dataZwrotu;
        this.miejsceOdbioru = miejsceOdbioru;
        this.miejsceZwrotu = miejsceZwrotu;
        this.stanLicznika = stanLicznika;
        this.zwrocony = false;
        noweWypozyczenie(pojazd);
        ustawOsobe(osoba);
        ustawDodatkoweOpcje(dodatkoweOpcje);
        extent.add(this);
    }

    /**
     * Metoda obliczająca koszt wypożyczenia
     * @return koszt wypożyczenia pojazdu
     */
    public double obliczKoszt()   {
        long roznica = dataZwrotu.getTime() - dataWypozyczenia.getTime();
        long dni = TimeUnit.DAYS.convert(roznica, TimeUnit.MILLISECONDS);
        double cena = 0.0;
        for(DodatkoweOpcje dodatkowaOpcja : dodatkoweOpcje) {
            cena = cena + dodatkowaOpcja.getCena() * dni;
        }
        double[] ceny = pojazd.getCenaZaDzien();
        double tempCena = 0.0;
        if(dni < 2) cena = cena + ceny[0];
        if(dni > 1 && dni <= 7) tempCena = ceny[1];
        else tempCena = ceny[2];
        for(int i = 0; i<dni; i++)  {
            cena = cena+tempCena;
        }
        return cena;
    }

    /**
     * Metoda ustawiająca dodatkowe opcje w ramach wypożyczenia
     * @param dodatkowaOpcja lista dodatkowych opcji
     */
    public void ustawDodatkoweOpcje(List<DodatkoweOpcje> dodatkowaOpcja)  {
        this.dodatkoweOpcje = dodatkowaOpcja;
        for(DodatkoweOpcje dodop : this.dodatkoweOpcje) {
            dodop.przypiszWypozyczenie(this);
        }
    }

    /**
     * Metoda dodająca pojdynczą dodatkową opcję w ramach wypożyczenia
     * @param dodatkoweOpcje dodatkowa opcja
     */
    public void ustawDodatkowaOpcje(DodatkoweOpcje dodatkoweOpcje)  {
        if(!this.dodatkoweOpcje.contains(dodatkoweOpcje) && this.dodatkoweOpcje.size() != 3)   {
            this.dodatkoweOpcje.add(dodatkoweOpcje);
            dodatkoweOpcje.przypiszWypozyczenie(this);
        }
    }

    /**
     * Metoda reverse-connection w ramach asocjacji Wypozyczenie-Osoba
     * uzupełniająca listy asocjacyjne
     * @param osoba osoba wypożyczająca pojazd
     */
    public void ustawOsobe(Osoba osoba) {
        if(this.osobaWypozyczajaca == null) {
            this.osobaWypozyczajaca = osoba;
            osoba.dodajWypozyczenie(this);
        }
    }

    /**
     * Metoda reverse-connection w ramach asocjacji Wypożyczenie-Pojazd
     * uzupełniająca dane asocjacyjne
     * @param pojazd pojazd, który ma być wypożyczony
     */
    public void noweWypozyczenie(Pojazd pojazd) {
        if(this.pojazd == null) {
            this.pojazd = pojazd;
            pojazd.noweWypozyczenie(this);
        }
    }

    /**
     * Metoda zwracająca wypożycozny pojazd
     * @return wypożyczony pojazd
     */
    public Pojazd getPojazd()   {
        return pojazd;
    }

    /**
     * Metoda sprawdzająca datę wypożyczenia pojazdu
     * @return data wypożyczenia pojazdu
     */
    public Date getDataWypozyczenia()   {
        return dataWypozyczenia;
    }

    /**
     * Metoda sprawdzająca datę zwortu pojazdu
     * @return data zwrotu pojazdu
     */
    public Date getDataZwrotu() {
        return dataZwrotu;
    }

    /**
     * Metoda rejestrująca zwrot pojazdu
     * @param zwrot obiekt typu zwrot - w ramach asocjaji kwalifikowanej
     * @throws Exception zwrot został już wykonany
     */
    public void zwrocPojazd(Zwrot zwrot) throws Exception   {
        if(wszystkieZwroty.contains(zwrot)) throw new Exception("Ten zwrot jest już zrealizowany dla innej rezerwacji");
        this.zwrot = zwrot;
        wszystkieZwroty.add(zwrot);
        this.zwrocony = true;
        zaktualizujWypozyczenie(this);
        Zwrot.getExtent().add(zwrot);
        Dane.zapiszDane();
    }

    /**
     * Metoda sprawdzająca, czy w ramach wypożyczenia pojazd jest już zwrócony
     * @return boolean (tak/nie), czy pojazd został zwrócony
     */
    public boolean czyZwrocony()   {
        return this.zwrocony;
    }

    /**
     * Metoda zwracająca obiekt osoby wypożyczającej pojazd
     * @return obiekt typu Osoba
     */
    public Osoba getOsoba() {
        return this.osobaWypozyczajaca;
    }

    /**
     * Metoda preparująca ciąg znaków o wypożyczeniu pojazdu
     * w celu poprawnej reprezentacji danych
     * @return ciąg znaków o wypożyczeniu pojazdu
     */
    @Override
    public String toString()    {
        StringBuilder dodatkoweOpcjeString = new StringBuilder();
        for(DodatkoweOpcje dodatkowaOpcja : dodatkoweOpcje) {
            dodatkoweOpcjeString.append(dodatkowaOpcja.toString()).append("\n");
        }
        if(dodatkoweOpcje.size() == 0 || dodatkoweOpcje == null) dodatkoweOpcjeString.append("BRAK");
        return "Osoba wypożyczająca : " + osobaWypozyczajaca.podstawoweDane() + "\n Pojazd:" + pojazd.toString() + "\n Okres ypożyczenia: " + dataWypozyczenia.toString() + " - " + dataZwrotu.toString() + "\n" +
                "Miejsce odbioru : " + miejsceOdbioru + " miejsce zwrotu: " + miejsceZwrotu + "\nDodatkowe opcje: \n" + dodatkoweOpcjeString;
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
        extent = (ArrayList<Wypozyczenie>) stream.readObject();
    }
    public static List<Wypozyczenie> getExtent()    {
        return extent;
    }
        /**
     * Metoda odpowiedzialna za zaktualizowanie stanu obiektu w ekstensji
     * @param wypozyczenie obiekt do zaktualizowania
     */
    public static void zaktualizujWypozyczenie(Wypozyczenie wypozyczenie) {
        int iterator = 0;
        for (Wypozyczenie w : extent) {
            if (w.toString().equals(wypozyczenie.toString())) {
                extent.set(iterator, wypozyczenie);
            }
            iterator++;
        }
    }
}
