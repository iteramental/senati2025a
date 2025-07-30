package far.uti;

import org.mindrot.jbcrypt.BCrypt;

public class VerificarHash {
    public static void main(String[] args) {
        String password = "admin";
        String hash = "$2a$12$XlyAfy/WSl0U4zP8g/50FOfw62h1k6WgBnILlAl28upYkZx99DFf.";

        boolean resultado = BCrypt.checkpw(password, hash);
        System.out.println("¿Coincide?: " + resultado);
    }
}

