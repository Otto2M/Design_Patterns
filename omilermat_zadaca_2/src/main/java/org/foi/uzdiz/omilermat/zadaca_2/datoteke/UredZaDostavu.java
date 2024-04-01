package org.foi.uzdiz.omilermat.zadaca_2.datoteke;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.foi.uzdiz.omilermat.zadaca_2.observer.Paket;
import org.foi.uzdiz.omilermat.zadaca_2.singleton.KalkulatorVirtualnogVremena;

public class UredZaDostavu {

  List<Vozilo> vozila = new ArrayList<>();
  UredZaPrijem uzp = null;
  HashMap<Paket, Date> mapa = new HashMap<>();

  public UredZaDostavu(UredZaPrijem uzp) {
    CSVLoader<Vozilo> napuniListuVozila = new UcitajVozilaCSV();
    vozila = napuniListuVozila
        .ucitajPodatkeCSV((Paths.get(MainClass.vrijednostPV).toAbsolutePath()).toString());
    this.uzp = uzp;
  }

  public void ukrcajPaketeUVoziloIDostavi() {

    List<Paket> hitniPaketi = uzp.filtrirajHitnePakete();
    String poruka = "";

    if (hitniPaketi.isEmpty()) {
      poruka = ispunjavaLiVoziloUvjeteZaUkrcavanjePaketa(uzp.listaZaprimljenihPaketa);
      String ostaliPaketiVozilo = odredVozilaZaDostavuPaketa(uzp.listaZaprimljenihPaketa);
      System.out.println(poruka);

    } else {
      poruka = ispunjavaLiVoziloUvjeteZaUkrcavanjePaketa(hitniPaketi);
      String hitniPaketiVozilo = odredVozilaZaDostavuPaketa(hitniPaketi);
      poruka = ispunjavaLiVoziloUvjeteZaUkrcavanjePaketa(uzp.listaZaprimljenihPaketa);
      String ostaliPaketiVozilo = odredVozilaZaDostavuPaketa(uzp.listaZaprimljenihPaketa);
      System.out.println(poruka);
    }

    // Pokretanje vozila prema odredištima paketa
    for (Vozilo vozilo : vozila) {
      if (vozilo.isStatusSlobodno() && (izracunajPostotakPopunjenostiVozila(vozilo.getKapacitet(),
          vozilo.getZauzetiProstorVozila()) >= (vozilo.getKapacitet() / 2)
          || izracunajPostotakPopunjenostiVozilaTezina(vozilo.getTezina(),
              vozilo.getTrenutnaTezinaVozila()) >= (vozilo.getTezina() / 2)
          || imaHitniPaket(vozilo.listaPaketaZaDostavu))) {

        System.out.println("Vozilo " + vozilo.getOpis() + " s oznakom "
            + vozilo.dohvatiRegistraciju() + " krenulo je na dostavu. Virtualno vrijeme je: "
            + KalkulatorVirtualnogVremena.getInstance().trenutnoVirtualnoVrijeme);

        vozilo.setStatusSlobodno(false);

        int vi = Integer.parseInt(MainClass.vrijednostVI);
        Calendar cal = Calendar.getInstance();
        cal.setTime(KalkulatorVirtualnogVremena.getInstance().trenutnoVirtualnoVrijeme);
        cal.add(Calendar.MINUTE, vi);

        vozilo.setVrijemeSljedeceIsporuke(cal.getTime());

      }
      if (!vozilo.isStatusSlobodno()) {
        dostaviPaket(vozilo);
      }
    }
  }

  public String odredVozilaZaDostavuPaketa(List<Paket> paketi) {

    String zaduzenoVoziloZaDostavu = null;

    int idPodrucja = uzp.odrediAdresuPaketa(paketi);
    zaduzenoVoziloZaDostavu = pronadiVoziloZaPodrucje(vozila, idPodrucja);

    return zaduzenoVoziloZaDostavu;
  }

  private static String pronadiVoziloZaPodrucje(List<Vozilo> vozila, int idPodrucje) {
    int najmanjiIndeks = Integer.MAX_VALUE;
    String registracija = null;
    for (Vozilo vozilo : vozila) {
      String[] podrucjaPoRangu = vozilo.getPodrucjaPoRangu().split(",");
      for (int i = 0; i < podrucjaPoRangu.length; i++) {
        if (Integer.parseInt(podrucjaPoRangu[i]) == idPodrucje) {
          if (i < najmanjiIndeks) {
            najmanjiIndeks = i;
            registracija = vozilo.dohvatiRegistraciju();
          }
        }
      }
    }
    return registracija;
  }

  public String ispunjavaLiVoziloUvjeteZaUkrcavanjePaketa(List<Paket> listaPaketa) {
    String obavijest = "";

    for (Vozilo vozilo : vozila) {
      if (KalkulatorVirtualnogVremena.getInstance().jeLiVrijemeUnutarRadnogVremena()) {
        vozilo.ukrcajPakete(listaPaketa); // state
      } else {
        System.out.println("Radno vrijeme je završilo!");
      }
    }

    return obavijest;
  }

  private void dostaviPaket(Vozilo vozilo) {
    // dostava paketa
    List<Paket> listaDostavljenihPaketa = new ArrayList<>();
    List<Paket> privremenaLista = new ArrayList<>();
    Date vrijeme;

    vrijeme = KalkulatorVirtualnogVremena.getInstance().dohvatiVirtualnoVrijeme();
    // System.out.println("LISTA PAKETA ZA DOSTAVU: " + vozilo.listaPaketaZaDostavu);

    if (!vrijeme.before(vozilo.getVrijemeSljedeceIsporuke())) {

      if (vozilo.listaPaketaZaDostavu.size() > 0) {
        System.out.println("Paket " + vozilo.listaPaketaZaDostavu.get(0).getOznaka()
            + " je isporučen primatelju u "
            + KalkulatorVirtualnogVremena.getInstance().dohvatiVirtualnoVrijeme() + " sati.");

        listaDostavljenihPaketa.add(vozilo.listaPaketaZaDostavu.get(0));
        mapa.put(vozilo.listaPaketaZaDostavu.get(0), vozilo.getVrijemeSljedeceIsporuke());

        vozilo.listaPaketaZaDostavu.get(0).setStatus("DOSTAVLJEN");

        vozilo.listaPaketaZaDostavu.remove(0);
        // System.out.println(
        // " --- LISTA PAKETA ZA DOSTAVU S PAKETOM MANJE: " + vozilo.listaPaketaZaDostavu);

      } else {
        vozilo.setStatusSlobodno(true);
      }

      int vi = Integer.parseInt(MainClass.vrijednostVI);
      Calendar cal = Calendar.getInstance();
      cal.setTime(KalkulatorVirtualnogVremena.getInstance().trenutnoVirtualnoVrijeme);
      cal.add(Calendar.MINUTE, vi);
      vozilo.setVrijemeSljedeceIsporuke(cal.getTime());
    }
  }


  public double izracunajVolumenPaketa(double visina, double sirina, double duzina) {
    return visina * sirina * duzina;
  }

  public double izracunajUkupnuCijenuPaketa(double osnovnaCijena, double cijenaP, double cijenaT,
      double volumen, double tezina) {
    return osnovnaCijena + (cijenaP * volumen) + (cijenaT * tezina);
  }

  public boolean imaJosProstoraUVozilu(double ukupanVolumen, double zauzetVolumen,
      double volumenPaketa) {
    return (zauzetVolumen + volumenPaketa) <= ukupanVolumen;
  }

  public boolean jePrihvatljivaTezina(double maxTezina, double trenutnaTezina,
      double tezinaNovogPaketa) {
    return (trenutnaTezina + tezinaNovogPaketa) <= maxTezina;
  }

  public double izracunajPostotakPopunjenostiVozila(double ukupniVolumen, double zauzetiVolumen) {
    return (zauzetiVolumen / ukupniVolumen) * 100;
  }

  public double izracunajPostotakPopunjenostiVozilaTezina(double ukupnaTezina,
      double trenutnaTezina) {
    return (trenutnaTezina / ukupnaTezina) * 100;
  }

  private boolean imaHitniPaket(List<Paket> listaPaketaZaDostavu) {
    for (Paket paket : listaPaketaZaDostavu) {
      if (paket.getVrstaPaketa().equals("H")) {
        return true;
      }
    }
    return false;
  }

}
