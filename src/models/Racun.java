package models;

public class Racun {

    private Integer idRacuna;
    private Integer idPopravke;
    private Double cijena;


    public Racun(Integer idRacuna, Integer idPopravke, Double cijena) {
        this.idRacuna = idRacuna;
        this.idPopravke = idPopravke;
        this.cijena = cijena;
    }

    public Racun(Integer idPopravke, Double cijena) {
        this.idPopravke = idPopravke;
        this.cijena = cijena;
    }

    public Integer getIdRacuna() {
        return idRacuna;
    }

    public void setIdRacuna(Integer idRacuna) {
        this.idRacuna = idRacuna;
    }

    public Integer getIdPopravke() {
        return idPopravke;
    }

    public void setIdPopravke(Integer idPopravke) {
        this.idPopravke = idPopravke;
    }

    public Double getCijena() {
        return cijena;
    }

    public void setCijena(Double cijena) {
        this.cijena = cijena;
    }
}
