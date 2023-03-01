package models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import services.Dane;

public class Osoba implements Serializable {
    private String imie;
    private String nazwisko;
    private String numerTelefonu;
    private String adresEmail;
    private String pesel;
    private EnumSet<TypOsoby> typOsoby = EnumSet.noneOf(TypOsoby.class);
    private EnumSet<TypPracownika> typPracownika = EnumSet.noneOf(TypPracownika.class);
    private List<Zgloszenie> listaZgloszen = new ArrayList<>();
    private List<Wypozyczenie> listaWypozyczen = new ArrayList<>();
    private List<PracownikMyciePojazdu> p_m_p = new ArrayList<>();
    //Pracownik
    private String miasto;
    private boolean dostepny;
    //Pracownik na myjni
    private static double premiaZaUmytyPojazd = 1.00;
    //Koordynator
    private static double premia = 100.00;
    //Kierowca
    private static double premiaZaRelokowanyPojazd = 10.00;
    //Klient
    private String numerPrawaJazdy;
    private String numerDowoduOsobistego;

    private static List<Osoba> extent = new ArrayList();


    /**
     * Konstruktor dla zwykłej osoby
     * @param imie imię osoby
     * @param nazwisko nazwisko osoby
     * @param numerTelefonu numer telefonu osoby
     * @param adresEmail adres e-mail osoby
     * @param pesel unikalny pesel osoby
     */
    public Osoba(String imie, String nazwisko, String numerTelefonu, String adresEmail, String pesel)   {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.numerTelefonu = numerTelefonu;
        this.adresEmail = adresEmail;
        this.pesel = pesel;
        typOsoby.add(TypOsoby.Osoba);
        extent.add(this);
    }

    /**
     * Konstruktor dla klienta
     * @param imie imię klienta
     * @param nazwisko nazwisko klienta
     * @param numerTelefonu numer telefonu klienta
     * @param adresEmail adres e-mail klienta
     * @param pesel unikalny pesel klienta
     * @param numerPrawaJazdy unikalny numer prawa jazdy klienta
     * @param numerDowoduOsobistego unikalny numer dowodu osobistego klienta
     */
    public Osoba(String imie, String nazwisko, String numerTelefonu, String adresEmail, String pesel, String numerPrawaJazdy, String numerDowoduOsobistego) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.numerTelefonu = numerTelefonu;
        this.adresEmail = adresEmail;
        this.pesel = pesel;
        this.numerPrawaJazdy = numerPrawaJazdy;
        this.numerDowoduOsobistego = numerDowoduOsobistego;
        typOsoby.add(TypOsoby.Osoba);
        typOsoby.add(TypOsoby.Klient);
        extent.add(this);
    }

    /**
     * Konstruktor dla pracownika
     * @param imie imię pracownika
     * @param nazwisko nazwisko pracownika
     * @param numerTelefonu numer telefonu pracownika
     * @param adresEmail adres email pracownika
     * @param pesel pesel pracownika
     * @param miasto miasto świadczenia pracy pracownika
     */
    public Osoba(String imie, String nazwisko, String numerTelefonu, String adresEmail, String pesel, String miasto) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.numerTelefonu = numerTelefonu;
        this.adresEmail = adresEmail;
        this.pesel = pesel;
        this.miasto = miasto;
        this.dostepny = true;
        typOsoby.add(TypOsoby.Osoba);
        typOsoby.add(TypOsoby.Pracownik);
        extent.add(this);
    }

    /**
     * Metoda reverse-connection służąca do zapisu danych o asocjacji
     * o relokacji klas Zgłoszenie - Osoba
     * @param zgloszenie zgłoszenie, które dotyczy konkretnej osoby
     */
    public void dodajZgloszenie(Zgloszenie zgloszenie)  {
        if(!listaZgloszen.contains(zgloszenie)) {
            listaZgloszen.add(zgloszenie);
            zgloszenie.przypiszKierowce(this);
        }
    }

    /**
     * Metoda usuwająca zapis relokacji
     * @param zgloszenie obiekt Zgłoszenie
     */
    public void usunZgloszenie(Zgloszenie zgloszenie)   {
        if(listaZgloszen.contains(zgloszenie))  {
            listaZgloszen.remove(zgloszenie);
            zgloszenie.usunOsobe(this);
        }
    }

    /**
     * Metoda reverse-connection służąca do zapisu danych o asocjacji
     * o wypożyczeniu klas Wypozyczenie - Osoba
     * @param wypozyczenie obiekt Wypozyczenie
     */
    public void dodajWypozyczenie(Wypozyczenie wypozyczenie)    {
        if(!listaWypozyczen.contains(wypozyczenie))   {
            listaWypozyczen.add(wypozyczenie);
            wypozyczenie.ustawOsobe(this);
        }
    }

    /**
     * Metoda służąca do ustawienia danych klienta w przypadku dodania
     * osobie dodatkowych uprawnień
     * @param numerPrawaJazdy unikalny numer prawa jazdy - String
     * @param numerDowoduOsobistego unikalny numer dowodu osobistego - STring
     */
    public void ustawDaneKlienta(String numerPrawaJazdy, String numerDowoduOsobistego)  {
        this.numerPrawaJazdy = numerPrawaJazdy;
        this.numerDowoduOsobistego = numerDowoduOsobistego;
    }

    /**
     * Metoda służąca do ustawienia danych pracownika w przypadku dodania
     * osobie dodatkowych uprawnień
     * @param miasto miasto, w którym pracownik świwadczy pracę
     */
    public void ustawDanePracownika(String miasto)  {
        this.miasto = miasto;
        this.dostepny = true;
    }

    /**
     * Metoda zwracająca spreparowany ciąg znaków w celu poprawnej
     * reprezentacji danych
     * @return dane kontaktowe - String
     */
    public String getDaneKontaktowe()   {
        return "Numer telefonu : " + numerTelefonu + " adres email: " + adresEmail;
    }

    /**
     * Metoda dodająca / usuwająca osobie dodatkowe uprawnienia
     * jakimi dysponuje klient
     * @param decyzja boolean decydujący o odebraniu lub nadaniu uprawnień
     * @throws Exception osoba już jest klientem
     */
    public void ustawKlient(boolean decyzja)   throws Exception    {
        if(decyzja) {
            if (!typOsoby.contains(TypOsoby.Klient)) typOsoby.add(TypOsoby.Klient);
            else throw new Exception("Ta osoba jest już klientem");
        }   else    {
            if(typOsoby.contains(TypOsoby.Klient)) typOsoby.remove(TypOsoby.Klient);
            else throw new Exception("Ta osoba nie jest klientem");
        }
    }

    /**
     * Metoda dodająca / usuwająca osobie dodatkowe uprawnienia
     * jakimi dysponuje pracownik
     * @param decyzja boolean decydujący o odebraniu lub nadaniu uprawnień
     * @throws Exception osoba już jest pracownikiem
     */
    public void ustawPracownik(boolean decyzja)   throws Exception    {
        if(decyzja) {
            if (!typOsoby.contains(TypOsoby.Pracownik)) typOsoby.add(TypOsoby.Pracownik);
            else throw new Exception("Ta osoba jest już pracownikiem");
        }   else    {
            if(typOsoby.contains(TypOsoby.Pracownik)) typOsoby.remove(TypOsoby.Pracownik);
            else throw new Exception("Ta osoba nie jest pracownikiem");
        }
    }

    /**
     * Metoda dodająca / usuwająca osobie dodatkowe uprawnienia
     * jakimi dysponuje pracownik myjni
     * @param decyzja boolean decydujący o odebraniu lub nadaniu uprawnień
     * @throws Exception ta osoba jest już pracownikiem myjni
     */
    public void ustawPracownikMyjni(boolean decyzja)   throws Exception    {
        if(typOsoby.contains(TypOsoby.Pracownik)) {
            if (decyzja) {
                if (!typPracownika.contains(TypPracownika.MyjacySamochody)) typPracownika.add(TypPracownika.MyjacySamochody);
                else throw new Exception("Ta osoba jest już pracownikiem na myjni");
            } else {
                if (typPracownika.contains(TypPracownika.MyjacySamochody)) typPracownika.remove(TypPracownika.MyjacySamochody);
                else throw new Exception("Ta osoba nie jest pracownikiem na myjni");
            }
        }   else throw new Exception("Ta osoba nie jest pracownikiem");
    }

    /**
     * Metoda dodająca / usuwająca osobie dodatkowe uprawnienia
     * jakimi dysponuje pracownik - kierowca.
     * @param decyzja boolean decydujący o odebraniu lub nadaniu uprawnień
     * @throws Exception ta osoba jest już kierowcą
     */
    public void ustawPracownikKierowca(boolean decyzja)   throws Exception    {
        if(typOsoby.contains(TypOsoby.Pracownik)) {
            if (decyzja) {
                if (!typPracownika.contains(TypPracownika.Kierowca)){
                    typPracownika.add(TypPracownika.Kierowca);
                    this.dostepny = true;
                }
                else throw new Exception("Ta osoba jest już kierowcą");
            } else {
                if (typPracownika.contains(TypPracownika.Kierowca)) typPracownika.remove(TypPracownika.Kierowca);
                else throw new Exception("Ta osoba nie jest kierowcą");
            }
        }   else throw new Exception("Ta osoba nie jest pracownikiem");
    }

    /**
     * Metoda dodająca / usuwająca osobie dodatkowe uprawnienia
     * jakimi dysponuje pracownik - koordynator
     * @param decyzja boolean decydujący o odebraniu lub nadaniu uprawnień
     * @throws Exception ta osoba jest już koordynatorem
     */
    public void ustawPracownikKoordynator(boolean decyzja)   throws Exception    {
        if(typOsoby.contains(TypOsoby.Pracownik)) {
            if (decyzja) {
                if (!typPracownika.contains(TypPracownika.Koordynator)) typPracownika.add(TypPracownika.Koordynator);
                else throw new Exception("Ta osoba jest już koordynatorem");
            } else {
                if (typPracownika.contains(TypPracownika.Koordynator)) typPracownika.remove(TypPracownika.Koordynator);
                else throw new Exception("Ta osoba nie jest koordynatorem");
            }
        }   else throw new Exception("Ta osoba nie jest pracownikiem");
    }

    /**
     * Metoda sprawdzająca, czy dana osoba jest kierowcą
     * @return tak/nie
     */
    public boolean czyKierowca()    { return this.typPracownika.contains(TypPracownika.Kierowca); }
    /**
     * Metoda sprawdzająca, czy dana osoba jest pracownikiem
     * @return tak/nie
     */
    public boolean czyPracownik()   { return this.typOsoby.contains(TypOsoby.Pracownik); }
    /**
     * Metoda sprawdzająca, czy dana osoba jest klientem
     * @return tak/nie
     */
    public boolean czyKlient()  {   return this.typOsoby.contains(TypOsoby.Klient); }
    /**
     * Metoda zwracająca łańcuch znaków przechowujący podstawowe informacje o osobie
     * @return podstawowe dane o osobie w postaci łańcucha znaków
     */
    public String podstawoweDane() {    return imie+" "+nazwisko+" numer dowodu osobistego: "+numerDowoduOsobistego;   }
    /**
     * Metoda sprawdzająca, czy dana osoba jest pracownikiem myjni
     * @return tak/nie
     */
    public boolean czyPracownikMyjni()  {return this.typPracownika.contains(TypPracownika.MyjacySamochody);}
    /**
     * Metoda sprawdzająca, czy dana osoba jest kierowcą
     * @return tak/nie
     */
    public boolean czyPracownikKierowca()   {return this.typPracownika.contains(TypPracownika.Kierowca);}
    /**
     * Metoda sprawdzająca, czy dana osoba jest koorynatorem
     * @return tak/nie
     */
    public boolean czyPracownikKoordynator()    {return this.typPracownika.contains(TypPracownika.Koordynator);}
    /**
     * Metoda zwracająca imię osoby
     * @return łańcuch znaków przechowujący imię sooby
     */
    public String getImie() {return imie;}
    /**
     * Metoda zwracająca nazwisko osoby
     * @return łańcuch znaków przechowujący nazwisko sooby
     */
    public String getNazwisko() {return nazwisko;}

    //Pracownik
    //Pracownik na myjni

    /**
     * Metoda wyliczająca ilość umytych pojazdów na podstawie
     * liczności elementów obecnych w liście asocjacyjnej
     * @return ilość umytych pojazdów w formie liczbowej
     */
    public int obliczIloscUmytychAut()    {return p_m_p.size();}

    public void dodajMyciePojazdu(PracownikMyciePojazdu pracownikMyciePojazdu)  {
        if(typPracownika.contains(TypPracownika.MyjacySamochody))   {
            if(!p_m_p.contains(pracownikMyciePojazdu)) {
                p_m_p.add(pracownikMyciePojazdu);
            }
        }
    }

    /**
     * Metoda zwracająca spreprawowany łańcuch znaków  w celu poprawnej
     * reprezentacji w systemie przechowujący informacje o osobie.
     * @return ciąg znaków - string przechowujący informacje o osobie
     */
    @Override
    public String toString()    {
        String status = "STATUSY: ";
        if(typOsoby.contains(TypOsoby.Klient))  status = "Klient";
        if(typOsoby.contains(TypOsoby.Pracownik)) status = status + " Pracownik";
        if(typPracownika.contains(TypPracownika.MyjacySamochody)) status = status + " Myjacy samochody";
        if(typPracownika.contains(TypPracownika.Kierowca)) status = status + " Kierowca";
        if(typPracownika.contains(TypPracownika.Koordynator)) status = status + " Koordynator";
        String osobaDane = imie + " " + nazwisko + " numer telefonu: " + numerTelefonu + " pesel: " + pesel + " adres email: " + adresEmail;
        return osobaDane + " \n " + status;
    }

    /**
     * Metoda zwracająca typ stanowiska osoby
     * @return ciąg znaków, stanowiska oddzielone spacją.
     */
    public String stanowisko()  {
        String status = "";
        if(typOsoby.contains(TypOsoby.Klient))  status = status + "Klient";
        if(typOsoby.contains(TypOsoby.Pracownik)) status = status + " Pracownik";
        if(typPracownika.contains(TypPracownika.MyjacySamochody)) status = status + " Myjacy samochody";
        if(typPracownika.contains(TypPracownika.Kierowca)) status = status + " Kierowca";
        if(typPracownika.contains(TypPracownika.Koordynator)) status = status + " Koordynator";

        return status;
    }

    /**
     * Metoda zwracająca dane klienta
     * @return Spreparowany ciąg znaków w celu popranwej reprezentacji danych
     */
    public String daneKlienta() {
        if(typOsoby.contains(TypOsoby.Klient))  {
            return imie + " " + nazwisko + " numer telefonu: " + numerTelefonu + " pesel: " + pesel + " adres email: " + adresEmail;
        }
        return "";
    }

    //Koordynator

    //Kierowca

    /**
     * Metoda dostępna dla kierowcy wyliczająca ilość
     * relokacji pojazdów na podstawie liczności
     * elementów w liście asocjacyjnej
     * @return ilość relokacji wykonanych przez kierowcę
     * @throws Exception osoba nie jest kierowcą
     */
    public int obliczIloscRelokacji() throws Exception {
        if(!typPracownika.contains(TypPracownika.Kierowca)) throw new Exception("To nie jest kierowca");
        return listaZgloszen.size();
    }

    /**
     * Metoda ustawiająca dostępność kierowcy
     */
    public void setDostepny()   {
        this.dostepny = true;
    }

    /**
     * Metoda ustawiająca niedostępność kierowcy
     */
    public void setNiedostepny()    {
        this.dostepny = false;
    }

    /**
     * Metoda sprawdzająca dostępność kierowcy
     * @return dostępny / niedostępny (boolean)
     */
    public boolean getDostepny()    {
        return this.dostepny;
    }

    /**
     * Metoda sprawdzająca należną premię dla pracownika myjni
     * @return kwota premii
     * @throws Exception osoba nie jest pracownikiem myjni
     */
    public double getPremiaMyjacySamochody()   throws Exception{
        if(!typPracownika.contains(TypPracownika.MyjacySamochody)) throw new Exception("To nie jest pracownik na myjni");
        return premiaZaUmytyPojazd * p_m_p.size();
    }
    /**
     * Metoda sprawdzająca należną premię dla koordynatora
     * @return kwota premii
     * @throws Exception osoba nie jest koordyantorem
     */
    public double getPremiaKoordynator()   throws Exception{
        if(!typPracownika.contains(TypPracownika.Koordynator)) throw new Exception("To nie jest koordynator");
        return premia;
    }
    /**
     * Metoda sprawdzająca należną premię dla kierowcy
     * @return kwota premii
     * @throws Exception osoba nie jest kierowcą
     */
    public double getPremiaKierowca() throws Exception{
        if(!typPracownika.contains(TypPracownika.Kierowca)) throw new Exception("To nie jest kierowca");
        return premiaZaRelokowanyPojazd * listaZgloszen.size();
    }
    
    /**
     * Metoda zwracajaca pesel osoby
     * @return String - pesel osoby
     */
    public String getPesel()    {
        return this.pesel;
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
        extent = (ArrayList<Osoba>) stream.readObject();
    }
    /**
     * Reverse connection dla mycia pojazdu
     * @param myciePojazdu
     */
    public void ustawMycie(PracownikMyciePojazdu myciePojazdu)  {
        if(!p_m_p.contains(myciePojazdu)) {
            p_m_p.add(myciePojazdu);
            myciePojazdu.ustawPracownika(this);
        }
    }
    public static List<Osoba> getExtent()    {
        return extent;
    }
        /**
     * Metoda odpowiedzialna za zaktualizowanie stanu obiektu w ekstensji
     * @param osoba obiekt do zaktualizowania
     */
    public static void zaktualizujOsobe(Osoba osoba) {
        int iterator = 0;
        for (Osoba o : extent) {
            if (osoba.podstawoweDane().equals(o.podstawoweDane())) {
                extent.set(iterator, osoba);
            }
            iterator++;
        }
    }
        /**
     * Metoda wyszukująca pracowników, którzy mają status kierowcy
     * @return lista kierowców w postaci tablicy łańcuchów znaków
     */
    public static String[] pobierzDostepnychKierowcow() {
        int iterator = 0;
        for (Osoba osoba : extent) {
            if (osoba.czyKierowca() && osoba.getDostepny()) {
                iterator++;
            }
        }
        String[] dostepniKierowcy = new String[iterator];
        iterator = 0;
        for (Osoba osoba : extent) {
            if (osoba.czyKierowca() && osoba.getDostepny()) {
                dostepniKierowcy[iterator++] = osoba.getImie() + " " + osoba.getNazwisko();
            }
        }
        return dostepniKierowcy;
    }
        /**
     * Metoda zwracająca obiekt osoby na podstawie jej unikalnych danych
     * @param osoba unikalne informacje o osobie (toString)
     * @return obiekt klasy Osoba
     */
    public static Osoba znajdzOsobe(String osoba) {
        for (Osoba o : extent) {
            if ((o.getImie() + " " + o.getNazwisko()).equals(osoba)) {
                return o;
            }
        }
        return null;
    }
       /**
     * Metoda zwracająca wszystkie osoby
     * @return lista osób zapisanych w systemie
     */
    public static List<Osoba> pobierzOsoby()    {
        return extent;
    }
        /**
     * Metoda wyszykująca osobę po jej peselu
     * @param pesel - String, numer pesel osoby
     * @return obiekt typu osoba
     */
    public static Osoba pobierzOsobePoPeselu(String pesel)  {
        for(Osoba o : extent)    {
            if(o.getPesel().equals(pesel))  {
                return o;
            }
        }
        return null;
    }
        /**
     * Metoda odpowiedzialna za dodawanie nowej osoby, a następnie zapisanie jej. Podczas działania należy podać odpowiednie dane.
     */
    public static void dodajOsobe(String imie, String nazwisko, String numerTelfonu, String email, String pesel) {
        try {
            if(imie == "" || nazwisko == "" || numerTelfonu == "" || email == "" || pesel == "")    {
                throw new Exception("Nieprawidłowe dane");
            }
        }   catch(Exception e)  {
            e.printStackTrace();
        }
        Osoba osoba = new Osoba(imie, nazwisko, numerTelfonu, email, pesel);
        //extent.add(osoba);
        try {
            Dane.zapiszDane();
        }   catch(Exception e)  {
            e.printStackTrace();
        }
        System.out.println("Dane osoby zostały zapisane");
    }

        /**
     * Metoda odpoweidzialna za dodawanie klienta. Uruchamiany jest odpowiedni konstruktor w klasie Osoba.
     */
    public static void dodajKlienta(String imie, String nazwisko, String numerTelefonu, String adresEmail, String pesel, String numerPrawaJazdy, String numerDowoduOsobistego) {
        try {
            if(imie == "" || nazwisko == "" || numerTelefonu == "" || adresEmail == "" || pesel == "" || numerPrawaJazdy == " " || numerDowoduOsobistego == "") {
                throw new Exception("Błędne dane");
            }
            Osoba osoba = new Osoba(imie, nazwisko, numerTelefonu, adresEmail, pesel, numerPrawaJazdy, numerDowoduOsobistego);
            //extent.add(osoba);
            Dane.zapiszDane();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        /**
     * Metoda odpoweidzialna za dodawanie pracownika. Uruchamiany jest odpowiedni konstruktor w klasie Osoba.
     */
    public static void dodajPracownika(String imie, String nazwisko, String numerTelefonu, String adresEmail, String pesel, String miasto)   {
        try {
            if(imie == "" || nazwisko == "" || numerTelefonu == "" || adresEmail == "" || pesel == "" || miasto == " ") {
                throw new Exception("Błędne dane");
            }
            Osoba pracownik = new Osoba(imie, nazwisko, numerTelefonu, adresEmail, pesel, miasto);
            //extent.add(pracownik);
            Dane.zapiszDane();
        }   catch(Exception e)  {
            e.printStackTrace();
        }
    }
}

/**
 * Typ wyliczeniowy
 * ograniczajacy typ pracownika
 */
enum TypPracownika implements Serializable{
    Koordynator,
    MyjacySamochody,
    Kierowca
}

/**
 * Typ wyliczeniowy
 * ograniczający typ osoby
 */
enum TypOsoby implements Serializable{
    Osoba,
    Klient,
    Pracownik
}


