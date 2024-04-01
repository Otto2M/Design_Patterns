package org.foi.uzdiz.omilermat.zadaca_2.datoteke;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.foi.uzdiz.omilermat.zadaca_2.composite.Podrucje;
import org.foi.uzdiz.omilermat.zadaca_2.observer.Osoba;
import org.foi.uzdiz.omilermat.zadaca_2.observer.Paket;
import org.foi.uzdiz.omilermat.zadaca_2.proxy.IspisVoznjeVozilaProxy;
import org.foi.uzdiz.omilermat.zadaca_2.singleton.KalkulatorVirtualnogVremena;
import org.foi.uzdiz.omilermat.zadaca_2.visitor.IspisSegmentaVoznjeVozilaVisitor;
import org.foi.uzdiz.omilermat.zadaca_2.visitor.IspisSvihVozilaVisitor;
import org.foi.uzdiz.omilermat.zadaca_2.visitor.VoziloVisitor;

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

    // if (vs == null || ms == null || pr == null || kr == null || vp == null || pv == null
    // || pp == null || mt == null || vi == null) {
    // System.out.println("Nisu navedeni svi argumenti/opcije!");
    // return;
    // }

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

      CSVLoader<VrstaPaketa> napuniListuVrstaPaketa = new UcitajVrstePaketaCSV();
      List<VrstaPaketa> vrstaPaketa = napuniListuVrstaPaketa
          .ucitajPodatkeCSV((Paths.get(vrijednostVP).toAbsolutePath()).toString());

      CSVLoader<Vozilo> napuniListuVozila = new UcitajVozilaCSV();
      List<Vozilo> vozila =
          napuniListuVozila.ucitajPodatkeCSV((Paths.get(vrijednostPV).toAbsolutePath()).toString());

      CSVLoader<Paket> napuniListuPaketa = new UcitajPaketeCSV();
      List<Paket> paketi =
          napuniListuPaketa.ucitajPodatkeCSV((Paths.get(vrijednostPP).toAbsolutePath()).toString());

      CSVLoader<Ulica> napuniListuUlica = new UcitajUliceCSV();
      List<Ulica> ulice =
          napuniListuUlica.ucitajPodatkeCSV((Paths.get(vrijednostPU).toAbsolutePath()).toString());

      CSVLoader<Mjesto> napuniListuMjesta = new UcitajMjestaCSV();
      List<Mjesto> mjesta =
          napuniListuMjesta.ucitajPodatkeCSV((Paths.get(vrijednostPM).toAbsolutePath()).toString());

      CSVLoader<Osoba> napuniListuOsoba = new UcitajOsobeCSV();
      List<Osoba> osobe =
          napuniListuOsoba.ucitajPodatkeCSV((Paths.get(vrijednostPO).toAbsolutePath()).toString());

      CSVLoader<Podrucje> napuniListuPodrucja = new UcitajPodrucjaCSV();
      List<Podrucje> podrucja = napuniListuPodrucja
          .ucitajPodatkeCSV((Paths.get(vrijednostPMU).toAbsolutePath()).toString());

      UredZaPrijem uredZaPrijem = new UredZaPrijem();
      UredZaDostavu uredZaDostavu = new UredZaDostavu(uredZaPrijem);

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

      while (true) {
        System.out.println(
            "Unesite naredbu: \n IP za ispis podataka \n VR za izvršavanje \n SV za pregled podataka za sva vozila \n VV za pregled podataka za vožnje odabranog vozila \n VS za pregled podataka za segmente vožnje \n PP za pregled područja s hijerarhijskim prikazom \n PS za promjenu statusa vozila \n PO za promjenu statusa slanja obavijest \n Q za izlaz: ");

        String cijelaNaredba = System.console().readLine();
        String[] naredbaSplit = cijelaNaredba.split(" ");
        String naredba = naredbaSplit[0];
        Date krajnjeVrijeme;

        if (naredba.equals("IP")) {

          System.out.println("\nTRENUTNO VIRTUALNO VRIJEME: "
              + KalkulatorVirtualnogVremena.getInstance().dohvatiVirtualnoVrijeme() + "\n");
          postaviZaglavlje();
          napuniTablicu(uredZaPrijem, uredZaDostavu);

        } else if (naredba.equals("VR")) {

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
        } else if (naredba.equals("SV")) {

          VoziloVisitor ispisSvihVozilaVisitor = new IspisSvihVozilaVisitor();
          postaviZaglavljeSvaVozila();

          for (Vozilo v : vozila) {
            v.accept(ispisSvihVozilaVisitor);
          }

        } else if (naredba.equals("VV")) {

          String registracija = naredbaSplit[1].trim();
          VoziloVisitor ispisVozilaProxy = new IspisVoznjeVozilaProxy();

          for (Vozilo vozilo : vozila) {
            if (vozilo.dohvatiRegistraciju().equals(registracija)) {
              vozilo.accept(ispisVozilaProxy);
            }
          }

        } else if (naredba.equals("VS")) {

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

        } else if (naredba.equals("PP")) {
          uredZaPrijem.kreirajPodrucjaMjestaUlice();

        } else if (naredba.equals("PS")) {

          String registracija = naredbaSplit[1].trim();
          String status = naredbaSplit[2].trim();

          System.out.println("TRENUTNO VIRTUALNO VRIJEME: "
              + KalkulatorVirtualnogVremena.getInstance().dohvatiVirtualnoVrijeme() + "\n");

          postaviStatusVozila(vozila, registracija, status);

        } else if (naredba.equals("PO")) {

          String imeOsobe = dajOsobu(cijelaNaredba);
          String imeOsobeTrim = imeOsobe.trim();
          String oznakaPaketa = naredbaSplit[3];
          String status = naredbaSplit[4];

          promijeniStatusSlanjaObavijesti(paketi, osobe, oznakaPaketa, imeOsobeTrim, status);

        } else if (naredba.equals("Q")) {
          System.out.println("Izlaz iz programa.");
          System.exit(0);
        } else {
          System.out.println("Nepoznata naredba.");
        }
      }
    } else {
      System.out.println("Problem kod učitavanja datoteke s parametrima! Prekidam program...");
      System.exit(0);
    }
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

  private static void postaviZaglavlje() {
    System.out.println(
        "--------------------------------------------------------------------------------------------"
            + "----------------------------------------------------------------------------------------------------------");
    System.out.println(
        "|     OZNAKA PAKETA     |     VRIJEME PRIJEMA     |   VRSTA PAKETA   |   VRSTA USLUGE  "
            + "|      STATUS ISPORUKE     |        VRIJEME PREUZIMANJA     |    IZNOS DOSTAVE    |    IZNOS POUZEĆA    |");
    System.out.println(
        "--------------------------------------------------------------------------------------------"
            + "----------------------------------------------------------------------------------------------------------");
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
        "-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

    // System.out
    // .println(oznakaPaketa + " " + vrijemePrijema + " " + vrstaPaketa + " " + vrstaUsluge + " "
    // + statusIsporuke + " " + vrijemePreuzimanja + " " + iznosDostave + " " + iznosPouzeća);

  }

  private static String provjeriStatus(Paket paket, UredZaDostavu uzd) {
    if (uzd.mapa.containsKey(paket)) {
      return "Dostavljen";
    } else {
      for (Vozilo vozilo : uzd.vozila) {
        for (Paket p : vozilo.listaPaketaZaDostavu) {
          if (p.equals(paket)) {
            return "U dostavi";
          }
        }
      }
    }
    return "Zaprimljen";
  }

  private static String postaviDatum(Date vp) {
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy. HH:mm:ss");
    String vrijeme = sdf.format(vp);
    return vrijeme;
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
