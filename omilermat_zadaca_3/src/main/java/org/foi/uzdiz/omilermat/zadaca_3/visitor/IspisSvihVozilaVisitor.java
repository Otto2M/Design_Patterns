package org.foi.uzdiz.omilermat.zadaca_3.visitor;

import org.foi.uzdiz.omilermat.zadaca_3.datoteke.Vozilo;

public class IspisSvihVozilaVisitor implements VoziloVisitor {

  @Override
  public void visit(Vozilo v) {

    String registracija = v.dohvatiRegistraciju();
    String status = v.getStatusVozila();
    Double ukupnoKm = 0.0d;
    int brHitnih = 0;
    int brObicnih = 0;
    int brIsporucenih = 0;
    Double postotakProstora =
        izracunajPostotakPopunjenostiVozila(v.getKapacitet(), v.getZauzetiProstorVozila());
    Double postotakTezina =
        izracunajPostotakPopunjenostiVozilaTezina(v.getTezina(), v.getTrenutnaTezinaVozila());
    int brVoznji = 0;

    prikaziPodatkeVozila(registracija, status, ukupnoKm, brHitnih, brObicnih, brIsporucenih,
        postotakProstora, postotakTezina, brVoznji);

    System.out.println(
        "---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
  }

  private static void prikaziPodatkeVozila(String registracija, String status, Double ukupnoKm,
      int brHitnih, int brObicnih, int brIsporucenih, Double postotakProstora,
      Double postotakTezina, int brVoznji) {
    System.out.printf("| %-20s | %-14s | %-13s | %-21s | %-23s | %-27s | %-23s | %-21s | %-17s |\n",
        registracija, status, ukupnoKm, brHitnih, brObicnih, brIsporucenih, postotakProstora,
        postotakTezina, brVoznji);
  }

  public double izracunajPostotakPopunjenostiVozila(double ukupniVolumen, double zauzetiVolumen) {
    return (zauzetiVolumen / ukupniVolumen) * 100;
  }

  public double izracunajPostotakPopunjenostiVozilaTezina(double ukupnaTezina,
      double trenutnaTezina) {
    return (trenutnaTezina / ukupnaTezina) * 100;
  }

}
