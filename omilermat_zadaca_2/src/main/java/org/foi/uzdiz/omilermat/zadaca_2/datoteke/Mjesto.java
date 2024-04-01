package org.foi.uzdiz.omilermat.zadaca_2.datoteke;

import java.util.ArrayList;
import java.util.List;
import org.foi.uzdiz.omilermat.zadaca_2.composite.PodrucjeComponent;

public class Mjesto implements PodrucjeComponent {

  private int id;
  private String naziv;
  private List<Ulica> uliceID;
  private final List<PodrucjeComponent> listaGradova;

  public Mjesto(int id, String naziv, List<Ulica> uliceID) {
    this.id = id;
    this.naziv = naziv;
    this.uliceID = uliceID;
    this.listaGradova = new ArrayList<>();
  }

  public int getId() {
    return id;
  }

  public String getNaziv() {
    return naziv;
  }

  public List<Ulica> getUliceID() {
    return uliceID;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setNaziv(String naziv) {
    this.naziv = naziv;
  }

  public void setUliceID(List<Ulica> uliceID) {
    this.uliceID = uliceID;
  }

  public void dodajUlicu(PodrucjeComponent lista) {
    listaGradova.add(lista);
  }

  @Override
  public String toString() {
    return id + "; " + naziv + "; " + uliceID;
  }

  @Override
  public void prikaziInformacijeOPodrucjima() {
    System.out.println("    Grad: " + id + " - " + naziv);
    for (PodrucjeComponent podrucje : listaGradova) {
      podrucje.prikaziInformacijeOPodrucjima();
    }
  }

  @Override
  public List<PodrucjeComponent> getListaPodrucja() {
    // TODO Auto-generated method stub
    return null;
  }

}
