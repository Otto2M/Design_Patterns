package org.foi.uzdiz.omilermat.zadaca_3.proxy;

import org.foi.uzdiz.omilermat.zadaca_3.datoteke.Vozilo;
import org.foi.uzdiz.omilermat.zadaca_3.visitor.IspisVoznjeVozilaVisitor;
import org.foi.uzdiz.omilermat.zadaca_3.visitor.VoziloVisitor;

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
