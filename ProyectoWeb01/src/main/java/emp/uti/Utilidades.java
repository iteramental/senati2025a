package emp.uti;

public final class Utilidades {
	public static boolean esNumerico(String cadena) {
		return cadena.matches("-?\\\\d+(\\\\.\\\\d+)?");
	}
}
