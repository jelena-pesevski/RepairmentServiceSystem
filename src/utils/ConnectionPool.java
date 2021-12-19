package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class ConnectionPool {

    private static final int PRECONNCECT_COUNT=0;
    private static final int MAX_IDLE_CONNECTIONS=10;
    private static final int MAX_CONNECTIONS=10;

    private  String jdbcURL;
    private  String username;
    private  String password;

    private int preconnectCount;
    private int maxIdleConnections;
    private int maxConnections;
    private int connectCount;
    private List<Connection> usedConnections;
    private List<Connection> freeConnections;

    private static ConnectionPool instance;


    public static ConnectionPool getInstance(){
        if(instance==null){
            instance=new ConnectionPool();
        }
        return instance;
    }

    private ConnectionPool(){
        readConfiguration();

        try{
            freeConnections = new ArrayList<>();
            usedConnections = new ArrayList<>();

            for (int i = 0; i < preconnectCount; i++) {
                Connection conn = DriverManager.getConnection(jdbcURL, username, password);
                freeConnections.add(conn);
            }
            connectCount = preconnectCount;

        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void readConfiguration(){
        ResourceBundle bundle= PropertyResourceBundle.getBundle("utils.db");
        jdbcURL=bundle.getString("jdbcURL");
        username=bundle.getString("username");
        password=bundle.getString("password");

        //default values
        preconnectCount=PRECONNCECT_COUNT;
        maxIdleConnections=MAX_IDLE_CONNECTIONS;
        maxConnections=MAX_CONNECTIONS;

        try{
            preconnectCount=Integer.parseInt(bundle.getString("preconnectCount"));
            maxIdleConnections=Integer.parseInt(bundle.getString("maxIdleConnections"));
            maxConnections = Integer.parseInt(bundle.getString("maxConnections"));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Connection getConnection() throws SQLException {
        Connection c= DriverManager.getConnection(jdbcURL,username, password);
        return c;
    }

    public synchronized Connection checkOut() throws SQLException {
        Connection conn = null;
        if (freeConnections.size() > 0) {
            conn = freeConnections.remove(0);
            usedConnections.add(conn);
        } else {
            if (connectCount < maxConnections) {
                conn = DriverManager.getConnection(jdbcURL, username, password);
                usedConnections.add(conn);
                connectCount++;
            } else {
                try {
                    wait();
                    conn = freeConnections.remove(0);
                    usedConnections.add(conn);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return conn;
    }

    public synchronized void checkIn(Connection conn) {
        if (conn == null)
            return;
        if (usedConnections.remove(conn)) {
            freeConnections.add(conn);
            while (freeConnections.size() > maxIdleConnections) {
                int lastOne = freeConnections.size() - 1;
                Connection c = freeConnections.remove(lastOne);
                try {
                    c.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            notify();
        }
    }


}
