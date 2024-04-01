package org.foi.uzdiz.omilermat.zadaca_3.CoR;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.foi.uzdiz.omilermat.zadaca_3.datoteke.UredZaDostavu;
import org.foi.uzdiz.omilermat.zadaca_3.datoteke.UredZaPrijem;
import org.foi.uzdiz.omilermat.zadaca_3.datoteke.Vozilo;
import org.foi.uzdiz.omilermat.zadaca_3.memento.Caretaker;
import org.foi.uzdiz.omilermat.zadaca_3.memento.Originator;
import org.foi.uzdiz.omilermat.zadaca_3.observer.Osoba;
import org.foi.uzdiz.omilermat.zadaca_3.observer.Paket;
import org.foi.uzdiz.omilermat.zadaca_3.singleton.KalkulatorVirtualnogVremena;

public class HandlerSPV implements Handler {
  private Handler sljedeciHandler;
  private HandlerPPV ppv;
  Originator o = new Originator();
  Caretaker ct;

  public List<Vozilo> kopijaVozila = new ArrayList<Vozilo>();

  public HandlerSPV(Caretaker caretaker) {
    ppv = new HandlerPPV(ct);
    this.ct = caretaker;
  }

  @Override
  public void obradiKomandu(String cijelaNaredba, String[] naredbaSplit, UredZaPrijem uredZaPrijem,
      UredZaDostavu uredZaDostavu, List<Vozilo> vozila, List<Paket> paketi, List<Osoba> osobe,
      Date krajnjeVrijeme) {
    if (naredbaSplit[0].equals("SPV")) {

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

      spremi(nazivStanja, vozila, paketi);

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

  private void spremi(String nazivStanja, List<Vozilo> vozila, List<Paket> paketi) {
    List<Vozilo> kopijaVozila = new ArrayList<>();
    List<Paket> kopijaPaketa = new ArrayList<>();
    for (Vozilo voz : vozila) {
      try {
        kopijaVozila.add(voz.cloneP());
      } catch (CloneNotSupportedException e) {
        e.printStackTrace();
      }
    }

    for (Paket originalPaket : paketi) {
      try {
        Paket kopiraniPaket = originalPaket.cloneP();
        kopijaPaketa.add(kopiraniPaket);
      } catch (CloneNotSupportedException e) {
        e.printStackTrace();
      }
    }

    o.setTrenutnoVirtualnoVrijeme(
        KalkulatorVirtualnogVremena.getInstance().dohvatiVirtualnoVrijeme());
    o.setTrenutnoStanjePaketa(kopijaPaketa);
    o.setTrenutnoStanjeVozila(kopijaVozila);

    Boolean status = ct.spremiStanje(nazivStanja, o.createMemento());
    if (status == true) {
      System.out
          .println("\n Spremanje trenutnog stanja virtualnog sata, paketa i vozila pod nazivom: "
              + nazivStanja);
    }

  }

}
