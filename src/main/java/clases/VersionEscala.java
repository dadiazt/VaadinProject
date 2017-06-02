package clases;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class VersionEscala implements Serializable{
	
	// idEscalador de la escala
	private int idEscala;
	// fecha de modificacion/creacion de la escala
	private Date fechaModificacion;
	// administrador que crea la escala (o versionEscala de la escala)
	private String CreadoDeLaversionEscala;
	// versionEscala de la escala. 
	// cada vez que se cambia aumenta en uno
	private int versionEscala;	
	// indica si la versionEscala se puede usar o no.
	private Boolean activo;
	// lista de Lineas
	private ArrayList<LineaEscala> misLineas= new ArrayList<LineaEscala>();
	
	// recorre el array list y busca el id mas alto.
	// por defecto retorna 0 = vacio
	public int getIdLineaMasAlta(){
		
		int max=0,tmp=0;
		
		for(LineaEscala le: misLineas){
			if((tmp=le.getid_Linea())>max) max=tmp;
		}
		
		return max;
		
	}
	// fin
	// INICIO ____ gestion del arraylist
	public LineaEscala getElemento(int pos){
		return misLineas.get(pos);
	}
	public void setElemento(LineaEscala escala){
		misLineas.add(escala);
	}
	public void delElemento(int pos){
		misLineas.remove(pos);
	}
	public void imprimirTodo(){
		System.out.println(toString());
		for(LineaEscala L: misLineas){
			System.out.println("\t"+L.toString());
		}
	}
	// FIN _______ gestion del arraylist
	
	// FINAL ____ Constructores
	public VersionEscala() {
		super();
	}
	// constructor sin arraylist
	public VersionEscala(int idEscala, String creadoDeLaversionEscala, int versionEscala, Boolean activo, Date fechaModificacion) {
		super();
		//this.fechaModificacion = fechaModificacion;
		CreadoDeLaversionEscala = creadoDeLaversionEscala;
		this.versionEscala = versionEscala;
		this.activo = activo;
		this.idEscala=idEscala;
		this.fechaModificacion=fechaModificacion;
	}
	// constructor completo
	public VersionEscala(int idEscala, String creadoDeLaversionEscala, int versionEscala, Boolean activo,
			Date fechaModificacion, ArrayList<LineaEscala> misLineas) {
		super();
		CreadoDeLaversionEscala = creadoDeLaversionEscala;
		this.versionEscala = versionEscala;
		this.activo = activo;
		this.misLineas = misLineas;
		this.idEscala=idEscala;
		this.fechaModificacion=fechaModificacion;
	}
	// FINAL _____ constructores
	
	
	
	// INICO ________ toString
	public String toStringSinArray() {
		return "versionEscala Escala [idEscala=" + idEscala + ", fechaModificacion=" + fechaModificacion + ", CreadoDeLaversionEscala="
				+ CreadoDeLaversionEscala + ", versionEscala=" + versionEscala + ", activo=" + activo + "]";
	}
	@Override
	public String toString() {
		return "versionEscala Escala [idEscala=" + idEscala + ", fechaModificacion=" + fechaModificacion + ", CreadoDeLaversionEscala="
				+ CreadoDeLaversionEscala + ", versionEscala=" + versionEscala + ", activo=" + activo + ", misLineas=" + misLineas + "]";
	}
	// FINAL _________ toString
	
	// INICIO ____ Getter y seters
	public int getidEscala() {
		return idEscala;
	}
	public void setidEscala(int idEscala) {
		this.idEscala = idEscala;
	}
	public String getCreadoDeLaversionEscala() {
		return CreadoDeLaversionEscala;
	}
	public void setCreadoDeLaversionEscala(String creadoDeLaversionEscala) {
		CreadoDeLaversionEscala = creadoDeLaversionEscala;
	}
	public int getversionEscala() {
		return versionEscala;
	}
	public void setversionEscala(int versionEscala) {
		this.versionEscala = versionEscala;
	}
	public Boolean getActivo() {
		return activo;
	}
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	public ArrayList<LineaEscala> getMisLineas() {
		return misLineas;
	}
	public void setMisLineas(ArrayList<LineaEscala> misLineas) {
		this.misLineas = misLineas;
	}
	public Date getFechaModificacion() {
		return fechaModificacion;
	}
	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	// FIN _______ getter y seters
	
	// validacion de las lineas de la escala entre si.
	public boolean validarLineas(){
		
		boolean test=false;
		
		// recorre el array de lineas
		// no puede haber dos lineas con la misma posicion en el formulario. (posicion no se puede repetir)
		// por cada version de lineas solo puede haber una linea activa. las demas deben estar desactivadas.
		
		
		return test;
	}
	
}
