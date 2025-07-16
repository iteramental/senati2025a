import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    public static void main(String[] args) {
        String url = "jdbc:oracle:thin:@localhost:1521:ORCL";
;
        String user = "FARMACIA";
        String password = "SafePass2025";
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("¡Conexión exitosa!");
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
