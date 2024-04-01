package org.foi.uzdiz.omilermat.zadaca_1.datoteke;

public class VrstaPaketa {

  private String oznaka;
  private String opis;
  private double visina;
  private double sirina;
  private double duzina;
  private double maksTezina;
  private double cijena;
  private double cijenaHitno;
  private double cijenaP;
  private double cijenaT;

  public VrstaPaketa(String oznaka, String opis, double visina, double sirina, double duzina,
      double maksTezina, double cijena, double cijenaHitno, double cijenaP, double cijenaT) {
    this.oznaka = oznaka;
    this.opis = opis;
    this.visina = visina;
    this.sirina = sirina;
    this.duzina = duzina;
    this.maksTezina = maksTezina;
    this.cijena = cijena;
    this.cijenaHitno = cijenaHitno;
    this.cijenaP = cijenaP;
    this.cijenaT = cijenaT;
  }

  public String getOznaka() {
    return oznaka;
  }

  public String getOpis() {
    return opis;
  }

  public double getVisina() {
    return visina;
  }

  public double getSirina() {
    return sirina;
  }

  public double getDuzina() {
    return duzina;
  }

  public double getMaksTezina() {
    return maksTezina;
  }

  public double getCijena() {
    return cijena;
  }

  public double getCijenaHitno() {
    return cijenaHitno;
  }

  public double getCijenaP() {
    return cijenaP;
  }

  public double getCijenaT() {
    return cijenaT;
  }

  @Override
  public String toString() {
    return "Oznaka: " + oznaka + ", Opis: " + opis + ", Visina: " + visina + " m, Širina: " + sirina
        + " m, Dužina: " + duzina + " m, Maksimalna težina: " + maksTezina + " kg, Cijena: "
        + cijena + " kn, Cijena hitno: " + cijenaHitno + " kn, CijenaP: " + cijenaP
        + " kn, CijenaT: " + cijenaT + " kn";
  }
}
