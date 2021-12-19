package models;

public class Majstor extends Zaposleni{

    Integer brojZaduzenja;

    public Majstor() {
        super();
    }

    public Majstor(Integer idZaposlenog, String ime, String prezime, String korisnickoIme, String tip, String status, Double plata) {
        super(idZaposlenog, ime, prezime, korisnickoIme, tip, status, plata);
    }

    public Integer getBrojZaduzenja() {
        return brojZaduzenja;
    }

    public void setBrojZaduzenja(Integer brojZaduzenja) {
        this.brojZaduzenja = brojZaduzenja;
    }
}
