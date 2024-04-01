package org.foi.uzdiz.omilermat.zadaca_1.datoteke;

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

public class UcitajPaketeCSV extends AbstractCSVLoader<Paket> {

  public static final DateFormat FORMAT_DATUMA = new SimpleDateFormat("dd.MM.yyyy. HH:mm:ss");
  private static final Set<String> DOPUSTENI_TIPOVI_PAKETA =
      new HashSet<>(Arrays.asList("A", "B", "C", "D", "E", "X"));
  private static final Set<String> DOPUSTENE_USLUGE_DOSTAVE =
      new HashSet<>(Arrays.asList("S", "H", "P", "R"));

  private List<Paket> listaPaketa = new ArrayList<>();

  @Override
  public List<Paket> ucitajPodatkeCSV(String CSVDatoteka) {

    System.out.println("Ucitavam od proslijedene datoteke CSVDatoteka: " + CSVDatoteka);

    try (BufferedReader br = new BufferedReader(new FileReader(CSVDatoteka))) {
      String red = br.readLine();
      int brojLinije = 0;

      while ((red = br.readLine()) != null) {
        brojLinije++;
        String[] atributi = red.split(";");

        if (atributi.length != 11) {
          System.out.println("Redak ne sadrži 11 argumenata: " + red);
          System.out.println("Preskačem red! ");
          continue;
        }

        Paket paket = kreirajPaket(atributi, brojLinije);
        listaPaketa.add(paket);
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
    listaPaketa.sort(Comparator.comparing(Paket::getVrijemePrijema));
    return listaPaketa;
  }

  private Paket kreirajPaket(String[] atribut, int brojLinije) {
    String oznaka = atribut[0];
    Date vrijemePrijema = parsirajDatum(atribut[1]);
    String posiljatelj = atribut[2];
    String primatelj = atribut[3];
    String vrstaPaketa = atribut[4];
    double visina = provjeriVrijednost(atribut[5], "visina");
    double sirina = provjeriVrijednost(atribut[6], "sirina");
    double duzina = provjeriVrijednost(atribut[7], "duzina");
    double tezina = provjeriVrijednost(atribut[8], "tezina");
    String uslugaDostave = atribut[9];
    double iznosPouzeća = provjeriVrijednost(atribut[10], "iznosPouzeća");

    if (!DOPUSTENI_TIPOVI_PAKETA.contains(vrstaPaketa)) {
      System.out.println("Pogrešan tip paketa: " + vrstaPaketa + " --> linija br. = " + brojLinije);
    } else if (!DOPUSTENE_USLUGE_DOSTAVE.contains(uslugaDostave)) {
      System.out.println(
          "Nepostojeća usluga dostave: " + uslugaDostave + " --> linija br. = " + brojLinije);
    } else {
      return new Paket(oznaka, vrijemePrijema, posiljatelj, primatelj, vrstaPaketa, visina, sirina,
          duzina, tezina, uslugaDostave, iznosPouzeća);
    }
    return null;

  }

  private static double provjeriVrijednost(String vrijednost, String poljeNaziv) {
    if (!vrijednost.contains(",")) {
      throw new IllegalArgumentException("Greška u formatu broja: vrijednost '" + vrijednost
          + "' mora sadržavati zarez u polju: " + poljeNaziv);
    }
    try {
      double broj = Double.parseDouble(vrijednost.replace(",", "."));
      if (broj < 0) {
        throw new IllegalArgumentException("Greška u formatu broja: Vrijednost '" + vrijednost
            + "' mora biti nenegativna u polju: " + poljeNaziv);
      }
      return broj;
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(
          "Greška u formatu broja: neispravan broj '" + vrijednost + "' u polju: " + poljeNaziv);
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

}
