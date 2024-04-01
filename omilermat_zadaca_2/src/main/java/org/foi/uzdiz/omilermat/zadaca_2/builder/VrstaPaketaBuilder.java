package org.foi.uzdiz.omilermat.zadaca_2.builder;

import org.foi.uzdiz.omilermat.zadaca_2.datoteke.VrstaPaketa;

public class VrstaPaketaBuilder implements BuilderVP {
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

  @Override
  public VrstaPaketaBuilder oznaka(String oznaka) {
    this.oznaka = oznaka;
    return this;
  }

  @Override
  public VrstaPaketaBuilder opis(String opis) {
    this.opis = opis;
    return this;
  }

  @Override
  public VrstaPaketaBuilder visina(double visina) {
    this.visina = visina;
    return this;
  }

  @Override
  public VrstaPaketaBuilder sirina(double sirina) {
    this.sirina = sirina;
    return this;
  }

  @Override
  public VrstaPaketaBuilder duzina(double duzina) {
    this.duzina = duzina;
    return this;
  }

  @Override
  public VrstaPaketaBuilder maksTezina(double maksTezina) {
    this.maksTezina = maksTezina;
    return this;
  }

  @Override
  public VrstaPaketaBuilder cijena(double cijena) {
    this.cijena = cijena;
    return this;
  }

  @Override
  public VrstaPaketaBuilder cijenaHitno(double cijenaHitno) {
    this.cijenaHitno = cijenaHitno;
    return this;
  }

  @Override
  public VrstaPaketaBuilder cijenaP(double cijenaP) {
    this.cijenaP = cijenaP;
    return this;
  }

  @Override
  public VrstaPaketaBuilder cijenaT(double cijenaT) {
    this.cijenaT = cijenaT;
    return this;
  }

  public VrstaPaketa build() {
    return new VrstaPaketa(oznaka, opis, visina, sirina, duzina, maksTezina, cijena, cijenaHitno,
        cijenaP, cijenaT);
  }
}

