package org.foi.uzdiz.omilermat.zadaca_3.state;

import java.util.List;
import org.foi.uzdiz.omilermat.zadaca_3.datoteke.Vozilo;
import org.foi.uzdiz.omilermat.zadaca_3.observer.Paket;

public interface VoziloState {

  void postaviStatusAktivno(Vozilo vozilo);

  void postaviStatusNeispravno(Vozilo vozilo);

  void postaviStatusNeaktivno(Vozilo vozilo);

  String ukrcajPaket(Vozilo vozilo, List<Paket> listaPaketa);
}
