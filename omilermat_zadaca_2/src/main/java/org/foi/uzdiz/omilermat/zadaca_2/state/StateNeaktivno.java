package org.foi.uzdiz.omilermat.zadaca_2.state;

import java.util.List;
import org.foi.uzdiz.omilermat.zadaca_2.datoteke.Vozilo;
import org.foi.uzdiz.omilermat.zadaca_2.observer.Paket;

public class StateNeaktivno implements VoziloState {

  @Override
  public void postaviStatusAktivno(Vozilo vozilo) {
    vozilo.setStatus(new StateAktivno());
    vozilo.setStatusVozila("A");
    System.out.println(
        "Status 'A - aktivno' uspješno promijenjeno za vozilo: " + vozilo.dohvatiRegistraciju());
  }

  @Override
  public void postaviStatusNeispravno(Vozilo vozilo) {
    System.out.println("Vozilo je neaktivno i ne može postati neispravno.");
  }

  @Override
  public void postaviStatusNeaktivno(Vozilo vozilo) {
    vozilo.setStatusVozila("NA");
    System.out.println("Vozilo je već neaktivno.");
  }

  @Override
  public String ukrcajPaket(Vozilo vozilo, List<Paket> listaPaketa) {
    return null;
  }
}
