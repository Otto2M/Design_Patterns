package org.foi.uzdiz.omilermat.zadaca_3.datoteke;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.foi.uzdiz.omilermat.zadaca_3.singleton.BrojacGresaka;

public class UcitajMjestaCSV implements CSVLoader<Mjesto> {

  public List<Mjesto> listaMjesta = new ArrayList<>();
  boolean ispravno = true;
  public List<Ulica> ulice;
  private BrojacGresaka rbrPogreske;

  public UcitajMjestaCSV(BrojacGresaka rbrPogreske, List<Ulica> ulice) {
    this.ulice = ulice;
    this.rbrPogreske = rbrPogreske;
  }

  @Override
  public List<Mjesto> ucitajPodatkeCSV(String CSVDatoteka) {

    try (BufferedReader br = new BufferedReader(new FileReader(CSVDatoteka))) {
      String red = br.readLine();
      boolean porukaIspisana = false;

      while ((red = br.readLine()) != null) {
        String[] atributi = red.split(";");

        if (atributi.length != 3) {
          if (!porukaIspisana) {
            System.out.println("Pogreška broj " + BrojacGresaka.getInstance().dohvatiVrijednost()
                + " - Redak ne sadrži 3 argumenata: " + red);
            System.out.println("Preskačem red! ");
            BrojacGresaka.getInstance().increment();
            porukaIspisana = true;
          }
          continue;
        } else {

          provjeriID(atributi[0], red);
          provjeriNaziv(atributi[1], red);
          provjeriUlice(atributi[2], red);

        }

        if (ispravno) {
          Mjesto mjesto = kreirajMjesto(atributi);
          listaMjesta.add(mjesto);
        }
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
    return listaMjesta;
  }

  private Mjesto kreirajMjesto(String[] atribut) {

    int id = Integer.parseInt(atribut[0].trim());
    String naziv = atribut[1].trim();
    String uliceID = atribut[2].trim();
    String[] uliceIDString = uliceID.split(",");

    List<Ulica> listaUlicaID = new ArrayList<Ulica>();

    StringBuilder naziviUlica = new StringBuilder();
    for (String broj : uliceIDString) {
      int idUlice = Integer.parseInt(broj.trim());
      Optional<Ulica> ulicaOptional =
          ulice.stream().filter(ulica -> ulica.getId() == idUlice).findFirst();

      if (ulicaOptional.isPresent()) {
        Ulica ulica = ulicaOptional.get();
        listaUlicaID.add(ulica);
      }
    }
    return new Mjesto(id, naziv, listaUlicaID);
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

  private void provjeriNaziv(String nazivMjesta, String red) {
    if (nazivMjesta.equals("")) {
      System.out.println("Pogreška broj " + BrojacGresaka.getInstance().dohvatiVrijednost()
          + " | Vrijednost naziv mjesta nije unešena! Sadržaj retka: " + red
          + " Red ne sadrži vrijednost naziv mjesta.");
      BrojacGresaka.getInstance().increment();
      ispravno = false;
    } else {
      String regex = "^[\\p{L} \\d'\\-]+$";
      if (!nazivMjesta.matches(regex)) {
        System.out.println("Pogreška broj " + BrojacGresaka.getInstance().dohvatiVrijednost()
            + " | Nedozvoljeni znakovi u nazivu mjesta! Sadržaj retka: " + red);
        BrojacGresaka.getInstance().increment();
        ispravno = false;
      }
    }
  }

  private void provjeriUlice(String ulica, String red) {
    if (ulica.equals("")) {
      System.out.println("Pogreška broj " + BrojacGresaka.getInstance().dohvatiVrijednost()
          + " | Vrijednost ulica nije unešena! Sadržaj retka: " + red
          + " Red ne sadrži vrijednost ulica.");
      BrojacGresaka.getInstance().increment();
      ispravno = false;
    } else {
      String regex = "^\\s?[1-9]\\d*(,[1-9]\\d*)*$";
      if (!ulica.matches(regex)) {
        System.out.println("Pogreška broj " + BrojacGresaka.getInstance().dohvatiVrijednost()
            + " | Nedozvoljeni znakovi u oznakama ulica! Sadržaj retka: " + red);
        BrojacGresaka.getInstance().increment();
        ispravno = false;
      }
    }
  }

}
