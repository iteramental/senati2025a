import java.sql.Connection;
import java.sql.DriverManager;

public class TestConexion {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/myDB", "root", "");
            System.out.println("✅ Conexión exitosa a MySQL!");
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
