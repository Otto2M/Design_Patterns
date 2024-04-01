package org.foi.uzdiz.omilermat.zadaca_2.composite;

import java.util.List;

public interface PodrucjeComponent {

  void prikaziInformacijeOPodrucjima();

  int getId();

  List<PodrucjeComponent> getListaPodrucja();

}
