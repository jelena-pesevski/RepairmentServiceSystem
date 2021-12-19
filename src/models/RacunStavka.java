package models;

public class RacunStavka {

    private String naziv;
    private Double cijena;
    private Integer kolicina;
    private Double cijenaUkupno;
    private Integer idRacuna;

    public RacunStavka(String naziv, Double cijena, Integer kolicina, Double cijenaUkupno, Integer idRacuna) {
        this.naziv = naziv;
        this.cijena = cijena;
        this.kolicina = kolicina;
        this.cijenaUkupno = cijenaUkupno;
        this.idRacuna = idRacuna;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Double getCijena() {
        return cijena;
    }

    public void setCijena(Double cijena) {
        this.cijena = cijena;
    }

    public Integer getKolicina() {
        return kolicina;
    }

    public void setKolicina(Integer kolicina) {
        this.kolicina = kolicina;
    }

    public Double getCijenaUkupno() {
        return cijenaUkupno;
    }

    public void setCijenaUkupno(Double cijenaUkupno) {
        this.cijenaUkupno = cijenaUkupno;
    }

    public Integer getIdRacuna() {
        return idRacuna;
    }

    public void setIdRacuna(Integer idRacuna) {
        this.idRacuna = idRacuna;
    }
}
