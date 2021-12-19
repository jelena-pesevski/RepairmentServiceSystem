package models;

public class Operater extends Zaposleni{

    public Operater() {
        super();
    }

    public Operater(Integer idZaposlenog, String ime, String prezime, String korisnickoIme, String tip, String status, Double plata) {
        super(idZaposlenog, ime, prezime, korisnickoIme, tip, status, plata);
    }
}
