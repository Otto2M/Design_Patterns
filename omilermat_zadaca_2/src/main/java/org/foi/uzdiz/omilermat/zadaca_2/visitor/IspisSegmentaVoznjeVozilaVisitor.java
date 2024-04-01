package org.foi.uzdiz.omilermat.zadaca_2.visitor;

import org.foi.uzdiz.omilermat.zadaca_2.datoteke.Vozilo;

public class IspisSegmentaVoznjeVozilaVisitor implements VoziloVisitor {

  @Override
  public void visit(Vozilo vozilo) {

    System.out.println("------------------------------");
    formatiraniIspis("Vrijeme početka", "/");
    System.out.println("------------------------------");
    formatiraniIspis("Vrijeme kraja", "/");
    System.out.println("------------------------------");
    formatiraniIspis("Trajanje", "/");
    System.out.println("------------------------------");
    formatiraniIspis("Odvoženi km", "/ km");
    System.out.println("------------------------------");
    formatiraniIspis("Paket", "/");
    System.out.println("------------------------------");

  }

  private void formatiraniIspis(String labela, String vrijednost) {
    System.out.printf("%-30s: %s%n", labela, vrijednost);
  }

}
