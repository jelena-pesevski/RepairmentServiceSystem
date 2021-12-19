package data.mysql;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Popravka;
import models.PrijavaKvara;
import utils.ConnectionPool;
import utils.DBUtil;

import java.math.BigDecimal;
import java.sql.*;
import java.text.SimpleDateFormat;

public class PopravkaDataAccess {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;

    public static boolean insertPopravka(Integer reportId, Integer staffId) {
        Connection conn = null;
        PreparedStatement ps = null;
        int retVal=0;

        String query = "INSERT INTO popravka (IdZaposlenog, IdPrijave) VALUES "
                + "(?, ?) ";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, staffId);
            ps.setInt(2,reportId);

            retVal=ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtil.close(ps);
        }
        return retVal!=0?true:false;
    }

    public static void endPopravka(Integer repairmentId){
        Connection c=null;
        CallableStatement cs=null;
        try{
            c=ConnectionPool.getInstance().checkOut();
            cs=c.prepareCall("{call zavrsi_popravku(?)}");

            cs.setInt(1, repairmentId);
            cs.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            ConnectionPool.getInstance().checkIn(c);
            DBUtil.close(cs);
        }
    }

    public static ObservableList<Popravka> getAll(Integer repairmanId){
        ObservableList<Popravka> list= FXCollections.observableArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * "
                + "FROM  nezavrsene_popravke_sa_opisom "
                + "WHERE IdZaposlenog=?";
        try{
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, repairmanId);
            rs = ps.executeQuery();

            Popravka p;
            while (rs.next()) {
                String date = sdf.format(new Date((rs.getTimestamp("Pocetak")).getTime()));
                p=new Popravka(rs.getInt("IdPopravke"), rs.getInt("IdZaposlenog"),
                        rs.getInt("IdPrijave"),date,  rs.getString("Opis"));
                list.add(p);
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
