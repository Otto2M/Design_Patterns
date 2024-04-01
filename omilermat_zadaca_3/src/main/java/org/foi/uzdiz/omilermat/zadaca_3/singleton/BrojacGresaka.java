package org.foi.uzdiz.omilermat.zadaca_3.singleton;

public class BrojacGresaka {

  private static BrojacGresaka instance;
  private int rbrPogreske;

  private BrojacGresaka() {
    this.rbrPogreske = 1;
  }

  public static BrojacGresaka getInstance() {
    if (instance == null) {
      instance = new BrojacGresaka();
    }
    return instance;
  }

  public int dohvatiVrijednost() {
    return rbrPogreske;
  }

  public void increment() {
    rbrPogreske++;
  }

}
