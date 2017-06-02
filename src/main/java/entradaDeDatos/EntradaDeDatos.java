package entradaDeDatos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import validacion.validacionGeneral;

public class EntradaDeDatos {
	
	public EntradaDeDatos(){}
	
	// pide y comprueba que se a introducido un entero
	public int dameEnteroPositivoSinCero(){
		
		System.out.print("\nDame un entero positivo (cero no): ");
		@SuppressWarnings("resource")
		Scanner miScan = new Scanner(System.in);
		
		int tmpInt=0;
		boolean bien=false;
		
		while(!bien){
			String tmp=miScan.nextLine();
			try{
				tmpInt=Integer.parseInt(tmp);
				if(tmpInt>0) { bien=true; }
				else{ System.out.println("EL dato introducido no es un entero positivo mayor que cero."); }
			}catch(Exception e){
				bien=false;
				System.out.println("EL dato introducido no es un entero positivo mayor que cero.");
			}
		}
		return tmpInt;
		
	}
	
	// pide y comprueba que se a introducido un entero
	public int dameEnteroEntre(int min, int max){
		
		System.out.print("Dame un entero positivo entre: "+min+" y "+max);
		@SuppressWarnings("resource")
		Scanner miScan = new Scanner(System.in);
		
		int tmpInt=0;
		boolean bien=false;
		
		while(!bien){
			String tmp=miScan.nextLine();
			try{
				tmpInt=Integer.parseInt(tmp);
				if(tmpInt>=min && tmpInt<=max) { bien=true; }
				else{ System.out.println("EL dato introducido no es un entero positivo dentro del margen solicitado."); }
			}catch(Exception e){
				bien=false;
				System.out.println("EL dato dato introducido no es un entero positivo dentro del margen solicitado.");
			}
		}
		return tmpInt;
		
	}
	
	// pide y comprueba que se a introducido un entero
	public int dameOpcion(){
		
		System.out.print("Dame un entero: ");
		Scanner miScan = new Scanner(System.in);
		
		int tmpInt=0;
		boolean bien=false;
		
		while(!bien){
			String tmp=miScan.nextLine();
			try{
				tmpInt=Integer.parseInt(tmp);
				bien=true;
			}catch(Exception e){
				bien=false;
				System.out.println("EL dato introducido no es un entero.");
			}
		}
		return tmpInt;
		
	}
	
	// pide y comprueba que recibe un decimal. 
	public long dameDecimal(){
		
		System.out.print("Dame un decimal: ");
		Scanner miScan = new Scanner(System.in);
		
		long tmpInt=0;
		boolean bien=false;
		
		while(!bien){
			String tmp=miScan.nextLine();
			try{
				tmpInt=Long.parseLong(tmp);
				bien=true;
			}catch(Exception e){
				bien=false;
				System.out.println("EL dato introducido no es un numero decimal.");
			}
		}
		return tmpInt;
		
	}
	
	// pide y comprueba que recibe un booleano.
	// si se responde s o S es true. cualquier otra respuesta es false
	public boolean dameLogico(){
		
		System.out.print("Dame un logico[s/S = True]: ");
		Scanner miScan = new Scanner(System.in);
		
		int tmpInt=0;
		boolean bien=false,salir=false;
		
		while(!salir){
			String tmp=miScan.nextLine();
			if(tmp.compareTo(""+'s')==0 || tmp.compareTo(""+'S')==0){
				salir=bien=true;
			}
			else{
				bien=false;
				salir=true;
//				System.out.println("EL dato introducido es erroneo. Introduzca s ó S para true. Cualquier otro valor es false.");
			}

		}
		return bien;
		
	}
	
	// pide y comprueba que recibe un string.
	// si recibe cadena nula pide un texto
	public String dameString(){
		System.out.print("Dame un texto: ");
		Scanner miScan = new Scanner(System.in);
		
		int tmpInt=0;
		boolean bien=false;
		String tmp="";
		
		while(!bien){
			tmp=miScan.nextLine();
			if(tmp.length()>0) bien=true;
			else{
				bien=false;
				System.out.println("Tiene que escribir algo.");
			}

		}
		return tmp;
	}
	
	// pide y comprueba que recibe un string.
	public String dameStringInclusoNulo(){
		
		System.out.print("Dame un texto: ");
		Scanner miScan = new Scanner(System.in);
		
		int tmpInt=0;
		boolean bien=false;
		String tmp="";
		
		while(!bien){
			tmp=miScan.nextLine();
			bien=true;

		}
		return tmp;
	}
	// pide y comprueba que recibe una fecha.
	public Date dameFecha(){
		System.out.print("Dame una fecha[dd/mm/yyyy]: ");
		Scanner miScan = new Scanner(System.in);
		validacionGeneral miVal=new validacionGeneral();
		
		int tmpInt=0;
		boolean bien=false;
		String tmp="";
		Date date=null;
		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
		
		while(!bien){
			tmp=miScan.nextLine();
			
			if(!(bien=miVal.esFecha(tmp))){
				System.out.println("Tiene que escribir una fecha correcta. El formato es dia, mes y año separados por el signo /");
			}

			
		}
		try{
			date=formatoFecha.parse(tmp);
		}catch(ParseException e){
			
		}
		
		return date;
	}
	
	// pide confirmacion. recibe un texto que explica lo que se quiere hacer
	// si no se ponenada en el texto solo pregunta si se desea continuar
	// retorna un true si la respuesta del usuario es correcta
	public boolean dameConfirmacion(String texto){
		
		System.out.println(texto);
		System.out.println("¿ Esta usted seguro que desea continuar. ?");
		return dameLogico();
	}
	

}
