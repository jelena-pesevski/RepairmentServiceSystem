package data.mysql;

import models.Majstor;
import models.Operater;
import utils.ConnectionPool;
import utils.DBUtil;

import java.sql.*;

public class MajstorDataAccess {


    public static int selectBrojZaduzenja(Integer staffId){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Integer brojZaduzenja=-1;

        String query = "SELECT BrojZaduzenja "
                + "FROM majstor "
                + "WHERE IdZaposlenog=?";

        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, staffId);
            rs = ps.executeQuery();

            if (rs.next()) {
                brojZaduzenja=rs.getInt("BrojZaduzenja");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtil.close(rs,ps);
        }

        return brojZaduzenja;

    }

}
