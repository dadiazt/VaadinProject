package clases;

public class TipoLogico {
	
	int id_valor=0;
	boolean valor=true;
	String mensajeAlUsuario="";
	public int getId_valor() {
		return id_valor;
	}
	public void setId_valor(int id_valor) {
		this.id_valor = id_valor;
	}
	public boolean isValor() {
		return valor;
	}
	public void setValor(boolean valor) {
		this.valor = valor;
	}
	public String getMensajeAlUsuario() {
		return mensajeAlUsuario;
	}
	public void setMensajeAlUsuario(String mensajeAlUsuario) {
		this.mensajeAlUsuario = mensajeAlUsuario;
	}
	@Override
	public String toString() {
		return "TipoLogico [id_valor=" + id_valor + ", valor=" + valor + ", mensajeAlUsuario=" + mensajeAlUsuario + "]";
	}
	public TipoLogico(int id_valor, boolean valor, String mensajeAlUsuario) {
		super();
		this.id_valor = id_valor;
		this.valor = valor;
		this.mensajeAlUsuario = mensajeAlUsuario;
	}
	
	public TipoLogico(){}

}
