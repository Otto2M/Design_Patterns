package org.foi.uzdiz.omilermat.zadaca_1.datoteke;

public class Vozilo {

  private String registracija;
  private String opis;
  private Double tezina;
  private Double kapacitet;
  private int redoslijed;

  public Vozilo(String registracija, String opis, Double tezina, Double kapacitet, int redoslijed) {
    this.registracija = registracija;
    this.opis = opis;
    this.tezina = tezina;
    this.kapacitet = kapacitet;
    this.redoslijed = redoslijed;
  }

  public int dohvatiRedoslijed() {
    return redoslijed;
  }

  public String dohvatiRegistraciju() {
    return registracija;
  }

  @Override
  public String toString() {
    return registracija + "; " + opis + "; " + tezina + " kg; " + kapacitet + " m3; " + redoslijed;
  }
}
