package clases;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import entradaDeDatos.EntradaDeDatos;
//import gestorEscalas.MainTaller;
import validacion.validacionGeneral;

/*
 * NOTAS MUY IMPORTANTES
 * 
 * ESTA PARTE ES EL CORAZON DEL PROGRAMA
 * ESTA CLASE CONTIENE LOS GETTERS Y SETTERS DE TODOS LOS ATRIBUTOS.
 * CONTIENE PETICION DE DATOS Y VALIDACION DE LOS MISMO (DURANTE LA ENTRADA)
 * 
 * las lineas no tienen nombre. se hace referencias por id linea+version.
 * 
 */

@SuppressWarnings("serial")
public class VersionLineaEntradaEscala implements Serializable {

	// id de la escala la que pertenece la linea
	private int id_escala;
	// id linea a la que pertenece
	private int idLinea;
	// indica si la version se puede usar o no.
	private Boolean activo;

	// texto que muestra el campo. relacionado con no nulo. tipo=String
	private String textoVisible;
	// posicion dentro del formulario. no nulo. entero.
	private int pos;
	// 1 fecha
	// 2 texto a introducir o modificar.
	// 3 booleano
	// 4 formula (contiene una formula) el atributo operacion debe contener
	/*
			mal si digito diferente de +-/*^()0123456789
			mal si termina por +-/*.(^$
			mal si comienza por )/*.^
			no puede haber dos veces seguidas los siguientes signos +-/*.$
			no puede haber dos seguidos(entre si) de los siguientes elementos +-x/.
			Entre los siguientes signos debe haber numeros +-/*.^
			no pueden estar seguidos los siguientes signos .( ni ).
			un punto debe tener numeros a los daos y sinbolos +-/*^()
			
			los numeros precedidos de $ son literales
			los bumeros deben ser enteros positivos y señalar a deldas monores que la actual
	*/
	// 5 elegir varios en una lista
	// 6 elegir uno en una lista
	// 7 entero
	// 8 float
	// 9 texto no modificable.
	// 10 busqueda en tablas.
	// 11 grafico
	// tipo de dato que contendra este campo.
	private int tipo;
	// nulo. indica si el campo puede ser nulo o no. booleano
	// si vale 1 admite nulo o ""
	// si vale 2 pone un valor por defecto int=0, double=0, fecha=fechaActual, booleano=true
	// si vale 3 no sale hasta que se ponga un valor ni nulo ni por defecto.
	// si vale 4 el valor por defecto es false
	private int puedeSerNulo;
	// visible. indica si es visible o no. no nulo. booleano
	private boolean lineaVisible;
	// Operacion a realizar. puede ser nulo. String.
	// Para hacer las media aritmeticas si un valor es 0
	// no cuenta como elemento para la media. Ejemplo
	// 0, 4, 6, 0, 10 la media es 4
	// null, 4, 6, null, 11 la media es 7 (los null no cuentan).
	// En operaciones hay referencias a valores contenidos el lineas pero
	// version de la linea.
//	// cada vez que se cambia aumenta en uno
//	int versionLinea;	// también números (constantes)
	// este campo se guarda en referencia cuando tipo=4
	private String operaciones;
	/*
	 * el valor introducido por el usuario no es necesario en esta parte.
	// Valor. Este es el contenido introducido por el usuario.
	// puede ser nulo si el campo correspondiente asi lo indica. String.
	private String contenido;
	*/	
	// referencia a donde se encuentran los datos
	// sea tabla o lista
	private String referencia;
	// nombre que pone el usuario a 	la linea para identificarla
	private String nombreLinea;
	// es entrada o salida
	private boolean esEntrada;
		
	// se crea un objeto que se utilizara para validar los datos
	validacionGeneral miValidacion = new validacionGeneral();
	
	public boolean isEsEntrada() {
		return esEntrada;
	}

	public void setEsEntrada(boolean esEntrada) {
		this.esEntrada = esEntrada;
	}

	public String getNombreLinea() {
		return nombreLinea;
	}

	public void setNombreLinea(String nombreLinea) {
		this.nombreLinea = nombreLinea;
	}

	// ___ inicio validacion formula _______________ inicio validacion formula _______ inicio validacion formula _____________
	public int validarFormula(){
		
		int esCorrecta=0;

		// si el tipo no es formula retorna 0 (esta bien)
		// sino valida la formula
		if(tipo!=4) return 0;
		
		// 11 = no introducida formula
		if(operaciones.length()==0) return 11; // cambiar codigo a retornar
		// 1 = ultimo caracter no es valido
		if((esCorrecta=siUltimoCaracNoValido())!=0) return esCorrecta;
		// 2 = primer caracter no valido
		if((esCorrecta=siPrimerCaracNoValido())!=0) return esCorrecta;
		// 3 = dos caracteres seguidos no validos +-*/^
		// 4 = despues de un punto debe haber un numero
		// 5 = antes de un punto debe haber un numero
		// 6 = la cadena contiene un caracter no valido
		// 7 = la cadena contiene dos caracteres seguidos no validos
		// 8 = caracter no valido despues de la apertura de un parentesis
		if((esCorrecta=siCaracterNoEsValido())!=0) return esCorrecta;
		// 9 = hace referencia a una celda no valida.
		// 10 = numero de celda no valido
		if((esCorrecta=siNumCeldaEsValido())!=0) return esCorrecta;
				
		return esCorrecta;
	}
	
	// obtiene los numeros de celdas.
	private int siNumCeldaEsValido(){
		
		// rompe la cadena en los separadores
		String[] cadena=operaciones.split("\\(\\)\\+\\-\\*\\/\\.\\^");
		// recorre todo el array de subcadenas
		for(int i=0;i<cadena.length;i++){
			// si empieza por dolar es una constante por lo que
			// descarta que sea contante o sin longitud (dos separadores seguidos)
			if(cadena[i].charAt(0)!='$' && cadena[i].length()>0){
				try{ 
					// se obtiene la posicion a la que hace referencia
					int num = Integer.parseInt(cadena[i]);
					if(num>=pos) return 9;
				}catch(Exception e){ 
					return 10;					
				}
			}
		}
		return 0;
	}
	
	// comprueba si el primer caracter es valido
	private int siPrimerCaracNoValido(){
			
		switch(operaciones.charAt(0)){
		case '/': 
		case '*':	
		case '.': 
		case '^':
		case ')': 
			return 2;
		default:
			return 0;
		}
		
	}
	
	// retorna cero si esta bien 1 si esta mal
	private int siUltimoCaracNoValido(){
		
		switch(operaciones.charAt((operaciones.length()-1))){
		case '+': 
		case '-':
		case '/': 
		case '*':	
		case '$': 
		case '^':
		case '.': 
		case '(': 
			return 1;
		default:
			return 0;
		}
		
	}
	
	private int siCaracterNoEsValido(){
		
		int esCorrecto=0, error=0;
		
		for(int i=0;i<operaciones.length();i++){
			switch(operaciones.charAt(i)){
				case '1': 
				case '2':
				case '3': 
				case '4':	
				case '5': 
				case '6':
				case '7': 
				case '8':
				case '9': 
				case '0':
					// no hace nada es correcto
					break;
				case '+': 
				case '-':
				case '/': 
				case '*':
				case '^':
					if(0!=siguienteCaracterValido(i)) return 3;
					break;
				case '.':
					error=puntoCorrecto(i);
					if(0!=error) return error;
				case '$': 
					error=dolarCorrecto(i);
					if(0!=error) return error;
				case '(':
					error=parentesisCorrecto(i);
					if(0!=error) return error;
				case ')':
					// no hace nada
				default:
					return 6;
			}
		}
		// si llega aqui es porque esta todo bie
		return esCorrecto;

	}
	
	private int parentesisCorrecto(int pos){
		
		switch(operaciones.charAt(pos+1)){
			case ')':
			case '.':
			case '/':
			case '*':
			case '^':
				return 8;
			default:
				return 0;
		}
		
	}
	
	private int dolarCorrecto(int pos){
		// he comprobado que no termina ni empieza por lo tanto no es el final ni el principio
		switch(operaciones.charAt(pos+1)){
			case '+': 
			case '-':
			case '/': 
			case '*':
			case '$':
			case '.':
				return 7;
			default:
				// no hace nada
		}
		
		switch(operaciones.charAt(pos-1)){
		case ')': 
		case '.':
			return 8;
		default:
			return 0;
	}
	}
	
	private int puntoCorrecto(int pos){

		// he comprobado que no termina ni empieza por lo tanto no es el final ni el principio
		switch(operaciones.charAt(pos+1)){
			case '1': 
			case '2':
			case '3': 
			case '4':	
			case '5': 
			case '6':
			case '7': 
			case '8':
			case '9': 
			case '0':
				break;
			default:
				return 4;
		}
		switch(operaciones.charAt(pos-1)){
			case '1': 
			case '2':
			case '3': 
			case '4':	
			case '5': 
			case '6':
			case '7': 
			case '8':
			case '9': 
			case '0':
				break;
			default:
				return 5;
		}
		
		return 0;
		
	}
	
	private int siguienteCaracterValido(int pos){

		// he comprobado que no termina ni en +-*/^. por lo tanto no esta al final de la linea
		switch(operaciones.charAt(pos+1)){
			case '+': 
			case '-':
			case '/': 
			case '*':
			case '.':
			case '^':
			case ')':
				return 3;
			default:
				return 0;
		}
		
	}
	// ___ fin validacion formula _______________ fin validacion formula _______ fin validacion formula _____________

	// pone a nulo contenido
	// si es tipo=9 pide el texto sino pone como nulo
	public void dameContenido() {

		if (tipo == 9) {
			System.out.println("\nDebe especificar un texto no modificable.");
			EntradaDeDatos miEntrada = new EntradaDeDatos();
			textoVisible = miEntrada.dameStringInclusoNulo();
		} else {
			textoVisible = "";
		}
	}

	// pone y valida visible
	public void dameEsVisible() {

		System.out.println("\nDigame si esta linea sera visible por el usuario[s/S=si]");
		EntradaDeDatos miEntrada = new EntradaDeDatos();
		lineaVisible = miEntrada.dameLogico();

	}

	// pone y valida posicion
	public void damePosicion(LineaEscala Lineas) {

		EntradaDeDatos miEntrada = new EntradaDeDatos();

		boolean salir = false;
		@SuppressWarnings("unused")
		int encontrado = 0;

		// debe comprobar en el arraylist si hay una linea con esa
		while (!salir) {
			// pide y recibe el numero de linea
			System.out.println("\nDeme la posicion de esta linea en el formulario: ");
			// pide y memoriza un entero positivo mayor de cero.
			int posicion = miEntrada.dameEnteroPositivoSinCero();
			pos = posicion;
			// poner para salir
			salir = true;
		}
	}

	// pone y valida tipo de linea
	public void dameTipo() {
		EntradaDeDatos miEntrada = new EntradaDeDatos();
		boolean salir = false;

		while (!salir) {
			System.out.println("Introduzca el tipo: [de 1 a 9 � el valor 100]: ");
			tipo = miEntrada.dameEnteroPositivoSinCero();
			if (tipo > 0 && tipo < 10) {
				salir = true;
			}
			if (tipo == 100) {
				salir = true;
			}
			if (!salir) {
				System.out.println("Dato introducido erroneo.");
			}

		}
		// si no hay listas no deja elegir ni 5 ni 6
		// si no hay tablas no deja elegir ni el 100 ni superior.
		// si elige formula no puede estar operaciones sin valor(nula)
	}
	/*
	 * // 1 fecha // 2 texto a introducir o modificar. // 3 booleano. (pregunta
	 * el valor por defecto) // 4 formula (contien una formula matematica) la
	 * formula esta en operaciones. // el resultado en contenido. (puede ser
	 * visible o no) // 5 elegir varios en una lista (debe haber en referencia
	 * un nombre a una lista que sea valida) // 6 elegir uno en una lista (debe
	 * haber en referencia un nombre a una lista que sea valida) // 7 entero
	 * (valida entero) // 8 float (valida decimal) // 9 texto no modificable.
	 * (comprobar que en contenido hay un texto) // 100 busqueda en tablas.
	 * (debe haber en referencia un nombre a una tabla que sea valida)
	 */
	//
	// fin entrada de datos y
	// confirmacio__________________________________________________________________________________________

	// geter y seters
	public int getId_escala() {
		return id_escala;
	}

	public void setId_escala(int id_escala) {
		this.id_escala = id_escala;
	}

	public String getTextoVisible() {
		return textoVisible;
	}

	public void setTextoVisible(String textoVisible) {
		this.textoVisible = textoVisible;
	}

	public int getIdLinea() {
		return idLinea;
	}

	public void setIdLinea(int id) {
		this.idLinea = id;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public String gettextoVisible() {
		return textoVisible;
	}

	public void settextoVisible(String textoVisible) {
		this.textoVisible = textoVisible;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public int getPuedeSerNulo() {
		return puedeSerNulo;
	}

	public void setPuedeSerNulo(int puedeSerNulo) {
		this.puedeSerNulo = puedeSerNulo;
	}

	public boolean isLineaVisible() {
		return lineaVisible;
	}

	public boolean getLineaVisible() {
		return lineaVisible;
	}

	public void setLineaVisible(boolean lineaVisible) {
		this.lineaVisible = lineaVisible;
	}

	public String getOperaciones() {
		return operaciones;
	}

	public void setOperaciones(String operaciones) {
		this.operaciones = operaciones;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	// fin geters y seters

	// constructores
	public VersionLineaEntradaEscala(VersionLineaEntradaEscala verEs) {
		
		setId_escala(verEs.getId_escala());
		setTextoVisible(verEs.getTextoVisible());
		setIdLinea(verEs.getIdLinea());
		setActivo(verEs.getActivo());
		setPos(verEs.getPos());
		setTipo(verEs.getTipo());
		setPuedeSerNulo(verEs.getPuedeSerNulo());
		setLineaVisible(verEs.isLineaVisible());
		setOperaciones(verEs.getOperaciones());
		setReferencia(verEs.getReferencia());
		
	}

	public VersionLineaEntradaEscala() { }

	
	public VersionLineaEntradaEscala(int id_escala, int idLinea,
			Boolean activo, String textoVisible, int pos, int tipo,
			int puedeSerNulo, boolean lineaVisible, String operaciones, String referencia,
			String nombreLinea, boolean esEntrada) {
		this.id_escala = id_escala;
		this.idLinea = idLinea;
		this.activo = activo;
		this.textoVisible = textoVisible;
		this.pos = pos;
		this.tipo = tipo;
		this.puedeSerNulo = puedeSerNulo;
		this.lineaVisible = lineaVisible;
		this.operaciones = operaciones;
		this.referencia = referencia;
		this.nombreLinea = nombreLinea;
		this.esEntrada=esEntrada;
	}

	// este qued inutil
//	public VersionLineaEntradaEscala(int Escala, int idLinea, 
//			Boolean activo, String textoVisible, int pos, int tipo,
//			int puedeSerNulo, boolean lineaVisible, String operaciones, String referencia) {
//
//		this.id_escala = Escala;
//		this.idLinea = idLinea;
//		this.activo = activo;
//		this.textoVisible = textoVisible;
//		this.pos = pos;
//		this.tipo = tipo;
//		this.puedeSerNulo = puedeSerNulo;
//		this.lineaVisible = lineaVisible;
//		this.operaciones = operaciones;
//		this.referencia = referencia;
//		
//	}
	// constructores

	// Este metodo comprueba que los datos contenidos en la linea son correctos
	// entre si.
	// No se comprueba con relacion a otras lineas. Solo es interno.
	/*
	 * criterio para la validacion
	 * 
	 * 
	 * 
tipo				visible			nulo/no nulo/por defecto

1 fecha				siempre			puede ser(si/no)/por defecto

2 texto
  introducir		siempre			puede ser (si/no)/no puede ser valor por defecto

3 booleano			siempre			siempre(no)/por defecto(true) -- (no puede ser nulo nunca)

4 formula			si/no			nunca nulo

5 elegir varios
  de una lista		si				puede ser(si/no) (puede no seleccionar nada/puede que se quiera alguna seleccion)

6 elegir uno
  de una lista		si				puede ser(si/no) (puede no seleccionar nada/puede que se quiera alguna seleccion)

7 entero			siempre			puede ser(si/no)/por defecto(0)

8 float				siempre			puede ser(si/no)/por defecto(0)

9 texto no
  modificable		siempre			siempre(no)(no puede ser nulo)

10-100
  tabla				si/no			no nulo/por defecto(0) (no puede ser nulo)

	 */
	public boolean esCorrecto() {

		// por defecto es true. si encuentra algun error lo convierte a false
		boolean correcto = true;
		String texto_visible=this.textoVisible;
		if(texto_visible==null) texto_visible="";
		if(texto_visible.length()<1) texto_visible="";

		if (lineaVisible == false
				&& (tipo == 1 || tipo==2 || tipo == 3 || tipo == 5 || tipo == 6 || tipo == 7 || tipo == 8 || tipo == 9))
			return false;

		// no puede ser booleano y nulo. O por defecto o espera un dato
		if(tipo==3 && puedeSerNulo==1) return false;
		// no puede ser tipo 3=booleano y puedesernulo<>2 y de 4
		// si es booleano y no es 2 o 4 en puedesernulo esta mal
		if(tipo==3 && (puedeSerNulo!=2 && puedeSerNulo!=4)) return false;
		// no puede ser formula y no contener nada
		// debe comprobar la formula correcta.??
		if(tipo==4 && operaciones.length()==0) return false;
		else if(validarFormula()>0) return false;
		// elegir uno o varios de una lista. se puede elgir o no pero no por defecto
		if((tipo==5 || tipo==6) && puedeSerNulo==2) return false;
		// en el tipo 9 no tiene sentido el puede ser nulo. siempre se imprimira el texto
		// y no recibe ningun dato por lo que no hay nada que validar escepto que exista texto a imprimir
		if(tipo==0 && texto_visible.length()==0) return false;
			
		return correcto;
		
	}

	@Override
	public String toString() {
		return "VersionLineaEntradaEscala [id_escala=" + id_escala 
				+ ", idLinea=" + idLinea 
				+ ", activo=" + activo + ", textoVisible="
				+ textoVisible + ", pos=" + pos + ", tipo=" + tipo + ", puedeSerNulo=" + puedeSerNulo
				+ ", lineaVisible=" + lineaVisible + ", operaciones=" + operaciones 
				+ ", referencia=" + referencia + ", nombreLinea=" + nombreLinea + ", miValidacion=" + miValidacion
				+ "]";
	}

}