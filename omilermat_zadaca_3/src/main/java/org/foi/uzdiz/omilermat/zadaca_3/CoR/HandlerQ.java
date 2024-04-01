package org.foi.uzdiz.omilermat.zadaca_3.CoR;

import java.util.Date;
import java.util.List;
import org.foi.uzdiz.omilermat.zadaca_3.datoteke.UredZaDostavu;
import org.foi.uzdiz.omilermat.zadaca_3.datoteke.UredZaPrijem;
import org.foi.uzdiz.omilermat.zadaca_3.datoteke.Vozilo;
import org.foi.uzdiz.omilermat.zadaca_3.observer.Osoba;
import org.foi.uzdiz.omilermat.zadaca_3.observer.Paket;

public class HandlerQ implements Handler {

  @Override
  public void obradiKomandu(String cijelaNaredba, String[] naredbaSplit, UredZaPrijem uredZaPrijem,
      UredZaDostavu uredZaDostavu, List<Vozilo> vozila, List<Paket> paketi, List<Osoba> osobe,
      Date krajnjeVrijeme) {

    if (naredbaSplit[0].equals("Q")) {
      System.out.println("Izlaz iz programa.");
      System.exit(0);
    } else {
      System.out.println("Nepoznata naredba: " + naredbaSplit[0]);
    }
  }

  @Override
  public Handler setNext(Handler sljedeciHandler) {
    return this;
  }
}
