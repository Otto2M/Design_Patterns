package org.foi.uzdiz.omilermat.zadaca_3.datoteke;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import org.foi.uzdiz.omilermat.zadaca_3.CoR.Handler;
import org.foi.uzdiz.omilermat.zadaca_3.CoR.HandlerIP;
import org.foi.uzdiz.omilermat.zadaca_3.CoR.HandlerPO;
import org.foi.uzdiz.omilermat.zadaca_3.CoR.HandlerPP;
import org.foi.uzdiz.omilermat.zadaca_3.CoR.HandlerPPV;
import org.foi.uzdiz.omilermat.zadaca_3.CoR.HandlerPS;
import org.foi.uzdiz.omilermat.zadaca_3.CoR.HandlerQ;
import org.foi.uzdiz.omilermat.zadaca_3.CoR.HandlerSPV;
import org.foi.uzdiz.omilermat.zadaca_3.CoR.HandlerSV;
import org.foi.uzdiz.omilermat.zadaca_3.CoR.HandlerVR;
import org.foi.uzdiz.omilermat.zadaca_3.CoR.HandlerVS;
import org.foi.uzdiz.omilermat.zadaca_3.CoR.HandlerVV;
import org.foi.uzdiz.omilermat.zadaca_3.composite.Podrucje;
import org.foi.uzdiz.omilermat.zadaca_3.memento.Caretaker;
import org.foi.uzdiz.omilermat.zadaca_3.observer.Osoba;
import org.foi.uzdiz.omilermat.zadaca_3.observer.Paket;
import org.foi.uzdiz.omilermat.zadaca_3.singleton.BrojacGresaka;
import org.foi.uzdiz.omilermat.zadaca_3.singleton.KalkulatorVirtualnogVremena;

public class MainClass {

  public static String vs;
  public static String pr;
  public static String ms;
  public static String kr;
  public static String vp;
  public static String pv;
  public static String pp;
  public static String mt;
  public static String vi;
  public static String vrijednostVP;
  public static String vrijednostPV;
  public static String vrijednostPP;
  public static String vrijednostPU;
  public static String vrijednostPM;
  public static String vrijednostMT;
  public static String vrijednostVI;
  public static String vrijednostPO;
  public static String vrijednostPMU;

  public static Caretaker caretaker = new Caretaker();
  public static BrojacGresaka rbrPogreske = BrojacGresaka.getInstance();

  public static void main(String[] args) {

    vs = dohvatiParsiranuVrijednost(args, "--vs");
    ms = dohvatiParsiranuVrijednost(args, "--ms");
    pr = dohvatiParsiranuVrijednost(args, "--pr");
    kr = dohvatiParsiranuVrijednost(args, "--kr");
    vp = dohvatiParsiranuVrijednost(args, "--vp");
    pv = dohvatiParsiranuVrijednost(args, "--pv");
    pp = dohvatiParsiranuVrijednost(args, "--pp");
    mt = dohvatiParsiranuVrijednost(args, "--mt");
    vi = dohvatiParsiranuVrijednost(args, "--vi");

    Properties postavke = ucitajDatoteku(args);
    ProvjeraArgumenata2 provjera = new ProvjeraArgumenata2();
    boolean ispravno = provjera.provjeriArgumente(postavke);

    if (ispravno) {

      vrijednostVP = provjera.mapaKljucevaIVrijednosti.get("vp");
      vrijednostPV = provjera.mapaKljucevaIVrijednosti.get("pv");
      vrijednostPP = provjera.mapaKljucevaIVrijednosti.get("pp");
      vrijednostPU = provjera.mapaKljucevaIVrijednosti.get("pu");
      vrijednostPM = provjera.mapaKljucevaIVrijednosti.get("pm");
      vrijednostMT = provjera.mapaKljucevaIVrijednosti.get("mt");
      vrijednostVI = provjera.mapaKljucevaIVrijednosti.get("vi");
      vrijednostPO = provjera.mapaKljucevaIVrijednosti.get("po");
      vrijednostPMU = provjera.mapaKljucevaIVrijednosti.get("pmu");

      CSVLoader<VrstaPaketa> napuniListuVrstaPaketa = new UcitajVrstePaketaCSV(rbrPogreske);
      List<VrstaPaketa> vrstaPaketa = napuniListuVrstaPaketa
          .ucitajPodatkeCSV((Paths.get(vrijednostVP).toAbsolutePath()).toString());

      CSVLoader<Vozilo> napuniListuVozila = new UcitajVozilaCSV(rbrPogreske);
      List<Vozilo> vozila =
          napuniListuVozila.ucitajPodatkeCSV((Paths.get(vrijednostPV).toAbsolutePath()).toString());

      CSVLoader<Paket> napuniListuPaketa = new UcitajPaketeCSV(rbrPogreske, vrstaPaketa);
      List<Paket> paketi =
          napuniListuPaketa.ucitajPodatkeCSV((Paths.get(vrijednostPP).toAbsolutePath()).toString());

      CSVLoader<Ulica> napuniListuUlica = new UcitajUliceCSV(rbrPogreske);
      List<Ulica> ulice =
          napuniListuUlica.ucitajPodatkeCSV((Paths.get(vrijednostPU).toAbsolutePath()).toString());

      CSVLoader<Mjesto> napuniListuMjesta = new UcitajMjestaCSV(rbrPogreske, ulice);
      List<Mjesto> mjesta =
          napuniListuMjesta.ucitajPodatkeCSV((Paths.get(vrijednostPM).toAbsolutePath()).toString());

      CSVLoader<Osoba> napuniListuOsoba = new UcitajOsobeCSV(rbrPogreske);
      List<Osoba> osobe =
          napuniListuOsoba.ucitajPodatkeCSV((Paths.get(vrijednostPO).toAbsolutePath()).toString());

      CSVLoader<Podrucje> napuniListuPodrucja = new UcitajPodrucjaCSV(rbrPogreske);
      List<Podrucje> podrucja = napuniListuPodrucja
          .ucitajPodatkeCSV((Paths.get(vrijednostPMU).toAbsolutePath()).toString());

      UredZaPrijem uredZaPrijem =
          new UredZaPrijem(ulice, paketi, vrstaPaketa, osobe, mjesta, podrucja);
      UredZaDostavu uredZaDostavu = new UredZaDostavu(uredZaPrijem, vozila);

      String vrijednostVS = provjera.mapaKljucevaIVrijednosti.get("vs");
      String vrijednostMS = provjera.mapaKljucevaIVrijednosti.get("ms");
      String vrijednostPR = provjera.mapaKljucevaIVrijednosti.get("pr");
      String vrijednostKR = provjera.mapaKljucevaIVrijednosti.get("kr");

      try {
        KalkulatorVirtualnogVremena.getInstance().postaviKonfParametre(vrijednostVS,
            Integer.parseInt(vrijednostMS), vrijednostPR, vrijednostKR);
      } catch (NumberFormatException e1) {
        e1.printStackTrace();
      } catch (ParseException e1) {
        e1.printStackTrace();
      }

      Handler korijenskiHandler = new HandlerIP().setNext(new HandlerVR()).setNext(new HandlerSV())
          .setNext(new HandlerVV()).setNext(new HandlerVS()).setNext(new HandlerPP())
          .setNext(new HandlerPS()).setNext(new HandlerPO()).setNext(new HandlerSPV(caretaker))
          .setNext(new HandlerPPV(caretaker)).setNext(new HandlerQ());

      while (true) {
        System.out.println(
            "\nUnesite naredbu: \n IP za ispis podataka \n      IP [1|2|3] \n         - 1 za ispis dostavljenih paketa,\n         - 2 za ispis svih zaprimljenih paketa,\n         - 3 za ispis svih paketa u dostavi \n "
                + "     IP [t|v|s|d|vol] \n VR za izvršavanje \n SV za pregled podataka za sva vozila \n VV za pregled podataka za vožnje odabranog vozila \n "
                + "VS za pregled podataka za segmente vožnje \n PP za pregled područja s hijerarhijskim prikazom \n PS za promjenu statusa vozila \n PO za promjenu statusa slanja obavijest "
                + "\n SPV za spremanje trenutnog stanja virtualnog sata, paketa i vozila \n PPV za povratak spremljenog stanja virtualnog sata, paketa i vozila \n Q za izlaz: ");

        String cijelaNaredba = System.console().readLine();
        String[] naredbaSplit = cijelaNaredba.split(" ");
        Date krajnjeVrijeme = null;

        korijenskiHandler.obradiKomandu(cijelaNaredba, naredbaSplit, uredZaPrijem, uredZaDostavu,
            vozila, paketi, osobe, krajnjeVrijeme);
      }

    } else {
      System.out.println("Problem kod učitavanja datoteke s parametrima! Prekidam program...");
      System.exit(0);
    }
  }

  public static Properties ucitajDatoteku(String[] args) {
    var datoteka = args[0];
    var putanja = Path.of(datoteka);
    Properties postavke = new Properties();

    if (Files.exists(putanja) && (!Files.isDirectory(putanja) || Files.isReadable(putanja))) {

      try {
        postavke.load(Files.newInputStream(putanja));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return postavke;
  }

  private static String dohvatiParsiranuVrijednost(String[] args, String key) {
    for (int i = 0; i < args.length - 1; i++) {
      if (args[i].equals(key)) {
        return args[i + 1];
      }
    }
    return null;
  }

}
