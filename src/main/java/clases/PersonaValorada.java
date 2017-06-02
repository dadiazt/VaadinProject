package clases;

import clases.UsuarioApp;

public class PersonaValorada {



	private int id;
	private String nombre;
	private String idDinamica;
	
	//Constructor para el index of, interfazOperador
	public PersonaValorada(String n){
		this.nombre=n;
	}
	
	public PersonaValorada(int id,String n,String d){
		this.id=id;
		this.nombre=n;
		this.idDinamica=d;		
		
	}

	public int getId() {		return id;	}

	public void setId(int id) {		this.id = id;	}

	public String getNombre() {		return nombre;	}

	public void setNombre(String nombre) {		this.nombre = nombre;	}

	public String getidDinamica() {		return idDinamica;	}

	public void setidDinamica(String dinamica) {		this.idDinamica = dinamica;	}
	
	@Override
	public String toString() {
		return "\nPersonaValorada [id=" + id + ", nombre=" + nombre + ", dinamica=" + idDinamica + "]";
	}
	

	   @Override
	    public boolean equals(Object object)
	    {
	        boolean sameSame = false;

	        if (object != null && object instanceof PersonaValorada)
	        {
	            sameSame = this.nombre.equals(((PersonaValorada) object).nombre);
	        }

	        return sameSame;
	    }
	
}
