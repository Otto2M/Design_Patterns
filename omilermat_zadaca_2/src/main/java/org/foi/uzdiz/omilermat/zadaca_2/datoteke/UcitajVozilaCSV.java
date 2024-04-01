package org.foi.uzdiz.omilermat.zadaca_2.datoteke;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UcitajVozilaCSV implements CSVLoader<Vozilo> {

  public List<Vozilo> listaVozila = new ArrayList<>();

  @Override
  public List<Vozilo> ucitajPodatkeCSV(String CSVDatoteka) {

    Set<Integer> redoslijedi = new HashSet<>();
    boolean ispravno = true;

    try (BufferedReader br = new BufferedReader(new FileReader(CSVDatoteka))) {
      String red = br.readLine();

      while ((red = br.readLine()) != null) {
        String[] atributi = red.split(";");
        int brojVelikihSlova = 0;
        int brojBrojeva = 0;

        if (atributi.length != 8) {
          System.out.println("Redak ne sadrži 8 argumenata: " + red);
          System.out.println("Preskačem red! ");
          continue;
        }

        String registracija = atributi[0];
        String opis = atributi[1];

        for (char c : registracija.toCharArray()) {
          if (Character.isUpperCase(c)) {
            brojVelikihSlova++;
          } else if (Character.isDigit(c)) {
            brojBrojeva++;
          }
        }

        if (brojVelikihSlova >= 3 && brojBrojeva >= 3) {
        } else if (registracija == "") {
          System.out.println("Registracija za vozilo ne postoji!");
          ispravno = false;
        } else {
          System.out.println("Pogreška kod registracije vozila u datoteci! Trenutna registracija: "
              + registracija);
          ispravno = false;
        }

        if (opis == "") {
          System.out.println("Opis za vozilo ne postoji!");
          ispravno = false;
        }

        int redoslijed = Integer.parseInt(atributi[4]);
        if (!redoslijedi.add(redoslijed)) {
          System.out.println("Dupla vrijednost atributa redoslijed za zapis: " + red);
          ispravno = false;
        } else if (Integer.toString(redoslijed) == "") {
          System.out.println("Zapis redoslijed vozila ne postoji!");
          ispravno = false;
        }

        if (atributi[2].equals("")) {
          System.out.println("Vrijednost za kapacitet težine u kg ne postoji!");
          ispravno = false;
        } else {
          Pattern pattern = Pattern.compile("\\d+,\\d{1,2}");
          Matcher matcher = pattern.matcher(atributi[2]);
          if (!matcher.matches()) {
            System.out.println("Zapis kapacitet težine u kg nije isparavan!");
            ispravno = false;
          }
        }

        if (atributi[3] == "") {
          System.out.println("Vrijednost za kapacitet prostora u m3 ne postoji!");
          ispravno = false;
        } else {
          Pattern pattern = Pattern.compile("^\\d+(?:[.,]\\d+)?$");
          Matcher matcher = pattern.matcher(atributi[3]);
          if (!matcher.matches()) {
            System.out.println("Zapis kapacitet prostora u m3 nije isparavan!");
            ispravno = false;
          }
        }

        if (atributi[5] == "") {
          System.out.println("Vrijednost za prosječnu brzinu nije upisana!");
          ispravno = false;
        } else {
          try {
            Integer.parseInt(atributi[5]);
            ispravno = true;
          } catch (NumberFormatException e) {
            System.out.println(
                "Pogreška u kod atributa prosječna brzina : Broj mora biti cijeli broj. Redak: "
                    + red);
            ispravno = false;
          }
        }

        if (atributi[6] == "") {
          System.out.println("Vrijednost za područje po rangu nije upisana!");
          ispravno = false;
        } else {
          Pattern pattern = Pattern.compile("^[0-9]+(?:,[0-9]+)*$");
          Matcher matcher = pattern.matcher(atributi[6]);
          if (!matcher.matches()) {
            System.out.println("Zapis područje po rangu nije isparavan! Red: " + red);
            ispravno = false;
          }
        }

        if (atributi[7] == "") {
          System.out.println("Vrijednost za status nije upisana!");
          ispravno = false;
        } else {
          Pattern pattern = Pattern.compile("^(NA|NI|A)$");
          Matcher matcher = pattern.matcher(atributi[7]);
          if (!matcher.matches()) {
            System.out.println("Zapis status nije isparavan! Red: " + red);
            ispravno = false;
          }
        }

        if (ispravno) {
          Vozilo vozilo = kreirajVozilo(atributi);
          listaVozila.add(vozilo);
        }
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }

    listaVozila.sort(Comparator.comparing(Vozilo::dohvatiRedoslijed));
    return listaVozila;
  }

  private Vozilo kreirajVozilo(String[] atribut) {
    String registracija = atribut[0];
    String opis = atribut[1];
    double kapacitetTezineKg = Double.parseDouble(atribut[2].replace(",", "."));
    double kapacitetProstoraM3 = Double.parseDouble(atribut[3].replace(",", "."));
    int redoslijed = Integer.parseInt(atribut[4]);
    boolean statusSlobodno = true;
    int prosjecnaBrzina = Integer.parseInt(atribut[5]);
    String podrucjePoRangu = atribut[6];
    // VoziloState status = atribut[7];
    String statusVozila = "A";
    return new Vozilo(registracija, opis, kapacitetTezineKg, kapacitetProstoraM3, redoslijed,
        statusSlobodno, prosjecnaBrzina, podrucjePoRangu, statusVozila);
  }

}
