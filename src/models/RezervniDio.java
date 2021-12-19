package models;

public class RezervniDio {

    private Integer sifra;
    private String naziv;
    private Double cijena;
    private Integer kolicina;

    public RezervniDio(Integer sifra, String naziv, Double cijena, Integer kolicina) {
        this.sifra = sifra;
        this.naziv = naziv;
        this.cijena = cijena;
        this.kolicina = kolicina;
    }

    public Integer getSifra() {
        return sifra;
    }

    public void setSifra(Integer sifra) {
        this.sifra = sifra;
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
}
