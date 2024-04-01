package org.foi.uzdiz.omilermat.zadaca_3.decorator;

import java.util.List;
import org.foi.uzdiz.omilermat.zadaca_3.observer.Paket;

public class VolumenPaketaDecorator implements KomandaIPDecorator {

  private KomandaIPDecorator decorator;

  public VolumenPaketaDecorator(KomandaIPDecorator decorator) {
    this.decorator = decorator;
  }

  @Override
  public void ispisiPodatke(List<Paket> paketi) {
    if (decorator != null) {
      decorator.ispisiPodatke(paketi);
    }
    System.out.println("\n Volumen pojedinog paketa:");
    for (Paket paket : paketi) {
      System.out.println("ID: " + paket.getOznaka() + " -> Volumen: "
          + (paket.getVisina() * paket.getSirina() * paket.getDuzina()));
    }
  }

}
