package org.foi.uzdiz.omilermat.zadaca_3.CoR;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.foi.uzdiz.omilermat.zadaca_3.datoteke.UredZaDostavu;
import org.foi.uzdiz.omilermat.zadaca_3.datoteke.UredZaPrijem;
import org.foi.uzdiz.omilermat.zadaca_3.datoteke.Vozilo;
import org.foi.uzdiz.omilermat.zadaca_3.decorator.DostavljeniPaketiDecorator;
import org.foi.uzdiz.omilermat.zadaca_3.decorator.DuzinaDecorator;
import org.foi.uzdiz.omilermat.zadaca_3.decorator.KomandaIPDecorator;
import org.foi.uzdiz.omilermat.zadaca_3.decorator.SirinaDecorator;
import org.foi.uzdiz.omilermat.zadaca_3.decorator.TezinaDecorator;
import org.foi.uzdiz.omilermat.zadaca_3.decorator.UDostaviPaketiDecorator;
import org.foi.uzdiz.omilermat.zadaca_3.decorator.VisinaDecorator;
import org.foi.uzdiz.omilermat.zadaca_3.decorator.VolumenPaketaDecorator;
import org.foi.uzdiz.omilermat.zadaca_3.decorator.ZaprimljeniPaketiDecorator;
import org.foi.uzdiz.omilermat.zadaca_3.observer.Osoba;
import org.foi.uzdiz.omilermat.zadaca_3.observer.Paket;
import org.foi.uzdiz.omilermat.zadaca_3.singleton.KalkulatorVirtualnogVremena;

public class HandlerIP implements Handler {
  private Handler sljedeciHandler;

  @Override
  public void obradiKomandu(String cijelaNaredba, String[] naredbaSplit, UredZaPrijem uredZaPrijem,
      UredZaDostavu uredZaDostavu, List<Vozilo> vozila, List<Paket> paketi, List<Osoba> osobe,
      Date krajnjeVrijeme) {

    String naredba = naredbaSplit[0];
    KomandaIPDecorator decorator = null;

    if (naredba.equals("IP") && naredbaSplit.length > 1) {
      String dodatnaKomanda = naredbaSplit[1];

      switch (dodatnaKomanda) {
        case "t":
          decorator = new TezinaDecorator(decorator);
          break;
        case "v":
          decorator = new VisinaDecorator(decorator);
          break;
        case "s":
          decorator = new SirinaDecorator(decorator);
          break;
        case "d":
          decorator = new DuzinaDecorator(decorator);
          break;
        case "vol":
          decorator = new VolumenPaketaDecorator(decorator);
          break;
        case "1":
          decorator = new DostavljeniPaketiDecorator(decorator);
          break;
        case "2":
          decorator = new ZaprimljeniPaketiDecorator(decorator);
          break;
        case "3":
          decorator = new UDostaviPaketiDecorator(decorator);
          break;
        default:
          System.out.println("Nepoznata komanda: " + cijelaNaredba);
          break;
      }
      if (decorator != null) {
        decorator.ispisiPodatke(uredZaPrijem.listaSvihZaprimljenihPaketa);
      }

    } else if (naredba.equals("IP")) {
      System.out.println("\nTRENUTNO VIRTUALNO VRIJEME: "
          + KalkulatorVirtualnogVremena.getInstance().dohvatiVirtualnoVrijeme() + "\n");
      postaviZaglavlje();
      napuniTablicu(uredZaPrijem, uredZaDostavu);
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

  private static void postaviZaglavlje() {
    System.out.println(
        "--------------------------------------------------------------------------------------------"
            + "---------------------------------------------------------------------------------------------------");
    System.out.println(
        "|     OZNAKA PAKETA     |     VRIJEME PRIJEMA     |   VRSTA PAKETA   |   VRSTA USLUGE  "
            + "|      STATUS ISPORUKE     |        VRIJEME PREUZIMANJA     |    IZNOS DOSTAVE    |    IZNOS POUZEĆA    |");
    System.out.println(
        "--------------------------------------------------------------------------------------------"
            + "---------------------------------------------------------------------------------------------------");
  }

  private static void napuniTablicu(UredZaPrijem uzp, UredZaDostavu uzd) {

    String oznakaPaketa = "";
    String vrijemePrijema = "";
    String vrstaPaketa = "";
    String vrstaUsluge = "";
    String statusIsporuke = "";
    String vrijemePreuzimanja;
    Double iznosDostave = 0d;
    Double iznosPouzeća = 0d;


    for (Paket paket : uzp.listaSvihZaprimljenihPaketa) {
      oznakaPaketa = paket.getOznaka();
      vrijemePrijema = postaviDatum(paket.getVrijemePrijema());
      vrstaPaketa = paket.getVrstaPaketa();
      vrstaUsluge = paket.getUslugaDostave();
      statusIsporuke = provjeriStatus(paket, uzd);
      if (uzd.mapa.containsKey(paket)) {
        vrijemePreuzimanja = (uzd.mapa.get(paket)).toString();
      } else {
        vrijemePreuzimanja = " / ";
      }
      iznosDostave = paket.getIznosDostave();
      iznosPouzeća = paket.getIznosPouzeća();

      System.out.printf("| %-21s | %-23s | %-16s | %-16s | %-23s | %-30s | %-19s | %-19s |\n",
          oznakaPaketa, vrijemePrijema, vrstaPaketa, vrstaUsluge, statusIsporuke,
          vrijemePreuzimanja, iznosDostave, iznosPouzeća);
    }
    System.out.println(
        "------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

  }

  private static String postaviDatum(Date vp) {
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy. HH:mm:ss");
    String vrijeme = sdf.format(vp);
    return vrijeme;
  }

  private static String provjeriStatus(Paket paket, UredZaDostavu uzd) {
    if (uzd.mapa.containsKey(paket)) {
      return "Dostavljen";
    } else {
      for (Vozilo vozilo : uzd.vozila) {
        for (Paket p : vozilo.listaPaketaZaDostavu) {
          if (p.equals(paket)) {
            return "Na dostavi";
          }
        }
      }
    }
    return "Zaprimljen";
  }

}
