package models;

public class Zaposleni {

    private Integer idZaposlenog;
    private String ime;
    private String prezime;
    private String korisnickoIme;
    private String lozinka;
    private String tip;
    private String status;
    private Double plata;

    public Zaposleni() {
        super();
    }

    public Zaposleni(Integer idZaposlenog, String ime, String prezime, String korisnickoIme, String tip, String status, Double plata) {
        this.idZaposlenog = idZaposlenog;
        this.ime = ime;
        this.prezime = prezime;
        this.korisnickoIme = korisnickoIme;
        this.tip=tip;
        this.status = status;
        this.plata = plata;
    }

    public Integer getIdZaposlenog() {
        return idZaposlenog;
    }

    public void setIdZaposlenog(Integer idZaposlenog) {
        this.idZaposlenog = idZaposlenog;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getPlata() {
        return plata;
    }

    public void setPlata(Double plata) {
        this.plata = plata;
    }

    @Override
    public String toString() {
        return "Zaposleni{" +
                "idZaposlenog=" + idZaposlenog +
                ", ime='" + ime + '\'' +
                ", prezime='" + prezime + '\'' +
                ", korisnickoIme='" + korisnickoIme + '\'' +
                ", tip='" + tip + '\'' +
                ", status='" + status + '\'' +
                ", plata=" + plata +
                '}';
    }
}
