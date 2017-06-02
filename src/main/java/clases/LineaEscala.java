package clases;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class LineaEscala implements Serializable {
	

	// long id de la escala
	private int id_Escala;
	// long la version a la que pertenece
	private int version_Escala;
	// id_Lineador de la linea
	private int id_Linea;
	// id_creador creador de la linea
	private String id_creador;
	// contine el nombre de la linea.
	private String nombre;
	// boolean que indica si es de entrada o salida.
	private boolean esEntrada=true;
	// indica si esta linea es activa o no
	private boolean esactivo=true;
	// fecha creacion de la linea
	private Date fecha_creacion;
	
	// contiene las distintas versiones de esta liena
	ArrayList<VersionLineaEntradaEscala> misVersiones = new ArrayList<VersionLineaEntradaEscala>();
	
	
	public int getId_Linea() {
		return id_Linea;
	}
	public void setId_Linea(int id_Linea) {
		this.id_Linea = id_Linea;
	}
	public String getId_creador() {
		return id_creador;
	}
	public void setId_creador(String id_creador) {
		this.id_creador = id_creador;
	}
	public Date getFecha_creacion() {
		return fecha_creacion;
	}
	public void setFecha_creacion(Date fecha_creacion) {
		this.fecha_creacion = fecha_creacion;
	}
	public boolean isEsactivo() {
		return esactivo;
	}
	public void setEsactivo(boolean esactivo) {
		this.esactivo = esactivo;
	}
	public boolean isEsEntrada() {
		return esEntrada;
	}
	public void setEsEntrada(boolean esEntrada) {
		this.esEntrada = esEntrada;
	}
	// gestion del array
	public VersionLineaEntradaEscala getElemento(int version){
		return misVersiones.get(version);
	}
	public void setElemento(VersionLineaEntradaEscala verLinea){
		misVersiones.add(verLinea);
	}
	// fin gestion del array
	
	// getters and setters
	public int getId_Escala() {
		return id_Escala;
	}
	public void setId_Escala(int id_Escala) {
		this.id_Escala = id_Escala;
	}
	public int getVersion_Escala() {
		return version_Escala;
	}
	public void setVersion_Escala(int version_Escala) {
		this.version_Escala = version_Escala;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getid_Linea() {
		return id_Linea;
	}
	public void setid_Linea(int id_Linea) {
		this.id_Linea = id_Linea;
	}
	public ArrayList<VersionLineaEntradaEscala> getMisVersiones() {
		return misVersiones;
	}
	public void setMisVersiones(ArrayList<VersionLineaEntradaEscala> misVersiones) {
		this.misVersiones = misVersiones;
	}
	// fin geters y seters
	
	// contructores
	public LineaEscala(int id_Escala, int version_Escala, int id_Linea, String nombre,Date fecha_creacion,String creador) {
		super();
		this.id_Escala = id_Escala;
		this.version_Escala = version_Escala;
		this.id_Linea = id_Linea;
		this.nombre = nombre;
		this.fecha_creacion=fecha_creacion;
		this.id_creador=creador;
	}
	public LineaEscala(int id_Escala, int version_Escala, int id_Linea, String nombre,Date fecha_creacion, String creador, ArrayList<VersionLineaEntradaEscala> misVersiones) {
		super();
		this.id_Escala = id_Escala;
		this.version_Escala = version_Escala;
		this.id_Linea = id_Linea;
		this.nombre = nombre;
		this.misVersiones=misVersiones;
		this.nombre = nombre;
		this.fecha_creacion=fecha_creacion;
		this.id_creador=creador;
	}
	public LineaEscala(int id_Linea, ArrayList<VersionLineaEntradaEscala> misVersiones) {
		super();
		this.id_Linea = id_Linea;
		this.misVersiones = misVersiones;
	}
	public LineaEscala() {
		super();
	}
	// fin constructores
	

	// impresion de la clase
	public String toStringSinArray() {
		return "LineaEscala [id_Escala=" + id_Escala + ", version_Escala=" + version_Escala + ", id_Linea="
				+ id_Linea + ", nombre=" + nombre + "]";
	}
	@Override
	public String toString() {
		return "LineaEscala [id_Escala=" + id_Escala + ", version_Escala=" + version_Escala + ", id_Linea="
				+ id_Linea + ", nombre=" + nombre + ", misVersiones=" + misVersiones + "]";
	}
	public void imprimirTodo(){
		
		System.out.println(toString());
		for(VersionLineaEntradaEscala L: misVersiones){
			System.out.println("\t" + L.toString());
		}
	}
	// fin impresion
	

	// ordena el array list por posicion
	public void ordenarVersionesLineas(){
		
		Collections.sort(misVersiones, (v1, v2) -> v1.getPos()-v2.getPos());
		
	}
	
	// valida las lineas entre si.
	public boolean validarLineas(){
		
		boolean esCorrecto = false;
		
		// debe comprobar que por grupo de versiones hay una linea activa
		//			si no fuera asi esta linea queda anulada
		// si es tipo formula no puede hacer referencia a ninguna linea
		//			posterior a la actual, solo a las anteriores. no a si misma.
		
		
		return esCorrecto;
		
	}

}
