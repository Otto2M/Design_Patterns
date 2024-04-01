package org.foi.uzdiz.omilermat.zadaca_1.datoteke;

public class VrstaPaketaBuilder {
  private String vrsta;
  private double volumen;
  private double tezina;

  public VrstaPaketaBuilder setVrsta(String vrsta) {
    this.vrsta = vrsta;
    return this;
  }

  public VrstaPaketaBuilder setVolumen(double volumen) {
    this.volumen = volumen;
    return this;
  }

  public VrstaPaketaBuilder setTezina(double tezina) {
    this.tezina = tezina;
    return this;
  }

  public PaketOpci build() {
    return new StandardniPaket(vrsta, volumen, tezina);
  }

  public PaketOpci buildCustom() {
    return new CustomPaket(volumen, tezina);
  }
}
