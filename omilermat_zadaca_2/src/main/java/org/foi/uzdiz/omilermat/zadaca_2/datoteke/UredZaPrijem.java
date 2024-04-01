package org.foi.uzdiz.omilermat.zadaca_2.datoteke;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.foi.uzdiz.omilermat.zadaca_2.composite.Podrucje;
import org.foi.uzdiz.omilermat.zadaca_2.observer.Osoba;
import org.foi.uzdiz.omilermat.zadaca_2.observer.Paket;
import org.foi.uzdiz.omilermat.zadaca_2.singleton.KalkulatorVirtualnogVremena;

public class UredZaPrijem {

  Date vrijemePrijemaPaketa;
  private List<Paket> paketi;
  private List<VrstaPaketa> vrstaPaketa;
  private List<Osoba> osobe;
  private List<Ulica> ulice;
  private List<Mjesto> mjesta;
  private List<Podrucje> podrucja;
  public Podrucje podrucjee = new Podrucje(-1, null);

  public UredZaPrijem() {
    CSVLoader<Paket> napuniListuPaketa = new UcitajPaketeCSV();
    paketi = napuniListuPaketa
        .ucitajPodatkeCSV((Paths.get(MainClass.vrijednostPP).toAbsolutePath()).toString());

    CSVLoader<VrstaPaketa> napuniListuVrstaPaketa = new UcitajVrstePaketaCSV();
    vrstaPaketa = napuniListuVrstaPaketa
        .ucitajPodatkeCSV((Paths.get(MainClass.vrijednostVP).toAbsolutePath()).toString());

    CSVLoader<Osoba> napuniListuOsoba = new UcitajOsobeCSV();
    osobe = napuniListuOsoba
        .ucitajPodatkeCSV((Paths.get(MainClass.vrijednostPO).toAbsolutePath()).toString());

    CSVLoader<Ulica> napuniListuUlica = new UcitajUliceCSV();
    ulice = napuniListuUlica
        .ucitajPodatkeCSV((Paths.get(MainClass.vrijednostPU).toAbsolutePath()).toString());

    CSVLoader<Mjesto> napuniListuMjesta = new UcitajMjestaCSV();
    mjesta = napuniListuMjesta
        .ucitajPodatkeCSV((Paths.get(MainClass.vrijednostPM).toAbsolutePath()).toString());

    CSVLoader<Podrucje> napuniListuPodrucja = new UcitajPodrucjaCSV();
    podrucja = napuniListuPodrucja
        .ucitajPodatkeCSV((Paths.get(MainClass.vrijednostPMU).toAbsolutePath()).toString());
  }

  public List<Paket> listaZaprimljenihPaketa = new ArrayList<>();
  public List<Paket> listaSvihZaprimljenihPaketa = new ArrayList<>();

  public void provjeriZaprimljenePakete() {

    List<Paket> privremenaLista = new ArrayList<>();

    for (Paket paket : paketi) {
      for (Osoba o : osobe) {
        if (paket.getPosiljatelj().equals(o.getImeIPrezimeOsobe())) {
          if (!paket.listaObservera.contains(o)) {
            paket.dodajOsobu(o);
          }
        }
        if (paket.getPrimatelj().equals(o.getImeIPrezimeOsobe())) {
          if (!paket.listaObservera.contains(o)) {
            paket.dodajOsobu(o);
          }
        }
      }
      if (paket.getVrijemePrijema()
          .before(KalkulatorVirtualnogVremena.getInstance().trenutnoVirtualnoVrijeme)) {
        if (paket.getVrstaPaketa() == "X") {
          if (paket.getTezina() < Double.parseDouble(MainClass.vrijednostMT)) {
            listaZaprimljenihPaketa.add(paket);
            privremenaLista.add(paket);
          } else
            break;
        }

        listaZaprimljenihPaketa.add(paket);
        listaSvihZaprimljenihPaketa.add(paket);
        privremenaLista.add(paket);

        naplatiDostavu(paket);
      }
    }

    for (Paket paket : privremenaLista) {
      paket.setStatus("ZAPRIMLJEN");
      paketi.remove(paket);
    }
    privremenaLista.clear();
  }

  public int odrediAdresuPaketa(List<Paket> paketi) {

    int idPodrucja = 0;

    for (Paket paket : paketi) {
      String primatelj = paket.getPrimatelj();
      for (Osoba osoba : osobe) {
        if (osoba.getImeIPrezimeOsobe().equals(primatelj)) {
          int gradID = osoba.getGrad();
          int ulicaID = osoba.getUlica();
          int kbr = osoba.getKucniBroj();
          idPodrucja = podrucjee.pronadiIDPodrucjaZaUlicu(podrucjee, ulicaID);
        }
      }
    }
    return idPodrucja;
  }

  public HashMap<Integer, Ulica> staviUliceUHashMapu() {

    HashMap<Integer, Ulica> mapaUlica = new HashMap<Integer, Ulica>();

    for (int i = 0; i < ulice.size(); i++) {
      Ulica ulica = ulice.get(i);

      int id = ulica.getId();
      mapaUlica.put(id, ulica);
    }
    return mapaUlica;
  }

  public HashMap<Integer, Mjesto> staviMjestaUHashMapu() {

    HashMap<Integer, Mjesto> mapaMjesta = new HashMap<Integer, Mjesto>();

    for (int i = 0; i < mjesta.size(); i++) {
      Mjesto mjesto = mjesta.get(i);

      int id = mjesto.getId();

      mapaMjesta.put(id, mjesto);
    }
    return mapaMjesta;
  }

  public void kreirajPodrucjaMjestaUlice() {

    HashMap<Integer, Ulica> uliceHashMap = staviUliceUHashMapu();
    HashMap<Integer, Mjesto> mjestaHashMap = staviMjestaUHashMapu();

    for (int i = 0; i < podrucja.size(); i++) {
      Podrucje podrucje = podrucja.get(i);
      int id = podrucje.getId();
      String s = "";
      Podrucje p = new Podrucje(id, s);
      String[] gradUlica = podrucje.getGradUlica().split(",");

      HashMap<Integer, Mjesto> m = new HashMap<>();

      for (String gu : gradUlica) {
        int gradID = Integer.parseInt(gu.substring(0, 1));

        if (!m.containsKey(gradID)) {
          for (int j = 0; j < mjesta.size(); j++) {
            Mjesto mjestoObj = mjesta.get(j);
            if (mjestoObj.getId() == gradID) {
              Mjesto mjestoNovo =
                  new Mjesto(mjestoObj.getId(), mjestoObj.getNaziv(), mjestoObj.getUliceID());
              m.put(mjestoNovo.getId(), mjestoNovo);
            }
          }
        }

        if (gu.contains("*")) {
          Mjesto mjHM = mjestaHashMap.get(gradID);
          Mjesto mjPodr = m.get(gradID);
          for (Ulica u : mjHM.getUliceID()) {
            mjPodr.dodajUlicu(u);
          }
        } else {
          int ulicaId = Integer.parseInt(gu.substring(2));
          if (uliceHashMap.containsKey(ulicaId)) {
            Mjesto mje = m.get(gradID);
            mje.dodajUlicu(uliceHashMap.get(Integer.parseInt(gu.substring(2))));
          }
        }
      }
      for (Map.Entry<Integer, Mjesto> me : m.entrySet()) {
        p.dodajPodrucjeUListu(me.getValue());
      }
      podrucjee.dodajPodrucjeUListu(p);
    }
    podrucjee.prikaziInformacijeOPodrucjima();

  }

  public void naplatiDostavu(Paket paket) {

    Double iznosDostave;
    Double osnovnaCijena = 0d;
    Double cijenaP = 0d;
    Double cijenaT = 0d;
    Double tezina = 0d;
    Double visina = 0d;
    Double sirina = 0d;
    Double duzina = 0d;
    Double volumen = 0d;

    for (VrstaPaketa vrste : vrstaPaketa) {
      if (paket.getVrstaPaketa() == vrste.getOznaka()) {
        osnovnaCijena = vrste.getCijena();
        cijenaP = vrste.getCijenaP();
        cijenaT = vrste.getCijenaT();
        tezina = vrste.getMaksTezina();
        visina = vrste.getVisina();
        sirina = vrste.getSirina();
        duzina = vrste.getDuzina();
      }
    }
    volumen = izracunajVolumenPaketa(visina, sirina, duzina);

    if (paket.getUslugaDostave() == "P") {
      iznosDostave = paket.getIznosPouzeća();
    } else {
      if (paket.getVrstaPaketa() == "X") {
        iznosDostave = osnovnaCijena + (cijenaP * volumen) + (cijenaT * tezina);
        paket.setIznosDostave(iznosDostave);
      } else {
        iznosDostave = paket.getIznosPouzeća();
      }
    }
  }

  public double izracunajVolumenPaketa(double visina, double sirina, double duzina) {
    return visina * sirina * duzina;
  }

  public List<Paket> filtrirajHitnePakete() {
    List<Paket> listaHitnihPaketa = filtrirajPaketePoTipuUsluge(listaZaprimljenihPaketa, "H");
    return listaHitnihPaketa;
  }

  private static List<Paket> filtrirajPaketePoTipuUsluge(List<Paket> listaPaketa,
      String tipUsluge) {
    List<Paket> filtriraniHitniPaketi = new ArrayList<>();
    Iterator<Paket> iterator = listaPaketa.iterator();

    while (iterator.hasNext()) {
      Paket paket = iterator.next();
      if (paket.getVrstaPaketa().equals(tipUsluge)) {
        filtriraniHitniPaketi.add(paket);
        iterator.remove();
      }
    }
    return filtriraniHitniPaketi;
  }

}
