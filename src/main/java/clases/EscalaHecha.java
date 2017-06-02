package clases;

import java.util.Date;


//Esta clase Guarda las lineas de la escala rellenada ya sea a medias o por completo
//Corresponde a la tabla EscalaHecha
public class EscalaHecha {
	


	private int id_escala ; // desde consulta
	private int id_lineaEscala; // desde consulta
	private int id_usuario;// id_personaValorada  // asignar valor al guardar en la bbdd
	private String textoVisible; // desde consulta
	private int pos; // desde consulta
	private int tipo; // desde consulta
	private int nulo;// puede ser nulo  //  desde consulta
	private boolean lineaVisible; // desde consulta
	private String contenido; // asignar valor al guardar en la bbdd
	private String referencia; // desde consulta
	private boolean esEntrada;	//desde consulta
	private String id_monitor; // asignar valor al guardar en la bbdd
	private Date fechaEscala; //fecha en la que se acabo la escala
	private String id_dinamica; // asignar valor al guardar en la bbdd

	private int id_escalaHecha; // asignar valor al guardar en la bbdd
	private boolean Finalizada; // asignar valor al guardar en la bbdd
	
	
	
	//Constructor que se usa para cargar los campos de la tabla LineaEscala para la EscalaHecha
	public EscalaHecha(int id_escala, int id_lineaEscala, String textoVisible, int pos, int tipo, int nulo,
			boolean lineaVisible,String referencia, boolean esEntrada) {
		super();
		this.id_escala = id_escala;
		this.id_lineaEscala = id_lineaEscala;
		this.textoVisible = textoVisible;
		this.pos = pos;
		this.tipo = tipo;
		this.nulo = nulo;
		this.lineaVisible = lineaVisible;
		this.referencia = referencia;
		this.esEntrada = esEntrada;
	}

	//constructor de cargarEscalaDeUsuario() ,carga las escalas hechas terminadas=true
	public EscalaHecha(int id_escala, int id_lineaEscala, int id_usuario, String textoVisible, int pos, int tipo,
			int nulo, boolean lineaVisible, String contenido, String referencia, boolean esEntrada, String id_monitor,
			Date fechaEscala, String id_dinamica, int id_escalaHecha) {
		super();
		this.id_escala = id_escala;
		this.id_lineaEscala = id_lineaEscala;
		this.id_usuario = id_usuario;
		this.textoVisible = textoVisible;
		this.pos = pos;
		this.tipo = tipo;
		this.nulo = nulo;
		this.lineaVisible = lineaVisible;
		this.contenido = contenido;
		this.referencia = referencia;
		this.esEntrada = esEntrada;
		this.id_monitor = id_monitor;
		this.fechaEscala = fechaEscala;
		this.id_dinamica = id_dinamica;
		this.id_escalaHecha = id_escalaHecha;
	}
	
	
	//Constructor para cargar las escalas hechas ,(sin finalizada)

	
	public int getId_escala() {		return id_escala;	}
	public void setId_escala(int id_escala) {		this.id_escala = id_escala;	}

	
	public int getId_lineaEscala() {		return id_lineaEscala;	}
	public void setId_lineaEscala(int id_lineaEscala) {		this.id_lineaEscala = id_lineaEscala;	}

	
	public int getId_usuario() {		return id_usuario;	}
	public void setId_usuario(int id_usuario) {		this.id_usuario = id_usuario;	}
	
	public String getTextoVisible() {		return textoVisible;	}	
	public void setTextoVisible(String textoVisible) {		this.textoVisible = textoVisible;	}
	
	public int getPos() {		return pos;	}
	public void setPos(int pos) {		this.pos = pos;	}
	
	public int getTipo() {		return tipo;	}
	public void setTipo(int tipo) {		this.tipo = tipo;	}
	
	public int getNulo() {		return nulo;	}
	public void setNulo(int nulo) {		this.nulo = nulo;	}
	
	public boolean getLineaVisible() {		return lineaVisible;	}
	public void setLineaVisible(boolean lineaVisible) {		this.lineaVisible = lineaVisible;	}

	
	public String getContenido() {		return contenido;	}
	public void setContenido(String contenido) {		this.contenido = contenido;	}
	
	public String getReferencia() {		return referencia;	}
	public void setReferencia(String referencia) {		this.referencia = referencia;	}
	
	public String getId_monitor() {		return id_monitor;	}
	public void setId_monitor(String id_monitor) {		this.id_monitor = id_monitor;	}
	
	public Date getFechaEscala() {		return fechaEscala;	}
	public void setFechaEscala(Date fechaEscala) {		this.fechaEscala = fechaEscala;	}
	
	public String getId_dinamica() {		return id_dinamica;	}
	public void setId_dinamica(String id_dinamica) {		this.id_dinamica = id_dinamica;	}
	
	public int getId_escalaHecha() {		return id_escalaHecha;	}
	public void setId_escalaHecha(int id_escalaHecha) {		this.id_escalaHecha = id_escalaHecha;	}
	
	public boolean isFinalizada() {		return Finalizada;	}
	public void setFinalizada(boolean finalizada) {		Finalizada = finalizada;	}
	public boolean isEsEntrada() {
		return esEntrada;
	}
	public void setEsEntrada(boolean esEntrada) {
		this.esEntrada = esEntrada;
	}


	@Override
	public String toString() {
		return "EscalaHecha [id_escala=" + id_escala + ", id_lineaEscala=" + id_lineaEscala + ", id_usuario="
				+ id_usuario + ", textoVisible=" + textoVisible + ", pos=" + pos + ", tipo=" + tipo + ", nulo=" + nulo
				+ ", lineaVisible=" + lineaVisible + ", contenido=" + contenido + ", referencia=" + referencia
				+ ", esEntrada=" + esEntrada + ", id_monitor=" + id_monitor + ", fechaEscala=" + fechaEscala
				+ ", id_dinamica=" + id_dinamica + ", id_escalaHecha=" + id_escalaHecha + ", Finalizada=" + Finalizada
				+ "]";
	}
	
	

}
