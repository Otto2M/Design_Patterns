package org.foi.uzdiz.omilermat.zadaca_2.datoteke;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.foi.uzdiz.omilermat.zadaca_2.composite.Podrucje;

public class UcitajPodrucjaCSV implements CSVLoader<Podrucje> {

  public List<Podrucje> listaPodrucja = new ArrayList<>();
  boolean ispravno = true;

  @Override
  public List<Podrucje> ucitajPodatkeCSV(String CSVDatoteka) {

    try (BufferedReader br = new BufferedReader(new FileReader(CSVDatoteka))) {
      String red = br.readLine();
      boolean porukaIspisana = false;

      while ((red = br.readLine()) != null) {
        String[] atributi = red.split(";");

        if (atributi.length != 2) {
          if (!porukaIspisana) {
            System.out.println("Redak ne sadrži 2 argumenata: " + red);
            System.out.println("Preskačem red! ");
            porukaIspisana = true;
          }
          continue;
        } else {

          provjeriID(atributi[0], red);
          provjeriZapis(atributi[1], red);

        }

        if (ispravno) {
          Podrucje podrucje = kreirajPodrucje(atributi);
          listaPodrucja.add(podrucje);
        }
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
    return listaPodrucja;
  }

  private Podrucje kreirajPodrucje(String[] atribut) {

    int id = Integer.parseInt(atribut[0].trim());
    String gradUlica = atribut[1].trim();

    return new Podrucje(id, gradUlica);
  }

  public void provjeriID(String id, String cijeliRedak) {
    try {
      Integer.parseInt(id);
      ispravno = true;
    } catch (NumberFormatException e) {
      System.out.println("Pogreška u ID stupcu: ID mora biti cijeli broj. Redak: " + cijeliRedak);
      ispravno = false;
    }
  }

  public void provjeriZapis(String ulicaGrad, String red) {
    if (ulicaGrad.equals("")) {
      System.out.println("Vrijednost ulica:grad nije unešena! Sadržaj retka: " + red
          + " Red ne sadrži vrijednost ulica:grad.");
      ispravno = false;
    }
    // else {
    // String regex = "^\\s?\\d:\\d{1,2}(,\\d:\\d{1,2}|\\d:\\*)*$";
    // if (!ulicaGrad.matches(regex)) {
    // System.out.println("Nedozvoljeni znakovi kod atributa ulica:grad! Sadržaj retka: " + red);
    // ispravno = false;
    // }
    // }
  }

}
