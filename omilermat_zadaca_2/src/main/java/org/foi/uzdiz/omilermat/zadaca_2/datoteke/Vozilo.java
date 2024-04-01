package org.foi.uzdiz.omilermat.zadaca_2.datoteke;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.foi.uzdiz.omilermat.zadaca_2.observer.Paket;
import org.foi.uzdiz.omilermat.zadaca_2.state.StateAktivno;
import org.foi.uzdiz.omilermat.zadaca_2.state.VoziloState;
import org.foi.uzdiz.omilermat.zadaca_2.visitor.Visitable;
import org.foi.uzdiz.omilermat.zadaca_2.visitor.VoziloVisitor;

public class Vozilo implements Visitable {

  private String registracija;
  private String opis;
  private Double tezina;
  private Double kapacitet;
  private int redoslijed;
  private boolean statusSlobodno;
  public List<Paket> listaPaketaZaDostavu = new ArrayList<>();
  private Double trenutnaTezinaVozila = 0.0;
  private Double zauzetiProstorVozila = 0.0;
  private Date vrijemeSljedeceIsporuke;
  private int prosjecnaBrzina;
  private String podrucjaPoRangu;
  private VoziloState status;
  private String statusVozila;
  private int brHitnihPaketa = 0;
  private int brObicnihPaketa = 0;
  private int brIsporucenihPaketa = 0;

  public Vozilo(String registracija, String opis, Double tezina, Double kapacitet, int redoslijed,
      boolean statusSlobodno, int prosjecnaBrzina, String podrucjaPoRangu, String statusVozila) {
    this.registracija = registracija;
    this.opis = opis;
    this.tezina = tezina;
    this.kapacitet = kapacitet;
    this.redoslijed = redoslijed;
    this.statusSlobodno = true;
    this.prosjecnaBrzina = prosjecnaBrzina;
    this.podrucjaPoRangu = podrucjaPoRangu;
    this.status = new StateAktivno();
    this.statusVozila = statusVozila;
  }

  public int dohvatiRedoslijed() {
    return redoslijed;
  }

  public String dohvatiRegistraciju() {
    return registracija;
  }

  public String getOpis() {
    return opis;
  }

  public Double getTezina() {
    return tezina;
  }

  public Double getKapacitet() {
    return kapacitet;
  }

  public boolean isStatusSlobodno() {
    return statusSlobodno;
  }

  public void setTezina(Double tezina) {
    this.tezina = tezina;
  }

  public void setKapacitet(Double kapacitet) {
    this.kapacitet = kapacitet;
  }

  public void setStatusSlobodno(boolean statusSlobodno) {
    this.statusSlobodno = statusSlobodno;
  }

  public Double getTrenutnaTezinaVozila() {
    return trenutnaTezinaVozila;
  }

  public Double getZauzetiProstorVozila() {
    return zauzetiProstorVozila;
  }

  public void setTrenutnaTezinaVozila(Double trenutnaTezinaVozila) {
    this.trenutnaTezinaVozila = trenutnaTezinaVozila;
  }

  public void setZauzetiProstorVozila(Double zauzetiProstorVozila) {
    this.zauzetiProstorVozila = zauzetiProstorVozila;
  }

  public Date getVrijemeSljedeceIsporuke() {
    return vrijemeSljedeceIsporuke;
  }

  public void setVrijemeSljedeceIsporuke(Date vrijemeSljedeceIsporuke) {
    this.vrijemeSljedeceIsporuke = vrijemeSljedeceIsporuke;
  }

  public int getProsjecnaBrzina() {
    return prosjecnaBrzina;
  }

  public void setProsjecnaBrzina(int prosjecnaBrzina) {
    this.prosjecnaBrzina = prosjecnaBrzina;
  }

  public String getPodrucjaPoRangu() {
    return podrucjaPoRangu;
  }

  public void setPodrucjaPoRangu(String podrucjaPoRangu) {
    this.podrucjaPoRangu = podrucjaPoRangu;
  }

  public VoziloState getStatus() {
    return status;
  }

  public void setStatus(VoziloState status) {
    this.status = status;
  }

  public int getBrHitnihPaketa() {
    return brHitnihPaketa;
  }

  public void setBrHitnihPaketa(int brHitnihPaketa) {
    this.brHitnihPaketa = brHitnihPaketa;
  }

  public int getBrObicnihPaketa() {
    return brObicnihPaketa;
  }

  public void setBrObicnihPaketa(int brObicnihPaketa) {
    this.brObicnihPaketa = brObicnihPaketa;
  }

  public int getBrIsporucenihPaketa() {
    return brIsporucenihPaketa;
  }

  public void setBrIsporucenihPaketa(int brIsporucenihPaketa) {
    this.brIsporucenihPaketa = brIsporucenihPaketa;
  }

  @Override
  public String toString() {
    return registracija + "; " + opis + "; " + tezina + " kg; " + kapacitet + " m3; " + redoslijed
        + "; " + prosjecnaBrzina + "; " + podrucjaPoRangu + "; " + status.toString();
  }

  public String ukrcajPakete(List<Paket> listaPaketa) {
    return status.ukrcajPaket(this, listaPaketa);
  }

  public void postaviStatusAktivno() {
    status.postaviStatusAktivno(this);
  }

  public void postaviStatusNeispravno() {
    status.postaviStatusNeispravno(this);
  }

  public void postaviStatusNeaktivno() {
    status.postaviStatusNeaktivno(this);
  }

  @Override
  public void accept(VoziloVisitor visitor) {
    visitor.visit(this);
  }

  public String getStatusVozila() {
    return statusVozila;
  }

  public void setStatusVozila(String statusVozila) {
    this.statusVozila = statusVozila;
  }

}
