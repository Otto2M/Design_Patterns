package org.foi.uzdiz.omilermat.zadaca_2.singleton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class KalkulatorVirtualnogVremena {
  private static volatile KalkulatorVirtualnogVremena instance;
  private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy. HH:mm:ss");
  private SimpleDateFormat radnoVrijeme = new SimpleDateFormat("HH:mm");
  private int multiplikator;
  public Date trenutnoVirtualnoVrijeme;
  private Date pocetakRada;
  private Date krajRada;

  public static KalkulatorVirtualnogVremena getInstance() {
    if (instance == null) {
      instance = new KalkulatorVirtualnogVremena();
    }
    return instance;
  }

  private KalkulatorVirtualnogVremena() {}

  public void postaviKonfParametre(String vs, int ms, String pocetakRada, String krajRada)
      throws ParseException {
    this.trenutnoVirtualnoVrijeme = sdf.parse(vs);
    this.multiplikator = ms;
    this.pocetakRada = radnoVrijeme.parse(pocetakRada);
    this.krajRada = radnoVrijeme.parse(krajRada);
  }

  public Date dohvatiVirtualnoVrijeme() {
    return this.trenutnoVirtualnoVrijeme;
  }

  public String postaviVirtualnoVrijeme() {
    Calendar cal = Calendar.getInstance();
    cal.setTime(this.trenutnoVirtualnoVrijeme);
    cal = addTime(cal, this.multiplikator);
    Date postavljenoVrijeme = cal.getTime();
    this.trenutnoVirtualnoVrijeme = cal.getTime();
    return sdf.format(postavljenoVrijeme);
  }


  public boolean provjeriVrijemeSvakuSekundu(Date krajnjeVrijeme) {

    postaviVirtualnoVrijeme();
    boolean jeRadnoVrijeme = jeLiVrijemeUnutarRadnogVremena();
    System.out.println(trenutnoVirtualnoVrijeme);
    System.out.println(krajnjeVrijeme);
    if (jeRadnoVrijeme && krajnjeVrijeme.after(trenutnoVirtualnoVrijeme)) {
      System.out.println("Virtualno vrijeme je unutar radnog vremena.");
      return true;
    } else {
      System.out.println("Virtualno vrijeme je izvan radnog vremena.");
      return false;
    }
  }

  public boolean jeLiVrijemeUnutarRadnogVremena() {
    Calendar cal = Calendar.getInstance();
    cal.setTime(trenutnoVirtualnoVrijeme);

    Calendar startCal = Calendar.getInstance();
    startCal.setTime(pocetakRada);

    Calendar endCal = Calendar.getInstance();
    endCal.setTime(krajRada);

    int trenutniSati = cal.get(Calendar.HOUR_OF_DAY);
    int trenutneMinute = cal.get(Calendar.MINUTE);

    int pocetniSati = startCal.get(Calendar.HOUR_OF_DAY);
    int pocetneMinute = startCal.get(Calendar.MINUTE);

    int zavrsniSat = endCal.get(Calendar.HOUR_OF_DAY);
    int zavrsneMinute = endCal.get(Calendar.MINUTE);

    if ((trenutniSati > pocetniSati
        || (trenutniSati == pocetniSati && trenutneMinute >= pocetneMinute))
        && (trenutniSati < zavrsniSat
            || (trenutniSati == zavrsniSat && trenutneMinute <= zavrsneMinute))) {
      return true;
    } else {
      return false;
    }
  }

  public boolean jeLiVirtualnoVrijemeUnutarRadnogVremena(String time) throws ParseException {
    Date inputTime = sdf.parse(time);
    Date trenutnoVirtualnoVrijeme = this.trenutnoVirtualnoVrijeme;

    Calendar calBefore = Calendar.getInstance();
    calBefore.setTime(trenutnoVirtualnoVrijeme);
    calBefore = addTime(calBefore, -this.multiplikator);

    Date tenMinutesBefore = calBefore.getTime();
    // System.out.println("Provjera: " + sdf.format(tenMinutesBefore) + " <= " + time + " <= "
    // + sdf.format(trenutnoVirtualnoVrijeme));

    return (inputTime.after(tenMinutesBefore) && inputTime.before(this.trenutnoVirtualnoVrijeme));
  }

  public Calendar addTime(Calendar cal, int sekunde) {
    if (sekunde > 60) {
      int sati = sekunde / 3600;
      int minute = (sekunde % 3600) / 60;
      int sek = sekunde % 60;
      if (sati > 0) {
        cal.add(Calendar.HOUR, sati);
      }
      if (minute > 0) {
        cal.add(Calendar.MINUTE, minute);
      }
      if (sek > 0) {
        cal.add(Calendar.SECOND, sekunde);
      }
    } else {
      cal.add(Calendar.SECOND, sekunde);
    }
    return cal;
  }
}
