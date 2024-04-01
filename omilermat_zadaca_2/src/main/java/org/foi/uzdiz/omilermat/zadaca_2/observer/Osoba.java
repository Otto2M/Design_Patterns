package org.foi.uzdiz.omilermat.zadaca_2.observer;

public class Osoba implements Observer {

  private String imeIPrezimeOsobe;
  private int grad;
  private int ulica;
  private int kucniBroj;

  public Osoba(String imeIPrezimeOsobe, int grad, int ulica, int kucniBroj) {
    this.imeIPrezimeOsobe = imeIPrezimeOsobe;
    this.grad = grad;
    this.ulica = ulica;
    this.kucniBroj = kucniBroj;
  }

  public String getImeIPrezimeOsobe() {
    return imeIPrezimeOsobe;
  }

  public int getGrad() {
    return grad;
  }

  public int getUlica() {
    return ulica;
  }

  public int getKucniBroj() {
    return kucniBroj;
  }

  public void setImeIPrezimeOsobe(String imeIPrezimeOsobe) {
    this.imeIPrezimeOsobe = imeIPrezimeOsobe;
  }

  public void setGrad(int grad) {
    this.grad = grad;
  }

  public void setUlica(int ulica) {
    this.ulica = ulica;
  }

  public void setKucniBroj(int kucniBroj) {
    this.kucniBroj = kucniBroj;
  }

  @Override
  public void update(String event, String oznaka) {
    System.out.println(
        "OBAVIJEST: Obavijesti osobu: " + imeIPrezimeOsobe + " - paket " + oznaka + " je " + event);
  }


}
