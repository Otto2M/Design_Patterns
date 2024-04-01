package org.foi.uzdiz.omilermat.zadaca_3.CoR;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.foi.uzdiz.omilermat.zadaca_3.datoteke.UredZaDostavu;
import org.foi.uzdiz.omilermat.zadaca_3.datoteke.UredZaPrijem;
import org.foi.uzdiz.omilermat.zadaca_3.datoteke.Vozilo;
import org.foi.uzdiz.omilermat.zadaca_3.observer.Osoba;
import org.foi.uzdiz.omilermat.zadaca_3.observer.Paket;

public class HandlerPO implements Handler {
  private Handler sljedeciHandler;

  @Override
  public void obradiKomandu(String cijelaNaredba, String[] naredbaSplit, UredZaPrijem uredZaPrijem,
      UredZaDostavu uredZaDostavu, List<Vozilo> vozila, List<Paket> paketi, List<Osoba> osobe,
      Date krajnjeVrijeme) {
    if (naredbaSplit[0].equals("PO")) {

      String imeOsobe = dajOsobu(cijelaNaredba);
      String imeOsobeTrim = imeOsobe.trim();
      String oznakaPaketa = naredbaSplit[3];
      String status = naredbaSplit[4];

      promijeniStatusSlanjaObavijesti(paketi, osobe, oznakaPaketa, imeOsobeTrim, status);
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

  private static String dajOsobu(String cijelaNaredba) {
    String imeOsobe = null;
    Pattern pattern = Pattern.compile("'(.*?)'");
    Matcher matcher = pattern.matcher(cijelaNaredba);

    if (matcher.find()) {
      imeOsobe = matcher.group(1);
    }
    return imeOsobe;
  }

  private static void promijeniStatusSlanjaObavijesti(List<Paket> paketi, List<Osoba> osobe,
      String oznakaPaketa, String imeOsobeTrim, String status) {

    for (Paket paket : paketi) {
      for (Osoba o : osobe) {
        if (oznakaPaketa.equals(paket.getOznaka())) {
          if (imeOsobeTrim.equals(o.getImeIPrezimeOsobe())) {
            if (status.equals("D")) {
              paket.dodajOsobu(o);
              System.out.println(
                  "Osoba " + imeOsobeTrim + " želi primati obavijesti za paket " + oznakaPaketa);
            } else {
              paket.ukloniOsobu(o);
              System.out.println(
                  "Osoba " + imeOsobeTrim + " ne želi primati obavijesti za paket " + oznakaPaketa);
            }
          }
        }
      }
    }
  }
}
