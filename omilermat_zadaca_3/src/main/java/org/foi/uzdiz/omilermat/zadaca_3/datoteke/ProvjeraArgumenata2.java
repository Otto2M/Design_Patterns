package org.foi.uzdiz.omilermat.zadaca_3.datoteke;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProvjeraArgumenata2 {

  public HashMap<String, String> mapaKljucevaIVrijednosti = new HashMap<>();

  public boolean provjeriArgumente(Properties postavke) {

    boolean ispravno = true;
    Enumeration<?> enumeration = postavke.propertyNames();
    while (enumeration.hasMoreElements()) {
      String kljuc = (String) enumeration.nextElement();
      String vrijednost = postavke.getProperty(kljuc);

      mapaKljucevaIVrijednosti.put(kljuc, vrijednost);

      switch (kljuc) {
        case "vp":
        case "pv":
        case "pp":
        case "po":
        case "pm":
        case "pu":
        case "pmu":
          ispravno &= provjeriVrijednostZaDatoteku(kljuc, vrijednost);
          break;

        case "mt":
        case "vi":
        case "ms":
          ispravno &= provjeriVrijednostZaBroj(kljuc, vrijednost);
          break;

        case "vs":
          ispravno &= provjeriVrijednostZaDatum(kljuc, vrijednost);
          break;

        case "pr":
        case "kr":
          ispravno &= provjeriVrijednostZaVrijeme(kljuc, vrijednost);
          break;

        case "gps":
          ispravno &= provjeriVrijednostZaGPS(kljuc, vrijednost);
          break;

        case "isporuka":
          ispravno &= provjeriVrijednostZaIsporuku(kljuc, vrijednost);
          break;

        default:
          System.out.println("Nepoznat ključ: " + kljuc);
          break;

      }
    }
    return ispravno;
  }

  private boolean provjeriVrijednostZaDatoteku(String kljuc, String vrijednost) {
    String regex = "^[a-zA-Z0-9._-]*$";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(vrijednost);
    if (matcher.matches()) {
      return true;
    } else {
      System.out.println("Provjera za ključ '" + kljuc + "': Upisana vrijednost " + vrijednost
          + " nije ispravna!");
      return false;
    }
  }

  private boolean provjeriVrijednostZaBroj(String kljuc, String vrijednost) {
    try {
      int broj = Integer.parseInt(vrijednost);
      if (broj >= 0) {
        return true;
      } else {
        System.out
            .println("Provjera za ključ '" + kljuc + "': Upisana vrijednost nije ispravan broj.");
        return false;
      }
    } catch (NumberFormatException e) {
      System.out.println("Provjera za ključ '" + kljuc + "': Upisana vrijednost nije broj.");
      return false;
    }
  }

  private boolean provjeriVrijednostZaDatum(String kljuc, String vrijednost) {
    SimpleDateFormat formatDatuma = new SimpleDateFormat("dd.MM.yyyy. HH:mm:ss");
    formatDatuma.setLenient(false);
    try {
      Date date = formatDatuma.parse(vrijednost);
      return true;
    } catch (ParseException e) {
      System.out.println(
          "Provjera za ključ '" + kljuc + "': Upisani datum u datoteci nije ispravnog formata.");
      return false;
    }
  }

  private boolean provjeriVrijednostZaVrijeme(String kljuc, String vrijednost) {
    if (vrijednost.matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$")) {
      return true;
    } else {
      System.out.println(
          "Provjera za ključ '" + kljuc + "': Upisano vrijeme u datoteci nije ispravnog formata.");
      return false;
    }
  }

  private boolean provjeriVrijednostZaGPS(String kljuc, String vrijednost) {
    if (vrijednost.matches("^\\s*-?\\d+\\.\\d+,\\s*-?\\d+\\.\\d+\\s*$")) {
      return true;
    } else {
      System.out.println("Provjera za ključ '" + kljuc
          + "': Upisane GPS koordinate u datoteci nisu ispravnog formata.");
      return false;
    }
  }

  private boolean provjeriVrijednostZaIsporuku(String kljuc, String vrijednost) {
    if (vrijednost.matches("^\\d+$")) {
      return true;
    } else {
      System.out
          .println("Provjera za ključ '" + kljuc + "': Upisana vrijednost isporuke nije ispravna!");
      return false;
    }
  }


}
