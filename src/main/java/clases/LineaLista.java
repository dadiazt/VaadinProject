package clases;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;


// -Tabla  lineaLista
public class LineaLista {

	private String nombreLista;
	private int idLineaLista;
	private int pos;
	private String textoLinea;
	private int valor;	//<<--- valor de retorno
	
	public LineaLista(){}
	
	
	public LineaLista(String nombreLista, int idLineaLista, int pos, String textoLinea, int valor) {
		super();
		this.nombreLista = nombreLista;
		this.idLineaLista = idLineaLista;
		this.pos = pos;
		this.textoLinea = textoLinea;
		this.valor = valor;
	}

	// constructor para la carga desde la bbdd
	public LineaLista(int idLineaLista, int pos, String textoLinea, int valor) {
		super();
		this.idLineaLista = idLineaLista;
		this.pos = pos;
		this.textoLinea = textoLinea;
		this.valor = valor;
	}

	//prueba
	   public LineaLista(int pos, String textoLinea, int valor) {
		super();
		this.pos = pos;
		this.textoLinea = textoLinea;
		this.valor = valor;
	   }
    public LineaLista(String textoLinea, int valor) {
		super();
		this.textoLinea = textoLinea;
		this.valor = valor;


    }
    //para el indexOf de texttolinea en controllineas
	public LineaLista(String n){	this.textoLinea=n;	}
	
	//indexof de valor en controlLineas
	public LineaLista(String n,boolean b){	this.textoLinea=n;	}

	
	public int getIdLineaLista() {
		return idLineaLista;
	}

	public void setIdLineaLista(int idLineaLista) {
		this.idLineaLista = idLineaLista;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public String getTextoLinea() {
		return textoLinea;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public void setTextoLinea(String textoLinea) {
		this.textoLinea = textoLinea;
	}

	public String getNombreLista() {
		return nombreLista;
	}


	public void setNombreLista(String nombreLista) {
		this.nombreLista = nombreLista;
	}
	
	
	
//	public boolean comprobarValidez(){
//		boolean valido;
//		
//		Set<String> set = new HashSet<String>(this.lista);
//
//		// comprobamos que no hayan valores duplicados en el arraylist
//		if(set.size()<this.lista.size()){
//			return false;			
//		}
//		//comprobamos si alguno de los item de la lista contiene una --> ,
//		for(String i:this.lista){
//			if(i.contains(",")){
//				System.out.println("Uno de los valores contiene una --> ,");
//				return false;
//			}
//		}
//		
//		
//		return true;
//	}
	


	@Override
	    public boolean equals(Object object)
	    {
	        boolean sameSame = false;

	        if (object != null && object instanceof LineaLista)
	        {
	            sameSame = this.textoLinea.equals(((LineaLista) object).textoLinea);
	        }

	        return sameSame;
	    }

	//comparar 
	public boolean equalsValor(Object o){
	    if(o instanceof LineaLista){
	    	int comparar = ((LineaLista) o).valor;
	        return this.valor==comparar;
	    }
	    return false;
	}
	

	@Override
	public String toString() {
		return "LineaLista [idLineaLista=" + idLineaLista + ", pos=" + pos + ", textoLinea=" + textoLinea + ", valor="
				+ valor + "]";
	}
	
}
