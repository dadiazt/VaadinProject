package clases;

//import java.util.Date;

public class Lista {
	
	private String nombre="";
	//private boolean isActivo=false;
	//private int version=0;
	//private Date fechaCreacion;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
//	public boolean isActivo() {
//		return isActivo;
//	}
//	public void setActivo(boolean isActivo) {
//		this.isActivo = isActivo;
//	}
//	public int getVersion() {
//		return version;
//	}
//	public void setVersion(int version) {
//		this.version = version;
//	}
//	public Date getFechaCreacion() {
//		return fechaCreacion;
//	}
//	public void setFechaCreacion(Date fechaCreacion) {
//		this.fechaCreacion = fechaCreacion;
//	}
	@Override
	public String toString() {
		return "Listas [nombre=" + nombre + "]";
	}
	public Lista(String nombre) {
		super();
		this.nombre = nombre;
		//this.isActivo = isActivo;
		//this.version = version;
		//this.fechaCreacion = fechaCreacion;
	}
	public Lista() {
		super();
	}

}
