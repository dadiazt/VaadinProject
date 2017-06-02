package myObjects;

import java.util.ArrayList;
import java.util.List;


import clases.PersonaValorada;
import clases.UsuarioApp;

public class ClaseObjStaticas {
	
	public interface ValueChangeListener {
		public void onValueChange(String newValue);
	}

	
	static ValueChangeListener valueChangeListener;
	
	public static String idUsuarioActual;
	public static String nombreUsuarioActual;
	public static String funcioUsuariActual;
	public static PersonaValorada pvEscala;
	public static ArrayList<String> dinamicas;
	public static ArrayList<String> idDinamicas;
	public static String DinamicaActual;
	public static int totalUsuariosApp;
	public static ArrayList<String[]> arrPVYEscalasPorTerminar;//
	
	
	public static String prueba="ricardo";
	public static String Cursoprueba="DAM";
	public static String NombrePersonaValorada;
	public static String valooor;
	public static String valooor2;
	
	
	public static void setValor(String newValor){
		valooor = newValor;
		valueChangeListener.onValueChange(newValor);
	}
	
	
	public static void setValueChangeListener(ValueChangeListener listener){
		valueChangeListener = listener;
	}

	
}
