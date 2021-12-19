package data.mysql;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Usluga;
import utils.ConnectionPool;
import utils.DBUtil;

import java.math.BigDecimal;
import java.sql.*;

public class UslugeDataAccess {

    public static ObservableList<Usluga> getAll(String name) {
        ObservableList<Usluga> list= FXCollections.observableArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT IdUsluge, Naziv, Cijena "
                + "FROM  usluga "
                + "WHERE Naziv LIKE ?;";
        try{
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setString(1,
                    DBUtil.preparePattern(name));
            rs = ps.executeQuery();

            Usluga u;
            while (rs.next()) {
                u=new Usluga(rs.getInt("IdUsluge"), rs.getString("Naziv"), rs.getDouble("Cijena"));
                list.add(u);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtil.close(rs,ps);
        }
        return list;
    }

    public static void insertUsluga(String name, Double price){
        Connection conn = null;
        PreparedStatement ps = null;

        String query = "INSERT INTO usluga (Naziv, Cijena) VALUES "
                + "(?, ?) ";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setString(1, name);
            ps.setBigDecimal(2, BigDecimal.valueOf(price));

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtil.close(ps);
        }
    }

    public static void updateUsluga(Integer id, String name, Double price){
        Connection conn = null;
        PreparedStatement ps = null;

        String query = "UPDATE usluga SET "
                + "Naziv=?, Cijena=?"
                + "WHERE IdUsluge=? ";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setString(1, name);
            ps.setBigDecimal(2, BigDecimal.valueOf(price));
            ps.setInt(3, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtil.close(ps);
        }
    }

    public static boolean deleteUsluga(Integer idUsluge) {
        boolean retVal = false;
        Connection conn = null;
        PreparedStatement ps = null;

        String query = "DELETE FROM usluga "
                + "WHERE IdUsluge=? ";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, idUsluge);

            retVal = ps.executeUpdate() == 1;
        } catch (SQLException e) {
           // e.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtil.close(ps);
        }
        return retVal;
    }


}
