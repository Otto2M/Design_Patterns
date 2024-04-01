package org.foi.uzdiz.omilermat.zadaca_1.datoteke;

public class PaketFactory {

  public static PaketOpci createStandardniPaket(String vrsta, double volumen, double tezina) {
    return new StandardniPaket(vrsta, volumen, tezina);
  }

  public static PaketOpci createCustomPaket(double volumen, double tezina) {
    return new CustomPaket(volumen, tezina);
  }
}
