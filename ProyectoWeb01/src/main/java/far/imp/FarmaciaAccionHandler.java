// far.imp.FarmaciaAccionHandler.java
package far.imp;

import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.*;

public interface FarmaciaAccionHandler {
    void procesar(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory emf) throws Exception;
}
