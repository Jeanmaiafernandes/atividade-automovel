package connection;

/**
 *
 * @author jeanm
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final String URL = "jdbc:mysql://localhost:3307/MySQL";
    private static final String USER = "root";
    private static final String PASSWORD = "123123";
   
    public static Connection getConnection() throws SQLException {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/automoveis","root","");            
        } catch (ClassNotFoundException ex) {
            throw new SQLException("Driver JDBC não encontrado", ex);
        }
        return conn;
    }
}