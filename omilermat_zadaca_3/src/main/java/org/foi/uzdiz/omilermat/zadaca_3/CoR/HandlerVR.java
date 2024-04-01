package org.foi.uzdiz.omilermat.zadaca_3.CoR;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.foi.uzdiz.omilermat.zadaca_3.datoteke.UredZaDostavu;
import org.foi.uzdiz.omilermat.zadaca_3.datoteke.UredZaPrijem;
import org.foi.uzdiz.omilermat.zadaca_3.datoteke.Vozilo;
import org.foi.uzdiz.omilermat.zadaca_3.observer.Osoba;
import org.foi.uzdiz.omilermat.zadaca_3.observer.Paket;
import org.foi.uzdiz.omilermat.zadaca_3.singleton.KalkulatorVirtualnogVremena;

public class HandlerVR implements Handler {
  private Handler sljedeciHandler;

  @Override
  public void obradiKomandu(String cijelaNaredba, String[] naredbaSplit, UredZaPrijem uredZaPrijem,
      UredZaDostavu uredZaDostavu, List<Vozilo> vozila, List<Paket> paketi, List<Osoba> osobe,
      Date krajnjeVrijeme) {
    if (naredbaSplit[0].equals("VR")) {
      Integer broj = (Integer.parseInt(naredbaSplit[1]));
      Calendar cal = Calendar.getInstance();
      cal.setTime(KalkulatorVirtualnogVremena.getInstance().trenutnoVirtualnoVrijeme);
      cal.add(Calendar.HOUR, broj);
      krajnjeVrijeme = cal.getTime();

      while (true) {
        if (!KalkulatorVirtualnogVremena.getInstance()
            .provjeriVrijemeSvakuSekundu(krajnjeVrijeme)) {
          break;
        }

        uredZaPrijem.provjeriZaprimljenePakete();
        uredZaDostavu.ukrcajPaketeUVoziloIDostavi();
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
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
