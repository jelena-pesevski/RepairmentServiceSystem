package data.mysql;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Individualni;
import models.RacunStavka;
import utils.ConnectionPool;
import utils.DBUtil;

import java.sql.*;

public class RacunStavkaDataAccess {

    public static void insertRacunStavka(Integer billId, Integer repairmentId) {
        Connection c=null;
        CallableStatement cs=null;
        try{
            c= ConnectionPool.getInstance().checkOut();
            cs=c.prepareCall("{call unesi_usluge_u_racun(?, ?)}");

            cs.setInt(1, billId);
            cs.setInt(2, repairmentId);
            cs.executeUpdate();

            cs=c.prepareCall("{call unesi_rezDijelove_u_racun(?, ?)}");

            cs.setInt(1, billId);
            cs.setInt(2, repairmentId);
            cs.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            ConnectionPool.getInstance().checkIn(c);
            DBUtil.close(cs);
        }
    }

    public static ObservableList<RacunStavka> getAll(Integer idRacuna){
        ObservableList<RacunStavka> list= FXCollections.observableArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * "
                + "FROM  stavke_usluge  WHERE IdRacuna=? UNION SELECT * FROM  stavke_dijelovi "
                + "WHERE IdRacuna=?";


        try{
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, idRacuna);
            ps.setInt(2, idRacuna);
            rs = ps.executeQuery();

            RacunStavka r;
            while (rs.next()) {
                r=new RacunStavka(rs.getString("Naziv"), rs.getBigDecimal("Cijena").doubleValue(),  rs.getInt("Kolicina"),
                        rs.getBigDecimal("Ukupno").doubleValue(), rs.getInt("IdRacuna"));
                list.add(r);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtil.close(rs,ps);
        }
        return list;
    }
}
