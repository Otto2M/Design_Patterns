package org.foi.uzdiz.omilermat.zadaca_3.CoR;

import java.util.Date;
import java.util.List;
import org.foi.uzdiz.omilermat.zadaca_3.datoteke.UredZaDostavu;
import org.foi.uzdiz.omilermat.zadaca_3.datoteke.UredZaPrijem;
import org.foi.uzdiz.omilermat.zadaca_3.datoteke.Vozilo;
import org.foi.uzdiz.omilermat.zadaca_3.observer.Osoba;
import org.foi.uzdiz.omilermat.zadaca_3.observer.Paket;
import org.foi.uzdiz.omilermat.zadaca_3.visitor.IspisSvihVozilaVisitor;
import org.foi.uzdiz.omilermat.zadaca_3.visitor.VoziloVisitor;

public class HandlerSV implements Handler {
  private Handler sljedeciHandler;

  @Override
  public void obradiKomandu(String cijelaNaredba, String[] naredbaSplit, UredZaPrijem uredZaPrijem,
      UredZaDostavu uredZaDostavu, List<Vozilo> vozila, List<Paket> paketi, List<Osoba> osobe,
      Date krajnjeVrijeme) {
    if (naredbaSplit[0].equals("SV")) {

      VoziloVisitor ispisSvihVozilaVisitor = new IspisSvihVozilaVisitor();
      postaviZaglavljeSvaVozila();

      for (Vozilo v : vozila) {
        v.accept(ispisSvihVozilaVisitor);
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

  private static void postaviZaglavljeSvaVozila() {
    System.out.println(
        "--------------------------------------------------------------------------------------------"
            + "-------------------------------------------------------------------------------------------------------------------");
    System.out
        .println("|     REGISTRACIJA     |     STATUS     |   UKUPNO KM   |   BROJ HITNIH PAKETA  "
            + "|   BROJ OBIČNIH PAKETA   |   BROJ ISPORUČENIH PAKETA   |    POSTOTAK PROSTORA    |    POSTOTAK TEŽINE    |    BROJ VOŽNJI    |");
    System.out.println(
        "--------------------------------------------------------------------------------------------"
            + "-------------------------------------------------------------------------------------------------------------------");
  }

}
