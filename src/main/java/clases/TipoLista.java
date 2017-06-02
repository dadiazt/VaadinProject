package clases;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

//Corresponde a la tabla Listas ,que contiene el nombre/id de las listas 
public class TipoLista {
	
	private String nombreLista;//id 
	private ArrayList<LineaLista> arrTL=new ArrayList<>(); // contiene las lineas de una lista.
	
	public TipoLista(){
		
	}
	//para el indexof
	public TipoLista(String n){
		this.nombreLista=n;		
	}
	
	public String getNombreLista() {
		return nombreLista;
	}
	public void setNombreLista(String nombreLista) {
		this.nombreLista = nombreLista;
	}

	public ArrayList<LineaLista> getArrTL() {
		return arrTL;
	}
	public void setArrTL(ArrayList<LineaLista> arrTL) {
		this.arrTL = arrTL;
	}
	public void addLineaLista(LineaLista tl){
		if(this.arrTL==null){
			this.arrTL=new ArrayList<>();
			this.arrTL.add(tl);			
		}else{
			this.arrTL.add(tl);
		}
		
	}
	
	//validar datos
	public boolean comprobarValidez(){
		boolean valido;
		
		ArrayList<String> arrSt=new ArrayList<>();		


		//comprobamos si alguno de los item de la lista contiene una --> ,
		for(LineaLista i:this.arrTL){
			arrSt.add(i.getTextoLinea());
			if(i.getTextoLinea().contains(",")){
				System.out.println("Uno de los valores contiene una --> ,");
				return true;
			}
		}
		Set<String> set = new HashSet<String>(arrSt);

		// comprobamos que no hayan valores duplicados en el arraylist
		if(set.size()<this.arrTL.size()){
			System.out.println("Hay valores dobles");
			return true;			
		}	
		System.out.println("todo OK");
		return false;
	}
	
	//comprobar orden de posicion
	public boolean comprobarOrden(){
//		LineaLista nl=new LineaLista(6,"88",9);
//		arrTL.add(nl);
		Collections.sort(arrTL,(t1,t2) ->t1.getPos()-t2.getPos());
	
		boolean correcte=true;
		System.out.println("··· "+arrTL.size());
		for(int i=0;i<arrTL.size()-1;i++){
			System.out.println("===>) "+arrTL.get(i+1).getPos()+" ,, "+arrTL.get((i)).getPos());
			if(arrTL.get(i+1).getPos()-arrTL.get((i)).getPos()==1){
				System.out.println("bien.");
			}else{
				System.out.println("esta mal");
				return false;
			}
		}
		return true;	
	}
	
	//valor de retorno dobles ?
	/**
	 * public static boolean containsId(List<DTO> list, long id) {
    for (DTO object : list) {
        if (object.getId() == id) {
            return true;
        }
    }
    return false;
}

	 */
	
	   
	@Override
	public String toString() {
		return "TipoLista [nombreLista=" + nombreLista + ", arrTL=" + arrTL + "]";
	}

	@Override
	    public boolean equals(Object object)
	    {
	        boolean sameSame = false;

	        if (object != null && object instanceof TipoLista)
	        {
	            sameSame = this.nombreLista.equals(((TipoLista) object).nombreLista);
	        }

	        return sameSame;
	    }	
}
