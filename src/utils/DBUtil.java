package utils;

import java.sql.*;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class DBUtil {

  /*  private static String jdbcURL;
    private static String username;
    private static String password;

    //konfiguracija se ucitava iz properties fajla
    static{
        ResourceBundle bundle= PropertyResourceBundle.getBundle("utils.db");
        jdbcURL=bundle.getString("jdbcURL");
        username=bundle.getString("username");
        password=bundle.getString("password");
    }

    public static Connection getConnection() throws SQLException {
        Connection c= DriverManager.getConnection(jdbcURL,username, password);
        return c;
    }*/

    public static void close(Connection c){
        if(c!=null){
            try{
                c.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }


    public static void close(Statement s){
        if(s!=null){
            try{
                s.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static void close(ResultSet rs){
        if(rs!=null){
            try{
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static void close(ResultSet rs, Statement s){
        close(rs);
        close(s);
    }

    public static void close(ResultSet rs, Statement s, Connection c){
        close(rs);
        close(s);
        close(c);
    }

    public static void close(Statement s, Connection c){
        close(s);
        close(c);
    }

    public static String preparePattern(String text) {
        return text.replace('*', '%').replace('?', '_');
    }

    public static boolean isSearchPatternValid(String pattern) {
        boolean retVal = true;
        for (int i = 0; i < pattern.length(); i++) {
            char c = pattern.charAt(i);
            if (!(Character.isLetterOrDigit(c) || c == '-' || c == '_'
                    || c == ' ' || c == '.' || c == ',' || c == '?' || c == '*')) {
                retVal = false;
                break;
            }
        }
        return retVal;
    }
}
