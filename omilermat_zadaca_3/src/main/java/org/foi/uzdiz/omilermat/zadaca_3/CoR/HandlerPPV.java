package org.foi.uzdiz.omilermat.zadaca_3.CoR;

import java.util.Date;
import java.util.List;
import org.foi.uzdiz.omilermat.zadaca_3.datoteke.UredZaDostavu;
import org.foi.uzdiz.omilermat.zadaca_3.datoteke.UredZaPrijem;
import org.foi.uzdiz.omilermat.zadaca_3.datoteke.Vozilo;
import org.foi.uzdiz.omilermat.zadaca_3.memento.Caretaker;
import org.foi.uzdiz.omilermat.zadaca_3.memento.Memento;
import org.foi.uzdiz.omilermat.zadaca_3.memento.Originator;
import org.foi.uzdiz.omilermat.zadaca_3.observer.Osoba;
import org.foi.uzdiz.omilermat.zadaca_3.observer.Paket;
import org.foi.uzdiz.omilermat.zadaca_3.singleton.KalkulatorVirtualnogVremena;

public class HandlerPPV implements Handler {
  private Handler sljedeciHandler;
  Originator o = new Originator();
  private Caretaker caretaker;

  public HandlerPPV(Caretaker caretaker) {
    this.caretaker = caretaker;
  }

  @Override
  public void obradiKomandu(String cijelaNaredba, String[] naredbaSplit, UredZaPrijem uredZaPrijem,
      UredZaDostavu uredZaDostavu, List<Vozilo> vozila, List<Paket> paketi, List<Osoba> osobe,
      Date krajnjeVrijeme) {
    if (naredbaSplit[0].equals("PPV")) {

      String[] dijelovi = cijelaNaredba.split(" ");
      String nazivStanja = "";
      for (int i = 1; i < dijelovi.length; i++) {
        if (i != 1) {
          nazivStanja += " " + dijelovi[i];
        } else {
          nazivStanja += dijelovi[i];
        }
      }
      nazivStanja = nazivStanja.replace("'", "");

      vrati(nazivStanja, paketi, vozila);

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

  private void vrati(String nazivStanja, List<Paket> paketi, List<Vozilo> vozila) {

    Memento memento = caretaker.dohvatiStanje(nazivStanja);
    if (memento != null) {
      o.setMemento(memento);

      List<Vozilo> novoStanjeVozila = o.getTrenutnoStanjeVozila();
      List<Paket> novoStanjePaketa = o.getTrenutnoStanjePaketa();
      KalkulatorVirtualnogVremena.getInstance().trenutnoVirtualnoVrijeme =
          o.getTrenutnoVirtualnoVrijeme();

      vozila.clear();
      vozila.addAll(novoStanjeVozila);
      paketi.clear();
      paketi.addAll(novoStanjePaketa);

      System.out
          .println("\n Povratak trenutnog stanja virtualnog sata, paketa i vozila pod nazivom: "
              + nazivStanja);
    } else {
      System.out.println("Nema spremljenog stanja s nazivom: " + nazivStanja);
    }
  }

}
