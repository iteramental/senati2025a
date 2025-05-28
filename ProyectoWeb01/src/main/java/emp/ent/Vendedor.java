package emp.ent;

public class Vendedor extends Persona {
	private int codigoVendedor;
	private int tipoDocumento;
	private String numeroDocumento;
	/**
	 * @return the codigoVendedor
	 */
	public int getCodigoVendedor() {
		return codigoVendedor;
	}
	/**
	 * @param codigoVendedor the codigoVendedor to set
	 */
	public void setCodigoVendedor(int codigoVendedor) {
		this.codigoVendedor = codigoVendedor;
	}
	/**
	 * @return the tipoDocumento
	 */
	public int getTipoDocumento() {
		return tipoDocumento;
	}
	/**
	 * @param tipoDocumento the tipoDocumento to set
	 */
	public void setTipoDocumento(int tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	/**
	 * @return the numeroDocumento
	 */
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	/**
	 * @param numeroDocumento the numeroDocumento to set
	 */
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

}
