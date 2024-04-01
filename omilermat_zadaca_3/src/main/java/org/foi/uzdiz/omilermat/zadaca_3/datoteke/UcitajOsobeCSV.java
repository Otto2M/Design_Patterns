package org.foi.uzdiz.omilermat.zadaca_3.datoteke;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.foi.uzdiz.omilermat.zadaca_3.observer.Osoba;
import org.foi.uzdiz.omilermat.zadaca_3.singleton.BrojacGresaka;

public class UcitajOsobeCSV implements CSVLoader<Osoba> {
  public List<Osoba> listaOsoba = new ArrayList<>();
  private BrojacGresaka rbrPogreske;
  private boolean ispravno = true;

  public UcitajOsobeCSV(BrojacGresaka rbrPogreske) {
    this.rbrPogreske = rbrPogreske;
  }

  @Override
  public List<Osoba> ucitajPodatkeCSV(String CSVDatoteka) {

    try (BufferedReader br = new BufferedReader(new FileReader(CSVDatoteka))) {
      String red = br.readLine();
      boolean porukaIspisana = false;

      while ((red = br.readLine()) != null) {
        String[] atributi = red.split(";");

        if (atributi.length != 4) {
          if (!porukaIspisana) {
            System.out.println("Pogreška broj " + BrojacGresaka.getInstance().dohvatiVrijednost()
                + " - Redak ne sadrži 4 argumenata: " + red);
            System.out.println("Preskačem red! ");
            BrojacGresaka.getInstance().increment();
            porukaIspisana = true;
          }
          continue;
        } else {
          provjeriImePrezime(atributi[0], red);
          provjeriBroj(atributi[1], red);
          provjeriBroj(atributi[2], red);
          provjeriBroj(atributi[3], red);
        }

        if (ispravno) {
          Osoba osoba = kreirajOsobu(atributi);
          listaOsoba.add(osoba);
        }
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
    return listaOsoba;
  }

  private Osoba kreirajOsobu(String[] atribut) {

    String imeIPrezimeOsobe = atribut[0];
    int grad = Integer.parseInt(atribut[1]);
    int ulica = Integer.parseInt(atribut[2]);
    int kbr = Integer.parseInt(atribut[3]);

    return new Osoba(imeIPrezimeOsobe, grad, ulica, kbr);
  }

  private void provjeriImePrezime(String imePrezimeOsobe, String red) {
    if (imePrezimeOsobe.equals("")) {
      System.out.println("Pogreška broj " + BrojacGresaka.getInstance().dohvatiVrijednost()
          + " | Vrijednost ime i prezime osobe nije unešena! Sadržaj retka: " + red
          + " Red ne sadrži vrijednost ime i prezime.");
      BrojacGresaka.getInstance().increment();
      ispravno = false;
    } else {
      String regex = "^[\\p{L} \\d'.\\-]+$";
      if (!imePrezimeOsobe.matches(regex)) {
        System.out.println("Pogreška broj " + BrojacGresaka.getInstance().dohvatiVrijednost()
            + " | Nedozvoljeni znakovi u imenu i prezimenu osobe! Sadržaj retka: " + red);
        BrojacGresaka.getInstance().increment();
        ispravno = false;
      }
    }
  }

  public void provjeriBroj(String broj, String red) {
    try {
      Integer.parseInt(broj);
      ispravno = true;
    } catch (NumberFormatException e) {
      System.out.println("Pogreška broj " + BrojacGresaka.getInstance().dohvatiVrijednost()
          + " | Pogreška u podacima! Upisana vrijednost u datoteci mora biti cijeli broj. Redak: "
          + red);
      BrojacGresaka.getInstance().increment();
      ispravno = false;
    }
  }

}
