package org.foi.uzdiz.omilermat.zadaca_1.datoteke;

import java.nio.file.Paths;
import java.util.List;

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

    if (vs == null || ms == null || pr == null || kr == null || vp == null || pv == null
        || pp == null || mt == null || vi == null) {
      System.out.println("Nisu navedeni svi argumenti/opcije!");
    }

    System.out.println("vs: " + vs + " ms: " + ms + " ; POCETAK RADA: " + pr + " ; KRAJ RADA: " + kr
        + " ; " + vp + " ; " + pv + " ; " + pp + " ; " + mt + " ; " + vi);

    CSVLoader<VrstaPaketa> napuniListuVrstaPaketa = new UcitajVrstePaketaCSV();
    List<VrstaPaketa> vrstaPaketa =
        napuniListuVrstaPaketa.ucitajPodatkeCSV((Paths.get(vp).toAbsolutePath()).toString());

    System.out.println("Lista paketaaaaaaa: " + vrstaPaketa);


    CSVLoader<Vozilo> napuniListuVozila = new UcitajVozilaCSV();
    List<Vozilo> vozila =
        napuniListuVozila.ucitajPodatkeCSV((Paths.get(pv).toAbsolutePath()).toString());

    System.out.println(" >>>>>>>> Lista vozilaaaaaaaaaaa: " + vozila);

    CSVLoader<Paket> napuniListuPaketa = new UcitajPaketeCSV();
    List<Paket> paketi =
        napuniListuPaketa.ucitajPodatkeCSV((Paths.get(pp).toAbsolutePath()).toString());

    System.out.println("-----Lista paketaaaaaaaaaa: " + paketi);
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
