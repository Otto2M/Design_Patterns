package org.foi.uzdiz.omilermat.zadaca_3.decorator;

import java.text.SimpleDateFormat;
import java.util.List;
import org.foi.uzdiz.omilermat.zadaca_3.observer.Paket;

public class ZaprimljeniPaketiDecorator implements KomandaIPDecorator {

  private KomandaIPDecorator decorator;

  public ZaprimljeniPaketiDecorator(KomandaIPDecorator decorator) {
    this.decorator = decorator;
  }

  @Override
  public void ispisiPodatke(List<Paket> paketi) {
    if (decorator != null) {
      decorator.ispisiPodatke(paketi);
    }
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy. HH:mm:ss");
    System.out.println("\n Popis svih zaprimljenih paketa:\n");
    postaviZaglavlje();
    for (Paket paket : paketi) {
      if (paket.getStatus().equalsIgnoreCase("ZAPRIMLJEN")) {
        System.out.printf("| %-21s | %-23s | %-23s |\n", paket.getOznaka(), paket.getStatus(),
            sdf.format(paket.getVrijemePrijema()));
      }
    }
    System.out
        .println("-----------------------------------------------------------------------------");

  }

  private static void postaviZaglavlje() {
    System.out
        .println("-----------------------------------------------------------------------------");
    System.out
        .println("|     OZNAKA PAKETA     |     STATUS ISPORUKE     |     VRIJEME PRIJEMA     |");
    System.out
        .println("-----------------------------------------------------------------------------");
  }
}
