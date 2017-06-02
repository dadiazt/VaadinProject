package clases;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Escalas implements Serializable {
	
	// idEscalador de la escala
	private int idEscala;
	// nombre de la escala
	private String Nombre;
	// administrador que crea la escala (o version de la escala)
	private String CreadorDeLaVersion;
	// es activa esta version. 
		// 1 esta siendo creada, esta en proceso de creacion
		// 2 terminada y activa. es una escala que se utiliza
		// 3 terminada pero inactiva. es una escala que ya no se usa
	private int esActiva;
	// esta siendo usado, si es true no se puede acceder a esta escala hasta que este libre
	private boolean estaLibre;
	
	// lista de versiones
	private ArrayList<VersionLineaEntradaEscala> misLineas = new ArrayList<VersionLineaEntradaEscala>();
	
	
	public boolean isEstaLibre() {
		return estaLibre;
	}

	public void setEstaLibre(boolean estaLibre) {
		this.estaLibre = estaLibre;
	}

	// destruye mis versiones.
	public void destruirLineas(){
		misLineas=null;
	}
	
	// INICIO _______ gestion del arraylist
	public VersionLineaEntradaEscala getElemento(int pos){
		return misLineas.get(pos);
	}
	public int getIdEscala() {
		return idEscala;
	}

	public void setIdEscala(int idEscala) {
		this.idEscala = idEscala;
	}

	public int getEsActiva() {
		return esActiva;
	}

	public void setEsActiva(int esActiva) {
		this.esActiva = esActiva;
	}

	public void setElemento(VersionLineaEntradaEscala linea){
		misLineas.add(linea);
	}
	public void delElemento(int pos){
		misLineas.remove(pos);
	}
	public void imprimirTodo(){
		System.out.println(toStringSinVersiones());
				for(VersionLineaEntradaEscala VLE: misLineas){
					System.out.println("\t\t\t"+VLE.toString());
				}
	}
	// FINAL ________ grestion del arraylist
	
	// INICIO ____ constructores
	// constructor con todos los datos

	// constructor vacio
	public Escalas() {
		super();
	}
	// constructor con todos los datos
	public Escalas(int idEscala, String nombre, String CreadorDeLaVersion,
			int esActiva, boolean estaLibre, ArrayList<VersionLineaEntradaEscala> misLineas) {
		super();
		this.idEscala = idEscala;
		this.Nombre = nombre;
		this.CreadorDeLaVersion = CreadorDeLaVersion;
		this.misLineas = misLineas;
		this.esActiva=esActiva;
		this.estaLibre=estaLibre;
	}
	// constructor que inicia todo menos el array
	public Escalas(int idEscala, String nombre, String CreadorDeLaVersion, int esActiva, boolean estaLibre) {
		super();
		this.idEscala = idEscala;
		Nombre = nombre;
		this.CreadorDeLaVersion = CreadorDeLaVersion;
		this.esActiva=esActiva;
		this.estaLibre=estaLibre;
	}
	// FINAL _____ constructores
	
	// INICIO ______ to string
	// tostring sin las versiones
	public String toStringSinVersiones() {
		return "Escalas [idEscala=" + idEscala + ", Nombre=" + Nombre 
				+ ", CreadorDeLaVersion=" + CreadorDeLaVersion + ", esActiva=" + esActiva+ ", estalibre=" + estaLibre+"]";
	}
	// IMPRIME TODO COMPLETO
	@Override
	public String toString() {
		return "Escalas [idEscala=" + idEscala + ", Nombre=" + Nombre 
				+ ", CreadorDeLaVersion=" + CreadorDeLaVersion + ", esActiva=" + esActiva+ ", esLibre=" + estaLibre+ ", misLineas="
				+ misLineas + "]";
	}
	// FINAL _______ to string
	
	// INICIO ______ geters and seters
	public int getidEscala() {
		return idEscala;
	}
		public void setidEscala(int idEscala) {
		this.idEscala = idEscala;
	}
	public String getNombre() {
		return Nombre;
	}
	public void setNombre(String nombre) {
		Nombre = nombre;
	}
	public ArrayList<VersionLineaEntradaEscala> getMisLineas() {
		return misLineas;
	}
	public void setMisLineas(ArrayList<VersionLineaEntradaEscala> misLineas) {
		this.misLineas = misLineas;
	}
	public String getCreadorDeLaVersion() {
		return CreadorDeLaVersion;
	}
	public void setCreadorDeLaVersion(String CreadorDeLaVersion) {
		CreadorDeLaVersion = CreadorDeLaVersion;
	}
	// FINAL _______ geters and seters


}
