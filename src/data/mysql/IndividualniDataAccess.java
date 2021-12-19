package data.mysql;

import controllers.MainPage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Individualni;
import utils.ConnectionPool;
import utils.DBUtil;

import java.sql.*;

public class IndividualniDataAccess {

    public static ObservableList<Individualni> getAllIndividualni(String lastname){
        ObservableList<Individualni> list= FXCollections.observableArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * "
                + "FROM  individualni_klijent_info "
                + "WHERE Prezime LIKE ?;";
        try{
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setString(1,
                    DBUtil.preparePattern(lastname));
            rs = ps.executeQuery();

            Individualni i;
            while (rs.next()) {
               i=new Individualni(rs.getInt("IdKlijenta"), rs.getString("Adresa"), rs.getString("Ime"),
                       rs.getString("Prezime"), rs.getString("BrojTelefona"));
               list.add(i);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtil.close(rs,ps);
        }
        return list;
    }


    public static void insertIndividualni(String name, String lastname, String address, String phone){
        Connection c=null;
        CallableStatement cs=null;
        try{
            c=ConnectionPool.getInstance().checkOut();
            cs=c.prepareCall("{call novi_individualni_klijent(?, ?, ?, ?)}");

            cs.setString(1, name);
            cs.setString(2, lastname);
            cs.setString(3, address);
            cs.setString(4,phone);
            cs.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            ConnectionPool.getInstance().checkIn(c);
            DBUtil.close(cs);
        }
    }

    public static void updateIndividualni(Integer id, String name, String lastname, String address, String phone) {
        Connection c=null;
        CallableStatement cs=null;
        try{
            c=ConnectionPool.getInstance().checkOut();
            cs=c.prepareCall("{call azuriraj_individualni(?, ?, ?, ?, ?)}");

            cs.setInt(1, id);
            cs.setString(2, name);
            cs.setString(3, lastname);
            cs.setString(4, address);
            cs.setString(5,phone);
            cs.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            ConnectionPool.getInstance().checkIn(c);
            DBUtil.close(cs);
        }
    }

    public static boolean deleteIndividualni(int id){
        boolean retVal = false;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query1 = "SELECT * FROM prijavakvara "
                + "WHERE IdKlijenta=? ";
        String query2 = "DELETE FROM telefonklijenta "
                + "WHERE IdKlijenta=? ";
        String query3 = "DELETE FROM individualni "
                + "WHERE IdKlijenta=? ";
        String query4 = "DELETE FROM klijent "
                + "WHERE IdKlijenta=? ";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            //prvo provjera da li se koristi kao strani kljuc
            ps = conn.prepareStatement(query1);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            //provjera da li ima prijava kvara sa datim id-jem klijenta
            if(!rs.next()){
                ps = conn.prepareStatement(query2);
                ps.setInt(1, id);
                retVal = ps.executeUpdate() == 1;

                ps = conn.prepareStatement(query3);
                ps.setInt(1, id);
                retVal = ps.executeUpdate() == 1;

                ps = conn.prepareStatement(query4);
                ps.setInt(1, id);
                retVal = ps.executeUpdate() == 1;
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            MainPage.showError("Brisanje nije moguce, klijent ima prijavljenje kvarove!");
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtil.close(ps);
        }
        return retVal;
    }

    public static Individualni getIndividualniBasedOnPrijava(Integer reportId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Individualni i=null;

        String query = " SELECT k.IdKlijenta, Ime, Prezime, Adresa, BrojTelefona from prijavakvara p INNER JOIN (klijent k natural join individualni natural join telefonklijenta)  on p.IdKlijenta=k.IdKlijenta where IdPrijave=?";
        try{
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1,reportId );
            rs = ps.executeQuery();

            if (rs.next()) {
                i=new Individualni(rs.getInt("IdKlijenta"), rs.getString("Adresa"), rs.getString("Ime"),
                        rs.getString("Prezime"), rs.getString("BrojTelefona"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtil.close(rs,ps);
        }
        return i;
    }
}
