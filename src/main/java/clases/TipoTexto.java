package clases;

public class TipoTexto {
	
	int codi_Texto=0;
	int tipo=0;
	String MensajeAlUsuario="";
	@Override
	public String toString() {
		return "TipoTexto [codi_Texto=" + codi_Texto + ", tipo=" + tipo + ", MensajeAlUsuario=" + MensajeAlUsuario
				+ "]";
	}
	public int getCodi_Texto() {
		return codi_Texto;
	}
	public void setCodi_Texto(int codi_Texto) {
		this.codi_Texto = codi_Texto;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public String getMensajeAlUsuario() {
		return MensajeAlUsuario;
	}
	public void setMensajeAlUsuario(String mensajeAlUsuario) {
		MensajeAlUsuario = mensajeAlUsuario;
	}
	public TipoTexto(int codi_Texto, int tipo, String mensajeAlUsuario) {
		super();
		this.codi_Texto = codi_Texto;
		this.tipo = tipo;
		MensajeAlUsuario = mensajeAlUsuario;
	}

	public TipoTexto(){}
	
	
}
