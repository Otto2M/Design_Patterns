package org.foi.uzdiz.omilermat.zadaca_3.state;

import java.util.List;
import org.foi.uzdiz.omilermat.zadaca_3.datoteke.Vozilo;
import org.foi.uzdiz.omilermat.zadaca_3.observer.Paket;

public class StateNeispravno implements VoziloState {

  @Override
  public void postaviStatusAktivno(Vozilo vozilo) {
    System.out.println("Vozilo je neispravno i ne može postati aktivno.");
  }

  @Override
  public void postaviStatusNeispravno(Vozilo vozilo) {
    vozilo.setStatusVozila("NI");
    System.out.println("Vozilo već ima postavljen status neispravno.");
  }

  @Override
  public void postaviStatusNeaktivno(Vozilo vozilo) {
    System.out.println("Vozilo je neispravno, te se ne može koristiti i postati neaktivno.");
  }

  @Override
  public String ukrcajPaket(Vozilo vozilo, List<Paket> listaPaketa) {
    return null;
  }
}
