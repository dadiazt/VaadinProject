package clases;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Tablas {
	
	private String id;
	private int idLinea;
	private int min,max,valor;
	ArrayList<Tablas> ArrayTablas= new ArrayList<Tablas>();

	
	public int getIdLinea() {
		return idLinea;
	}

	public void setIdLinea(int idLinea) {
		this.idLinea = idLinea;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public ArrayList<Tablas> getArrayTablas() {
		return ArrayTablas;
	}

	public void setArrayTablas(ArrayList<Tablas> arrayTablas) {
		ArrayTablas = arrayTablas;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	
	public Tablas(String id, int min, int max, int valor, int idLinea) {
		super();
		this.id = id;
		this.min = min;
		this.max = max;
		this.valor = valor;
		this.idLinea=idLinea;
	}

	public Tablas() {
		super();
	}
	public Tablas(String id) {
		this.id=id;
	}
	
	@Override
	public String toString() {
		return "Tablas [min=" + min + ", max=" + max + ", valor=" + valor + "]";
	}
	//añadir un numero
	public boolean anyadirNumero(String id,int min,int max,int valor, int idLinea) throws NumberFormatException, IOException{
		BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
		boolean opcion=false,correcte=false;
		
		
			//cuando el numero minimo introducido sea mas pequeño que el numero maximo introducido
			if(min<=max){
				boolean esigual=false;
				correcte=true;
				for(int i=0;i<ArrayTablas.size();i++){
					//System.out.println(ArrayTablas.size());
					//cuando el numero min introducido sea mas pequeño o igual a array con el atributo min O array de max sea mas grande que el numero minimo
					//Y Cuando el array de max sea mas grande o igual que el max introducido O que el array de min sea mas pequeño o igual a el max introducido
					//O que el array de valor sea igual a valor introducido
					if((ArrayTablas.get(i).getMin()<=min&&ArrayTablas.get(i).getMax()>=min)||(ArrayTablas.get(i).getMax()>=max&&ArrayTablas.get(i).getMin()<=max)||ArrayTablas.get(i).getValor()==valor){
						//añadir un nueva tabla
						esigual=true;
						correcte=false;
						break;
					}else{
						correcte=true;
						esigual=false; 
					}
					
				}
				
				if(!esigual){
					ArrayTablas.add(new Tablas(id,min,max,valor,idLinea));
				}
				
			}else{correcte=false;}
			Collections.sort(ArrayTablas,(t1,t2) ->t1.getMin()-t2.getMin());
			
			
			return correcte;
		}
	
	// retorna true si los datos estan ordenados
	public boolean comprobarOrden(){
		boolean correcte=true;
		for(int i=0;i<ArrayTablas.size();i++){
			if(i>=1){
			if(ArrayTablas.get(i).getMin()-ArrayTablas.get((i-1)).getMax()==1){
			}else{
				correcte=false;
				break;
			}
		}		
		
	}
		
		return correcte;
	}

	// retorna true si los datos estan ordenados
	public boolean gravarLineas(){
		
		boolean correcte=true;
		
		for(int i=0;i<ArrayTablas.size();i++){
			if(i>=1){
				if(ArrayTablas.get(i).getMin()-ArrayTablas.get((i-1)).getMax()==1){
				}else{
					correcte=false;
					break;
				}
			}		
		
		}
		
		return correcte;
	}

	
	public void Ordenar(){
		Collections.sort(ArrayTablas,(t1,t2) ->t1.getMin()-t2.getMin());
	}
		
	}