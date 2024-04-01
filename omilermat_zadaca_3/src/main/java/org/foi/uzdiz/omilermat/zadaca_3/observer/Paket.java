package org.foi.uzdiz.omilermat.zadaca_3.observer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.foi.uzdiz.omilermat.zadaca_3.prototype.Prototype;

public class Paket implements Subjekt, Prototype<Paket> {

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
  private double iznosDostave;
  private String status;
  public List<Observer> listaObservera = new ArrayList<>();

  public Paket(String oznaka, Date vrijemePrijema, String posiljatelj, String primatelj,
      String vrstaPaketa, double visina, double sirina, double duzina, double tezina,
      String uslugaDostave, double iznosPouzeća, double iznosDostave) {
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
    this.iznosDostave = iznosDostave;
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

  public double getIznosDostave() {
    return iznosDostave;
  }

  public void setIznosDostave(double iznosDostave) {
    this.iznosDostave = iznosDostave;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
    obavijestiOsobu();
  }

  @Override
  public String toString() {
    return oznaka + "; " + vrijemePrijema + "; " + posiljatelj + "; " + primatelj + "; "
        + vrstaPaketa + "; " + visina + "; " + sirina + "; " + duzina + "; " + tezina + "; "
        + uslugaDostave + "; " + iznosPouzeća + "; " + status;
  }

  @Override
  public void dodajOsobu(Observer observer) {
    listaObservera.add(observer);
  }

  @Override
  public void ukloniOsobu(Observer observer) {
    listaObservera.remove(observer);
  }

  @Override
  public void obavijestiOsobu() {
    for (Observer o : listaObservera) {
      o.update(status, oznaka);
    }
  }

  @Override
  public Paket cloneP() throws CloneNotSupportedException {
    Paket clonedPaket = new Paket(oznaka, vrijemePrijema, posiljatelj, primatelj, vrstaPaketa,
        visina, sirina, duzina, tezina, uslugaDostave, iznosPouzeća, iznosDostave);

    clonedPaket.setStatus(status);
    return clonedPaket;
  }


}
