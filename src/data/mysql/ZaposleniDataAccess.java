package data.mysql;

import controllers.LoginPage;
import javafx.scene.control.Alert;
import models.Majstor;
import models.Operater;
import models.Zaposleni;
import utils.ConnectionPool;
import utils.DBUtil;

import java.sql.*;

public class ZaposleniDataAccess {

    public static Zaposleni selectZaposleniUsernamePass(String uname, String pass){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT IdZaposlenog, Ime, Prezime, KorisničkoIme, Tip, Plata, Status "
                + "FROM zaposleni "
                + "WHERE KorisničkoIme=? and Lozinka=?";

        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setString(1, uname);
            ps.setString(2, pass);
            rs = ps.executeQuery();

            if (rs.next()) {
                //postoji dati korisnik
                Integer idZaposlenog = rs.getInt("IdZaposlenog");
                String ime = rs.getString("Ime");
                String prezime = rs.getString("Prezime");
                String korisnickoIme = uname;
                String tip = rs.getString("Tip");
                String status = rs.getString("Status");
                Double plata = rs.getBigDecimal("Plata").doubleValue();

                switch(tip){
                    case "O":
                        return new Operater(idZaposlenog, ime, prezime, korisnickoIme, tip, status, plata);
                    case "M":
                        Majstor m=new Majstor(idZaposlenog, ime, prezime, korisnickoIme, tip, status, plata);
                        m.setBrojZaduzenja(MajstorDataAccess.selectBrojZaduzenja(idZaposlenog));
                        return m;
                    default:
                        break;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtil.close(rs,ps);
        }

        return null;
    }

}
