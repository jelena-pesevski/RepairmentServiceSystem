package data.mysql;

import models.Popravka;
import models.Usluga;
import utils.ConnectionPool;
import utils.DBUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PopravkaUslugaDataAccess {

    public static void insert(Popravka p, Usluga u, Integer amount){
        Connection conn = null;
        PreparedStatement ps = null;

        String query = "INSERT INTO popravka_usluga (IdPopravke, IdUsluge, Kolicina, Cijena) VALUES "
                + "(?, ?, ?, ?) ";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, p.getIdPopravke());
            ps.setInt(2,u.getIdUsluge());
            ps.setInt(3, amount);
            ps.setBigDecimal(4, BigDecimal.valueOf(u.getCijena()));

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtil.close(ps);
        }


    }

}
