package org.foi.uzdiz.omilermat.zadaca_3.builder;

public interface BuilderVP {
  BuilderVP oznaka(String oznaka);

  BuilderVP opis(String opis);

  BuilderVP visina(double visina);

  BuilderVP sirina(double sirina);

  BuilderVP duzina(double duzina);

  BuilderVP maksTezina(double maksTezina);

  BuilderVP cijena(double cijena);

  BuilderVP cijenaHitno(double cijenaHitno);

  BuilderVP cijenaP(double cijenaP);

  BuilderVP cijenaT(double cijenaT);
}
