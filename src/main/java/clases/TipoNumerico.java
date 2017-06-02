package clases;

public class TipoNumerico {
	
	int codi_Numero=0;
	int tipo=0;
	String textoAlUsuari="";
	public int getCodi_Numero() {
		return codi_Numero;
	}
	public void setCodi_Numero(int codi_Numero) {
		this.codi_Numero = codi_Numero;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public String getTextoAlUsuari() {
		return textoAlUsuari;
	}
	public void setTextoAlUsuari(String textoAlUsuari) {
		this.textoAlUsuari = textoAlUsuari;
	}
	@Override
	public String toString() {
		return "TipoNumerico [codi_Numero=" + codi_Numero + ", tipo=" + tipo + ", textoAlUsuari=" + textoAlUsuari + "]";
	}
	public TipoNumerico(int codi_Numero, int tipo, String textoAlUsuari) {
		super();
		this.codi_Numero = codi_Numero;
		this.tipo = tipo;
		this.textoAlUsuari = textoAlUsuari;
	}
	
	public TipoNumerico(){}

}
