package models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DodatkoweOpcje implements Serializable {
    private String nazwa;
    private double cena;
    private List<Wypozyczenie> wypozyczenia = new ArrayList<>();
    private static List<DodatkoweOpcje> extent = new ArrayList();

    /**
     * Konstruktor tworzący obiekt typu DodatkoweOpcje
     * @param nazwa nazwa dodatkowej opcji dodawanej do wypożyczenia
     * @param cena cena dodatowej opcji
     */
    public DodatkoweOpcje(String nazwa, double cena)    {
        this.nazwa = nazwa;
        this.cena = cena;
        extent.add(this);
    }

    /**
     * Metoda reverse-connection używana do uzupełnienia informacji o asocjacji
     * dla wypożyczenia
     * @param wypozyczenie - wypożyczenie, w którym wymagane jest dołączenie dodatkowej opcji.
     */
    public void przypiszWypozyczenie(Wypozyczenie wypozyczenie) {
        if(!wypozyczenia.contains(wypozyczenie))   {
            wypozyczenia.add(wypozyczenie);
            wypozyczenie.ustawDodatkowaOpcje(this);
        }
    }

    /**
     * Metoda zwracająca wszystkie wypożyczenia, w których
     * potrzeba była dołączyć dodatkową opcję
     * @return lista wszystkich wypożyczeń
     */
    public List<Wypozyczenie> getWypozyczenia()   {
        return wypozyczenia;
    }

    /**
     * Metoda odpoweidzialna za usuwanie z listy asocjacyjnej informacji o wypożyczeniu
     * @param wypozyczenie obiekt Wypożyczenie
     */
    public void usunWypozyczenie(Wypozyczenie wypozyczenie)  {
        wypozyczenia.remove(wypozyczenie);
    }

    /**
     * Metoda preparująca ciag znaków w celu
     * poprawnej reprezentacji danych o dodatkwoych opcjach
     * @return ciąg znaków przechowujący informacje o dodatkowej opcji
     */
    @Override
    public String toString()    {
        return nazwa + " ("+cena+")/dz";
    }

    /**
     * Metoda sprawdzająca cenę dodatkowej opcji
     * @return cena dodatkowej opcji
     */
    public double getCena() {
        return cena;
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
        extent = (ArrayList<DodatkoweOpcje>) stream.readObject();
    }
    public static List<DodatkoweOpcje> getExtent()    {
        return extent;
    }
        /**
     * Metoda odpowiedzialna za zaktualizowanie stanu obiektu w ekstensji
     * @param dodOpcje lista obiektów do zaktualizowania
     */
    public static void zaktualizujDodatkoweOpcje(List<DodatkoweOpcje> dodOpcje) {
        int iterator = 0;
        for (DodatkoweOpcje dodatkowaOpcja : dodOpcje) {
            for (DodatkoweOpcje dodatkowaOpcjaEkstensji : extent) {
                if (dodatkowaOpcja.toString().equals(dodatkowaOpcjaEkstensji.toString())) {
                    extent.set(iterator, dodatkowaOpcja);
                }
            }
            iterator++;
        }
    }

}

