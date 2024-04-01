package org.foi.uzdiz.omilermat.zadaca_3.memento;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.foi.uzdiz.omilermat.zadaca_3.datoteke.Vozilo;
import org.foi.uzdiz.omilermat.zadaca_3.observer.Paket;

public class Originator {
  private List<Paket> trenutnoStanjePaketa;
  private List<Vozilo> trenutnoStanjeVozila;
  private Date trenutnoVirtualnoVrijeme;

  public Memento createMemento() {
    List<Paket> kopijaPaketa = new ArrayList<>(this.trenutnoStanjePaketa);
    List<Vozilo> kopijaVozila = new ArrayList<>(this.trenutnoStanjeVozila);

    return new Memento(kopijaPaketa, kopijaVozila, this.trenutnoVirtualnoVrijeme);
  }

  public void setMemento(Memento memento) {
    this.trenutnoStanjePaketa = memento.getStanjePaketa();
    this.trenutnoStanjeVozila = new ArrayList<>(memento.getStanjeVozila());
    this.trenutnoVirtualnoVrijeme = memento.getVirtualnoVrijeme();
  }

  public List<Paket> getTrenutnoStanjePaketa() {
    return trenutnoStanjePaketa;
  }

  public void setTrenutnoStanjePaketa(List<Paket> trenutnoStanjePaketa) {
    this.trenutnoStanjePaketa = trenutnoStanjePaketa;
  }

  public List<Vozilo> getTrenutnoStanjeVozila() {
    return trenutnoStanjeVozila;
  }

  public void setTrenutnoStanjeVozila(List<Vozilo> trenutnoStanjeVozila) {
    this.trenutnoStanjeVozila = trenutnoStanjeVozila;
  }

  public Date getTrenutnoVirtualnoVrijeme() {
    return trenutnoVirtualnoVrijeme;
  }

  public void setTrenutnoVirtualnoVrijeme(Date trenutnoVirtualnoVrijeme) {
    this.trenutnoVirtualnoVrijeme = trenutnoVirtualnoVrijeme;
  }
}
