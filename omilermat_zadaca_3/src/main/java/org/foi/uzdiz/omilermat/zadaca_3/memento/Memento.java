package org.foi.uzdiz.omilermat.zadaca_3.memento;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.foi.uzdiz.omilermat.zadaca_3.datoteke.Vozilo;
import org.foi.uzdiz.omilermat.zadaca_3.observer.Paket;

public class Memento {
  private List<Paket> stanjePaketa;
  private List<Vozilo> stanjeVozila;
  private Date virtualnoVrijeme;

  public Memento(List<Paket> stanjePaketa, List<Vozilo> stanjeVozila, Date virtualnoVrijeme) {
    this.stanjePaketa = stanjePaketa;
    this.stanjeVozila = new ArrayList<>(stanjeVozila);
    this.virtualnoVrijeme = virtualnoVrijeme;
  }

  public List<Paket> getStanjePaketa() {
    return stanjePaketa;
  }

  public List<Vozilo> getStanjeVozila() {
    return new ArrayList<>(stanjeVozila);
  }

  public Date getVirtualnoVrijeme() {
    return virtualnoVrijeme;
  }

}
