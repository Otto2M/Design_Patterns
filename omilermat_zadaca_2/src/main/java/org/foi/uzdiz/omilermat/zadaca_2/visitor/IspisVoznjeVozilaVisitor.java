package org.foi.uzdiz.omilermat.zadaca_2.visitor;

import org.foi.uzdiz.omilermat.zadaca_2.datoteke.Vozilo;

public class IspisVoznjeVozilaVisitor implements VoziloVisitor {

  @Override
  public void visit(Vozilo vozilo) {
    System.out.println("\n VOZILO: " + vozilo.dohvatiRegistraciju() + "\n");

    System.out.println("------------------------------");
    formatiraniIspis("Vrijeme početka", "/");
    System.out.println("------------------------------");
    formatiraniIspis("Vrijeme povratka", "/");
    System.out.println("------------------------------");
    formatiraniIspis("Trajanje", "/");
    System.out.println("------------------------------");
    formatiraniIspis("Ukupno odvoženi km", "/ km");
    System.out.println("------------------------------");
    formatiraniIspis("Broj paketa - Hitni", vozilo.getBrHitnihPaketa() + "");
    System.out.println("------------------------------");
    formatiraniIspis("Broj paketa - Obični", vozilo.getBrObicnihPaketa() + "");
    System.out.println("------------------------------");
    formatiraniIspis("Broj paketa - Isporučeni", vozilo.getBrIsporucenihPaketa() + "");
    System.out.println("------------------------------");
    formatiraniIspis("Trenutni % zauzeća prostora",
        izracunajPostotakPopunjenostiVozila(vozilo.getKapacitet(), vozilo.getZauzetiProstorVozila())
            + "%");
    System.out.println("------------------------------");
    formatiraniIspis("Trenutni % zauzeća težine",
        izracunajPostotakPopunjenostiVozilaTezina(vozilo.getTezina(),
            vozilo.getTrenutnaTezinaVozila()) + "%");
    System.out.println("------------------------------");

  }

  private void formatiraniIspis(String labela, String vrijednost) {
    System.out.printf("%-30s: %s%n", labela, vrijednost);
  }

  public double izracunajPostotakPopunjenostiVozila(double ukupniVolumen, double zauzetiVolumen) {
    return (zauzetiVolumen / ukupniVolumen) * 100;
  }

  public double izracunajPostotakPopunjenostiVozilaTezina(double ukupnaTezina,
      double trenutnaTezina) {
    return (trenutnaTezina / ukupnaTezina) * 100;
  }

}
