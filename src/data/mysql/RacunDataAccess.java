package data.mysql;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.PrijavaKvara;
import models.Racun;
import utils.ConnectionPool;
import utils.DBUtil;

import java.math.BigDecimal;
import java.sql.*;

public class RacunDataAccess {

    public static Racun insertRacun(Integer repairmentId) {
        int retVal = 0;
        Integer newPrijavaID=-1;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Racun racun=new Racun(repairmentId,0.0);

        String query = "INSERT INTO račun (IdPopravke, Cijena) VALUES "
                + "(?, ?) ";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query,  Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, repairmentId);
            ps.setBigDecimal(2, BigDecimal.valueOf(0));

            retVal = ps.executeUpdate();
            if (retVal != 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next())
                    racun.setIdRacuna(rs.getInt(1));
                return racun;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtil.close(ps);
        }
        return null;
    }

    public static Double getTotal(Integer id){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Double total=0.0;

        String query = "SELECT Cijena "
                + "FROM  račun "
                + "WHERE IdRacuna=?";
        try{
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                total=rs.getDouble("Cijena");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtil.close(rs,ps);
        }
        return total;

    }

}
