package org.foi.uzdiz.omilermat.zadaca_3.decorator;

import java.util.List;
import org.foi.uzdiz.omilermat.zadaca_3.observer.Paket;

public class UDostaviPaketiDecorator implements KomandaIPDecorator {

  private KomandaIPDecorator decorator;

  public UDostaviPaketiDecorator(KomandaIPDecorator decorator) {
    this.decorator = decorator;
  }

  @Override
  public void ispisiPodatke(List<Paket> paketi) {
    if (decorator != null) {
      decorator.ispisiPodatke(paketi);
    }
    System.out.println("\n Popis svih paketa na dostavi:\n");
    postaviZaglavlje();
    for (Paket paket : paketi) {
      if (paket.getStatus().equalsIgnoreCase("NA DOSTAVI")) {
        System.out.printf("| %-21s | %-23s | %-22s |\n", paket.getOznaka(), paket.getStatus(),
            paket.getIznosDostave());
      }
    }
    System.out
        .println("----------------------------------------------------------------------------");

  }

  private static void postaviZaglavlje() {
    System.out
        .println("----------------------------------------------------------------------------");
    System.out
        .println("|     OZNAKA PAKETA     |     STATUS ISPORUKE     |      IZNOS DOSTAVE     |");
    System.out
        .println("----------------------------------------------------------------------------");
  }
}
