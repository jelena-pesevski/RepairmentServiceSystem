package models;

public class Usluga {

    private Integer idUsluge;
    private String naziv;
    private Double cijena;


    public Usluga(Integer idUsluge, String naziv, Double cijena) {
        this.idUsluge = idUsluge;
        this.naziv = naziv;
        this.cijena = cijena;
    }

    public Usluga(String naziv, Double cijena) {
        this.naziv = naziv;
        this.cijena = cijena;
    }

    public Integer getIdUsluge() {
        return idUsluge;
    }

    public void setIdUsluge(Integer idUsluge) {
        this.idUsluge = idUsluge;
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
}
