package data.mysql;

import models.Popravka;
import models.RezervniDio;
import models.Usluga;
import utils.ConnectionPool;
import utils.DBUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PopravkaRezervniDioDataAccess {

    public static void insert(Popravka p, RezervniDio r, Integer amount){
        Connection conn = null;
        PreparedStatement ps = null;

        String query = "INSERT INTO popravka_rezervnidio (IdPopravke, Sifra, Kolicina, Cijena) VALUES "
                + "(?, ?, ?, ?) ";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, p.getIdPopravke());
            ps.setInt(2,r.getSifra());
            ps.setInt(3, amount);
            ps.setBigDecimal(4, BigDecimal.valueOf(r.getCijena()));

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtil.close(ps);
        }


    }
}
