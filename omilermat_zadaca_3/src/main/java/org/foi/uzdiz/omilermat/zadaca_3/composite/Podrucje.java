package org.foi.uzdiz.omilermat.zadaca_3.composite;

import java.util.ArrayList;
import java.util.List;
import org.foi.uzdiz.omilermat.zadaca_3.datoteke.Mjesto;
import org.foi.uzdiz.omilermat.zadaca_3.datoteke.Ulica;

public class Podrucje implements PodrucjeComponent {

  private int id;
  private String gradUlica;

  public final List<PodrucjeComponent> listaPodrucja;

  public Podrucje(int id, String gradUlica) {
    this.id = id;
    this.gradUlica = gradUlica;
    this.listaPodrucja = new ArrayList<>();
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getGradUlica() {
    return gradUlica;
  }

  public void setGradUlica(String gradUlica) {
    this.gradUlica = gradUlica;
  }

  public void dodajPodrucjeUListu(PodrucjeComponent podrucjeComp) {
    listaPodrucja.add(podrucjeComp);
  }

  public List<PodrucjeComponent> getListaPodrucja() {
    return listaPodrucja;
  }

  @Override
  public String toString() {
    return id + "; " + gradUlica;
  }

  @Override
  public void prikaziInformacijeOPodrucjima() {
    System.out.println("Područje: " + id);
    for (PodrucjeComponent podrucje : listaPodrucja) {
      podrucje.prikaziInformacijeOPodrucjima();
    }
  }

  public int pronadiIDPodrucjaZaUlicu(PodrucjeComponent podrucjeComp, int trazenaUlicaID) {
    if (podrucjeComp instanceof Mjesto) {
      Mjesto mjesto = (Mjesto) podrucjeComp;

      for (Ulica ulica : mjesto.getUliceID()) {
        if (ulica instanceof Ulica && ulica.getId() == trazenaUlicaID) {
          return mjesto.getId();
        }
      }
    } else if (podrucjeComp instanceof Podrucje) {
      Podrucje podrucje = (Podrucje) podrucjeComp;

      for (PodrucjeComponent podPodrucje : podrucje.getListaPodrucja()) {
        int pronadeno = pronadiIDPodrucjaZaUlicu(podPodrucje, trazenaUlicaID);
        if (pronadeno != -1) {
          return pronadeno;
        }
      }
    }
    return -1;
  }
}
