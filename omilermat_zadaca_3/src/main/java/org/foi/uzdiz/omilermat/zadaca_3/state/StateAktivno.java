package org.foi.uzdiz.omilermat.zadaca_3.state;

import java.util.ArrayList;
import java.util.List;
import org.foi.uzdiz.omilermat.zadaca_3.datoteke.Vozilo;
import org.foi.uzdiz.omilermat.zadaca_3.observer.Paket;
import org.foi.uzdiz.omilermat.zadaca_3.singleton.KalkulatorVirtualnogVremena;

public class StateAktivno implements VoziloState {

  @Override
  public void postaviStatusAktivno(Vozilo vozilo) {
    vozilo.setStatusVozila("A");
    System.out.println("Vozilo je već aktivno.");
  }

  @Override
  public void postaviStatusNeispravno(Vozilo vozilo) {
    vozilo.setStatusVozila("NI");
    vozilo.setStatus(new StateNeispravno());
    System.out.println("Status 'NI - nije ispravno' uspješno promijenjen za vozilo: "
        + vozilo.dohvatiRegistraciju());
  }

  @Override
  public void postaviStatusNeaktivno(Vozilo vozilo) {
    vozilo.setStatus(new StateNeaktivno());
    vozilo.setStatusVozila("NA");
    System.out.println("Status 'NA - nije aktivno' uspješno promijenjen za vozilo: "
        + vozilo.dohvatiRegistraciju());
  }

  @Override
  public String ukrcajPaket(Vozilo vozilo, List<Paket> listaPaketa) {
    String obavijest = "";
    List<Paket> privremenaLista = new ArrayList<>();
    int brHitnihPaketa = 0;
    int brObicnihPaketa = 0;

    for (Paket paket : listaPaketa) {
      if (vozilo.isStatusSlobodno()
          && jePrihvatljivaTezina(vozilo.getTezina(), vozilo.getTrenutnaTezinaVozila(),
              paket.getTezina())
          && imaJosProstoraUVozilu(vozilo.getKapacitet(), vozilo.getZauzetiProstorVozila(),
              izracunajVolumenPaketa(paket.getVisina(), paket.getSirina(), paket.getDuzina()))) {

        vozilo.listaPaketaZaDostavu.add(paket);
        paket.setStatus("NA DOSTAVI");
        privremenaLista.add(paket);

        if (paket.getVrstaPaketa().equals("H")) {
          brHitnihPaketa++;
        } else {
          brObicnihPaketa++;
        }
        vozilo.setBrHitnihPaketa(brHitnihPaketa);
        vozilo.setBrObicnihPaketa(brObicnihPaketa);

        obavijest = "Paket s oznakom " + paket.getOznaka()
            + " je ukrcan u dostavno vozilo. Virtualno vrijeme je: "
            + KalkulatorVirtualnogVremena.getInstance().dohvatiVirtualnoVrijeme();

        for (Paket paketZaDostavu : vozilo.listaPaketaZaDostavu) {
          vozilo.setTrenutnaTezinaVozila(
              vozilo.getTrenutnaTezinaVozila() + paketZaDostavu.getTezina());
          vozilo.setZauzetiProstorVozila(
              vozilo.getZauzetiProstorVozila() + (paketZaDostavu.getVisina()
                  * paketZaDostavu.getDuzina() * paketZaDostavu.getVisina()));
        }
      }
    }
    for (Paket paket : privremenaLista) {
      listaPaketa.remove(paket);
    }
    return obavijest;
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

}
