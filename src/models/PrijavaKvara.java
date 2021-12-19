package models;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PrijavaKvara {

    private Integer idPrijave;
    private String datumPrijave; //String jer se koristi timestamp iz baze
    private String opis;
    private Integer idZaposlenogOperater;
    private Integer idKlijenta;
    private Integer idZaposlenogMajstor;
    private String status;

    public PrijavaKvara() {
    }

    public PrijavaKvara(Integer idPrijave, String datumPrijave, String opis, Integer idZaposlenogOperater, Integer idKlijenta, Integer idZaposlenogMajstor, String status) {
        this.idPrijave = idPrijave;
        this.datumPrijave = datumPrijave;
        this.opis = opis;
        this.idZaposlenogOperater = idZaposlenogOperater;
        this.idKlijenta = idKlijenta;
        this.idZaposlenogMajstor = idZaposlenogMajstor;
        this.status = status;
    }

    public PrijavaKvara(String datumPrijave, String opis, Integer idZaposlenogOperater, Integer idKlijenta, Integer idZaposlenogMajstor, String status) {
        this.datumPrijave = datumPrijave;
        this.opis = opis;
        this.idZaposlenogOperater = idZaposlenogOperater;
        this.idKlijenta = idKlijenta;
        this.idZaposlenogMajstor = idZaposlenogMajstor;
        this.status = status;
    }

    public Integer getIdPrijave() {
        return idPrijave;
    }

    public void setIdPrijave(Integer idPrijave) {
        this.idPrijave = idPrijave;
    }

    public String getDatumPrijave() {
        return datumPrijave;
    }

    public void setDatumPrijave(String datumPrijave) {
        this.datumPrijave = datumPrijave;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Integer getIdZaposlenogOperater() {
        return idZaposlenogOperater;
    }

    public void setIdZaposlenogOperater(Integer idZaposlenogOperater) {
        this.idZaposlenogOperater = idZaposlenogOperater;
    }

    public Integer getIdKlijenta() {
        return idKlijenta;
    }

    public void setIdKlijenta(Integer idKlijenta) {
        this.idKlijenta = idKlijenta;
    }

    public Integer getIdZaposlenogMajstor() {
        return idZaposlenogMajstor;
    }

    public void setIdZaposlenogMajstor(Integer idZaposlenogMajstor) {
        this.idZaposlenogMajstor = idZaposlenogMajstor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PrijavaKvara{" +
                "idPrijave=" + idPrijave +
                ", datumPrijave='" + datumPrijave + '\'' +
                ", opis='" + opis + '\'' +
                ", idZaposlenogOperater=" + idZaposlenogOperater +
                ", idKlijenta=" + idKlijenta +
                ", idZaposlenogMajstor=" + idZaposlenogMajstor +
                ", status='" + status + '\'' +
                '}';
    }
}
