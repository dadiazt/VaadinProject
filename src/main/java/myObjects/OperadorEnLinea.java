package myObjects;

public class OperadorEnLinea {

	private String nombre;
	private String dinamica;
	private String nombrePaciente;
	


	public OperadorEnLinea(String n,String d){
		this.nombre=n;
		this.dinamica=d;
	}

	public String getNombre() {		return nombre;	}

	public void setNombre(String nombre) {		this.nombre = nombre;	}

	public String getDinamica() {		return dinamica;	}

	public void setDinamica(String dinamica) {		this.dinamica = dinamica;	}
	
	public String getNombrePaciente() {		return nombrePaciente;	}

	public void setNombrePaciente(String nombrePaciente) {		this.nombrePaciente = nombrePaciente;	}
	
}
