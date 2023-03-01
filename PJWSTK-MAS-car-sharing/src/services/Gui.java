package services;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import models.DodatkoweOpcje;
import models.PracownikMyciePojazdu;
import models.Wypozyczenie;
import models.Kategoria;
import models.Osoba;
import models.Pojazd;
import models.Zgloszenie;
import models.Zwrot;
import models.PracownikMyciePojazdu;

import java.awt.event.*;
import javax.swing.event.*;
import java.util.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Gui {
    private List<String> listaWybranychPojazdowString;
    private List<String> sparowani;

    //zmienne potrzebne do obsłużenia listy umytych pojazdów
    private JScrollPane ostatni;
    private boolean pierwszy;

    public Gui()   {
        this.ostatni = new JScrollPane();
        this.pierwszy = true;
    }

    /**
     * Metoda uruchamiająca sekwencję okienek
     * odpowiedzialnych za funkcjonalność jaką jest stworzenie
     * relokacji pojazdu
     */
    public void nowaRelokacja() {
        pokazOknoGlowneRelokacji();
    }
    /**
     * Metoda inicjująca główne menu
     */
    public void menuGlowne()    {
        pokazMenuGlowne();
    }

    /**
     * Metoda inicjująca okno nowej osoby
     */
    public void nowaOsoba() {
        stworzOsobe();
    }

    /**
     * Metoda inicjująca okno nowego klienta
     */
    public void nowyKlient()    {
        stworzKlienta();
    }

    /**
     * Metoda inicjująca okno wszystkich osób
     */
    public void pokazWszystkieOsoby()   {
        wszystkieOsoby();
    }
    
    /**
     * Metoda inicjująca okno wyświetlania premii
     */
    public void obliczPremie()  {
        obliczaniePremii();
    }

    /**
     * Metoda inicjująca okno nowego pracownika
     */
    public void nowyPracownik() {
        stworzPracownika();
    }
    /**
     * Metoda inicjująca okno dla zmiany uprawnień
     */
    public void zmianaUprawnien()   {
        zmienUprawnienia();
    }
    /**
     * Metoda inicjująca okno tworzenia nowej kategorii
     */
    public void utworzKategorie()   {
        stworzKategorie();
    }
    /**
     * Metoda inicjująca okno tworzenia nowego pojazdu
     */
    public void utworzPojazd()  {
        stworzPojazd();
    }
    /**
     * Metoda inicjująca okno mycia pojazdu
     */
    public void utworzMyciePojazdu()    {
        myciePojazdu();
    }
    /**
     * Metoda inicująca okno zmiany kategorii pojazdu
     */
    public void zmienKategoriePojazdu() {
        zmianaKategoriiPojazdu();
    }
    /**
     * Metoda inicująca okno nowego dodatkowego wyposażenia
     */
    public void nowaOpcja() {
        stworzDodatkoweWyposazenie();
    }
    /**
     * Metoda inicjująca okno nowej rezerwacji
     */
    public void nowaRezerwacja()    {
        stworzRezerwacje();
    }
    /**
     * Metoda inicjująca okno niezwróconych pojazdów
     */
    public void niezwroconePojazdy()    {
        pokazNiezwroconePojazdy();
    }
    /**
     * Metoda inicjująca okno zwracania pojazdu
     */
    public void zwrocPojazd()   {
        pokazZwracaniePojazdu();
    }
    /**
     * Okno wyświetlające podsumowanie zwrotu pojazdu
     * @param wybranyPojazd obiekt pojazdu, który został wypożyczony
     * @param wybraneWypozyczenie obiekt wypożyczenia, które jest zwracane
     * @param dataZwrotu data zwrotu w formie String (dd/mm/yyyy)
     * @param kwotaKary kwota kary - opcjonalne
     * @param opisPojazdu opis pojazdu - jeżeli doszły dodatkowe uszkodzenia, należy wpisać taką informację
     * @param stanLicznika przebieg pojazdu podczas zwracania
     * @param kara czy klient powinien zostać ukarany (tak/nie) - boolean
     * @param miejsceZwrotu miejsce zwrotu pojazdu
     * @throws Exception wyjątek odnoszacy się do tworzenia części bez całości (zwrot -> wypożyczenie)
     */
    private void wyswietlPodsumowanieZwrotu(models.Pojazd wybranyPojazd, models.Wypozyczenie wybraneWypozyczenie, String dataZwrotu, String kwotaKary, String opisPojazdu, String stanLicznika, boolean kara, String miejsceZwrotu) throws Exception{
        //Szablon
        JFrame niezwroconeFrame = new JFrame("Zwrot pojazdu");
        JLabel naglowek = new JLabel("Tanie wypożyczanie - zwrot");
        JLabel logo = new JLabel(new ImageIcon("images\\tanie_wypozyczanie_logo.png"));
        JButton przyciskWroc = new JButton("Wróć");
        JButton przyciskZatwierdz = new JButton("Zwróć");

        przyciskWroc.setBounds(100, 600, 200, 50);
        przyciskZatwierdz.setBounds(350, 600, 200, 50);

        naglowek.setBounds(50, 25, 500, 100);
        naglowek.setFont(new Font("SansSerif", Font.BOLD, 25));

        logo.setBounds(650, 0, 200, 130);

        niezwroconeFrame.setBounds(100, 100, 900, 700);
        niezwroconeFrame.setLayout(null);
        niezwroconeFrame.setAlwaysOnTop(true);
        niezwroconeFrame.setResizable(false);
        //Koniec szablonu
        String[] daneZwrotu = new String[11];
        String pattern = "dd/MM/yyyy";
        DateFormat df = new SimpleDateFormat(pattern);
        String dataWypozyczenia = df.format(wybraneWypozyczenie.getDataWypozyczenia());
        String dataZwrotuO = df.format(wybraneWypozyczenie.getDataZwrotu());
        daneZwrotu[0] = "Klient:";
        daneZwrotu[1] = wybraneWypozyczenie.getOsoba().getImie() + " " + wybraneWypozyczenie.getOsoba().getNazwisko();
        daneZwrotu[2] = "Pojazd:";
        daneZwrotu[3] = wybranyPojazd.getMarka() + " " + wybranyPojazd.getModel() + " " + "(" + wybranyPojazd.getRejestracja() + ")";
        daneZwrotu[4] = "Wypożyczenie: ";
        daneZwrotu[5] = "Data wypożyczenia : " + dataWypozyczenia;
        daneZwrotu[6] = "Data zwrotu : " + dataZwrotuO;
        daneZwrotu[7] = "Faktyczna data zwrotu : " + dataZwrotu;
        daneZwrotu[8] = "Przebieg : " + stanLicznika;
        daneZwrotu[9] = "Kwota kary : " + kwotaKary;
        daneZwrotu[10] = "Opis uszkodzeń : " + opisPojazdu;
        JList danePodsumowania = new JList(daneZwrotu);
        danePodsumowania.setBounds(100, 200, 700, 400);
        JScrollPane scrollPane = new JScrollPane(danePodsumowania);
        scrollPane.getViewport().setView(danePodsumowania);
        scrollPane.setBounds(100, 200, 700, 400);

        przyciskWroc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                niezwroconeFrame.setVisible(false);
                menuGlowne();
            }
        });

        przyciskZatwierdz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    if (kara) {
                        Zwrot.stworzZwrot(Wypozyczenie.getExtent().get(Wypozyczenie.getExtent().indexOf(wybraneWypozyczenie)), new SimpleDateFormat("dd/MM/yyyy").parse(dataZwrotu), miejsceZwrotu, Integer.parseInt(stanLicznika), opisPojazdu, Double.parseDouble(kwotaKary));
                        Wypozyczenie.getExtent().get(Wypozyczenie.getExtent().indexOf(wybraneWypozyczenie)).getPojazd().zakonczWypozyczenie();
                        Wypozyczenie.getExtent().get(Wypozyczenie.getExtent().indexOf(wybraneWypozyczenie)).getPojazd().aktualizujPrzebieg(stanLicznika);
                    } else {
                        Zwrot.stworzZwrot(Wypozyczenie.getExtent().get(Wypozyczenie.getExtent().indexOf(wybraneWypozyczenie)), new SimpleDateFormat("dd/MM/yyyy").parse(dataZwrotu), miejsceZwrotu, Integer.parseInt(stanLicznika), opisPojazdu);
                        Wypozyczenie.getExtent().get(Wypozyczenie.getExtent().indexOf(wybraneWypozyczenie)).getPojazd().aktualizujPrzebieg(stanLicznika);
                        Wypozyczenie.getExtent().get(Wypozyczenie.getExtent().indexOf(wybraneWypozyczenie)).getPojazd().zakonczWypozyczenie();
                    }
                    Wypozyczenie.zaktualizujWypozyczenie(Wypozyczenie.getExtent().get(Wypozyczenie.getExtent().indexOf(wybraneWypozyczenie)));
                    Pojazd.zaktualizujPojazd(Wypozyczenie.getExtent().get(Wypozyczenie.getExtent().indexOf(wybraneWypozyczenie)).getPojazd());
                    Dane.zapiszDane();
                    niezwroconeFrame.setVisible(false);

                    menuGlowne();
                }   catch(Exception e1)  {
                    e1.printStackTrace();
                }
            }
        });

        niezwroconeFrame.getContentPane().add(logo);
        niezwroconeFrame.getContentPane().add(naglowek);
        niezwroconeFrame.getContentPane().add(przyciskWroc);
        niezwroconeFrame.getContentPane().add(przyciskZatwierdz);
        niezwroconeFrame.getContentPane().add(scrollPane);
        niezwroconeFrame.setVisible(true);
    }

    /**
     * Okno wyświetlające interfejs zwrotu wypożyczonego pojazdu
     */
    private void pokazZwracaniePojazdu()    {
        //Szablon
        JFrame niezwroconeFrame = new JFrame("Zwrot pojazdu");
        JLabel naglowek = new JLabel("Tanie wypożyczanie - zwrot");
        JLabel logo = new JLabel(new ImageIcon("images\\tanie_wypozyczanie_logo.png"));
        JButton przyciskWroc = new JButton("Wróć");
        JButton przyciskZatwierdz = new JButton("OK");

        przyciskWroc.setBounds(100, 600, 200, 50);
        przyciskZatwierdz.setBounds(350, 600, 200, 50);

        naglowek.setBounds(50, 25, 500, 100);
        naglowek.setFont(new Font("SansSerif", Font.BOLD, 25));

        logo.setBounds(650, 0, 200, 130);

        niezwroconeFrame.setBounds(100, 100, 900, 700);
        niezwroconeFrame.setLayout(null);
        niezwroconeFrame.setAlwaysOnTop(true);
        niezwroconeFrame.setResizable(false);
        //Koniec szablonu
        List<models.Wypozyczenie> niezwroconeWypozyczenia = new ArrayList<>();
        for(models.Wypozyczenie wyp : Wypozyczenie.getExtent())   {
            if(!wyp.czyZwrocony())  {
                niezwroconeWypozyczenia.add(wyp);
            }
        }
        String[] tablicaNiezwroconychAut = new String[niezwroconeWypozyczenia.size()];
        models.Pojazd[] tablicaPojazdow = new models.Pojazd[niezwroconeWypozyczenia.size()];
        models.Wypozyczenie[] tablicaWypozyczen = new models.Wypozyczenie[niezwroconeWypozyczenia.size()];
        int iterator = 0;
        for(models.Wypozyczenie wyp : niezwroconeWypozyczenia)  {
            StringBuilder sb = new StringBuilder();
            String pattern = "dd/MM/yyyy";
            DateFormat df = new SimpleDateFormat(pattern);
            String dataWypozyczenia = df.format(wyp.getDataWypozyczenia());
            String dataZwrotu = df.format(wyp.getDataZwrotu());
            sb.append("Pojazd: ").append(wyp.getPojazd().getMarka()).append(" ").append(wyp.getPojazd().getModel()).append(" <").append(wyp.getPojazd().getRejestracja())
            .append(">, Klient: ").append(wyp.getOsoba().getImie() + " ").append(wyp.getOsoba().getNazwisko()).append(", Data wypożyczenia: ").append(dataWypozyczenia)
            .append(", Data zwrotu: ").append(dataZwrotu);
            tablicaNiezwroconychAut[iterator] = sb.toString();
            tablicaPojazdow[iterator] = wyp.getPojazd();
            tablicaWypozyczen[iterator] = wyp;
            iterator++;
        }
        JList listaPojazdow = new JList(tablicaNiezwroconychAut);
        JLabel pojazdyInfo = new JLabel("Niezwrócone pojazdy - kliknij na pojazd i kliknij zatwierdź");
        JScrollPane listaPojazdowScroll = new JScrollPane(listaPojazdow);
        listaPojazdowScroll.getViewport().setView(listaPojazdow);

        JTextArea dataZwrotu = new JTextArea("Data zwrotu (dd/mm/rrrr)");
        JTextArea kwotaKary = new JTextArea("Kwota kary - nie ruszaj, jeśli brak kary");
        JTextArea opisPojazdu = new JTextArea("Opis nowych uszkodzeń - nie ruszaj, jeśli brak");
        JTextArea stanLicznika = new JTextArea("Stan licznika");
        JTextArea miejsceZwrotuArea = new JTextArea("Miejsce zwrotu");

        listaPojazdow.setBounds(100, 150, 300, 400);
        listaPojazdowScroll.setBounds(100, 150, 300, 400);
        pojazdyInfo.setBounds(100, 90, 400, 50);
        dataZwrotu.setBounds(450, 150, 400, 50);
        kwotaKary.setBounds(450, 210, 400, 50);
        opisPojazdu.setBounds(450, 270, 400, 50);
        stanLicznika.setBounds(450, 330, 400, 50);
        miejsceZwrotuArea.setBounds(450, 390, 400, 50);

        przyciskWroc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                niezwroconeFrame.setVisible(false);
                menuGlowne();
            }
        });
        przyciskZatwierdz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int wybranyElement = listaPojazdow.getSelectedIndex();
                models.Pojazd wybranyPojazd = tablicaPojazdow[wybranyElement];
                models.Wypozyczenie wybraneWypozyczenie = tablicaWypozyczen[wybranyElement];
                String kara = "0";
                boolean karaFlaga = false;
                if(!(kwotaKary.getText().strip()).equals(("Kwota kary - nie ruszaj, jeśli brak kary").strip()))    {
                    kara = kwotaKary.getText();
                    karaFlaga = true;
                }
                String opis = "BRAK";
                if(!(opisPojazdu.getText().strip()).equals(("Opis nowych uszkodzeń - nie ruszaj, jeśli brak").strip()))    {
                    opis = opisPojazdu.getText();
                }
                niezwroconeFrame.setVisible(false);
                try {
                wyswietlPodsumowanieZwrotu(wybranyPojazd, wybraneWypozyczenie, dataZwrotu.getText(), kara, opis, stanLicznika.getText(), karaFlaga, miejsceZwrotuArea.getText());
                }   catch(Exception e1) {
                    e1.printStackTrace();
                }
            }
        });


        niezwroconeFrame.getContentPane().add(logo);
        niezwroconeFrame.getContentPane().add(naglowek);
        niezwroconeFrame.getContentPane().add(przyciskZatwierdz);
        niezwroconeFrame.getContentPane().add(przyciskWroc);
        niezwroconeFrame.getContentPane().add(pojazdyInfo);
        niezwroconeFrame.getContentPane().add(listaPojazdowScroll);
        niezwroconeFrame.getContentPane().add(dataZwrotu);
        niezwroconeFrame.getContentPane().add(kwotaKary);
        niezwroconeFrame.getContentPane().add(opisPojazdu);
        niezwroconeFrame.getContentPane().add(stanLicznika);
        niezwroconeFrame.getContentPane().add(miejsceZwrotuArea);

        niezwroconeFrame.setVisible(true);


    }
    /**
     * Metoda wyświetlająca okno niezwróconych pojazdów
     */
    private void pokazNiezwroconePojazdy()   {
        //Szablon
        JFrame niezwroconeFrame = new JFrame("Niezwrócone pojazdy");
        JLabel naglowek = new JLabel("Tanie wypożyczanie - niezwrócone pojazdy");
        JLabel logo = new JLabel(new ImageIcon("images\\tanie_wypozyczanie_logo.png"));
        JButton przyciskWroc = new JButton("Wróć");
        JButton przyciskZatwierdz = new JButton("OK");

        przyciskWroc.setBounds(360, 600, 200, 50);
        przyciskZatwierdz.setBounds(350, 600, 200, 50);

        naglowek.setBounds(50, 25, 500, 100);
        naglowek.setFont(new Font("SansSerif", Font.BOLD, 25));

        logo.setBounds(650, 0, 200, 130);

        niezwroconeFrame.setBounds(100, 100, 900, 700);
        niezwroconeFrame.setLayout(null);
        niezwroconeFrame.setAlwaysOnTop(true);
        niezwroconeFrame.setResizable(false);
        //Koniec szablonu
        List<models.Wypozyczenie> niezwroconeWypozyczenia = new ArrayList<>();
        for(models.Wypozyczenie wyp : Wypozyczenie.getExtent())   {
            if(!wyp.czyZwrocony())  {
                niezwroconeWypozyczenia.add(wyp);
            }
        }
        String[] tablicaNiezwroconychAut = new String[niezwroconeWypozyczenia.size()];
        int iterator = 0;
        for(models.Wypozyczenie wyp : niezwroconeWypozyczenia)  {
            StringBuilder sb = new StringBuilder();
            String pattern = "dd/MM/yyyy";
            DateFormat df = new SimpleDateFormat(pattern);
            String dataWypozyczenia = df.format(wyp.getDataWypozyczenia());
            String dataZwrotu = df.format(wyp.getDataZwrotu());
            sb.append("Pojazd: ").append(wyp.getPojazd().getMarka()).append(" ").append(wyp.getPojazd().getModel()).append(" (").append(wyp.getPojazd().getRejestracja())
            .append(") Klient: ").append(wyp.getOsoba().getImie() + " ").append(wyp.getOsoba().getNazwisko()).append(" Data wypożyczenia: ").append(dataWypozyczenia)
            .append(" Data zwrotu: ").append(dataZwrotu);
            tablicaNiezwroconychAut[iterator] = sb.toString();
            iterator++;
        }
        JList listaPojazdow = new JList(tablicaNiezwroconychAut);
        JLabel pojazdyInfo = new JLabel("Niezwrócone pojazdy");
        JScrollPane listaPojazdowScroll = new JScrollPane(listaPojazdow);
        listaPojazdowScroll.getViewport().setView(listaPojazdow);

        listaPojazdow.setBounds(100, 150, 700, 400);
        listaPojazdowScroll.setBounds(100, 150, 700, 400);
        pojazdyInfo.setBounds(100, 90, 200, 50);

        przyciskZatwierdz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                niezwroconeFrame.setVisible(false);
                menuGlowne();
            }
        });

        niezwroconeFrame.getContentPane().add(logo);
        niezwroconeFrame.getContentPane().add(naglowek);
        niezwroconeFrame.getContentPane().add(przyciskZatwierdz);
        niezwroconeFrame.getContentPane().add(pojazdyInfo);
        niezwroconeFrame.getContentPane().add(listaPojazdowScroll);
        niezwroconeFrame.setVisible(true);
    }
    /**
     * Metoda wyświetlająca podsumowanie rezerwacji
     * @param koszt całkowity koszt rezerwacji
     * @param wybraneOpcje wybrane dodatkowe opcje 
     * @param klientObj obiekt klienta rezerwującego
     * @param wybranyPojazd obiekt pojazdu rezerwowanego
     * @param odbior miejsce odbioru auta (String)
     * @param zwrot miejsce zwrotu auta (String)
     * @param rozpoczecie data rozpoczęcia rezerwacji (String)
     * @param zakonczenie data zakończenia rezerwacji (String)
     */
    private void wyswietlPodsumowanieRezerwacji(double koszt, List<models.DodatkoweOpcje> wybraneOpcje, models.Osoba klientObj, models.Pojazd wybranyPojazd, String odbior, String zwrot, String rozpoczecie, String zakonczenie)    {
        //Szablon
        JFrame nowaRezerwacja = new JFrame("Nowe wyposażenie");
        JLabel naglowek = new JLabel("Tanie wypożyczanie - kategoria");
        JLabel logo = new JLabel(new ImageIcon("images\\tanie_wypozyczanie_logo.png"));
        JButton przyciskWroc = new JButton("Wróć");
        JButton przyciskZatwierdz = new JButton("Zatwierdź");

        przyciskWroc.setBounds(190, 600, 200, 50);
        przyciskZatwierdz.setBounds(350, 600, 200, 50);

        naglowek.setBounds(50, 25, 500, 100);
        naglowek.setFont(new Font("SansSerif", Font.BOLD, 25));

        logo.setBounds(650, 0, 200, 130);

        nowaRezerwacja.setBounds(100, 100, 900, 700);
        nowaRezerwacja.setLayout(null);
        nowaRezerwacja.setAlwaysOnTop(true);
        nowaRezerwacja.setResizable(false);
        //Koniec szablonu
        JLabel podsumowanieTekst = new JLabel("Podsumowanie rezerwacji");
        List<String> daneRezerwacji = new ArrayList<>();
        daneRezerwacji.add("Koszt : " + Double.toString(koszt));
        for(models.DodatkoweOpcje op : wybraneOpcje)    {
            daneRezerwacji.add("Dodatkowa opcja: " + op.toString());
        }
        daneRezerwacji.add("Klient : " + klientObj.getImie() + " " + klientObj.getNazwisko());
        daneRezerwacji.add("Pojazd : " + wybranyPojazd.getMarka() + " " + wybranyPojazd.getModel());
        daneRezerwacji.add("Miejsce odbioru : " + odbior);
        daneRezerwacji.add("MiejsceZwrotu : " + zwrot);
        daneRezerwacji.add("Data rozpoczęcia : " + rozpoczecie);
        daneRezerwacji.add("Data zakończenia : " + zakonczenie);
        String[] daneRezerwacjiTab = new String[daneRezerwacji.size()];
        daneRezerwacji.toArray(daneRezerwacjiTab);
        JList daneRezerwacjiList = new JList(daneRezerwacjiTab);
        podsumowanieTekst.setBounds(400, 90, 200, 50);
        daneRezerwacjiList.setBounds(200, 150, 500, 500);

        przyciskZatwierdz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nowaRezerwacja.setVisible(false);
                menuGlowne();
            }
        });

        nowaRezerwacja.getContentPane().add(przyciskZatwierdz);
        nowaRezerwacja.getContentPane().add(podsumowanieTekst);
        nowaRezerwacja.getContentPane().add(daneRezerwacjiList);
        nowaRezerwacja.getContentPane().add(logo);
        nowaRezerwacja.getContentPane().add(naglowek);
        nowaRezerwacja.setVisible(true);
    }
    /**
     * Metoda wyświetlająca drugi krok tworzenia rezerwacji
     */
    private void wyswietlOknoPojazdowRezerwacji(String klientDane, String odbior, String zwrot, String rozpoczecie, String zakonczenie)    {
        //Szablon
        JFrame nowaRezerwacja = new JFrame("Nowe wyposażenie");
        JLabel naglowek = new JLabel("Tanie wypożyczanie - kategoria");
        JLabel logo = new JLabel(new ImageIcon("images\\tanie_wypozyczanie_logo.png"));
        JButton przyciskWroc = new JButton("Wróć");
        JButton przyciskZatwierdz = new JButton("Zatwierdź");

        przyciskWroc.setBounds(190, 600, 200, 50);
        przyciskZatwierdz.setBounds(420, 600, 200, 50);

        naglowek.setBounds(50, 25, 500, 100);
        naglowek.setFont(new Font("SansSerif", Font.BOLD, 25));

        logo.setBounds(650, 0, 200, 130);

        nowaRezerwacja.setBounds(100, 100, 900, 700);
        nowaRezerwacja.setLayout(null);
        nowaRezerwacja.setAlwaysOnTop(true);
        nowaRezerwacja.setResizable(false);
        //Koniec szablonu
        JLabel dostepnePojazdyLabel = new JLabel("Dostępne pojazdy");
        dostepnePojazdyLabel.setBounds(50, 90, 200, 50);
        List<models.Pojazd> dostepnePojazdy = Pojazd.sprawdzDostepnePojazdy(rozpoczecie, zakonczenie);
        String[] dostepnePojazdyTablica = new String[dostepnePojazdy.size()];
        int iterator = 0;
        for(models.Pojazd p : dostepnePojazdy)  {
            StringBuilder sb = new StringBuilder();
            double[] ceny = p.getKategoria().getCena();
            double cena = 0.0;
            try {
                Date pojazdDataOdDate = new SimpleDateFormat("dd/MM/yyyy").parse(rozpoczecie);
                Date pojazdDataDoDate = new SimpleDateFormat("dd/MM/yyyy").parse(zakonczenie);
                long czasPomiedzy = pojazdDataDoDate.getTime() - pojazdDataOdDate.getTime();
                long dniPomiedzy = (czasPomiedzy / (1000*60*60*24));
                if(dniPomiedzy < 2) {
                    cena = ceny[0];
                }
                if(dniPomiedzy > 1 && dniPomiedzy <=7 )   {
                    cena = ceny[1];
                }
                if(dniPomiedzy > 7) {
                    cena = ceny[2];
                }
            }   catch(Exception e1) {
                e1.printStackTrace();
            }
            sb.append(cena).append("zl/dz ").append(p.getMarka()).append(" ").append(p.getModel()).append(" <").append(p.getRejestracja()).append(">");
            dostepnePojazdyTablica[iterator] = sb.toString();
            iterator++;
        }
        JList dostepnePojazdyList = new JList(dostepnePojazdyTablica);
        JScrollPane dostepnePojazdyScroll = new JScrollPane(dostepnePojazdyList);
        dostepnePojazdyScroll.getViewport().setView(dostepnePojazdyList);
        dostepnePojazdyList.setBounds(50, 150, 300, 400);
        dostepnePojazdyScroll.setBounds(50, 150, 300, 400);
        JLabel dodatkoweWyposazenieLabel = new JLabel("Dodatkowe wyposażenie");
        dodatkoweWyposazenieLabel.setBounds(400, 90, 200, 50);
        List<models.DodatkoweOpcje> dostepneDodatkoweOpcje = new ArrayList<>();

        for(models.DodatkoweOpcje dodatkowaOpcja : DodatkoweOpcje.getExtent()) {
            boolean overlap = false;
            if (dodatkowaOpcja.getWypozyczenia().size() == 0){
                dostepneDodatkoweOpcje.add(dodatkowaOpcja);
            }
            else {
                for (models.Wypozyczenie wyp : dodatkowaOpcja.getWypozyczenia()) {
                    Date dataStartu = null;
                    Date dataKonca = null;
                    try {
                    dataStartu = new SimpleDateFormat("dd/MM/yyyy").parse(rozpoczecie);
                    dataKonca = new SimpleDateFormat("dd/MM/yyyy").parse(zakonczenie);
                    } catch(Exception e1)   {
                        e1.printStackTrace();
                    }
                    Date dataWypozyczeniaStart = wyp.getDataWypozyczenia();
                    Date dataWypozyczeniaKoniec = wyp.getDataZwrotu();
                    if ((dataStartu.getTime() <= dataWypozyczeniaKoniec.getTime()) && (dataWypozyczeniaStart.getTime() <= dataKonca.getTime())) {
                        overlap = true;
                    }
                }
                if(!overlap) dostepneDodatkoweOpcje.add(dodatkowaOpcja);
            }
        }
        models.DodatkoweOpcje[] dodatkoweOpcjeTablica = new models.DodatkoweOpcje[dostepneDodatkoweOpcje.size()];
        iterator = 0;
        for(models.DodatkoweOpcje op : dostepneDodatkoweOpcje)    {
            dodatkoweOpcjeTablica[iterator] = op;
            iterator++;
        }
        JList dostepneOpcjeWyposazeniaList = new JList<models.DodatkoweOpcje>(dodatkoweOpcjeTablica);
        JScrollPane dostepneOpcjeScroll = new JScrollPane(dostepneOpcjeWyposazeniaList);
        dostepneOpcjeScroll.getViewport().setView(dostepneOpcjeWyposazeniaList);

        dostepneOpcjeWyposazeniaList.setBounds(360, 150, 300, 400);
        dostepneOpcjeScroll.setBounds(360, 150, 300, 400);

        dostepneOpcjeWyposazeniaList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);//wroctutaj
        //getSelectedValuesList

        przyciskWroc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nowaRezerwacja.setVisible(false);
                menuGlowne();
            }
        });

        przyciskZatwierdz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] splittedSamochod = dostepnePojazdyList.getSelectedValue().toString().split("<");
                String rejestracja = splittedSamochod[1];
                rejestracja = rejestracja.replace("<", "");
                rejestracja = rejestracja.replace(">", "");
                models.Pojazd wybranyPojazd = Pojazd.pobierzPojazdPoRejestracji(rejestracja);
                List<models.DodatkoweOpcje> wybraneOpcje = dostepneOpcjeWyposazeniaList.getSelectedValuesList();
                models.Osoba klientObj = null;
                for(models.Osoba o : Osoba.getExtent())   {
                    if(o.czyKlient())   {
                        if(o.podstawoweDane().equals(klientDane))   {
                            klientObj = o;
                        }
                    }
                }
                if(wybraneOpcje.size() <= 3)    {
                    try {
                        models.Wypozyczenie wypozyczenie = new models.Wypozyczenie(wybraneOpcje, new SimpleDateFormat("dd/MM/yyyy").parse(rozpoczecie), new SimpleDateFormat("dd/MM/yyyy").parse(zakonczenie),
                        odbior, zwrot, wybranyPojazd.getStanLicznika(), wybranyPojazd, klientObj);
                        for(models.DodatkoweOpcje dodatkowaOpcja : wybraneOpcje)  {
                            wypozyczenie.ustawDodatkowaOpcje(dodatkowaOpcja);
                        }
                        DodatkoweOpcje.zaktualizujDodatkoweOpcje(wybraneOpcje);
                        Osoba.zaktualizujOsobe(klientObj);
                        Pojazd.zaktualizujPojazd(wybranyPojazd);
                        Wypozyczenie.getExtent().remove(wypozyczenie);
                        Wypozyczenie.getExtent().add(wypozyczenie);
                        Dane.zapiszDane();
                        nowaRezerwacja.setVisible(false);
                        wyswietlPodsumowanieRezerwacji(wypozyczenie.obliczKoszt(), wybraneOpcje, klientObj, wybranyPojazd, odbior, zwrot, rozpoczecie, zakonczenie);

                    }   catch(Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });



        nowaRezerwacja.getContentPane().add(naglowek);
        nowaRezerwacja.getContentPane().add(logo);
        nowaRezerwacja.getContentPane().add(przyciskWroc);
        nowaRezerwacja.getContentPane().add(przyciskZatwierdz);
        nowaRezerwacja.getContentPane().add(dostepnePojazdyLabel);
        nowaRezerwacja.getContentPane().add(dostepnePojazdyScroll);
        nowaRezerwacja.getContentPane().add(dodatkoweWyposazenieLabel);
        nowaRezerwacja.getContentPane().add(dostepneOpcjeScroll);

        nowaRezerwacja.setVisible(true);//wroctutaj
    }
    /**
     * Metoda wyświetlająca okno tworzenia rezerwacji
     */
    private void stworzRezerwacje() {
        //Szablon
        JFrame nowaRezerwacja = new JFrame("Nowe wyposażenie");
        JLabel naglowek = new JLabel("Tanie wypożyczanie - kategoria");
        JLabel logo = new JLabel(new ImageIcon("images\\tanie_wypozyczanie_logo.png"));
        JButton przyciskWroc = new JButton("Wróć");
        JButton przyciskZatwierdz = new JButton("Dalej");

        przyciskWroc.setBounds(190, 600, 200, 50);
        przyciskZatwierdz.setBounds(420, 600, 200, 50);

        naglowek.setBounds(50, 25, 500, 100);
        naglowek.setFont(new Font("SansSerif", Font.BOLD, 25));

        logo.setBounds(650, 0, 200, 130);

        nowaRezerwacja.setBounds(100, 100, 900, 700);
        nowaRezerwacja.setLayout(null);
        nowaRezerwacja.setAlwaysOnTop(true);
        nowaRezerwacja.setResizable(false);
        //Koniec szablonu
        JLabel osobaWypozyczajacaLabel = new JLabel("Osoba wypożyczająca");
        JLabel daneWypozyczeniaLabel = new JLabel("Dane wypożyczenia");
        int iterator = 0;
        for(models.Osoba klient : Osoba.getExtent())  {
            if(klient.czyKlient())  {
                iterator++;
            }
        }
        String[] klienciTablica = new String[iterator];
        iterator = 0;
        for(models.Osoba klient : Osoba.getExtent())  {
            if(klient.czyKlient())  {
                klienciTablica[iterator] = klient.podstawoweDane();
                iterator++;
            }
        }
        JList listaKlientow = new JList(klienciTablica);
        JScrollPane klienciScroll = new JScrollPane(listaKlientow);
        klienciScroll.getViewport().setView(listaKlientow);

        listaKlientow.setBounds(50, 150, 350, 200);
        klienciScroll.setBounds(50, 150, 350, 300);
        osobaWypozyczajacaLabel.setBounds(50, 90, 200, 50);
        daneWypozyczeniaLabel.setBounds(400, 90, 200, 50);

        JTextArea miejsceOdbioru = new JTextArea("Miejsce odbioru pojazdu");
        JTextArea miejsceZwrotu = new JTextArea("Miejsce zwrotu pojazdu");
        JTextArea dataRozpoczecia = new JTextArea("Data rozpoczęcia (DD/MM/YYYY)");
        JTextArea dataZakonczenia = new JTextArea("Data zakończenia (DD/MM/YYYY)");

        miejsceOdbioru.setBounds(400, 150, 200, 50);
        miejsceZwrotu.setBounds(400, 210, 200, 50);
        dataRozpoczecia.setBounds(400, 270, 200, 50);
        dataZakonczenia.setBounds(400, 330, 200, 50);
        
        miejsceOdbioru.setBorder(BorderFactory.createLineBorder(Color.black));
        miejsceZwrotu.setBorder(BorderFactory.createLineBorder(Color.black));
        dataRozpoczecia.setBorder(BorderFactory.createLineBorder(Color.black));
        dataZakonczenia.setBorder(BorderFactory.createLineBorder(Color.black));

        przyciskWroc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nowaRezerwacja.setVisible(false);
                menuGlowne();
            }
        });

        przyciskZatwierdz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String klientDane = listaKlientow.getSelectedValue().toString();
                String odbior = miejsceOdbioru.getText();
                String zwrot = miejsceZwrotu.getText();
                String rozpoczecie = dataRozpoczecia.getText();
                String zakonczenie = dataZakonczenia.getText();
                if(klientDane != "" && odbior != "" && zwrot != "" && rozpoczecie != "" && zakonczenie != "")   {
                    if(Pojazd.sprawdzDostepnePojazdy(rozpoczecie, zakonczenie).size() != 0){
                        nowaRezerwacja.setVisible(false);
                        wyswietlOknoPojazdowRezerwacji(klientDane, odbior, zwrot, rozpoczecie, zakonczenie);
                    
                    }
                    else daneWypozyczeniaLabel.setText("BRAK SAMOCHODÓW W TERMINIE");
                }
            }
        });

        nowaRezerwacja.getContentPane().add(przyciskWroc);
        nowaRezerwacja.getContentPane().add(przyciskZatwierdz);
        nowaRezerwacja.getContentPane().add(klienciScroll);
        nowaRezerwacja.getContentPane().add(logo);
        nowaRezerwacja.getContentPane().add(naglowek);
        nowaRezerwacja.getContentPane().add(osobaWypozyczajacaLabel);
        nowaRezerwacja.getContentPane().add(miejsceOdbioru);
        nowaRezerwacja.getContentPane().add(miejsceZwrotu);
        nowaRezerwacja.getContentPane().add(dataRozpoczecia);
        nowaRezerwacja.getContentPane().add(dataZakonczenia);
        nowaRezerwacja.getContentPane().add(daneWypozyczeniaLabel);
        nowaRezerwacja.setVisible(true);

    }

    /**
     * Metoda odpowiedzialna za wyświetlenie nowego okna tworzenia dodatkowego wyposażenia do wypożyczeń aut
     */
    private void stworzDodatkoweWyposazenie()  {
         //Szablon
         JFrame wyposazenie = new JFrame("Nowe wyposażenie");
         JLabel naglowek = new JLabel("Tanie wypożyczanie - kategoria");
         JLabel logo = new JLabel(new ImageIcon("images\\tanie_wypozyczanie_logo.png"));
         JButton przyciskWroc = new JButton("Wróć");
         JButton przyciskZatwierdz = new JButton("Zatwierdź");
 
         przyciskWroc.setBounds(190, 600, 200, 50);
         przyciskZatwierdz.setBounds(420, 600, 200, 50);
 
         naglowek.setBounds(50, 25, 500, 100);
         naglowek.setFont(new Font("SansSerif", Font.BOLD, 25));
 
         logo.setBounds(650, 0, 200, 130);
 
         wyposazenie.setBounds(100, 100, 900, 700);
         wyposazenie.setLayout(null);
         wyposazenie.setAlwaysOnTop(true);
         wyposazenie.setResizable(false);
         //Koniec szablonu
         JTextArea nazwaWyposazenia = new JTextArea("Nazwa");
         JTextArea cenaWyposazenia = new JTextArea("Cena");
         nazwaWyposazenia.setBorder(BorderFactory.createLineBorder(Color.black));
         cenaWyposazenia.setBorder(BorderFactory.createLineBorder(Color.black));
         nazwaWyposazenia.setBounds(350, 200, 200, 50);
         cenaWyposazenia.setBounds(350, 260, 200, 50);

         przyciskWroc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                wyposazenie.setVisible(false);
                menuGlowne();
            }
        });

        przyciskZatwierdz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nazwa = nazwaWyposazenia.getText();
                String cena = cenaWyposazenia.getText();
                if(nazwa != "" && cena != "")   {
                    models.DodatkoweOpcje dodatkowaOpcja = new models.DodatkoweOpcje(nazwa, Double.parseDouble(cena));
                    //DodatkoweOpcje.getExtent().add(dodatkowaOpcja);
                    try {
                        Dane.zapiszDane();
                    }   catch(Exception e1) {
                        e1.printStackTrace();
                    }
                    wyposazenie.setVisible(false);
                    menuGlowne();
                }
            }
        });
        wyposazenie.getContentPane().add(logo);
        wyposazenie.getContentPane().add(naglowek);
        wyposazenie.getContentPane().add(przyciskWroc);
        wyposazenie.getContentPane().add(przyciskZatwierdz);
        wyposazenie.getContentPane().add(nazwaWyposazenia);
        wyposazenie.getContentPane().add(cenaWyposazenia);
        wyposazenie.setVisible(true);
    }
    /**
     * Metoda wyświetlająca okno zmiany kategorii pojazdu
     */
    private void zmianaKategoriiPojazdu()   {
        //Szablon
        JFrame kategoria = new JFrame("Nowa kategoria");
        JLabel naglowek = new JLabel("Tanie wypożyczanie - kategoria");
        JLabel logo = new JLabel(new ImageIcon("images\\tanie_wypozyczanie_logo.png"));
        JButton przyciskWroc = new JButton("Wróć");
        JButton przyciskZatwierdz = new JButton("Zatwierdź");

        przyciskWroc.setBounds(190, 600, 200, 50);
        przyciskZatwierdz.setBounds(420, 600, 200, 50);

        naglowek.setBounds(50, 25, 500, 100);
        naglowek.setFont(new Font("SansSerif", Font.BOLD, 25));

        logo.setBounds(650, 0, 200, 130);

        kategoria.setBounds(100, 100, 900, 700);
        kategoria.setLayout(null);
        kategoria.setAlwaysOnTop(true);
        kategoria.setResizable(false);
        //Koniec szablonu
        JLabel pojazd = new JLabel("Kategoria : Pojazd");
        JLabel kategorie = new JLabel("Dostępne kategorie");
        String[] pojazdyTablica = new String[Pojazd.getExtent().size()];
        String[] kategorieTablica = new String[Kategoria.getExtent().size()];
        int iterator = 0;
        for(models.Pojazd p : Pojazd.getExtent())    {
            StringBuilder sb = new StringBuilder();
            sb.append(p.getKategoria().getNazwa()).append(" : ").append(p.getMarka()).append(" ").append(p.getModel()).append(" <").append(p.getRejestracja()).append(">");
            pojazdyTablica[iterator] = sb.toString();
            iterator++;
        }
        iterator = 0;
        for(models.Kategoria k : Kategoria.getExtent())   {
            kategorieTablica[iterator] = k.getNazwa();
            iterator++;
        }
        JList listaPojazdow = new JList(pojazdyTablica);
        JList listaKategorii = new JList(kategorieTablica);
        JScrollPane scrollPojazdy = new JScrollPane(listaPojazdow);
        JScrollPane scrollKategorie = new JScrollPane(listaKategorii);
        scrollPojazdy.getViewport().setView(listaPojazdow);
        scrollKategorie.getViewport().setView(listaKategorii);

        kategorie.setBounds(485, 170, 200, 50);
        pojazd.setBounds(75, 170, 200, 50);
        listaPojazdow.setBounds(75, 210, 350, 300);
        scrollPojazdy.setBounds(75, 210, 350, 300);
        listaKategorii.setBounds(485, 210, 350, 300);
        scrollKategorie.setBounds(485, 210, 350, 300);

        przyciskWroc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                kategoria.setVisible(false);
                menuGlowne();
            }
        });

        przyciskZatwierdz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String wybranyPojazd = listaPojazdow.getSelectedValue().toString();
                String wybranaKategoria = listaKategorii.getSelectedValue().toString();
                String[] pojazdSplitted = wybranyPojazd.split("<");
                String rejestracjaPojazdu = pojazdSplitted[1];
                rejestracjaPojazdu = rejestracjaPojazdu.replace("<", "");
                rejestracjaPojazdu = rejestracjaPojazdu.replace(">", "");

                models.Pojazd pojazdObj = Pojazd.pobierzPojazdPoRejestracji(rejestracjaPojazdu);
                models.Kategoria kategoriaObj = null;
                int iterator = 0;
                int idKategorii = 0;
                //Zaktualizowanie obiektu kategoria i pojazd
                for(models.Kategoria k : Kategoria.getExtent())   {
                    if(k.getNazwa().equals(wybranaKategoria))   {
                        kategoriaObj = k;
                        pojazdObj.przypiszKategorie(kategoriaObj); //reverse connection
                        idKategorii = iterator;
                    }
                    iterator++;
                }
                Kategoria.getExtent().get(idKategorii).usunPojazd(pojazdObj);
                try {
                    Dane.zapiszDane();
                }   catch(Exception e1) {
                    e1.printStackTrace();
                }


                kategoria.setVisible(false);
                menuGlowne();
            }
        });

        kategoria.getContentPane().add(logo);
        kategoria.getContentPane().add(naglowek);
        kategoria.getContentPane().add(pojazd);
        kategoria.getContentPane().add(kategorie);
        kategoria.getContentPane().add(scrollPojazdy);
        kategoria.getContentPane().add(scrollKategorie);
        kategoria.getContentPane().add(przyciskWroc);
        kategoria.getContentPane().add(przyciskZatwierdz);
        kategoria.setVisible(true);

    }

    /**
     * Metoda wyświetlająca okno mycia pojazdu
     */
    private void myciePojazdu() {
        //Szablon
        JFrame mycie = new JFrame("Mycie pojazdu");
        JLabel naglowek = new JLabel("Tanie wypożyczanie - mycie");
        JLabel logo = new JLabel(new ImageIcon("images\\tanie_wypozyczanie_logo.png"));
        JButton przyciskWroc = new JButton("Wróć");
        JButton przyciskZatwierdz = new JButton("Zatwierdź");

        przyciskWroc.setBounds(190, 600, 200, 50);
        przyciskZatwierdz.setBounds(420, 600, 200, 50);

        naglowek.setBounds(50, 25, 500, 100);
        naglowek.setFont(new Font("SansSerif", Font.BOLD, 25));

        logo.setBounds(650, 0, 200, 130);

        mycie.setBounds(100, 100, 900, 700);
        mycie.setLayout(null);
        mycie.setAlwaysOnTop(true);
        mycie.setResizable(false);
        //Koniec szablonu
        JLabel osobaMyjaca = new JLabel("Osoba myjąca");
        JLabel mytyPojazd = new JLabel("Myty pojazd");

        Osoba[] osobyTablica = new Osoba[Osoba.getExtent().size()];
        Pojazd[] pojazdyTablica = new Pojazd[Pojazd.getExtent().size()];
        Pojazd.getExtent().toArray(pojazdyTablica);
        Osoba.getExtent().toArray(osobyTablica);

        JList listaOsob = new JList<Osoba>(osobyTablica);
        JList listaPojazdow = new JList<Pojazd>(pojazdyTablica);
        JScrollPane scrollOsoby = new JScrollPane(listaOsob);
        JScrollPane scrollPojazdy = new JScrollPane(listaPojazdow);
        scrollOsoby.getViewport().setView(listaOsob);
        scrollPojazdy.getViewport().setView(listaPojazdow);
        JTextArea czasMycia = new JTextArea("Czas mycia (min)");
        czasMycia.setBorder(BorderFactory.createLineBorder(Color.black));
        JTextArea dataMycia = new JTextArea("Data mycia (dd/mm/yyyy)");
        dataMycia.setBorder(BorderFactory.createLineBorder(Color.black));

        osobaMyjaca.setBounds(50, 150, 200, 50);
        mytyPojazd.setBounds(650, 150, 200, 50);
        listaOsob.setBounds(50, 210, 200, 300);
        scrollOsoby.setBounds(50, 210, 200, 300);
        listaPojazdow.setBounds(650, 210, 200, 300);
        scrollPojazdy.setBounds(650, 210, 200, 300);
        czasMycia.setBounds(350, 400, 200, 50);
        dataMycia.setBounds(350, 460, 200, 50);

        przyciskWroc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mycie.setVisible(false);
                menuGlowne();
            }
        });

        przyciskZatwierdz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Osoba osobaMyjacaText = (Osoba)listaOsob.getSelectedValue();
                Pojazd mytyPojazdText = (Pojazd)listaPojazdow.getSelectedValue();
                String dataMyciaText = dataMycia.getText();
                String czasMyciatext = czasMycia.getText();
                try {
                    Date data = new SimpleDateFormat("dd/MM/yyyy").parse(dataMyciaText);

                PracownikMyciePojazdu myciePojazdu = new PracownikMyciePojazdu(osobaMyjacaText, mytyPojazdText, data, Integer.parseInt(czasMyciatext));
                Pojazd.zaktualizujPojazd(mytyPojazdText);
                Osoba.zaktualizujOsobe(osobaMyjacaText);
                Dane.zapiszDane();
                mycie.setVisible(false);
                menuGlowne();
                }   catch(Exception e1) {
                    e1.printStackTrace();
                }
            }
        });


        mycie.getContentPane().add(osobaMyjaca);
        mycie.getContentPane().add(mytyPojazd);
        mycie.getContentPane().add(scrollOsoby);
        mycie.getContentPane().add(scrollPojazdy);
        mycie.getContentPane().add(czasMycia);
        mycie.getContentPane().add(naglowek);
        mycie.getContentPane().add(logo);
        mycie.getContentPane().add(przyciskWroc);
        mycie.getContentPane().add(przyciskZatwierdz);
        mycie.getContentPane().add(dataMycia);
        mycie.setVisible(true);




    }
    /**
     * Metoda wyświetlająca okno nowego pojazdu
     */
    private void stworzPojazd()  {
        //Szablon
        JFrame nowyPojazd = new JFrame("Nowa kategoria");
        JLabel naglowek = new JLabel("Tanie wypożyczanie - kategoria");
        JLabel logo = new JLabel(new ImageIcon("images\\tanie_wypozyczanie_logo.png"));
        JButton przyciskWroc = new JButton("Wróć");
        JButton przyciskZatwierdz = new JButton("Zatwierdź");

        przyciskWroc.setBounds(190, 600, 200, 50);
        przyciskZatwierdz.setBounds(420, 600, 200, 50);

        naglowek.setBounds(50, 25, 500, 100);
        naglowek.setFont(new Font("SansSerif", Font.BOLD, 25));

        logo.setBounds(650, 0, 200, 130);

        nowyPojazd.setBounds(100, 100, 900, 700);
        nowyPojazd.setLayout(null);
        nowyPojazd.setAlwaysOnTop(true);
        nowyPojazd.setResizable(false);
        //Koniec szablonu
        String[] kategorieTab = new String[Kategoria.getExtent().size()];
        int iterator = 0;
        for(models.Kategoria kat : Kategoria.getExtent()) {
            kategorieTab[iterator] = kat.getNazwa();
            iterator ++;
        }
        JComboBox kategorieCombo = new JComboBox(kategorieTab);
        JLabel kategoriaLabel = new JLabel("Kategoria pojazdu");
        //kategoria.setBorder(BorderFactory.createLineBorder(Color.black));
        JTextArea model = new JTextArea("Model");
        JTextArea marka = new JTextArea("Marka");
        JTextArea rejestracja = new JTextArea("Numer rejestracyjny");
        JTextArea vin = new JTextArea("Numer vin");
        JTextArea miejsce = new JTextArea("Obecne miejsce pojazdu");
        JTextArea opisKaroserii = new JTextArea("Opis karoserii pojazdu");

        model.setBorder(BorderFactory.createLineBorder(Color.black));
        marka.setBorder(BorderFactory.createLineBorder(Color.black));
        rejestracja.setBorder(BorderFactory.createLineBorder(Color.black));
        vin.setBorder(BorderFactory.createLineBorder(Color.black));
        miejsce.setBorder(BorderFactory.createLineBorder(Color.black));
        opisKaroserii.setBorder(BorderFactory.createLineBorder(Color.black));

        kategoriaLabel.setBounds(350, 150, 400, 50);
        kategorieCombo.setBounds(350, 210, 200, 50);

        marka.setBounds(250, 270, 200, 50);
        model.setBounds(460, 270, 200, 50);
        
        vin.setBounds(150, 330, 200, 50);
        miejsce.setBounds(570, 330, 200, 50);
        rejestracja.setBounds(360, 330, 200, 50);

        opisKaroserii.setBounds(50, 420, 800, 150);






        przyciskWroc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nowyPojazd.setVisible(false);
                menuGlowne();
            }
        });

        przyciskZatwierdz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String wybranaKategoria = kategorieCombo.getSelectedItem().toString();
                String wybranaMarka = marka.getText();
                String wybranyModel = model.getText();
                String wybranyVin = vin.getText();
                String wybranaRejstracja = rejestracja.getText();
                wybranaRejstracja = wybranaRejstracja.replace(" ", "");
                String wybraneMiejsce = miejsce.getText();
                String wybranyOpis = opisKaroserii.getText();

                models.Pojazd pojazd = new models.Pojazd(wybranyVin, wybranaRejstracja, wybranyOpis, wybraneMiejsce, wybranaMarka, wybranyModel);
                //Pobrać id kategorii!
                int iterator = 0;
                int idKategorii = 0;
                for(models.Kategoria kat : Kategoria.getExtent()) {
                    if(kat.getNazwa() == wybranaKategoria)  {
                        idKategorii = iterator;
                    }
                    iterator++;
                }
                pojazd.przypiszKategorie(Kategoria.getExtent().get(idKategorii));
                Pojazd.getExtent().remove(pojazd);
                Pojazd.getExtent().add(pojazd);
                try {
                    Dane.zapiszDane();
                }   catch(Exception e1) {
                    e1.printStackTrace();
                }
                nowyPojazd.setVisible(false);
                menuGlowne();

            }
        });

        nowyPojazd.getContentPane().add(logo);
        nowyPojazd.getContentPane().add(naglowek);
        nowyPojazd.getContentPane().add(przyciskWroc);
        nowyPojazd.getContentPane().add(przyciskZatwierdz);
        nowyPojazd.getContentPane().add(kategoriaLabel);
        nowyPojazd.getContentPane().add(kategorieCombo);
        nowyPojazd.getContentPane().add(marka);
        nowyPojazd.getContentPane().add(model);
        nowyPojazd.getContentPane().add(rejestracja);
        nowyPojazd.getContentPane().add(miejsce);
        nowyPojazd.getContentPane().add(vin);
        nowyPojazd.getContentPane().add(opisKaroserii);


        nowyPojazd.setVisible(true);

    }
    /**
     * Metoda wyświetlająca okno nowej kategorii
     */
    private void stworzKategorie()  {
        //Szablon
        JFrame nowaKategoria = new JFrame("Nowa kategoria");
        JLabel naglowek = new JLabel("Tanie wypożyczanie - kategoria");
        JLabel logo = new JLabel(new ImageIcon("images\\tanie_wypozyczanie_logo.png"));
        JButton przyciskWroc = new JButton("Wróć");
        JButton przyciskZatwierdz = new JButton("Zatwierdź");

        przyciskWroc.setBounds(190, 600, 200, 50);
        przyciskZatwierdz.setBounds(420, 600, 200, 50);

        naglowek.setBounds(50, 25, 500, 100);
        naglowek.setFont(new Font("SansSerif", Font.BOLD, 25));

        logo.setBounds(650, 0, 200, 130);

        nowaKategoria.setBounds(100, 100, 900, 700);
        nowaKategoria.setLayout(null);
        nowaKategoria.setAlwaysOnTop(true);
        nowaKategoria.setResizable(false);
        //Koniec szablonu
        JLabel kategoriaLabel = new JLabel("Wpisz nazwę kategori (A, B, C, D, Van, Premium)");
        JTextArea kategoria = new JTextArea("");
        JTextArea jedenDzien = new JTextArea();
        JTextArea siedemDni = new JTextArea();
        JTextArea wiecejNizSiedemDni = new JTextArea();
        JLabel opis = new JLabel("Wpisz ceny za wynajem pojazdu za jeden dzień, poniżej siedmu dni, powyżej siedmiu dni");

        kategoria.setBorder(BorderFactory.createLineBorder(Color.black));
        jedenDzien.setBorder(BorderFactory.createLineBorder(Color.black));
        siedemDni.setBorder(BorderFactory.createLineBorder(Color.black));
        wiecejNizSiedemDni.setBorder(BorderFactory.createLineBorder(Color.black));

        kategoriaLabel.setBounds(350, 200, 400, 50);
        kategoria.setBounds(350, 260, 200, 50);
        jedenDzien.setBounds(150, 380, 200, 50);
        siedemDni.setBounds(360, 380, 200, 50);
        wiecejNizSiedemDni.setBounds(570, 380, 200, 50);
        opis.setBounds(150, 320, 500, 50);

        przyciskWroc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nowaKategoria.setVisible(false);
                menuGlowne();
            }
        });

        przyciskZatwierdz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String kategoriaText = kategoria.getText();
                String jedenDzienText = jedenDzien.getText();
                String siedemDniText = siedemDni.getText();
                String wiecejNizSiedemDniText = wiecejNizSiedemDni.getText();

                if(kategoriaText != "" && jedenDzienText != "" && siedemDniText != "" && wiecejNizSiedemDniText != "")  {
                    try {
                        models.Kategoria kategoria = new models.Kategoria(kategoriaText, Double.parseDouble(jedenDzienText), Double.parseDouble(siedemDniText), Double.parseDouble(wiecejNizSiedemDniText));
                        //Kategoria.getExtent().add(kategoria);
                        Dane.zapiszDane();
                        nowaKategoria.setVisible(false);
                        menuGlowne();
                    }   catch(Exception e1)  {
                        e1.printStackTrace();
                    }
                }
            }
        });
        nowaKategoria.getContentPane().add(logo);
        nowaKategoria.getContentPane().add(naglowek);
        nowaKategoria.getContentPane().add(przyciskZatwierdz);
        nowaKategoria.getContentPane().add(przyciskWroc);
        nowaKategoria.getContentPane().add(kategoriaLabel);
        nowaKategoria.getContentPane().add(kategoria);
        nowaKategoria.getContentPane().add(jedenDzien);
        nowaKategoria.getContentPane().add(siedemDni);
        nowaKategoria.getContentPane().add(wiecejNizSiedemDni);
        nowaKategoria.getContentPane().add(opis);
        nowaKategoria.setVisible(true);

        
    }

    /**
     * Wyświetlenie głównego menu.
     * Z tego poziomu dostępne są wszystkie funkcjonalności
     */
    private void pokazMenuGlowne()  {
        JFrame menuGlowne = new JFrame("Menu główne");

        menuGlowne.setBounds(100, 100, 900, 700);
        menuGlowne.setLayout(null);
        menuGlowne.setAlwaysOnTop(true);

        JLabel labelOsoby = new JLabel("Zarządzanie osobami"); //<-----
        JButton przyciskNowaOsoba = new JButton("Nowa osoba");
        JButton przyciskZmianaUprawnien = new JButton("Zmiana uprawnień");
        JButton przyciskNowyKlient = new JButton("Nowy klient");
        JButton przyciskNowyPracownik = new JButton("Nowy Pracownik");
        JButton przyciskWszystkieOsoby = new JButton("Wszystkie osoby");
        JButton przyciskWyliczeniePremii = new JButton("Kalkulator premii");

        JLabel labelFlota = new JLabel("Zarządzanie flotą"); //<-----
        JButton przyciskNowaKategoria = new JButton("Nowa kategoria pojazdu");
        JButton przyciskNowyPojazd = new JButton("Nowy pojazd");
        JButton przyciskMyciePojazdu = new JButton("Mycie pojazdu");
        JButton przyciskZminaKategorii = new JButton("Zmiana katgorii pojazdu");
        JButton przyciskNiezwrocone = new JButton("Niezwrócone pojazdy");
        JButton przyciskMyciaPojazdow = new JButton("Mycia pojazdów");

        JLabel labelFunkcje = new JLabel("Funkcje"); //<------
        JButton przyciskNowaRelokacja = new JButton("Nowa relokacja");
        JButton przyciskNowaRezerwacja = new JButton("Nowa rezerwacja");
        JButton przyciskZapisDanych = new JButton("Zapis danych");
        JButton przyciskWszystkieRelokacje = new JButton("Wszystkie relokacje");
        JButton przyciskZwrotPojazdu = new JButton("Zwrot pojazdu");
        JButton przyciskNowaOpcja = new JButton("Dodatkowe wyposażenie");

        JLabel naglowek = new JLabel("Tanie wypożyczanie - menu główne");
        JLabel logo = new JLabel(new ImageIcon("images\\tanie_wypozyczanie_logo.png"));

        naglowek.setBounds(50, 25, 500, 100);
        naglowek.setFont(new Font("SansSerif", Font.BOLD, 25));

        logo.setBounds(650, 0, 200, 130);

        //setBounds -> x, y, szer, wys
        labelOsoby.setBounds(100, 100, 500, 50);
        labelOsoby.setFont(new Font("SansSerif", Font.BOLD, 20));
        labelFlota.setBounds(100, 250, 500, 60);
        labelFlota.setFont(new Font("SansSerif", Font.BOLD, 20));
        labelFunkcje.setBounds(100, 400, 500, 70);
        labelFunkcje.setFont(new Font("SansSerif", Font.BOLD, 20));

        //Osoby - przyciski
        przyciskNowaOsoba.setBounds(50, 150, 200, 50);
        przyciskZmianaUprawnien.setBounds(260, 150, 200, 50);
        przyciskNowyKlient.setBounds(470, 150, 200, 50);
        przyciskNowyPracownik.setBounds(680, 150, 200, 50);
        przyciskWszystkieOsoby.setBounds(50, 210, 200, 50);
        przyciskWyliczeniePremii.setBounds(260, 210, 200, 50);

        //Flota - przyciski
        przyciskNowaKategoria.setBounds(50, 300, 200, 50);
        przyciskNowyPojazd.setBounds(260, 300, 200, 50);
        przyciskMyciePojazdu.setBounds(470, 300, 200, 50);
        przyciskZminaKategorii.setBounds(680, 300, 200, 50);
        przyciskNiezwrocone.setBounds(50, 360, 200, 50);
        przyciskMyciaPojazdow.setBounds(260, 360, 200, 50);

        //Funkcje - przyciski
        przyciskNowaRelokacja.setBounds(50, 450, 200, 50);
        przyciskNowaRezerwacja.setBounds(260, 450, 200, 50);
        przyciskZapisDanych.setBounds(470, 450, 200, 50);
        przyciskWszystkieRelokacje.setBounds(680, 450, 200, 50);
        przyciskZwrotPojazdu.setBounds(50, 510, 200, 50);
        przyciskNowaOpcja.setBounds(260, 510, 200, 50);


        //Triggery
        //Osoby
        przyciskNowaOsoba.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuGlowne.setVisible(false);
                stworzOsobe();
            }
        });
        przyciskZmianaUprawnien.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuGlowne.setVisible(false);
                zmienUprawnienia();
            }
        });
        przyciskNowyKlient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuGlowne.setVisible(false);
                stworzKlienta();
            }
        });
        przyciskNowyPracownik.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuGlowne.setVisible(false);
                stworzPracownika();
            }
        });
        przyciskWszystkieOsoby.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuGlowne.setVisible(false);
                wszystkieOsoby();
            }
        });
        przyciskWyliczeniePremii.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuGlowne.setVisible(false);
                obliczaniePremii();
            }
        });
        //Flota
        przyciskNowaKategoria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuGlowne.setVisible(false);
                stworzKategorie();
            }
        });
        przyciskNowyPojazd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuGlowne.setVisible(false);
                stworzPojazd();
            }
        });
        przyciskMyciePojazdu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuGlowne.setVisible(false);
                utworzMyciePojazdu();
            }
        });
        przyciskZminaKategorii.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuGlowne.setVisible(false);
                zmianaKategoriiPojazdu();
            }
        });
        przyciskNiezwrocone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuGlowne.setVisible(false);
                pokazNiezwroconePojazdy();
            }
        });
        przyciskMyciaPojazdow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuGlowne.setVisible(false);
                myciaPojazdow();
            }
        });
        //Funkcje
        przyciskNowaRelokacja.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuGlowne.setVisible(false);
                nowaRelokacja();
            }
        });
        przyciskNowaRezerwacja.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuGlowne.setVisible(false);
                nowaRezerwacja();
            }
        });
        przyciskZapisDanych.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Dane.zapiszDane();
                }   catch(Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        przyciskWszystkieRelokacje.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuGlowne.setVisible(false);
                wszystkieRelokacje();
            }
        });
        przyciskZwrotPojazdu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuGlowne.setVisible(false);
                pokazZwracaniePojazdu();
            }
        });
        przyciskNowaOpcja.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuGlowne.setVisible(false);
                nowaOpcja();
            }
        });



        //Label
        menuGlowne.getContentPane().add(logo);
        menuGlowne.getContentPane().add(naglowek);
        menuGlowne.getContentPane().add(labelOsoby);
        menuGlowne.getContentPane().add(labelFlota);
        menuGlowne.getContentPane().add(labelFunkcje);
        //Button
        menuGlowne.getContentPane().add(przyciskNowaOsoba);
        menuGlowne.getContentPane().add(przyciskZmianaUprawnien);
        menuGlowne.getContentPane().add(przyciskNowyKlient);
        menuGlowne.getContentPane().add(przyciskNowyPracownik);
        menuGlowne.getContentPane().add(przyciskWszystkieOsoby);
        menuGlowne.getContentPane().add(przyciskWyliczeniePremii);
        menuGlowne.getContentPane().add(przyciskNowaKategoria);
        menuGlowne.getContentPane().add(przyciskNowyPojazd);
        menuGlowne.getContentPane().add(przyciskMyciePojazdu);
        menuGlowne.getContentPane().add(przyciskMyciaPojazdow);
        menuGlowne.getContentPane().add(przyciskZminaKategorii);
        menuGlowne.getContentPane().add(przyciskNiezwrocone);
        menuGlowne.getContentPane().add(przyciskNowaRelokacja);
        menuGlowne.getContentPane().add(przyciskNowaRezerwacja);
        menuGlowne.getContentPane().add(przyciskZapisDanych);
        menuGlowne.getContentPane().add(przyciskWszystkieRelokacje);
        menuGlowne.getContentPane().add(przyciskZwrotPojazdu);
        menuGlowne.getContentPane().add(przyciskNowaOpcja);

        menuGlowne.setResizable(false);
        menuGlowne.setVisible(true);
    }

    /**
     * Wyświetlanie okienka zawierającego informacje
     * o wszystkich myciach pojazdów
     */
    private void myciaPojazdow()    {
        JFrame myciaPojazdow = new JFrame("Mycia pojazdów");
        JLabel naglowek = new JLabel("Tanie wypożyczanie - mycia");
        JLabel logo = new JLabel(new ImageIcon("images\\tanie_wypozyczanie_logo.png"));
        JButton przyciskWroc = new JButton("Wróć");
        JScrollPane scrollPane;
        JLabel info = new JLabel("Pojazd - Marka model (numer rejestracyjny)");
        JLabel osobaInfo = new JLabel("Osoba myjąca - Imie Nazwisko (data)");
        JScrollPane scrollPane2;
        String[] mycia;
        pierwszy = true;
        JList myciaPuste = new JList();
        myciaPuste.setBounds(450, 150, 300, 300);
        myciaPuste.setBorder(BorderFactory.createLineBorder(Color.black));


        przyciskWroc.setBounds(350, 600, 200, 50);

        naglowek.setBounds(50, 25, 500, 100);
        naglowek.setFont(new Font("SansSerif", Font.BOLD, 25));

        logo.setBounds(650, 0, 200, 130);

        myciaPojazdow.setBounds(100, 100, 900, 700);
        myciaPojazdow.setLayout(null);
        myciaPojazdow.setAlwaysOnTop(true);

        List<models.Pojazd> pojazdy = Pojazd.pobierzPojazdy();
        Pojazd[] pojazdyTablica = new Pojazd[pojazdy.size()];
        Pojazd.getExtent().toArray(pojazdyTablica);

        JList listaPojazdow = new JList<Pojazd>(pojazdyTablica);
        scrollPane = new JScrollPane(listaPojazdow);

        listaPojazdow.setBounds(100, 150, 300, 300);
        scrollPane.getViewport().setView(listaPojazdow);
        scrollPane.setBounds(100, 150, 300, 300);
        info.setBounds(100, 100, 300, 50);
        osobaInfo.setBounds(450, 100, 300, 50);

        scrollPane.setBorder(BorderFactory.createLineBorder(Color.black));
        listaPojazdow.setBorder(BorderFactory.createLineBorder(Color.black));
        myciaPuste.setBorder(BorderFactory.createLineBorder(Color.black));


        listaPojazdow.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
               if (me.getClickCount() == 2) {
                    //Najpierw pobieram informacje o pojeździe
                    Pojazd szukanyPojazd = (Pojazd)listaPojazdow.getSelectedValue();
                    //Potem pobieram informacje o jego myciach
                    List<PracownikMyciePojazdu> myciaPojazdu = szukanyPojazd.getMycia();
                    List<String> listaDanych = new ArrayList<>();
                    for(PracownikMyciePojazdu mp : myciaPojazdu) {
                        StringBuilder sb = new StringBuilder();
                        String pattern = "dd/MM/yyyy";
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                        String data = simpleDateFormat.format(mp.getData());
                        sb.append("Pracownik: ").append(mp.getOsoba().getImie()).append(" ").append(mp.getOsoba().getNazwisko()).append(" (").append(data).append(")");
                        listaDanych.add(sb.toString());
                    }
                    //Zaktualizowanie listy
                    myciaPojazdow.getContentPane().remove(myciaPuste);
                    String[] tablicaOsob = new String[listaDanych.size()];
                    listaDanych.toArray(tablicaOsob);
                    JList mycie = new JList(tablicaOsob);
                    JScrollPane scroll = new JScrollPane(mycie);
                    
                    mycie.setBounds(450, 150, 300, 300);
                    scroll.getViewport().setView(mycie);
                    scroll.setBounds(450, 150, 300, 300);
                    scroll.setBorder(BorderFactory.createLineBorder(Color.black));
                    mycie.setBorder(BorderFactory.createLineBorder(Color.black));
                    aktualizujListe(myciaPojazdow, scroll);
            
                    
               }
            }
         });


        przyciskWroc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myciaPojazdow.setVisible(false);
                pokazMenuGlowne();
            }
        });
        myciaPojazdow.getContentPane().add(przyciskWroc);
        myciaPojazdow.getContentPane().add(logo);
        myciaPojazdow.getContentPane().add(naglowek);
        myciaPojazdow.getContentPane().add(scrollPane);
        myciaPojazdow.getContentPane().add(myciaPuste);
        myciaPojazdow.getContentPane().add(info);
        myciaPojazdow.getContentPane().add(osobaInfo);
        myciaPojazdow.setResizable(false);
        myciaPojazdow.setVisible(true);

    }

    private void aktualizujListe(JFrame okno, JScrollPane scroll) {
        if(pierwszy)   {
            okno.getContentPane().add(scroll);
            ostatni = scroll;
            pierwszy = false;
        }   else    {
            okno.getContentPane().remove(ostatni);
            okno.getContentPane().add(scroll);
        }
    }
    /**
     * Wyświetlanie okna zawierającego informacje
     * o wszystkich relokacjach zapisanych w systemie
     */
    private void wszystkieRelokacje()   {
        JFrame rlokacjePojazdow = new JFrame("Relokacje");
        JLabel naglowek = new JLabel("Tanie wypożyczanie - relokacje");
        JLabel logo = new JLabel(new ImageIcon("images\\tanie_wypozyczanie_logo.png"));
        JButton przyciskWroc = new JButton("Wróć");
        JScrollPane scrollPane;
        JLabel info = new JLabel("Pojazd - Marka model (numer rejestracyjny)");
        JLabel osobaInfo = new JLabel("Osoba myjąca - Imie Nazwisko (data)");
        JScrollPane scrollPane2;
        String[] mycia;
        pierwszy = true;
        JList myciaPuste = new JList();
        myciaPuste.setBounds(450, 150, 300, 300);
        myciaPuste.setBorder(BorderFactory.createLineBorder(Color.black));

        przyciskWroc.setBounds(350, 600, 200, 50);

        naglowek.setBounds(50, 25, 500, 100);
        naglowek.setFont(new Font("SansSerif", Font.BOLD, 25));

        logo.setBounds(650, 0, 200, 130);

        rlokacjePojazdow.setBounds(100, 100, 900, 700);
        rlokacjePojazdow.setLayout(null);
        rlokacjePojazdow.setAlwaysOnTop(true);

        List<Zgloszenie> zgloszenia = Zgloszenie.getExtent();
        Zgloszenie[] zgloszeniaTablica = new Zgloszenie[zgloszenia.size()];
        zgloszenia.toArray(zgloszeniaTablica);

        JList listaZgloszen = new JList<Zgloszenie>(zgloszeniaTablica);
        scrollPane = new JScrollPane(listaZgloszen);

        listaZgloszen.setBounds(100, 150, 300, 300);
        scrollPane.getViewport().setView(listaZgloszen);
        scrollPane.setBounds(100, 150, 300, 300);
        info.setBounds(100, 100, 300, 50);
        osobaInfo.setBounds(450, 100, 300, 50);

        scrollPane.setBorder(BorderFactory.createLineBorder(Color.black));
        listaZgloszen.setBorder(BorderFactory.createLineBorder(Color.black));
        myciaPuste.setBorder(BorderFactory.createLineBorder(Color.black));


        listaZgloszen.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
               if (me.getClickCount() == 2) {
                    //Najpierw pobieram informacje o pojeździe
                    Zgloszenie wybraneZgloszenie = (Zgloszenie)listaZgloszen.getSelectedValue();
                    //Potem pobieram informacje o jego myciach
                    List<Osoba> osobyMyjace = wybraneZgloszenie.pobierzKierowcow();
                    List<String> listaDanych = new ArrayList<>();
                    for(Osoba mp : osobyMyjace) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(mp.getImie() + " " + mp.getNazwisko());
                        listaDanych.add(sb.toString());
                    }
                    //Zaktualizowanie listy
                    rlokacjePojazdow.getContentPane().remove(myciaPuste);
                    String[] tablicaOsob = new String[listaDanych.size()];
                    listaDanych.toArray(tablicaOsob);
                    JList mycie = new JList(tablicaOsob);
                    JScrollPane scroll = new JScrollPane(mycie);
                    
                    mycie.setBounds(450, 150, 300, 300);
                    scroll.getViewport().setView(mycie);
                    scroll.setBounds(450, 150, 300, 300);
                    scroll.setBorder(BorderFactory.createLineBorder(Color.black));
                    mycie.setBorder(BorderFactory.createLineBorder(Color.black));
                    aktualizujListe(rlokacjePojazdow, scroll);
            
                    
               }
            }
         });


        przyciskWroc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rlokacjePojazdow.setVisible(false);
                pokazMenuGlowne();
            }
        });
        rlokacjePojazdow.getContentPane().add(przyciskWroc);
        rlokacjePojazdow.getContentPane().add(logo);
        rlokacjePojazdow.getContentPane().add(naglowek);
        rlokacjePojazdow.getContentPane().add(scrollPane);
        rlokacjePojazdow.getContentPane().add(myciaPuste);
        rlokacjePojazdow.getContentPane().add(info);
        rlokacjePojazdow.getContentPane().add(osobaInfo);
        rlokacjePojazdow.setResizable(false);
        rlokacjePojazdow.setVisible(true);


    }

    /**
     * Wyświetlenie głównego okna w ramach relokacji.
     * Okno wyświetla dane wszystkich dostępnych pojazdów.
     */
    private void pokazOknoGlowneRelokacji() {
        List<String> wybranePojazdy = new ArrayList<>();
        String[] pojazdy = Pojazd.pobierzDostepnePojazdy();
        JList listaPojazdow = new JList<String>(pojazdy);

        JFrame oknoGlowne = new JFrame("Relokacja pojazdu");
        JScrollPane scrollPane = new JScrollPane(listaPojazdow);
        JButton przyciskDodaj = new JButton("Dodaj");
        JButton przyciskUsun = new JButton("Usuń");
        JButton przyciskZatwierdz = new JButton("Zatwierdź");
        JLabel aktualnyStatus = new JLabel("");
        JLabel naglowek = new JLabel("Tanie wypożyczanie - relokacja");
        JLabel logo = new JLabel(new ImageIcon("images\\tanie_wypozyczanie_logo.png"));

        oknoGlowne.setBounds(100, 100, 900, 700);
        oknoGlowne.setLayout(null);
        oknoGlowne.setAlwaysOnTop(true);

        listaPojazdow.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listaPojazdow.setBounds(100, 150, 700, 300);

        scrollPane.getViewport().setView(listaPojazdow);
        scrollPane.setBounds(100, 150, 700 ,300);

        przyciskDodaj.setBounds(100, 475, 100, 50);
        przyciskUsun.setBounds(225, 475, 100, 50);

        przyciskZatwierdz.setBounds(375, 550, 150, 75);
        aktualnyStatus.setBounds(350, 475, 200, 50);

        naglowek.setBounds(50, 25, 500, 100);
        naglowek.setFont(new Font("SansSerif", Font.BOLD, 25));

        logo.setBounds(650, 0, 200, 130);

        przyciskDodaj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(Object s : listaPojazdow.getSelectedValuesList())   {
                    if(!wybranePojazdy.contains(s.toString()))  {
                        wybranePojazdy.add(s.toString());
                    }
                }
                aktualnyStatus.setText("Dodano pojazdy do listy");
            }
        });
        przyciskUsun.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(Object s : listaPojazdow.getSelectedValuesList())   {
                    if(wybranePojazdy.contains(s.toString()))   {
                        wybranePojazdy.remove(s.toString());
                    }
                }
                aktualnyStatus.setText("Usunięto wybrane pojazdy z listy");
            }
        });
        przyciskZatwierdz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                oknoGlowne.setVisible(false);
                pokazWybranePojazdy(wybranePojazdy);
            }
        });

        oknoGlowne.getContentPane().add(logo);
        oknoGlowne.getContentPane().add(naglowek);
        oknoGlowne.getContentPane().add(aktualnyStatus);
        oknoGlowne.getContentPane().add(przyciskZatwierdz);
        oknoGlowne.getContentPane().add(przyciskUsun);
        oknoGlowne.getContentPane().add(przyciskDodaj);
        oknoGlowne.getContentPane().add(scrollPane);
        oknoGlowne.setResizable(false);
        oknoGlowne.setVisible(true);
    }

    /**
     * Okno wyświetlające podsumowanie wybranych pojazdów z okna głównego
     * @param listaWybranychPojazdow lista wybranych pojazdów do relokacji
     */
    private void pokazWybranePojazdy(List<String> listaWybranychPojazdow)   {
        if(listaWybranychPojazdow.size() == 0) pokazOknoGlowneRelokacji();
        String[] wybranePojazdy = new String[listaWybranychPojazdow.size()];
        listaWybranychPojazdow.toArray(wybranePojazdy);

        JFrame oknoWybranePojazdy = new JFrame("Relokacja pojazdu");
        //JList listaPojazdow = new JList<String>(wybranePojazdy);
        JTextArea listaPojazdow = new JTextArea();
        listaPojazdow.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(listaPojazdow);
        JButton przyciskWroc = new JButton("Wróć");
        JButton przyciskZatwierdz = new JButton("Zatwierdź");
        JLabel naglowek = new JLabel("Tanie wypożyczanie - relokacja");
        JLabel logo = new JLabel(new ImageIcon("images\\tanie_wypozyczanie_logo.png"));

        oknoWybranePojazdy.setBounds(100, 100, 900, 700);
        oknoWybranePojazdy.setLayout(null);
        oknoWybranePojazdy.setAlwaysOnTop(true);


        listaPojazdow.setBounds(100, 150, 700, 300);
        int jump = 20; //'skok' linii tekstowej w dół
        for(String pojazd : listaWybranychPojazdow) {
            JLabel label = new JLabel(pojazd.toString());
            label.setBounds(0, jump, 700, 20);
            jump = jump+20;
            listaPojazdow.add(label);
        }
        scrollPane.getViewport().setView(listaPojazdow);
        scrollPane.setBounds(100, 150, 700 ,300);

        przyciskWroc.setBounds(650, 475, 150, 75);

        przyciskZatwierdz.setBounds(100, 475, 150, 75);

        naglowek.setBounds(50, 25, 500, 100);
        naglowek.setFont(new Font("SansSerif", Font.BOLD, 25));

        logo.setBounds(650, 0, 200, 130);

        przyciskWroc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                oknoWybranePojazdy.setVisible(false);
                pokazOknoGlowneRelokacji();
            }
        });
        przyciskZatwierdz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listaWybranychPojazdowString = listaWybranychPojazdow;
                oknoWybranePojazdy.setVisible(false);
                parujPracownikow(wybranePojazdy);
            }
        });

        oknoWybranePojazdy.getContentPane().add(logo);
        oknoWybranePojazdy.getContentPane().add(naglowek);
        oknoWybranePojazdy.getContentPane().add(przyciskZatwierdz);
        oknoWybranePojazdy.getContentPane().add(przyciskWroc);
        oknoWybranePojazdy.getContentPane().add(scrollPane);
        oknoWybranePojazdy.setResizable(false);
        oknoWybranePojazdy.setVisible(true);
    }

    /**
     * Okno wyświetlające wybrane pojazdy, oraz dostępnych kierowców do relokacji.
     * Można sparować jeden samochód z maksymalnie dwoma kierowcami.
     * @param wybranePojazdy lista wybranych pojazdów z okna głównego
     */
    private void parujPracownikow(String[] wybranePojazdy) {
        JFrame oknoParowania = new JFrame("Relokacja pojazdu");
        JList listaPojazdow = new JList<String>(wybranePojazdy);
        JScrollPane pojazdyScroll = new JScrollPane(listaPojazdow);
        JList listaPracownikow = new JList<String>(Osoba.pobierzDostepnychKierowcow());
        JScrollPane pracownicyScroll = new JScrollPane(listaPracownikow);
        JButton przyciskZatwierdz = new JButton("Zatwierdź");
        JLabel naglowek = new JLabel("Tanie wypożyczanie - relokacja");
        JLabel logo = new JLabel(new ImageIcon("images\\tanie_wypozyczanie_logo.png"));
        JLabel samochod = new JLabel("");
        JLabel pracownik = new JLabel("");
        JLabel samochodInfo = new JLabel("Wybrany pojazd");
        JLabel pracownikInfo = new JLabel("Wybrany kierowca");
        JLabel kierowcyHead = new JLabel("Kierowcy");
        JLabel wybranePojazdyHead = new JLabel("Wybrane pojazdy");
        JButton paruj = new JButton("Paruj");
        DefaultListModel sparowani = new DefaultListModel();
        JList listaSparowanych = new JList(sparowani);
        JScrollPane sparowaniScroll = new JScrollPane(listaSparowanych);
        List<String> uzyciPracownicy = new ArrayList<>();
        List<String> uzytePojazdy = new ArrayList<>();
        JButton usun = new JButton("Usuń");

        oknoParowania.setBounds(100, 100, 900, 700);
        oknoParowania.setLayout(null);
        oknoParowania.setAlwaysOnTop(true);

        listaPojazdow.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaPojazdow.setBounds(100, 180, 300, 200);

        listaPracownikow.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listaPracownikow.setBounds(500, 180, 300, 200);

        wybranePojazdyHead.setBounds(200, 150, 100, 20);

        pojazdyScroll.getViewport().setView(listaPojazdow);
        pojazdyScroll.setBounds(100, 180, 300 ,200);

        samochodInfo.setBounds(200, 400, 200, 20);
        samochod.setBounds(100, 425, 300, 25);
        Border borderSamochod = BorderFactory.createLineBorder(Color.BLACK, 1);
        samochod.setBorder(borderSamochod);
        samochod.setFont(new Font("SansSerif", Font.PLAIN, 10));

        kierowcyHead.setBounds(625, 150, 100, 20);

        pracownicyScroll.getViewport().setView(listaPracownikow);
        pracownicyScroll.setBounds(500, 180, 300, 200);

        listaSparowanych.setBounds(100, 475, 700, 100);
        sparowaniScroll.getViewport().setView(listaSparowanych);
        sparowaniScroll.setBounds(100, 475, 600, 100);

        pracownikInfo.setBounds(600, 400, 200, 20);
        pracownik.setBounds(500, 425, 300, 25);
        Border borderPracownik = BorderFactory.createLineBorder(Color.BLACK, 1);
        pracownik.setBorder(borderPracownik);
        pracownik.setFont(new Font("SansSerif", Font.PLAIN, 10));

        usun.setBounds(750, 500, 75, 30);

        przyciskZatwierdz.setBounds(400, 600, 100, 40);

        paruj.setBounds(410, 425, 80, 25);

        naglowek.setBounds(50, 25, 500, 100);
        naglowek.setFont(new Font("SansSerif", Font.BOLD, 25));

        logo.setBounds(650, 0, 200, 130);


        paruj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(listaPojazdow.getSelectedValue().toString() != null) {
                    if(!listaPojazdow.getSelectedValue().toString().equals(""))  {
                        if(listaPracownikow.getSelectedValuesList().size() > 0) {
                            if(listaPracownikow.getSelectedValuesList().size() < 3) {
                                if(!uzytePojazdy.contains(listaPojazdow.getSelectedValue().toString()) && !uzyciPracownicy.contains(listaPracownikow.getSelectedValue().toString())) {
                                    String osobyString = "";
                                    if(listaPracownikow.getSelectedValuesList().size() == 1)  osobyString = listaPracownikow.getSelectedValuesList().get(0).toString();
                                    if(listaPracownikow.getSelectedValuesList().size() == 2) osobyString = listaPracownikow.getSelectedValuesList().get(0).toString() + ","+listaPracownikow.getSelectedValuesList().get(1).toString();
                                    sparowani.add(sparowani.getSize(), listaPojazdow.getSelectedValue().toString() + "<-------->" + osobyString);
                                    uzytePojazdy.add(listaPojazdow.getSelectedValue().toString());
                                    for(Object os : listaPracownikow.getSelectedValuesList())   {
                                        uzyciPracownicy.add(os.toString());
                                    }
                                }   else {
                                    if (uzytePojazdy.contains(listaPojazdow.getSelectedValue().toString())) {
                                        samochod.setText("już w liście");
                                    }
                                    if (uzyciPracownicy.contains(listaPracownikow.getSelectedValue().toString())) {
                                        pracownik.setText("Już w liście");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
        przyciskZatwierdz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> sparowani = new ArrayList<>();
                for(int i = 0; i < listaSparowanych.getModel().getSize(); i++) {
                    sparowani.add(String.valueOf(listaSparowanych.getModel().getElementAt(i)));
                }
                oknoParowania.setVisible(false);
                wyswietlPotwierdzenieParowania(sparowani, wybranePojazdy);
            }
        });
        usun.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] daneParowania = listaSparowanych.getSelectedValue().toString().split("<-------->");
                uzytePojazdy.remove(daneParowania[0]);
                uzyciPracownicy.remove(daneParowania[1]);
                sparowani.remove(sparowani.indexOf(listaSparowanych.getSelectedValue().toString()));

            }
        });
        listaPojazdow.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                if (!arg0.getValueIsAdjusting()) {
                    samochod.setText(listaPojazdow.getSelectedValue().toString());
                }
            }
        });
        listaPracownikow.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                if (!arg0.getValueIsAdjusting()) {
                    pracownik.setText(listaPracownikow.getSelectedValue().toString());
                }
            }
        });
        oknoParowania.getContentPane().add(sparowaniScroll);
        oknoParowania.getContentPane().add(paruj);
        oknoParowania.getContentPane().add(wybranePojazdyHead);
        oknoParowania.getContentPane().add(kierowcyHead);
        oknoParowania.getContentPane().add(pracownikInfo);
        oknoParowania.getContentPane().add(samochodInfo);
        oknoParowania.getContentPane().add(samochod);
        oknoParowania.getContentPane().add(pracownik);
        oknoParowania.getContentPane().add(logo);
        oknoParowania.getContentPane().add(naglowek);
        oknoParowania.getContentPane().add(przyciskZatwierdz);
        oknoParowania.getContentPane().add(usun);
        oknoParowania.getContentPane().add(pojazdyScroll);
        oknoParowania.getContentPane().add(pracownicyScroll);
        oknoParowania.setResizable(false);
        oknoParowania.setVisible(true);
    }

    /**
     * Okno podsumowujace dane potrzebne do stworzenia zlecenia relokacji.
     * @param sparowani lista przechowująca dane o pojeździe i przypisanym mu kierowcom w postaci łańcuchów znaków
     * @param pojazdy wybrane pojazdy podczas tworzenia relokacji
     */
    private void wyswietlPotwierdzenieParowania(List<String> sparowani, String[] pojazdy) {
        if(sparowani.size() == 0) parujPracownikow(pojazdy);
        this.sparowani = sparowani;
        String[] sparowanePary = new String[sparowani.size()];
        sparowani.toArray(sparowanePary);

        JFrame oknoPary = new JFrame("Relokacja pojazdu");
        JTextArea utworzonePary = new JTextArea();
        utworzonePary.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(utworzonePary);
        JButton przyciskAnuluj = new JButton("Anuluj");
        JButton przyciskZatwierdz = new JButton("Zatwierdź");
        JLabel naglowek = new JLabel("Tanie wypożyczanie - relokacja");
        JLabel logo = new JLabel(new ImageIcon("images\\tanie_wypozyczanie_logo.png"));

        oknoPary.setBounds(100, 100, 900, 700);
        oknoPary.setLayout(null);
        oknoPary.setAlwaysOnTop(true);


        utworzonePary.setBounds(100, 150, 700, 300);
        int jump = 20; //Skok linii tekstowej w dół
        for(String para : sparowani) {
            String paraText = para.replace("<-------->", "        =>     ");
            JLabel label = new JLabel(paraText);
            label.setBounds(0, jump, 700, 20);
            jump = jump+20;
            utworzonePary.add(label);
        }
        scrollPane.getViewport().setView(utworzonePary);
        scrollPane.setBounds(100, 150, 700 ,300);

        przyciskAnuluj.setBounds(650, 475, 150, 75);

        przyciskZatwierdz.setBounds(100, 475, 150, 75);

        naglowek.setBounds(50, 25, 500, 100);
        naglowek.setFont(new Font("SansSerif", Font.BOLD, 25));

        logo.setBounds(650, 0, 200, 130);

        przyciskAnuluj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                oknoPary.setVisible(false);
            }
        });
        przyciskZatwierdz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                oknoPary.setVisible(false);
                wyswietlOknoZatwierdzenia();
            }
        });

        oknoPary.getContentPane().add(logo);
        oknoPary.getContentPane().add(naglowek);
        oknoPary.getContentPane().add(przyciskZatwierdz);
        oknoPary.getContentPane().add(przyciskAnuluj);
        oknoPary.getContentPane().add(scrollPane);
        oknoPary.setResizable(false);
        oknoPary.setVisible(true);
    }

    /**
     * Okno wyświetlające informacje o pozytywnie zarejestrowanej relokacji
     */
    private void wyswietlOknoZatwierdzenia()    {
        Zgloszenie.zapiszRelokacje(sparowani);
        JFrame potwierdzenie = new JFrame("Relokacja pojazdu");
        potwierdzenie.setBounds(100, 100, 900, 700);
        JLabel naglowek = new JLabel("Tanie wypożyczanie - relokacja");
        JLabel logo = new JLabel(new ImageIcon("images\\tanie_wypozyczanie_logo.png"));
        JLabel obrazek = new JLabel(new ImageIcon("images\\potwierdzenie.png"));
        JLabel potwierdzenieTekst = new JLabel("Pomyślnie zapisano relokację!");
        JButton zatwierdzPrzycisk = new JButton("OK");

        zatwierdzPrzycisk.setBounds(350, 550, 200, 50);

        potwierdzenie.setLayout(null);
        potwierdzenie.setAlwaysOnTop(true);

        naglowek.setBounds(50, 25, 500, 100);
        naglowek.setFont(new Font("SansSerif", Font.BOLD, 25));

        potwierdzenieTekst.setBounds(250, 70, 500, 50);
        potwierdzenieTekst.setFont(new Font("SansSerif", Font.BOLD, 25));

        obrazek.setBounds(250, 130, 400, 400);
        logo.setBounds(650, 0, 200, 130);

        zatwierdzPrzycisk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                potwierdzenie.setVisible(false);
                menuGlowne();
            }
        });


        potwierdzenie.getContentPane().add(potwierdzenieTekst);
        potwierdzenie.getContentPane().add(naglowek);
        potwierdzenie.getContentPane().add(logo);
        potwierdzenie.getContentPane().add(obrazek);
        potwierdzenie.getContentPane().add(zatwierdzPrzycisk);
        potwierdzenie.setVisible(true);
    }

    /**
     * Okno odpowiedzialne za dodawanie nowej osoby
     */
    private void stworzOsobe()  {
        JFrame nowaOsoba = new JFrame("Nowa osoba");
        JLabel naglowek = new JLabel("Tanie wypożyczanie - relokacja");
        JLabel logo = new JLabel(new ImageIcon("images\\tanie_wypozyczanie_logo.png"));
        nowaOsoba.setBounds(100, 100, 900, 700);
        nowaOsoba.setAlwaysOnTop(true);
        nowaOsoba.setResizable(false);

        naglowek.setBounds(50, 25, 500, 100);
        naglowek.setFont(new Font("SansSerif", Font.BOLD, 25));
        logo.setBounds(650, 0, 200, 130);

        JTextArea pesel = new JTextArea("PESEL");
        JTextArea imie = new JTextArea("Imię");
        JTextArea nazwisko = new JTextArea("Nazwisko");
        JTextArea numerTelefonu = new JTextArea("Numer telefonu");
        JTextArea email = new JTextArea("Email");
        JButton przyciskWroc = new JButton("Wróć");
        JButton przyciskDalej = new JButton("Dalej");
        JLabel debug = new JLabel("");

        imie.setBounds(250, 200, 200, 50);
        nazwisko.setBounds(460, 200, 200, 50);
        numerTelefonu.setBounds(250, 260, 200, 50);
        email.setBounds(350, 320, 200, 50);
        pesel.setBounds(460, 260, 200, 50);
        przyciskWroc.setBounds(250, 400, 200, 50);
        przyciskDalej.setBounds(460, 400, 200, 50);

        imie.setBorder(BorderFactory.createLineBorder(Color.black));
        nazwisko.setBorder(BorderFactory.createLineBorder(Color.black));
        email.setBorder(BorderFactory.createLineBorder(Color.black));
        pesel.setBorder(BorderFactory.createLineBorder(Color.black));
        numerTelefonu.setBorder(BorderFactory.createLineBorder(Color.black));

        przyciskWroc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nowaOsoba.setVisible(false);
                menuGlowne();
            }
        });
        przyciskDalej.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String imieVal = imie.getText();
                String nazwiskoVal = nazwisko.getText();
                String numerTelVal = numerTelefonu.getText();
                String emailVal = email.getText();
                String peselVal = pesel.getText();
                Osoba.dodajOsobe(imieVal, nazwiskoVal, numerTelVal, emailVal, peselVal);
                nowaOsoba.setVisible(false);
                menuGlowne();
            }
        });


        nowaOsoba.getContentPane().add(naglowek);
        nowaOsoba.getContentPane().add(numerTelefonu);
        nowaOsoba.getContentPane().add(nazwisko);
        nowaOsoba.getContentPane().add(imie);
        nowaOsoba.getContentPane().add(pesel);
        nowaOsoba.getContentPane().add(logo);
        nowaOsoba.getContentPane().add(email);
        nowaOsoba.getContentPane().add(przyciskWroc);
        nowaOsoba.getContentPane().add(przyciskDalej);
        nowaOsoba.getContentPane().add(debug);
        nowaOsoba.setVisible(true);
    }

    /**
     * Okno odpowiedzialne za tworzenie nowego klienta
     */
    private void stworzKlienta()  {
        JFrame nowyKlient = new JFrame("Nowy klient");
        JLabel naglowek = new JLabel("Tanie wypożyczanie - nowy klient");
        JLabel logo = new JLabel(new ImageIcon("images\\tanie_wypozyczanie_logo.png"));
        nowyKlient.setBounds(100, 100, 900, 700);
        nowyKlient.setAlwaysOnTop(true);
        nowyKlient.setResizable(false);

        naglowek.setBounds(50, 25, 500, 100);
        naglowek.setFont(new Font("SansSerif", Font.BOLD, 25));
        logo.setBounds(650, 0, 200, 130);

        JTextArea pesel = new JTextArea("PESEL");
        JTextArea imie = new JTextArea("Imię");
        JTextArea nazwisko = new JTextArea("Nazwisko");
        JTextArea numerTelefonu = new JTextArea("Numer telefonu");
        JTextArea email = new JTextArea("Email");
        JTextArea nrPrawaJazdy = new JTextArea("Nr. Prawa jazdy");
        JTextArea nrDowodu = new JTextArea("Nr. Dowodu osobistego");
        JButton przyciskWroc = new JButton("Wróć");
        JButton przyciskDalej = new JButton("Dalej");
        JLabel debug = new JLabel("");

        imie.setBounds(250, 200, 200, 50);
        nazwisko.setBounds(460, 200, 200, 50);
        numerTelefonu.setBounds(250, 260, 200, 50);
        email.setBounds(250, 320, 200, 50);
        pesel.setBounds(460, 260, 200, 50);
        nrPrawaJazdy.setBounds(460, 320, 200, 50);
        nrDowodu.setBounds(350, 380, 200, 50);
        przyciskWroc.setBounds(250, 460, 200, 50);
        przyciskDalej.setBounds(460, 460, 200, 50);

        imie.setBorder(BorderFactory.createLineBorder(Color.black));
        nazwisko.setBorder(BorderFactory.createLineBorder(Color.black));
        email.setBorder(BorderFactory.createLineBorder(Color.black));
        pesel.setBorder(BorderFactory.createLineBorder(Color.black));
        numerTelefonu.setBorder(BorderFactory.createLineBorder(Color.black));
        nrPrawaJazdy.setBorder(BorderFactory.createLineBorder(Color.black));
        nrDowodu.setBorder(BorderFactory.createLineBorder(Color.black));

        przyciskWroc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nowyKlient.setVisible(false);
                menuGlowne();
            }
        });
        przyciskDalej.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String imieVal = imie.getText();
                String nazwiskoVal = nazwisko.getText();
                String numerTelVal = numerTelefonu.getText();
                String emailVal = email.getText();
                String peselVal = pesel.getText();
                String nrPrawkaVal = nrPrawaJazdy.getText();
                String nrDowoduVal = nrDowodu.getText();
                Osoba.dodajKlienta(imieVal, nazwiskoVal, numerTelVal, emailVal, peselVal, nrPrawkaVal, nrDowoduVal);
                nowyKlient.setVisible(false);
                menuGlowne();
            }
        });


        nowyKlient.getContentPane().add(naglowek);
        nowyKlient.getContentPane().add(numerTelefonu);
        nowyKlient.getContentPane().add(nazwisko);
        nowyKlient.getContentPane().add(imie);
        nowyKlient.getContentPane().add(pesel);
        nowyKlient.getContentPane().add(logo);
        nowyKlient.getContentPane().add(email);
        nowyKlient.getContentPane().add(nrDowodu);
        nowyKlient.getContentPane().add(nrPrawaJazdy);
        nowyKlient.getContentPane().add(przyciskWroc);
        nowyKlient.getContentPane().add(przyciskDalej);
        nowyKlient.getContentPane().add(debug);
        nowyKlient.setVisible(true);
    }

    /**
     * Okno odpowiedzialne za wyświetlanie i filtrowanie wszystkich osób w systemie
     */
    private void wszystkieOsoby()   {
        JFrame wszystkieOsoby = new JFrame("Wszystkie osoby");
        JLabel naglowek = new JLabel("Tanie wypożyczanie - osoby");
        JLabel logo = new JLabel(new ImageIcon("images\\tanie_wypozyczanie_logo.png"));
        wszystkieOsoby.setBounds(100, 100, 900, 700);
        wszystkieOsoby.setLayout(null);
        wszystkieOsoby.setAlwaysOnTop(true);
        wszystkieOsoby.setResizable(false);

        naglowek.setBounds(50, 25, 500, 100);
        naglowek.setFont(new Font("SansSerif", Font.BOLD, 25));
        logo.setBounds(650, 0, 200, 130);
        JTextArea listaPracownikow = new JTextArea();
        listaPracownikow.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(listaPracownikow);
        JButton przyciskWszyscy = new JButton("Wszyscy");
        JButton przyciskPracownicy = new JButton("Pracownicy");
        JButton przyciskKlienci = new JButton("Klienci");
        JButton przyciskWroc = new JButton("Wróć");

        przyciskWszyscy.setBounds(10, 500, 200, 50);
        przyciskPracownicy.setBounds(220, 500, 200, 50);
        przyciskKlienci.setBounds(430, 500, 200, 50);
        przyciskWroc.setBounds(640, 500, 200, 50);
        

        int jump = 20;
        for(models.Osoba o : Osoba.getExtent())  {
            JLabel label = new JLabel(o.getImie() + " " + o.getNazwisko() + " " + o.stanowisko());
            label.setBounds(0, jump, 700, 20);
            jump = jump+20;
            listaPracownikow.add(label);
        }

        przyciskWroc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                wszystkieOsoby.setVisible(false);
                menuGlowne();
            }
        });

        przyciskWszyscy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listaPracownikow.removeAll();
                int jump = 20;
                for(models.Osoba o : Osoba.getExtent())  {
                    JLabel label = new JLabel(o.getImie() + " " + o.getNazwisko() + " " + o.stanowisko());
                    label.setBounds(0, jump, 700, 20);
                    jump = jump+20;
                    listaPracownikow.add(label);
                }
                scrollPane.getViewport().setView(listaPracownikow);
            }
        });

        przyciskPracownicy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listaPracownikow.removeAll();
                int jump = 20;
                for(models.Osoba o : Osoba.getExtent())  {
                        if(o.stanowisko().contains("Pracownik"))   {
                            JLabel label = new JLabel(o.getImie() + " " + o.getNazwisko() + " " + o.stanowisko());
                            label.setBounds(0, jump, 700, 20);
                            jump = jump+20;
                            listaPracownikow.add(label);
                        }
                    }
                scrollPane.getViewport().setView(listaPracownikow);
            }
        });

        przyciskKlienci.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listaPracownikow.removeAll();
                int jump = 20;
                for(models.Osoba o : Osoba.getExtent())  {
                        if(o.stanowisko().contains("Klient"))   {
                            JLabel label = new JLabel(o.getImie() + " " + o.getNazwisko() + " " + o.stanowisko());
                            label.setBounds(0, jump, 700, 20);
                            jump = jump+20;
                            listaPracownikow.add(label);
                        }
                    }
                scrollPane.getViewport().setView(listaPracownikow);
            }
        });

        scrollPane.getViewport().setView(listaPracownikow);
        scrollPane.setBounds(100, 150, 700, 300);

        wszystkieOsoby.getContentPane().add(naglowek);
        wszystkieOsoby.getContentPane().add(naglowek);
        wszystkieOsoby.getContentPane().add(scrollPane);
        wszystkieOsoby.getContentPane().add(przyciskWszyscy);
        wszystkieOsoby.getContentPane().add(przyciskPracownicy);
        wszystkieOsoby.getContentPane().add(przyciskKlienci);
        wszystkieOsoby.getContentPane().add(przyciskWroc);
        wszystkieOsoby.setVisible(true);
    }

    /**
     * Metoda odpowiedzialna za wyświetlenie wszystkich premii przysługujących pracownikom
     */
    public void obliczaniePremii()  {
        JFrame wszystkieOsoby = new JFrame("Oblicaznie premii");
        JLabel naglowek = new JLabel("Tanie wypożyczanie - premie");
        JLabel logo = new JLabel(new ImageIcon("images\\tanie_wypozyczanie_logo.png"));
        wszystkieOsoby.setBounds(100, 100, 900, 700);
        wszystkieOsoby.setLayout(null);
        wszystkieOsoby.setAlwaysOnTop(true);
        wszystkieOsoby.setResizable(false);

        naglowek.setBounds(50, 25, 500, 100);
        naglowek.setFont(new Font("SansSerif", Font.BOLD, 25));
        logo.setBounds(650, 0, 200, 130);
        JTextArea listaPracownikow = new JTextArea();
        listaPracownikow.setEditable(false);
        JButton przyciskWroc = new JButton("Wróć");
        JScrollPane scrollPane = new JScrollPane(listaPracownikow);

        przyciskWroc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                wszystkieOsoby.setVisible(false);
                menuGlowne();
            }
        });
        try {
            int jump = 20;
            for(models.Osoba pracownik : Osoba.getExtent())  {
                double kwotaPremii = 0;
                if (pracownik.czyPracownik())   {
                    if(pracownik.czyPracownikMyjni())   {
                        kwotaPremii = kwotaPremii + pracownik.getPremiaMyjacySamochody();
                    }
                    if(pracownik.czyPracownikKoordynator()) {
                        kwotaPremii = kwotaPremii + pracownik.getPremiaKoordynator();
                    }
                    if(pracownik.czyPracownikKierowca())    {
                        kwotaPremii = kwotaPremii + pracownik.getPremiaKierowca();
                    }
                    JLabel label = new JLabel(pracownik.getImie() + " " + pracownik.getNazwisko() + " : " + kwotaPremii);
                    label.setBounds(0, jump, 700, 20);
                    jump = jump+20;
                    listaPracownikow.add(label);
                }
            }
        }
        catch(Exception e)  {
            e.printStackTrace();
        }

        scrollPane.getViewport().setView(listaPracownikow);
        scrollPane.setBounds(100, 150, 700, 300);

        przyciskWroc.setBounds(100, 500, 200, 50);

        wszystkieOsoby.getContentPane().add(przyciskWroc);
        wszystkieOsoby.getContentPane().add(naglowek);
        wszystkieOsoby.getContentPane().add(logo);
        wszystkieOsoby.getContentPane().add(scrollPane);
        wszystkieOsoby.setVisible(true);
    }

    /**
     * Wyświetlenie okna odpowiadającego za stworzenie nowego pracownika
     */
    public void stworzPracownika()  {
        JFrame nowyPracownik = new JFrame("Nowy pracownik");
        JLabel naglowek = new JLabel("Tanie wypożyczanie - nowy pracownik");
        JLabel logo = new JLabel(new ImageIcon("images\\tanie_wypozyczanie_logo.png"));
        nowyPracownik.setBounds(100, 100, 900, 700);
        nowyPracownik.setAlwaysOnTop(true);
        nowyPracownik.setResizable(false);

        naglowek.setBounds(50, 25, 500, 100);
        naglowek.setFont(new Font("SansSerif", Font.BOLD, 25));
        logo.setBounds(650, 0, 200, 130);

        JTextArea pesel = new JTextArea("PESEL");
        JTextArea imie = new JTextArea("Imię");
        JTextArea nazwisko = new JTextArea("Nazwisko");
        JTextArea numerTelefonu = new JTextArea("Numer telefonu");
        JTextArea email = new JTextArea("Email");
        JTextArea miasto = new JTextArea("Miasto");
        JButton przyciskWroc = new JButton("Wróć");
        JButton przyciskDalej = new JButton("Dalej");
        JLabel debug = new JLabel("");

        imie.setBounds(250, 200, 200, 50);
        nazwisko.setBounds(460, 200, 200, 50);
        numerTelefonu.setBounds(250, 260, 200, 50);
        email.setBounds(250, 320, 200, 50);
        miasto.setBounds(460, 320, 200, 50);
        pesel.setBounds(460, 260, 200, 50);
        przyciskWroc.setBounds(250, 400, 200, 50);
        przyciskDalej.setBounds(460, 400, 200, 50);

        imie.setBorder(BorderFactory.createLineBorder(Color.black));
        nazwisko.setBorder(BorderFactory.createLineBorder(Color.black));
        email.setBorder(BorderFactory.createLineBorder(Color.black));
        pesel.setBorder(BorderFactory.createLineBorder(Color.black));
        numerTelefonu.setBorder(BorderFactory.createLineBorder(Color.black));
        miasto.setBorder(BorderFactory.createLineBorder(Color.black));

        przyciskWroc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nowyPracownik.setVisible(false);
                menuGlowne();
            }
        });
        przyciskDalej.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String imieVal = imie.getText();
                String nazwiskoVal = nazwisko.getText();
                String numerTelVal = numerTelefonu.getText();
                String emailVal = email.getText();
                String peselVal = pesel.getText();
                String miastoVal = miasto.getText();
                Osoba.dodajPracownika(imieVal, nazwiskoVal, numerTelVal, emailVal, peselVal, miastoVal);
                nowyPracownik.setVisible(false);
                menuGlowne();
            }
        });


        nowyPracownik.getContentPane().add(naglowek);
        nowyPracownik.getContentPane().add(numerTelefonu);
        nowyPracownik.getContentPane().add(nazwisko);
        nowyPracownik.getContentPane().add(imie);
        nowyPracownik.getContentPane().add(pesel);
        nowyPracownik.getContentPane().add(logo);
        nowyPracownik.getContentPane().add(email);
        nowyPracownik.getContentPane().add(przyciskWroc);
        nowyPracownik.getContentPane().add(przyciskDalej);
        nowyPracownik.getContentPane().add(miasto);
        nowyPracownik.getContentPane().add(debug);
        nowyPracownik.setVisible(true);
    }

    /**
     * Wyświetlenie okna odpowiadającego za zmianę uprawnień pracownika
     */
    public void zmienUprawnienia()  {
        JFrame uprawnienia = new JFrame("Uprawnienia");
        JLabel naglowek = new JLabel("Tanie wypożyczanie - uprawnienia");
        JLabel logo = new JLabel(new ImageIcon("images\\tanie_wypozyczanie_logo.png"));
        JButton przyciskWroc = new JButton("Wróć");
        JButton przyciskZatwierdz = new JButton("Zatwierdź");
        JScrollPane scrollPane;
        JCheckBox klientBox = new JCheckBox("Klient", false);
        JCheckBox koordynatorBox = new JCheckBox("Koordynator", false);
        JCheckBox myjniaBox = new JCheckBox("Myjący pojazdy", false);
        JCheckBox kierowcaBox = new JCheckBox("Kierowca", false);
        JCheckBox pracownikBox = new JCheckBox("Pracownik", false);



        przyciskWroc.setBounds(50, 600, 200, 50);
        przyciskZatwierdz.setBounds(260, 600, 200, 50);

        naglowek.setBounds(50, 25, 500, 100);
        naglowek.setFont(new Font("SansSerif", Font.BOLD, 25));

        logo.setBounds(650, 0, 200, 130);

        uprawnienia.setBounds(100, 100, 900, 700);
        uprawnienia.setLayout(null);
        uprawnienia.setAlwaysOnTop(true);

        List<models.Osoba> osoby = Osoba.pobierzOsoby();
        String[] osobyTablica = new String[osoby.size()];

        JList listaOsob = new JList<String>(osobyTablica);
        scrollPane = new JScrollPane(listaOsob);

        klientBox.setBounds(460, 150, 200, 50);
        koordynatorBox.setBounds(460, 210, 200, 50);
        myjniaBox.setBounds(460, 270, 200, 50);
        kierowcaBox.setBounds(460, 330, 200, 50);
        pracownikBox.setBounds(460, 390, 200, 50);
        


        int iterator = 0;
        for(models.Osoba o : osoby) {
            StringBuilder osoba = new StringBuilder();
            osoba.append(o.getImie()).append(",").append(o.getNazwisko()).append(",").append(o.getPesel()).append(",(").append(o.stanowisko()).append(")").append(",").append(iterator);
            osobyTablica[iterator] = osoba.toString();
            iterator++;
        }
        listaOsob.setBounds(50, 150, 400, 300);
        scrollPane.getViewport().setView(listaOsob);
        scrollPane.setBounds(50, 150, 400, 300);


        przyciskWroc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uprawnienia.setVisible(false);
                pokazMenuGlowne();
            }
        });

        przyciskZatwierdz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] daneOsoby = listaOsob.getSelectedValue().toString().split(",");
                models.Osoba o = models.Osoba.pobierzOsobePoPeselu(daneOsoby[2]);
                boolean kierowcaFlag = kierowcaBox.isSelected();
                boolean klientFlag = klientBox.isSelected();
                boolean koordynatorFlag = koordynatorBox.isSelected();
                boolean pracownikFlag = pracownikBox.isSelected();
                boolean myjniaFlag = myjniaBox.isSelected();
                if(o != null) {
                    uprawnienia.setVisible(false);
                    zmianaUprawnienPodsumowanie(o, kierowcaFlag, klientFlag, koordynatorFlag, pracownikFlag, myjniaFlag, daneOsoby[4]);
                }
                //pokazMenuGlowne();
            }
        });

        uprawnienia.getContentPane().add(klientBox);
        uprawnienia.getContentPane().add(koordynatorBox);
        uprawnienia.getContentPane().add(kierowcaBox);
        uprawnienia.getContentPane().add(pracownikBox);
        uprawnienia.getContentPane().add(myjniaBox);
        uprawnienia.getContentPane().add(przyciskWroc);
        uprawnienia.getContentPane().add(logo);
        uprawnienia.getContentPane().add(naglowek);
        uprawnienia.getContentPane().add(scrollPane);
        uprawnienia.getContentPane().add(przyciskZatwierdz);
        uprawnienia.setResizable(false);
        uprawnienia.setVisible(true);
    }

    /**
     * Drugi krok zmiany uprawnień
     * @param osoba obiekt osoby, której uprawnienia są modyfikowane
     * @param kierowcaFlag modyfikator uprawnienia
     * @param klientFlag modyfikator uprawnienia
     * @param koordynatorFlag modyfikator uprawniania
     * @param pracownikFlag modyfikator uprawneinia
     * @param myjniaFlag modyfikator uprawnienia
     * @param id numer osoby z listy 
     */
    private void zmianaUprawnienPodsumowanie(models.Osoba osoba, boolean kierowcaFlag, boolean klientFlag, boolean koordynatorFlag, boolean pracownikFlag, boolean myjniaFlag, String id)  {
        JFrame uprawnienia = new JFrame("Uprawnienia");
        JLabel naglowek = new JLabel("Tanie wypożyczanie - uprawnienia");
        JLabel logo = new JLabel(new ImageIcon("images\\tanie_wypozyczanie_logo.png"));
        JTextArea dodawanie = new JTextArea();
        JButton zatwierdz = new JButton("Zatwierdź");
        dodawanie.setBounds(10, 100, 400, 150);
        dodawanie.setEditable(false);

        naglowek.setBounds(50, 25, 500, 100);
        naglowek.setFont(new Font("SansSerif", Font.BOLD, 25));

        logo.setBounds(650, 0, 200, 130);

        uprawnienia.setBounds(100, 100, 900, 700);
        uprawnienia.setLayout(null);
        uprawnienia.setAlwaysOnTop(true);

        zatwierdz.setBounds(550, 300, 200, 50);


        int dodawaniePozycja = 20;
        //Sprawdzanie flag
        JLabel dodawanieLabel = new JLabel("Dodawanie:");
        dodawanieLabel.setBounds(0, 10, 200, 20);
        dodawanie.add(dodawanieLabel);
        if(kierowcaFlag)    {
            JLabel kierowca = new JLabel("Kierowca");
            kierowca.setBounds(0, dodawaniePozycja, 200, 20);
            dodawanie.add(kierowca);
            dodawaniePozycja += 10;
        }
        if(klientFlag)  {
            JLabel klient = new JLabel("Klient");
            klient.setBounds(0, dodawaniePozycja, 200, 20);
            dodawanie.add(klient);
            dodawaniePozycja += 10;
        }
        if(koordynatorFlag)  {
            JLabel koordynator = new JLabel("Koordynator");
            koordynator.setBounds(0, dodawaniePozycja, 200, 20);
            dodawanie.add(koordynator);
            dodawaniePozycja += 10;
        }
        if(pracownikFlag)  {
            JLabel pracownik = new JLabel("Pracownik");
            pracownik.setBounds(0, dodawaniePozycja, 200, 20);
            dodawanie.add(pracownik);
            dodawaniePozycja += 10;
        }
        if(myjniaFlag)  {
            JLabel myjnia = new JLabel("Pracownik myjni");
            myjnia.setBounds(0, dodawaniePozycja, 200, 20);
            dodawanie.add(myjnia);
            dodawaniePozycja += 10;
        }

        JLabel usuwanieLabel = new JLabel("Usuwanie:");
        usuwanieLabel.setBounds(210, 10, 200, 20);
        dodawanie.add(usuwanieLabel);
        dodawaniePozycja = 20;

        if(!kierowcaFlag)    {
            JLabel kierowca = new JLabel("Kierowca");
            kierowca.setBounds(210, dodawaniePozycja, 200, 20);
            dodawanie.add(kierowca);
            dodawaniePozycja += 10;
        }
        if(!klientFlag)  {
            JLabel klient = new JLabel("Klient");
            klient.setBounds(210, dodawaniePozycja, 200, 20);
            dodawanie.add(klient);
            dodawaniePozycja += 10;
        }
        if(!koordynatorFlag)  {
            JLabel koordynator = new JLabel("Koordynator");
            koordynator.setBounds(210, dodawaniePozycja, 200, 20);
            dodawanie.add(koordynator);
            dodawaniePozycja += 10;
        }
        if(!pracownikFlag)  {
            JLabel pracownik = new JLabel("Pracownik");
            pracownik.setBounds(210, dodawaniePozycja, 200, 20);
            dodawanie.add(pracownik);
            dodawaniePozycja += 10;
        }
        if(!myjniaFlag)  {
            JLabel myjnia = new JLabel("Pracownik myjni");
            myjnia.setBounds(210, dodawaniePozycja, 200, 20);
            dodawanie.add(myjnia);
            dodawaniePozycja += 10;
        }
        //Dodawanie pól do wpisania danych
        JTextArea prawoJazdy = new JTextArea("Numer prawa jazdy");
        JTextArea dowodOsobisty = new JTextArea("Numer dowodu osobistego");
        JTextArea miasto = new JTextArea("Miasto pracownika");
        int start = 300;
        if(klientFlag)  {
            prawoJazdy.setBounds(50, start, 200, 50);
            dowodOsobisty.setBounds(50, start+60, 200, 50);
            dowodOsobisty.setBorder(BorderFactory.createLineBorder(Color.black));
            prawoJazdy.setBorder(BorderFactory.createLineBorder(Color.black));
            start = start+120;
            uprawnienia.getContentPane().add(prawoJazdy);
            uprawnienia.getContentPane().add(dowodOsobisty);
        }
        if(pracownikFlag || myjniaFlag || koordynatorFlag || kierowcaFlag)  {
            miasto.setBounds(50, start, 200, 50);
            miasto.setBorder(BorderFactory.createLineBorder(Color.black));
            uprawnienia.getContentPane().add(miasto);
        }

        zatwierdz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String stanowiska = osoba.stanowisko();
                        if(pracownikFlag && !stanowiska.contains("Pracownik"))   {
                            Osoba.getExtent().get(Integer.parseInt(id)).ustawDanePracownika(miasto.getText());
                            Osoba.getExtent().get(Integer.parseInt(id)).ustawPracownik(true);
                            Dane.zapiszDane();
                            stanowiska = osoba.stanowisko();
                        }
                    if(myjniaFlag)  {
                        Osoba.getExtent().get(Integer.parseInt(id)).ustawDanePracownika(miasto.getText());
                        if(!stanowiska.contains("Pracownik"))   {
                            Osoba.getExtent().get(Integer.parseInt(id)).ustawPracownik(true);
                            Dane.zapiszDane();
                            stanowiska = osoba.stanowisko();
                        }
                        if(!stanowiska.contains("samochody"))   {
                            Osoba.getExtent().get(Integer.parseInt(id)).ustawPracownikMyjni(true);
                            Dane.zapiszDane();
                            stanowiska = osoba.stanowisko();
                        }
                    }
                    if(koordynatorFlag) {
                        Osoba.getExtent().get(Integer.parseInt(id)).ustawDanePracownika(miasto.getText());
                        if(!stanowiska.contains("Pracownik"))   {
                            Osoba.getExtent().get(Integer.parseInt(id)).ustawPracownik(true);
                            Dane.zapiszDane();
                            stanowiska = osoba.stanowisko();
                        }
                        if(!stanowiska.contains("Koordynator"))   {
                            Osoba.getExtent().get(Integer.parseInt(id)).ustawPracownikKoordynator(true);
                            Dane.zapiszDane();
                            stanowiska = osoba.stanowisko();
                        }
                    }
                    if(kierowcaFlag)    {
                        Osoba.getExtent().get(Integer.parseInt(id)).ustawDanePracownika(miasto.getText());
                        if(!stanowiska.contains("Pracownik"))   {
                            Osoba.getExtent().get(Integer.parseInt(id)).ustawPracownik(true);
                            Dane.zapiszDane();
                            stanowiska = osoba.stanowisko();
                        }
                        if(!stanowiska.contains("Kierowca"))   {
                            Osoba.getExtent().get(Integer.parseInt(id)).ustawPracownikKierowca(true);
                            Dane.zapiszDane();
                            stanowiska = osoba.stanowisko();
                        }
                    }
                    if(klientFlag)  {
                        Osoba.getExtent().get(Integer.parseInt(id)).ustawDaneKlienta(prawoJazdy.getText(), dowodOsobisty.getText());
                        if(!stanowiska.contains("Klient"))   {
                            Osoba.getExtent().get(Integer.parseInt(id)).ustawKlient(true);
                            Dane.zapiszDane();
                            stanowiska = osoba.stanowisko();
                        }
                    }
                    //Usuwanie
                    if(!myjniaFlag) {
                        if(stanowiska.strip().contains("samochody")) {
                            Osoba.getExtent().get(Integer.parseInt(id)).ustawPracownikMyjni(false);
                            Dane.zapiszDane();
                            stanowiska = osoba.stanowisko();
                        }
                    }
                    if(!koordynatorFlag) {
                        if(stanowiska.contains("Koordynator")) {
                            Osoba.getExtent().get(Integer.parseInt(id)).ustawPracownikKoordynator(false);
                            Dane.zapiszDane();
                            stanowiska = osoba.stanowisko();
                        }
                    }
                    if(!kierowcaFlag) {
                        if(stanowiska.contains("Kierowca")) {
                            Osoba.getExtent().get(Integer.parseInt(id)).ustawPracownikKierowca(false);
                            Dane.zapiszDane();
                            stanowiska = osoba.stanowisko();
                        }
                    }
                    if(!klientFlag) {
                        if(stanowiska.contains("Klient")) {
                            Osoba.getExtent().get(Integer.parseInt(id)).ustawKlient(false);
                            Dane.zapiszDane();
                            stanowiska = osoba.stanowisko();
                        }
                    }
                    if(!pracownikFlag && !kierowcaFlag && !koordynatorFlag && !myjniaFlag) {
                        if(stanowiska.contains("Kierowca")) {
                            Osoba.getExtent().get(Integer.parseInt(id)).ustawPracownikKierowca(false);
                            Dane.zapiszDane();
                            stanowiska = osoba.stanowisko();
                        }
                        if(stanowiska.contains("Koordynator")) {
                            Osoba.getExtent().get(Integer.parseInt(id)).ustawPracownikKoordynator(false);
                            Dane.zapiszDane();
                            stanowiska = osoba.stanowisko();
                        }
                        if(stanowiska.contains("samochody")) {
                            Osoba.getExtent().get(Integer.parseInt(id)).ustawPracownikMyjni(false);
                            Dane.zapiszDane();
                            stanowiska = osoba.stanowisko();
                        }
                        if(stanowiska.contains("Pracownik")) {
                            Osoba.getExtent().get(Integer.parseInt(id)).ustawPracownik(false);
                            Dane.zapiszDane();
                            stanowiska = osoba.stanowisko();
                        }
                    }
                    Dane.zapiszDane();
                }   catch(Exception e1)  {
                    e1.printStackTrace();
                }
            uprawnienia.setVisible(false);
            zmienUprawnienia();
            }
        });
        uprawnienia.getContentPane().add(zatwierdz);
        uprawnienia.getContentPane().add(logo);
        uprawnienia.getContentPane().add(naglowek);
        uprawnienia.getContentPane().add(dodawanie);
        uprawnienia.setVisible(true);
    }
}

