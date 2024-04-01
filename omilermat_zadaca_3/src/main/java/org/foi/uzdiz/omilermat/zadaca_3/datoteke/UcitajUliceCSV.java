package org.foi.uzdiz.omilermat.zadaca_3.datoteke;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.foi.uzdiz.omilermat.zadaca_3.singleton.BrojacGresaka;

public class UcitajUliceCSV implements CSVLoader<Ulica> {

  public List<Ulica> listaUlica = new ArrayList<>();
  boolean ispravno = true;
  private BrojacGresaka rbrPogreske;

  public UcitajUliceCSV(BrojacGresaka rbrPogreske) {
    this.rbrPogreske = rbrPogreske;
  }

  public List<Ulica> ucitajPodatkeCSV(String CSVDatoteka) {

    try (BufferedReader br = new BufferedReader(new FileReader(CSVDatoteka))) {
      String red = br.readLine();

      while ((red = br.readLine()) != null) {
        String[] atributi = red.split(";");

        if (atributi.length != 7) {

          System.out.println("Pogreška broj " + BrojacGresaka.getInstance().dohvatiVrijednost()
              + " - Redak ne sadrži 7 argumenata: " + red);
          System.out.println("Preskačem red! ");
          BrojacGresaka.getInstance().increment();
          continue;
        } else {

          provjeriID(atributi[0], red);
          provjeriNaziv(atributi[1], red);
          provjeriGPS(atributi[2], red);
          provjeriGPS(atributi[3], red);
          provjeriGPS(atributi[4], red);
          provjeriGPS(atributi[5], red);
          provjeriNajveciKucniBroj(atributi[6], red);
        }

        if (ispravno) {
          Ulica ulica = kreirajUlicu(atributi);
          listaUlica.add(ulica);
        }
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
    return listaUlica;

  }

  private Ulica kreirajUlicu(String[] atribut) {

    int id = Integer.parseInt(atribut[0].trim());
    String naziv = atribut[1].trim();
    Double gps_lat_1 = Double.parseDouble(atribut[2].trim());
    Double gps_lon_1 = Double.parseDouble(atribut[3].trim());
    Double gps_lat_2 = Double.parseDouble(atribut[4].trim());
    Double gps_lon_2 = Double.parseDouble(atribut[5].trim());
    int kucniBroj = Integer.parseInt(atribut[6].trim());

    return new Ulica(id, naziv, gps_lat_1, gps_lon_1, gps_lat_2, gps_lon_2, kucniBroj);
  }

  public boolean provjeriID(String id, String cijeliRedak) {
    try {
      Integer.parseInt(id);
      return true;
    } catch (NumberFormatException e) {
      System.out.println("Pogreška broj " + BrojacGresaka.getInstance().dohvatiVrijednost()
          + " | Pogreška u ID stupcu: ID mora biti cijeli broj. Redak: " + cijeliRedak);
      BrojacGresaka.getInstance().increment();
      return false;
    }
  }

  public boolean provjeriNaziv(String naziv, String cijeliRedak) {
    if (naziv.trim().isEmpty()) {
      System.out.println("Pogreška broj " + BrojacGresaka.getInstance().dohvatiVrijednost()
          + " | Pogreška u stupcu naziv: Naziv ne smije biti prazan. Redak: " + cijeliRedak);
      BrojacGresaka.getInstance().increment();
      return false;
    }
    return true;
  }

  public boolean provjeriGPS(String gpsKoordinata, String cijeliRedak) {
    String regex = "^[-+]?[0-9]*\\.?[0-9]+$";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(gpsKoordinata.trim());
    if (!matcher.matches()) {
      System.out.println("Pogreška broj " + BrojacGresaka.getInstance().dohvatiVrijednost()
          + " | Pogreška u stupcu GPS koordinate: Neispravan format. Redak: " + cijeliRedak);
      BrojacGresaka.getInstance().increment();
      return false;
    }
    return true;
  }

  public boolean provjeriNajveciKucniBroj(String broj, String cijeliRedak) {
    try {
      Integer.parseInt(broj);
      return true;
    } catch (NumberFormatException e) {
      System.out.println("Pogreška broj " + BrojacGresaka.getInstance().dohvatiVrijednost()
          + " | Pogreška u stupcu najveći kućni broj: Broj mora biti cijeli broj. Redak: "
          + cijeliRedak);
      BrojacGresaka.getInstance().increment();
      return false;
    }
  }
}
