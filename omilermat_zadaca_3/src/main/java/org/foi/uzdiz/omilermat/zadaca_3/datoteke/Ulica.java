package org.foi.uzdiz.omilermat.zadaca_3.datoteke;

import java.util.List;
import org.foi.uzdiz.omilermat.zadaca_3.composite.PodrucjeComponent;

public class Ulica implements PodrucjeComponent {

  private int id;
  private String naziv;
  private Double gps_lat_1;
  private Double gps_lon_1;
  private Double gps_lat_2;
  private Double gps_lon_2;
  private int najveciKucniBroj;

  public Ulica(int id, String naziv, Double gps_lat_1, Double gps_lon_1, Double gps_lat_2,
      Double gps_lon_2, int najveciKucniBroj) {
    this.id = id;
    this.naziv = naziv;
    this.gps_lat_1 = gps_lat_1;
    this.gps_lon_1 = gps_lon_1;
    this.gps_lat_2 = gps_lat_2;
    this.gps_lon_2 = gps_lon_2;
    this.najveciKucniBroj = najveciKucniBroj;
  }

  public int getId() {
    return id;
  }

  public String getNaziv() {
    return naziv;
  }

  public Double getGps_lat_1() {
    return gps_lat_1;
  }

  public Double getGps_lon_1() {
    return gps_lon_1;
  }

  public Double getGps_lat_2() {
    return gps_lat_2;
  }

  public Double getGps_lon_2() {
    return gps_lon_2;
  }

  public int getNajveciKucniBroj() {
    return najveciKucniBroj;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setNaziv(String naziv) {
    this.naziv = naziv;
  }

  public void setGps_lat_1(Double gps_lat_1) {
    this.gps_lat_1 = gps_lat_1;
  }

  public void setGps_lon_1(Double gps_lon_1) {
    this.gps_lon_1 = gps_lon_1;
  }

  public void setGps_lat_2(Double gps_lat_2) {
    this.gps_lat_2 = gps_lat_2;
  }

  public void setGps_lon_2(Double gps_lon_2) {
    this.gps_lon_2 = gps_lon_2;
  }

  public void setNajveciKucniBroj(int najveciKucniBroj) {
    this.najveciKucniBroj = najveciKucniBroj;
  }

  @Override
  public String toString() {
    return id + "; " + naziv + "; " + gps_lat_1 + "; " + gps_lon_1 + "; " + gps_lat_2 + "; "
        + gps_lon_2 + "; " + najveciKucniBroj;
  }

  @Override
  public void prikaziInformacijeOPodrucjima() {
    System.out.println("        Ulica: " + id + " - " + naziv);
  }

  @Override
  public List<PodrucjeComponent> getListaPodrucja() {
    // TODO Auto-generated method stub
    return null;
  }

}
