package br.com.alura.bytebank;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

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
//            return createDataSource().getConnection();
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//    private HikariDataSource createDataSource(){
//        HikariConfig config = new HikariConfig();
//        config.setJdbcUrl(url);
//        config.setUsername(user);
//        config.setPassword(password);
//        config.setMaximumPoolSize(150);
//        return new HikariDataSource(config);
//    }
}