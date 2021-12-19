package models;

public class Klijent {

    protected Integer idKlijenta;
    protected String adresa;
    protected String brojTelefona;

    public Klijent(Integer idKlijenta, String adresa, String brojTelefona) {
        this.idKlijenta = idKlijenta;
        this.adresa = adresa;
        this.brojTelefona=brojTelefona;
    }

    public Klijent(String adresa, String brojTelefona) {
        this.adresa = adresa;
        this.brojTelefona=brojTelefona;
    }

    public Integer getIdKlijenta() {
        return idKlijenta;
    }

    public void setIdKlijenta(Integer idKlijenta) {
        this.idKlijenta = idKlijenta;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getBrojTelefona() {
        return brojTelefona;
    }

    public void setBrojTelefona(String brojTelefona) {
        this.brojTelefona = brojTelefona;
    }

    @Override
    public String toString() {
        return "Klijent{" +
                "idKlijenta=" + idKlijenta +
                ", adresa='" + adresa + '\'' +
                ", brojTelefona='" + brojTelefona + '\'' +
                '}';
    }
}
