package org.foi.uzdiz.omilermat.zadaca_3.decorator;

import java.util.List;
import org.foi.uzdiz.omilermat.zadaca_3.observer.Paket;

public class DostavljeniPaketiDecorator implements KomandaIPDecorator {

  private KomandaIPDecorator decorator;

  public DostavljeniPaketiDecorator(KomandaIPDecorator decorator) {
    this.decorator = decorator;
  }

  @Override
  public void ispisiPodatke(List<Paket> paketi) {
    if (decorator != null) {
      decorator.ispisiPodatke(paketi);
    }
    System.out.println("\n Popis svih dostavljenih paketa:\n");
    postaviZaglavlje();
    for (Paket paket : paketi) {
      if (paket.getStatus().equalsIgnoreCase("DOSTAVLJEN")) {
        System.out.printf("| %-21s | %-23s | %-20s |\n", paket.getOznaka(), paket.getStatus(),
            paket.getPrimatelj());
      }
    }
    System.out
        .println("--------------------------------------------------------------------------");

  }

  private static void postaviZaglavlje() {
    System.out
        .println("--------------------------------------------------------------------------");
    System.out
        .println("|     OZNAKA PAKETA     |     STATUS ISPORUKE     |       PRIMATELJ      |");
    System.out
        .println("--------------------------------------------------------------------------");
  }
}
