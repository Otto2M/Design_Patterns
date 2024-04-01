package org.foi.uzdiz.omilermat.zadaca_3.CoR;

import java.util.Date;
import java.util.List;
import org.foi.uzdiz.omilermat.zadaca_3.datoteke.UredZaDostavu;
import org.foi.uzdiz.omilermat.zadaca_3.datoteke.UredZaPrijem;
import org.foi.uzdiz.omilermat.zadaca_3.datoteke.Vozilo;
import org.foi.uzdiz.omilermat.zadaca_3.observer.Osoba;
import org.foi.uzdiz.omilermat.zadaca_3.observer.Paket;
import org.foi.uzdiz.omilermat.zadaca_3.singleton.KalkulatorVirtualnogVremena;

public class HandlerPS implements Handler {
  private Handler sljedeciHandler;

  @Override
  public void obradiKomandu(String cijelaNaredba, String[] naredbaSplit, UredZaPrijem uredZaPrijem,
      UredZaDostavu uredZaDostavu, List<Vozilo> vozila, List<Paket> paketi, List<Osoba> osobe,
      Date krajnjeVrijeme) {
    if (naredbaSplit[0].equals("PS")) {

      String registracija = naredbaSplit[1].trim();
      String status = naredbaSplit[2].trim();

      System.out.println("TRENUTNO VIRTUALNO VRIJEME: "
          + KalkulatorVirtualnogVremena.getInstance().dohvatiVirtualnoVrijeme() + "\n");

      postaviStatusVozila(vozila, registracija, status);
    } else if (sljedeciHandler != null) {
      sljedeciHandler.obradiKomandu(cijelaNaredba, naredbaSplit, uredZaPrijem, uredZaDostavu,
          vozila, paketi, osobe, krajnjeVrijeme);
    }
  }

  @Override
  public Handler setNext(Handler sljedeciHandler) {
    if (this.sljedeciHandler == null) {
      this.sljedeciHandler = sljedeciHandler;
    } else {
      this.sljedeciHandler.setNext(sljedeciHandler);
    }
    return this;
  }

  private static void postaviStatusVozila(List<Vozilo> vozila, String registracija, String status) {

    for (Vozilo vozilo : vozila) {
      if (vozilo.dohvatiRegistraciju().equals(registracija)) {
        if (status.equals("A")) {
          vozilo.postaviStatusAktivno();
        } else if (status.equals("NA")) {
          vozilo.postaviStatusNeaktivno();
        } else if (status.equals("NI")) {
          vozilo.postaviStatusNeispravno();
        } else {
          System.out.println("Unešeno je nepostojeće stanje za vozilo.");
        }
      }
    }
  }

}
