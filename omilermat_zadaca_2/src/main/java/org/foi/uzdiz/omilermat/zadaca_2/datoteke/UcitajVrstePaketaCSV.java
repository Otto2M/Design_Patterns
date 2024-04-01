package org.foi.uzdiz.omilermat.zadaca_2.datoteke;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.foi.uzdiz.omilermat.zadaca_2.builder.VrstaPaketaBuilder;

public class UcitajVrstePaketaCSV implements CSVLoader<VrstaPaketa> {

  public List<VrstaPaketa> listaVrstaPaketa = new ArrayList<>();

  public static void main(String[] args) {

    UcitajVrstePaketaCSV loader = new UcitajVrstePaketaCSV();
    MainClass mainClass = new MainClass();
    loader.ucitajPodatkeCSV(mainClass.vs);

  }

  @Override
  public List<VrstaPaketa> ucitajPodatkeCSV(String CSVDatoteka) {

    try (BufferedReader br = new BufferedReader(new FileReader(CSVDatoteka))) {
      String red = br.readLine();
      int brojLinije = 0;

      while ((red = br.readLine()) != null) {
        brojLinije++;
        String[] atributi = red.split(";");

        if (atributi.length != 10) {
          System.out.println("Redak ne sadrži 10 argumenata: " + red);
          System.out.println("Preskačem red! ");
          continue;
        }

        String oznaka = atributi[0];
        if (!oznaka.matches("[ABCDEX]")) {
          System.out.println("Neispravna oznaka: " + oznaka + " u redu " + brojLinije);
          continue;
        }

        VrstaPaketa paketVrsta = kreirajPaket(atributi);
        listaVrstaPaketa.add(paketVrsta);
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }

    return listaVrstaPaketa;
  }

  VrstaPaketaBuilder builder = new VrstaPaketaBuilder();

  public VrstaPaketa kreirajPaket(String[] atribut) {
    double visina = Double.parseDouble(atribut[2].replace(",", "."));
    double sirina = Double.parseDouble(atribut[3].replace(",", "."));
    double duzina = Double.parseDouble(atribut[4].replace(",", "."));
    double maksTezina = Double.parseDouble(atribut[5].replace(",", "."));
    double cijena = Double.parseDouble(atribut[6].replace(",", "."));
    double cijenaHitno = Double.parseDouble(atribut[7].replace(",", "."));
    double cijenaP = Double.parseDouble(atribut[8].replace(",", "."));
    double cijenaT = Double.parseDouble(atribut[9].replace(",", "."));

    builder.oznaka(atribut[0]).opis(atribut[1]).visina(visina).sirina(sirina).duzina(duzina)
        .maksTezina(maksTezina).cijena(cijena).cijenaHitno(cijenaHitno).cijenaP(cijenaP)
        .cijenaT(cijenaT);

    VrstaPaketa vp = builder.build();
    return vp;
  }


}
