package br.com.alura.bytebank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private String url = "jdbc:mysql://localhost:3306/byte_bank";
    private String user = System.getenv("MYSQL_USER");
    private String password = System.getenv("MYSQL_PASSWORD");

    public Connection recuperarConexao(){
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Conexão estabelecida com sucesso");
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}