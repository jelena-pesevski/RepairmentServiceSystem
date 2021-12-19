package models;

import java.util.Date;

public class Popravka {

    private Integer idPopravke;
    private Integer idZaposlenog;
    private Integer idPrijave;
    private String pocetak;
    private String zavrsetak;
    private String opis; //opis iz prijave


    public Popravka(Integer idPopravke, Integer idZaposlenog, Integer idPrijave, String pocetak, String opis) {
        this.idPopravke = idPopravke;
        this.idZaposlenog = idZaposlenog;
        this.idPrijave = idPrijave;
        this.pocetak = pocetak;
        this.zavrsetak = null;
        this.opis = opis;
    }

    public Integer getIdPopravke() {
        return idPopravke;
    }

    public void setIdPopravke(Integer idPopravke) {
        this.idPopravke = idPopravke;
    }

    public Integer getIdZaposlenog() {
        return idZaposlenog;
    }

    public void setIdZaposlenog(Integer idZaposlenog) {
        this.idZaposlenog = idZaposlenog;
    }

    public Integer getIdPrijave() {
        return idPrijave;
    }

    public void setIdPrijave(Integer idPrijave) {
        this.idPrijave = idPrijave;
    }

    public String getPocetak() {
        return pocetak;
    }

    public void setPocetak(String pocetak) {
        this.pocetak = pocetak;
    }

    public String getZavrsetak() {
        return zavrsetak;
    }

    public void setZavrsetak(String zavrsetak) {
        this.zavrsetak = zavrsetak;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
}
