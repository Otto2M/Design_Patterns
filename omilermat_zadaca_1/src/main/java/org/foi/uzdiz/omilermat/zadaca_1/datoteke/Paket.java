package org.foi.uzdiz.omilermat.zadaca_1.datoteke;

import java.util.Date;

public class Paket {

  private String oznaka;
  private Date vrijemePrijema;
  private String posiljatelj;
  private String primatelj;
  private String vrstaPaketa;
  private double visina;
  private double sirina;
  private double duzina;
  private double tezina;
  private String uslugaDostave;
  private double iznosPouzeća;

  public Paket(String oznaka, Date vrijemePrijema, String posiljatelj, String primatelj,
      String vrstaPaketa, double visina, double sirina, double duzina, double tezina,
      String uslugaDostave, double iznosPouzeća) {
    this.oznaka = oznaka;
    this.vrijemePrijema = vrijemePrijema;
    this.posiljatelj = posiljatelj;
    this.primatelj = primatelj;
    this.vrstaPaketa = vrstaPaketa;
    this.visina = visina;
    this.sirina = sirina;
    this.duzina = duzina;
    this.tezina = tezina;
    this.uslugaDostave = uslugaDostave;
    this.iznosPouzeća = iznosPouzeća;
  }

  public String getOznaka() {
    return oznaka;
  }


  public Date getVrijemePrijema() {
    return vrijemePrijema;
  }


  public String getPosiljatelj() {
    return posiljatelj;
  }


  public String getPrimatelj() {
    return primatelj;
  }


  public String getVrstaPaketa() {
    return vrstaPaketa;
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


  public double getTezina() {
    return tezina;
  }


  public String getUslugaDostave() {
    return uslugaDostave;
  }


  public double getIznosPouzeća() {
    return iznosPouzeća;
  }

  @Override
  public String toString() {
    return oznaka + "; " + vrijemePrijema + "; " + posiljatelj + "; " + primatelj + "; "
        + vrstaPaketa + "; " + visina + "; " + sirina + "; " + duzina + "; " + tezina + "; "
        + uslugaDostave + "; " + iznosPouzeća;
  }

}
