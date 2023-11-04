package org.example.model.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MariaDBConnection {
    private static Connection conn = null;
    //read config from xml file
    //alpha version
    private final static String uri="jdbc:mariadb://localhost:3306/olimpiadas";
    private final static String user = "root";
    private final static String password = "";

    private MariaDBConnection(){}

    public static Connection getConnection(){
        if(conn==null){
            try {
                conn = DriverManager.getConnection(uri, user, password);
            } catch (SQLException e) {
                conn = null;
                throw new RuntimeException(e);
            }
        }
        return conn;
    }

    public static void close(){
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                conn = null;
            }
        }
    }
}
