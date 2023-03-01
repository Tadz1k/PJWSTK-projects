package models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import services.Dane;

public class Zgloszenie implements Serializable {
    private Pojazd pojazd;
    private Date dataOtwarciaZgloszenia;
    private String miejsceOdbioruPojazdu;
    private List<Osoba> kierowcy = new ArrayList<>();
    private Osoba osoba;

    private static List<Zgloszenie> extent = new ArrayList<>();

    /**
     * Konstruktor tworzący obiekt typy Zgloszenie
     * @param pojazd pojazd, który ma być poddany relokacji
     * @param dataOtwarciaZgloszenia data stworzenia zgłoszenia relokacji
     * @param miejsceOdbioruPojazdu miejsce odbioru pojazdu
     * @param kierowcyL lista kierowców, która bierze udział w relokacji (1-2)
     */
    public Zgloszenie(Pojazd pojazd, Date dataOtwarciaZgloszenia, String miejsceOdbioruPojazdu, List<Osoba> kierowcyL) {
        try {
            for (Osoba osoba : kierowcyL) {
                if (!osoba.czyKierowca()) throw new Exception("Ta osoba nie jest kierowcą!");
            }
        }   catch (Exception e) {
            e.printStackTrace();
        }
        this.pojazd = pojazd;
        this.dataOtwarciaZgloszenia = dataOtwarciaZgloszenia;
        this.miejsceOdbioruPojazdu = miejsceOdbioruPojazdu;
        try {
            if (kierowcyL.size() > 2) throw new Exception("Nie można przypisać więcej niż dwóch kierowców");
        }   catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (kierowcyL.size() == 0) throw new Exception("Pusta lista kierowców");
        }   catch (Exception e) {
            e.printStackTrace();
        }

        przypiszPojazd(this.pojazd);

        for(Osoba o : kierowcyL) {
            przypiszKierowce(o);
        }
        extent.add(this);
    }

    /**
     * Metoda reverse-connection
     * pozwalająca na dodanie kierowcy do istniejącej relokacji
     * i uzupełnienie list asocjacyjnych w ramach asocjacji Zgloszenie-Osoba
     * @param kierowca kierowca, który bierze udział w relokacji
     */
    public void przypiszKierowce(Osoba kierowca) {
        try {
            if(!kierowcy.contains(kierowca)) {
                kierowcy.add(kierowca);
                kierowca.dodajZgloszenie(this);
            }

        }   catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda niszcząca asocjację Zgloszenie-Osoba
     * @param osoba kierowca, który ma jendak nie brać udziału w relokacji
     */
    public void usunOsobe(Osoba osoba) {
        if(this.osoba == osoba) {
            this.osoba = null;
            osoba.usunZgloszenie(this);
        }
    }

    /**
     * Metoda reverse-connection
     * pozwalająca na podmianę / dodanie pojazdu w ramach relokacji
     * @param pojazd pojazd, który ma zostać poddany relokacji
     */
    public void przypiszPojazd(Pojazd pojazd)   {
        if(this.pojazd != pojazd)   {
            this.pojazd = pojazd;
            pojazd.przypiszZgloszenie(this);
        }
    }
    
    /**
     * Metoda zwracająca pojazd, którego dotyczy relokacja
     * @return obiekt typu Pojazd
     */
    public Pojazd pobierzPojazd()   {
        return this.pojazd;
    }

    /**
     * Metoda zwracająca listę kierowców biorących udział w relokacji
     * @return lista typu Osoba
     */
    public List<Osoba> pobierzKierowcow()   {
        return this.kierowcy;
    }

    /**
     * Metoda preparująca ciąg znaków w celu
     * poprawnej reprezentacji danych o zgłoszeniu
     * @return ciąg znaków przechowujący informację o zgłoszeniu
     */
    @Override
    public String toString()    {
        try {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            return pojazd.toString() + " ("+ dateFormat.format(dataOtwarciaZgloszenia) + ") ";
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
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
        extent = (ArrayList<Zgloszenie>) stream.readObject();
    }
    public static List<Zgloszenie> getExtent()    {
        return extent;
    }
        /**
     * Metoda zwracająca wszystkie zgłoszenia relokacji
     * @return lista relokacji zapisanych w systemie
     */
    public static List<Zgloszenie> znajdzZgloszenia()   {
        return extent;
     }
         /**
     * Metoda zapisująca dane dotyczące relokacji.
     * Dane pochodzą z klasy services.Gui
     * @param sparowani lista sparowanych pojazdów z kierowcami.
     */
    public static void zapiszRelokacje(List<String> sparowani) {
        for(String  s : sparowani)  {
            String[] informacje = s.split("<-------->");
            String[] kierowcy = informacje[1].split(",");
            List<Osoba> listaKierowcow = new ArrayList<>();
            if(kierowcy.length > 1) {
                listaKierowcow.add(Osoba.znajdzOsobe(kierowcy[0]));
                listaKierowcow.add(Osoba.znajdzOsobe(kierowcy[1]));
            }   else    {
                listaKierowcow.add(Osoba.znajdzOsobe(kierowcy[0]));
            }
            Pojazd pojazd = Pojazd.znajdzPojazd(informacje[0]);
            Zgloszenie zgloszenie = new Zgloszenie(pojazd, new Date(System.currentTimeMillis()), pojazd.getMiejscePostoju(),  listaKierowcow);
            //extent.add(zgloszenie);
            for(Osoba o : listaKierowcow)   {
                Osoba.zaktualizujOsobe(o);
            }
            Pojazd.zaktualizujPojazd(pojazd);
            try {
                Dane.zapiszDane();
            }   catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
