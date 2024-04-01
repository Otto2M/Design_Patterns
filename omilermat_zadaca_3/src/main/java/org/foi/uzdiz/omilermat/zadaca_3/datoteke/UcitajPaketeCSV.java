package org.foi.uzdiz.omilermat.zadaca_3.datoteke;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.foi.uzdiz.omilermat.zadaca_3.observer.Paket;
import org.foi.uzdiz.omilermat.zadaca_3.singleton.BrojacGresaka;

public class UcitajPaketeCSV implements CSVLoader<Paket> {

  public static final DateFormat FORMAT_DATUMA = new SimpleDateFormat("dd.MM.yyyy. HH:mm:ss");
  private static final Set<String> DOPUSTENI_TIPOVI_PAKETA =
      new HashSet<>(Arrays.asList("A", "B", "C", "D", "E", "X"));
  private static final Set<String> DOPUSTENE_USLUGE_DOSTAVE =
      new HashSet<>(Arrays.asList("S", "H", "P", "R"));

  public List<Paket> listaPaketa = new ArrayList<>();
  private List<VrstaPaketa> vrstaPaketa;
  private BrojacGresaka rbrPogreske;
  boolean ispravno = true;

  public UcitajPaketeCSV(BrojacGresaka rbrPogreske, List<VrstaPaketa> vrstaPaketa) {
    this.rbrPogreske = rbrPogreske;
    this.vrstaPaketa = vrstaPaketa;
  }

  @Override
  public List<Paket> ucitajPodatkeCSV(String CSVDatoteka) {

    try (BufferedReader br = new BufferedReader(new FileReader(CSVDatoteka))) {
      String red = br.readLine();
      int brojLinije = 0;

      while ((red = br.readLine()) != null) {
        brojLinije++;
        String[] atributi = red.split(";");

        if (atributi.length != 11) {
          System.out.println("Pogreška broj " + BrojacGresaka.getInstance().dohvatiVrijednost()
              + " - Redak ne sadrži 11 argumenata: " + red);
          System.out.println("Preskačem red! ");
          BrojacGresaka.getInstance().increment();
          continue;
        }

        provjeriOznaku(atributi[0], red);
        provjeriDatum(atributi[1], red);
        provjeriPosiljatelja(atributi[2], red);
        provjeriPrimatelja(atributi[3], red);
        provjeriVrstuPaketa(atributi[4], red);
        provjeriVisinu(atributi[5], atributi[4], red);
        provjeriSirinu(atributi[6], atributi[4], red);
        provjeriDuzinu(atributi[7], atributi[4], red);
        provjeriUsluguDostave(atributi[9], red);
        provjeriIznosPouzeća(atributi[10], atributi[9], red);

        if (ispravno) {
          Paket paket = kreirajPaket(atributi, brojLinije);
          listaPaketa.add(paket);
        }
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
    listaPaketa.sort(Comparator.comparing(Paket::getVrijemePrijema));
    return listaPaketa;
  }

  private Paket kreirajPaket(String[] atribut, int brojLinije) {

    Double visinaPaketa = 0.0;
    Double sirinaPaketa = 0.0;
    Double duzinaPaketa = 0.0;
    Double iznosDostave = 0.0;
    for (VrstaPaketa vrsta : vrstaPaketa) {
      if (atribut[4].equals(vrsta.getOznaka())) {
        visinaPaketa = vrsta.getVisina();
        sirinaPaketa = vrsta.getSirina();
        duzinaPaketa = vrsta.getDuzina();
        if (atribut[9].equals("H")) {
          iznosDostave = vrsta.getCijenaHitno();
        } else if (atribut[4].equals("X")) {
          iznosDostave = vrsta.getCijena()
              + (vrsta.getCijenaP() * (Double.parseDouble(atribut[5].replace(",", "."))
                  * Double.parseDouble(atribut[6].replace(",", "."))
                  * Double.parseDouble(atribut[7].replace(",", "."))))
              + (vrsta.getCijenaT() * Double.parseDouble(atribut[8].replace(",", ".")));
        } else
          iznosDostave = vrsta.getCijena();
      }
    }

    String oznaka = atribut[0];
    Date vrijemePrijema = parsirajDatum(atribut[1]);
    String posiljatelj = atribut[2];
    String primatelj = atribut[3];
    String vrstaPaketa = atribut[4];
    double visina = provjeriVrijednost(visinaPaketa, "visina");
    double sirina = provjeriVrijednost(sirinaPaketa, "sirina");
    double duzina = provjeriVrijednost(duzinaPaketa, "duzina");
    double tezina = Double.parseDouble(atribut[8].replace(",", "."));
    String uslugaDostave = atribut[9];
    double iznosPouzeća = Double.parseDouble(atribut[10].replace(",", "."));

    if (!DOPUSTENI_TIPOVI_PAKETA.contains(vrstaPaketa)) {
      System.out.println("Pogrešan tip paketa: " + vrstaPaketa + " --> linija br. = " + brojLinije);
    } else if (!DOPUSTENE_USLUGE_DOSTAVE.contains(uslugaDostave)) {
      System.out.println(
          "Nepostojeća usluga dostave: " + uslugaDostave + " --> linija br. = " + brojLinije);
    } else {
      return new Paket(oznaka, vrijemePrijema, posiljatelj, primatelj, vrstaPaketa, visina, sirina,
          duzina, tezina, uslugaDostave, iznosPouzeća, iznosDostave);
    }
    return null;

  }

  private void provjeriIznosPouzeća(String iznosPouzeca, String usluga, String red) {
    Float iznosPouzecaFloat = Float.parseFloat(iznosPouzeca.replace(",", "."));
    if (iznosPouzeca.equals("")) {
      System.out.println("Pogreška broj " + BrojacGresaka.getInstance().dohvatiVrijednost()
          + " | Vrijednost iznos pouzeća nije unesena! Sadržaj retka: " + red
          + " Red ne sadrži vrijednost za iznos pouzeća.");
      BrojacGresaka.getInstance().increment();
      ispravno = false;
    } else {
      if (iznosPouzecaFloat != 0 && !usluga.equals("P")) {
        System.out.println("Pogreška broj " + BrojacGresaka.getInstance().dohvatiVrijednost()
            + " | Dozvoljeni iznos za atribut iznos pouzeća za tip usluge H, S i R je 0,0! Sadržaj retka: "
            + red);
        BrojacGresaka.getInstance().increment();
        ispravno = false;
      }
    }

  }

  private void provjeriOznaku(String oznaka, String red) {
    if (oznaka.equals("")) {
      System.out.println("Pogreška broj " + BrojacGresaka.getInstance().dohvatiVrijednost()
          + " | Vrijednost oznake paketa nije unesena! Sadržaj retka: " + red
          + " Red ne sadrži početni atribut oznaka.");
      BrojacGresaka.getInstance().increment();
      ispravno = false;
    }
  }

  private static Date parsirajDatum(String datum) {
    try {
      return UcitajPaketeCSV.FORMAT_DATUMA.parse(datum);
    } catch (ParseException e) {
      e.printStackTrace();
      return null;
    }
  }

  private double provjeriVrijednost(Double vrijednost, String poljeNaziv) {
    String vrijednostString = vrijednost.toString();
    try {
      double broj = Double.parseDouble(vrijednostString.replace(",", "."));
      if (broj < 0) {
        System.out.println("Pogreška broj " + BrojacGresaka.getInstance().dohvatiVrijednost()
            + " | Greška u formatu broja: Vrijednost '" + vrijednost
            + "' mora biti nenegativna u polju: " + poljeNaziv);
        BrojacGresaka.getInstance().increment();
      }
      return broj;
    } catch (NumberFormatException e) {
      System.out.println("Pogreška broj " + BrojacGresaka.getInstance().dohvatiVrijednost()
          + " | Greška u formatu broja: neispravan broj '" + vrijednost + "' u polju: "
          + poljeNaziv);
      BrojacGresaka.getInstance().increment();
    }
    return 0;
  }

  private void provjeriDatum(String datum, String red) {
    if (datum.equals("")) {
      System.out.println("Pogreška broj " + BrojacGresaka.getInstance().dohvatiVrijednost()
          + " | Vrijednost vrijeme prijema nije unesena! Sadržaj retka: " + red
          + " Red ne sadrži vrijednost vrijeme prijema.");
      BrojacGresaka.getInstance().increment();
      ispravno = false;
    } else {
      String regex = "(\\d{2}\\.\\d{2}\\.\\d{4}\\.) (\\d{2}\\:\\d{2}\\:\\d{2})";
      if (!datum.matches(regex)) {
        System.out.println("Pogreška broj " + BrojacGresaka.getInstance().dohvatiVrijednost()
            + " | Format datuma nije isapravan! Sadržaj retka: " + red);
        BrojacGresaka.getInstance().increment();
        ispravno = false;
      }
    }
  }

  private void provjeriPosiljatelja(String posiljatelj, String red) {
    if (posiljatelj.equals("")) {
      System.out.println("Pogreška broj " + BrojacGresaka.getInstance().dohvatiVrijednost()
          + " | Vrijednost posiljatelj (ime i prezime) nisu unešeni! Sadržaj retka: " + red
          + " Red ne sadrži vrijednost pošiljatelj.");
      BrojacGresaka.getInstance().increment();
      ispravno = false;
    } else {
      String regex = "^[a-žA-Ž.]+\\s[a-žA-Ž.]+$";
      if (!posiljatelj.matches(regex)) {
        System.out.println("Pogreška broj " + BrojacGresaka.getInstance().dohvatiVrijednost()
            + " | Nedozvoljeni znakovi u nazivu pošiljatelja! Sadržaj retka: " + red);
        BrojacGresaka.getInstance().increment();
        ispravno = false;
      }
    }
  }

  private void provjeriPrimatelja(String primatelj, String red) {
    if (primatelj.equals("")) {
      System.out.println("Pogreška broj " + BrojacGresaka.getInstance().dohvatiVrijednost()
          + " | Vrijednost primatelj (ime i prezime) nisu unešeni! Sadržaj retka: " + red
          + " Red ne sadrži vrijednost primatelj.");
      BrojacGresaka.getInstance().increment();
      ispravno = false;
    } else {
      String regex = "^[a-žA-Ž.]+\\s[a-žA-Ž.]+$";
      if (!primatelj.matches(regex)) {
        System.out.println("Pogreška broj " + BrojacGresaka.getInstance().dohvatiVrijednost()
            + " | Nedozvoljeni znakovi u nazivu primatelja! Sadržaj retka: " + red);
        BrojacGresaka.getInstance().increment();
        ispravno = false;
      }
    }
  }

  private void provjeriVrstuPaketa(String vrstaPaketa, String red) {
    if (vrstaPaketa.equals("")) {
      System.out.println("Pogreška broj " + BrojacGresaka.getInstance().dohvatiVrijednost()
          + " | Vrijednost vrsta paketa nije unesena! Sadržaj retka: " + red
          + " Red ne sadrži vrijednost vrsta paketa.");
      BrojacGresaka.getInstance().increment();
      ispravno = false;
    } else {
      String regex = "^[ABCDEX]$";
      if (!vrstaPaketa.matches(regex)) {
        System.out.println("Pogreška broj " + BrojacGresaka.getInstance().dohvatiVrijednost()
            + " | Nedozvoljeni znak za vrstu paketa! Sadržaj retka: " + red);
        BrojacGresaka.getInstance().increment();
        ispravno = false;
      }
    }
  }

  private void provjeriVisinu(String visina, String vrsta, String red) {
    if (visina.equals("")) {
      System.out.println("Pogreška broj " + BrojacGresaka.getInstance().dohvatiVrijednost()
          + " | Vrijednost visina nije unesena! Sadržaj retka: " + red
          + " Red ne sadrži vrijednost visina.");
      BrojacGresaka.getInstance().increment();
      ispravno = false;
    } else {
      String regex = "^0,0$";
      if (!visina.matches(regex) && !vrsta.equals("X")) {
        System.out.println("Pogreška broj " + BrojacGresaka.getInstance().dohvatiVrijednost()
            + " | Dozvoljena visina za pakete A, B, C, D i E je 0,0! Sadržaj retka: " + red);
        BrojacGresaka.getInstance().increment();
        ispravno = false;
      }
    }
  }

  private void provjeriSirinu(String sirina, String vrsta, String red) {
    if (sirina.equals("")) {
      System.out.println("Pogreška broj " + BrojacGresaka.getInstance().dohvatiVrijednost()
          + " | Vrijednost širina nije unesena! Sadržaj retka: " + red
          + " Red ne sadrži vrijednost širina.");
      BrojacGresaka.getInstance().increment();
      ispravno = false;
    } else {
      String regex = "^0,0$";
      if (!sirina.matches(regex) && !vrsta.equals("X")) {
        System.out.println("Pogreška broj " + BrojacGresaka.getInstance().dohvatiVrijednost()
            + " | Dozvoljena širina za pakete A, B, C, D i E je 0,0! Sadržaj retka: " + red);
        BrojacGresaka.getInstance().increment();
        ispravno = false;
      }
    }
  }

  private void provjeriDuzinu(String duzina, String vrsta, String red) {
    if (duzina.equals("")) {
      System.out.println("Pogreška broj " + BrojacGresaka.getInstance().dohvatiVrijednost()
          + " | Vrijednost dužina nije unesena! Sadržaj retka: " + red
          + " Red ne sadrži vrijednost dužina.");
      BrojacGresaka.getInstance().increment();
      ispravno = false;
    } else {
      String regex = "^0,0$";
      if (!duzina.matches(regex) && !vrsta.equals("X")) {
        System.out.println("Pogreška broj " + BrojacGresaka.getInstance().dohvatiVrijednost()
            + " | Dozvoljena dužina za pakete A, B, C, D i E je 0,0! Sadržaj retka: " + red);
        BrojacGresaka.getInstance().increment();
        ispravno = false;
      }
    }
  }

  private void provjeriUsluguDostave(String uslugaDostave, String red) {
    if (uslugaDostave.equals("")) {
      System.out.println("Pogreška broj " + BrojacGresaka.getInstance().dohvatiVrijednost()
          + " | Vrijednost usluga dostave nije unesena! Sadržaj retka: " + red
          + " Red ne sadrži vrijednost usluga dostave.");
      BrojacGresaka.getInstance().increment();
      ispravno = false;
    } else {
      String regex = "^[SHPR]$";
      if (!uslugaDostave.matches(regex)) {
        System.out.println("Pogreška broj " + BrojacGresaka.getInstance().dohvatiVrijednost()
            + " | Nedozvoljeni znak za uslugu dostave! Sadržaj retka: " + red);
        BrojacGresaka.getInstance().increment();
        ispravno = false;
      }
    }
  }

}
