package me.eleuna.progetto;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sun.jdi.connect.spi.Connection;

public class DbHandler {

    private String host, username, psw;
    private java.sql.Connection conn = null;
    /*
     * Scegliamo di utilizzare una classe handler per estendere le funzionalita' in futuro
     * */
    public  DbHandler(String host, String username, String psw) throws SQLException {
        this.host = host;
        this.username = username;
        this.psw = psw;

        connectToDb();

    }

    private void connectToDb() throws SQLException {
        //esempio host -> "127.0.0.1:3306/gestione_centri"
        conn =  DriverManager.getConnection("jdbc:mysql://"+host, username, psw);

        if (conn != null) {
            System.out.println("Connected to the database!");
        } else {
            System.out.println("Failed to make connection!");
        }

    }

    /**
     * Deprecato per cattiva implementazione
     * */
    public ResultSet executeQuery(String query) throws Exception {
        PreparedStatement preparedStatement = conn.prepareStatement(query);

        return (preparedStatement.executeQuery());
    }

    /**
     * Deprecato per cattiva implementazione
     * */
    public void execUpdate(String query)throws Exception {
        PreparedStatement preparedStatement = conn.prepareStatement(query);

        preparedStatement.executeUpdate();
    }

    public java.sql.Connection getConnection() {
        return conn;
    }

}
