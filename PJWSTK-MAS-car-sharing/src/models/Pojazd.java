package models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class Pojazd implements Serializable {
    private String numerVin;
    private String numerRejestracyjny;
    private String opisKaroserii;
    private String miejscePostoju = "";
    private String marka;
    private String model;
    private int stanLicznika;
    private List<Zgloszenie> zgloszenia;
    private Kategoria kategoria;
    private boolean wTrakcieWypozyczenia;
    private List<PracownikMyciePojazdu> myciaPojazdow = new ArrayList<>();
    private List<Wypozyczenie> wypozyczenia = new ArrayList<>();

    private static List<Pojazd> extent = new ArrayList<>();

    /**
     * Konstruktor tworzący nowy obiekt typu Pojazd
     * @param numerVin unikalny numer vin pojazdu
     * @param numerRejestracyjny unikalny numer rejestracji pojazdu
     * @param opisKaroserii opis karoserii pojazdu
     * @param miejscePostoju aktualne miejsce postoju pojazdu
     * @param marka marka pojazdu
     * @param model model pojazdu
     */
    public Pojazd(String numerVin, String numerRejestracyjny, String opisKaroserii, String miejscePostoju, String marka, String model) {
        this.numerVin = numerVin;
        this.numerRejestracyjny = numerRejestracyjny;
        this.opisKaroserii = opisKaroserii;
        this.miejscePostoju = miejscePostoju;
        this.marka = marka;
        this.model = model;
        this.stanLicznika = 0;
        this.wTrakcieWypozyczenia = false;
        extent.add(this);
    }

    /**
     * Metoda sprawdzająca stan licznika pojazdu
     * @return aktualny stan licznika
     */
    public int getPrzebieg()   {
        return stanLicznika;
    }

    /**
     * Metoda sprawdzająca stan karoserii pojazdu
     * @return aktualny stan karosierii pojazdu
     */
    public String wyswietlOpisKaroserii()   {
        return opisKaroserii;
    }

    /**
     * Metoda reverse-connection w ramach
     * asocjacji Pojazd - Zgloszenie
     * uzupełniająca listy asocjacyjne
     * @param zgloszenie powiązany obiekt Zgłoszenie
     */
    public void przypiszZgloszenie(Zgloszenie zgloszenie)   {
        if(!zgloszenia.contains(zgloszenie))   {
            zgloszenia.add(zgloszenie);
            zgloszenie.przypiszPojazd(this);
        }
    }

    /**
     * Metoda reverse-connection w ramach
     * asocjacji Pojazd - Kategoria
     * uzupełniająca dane asocjacyjne
     * @param kategoria powiązana kategoria pojazdu
     */
    public void przypiszKategorie(Kategoria kategoria)  {
        if(this.kategoria != kategoria) {
            this.kategoria = kategoria;
            kategoria.dodajPojazd(this);
        }
    }

    /**
     * Metoda reverse-connection w ramach
     * asocjacji z atybutem Pojazd - Mycie pojazdu
     * uzupełniająca dane asocjacyjne
     * @param myciePojazdu powiązany akt mycia pojazdu
     */
    //MP2
    //Reverse connection
    public void ustawMycie(PracownikMyciePojazdu myciePojazdu)  {
        if(!myciaPojazdow.contains(myciePojazdu)) {
            myciaPojazdow.add(myciePojazdu);
            myciePojazdu.ustawPojazd(this);
        }
    }


    /**
     * Metoda reverse-connection w ramach
     * asocjacji Pojazd - Wypozyczenie
     * uzupełniająca listy asocjacyjne
     * @param wypozyczenie powiązana instancja wypożyczenia pojazdu
     */
    public void noweWypozyczenie(Wypozyczenie wypozyczenie) {
        if(!wypozyczenia.contains(wypozyczenie))    {
            wypozyczenia.add(wypozyczenie);
            wTrakcieWypozyczenia = true;
            wypozyczenie.noweWypozyczenie(this);
        }
    }

    /**
     * Metoda sprawdzająca historyczne, obecne i przyszłe wypożyczenia pojazdu
     * @return lista wypożyczeń pojazdu
     */
    public List<Wypozyczenie> getWypozyczenia() {
        return wypozyczenia;
    }

    /**
     * Metoda sprwadzająca stan licznika pojazdu
     * @return aktualny stan licznika pojazdu
     */
    public int getStanLicznika()    {
        return stanLicznika;
    }

    /**
     * Metoda sprawdzająca numer vin pojazdu
     * @return numer vin pojazdu
     */
    public String getNumerVin() {
        return numerVin;
    }

    /**
     * Metoda odwołująca się za pomocą pola asocjacyjnego
     * do powiązanego obiektu typu Kategoria
     * sprwadzająca ceny za wynajem pojazdu
     * @return trzyelementowa tablica przechowująca ceny z wypożyczenie pojazdu
     */
    public double[] getCenaZaDzien()  {
        return kategoria.getCena();
    }

    /**
     * Metoda aktualizująca przebieg pojazdu
     * @param przebieg nowy przebieg pojazdu w formie łańcuchu znaków
     */
    public void aktualizujPrzebieg(String przebieg) {
        this.stanLicznika = Integer.parseInt(przebieg);
    }

    /**
     * Metoda pomocnicza 'zwalniająca' pojazd zakończeniu wypożyczenia
     */
    public void zakonczWypozyczenie()   {
        this.wTrakcieWypozyczenia = false;
    }

    /**
     * Metoda sprawdzająca, czy pojazd jest w trakcie wypożyczenia
     * @return boolean (tak/nie) czy pojazd jest w trakcie wypożyczenia
     */
    public boolean czyWTrakiceWypozyczenia()    {return wTrakcieWypozyczenia;}

    /**
     * Metoda sprawdzająca aktualne miejsce postoju pojazdu
     * @return aktualne miejsce postoju pojazdu
     */
    public String getMiejscePostoju()   { return miejscePostoju;}

    /**
     * Metoda zwracająca markę pojazdu
     * @return marka pojazdu (String)
     */
    public String getMarka()    {
        return this.marka;
    }

    /**
     * Metoda zwracająca model pojazdu
     * @return model pojazdu (String)
     */
    public String getModel()    {
        return this.model;
    }

    /**
     * Metoda zwracająca numer rejestracyjny pojazdu
     * @return numer rejestracyjny pojazdu
     */
    public String getRejestracja()  {
        return this.numerRejestracyjny;
    }

    /**
     * Metoda zwracająca listę dotyczącą asocjacji z atrybutem 
     * osoba <-> pojazd (mycie)
     * @return lista typu MyciePojazdu
     */
    public List<PracownikMyciePojazdu> getMycia()    {
        return myciaPojazdow;
    }

    /**
     * Metoda zwracająca kategorię pojazdu
     * @return obiekt kategorii pojazdu
     */
    public Kategoria getKategoria() {
        return this.kategoria;
    }

    /**
     * Metoda preparująca odpowiedni ciąg znaków
     * przechowujący informacje o pojeździe
     * w celu poprawnej prezentacji danych
     * @return ciąg znaków informacji o pojeździe
     */
    @Override
    public String toString()    {
        return marka + " " + model + " numer rejestracyjny: " + numerRejestracyjny + " kategoria: " + kategoria.getNazwa();
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
        extent = (ArrayList<Pojazd>) stream.readObject();
    }

    public static List<Pojazd> getExtent()    {
        return extent;
    }
        /**
     * Metoda odpowiedzialna za zaktualizowanie stanu obiektu w ekstensji
     * @param pojazd obiekt do zaktualizowania
     */
    public static void zaktualizujPojazd(Pojazd pojazd) {
        int iterator = 0;
        for (Pojazd p : extent) {
            if (p.getNumerVin().equals(pojazd.getNumerVin())) {
                extent.set(iterator, pojazd);
            }
            iterator++;
        }
    }
        /**
     * Metoda odpowiedzialna za wyszukanie samochodów, których data wypożyczeń nie koliduje
     * z datami podanymi jako argumenty
     * @param dataOd data rozpoczęcia proponowanego wynajmu
     * @param dataDo data zakończenia proponowanego wynajmu
     * @return lista pojazdów, których daty wypożyczeń nie kolidują z proponowaną datą wypożyczenia.
     */
    public static List<Pojazd> sprawdzDostepnePojazdy(String dataOd, String dataDo) {
        List<Pojazd> dostepnePojazdy = new ArrayList<>();
        //Sprawdzam wszystkie wypożyczenia w ramach każdego pojazdu.
        //Jeżeli dla któregoś pojazdu któraś z dat wynajmów koliduje z proponowaną datą wypożyczenia
        //wówczas samochód nie zostanie dodany do listy dostępnych pojazdów
        for (Pojazd pojazd : extent) {
            boolean overlap = false;
            for (Wypozyczenie wypozyczenie : pojazd.getWypozyczenia()) {
                try {
                    Date pojazdDataOdDate = new SimpleDateFormat("dd/MM/yyyy").parse(dataOd);
                    Date pojazdDataDoDate = new SimpleDateFormat("dd/MM/yyyy").parse(dataDo);
                    Date rezerwacjaOd = wypozyczenie.getDataWypozyczenia();
                    Date rezerwacjaDo = wypozyczenie.getDataZwrotu();
                    //Metoda sprawdzająca, czy daty nie kolidują
                    if ((pojazdDataOdDate.getTime() <= rezerwacjaDo.getTime()) && (rezerwacjaOd.getTime() <= pojazdDataDoDate.getTime())) {
                        overlap = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (!overlap) dostepnePojazdy.add(pojazd);
        }
        return dostepnePojazdy;
    }
        /**
     * Metoda wykorzystywana podczas planowania relokacji.
     * Wyszukuje samochody, które nie są w trakcie wypożyczenia.
     * @return lista samochodów w postaci tablicy łańcuchów znaków
     */
    public static String[] pobierzDostepnePojazdy() {
        int iterator = 0;
        for (Pojazd pojazd : extent) {
            if (!pojazd.czyWTrakiceWypozyczenia()) iterator++;
        }
        String[] wypuszczonePojazdy = new String[iterator];
        iterator = 0;
        for (Pojazd pojazd : extent) {
            if (!pojazd.czyWTrakiceWypozyczenia()) wypuszczonePojazdy[iterator++] = pojazd.toString();
        }
        return wypuszczonePojazdy;
    }
        /**
     * Metoda znajdująca obiekt pojazdu na podstawie jego unikalnych danych
     * @param pojazdInfo unikalne informacje o pojeździe (toString)
     * @return obiekt klasy Pojazd
     */
    public static Pojazd znajdzPojazd(String pojazdInfo) {
        for (Pojazd p : extent) {
            if (p.toString().equals(pojazdInfo)) return p;
        }
        return null;
    }
        /**
     * Metoda zwracająca wszystkie pojazdy
     * @return lista pojazdów zapisanych w systemie
     */
    public static List<Pojazd> pobierzPojazdy() {
        return extent;
    }
        /**
     * Metoda wyszukująca pojazd po jego tablicy rejestracyjnej
     * @param rejestracja - String, numer tablicy rejestracyjnej pojazdu
     * @return obiekt typu Pojazd
     */
    public static Pojazd pobierzPojazdPoRejestracji(String rejestracja)   {
        for(Pojazd p : extent) {
            if(p.getRejestracja().equals(rejestracja))  {
                return p;
            }
        } 
        return null;
    }
}
