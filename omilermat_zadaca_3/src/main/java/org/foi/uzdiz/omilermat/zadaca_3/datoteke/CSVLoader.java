package org.foi.uzdiz.omilermat.zadaca_3.datoteke;

import java.util.List;

public interface CSVLoader<T> {
  List<T> ucitajPodatkeCSV(String CSVDatoteka);
}

