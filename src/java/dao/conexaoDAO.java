package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexaoDAO {
    
    private static final String URL = "jdbc:mysql://localhost:3306/atividade_automovel";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    public static Connection conectaDB() {
        try {
            System.out.println("Tentando carregar driver MySQL...");
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver MySQL carregado com sucesso!");
            
            System.out.println("Conectando ao banco: " + URL);
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexão estabelecida com sucesso!");
            
            return conn;
            // MEU DEUS que dificuldade eu tive pra resolver isso do drivermysql
        } catch (ClassNotFoundException e) {
            System.err.println("ERRO: Driver MySQL não encontrado!");
            System.err.println("Verifique se o mysql-connector-j-X.X.X.jar está em:");
            System.err.println("   - WEB-INF/lib/ do seu projeto");
            System.err.println("   - OU em C:\\xampp\\tomcat\\lib\\");
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            System.err.println("❌ ERRO SQL: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}