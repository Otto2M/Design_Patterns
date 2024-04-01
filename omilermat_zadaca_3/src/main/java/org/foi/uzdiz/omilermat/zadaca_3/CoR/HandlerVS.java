package org.foi.uzdiz.omilermat.zadaca_3.CoR;

import java.util.Date;
import java.util.List;
import org.foi.uzdiz.omilermat.zadaca_3.datoteke.UredZaDostavu;
import org.foi.uzdiz.omilermat.zadaca_3.datoteke.UredZaPrijem;
import org.foi.uzdiz.omilermat.zadaca_3.datoteke.Vozilo;
import org.foi.uzdiz.omilermat.zadaca_3.observer.Osoba;
import org.foi.uzdiz.omilermat.zadaca_3.observer.Paket;
import org.foi.uzdiz.omilermat.zadaca_3.visitor.IspisSegmentaVoznjeVozilaVisitor;
import org.foi.uzdiz.omilermat.zadaca_3.visitor.VoziloVisitor;

public class HandlerVS implements Handler {
  private Handler sljedeciHandler;

  @Override
  public void obradiKomandu(String cijelaNaredba, String[] naredbaSplit, UredZaPrijem uredZaPrijem,
      UredZaDostavu uredZaDostavu, List<Vozilo> vozila, List<Paket> paketi, List<Osoba> osobe,
      Date krajnjeVrijeme) {
    if (naredbaSplit[0].equals("VS")) {

      String registracija = naredbaSplit[1].trim();
      String podatakOSegmentu = naredbaSplit[2].trim();

      System.out.println("Pregled podataka za segmente vožnje vozila tijekom dana\n");
      System.out.println("Vozilo: " + registracija);
      System.out.println("Segment vožnje: " + podatakOSegmentu + "\n");
      VoziloVisitor ispisSegmentaVoznjeVozilaVisitor = new IspisSegmentaVoznjeVozilaVisitor();

      for (Vozilo vozilo : vozila) {
        if (vozilo.dohvatiRegistraciju().equals(registracija)) {
          vozilo.accept(ispisSegmentaVoznjeVozilaVisitor);
        }
      }
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

}
