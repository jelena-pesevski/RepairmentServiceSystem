package models;

public class Preduzece extends Klijent{

    private String naziv;

    public Preduzece(Integer idKlijenta, String adresa, String naziv, String brojTelefona) {
        super(idKlijenta, adresa, brojTelefona);
        this.naziv = naziv;
    }

    public Preduzece(String adresa, String naziv, String brojTelefona) {
        super(adresa, brojTelefona);
        this.naziv = naziv;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
}
