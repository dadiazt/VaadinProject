package clases;

import java.util.ArrayList;
import java.util.List;

import interfaces.objGenerico;

public class TipoFecha implements objGenerico{
	
	private int id_fecha=0;
	private String textoAlUsuario="";
	List<objGenerico> miObj = new ArrayList<objGenerico>();
	
	public int getId_fecha() {
		return id_fecha;
	}
	public void setId_fecha(int id_fecha) {
		this.id_fecha = id_fecha;
	}
	public String getTextoAlUsuario() {
		return textoAlUsuario;
	}
	public void setTextoAlUsuario(String textoAlUsuario) {
		this.textoAlUsuario = textoAlUsuario;
	}
	@Override
	public String toString() {
		return "TipoFecha [id_fecha=" + id_fecha + ", textoAlUsuario=" + textoAlUsuario + "]";
	}
	public TipoFecha(int id_fecha, String textoAlUsuario) {
		super();
		this.id_fecha = id_fecha;
		this.textoAlUsuario = textoAlUsuario;
	}

	public TipoFecha(){}
}
