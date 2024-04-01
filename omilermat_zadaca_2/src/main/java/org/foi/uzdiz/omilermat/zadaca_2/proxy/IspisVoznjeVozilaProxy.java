package org.foi.uzdiz.omilermat.zadaca_2.proxy;

import org.foi.uzdiz.omilermat.zadaca_2.datoteke.Vozilo;
import org.foi.uzdiz.omilermat.zadaca_2.visitor.IspisVoznjeVozilaVisitor;
import org.foi.uzdiz.omilermat.zadaca_2.visitor.VoziloVisitor;

public class IspisVoznjeVozilaProxy extends IspisVoznjeVozilaVisitor {

  VoziloVisitor stvarniVisitor = new IspisVoznjeVozilaVisitor();

  @Override
  public void visit(Vozilo vozilo) {
    if (vozilo.getStatusVozila().equals("A")) {
      vozilo.accept(stvarniVisitor);
    } else {
      System.out.println(
          "Nemoguće ispisati podatke za neaktivno ili neispravno vozilo. Prvo promijenite status vozila");
    }
  }

}
