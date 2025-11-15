package com.mycompany.IntegradorMVC.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLQuery {

    protected Connection conn;
    protected PreparedStatement consulta;
    protected ResultSet datos;

    protected final String SERVIDOR = "127.0.0.1";
    protected final String BD = "integrador-mvc";
    protected final String USUARIO = "root";
    protected final String PASSWORD = "root";

    public void conectar() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.conn = DriverManager.getConnection("jdbc:mysql://" + this.SERVIDOR + "/" + this.BD, this.USUARIO,
                 this.PASSWORD);
    }

    public void desconectar() {
        try {
            if (consulta != null) {
                consulta.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void desconectar(ResultSet rs) {
        try {
            try (rs) {
                this.desconectar();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

    }
}
