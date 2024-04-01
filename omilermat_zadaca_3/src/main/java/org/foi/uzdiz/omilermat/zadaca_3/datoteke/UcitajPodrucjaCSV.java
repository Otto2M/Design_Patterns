package org.foi.uzdiz.omilermat.zadaca_3.datoteke;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.foi.uzdiz.omilermat.zadaca_3.composite.Podrucje;
import org.foi.uzdiz.omilermat.zadaca_3.singleton.BrojacGresaka;

public class UcitajPodrucjaCSV implements CSVLoader<Podrucje> {

  public List<Podrucje> listaPodrucja = new ArrayList<>();
  private BrojacGresaka rbrPogreske;
  boolean ispravno = true;

  public UcitajPodrucjaCSV(BrojacGresaka rbrPogreske) {
    this.rbrPogreske = rbrPogreske;
  }

  @Override
  public List<Podrucje> ucitajPodatkeCSV(String CSVDatoteka) {

    try (BufferedReader br = new BufferedReader(new FileReader(CSVDatoteka))) {
      String red = br.readLine();
      boolean porukaIspisana = false;

      while ((red = br.readLine()) != null) {
        String[] atributi = red.split(";");

        if (atributi.length != 2) {
          if (!porukaIspisana) {
            System.out.println("Pogreška broj " + BrojacGresaka.getInstance().dohvatiVrijednost()
                + " - Redak ne sadrži 2 argumenata: " + red);
            System.out.println("Preskačem red! ");
            BrojacGresaka.getInstance().increment();
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
      System.out.println("Pogreška broj " + BrojacGresaka.getInstance().dohvatiVrijednost()
          + " | Pogreška u ID stupcu: ID mora biti cijeli broj. Redak: " + cijeliRedak);
      BrojacGresaka.getInstance().increment();
      ispravno = false;
    }
  }

  public void provjeriZapis(String ulicaGrad, String red) {
    if (ulicaGrad.equals("")) {
      System.out.println("Pogreška broj " + BrojacGresaka.getInstance().dohvatiVrijednost()
          + " | Vrijednost ulica:grad nije unešena! Sadržaj retka: " + red
          + " Red ne sadrži vrijednost ulica:grad.");
      BrojacGresaka.getInstance().increment();
      ispravno = false;
    }
  }

}
