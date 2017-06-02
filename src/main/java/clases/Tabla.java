package clases;
public class Tabla {

	private String nombre="";	
	
	public Tabla() { }
	
	public Tabla(String nombre) {
		this.nombre = nombre;
	}
	@Override
	public String toString() {
		return "Tablas [nombre=" + nombre  + "]";
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}