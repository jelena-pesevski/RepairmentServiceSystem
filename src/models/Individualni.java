package models;

public class Individualni extends Klijent{

    private String ime;
    private String prezime;

    public Individualni(Integer idKlijenta, String adresa, String ime, String prezime, String brojTelefona) {
        super(idKlijenta, adresa, brojTelefona);
        this.ime = ime;
        this.prezime = prezime;
    }

    public Individualni(String adresa, String ime, String prezime, String brojTelefona) {
        super(adresa, brojTelefona);
        this.ime = ime;
        this.prezime = prezime;
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
}


