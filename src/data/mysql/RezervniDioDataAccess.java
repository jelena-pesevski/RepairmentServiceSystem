package data.mysql;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.RezervniDio;
import models.Usluga;
import utils.ConnectionPool;
import utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RezervniDioDataAccess {

    public static ObservableList<RezervniDio> getAll(String name){
        ObservableList<RezervniDio> list= FXCollections.observableArrayList();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT *"
                + "FROM  rezervnidio "
                + "WHERE Kolicina>0 and Naziv LIKE ?;";
        try{
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setString(1,
                    DBUtil.preparePattern(name));
            rs = ps.executeQuery();

            RezervniDio r;
            while (rs.next()) {
                r=new RezervniDio(rs.getInt("Sifra"), rs.getString("Naziv"), rs.getDouble("Cijena"), rs.getInt("Kolicina"));
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
