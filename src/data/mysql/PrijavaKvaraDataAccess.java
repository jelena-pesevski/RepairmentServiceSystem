package data.mysql;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.PrijavaKvara;
import utils.ConnectionPool;
import utils.DBUtil;

import java.sql.*;
import java.text.SimpleDateFormat;

public class PrijavaKvaraDataAccess {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;

    public static boolean insertPrijavaKvara(String description, Integer clientID, Integer staffID){
        int retVal = 0;
        Integer newPrijavaID=-1;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "INSERT INTO prijavakvara (Opis, Operater_IdZaposlenog, IdKlijenta) VALUES "
                + "(?, ?, ?) ";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, description);
            ps.setInt(2, staffID);
            ps.setInt(3, clientID);

            retVal = ps.executeUpdate();
            if (retVal != 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next())
                    newPrijavaID=rs.getInt(1);
            }

            //sada poziv procedure
            CallableStatement cs=null;

            cs=conn.prepareCall("{call dodijeli_zaduzenje_majstoru(?)}");
            cs.setInt(1, newPrijavaID);
            cs.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtil.close(ps);
        }
        return retVal!=0?true:false;
    }

    public static ObservableList<PrijavaKvara> getPrijavaKvaraIndividualni(Integer repairmanId){
        ObservableList<PrijavaKvara> list= FXCollections.observableArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * "
                + "FROM  nezavrsene_prijave_individualni "
                + "WHERE Majstor_IdZaposlenog=?";
        try{
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, repairmanId);
            rs = ps.executeQuery();

            PrijavaKvara p;
            while (rs.next()) {
                String date = sdf.format(new Date((rs.getTimestamp("DatumPrijave")).getTime()));
                p=new PrijavaKvara(rs.getInt("IdPrijave"), date, rs.getString("Opis"),
                        rs.getInt("Operater_IdZaposlenog"), rs.getInt("IdKlijenta"), rs.getInt("Majstor_IdZaposlenog"), rs.getString("Status"));
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

    public static ObservableList<PrijavaKvara> getPrijavaKvaraPreduzeca(Integer repairmanId){
        ObservableList<PrijavaKvara> list= FXCollections.observableArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * "
                + "FROM  nezavrsene_prijave_preduzeca "
                + "WHERE Majstor_IdZaposlenog=?";
        try{
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, repairmanId);
            rs = ps.executeQuery();

            PrijavaKvara p;
            while (rs.next()) {
                String date = sdf.format(new Date((rs.getTimestamp("DatumPrijave")).getTime()));
                p=new PrijavaKvara(rs.getInt("IdPrijave"), date, rs.getString("Opis"),
                        rs.getInt("Operater_IdZaposlenog"), rs.getInt("IdKlijenta"), rs.getInt("Majstor_IdZaposlenog"), rs.getString("Status"));
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

    public static void updateState(Integer reportId, String newStatus){
        Connection conn = null;
        PreparedStatement ps = null;

        String query = "UPDATE prijavakvara SET "
                + "Status=?"
                + "WHERE IdPrijave=? ";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setString(1, newStatus);
            ps.setInt(2, reportId);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtil.close(ps);
        }

    }

}
