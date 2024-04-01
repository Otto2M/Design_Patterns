package org.foi.uzdiz.omilermat.zadaca_3.memento;

import java.util.HashMap;
import java.util.Map;

public class Caretaker {
  private Map<String, Memento> spremljenaStanja = new HashMap<>();

  public Boolean spremiStanje(String naziv, Memento memento) {
    if (spremljenaStanja.containsKey(naziv)) {
      System.out.println(
          "Nije moguće spremiti stanje pod odabranim nazivom! Mapa već sadrži stanje s nazivom: "
              + naziv);
      return false;
    } else {
      spremljenaStanja.put(naziv, memento);
      return true;
    }
  }

  public Memento dohvatiStanje(String naziv) {
    return spremljenaStanja.get(naziv);
  }

}
