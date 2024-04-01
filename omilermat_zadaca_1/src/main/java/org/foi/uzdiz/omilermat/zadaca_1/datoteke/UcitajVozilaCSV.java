package org.foi.uzdiz.omilermat.zadaca_1.datoteke;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UcitajVozilaCSV extends AbstractCSVLoader<Vozilo> {

  private List<Vozilo> listaVozila = new ArrayList<>();

  @Override
  public List<Vozilo> ucitajPodatkeCSV(String CSVDatoteka) {

    int brojVelikihSlova = 0;
    int brojBrojeva = 0;

    System.out.println("Ucitavam od proslijedene datoteke CSVDatoteka: " + CSVDatoteka);
    Set<Integer> redoslijedi = new HashSet<>();

    try (BufferedReader br = new BufferedReader(new FileReader(CSVDatoteka))) {
      String red = br.readLine();

      while ((red = br.readLine()) != null) {
        String[] atributi = red.split(";");

        if (atributi.length != 5) {
          System.out.println("Redak ne sadrži 5 argumenata: " + red);
          System.out.println("Preskačem red! ");
          continue;
        }

        String registracija = atributi[0];

        for (char c : registracija.toCharArray()) {
          if (Character.isUpperCase(c)) {
            brojVelikihSlova++;
          } else if (Character.isDigit(c)) {
            brojBrojeva++;
          }
        }

        if (brojVelikihSlova >= 3 && brojBrojeva >= 3) {
        } else {
          System.out.println("Pogreška kod registracije vozila u datoteci! Trenutna registracija: "
              + registracija);
          continue;
        }

        int redoslijed = Integer.parseInt(atributi[4]);
        // Provjera jedinstvenosti redoslijeda
        if (!redoslijedi.add(redoslijed)) {
          System.out.println("Dupla vrijednost atributa redoslijed za zapis: " + red);
          continue;
        }

        try {
          double kapacitetTezine = Double.parseDouble(atributi[2].replace(",", "."));
          double kapacitetProstora = Double.parseDouble(atributi[3].replace(",", "."));
          // Ako pretvorba uspije, atributi su brojčane vrijednosti
        } catch (NumberFormatException e) {
          if (e.getMessage().contains(atributi[2])) {
            System.out.println("Neispravan atribut kapacitet težine: " + atributi[2]
                + ". Vrijednost nije decimalni broj.");
          } else if (e.getMessage().contains(atributi[3])) {
            System.out.println("Neispravan atribut kapacitet prostora: " + atributi[3]
                + ". Vrijednost nije decimalni broj.");
          } else {
            System.out.println("Neispravna su oba atributa: " + atributi[2] + ", " + atributi[3]);
          }
          continue;
        }

        Vozilo vozilo = kreirajVozilo(atributi);
        listaVozila.add(vozilo);
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
    return new Vozilo(registracija, opis, kapacitetTezineKg, kapacitetProstoraM3, redoslijed);
  }

}
