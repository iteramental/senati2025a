package far.uti;

import org.mindrot.jbcrypt.BCrypt;

public class HashGenerador {

    /**
     * Genera un hash seguro de la contraseña usando BCrypt.
     * 
     * @param password La contraseña en texto plano.
     * @return El hash de la contraseña.
     */
    public static String generarHash(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede ser nula o vacía.");
        }
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    /**
     * Verifica si una contraseña coincide con su hash almacenado.
     * 
     * @param password Contraseña en texto plano.
     * @param hash     Hash previamente generado.
     * @return true si la contraseña es válida, false en caso contrario.
     */
    public static boolean verificarPassword(String password, String hash) {
        if (password == null || hash == null) return false;
        return BCrypt.checkpw(password, hash);
    }

    // Método de prueba opcional
    public static void main(String[] args) {
        String password = "admin";
        String hashed = "$2a$12$SHo9fEyWV7cY8wYW2U692.MNYzBdHchVWuPzW8oPGJywXanFpN9q."; // Copia exacta del que tienes
        boolean coincide = HashGenerador.verificarPassword(password, hashed);
        System.out.println("¿Coincide?: " + coincide);
    }

}