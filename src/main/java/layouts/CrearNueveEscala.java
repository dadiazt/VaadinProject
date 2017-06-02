package layouts;

import java.io.Serializable;
import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.GregorianCalendar;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;
//import com.vaadin.ui.PopupView;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
//import com.vaadin.ui.VerticalLayout;

import BBDD.ConexionBBDD;
import clases.Escalas;
//import clases.LineaEscala;
import clases.Lista;
import clases.Tabla;
//import clases.VersionEscala;
import clases.VersionLineaEntradaEscala;
import myObjects.ClaseObjStaticas;
import validacion.validacionGeneral;

@SuppressWarnings("serial")
public class CrearNueveEscala implements Serializable {
	
	private static final String anchoPeque="1000"; 
	private static final String altoPeque="1000";

	// tabla de las lineas de las escalas
	private Table tablaLinea;
	// contiene las escalas
	private static Table tablaEscalas;
	
	// se inicia la conexion a la bbDD esta bacia no contine nada
	ConexionBBDD miBBDD  = new ConexionBBDD();
	
	// parte de administrador escalas
	// Labels
	//Label nomAdmin1 = new Label(); // label administrador
	Label nomEscNuevaLabel = new Label(); // lavel nombre escala nueva
	Label errorLabelAdminNuevo = new Label(); // label de mendajes de errores
	Label LabelTextoLinea = new Label(); // label texto linez
	// cajas de texto
	TextField nonEscNuevaText=new TextField();
	// botones
	Button anyadirEscala=new Button("Añadir Escala");
	
	// boton nueva linea
	Button bNuevo = new Button("Nueva linea");
	Button bBorrar= new Button("Borrar linea");
	Button anyadirLineas=new Button("Añadir linea");
	//Button volverLogin=new Button("Volver a logearse");
	Button reiniciarNueveEscala=new Button("Reiniciar");
	
	// se ha terminado de añadir lineas y se guiere grabar la escala en la bbdd
	Button terminarLineas=new Button("Terminar");
	Button guardarSinTerminar=new Button("Guardar sin terminar");
	// labels de lineas
	Label idLbl = new Label("Id linea"); // label informativo id linea
	Label idLblLinea = new Label(); // label donde se pone el id de linea
	Label nombreLineaLbl = new Label("nombre linea"); // label informaivo nom
	Label posicionLabel = new Label("Posición en la escala: "); // label informativo
	// checksbox visible
	CheckBox chbVisible = new CheckBox("Es visible");
	// checksbox entrada salida
	CheckBox chbEntrada = new CheckBox("Entrada=true/Salida=false");
	// checkBox si/no configuracion
	// CheckBox chbConfiguracion = new CheckBox("Valor por defecto");
	// combo box
	ComboBox cbTipo = new ComboBox("Tipo");
	ComboBox cbReferencia = new ComboBox("Referencia");
	// combobox nulo o pide dato o valor por defecto
	ComboBox cbNulo = new ComboBox("Valor por defecto");
	
	// boton borrar lines
	Label formulaLbl = new Label("Formula");
	Label ValorLbl = new Label();

	// cajas de texto que reciben datos
	// recie el nombre de la linea
	TextField nombreLineaTxt = new TextField();
	// recibe la formula
	TextField formulaTxt = new TextField();
	// recibe el valor introducido por el usuario
	TextField valorTxt = new TextField();
	// contiene la posicion de la linea
	TextField posTxt = new TextField();	
	// contiene el texto de la linea
	TextField textoLinea = new TextField();
	
	/*  Temporal contiene la escala actual 	 */
	Escalas escalaActual = new Escalas();
	/*  Temporal contiene la version de la escala actual  */
	Escalas versionEscalaActual = null;
	/*  Temporal contiene la linea actual  */
	Escalas lineaEscalaActual = null;
	/*  Temporal contiene la version de la linea actual  */
	Escalas VersionLineaEscalaActual = null;
	
	String escalaBorrar="";
	String lineaBorrar="";
	// lista de tipos que existen
	String[] tipos = { "Fecha" , "Recepción de texto" , "Booleano" , "Formula" ,
			"Selección (varios de una lista)" , "Selección (uno de una lista)" ,
			"Recepción de un entero" , "Recepción de un decimal" ,
			"Texto informativo" , "Busqueda en una tabla." , "Grafico" };
	// lista de valores por defecto que existen
	// valor por defecto es cadena="" numericos=0 fecha=fecha actual, booleano=true
	String[] defecto = {"Nulo y cadena nula" , "Valores por defecto" , "Requiere un valor" , "False"};
	int numEscalas=0;
	
	/*  temporal contiene las versiones de las lineas de cada verion de la escala */
	ArrayList<VersionLineaEntradaEscala> misLineas = new ArrayList<VersionLineaEntradaEscala>();
	
	// lista de tablas y listas que existen.
	ArrayList<String> listaTablas = new ArrayList<String>();
	
	// creamos un objeto para validar datos
	validacionGeneral validacion = new validacionGeneral();
	
	// carga en el combobox una lista de las tablas
	public void cargarListasTablas(){
		cbReferencia.removeAllItems();
		for(Tabla t: miBBDD.getMisTablas()){
			// comprovar si es activa
			cbReferencia.addItem(t.getNombre());
		}
	}
	
	// carga en el combobox una lista de listas
	public void cargarListas(){
		cbReferencia.removeAllItems();
		for(Lista l: miBBDD.getMisListas()){
			// comprovar si es activa
			cbReferencia.addItem(l.getNombre());
		}
	}
	
	public CrearNueveEscala(AbsoluteLayout tabNuevo){
		
		// iniciar los combobox (el de tipo)
		for(String s: tipos){
			cbTipo.addItem(s);
		}

		// iniciar el combobox de listas y tablas
		for(String s: defecto){
			cbNulo.addItem(s);
		}
		
		iniciarLayoutNuevaEscala(tabNuevo);
		
		// tabla que contiene las lineas de la tabla__________________________________________________________________________________________
		// se a iniciado globalmente
		tabNuevo.addComponent(tablaLinea=new Table(), "left: 10px; top: 675px;");
		iniciarTablaLinea();
		// capturar cuando se pulse el boton añadir linea
		anyadirLineas.addClickListener(e -> { queHacerPulsadoanyadirLineas(tablaLinea); });
		
		// caputa la seleccion de un elemento de la tablaLinea
		tablaLinea.addItemClickListener(e -> {
			
			int filaSelecionada=(int)e.getItemId();
			
			// obtengo el id de la linea a borrar
			if(filaSelecionada>0) {
				lineaBorrar=(String)tablaLinea.getItem(filaSelecionada).getItemProperty("id:").getValue();
			}

		});
		
		// captura lo que se haga al pulsar el boton borrar linea
		bBorrar.addClickListener( e -> { queHacerPulsadoBorrarLinea(tablaLinea); });
		
		bNuevo.addClickListener(e -> { queHacerPulsadoNuevaLinea(tablaLinea); });
		
		// obtiene registro seleccionado de linea de tabla
		 tablaLinea.addValueChangeListener(new ValueChangeListener(){
			 	@Override
		        public void valueChange(ValueChangeEvent event){ }

		    });

		// fin tabla que contiene las lineas de la tabla ____________________________________________________________________________________
		
		// inicio de la gestion de tabla escalas____________________________________________________________________________________________
		// tabla que contiene la lista de las escalas

		tabNuevo.addComponent(tablaEscalas=new Table(), "left: 10px; top: 130px;");
		iniciarTablaEscalas();
		// iniciar el grid de las escalas. cargar el contenido en la tabla escalas
		// ___ miBBDD.leerListas();	// leo las listas que hay 
		// ___ miBBDD.leerTablas();	// leo las tablas que hay
		miBBDD.leerEscalas();	// leo las escalas que hay
		numEscalas=0;
		for(Escalas e: miBBDD.getMisEscalas()){ if(numEscalas<e.getIdEscala()) numEscalas=e.getidEscala(); }
		

		// captura lo que hace al pulsar el boton terninar la creacion de la escala
		// es decir se ha terminado de introducir lineas
		terminarLineas.addClickListener(e -> { queHacerPulsadoTerminar(tablaLinea,2); });
		// se guarda con esActivo_Escala como 1 indicando que no se ha terminado
		guardarSinTerminar.addClickListener(e -> { queHacerPulsadoTerminar(tablaLinea,1); });
		// guardarSinTerminar.addClickListener(e -> { queHacerPulsadoAnyadirSinTerminar(tablaLinea); });
		// captura lo que se haga al pulsar el boton añadir escala
		anyadirEscala.addClickListener( e -> { queHacerPulsadoAnyadirEscala(); });
		
		// captura lo que se haga al pulsar el boton añadir escala
		reiniciarNueveEscala.addClickListener( e -> { queHacerPulsadoReiniciar(); });
		
		// caputa la seleccion de un elemento de la tabla escalas
		tablaEscalas.addItemClickListener(e -> {
			
			int filaSelecionada=(int)e.getItemId();
			
			// obtengo el nombre a borrar
			if(filaSelecionada>0)escalaBorrar=(String)tablaEscalas.getItem(filaSelecionada ).getItemProperty("Nombre:").getValue();
			errorLabelAdminNuevo.setCaption("Error: no especificado el elemento a borrar."+escalaBorrar);

		});
		
		// captura lo que se haga al pulsar el boton borrar escala
		// borrarEscala.addClickListener( e -> { queHacerPulsadoBorrarEscala(tablaEscalas); });
		
		// obtiene registro seleccionado
		tablaEscalas.addValueChangeListener( new ValueChangeListener(){
			 @Override
		     public void valueChange(ValueChangeEvent event){
			 	// obtiene el elemento de la lista selecionado
			 	// errorLabelAdminNuevo.setCaption(tabla.getValue().toString());		               
		     }
		 });
		
		// se ejecuta al selecionar un elemento del combobox tipo
		cbTipo.addValueChangeListener(event -> { seleccionadoNuevoTipo(); });
        // fin-EXAMPLE: component.select.combobox.basic

		// fin de la gestion de tabla _______________________________________________________________________________________________________

		// Actualizamos la lista de esclas
		cargarEscalaExterior();
		anyadirControles(tabNuevo);
		// añade elementos al tab
	}
	
	private void queHacerPulsadoReiniciar(){
		
		escalaActual=new Escalas();
		miBBDD.leerEscalas();	// leo las escalas que hay (reinicio)
		
		// borra registros
		tablaEscalas.removeAllItems();
		
		reiniciar();
		
		// inicializa el contenido de los controles por si han sido usados
		inicializarControlesLinea();
		// se actualiza con los datos de la base de datos.
		cargarEscalaExterior();
		// reiniciar nombre caja
		nonEscNuevaText.setValue("");
		nonEscNuevaText.setVisible(true);
		nonEscNuevaText.setEnabled(true);
		
	}

	private void reiniciar(){
		
		// borra grid.
		// hace invisible el boton añadir escala y borrar escala
		anyadirEscala.setVisible(true);
		// hace invisibles controles.
		controlesVisibles(false);
		// hace visible controles.
		//chbConfiguracion.setVisible(false);
		formulaLbl.setVisible(false);
		formulaTxt.setVisible(false);
		// hace invisible los controles de referencia
		cbReferencia.setVisible(false);
		LabelTextoLinea.setVisible(false);
		textoLinea.setVisible(false);
		
	}

	
	// borra la linea borrada en la Table en memoria
	private void borrarEnMemoria(String id){
		
		int idTmp=Integer.parseInt(id);
		
		// busca el registro a borrar
		for(int i=0;i<misLineas.size();i++){
			// si lo encuentra lo borra
			if(misLineas.get(i).getIdLinea()==idTmp){ 
				misLineas.remove(i); 
				break; //sale porque solo se borra uno
			}
		}
		
	}
	
	private void queHacerPulsadoNuevaLinea(Table tablaLinea){
		// valorDefectoControles();
		// inicializa el contenido de los controles por si han sido usados
		inicializarControlesLinea();
		// dameIdMax()+;
		
	}
	
	// pulsado el boton borrar
	private void queHacerPulsadoBorrarLinea(Table tablaLinea){

		if(lineaBorrar.length()>0){
			// se borra del arraylist
			borrarEnMemoria(lineaBorrar);
	
			// actualiza la tabla con las escalas en memoria
			cargarLinea(tablaLinea);
			//chbConfiguracion.setVisible(false);
		}
		lineaBorrar="";
		
	}
	
	private void inicializarControlesLinea(){

		// checksbox nulo
		cbNulo.setValue(null);
		// checksbox visible
		chbVisible.setValue(false);
		// combo box
		chbEntrada.setValue(false);
		// combo box
		cbTipo.setValue(null);

		// boton borrar lines
		//ValorLbl.setVisible(estado);
		nombreLineaTxt.setValue("");
		valorTxt.setValue("");
		posTxt.setValue(idLblLinea.getCaption().toString());
		
		// label y caja texto de nombre de la escala
		//nomEscNuevaLabel.setVisible(!estado);
		//nonEscNuevaText.setValue("");
		nonEscNuevaText.setEnabled(false);
		// controles temporales
		//LabelTextoLinea.setCaption("");
		textoLinea.setValue("");		
		
		formulaTxt.setValue("");
		// hace invisible los controles de referencia
		cbReferencia.setValue(null);
	}
	
	// esta funcion borra el contenido de las cajas eliminando posibles
	// contenidos anteriores
	private void vaciarCajasParaTipo(){
		
		// LabelTextoLinea.setVisible(false);
		textoLinea.setValue("");
		// hace invisible los controles de formula
		formulaTxt.setValue("");
		// hace invisible los controles de referencia
		//cbReferencia.setVisible(false);
		
	}
	
	// retorna la posicion del tipo selecionado
	private int queTipoEs(){
		
		int tipo=1;
		
		if(cbTipo.isEmpty()) return 0;
		// recorre el array
		for(String s: tipos){
			// si lo encuentra retorna la posicion
			if(cbTipo.getValue().toString().equals(s)) return tipo;
			// si no lo encuentra suma uno y busca en la siguiente posicion
			tipo++;
		}
		
		return -1;
		
	}
	
	// son los valores por defecto de los controles al seleccionar un tipo
	private void valorDefectoControles(){
		
		// solo en texto que vera el usuario.
		// hace visible sus controles
		LabelTextoLinea.setVisible(false);
		textoLinea.setVisible(false);
		// hace invisible los controles de formula
		formulaLbl.setVisible(false);
		formulaTxt.setVisible(false);
		// hace invisible los controles de referencia
		cbReferencia.setVisible(false);
		// vaciar contenido de cbreferencia.
		cbReferencia.removeAllItems();
		//chbConfiguracion.setVisible(false);
		// dato introducido por el usuario
		chbVisible.setEnabled(true);
		// por defecto el valor nulo es activo
		cbNulo.setEnabled(true);
		
	}
	
	// se ejecuta al ser seleccionado un elemento de un combobox
	private void seleccionadoNuevoTipo(){
		vaciarCajasParaTipo();
		// pone por defecto el estado de los controles
		valorDefectoControles();
		switch(queTipoEs()){
		case 0: // no se ha seleccionado nada
			// solo en texto que vera el usuario.
			// hace visible sus controles
			// no hace nada ya lo ha hecho antes en la funcion valordefectocontroles()
			break;
		case 1: // fecha. 2 campos en total
			// solo en texto que vera el usuario.
			// hace visible sus controles
			LabelTextoLinea.setCaption("Fecha: ");
			LabelTextoLinea.setVisible(true);
			textoLinea.setVisible(true);
			// si es fecha siempre es visible
			// si es fecha tiene que ser visible
			chbVisible.setValue(true);
			chbVisible.setEnabled(false);
			// dato introducido por el usuario
			break;
		case 2: // Recepción de texto. 2 campos en total
			// solo el texto de vera el usuario
			LabelTextoLinea.setCaption("Texto: ");
			LabelTextoLinea.setVisible(true);
			textoLinea.setVisible(true);
			// dato introducido por el usuario
			// destruyo el objeto viejo y creo uno de texto
			// si es texto tiene que ser visible
			chbVisible.setValue(true);
			chbVisible.setEnabled(false);
			break;
		case 3: // booleano. 2 campos en total
			// solo el texto de vera el usuario
			// hace visible sus controles
			LabelTextoLinea.setCaption("Mensaje: ");
			LabelTextoLinea.setVisible(true);
			textoLinea.setVisible(true);
			// hace visible
			//chbConfiguracion.setVisible(true);
			// dato introducido por el usuario
			// si es booleano tiene que ser visible
			chbVisible.setValue(true);
			chbVisible.setEnabled(false);
			break;
		case 4: // formula. 2 campos en total
			// solo la formula. solo referencia a celdas anteriores y 
			// que su estructura sea correcta
			// hace invisible los controles de formula
			formulaLbl.setVisible(true);
			formulaTxt.setVisible(true);
			// dato introducido por el usuario
			chbVisible.setEnabled(true);
			break;
		case 5: // seleccion. 2 campos en total
			// hace invisible los controles de referencia
			cbReferencia.setVisible(true);
			// pone el contenido (lista)
			cargarListas();
			// hace visible sus controles
			LabelTextoLinea.setCaption("Texto: ");
			LabelTextoLinea.setVisible(true);
			textoLinea.setVisible(true);
			// falta tipo ( ) lista o tabla
			// dato introducido por el usuario
			// si es seleccion tiene que ser visible
			chbVisible.setValue(true);
			chbVisible.setEnabled(false);
			break;
		case 6: // Selección (varios de una lista). 2 campos en total
			// hace invisible los controles de referencia
			cbReferencia.setVisible(true);
			// pone el contenido (lista)
			cargarListas();
			// hace visible sus controles
			LabelTextoLinea.setCaption("Texto: ");
			LabelTextoLinea.setVisible(true);
			textoLinea.setVisible(true);
			// falta tipo ( ) lista o tabla
			// dato introducido por el usuario
			// si es seleccion tiene que ser visible
			chbVisible.setValue(true);
			chbVisible.setEnabled(false);
			break;
		case 7: // entero. 2 campos en total
			// solo el texto de vera el usuario
			// solo en texto que vera el usuario.
			// hace visible sus controles
			LabelTextoLinea.setCaption("Entero: ");
			LabelTextoLinea.setVisible(true);
			textoLinea.setVisible(true);
			// dato introducido por el usuario
			// si es entero tiene que ser visible
			chbVisible.setValue(true);
			chbVisible.setEnabled(false);
			break;
		case 8: // Recepción de un decimal. 2 campos en total
			// solo en texto que vera el usuario.
			// hace visible sus controles
			LabelTextoLinea.setCaption("Decimal: ");
			LabelTextoLinea.setVisible(true);
			textoLinea.setVisible(true);
			// dato introducido por el usuario
			// si es decimal tiene que ser visible
			chbVisible.setValue(true);
			chbVisible.setEnabled(false);
			break;
		case 9: // Texto informativo, 2 campo en total
			// solo el texto que vera el usuario
			// hace visible sus controles
			LabelTextoLinea.setCaption("Texto: ");
			LabelTextoLinea.setVisible(true);
			textoLinea.setVisible(true);
			// quizas haga falta especificar longitud de texto.
			// si es texto informativo tiene que ser visible
			chbVisible.setValue(true);
			chbVisible.setEnabled(false);
			cbNulo.setValue(null);
			cbNulo.setEnabled(false);
			break;
		case 10: // Busqueda en una tabla/lista. 4 campos en total
			// solo el texto de vera el usuario
			// el nombre de la tabla o de la lista
			// un indicativo de si es tabla o lista
			// dato/os retornado/os
			// hace invisible los controles de referencia
			cbReferencia.setVisible(true);
			// pone el contenido (tabla)
			cargarListasTablas();
			// solo el texto que vera el usuario
			// hace visible sus controles
			LabelTextoLinea.setCaption("id linea: ");
			LabelTextoLinea.setVisible(true);
			textoLinea.setVisible(true);
			// falta tipo ( ) lista o tabla
			// dato introducido por el usuario
			break;
		case 11: // Grafico. 3 campos en toral
			// texto que vera el usuario
			// campos x de la grafica. siempre anterior a la linea actual.
			// campos y de la grafica. siempre anterior a la linea actual
			break;
		default:
			// contiene una opcion errornea
			break;
		}
		
	}
	
	// pone el tabla el contenido en memoria
	// pone las escalas en una lista
	public void cargarEscalaExterior(){

		ConexionBBDD miConexion = new ConexionBBDD();
		miConexion.leerEscalas();
		
		int contador=1;
		tablaEscalas.removeAllItems();
		for(Escalas e: miConexion.getMisEscalas()){
			if(e.getEsActiva()==2 || e.getEsActiva()==1){
				String nombreEscala = e.getNombre();
				//String fecha = e.getUltimafechaModificacion().toString();
				String autor=e.getCreadorDeLaVersion();
				tablaEscalas.addItem(new Object[] { nombreEscala,autor }, new Integer(contador++));
			}
		}
	}
	
	// este metodo se realiza cuando se pulsa el boton añadir una escala
	private void queHacerPulsadoAnyadirEscala(){
		
		miBBDD.leerListas();	// leo las listas que hay 
		miBBDD.leerTablas();	// leo las tablas que hay
		// si no se ha especificado un nombre da error y sale.
		if(nonEscNuevaText.getValue().length()==0){
			errorLabelAdminNuevo.setCaption("Error: no especificado el nombre de la escala a crear.");
		}else if(existeEscala()) { // se crea una escala
			errorLabelAdminNuevo.setCaption("Error: La escala ya existe.");
		}else {
			// comprobar que la escala no existe ????
			// como no existe sigue ...
			errorLabelAdminNuevo.setCaption("No hay mensajes de error");
			//Calendar calendario = GregorianCalendar.getInstance();
			// creo un id nuevo para esta escala. este metodo es valido porque las escalas no se borrar.
			// se activan y desactivan
			for(Escalas e: miBBDD.getMisEscalas()){ if(numEscalas<e.getIdEscala()) numEscalas=e.getidEscala(); }
			numEscalas++; 
			// se crea un objeto que es la escala
			// int idEscala, String nombre, String CreadorDeLaVersion, boolean esActiva, boolean estaLibre
			escalaActual = new Escalas(numEscalas,nonEscNuevaText.getValue().toString(),ClaseObjStaticas.idUsuarioActual,1,true);
			// añado la nueva escala en la lista de la memoria
			// int idEscala, String nombre, String CreadorDeLaVersion, boolean esActiva, boolean estaLibre
			miBBDD.anyadirEscala(new Escalas(numEscalas,nonEscNuevaText.getValue().toString(),ClaseObjStaticas.idUsuarioActual,1,true));
			// obtengo mis escalas y ls dejo en memoria
			// miBBDD.getMisEscalas();
			// elimino el contenido de la caja
			nonEscNuevaText.setEnabled(false);
			// pongo linea nueva. preparo la ediciones de lineas.
			idLblLinea.setCaption("1"); // en esta parte este label solo puede tener el valor 1
			posTxt.setValue("1");
			
			// carga la tabla con los datos en memoria arraylist misEscalas
			cargarEscalaExterior();
	
			// hace invisible el boton añadir escala y borrar escala
			anyadirEscala.setVisible(false);
			
			// controlas visibles o no segun se ha pulsado nueva escala
			controlesVisibles(true);
			//chbConfiguracion.setVisible(false);
			// inicializa el contenido de los controles por si han sido usados
			inicializarControlesLinea();
			// nonEscNuevaText.setValue("");
			nonEscNuevaText.setVisible(true);
			nonEscNuevaText.setEnabled(false);
			// borro el contenido de la tabla de lieas para que no muestre nada
			misLineas.clear();
			cargarLinea(tablaLinea);
		}
		 
	}
	
	// retorna falso si ya esiste esa escala (el mismo nombre)
	private boolean existeEscala(){
		boolean existe=false;
		
		for(Escalas e: miBBDD.getMisEscalas()){
			if(e.getNombre().equals(nonEscNuevaText.getValue().toString())){ return true; }
		}
		return existe;
	}
	
	private void controlesVisibles(boolean estado){
		
		idLbl.setVisible(estado);
		idLblLinea.setVisible(estado);
		nombreLineaLbl.setVisible(estado);
		posicionLabel.setVisible(estado);
		// checksbox nulo
		cbNulo.setVisible(estado);
		// checksbox visible
		chbVisible.setVisible(estado);
		// combo box
		chbEntrada.setVisible(estado);
		// combo box
		cbTipo.setVisible(estado);

		// boton nueva linea
		bNuevo.setVisible(estado);
		bBorrar.setVisible(estado);
		anyadirLineas.setVisible(estado);
		guardarSinTerminar.setVisible(estado);
		terminarLineas.setVisible(estado);
		// boton borrar lines
		ValorLbl.setVisible(estado);
		nombreLineaTxt.setVisible(estado);
		valorTxt.setVisible(estado);
		posTxt.setVisible(estado);
		
		// tabla de las lineas creadas hasta el momento
		tablaLinea.setVisible(estado);
		
		// label y caja texto de nombre de la escala
		nonEscNuevaText.setEnabled(!estado);
		// controles temporales
		LabelTextoLinea.setVisible(!estado);
		textoLinea.setVisible(!estado);
		
		//chbConfiguracion.setVisible(estado);
		formulaLbl.setVisible(!estado);
		formulaTxt.setVisible(!estado);
		// hace invisible los controles de referencia
		cbReferencia.setVisible(!estado);
		
	}
	
	// terminado la creacion de la escala
	// terminado la introduccion de las lineas
	private void queHacerPulsadoTerminar(Table tablaLineas, int estado){
		
		//..................................................
		// si no se han introducio lineas no hace nada......
		// .................................................
		
		// graba registros
		tablaEscalas.removeAllItems();
		tablaLineas.removeAllItems();
		// actualiza la tabla con los datos en memoria de las esclas
		cargarEscalaExterior();
		cargarLinea(tablaLineas);
		
		// modificar Escalas para que se a terminado y esta en uso.
		// estado=1 no terminada de crear, 2 creada y activa, 3 esta siendo usada
		escalaActual.setEsActiva(estado);
		// actualiza las escalas y las versiones con los datos en memoria de las esclas
		miBBDD.insertarEscala(escalaActual);
		// gravar lineas y sus versiones
		miBBDD.gravarLinea(misLineas);
		// iniciado nombre de la escala
		nonEscNuevaText.setValue("");
		nonEscNuevaText.setVisible(true);
		nonEscNuevaText.setEnabled(true);
		// borra grid.
		// hace invisible el boton añadir escala y borrar escala
		anyadirEscala.setVisible(true);
		// hace invisibles controles.
		controlesVisibles(false);
		// hace visible controles.
		//chbConfiguracion.setVisible(false);
		formulaLbl.setVisible(false);
		formulaTxt.setVisible(false);
		// hace invisible los controles de referencia
		cbReferencia.setVisible(false);
		LabelTextoLinea.setVisible(false);
		textoLinea.setVisible(false);
		// se reinicia valores
		escalaActual=new Escalas();
		
	}
	
	private void iniciarTablaEscalas(){
		// determino la altura de la lista de escalas
		tablaEscalas.setHeight("200");
		// cracion columna nombre y fecha
		tablaEscalas.addContainerProperty("Nombre:", String.class, null);
		//tablaEscalas.addContainerProperty("Fecha", String.class, null);
		tablaEscalas.addContainerProperty("Autor", String.class, null);
		// activamos la capacidad de seleccionar lineas (registros)
		tablaEscalas.setSelectable(true);
		// hacemos que cuando tenga mas de 2 registros aparezca la barra
		// de desplazamiento vertical, si tiene menos y no se llena la zona
		// no sale la barra de desplazamiento
		tablaEscalas.setPageLength(2);
		
		// pone los controles visible o no segun el estado inicial
		controlesVisibles(false);
		// formula
		formulaLbl.setVisible(false);
		// combobox de referencia a tabla o lista
		cbReferencia.setVisible(false);
		// recepcion de formula
		formulaTxt.setVisible(false);
		textoLinea.setVisible(false);
		
	}
	
	// se crea una linea nueva de una escala nueva. 
	@SuppressWarnings("unused")
	private boolean validarLinea(int idEscala,Table tablaLinea){
		
		boolean correcto=true;
		
		// obitene la version de la escala y la linea
		//long idVersionEscal=1;
		// crea el objeto version linea
		//long idVersionLinea=1,tmp;
		long tmp=0;
		int tmpInt=0;
		String tmpTxt="";
		
		// se crea una linea temporal para guardar los datos y procesarlos antes de 
		// ponerlos dentro del array
		VersionLineaEntradaEscala miLinea = new VersionLineaEntradaEscala();
		
		// si es un numero guarda la posicion
		if(!existePosLinea()){
			tmpTxt=posTxt.getValue().toString();
			tmpInt=Integer.parseInt(tmpTxt);
			miLinea.setPos(tmpInt);
		}else{
			errorLabelAdminNuevo.setCaption("Error: La posición especificada es incorrecta.");
			return false; 
		}
		// id escala
		miLinea.setId_escala(idEscala);
		// id linea
		// debe comprobar que el numero de linea no existe ya
		if(!existeIdLinea()) miLinea.setIdLinea(Integer.parseInt(idLblLinea.getCaption()));
		else {
			errorLabelAdminNuevo.setCaption("Error: El id de la linea ya existe.");
			return false;
		}
		// la linea es activa
		// poner el resto de versiones de la linea a false ????? esto da problemas acerlo aqui
		miLinea.setActivo(true);
		// añade texto
		miLinea.setTextoVisible(textoLinea.getValue());
		// puede ser nulo. tipo valor por defecto
		tmpInt=queNuloEs();
		// si es titulo no modificable no hay datos a introducir por lo que este campo
		// no tiene sentido.
		if(tmpInt>4) {
			errorLabelAdminNuevo.setCaption("Error: El tipo de valor por defecto elegido no es valido.");
			return false;
		}
		miLinea.setPuedeSerNulo(tmpInt);
		// visible
		miLinea.setLineaVisible(chbVisible.getValue());
		// tipo
		tmpInt=queTipoEs();
		miLinea.setTipo(tmpInt); // guardo tipo
		switch(tmpInt){
			case 0:
				errorLabelAdminNuevo.setCaption("Error: Debe elegir un tipo.");
				return false;
			case 4: // tipo formula sin formula
				int error=0;
				// operaciones
				if(formulaTxt.getValue().toString().length()==0){ return false; }
				miLinea.setOperaciones(formulaTxt.getValue().toString()); // guarda las operaciones
				if((error=miLinea.validarFormula())>0) { // valida la formula
					String texto="Error indeterminado.";
					if(error==1) texto="El ultimo caracter de la formula no es valido.";
					if(error==2) texto="El primer caracter de la formula no es valido.";
					if(error==3) texto="En la formula hay dos caracteres seguidos no validos +-*/^";
					if(error==4) texto="En las formulas despues de un punto debe haber un numero.";
					if(error==5) texto="En las formulas antes de un punto debe haber un numero.";
					if(error==6) texto="La formula contiene un caracter no valido";
					if(error==7) texto="La formula contiene dos caracteres seguidos no validos.";
					if(error==8) texto="En la formula hay un caracter no valido despues de la apertura de un parentesis.";
					if(error==9) texto="La formula hace referencia a una celda no valida.";
					if(error==10) texto="En la formula hay un numero de celda no valido.";
					if(error==11) texto="No especificada ninguna formula.";
					errorLabelAdminNuevo.setCaption("Error: "+texto);
					return false;
				}
				break;
			case 5: // listas
			case 6:
				if(queReferenciaEs()==0){
					errorLabelAdminNuevo.setCaption("Error: La referencia a la lista es incorrecta.");
					return false;
				}else{
					miLinea.setReferencia(cbReferencia.getValue().toString());
					miLinea.setTextoVisible(textoLinea.getValue().toString());;
				}
				break;
			case 10: // tablas
				switch(validarReferencia(miLinea.getPos())){
					case 0:
						tmpTxt=cbReferencia.getValue()+","+textoLinea.getValue().toString();
						miLinea.setReferencia(tmpTxt);
						break;
					case 1:
						errorLabelAdminNuevo.setCaption("Error: La referencia a la lista es incorrecta.");
						return false;
					case 2:
						errorLabelAdminNuevo.setCaption("Error: La referencia a la linea no es correcta. Debe especifica la id de la linea.");
						return false;
					case 3:
						errorLabelAdminNuevo.setCaption("Error: La posición de la linea debe ser inferior a la actual.");
						return false;
					}
				break;
		}
		// guarda nombre linea
		miLinea.setNombreLinea(nombreLineaTxt.getValue().toString());	
		// valida que la linea no tenga la posicion de linea ya cogida
		// Si es correcto todo borra las cajas y las inicializa.
		if((correcto=miLinea.esCorrecto())){
			// aumenta en uno el número de linea.
			// añade un registro. valida la linea incluida la formula
			misLineas.add(miLinea);	
			// pasa a la memoria la linea a los objetos escala que le corresponde
			momorizarLineas(miLinea);
			cargarLinea(tablaLinea);
		}
		
		return correcto;
	}
	
	// valida si los datos para referencia son correctos o no
	public int validarReferencia(int posLinea){
		int error=0, posicion=0;
		
		// comprueba que el contenido de la caja sea un entero
		try{
			// posicion es la linea a la que se hace referencia
			posicion=Integer.parseInt(textoLinea.getValue().toString());
		}catch(Exception e){
			textoLinea.setValue("");
		}
		if(queReferenciaEs()==0){ // si no se ha escogido ninguna tabla
			error= 1;
		// si no contine un numero
		}else if(textoLinea.getValue().length()<1){
			error= 2;
		}
		// comprobar que la posicion de linea especificada sea 
		// inferior a una posicion de linea actual y que sea de tipo numerico
		if(posicion>=posLinea){ return 3; }
		
		return error;
	}
	
	public void momorizarLineas(VersionLineaEntradaEscala miLinea){
		
		for(Escalas e: miBBDD.getMisEscalas()){
			VersionLineaEntradaEscala linea = new VersionLineaEntradaEscala(miLinea.getId_escala(), 
					miLinea.getIdLinea(),miLinea.getActivo(),miLinea.gettextoVisible(),
					miLinea.getPos(),miLinea.getTipo(),miLinea.getPuedeSerNulo(),miLinea.isLineaVisible(),
					miLinea.getOperaciones(),miLinea.getReferencia(),miLinea.getNombreLinea(),miLinea.isEsEntrada());
			// añade una linea. solo hay una linea no hay versiones de lineas y no existia antes
			e.getMisLineas().add(linea);
		}
		
	}

	// mira si la idlinea que tiene ya esiste
	public boolean existePosLinea(){
		
		String tmpTxt="";
		int tmpInt=0;
		
		// mos aseguramos de leer algo
		if(posTxt.getValue().length()<1) { return true; }
		// obtenemos el valor
		tmpTxt=posTxt.getValue();
		// lo pasamos a numerico
		tmpInt=Integer.parseInt(tmpTxt);
		// buscamos coincidencias
		for(VersionLineaEntradaEscala vlee: misLineas){
			if(vlee.getPos()==tmpInt) return true;
		}
		
		return false;
	}
	
	// mira si la idlinea que tiene ya esiste
	public boolean existeIdLinea(){
		
		String tmpTxt="";
		int tmpInt=0;
		
		tmpTxt=idLblLinea.getCaption();
		tmpInt=Integer.parseInt(tmpTxt);
		for(VersionLineaEntradaEscala vlee: misLineas){
			if(vlee.getIdLinea()==tmpInt) return true;
		}
		
		return false;
	}
	
	// pone linea en memoria
	// pone el tabla el contenido en memoria
	private void cargarLinea(Table tabla_linea){

		int contador=1;
		
		// borra el contenido anterior de la tabla de lineas
		tabla_linea.removeAllItems();
		// recorre todo el array list de lines en memoria
		for(VersionLineaEntradaEscala e: misLineas) {
			// id linea a la que pertenece
			String idLinea=String.valueOf(e.getIdLinea());
			// indica si la version se puede usar o no.
			String activo=String.valueOf(e.getActivo());
			// posicion dentro del formulario. no nulo. entero.
			String pos=Integer.toString(e.getPos());
			// tipo de dato que contendra este campo.
			String tipo=Integer.toString(e.getTipo());
			// nulo. indica si el campo puede ser nulo o no. booleano
			// si vale 1 admite nulo o ""
			// si vale 2 pone un valor por defecto int=0, double=0, fecha=fechaActual
			// si vale 3 no sale hasta que se ponga un valor ni nulo ni por defecto.
			String puedeSerNulo=Integer.toString(e.getPuedeSerNulo());
			// visible. indica si es visible o no. no nulo. booleano
			String lineaVisible=String.valueOf(e.getLineaVisible());
			// Operacion a realizar. puede ser nulo. String.
			// Para hacer las media aritmeticas si un valor es 0
			// no cuenta como elemento para la media. Ejemplo
			// 0, 4, 6, 0, 10 la media es 4
			// null, 4, 6, null, 11 la media es 7 (los null no cuentan).
			// En operaciones hay referencias a valores contenidos el lineas pero
			// también números
			String operaciones=e.getOperaciones();
			/*
			 * 
			// Valor. Este es el contenido introducido por el usuario.
			// puede ser nulo si el campo correspondiente asi lo indica. String.
			// String contenido=e.getContenido();
			 * 
			 */
			// referencia a donde se encuentran los datos
			// sea tabla o lista
			String referencia=e.getReferencia();
			// nombre de la linea
			String nombre=e.getNombreLinea();

			// esta parte esta mal hecha. los campos en tabla_linea no son los correctos. ademas hay que hacer que linea y
			// el array list en memoria sean iguales. en el objeto (clase) linea falta el nombre de la linea
			// que se pide pero no se guarda. referencia y formula que no salgan
			// en el mismo objeto falta guardar los objetos de cada tipo.
			tabla_linea.addItem(new Object[] { idLinea, nombre,
					pos, puedeSerNulo, lineaVisible, activo, tipo, referencia, operaciones }, new Integer(contador++));			
		}
		// vaciar las cajas para reiniciar la introduccion de datos
		// aumenta en uno el numero de linea
		int num=dameIdMax()+1; // Integer.parseInt(tmpCad);
		String tmpNum=""+num;
		idLblLinea.setCaption(tmpNum);
		// por defecto pone la misma posicion de la escala que el id de la linea
		posTxt.setValue(tmpNum);
		// borra el nombre
		//nombreLineaTxt.setValue("");
		// pone el valor por defecto a 0
		cbNulo.setValue(null);
		// pone es visible a false
		chbVisible.setVisible(true);
		// checksbox entrada salida
		chbEntrada.setValue(false);
		// pone tipo por defecto a 0
		cbTipo.setValue(null);
	}
	
	// eset metodo retorna el id mas alto de los usados
	private int dameIdMax(){
		int max=0,tmp=0;
		
		for(VersionLineaEntradaEscala le: misLineas){
			if((tmp=le.getIdLinea())>max) max=tmp;
		}
		
		return max;
		
	}
	
	// obtiene pa posicion de la referencia seleccionada
	private int queReferenciaEs(){
		
		int tipo=1;
		
		if(cbReferencia.isEmpty()) return 0;
		for(String s: listaTablas){
			if(cbReferencia.getValue().toString().equals(s)) return tipo;
			tipo++;
		}
		
		return -1;
		
	}
	
	// obtiene la posicion de si es nulo o no
	private int queNuloEs(){
		
		int tipo=1;
		
		// si no hay selecionado nada retorna cero
		if(cbNulo.isEmpty()) return 0;
		for(String s: defecto){
			if(cbNulo.getValue().toString().equals(s)) return tipo;
			tipo++;
		}
		
		return -1;
		
	}
	
	// boton añadir linea a sido pulsado _____________________________________________________________________________
	private void queHacerPulsadoanyadirLineas(Table tablaLinea){
		
		//VersionLineaEntradaEscala linea=new VersionLineaEntradaEscala();
		
		// se adjudican valores. al hacerlo se valida informacion del campo.
		// se crea y guarda la linea
		// se valida la linea
		// se añade al grid.
		
		// obtiene la id de la escala.
		int idEscala=escalaActual.getidEscala();
		// valida el objeto (interiormente) que sea valido
		// lo pasa a la memoria
		if(validarLinea(idEscala, tablaLinea)){
			// son correctos los datos
			// actualizar numero de linea.
			// inicia los controles otra vez
			inicializarControlesLinea();
		}
		
	}
	
	private void iniciarLayoutNuevaEscala(AbsoluteLayout tabNuevo){
		
		tabNuevo.setWidth(anchoPeque);
		tabNuevo.setHeight(altoPeque);
		tabNuevo.setCaption("Nueva escala");
		
		// label
		nomEscNuevaLabel.setCaption("Ponga el nombre de la nueva escala");
		tabNuevo.addComponent(nomEscNuevaLabel, "left: 10px; top: 50px;");
		tabNuevo.addComponent(nonEscNuevaText, "left: 280px; top: 20px;");
		errorLabelAdminNuevo.setCaption("Mensajes de error");
		// se marca por defecto que sea visible la linea
		chbVisible.setValue(false);
	}
	
	private void iniciarTablaLinea(){
		// determino la altura de la lista de escalas
		tablaLinea.setHeight("200");
		tablaLinea.setWidth("800");
		// cracion columna nombre y fecha
		tablaLinea.addContainerProperty("id:", String.class, null);
		tablaLinea.addContainerProperty("Nombre", String.class, null);
		tablaLinea.addContainerProperty("Posición", String.class, null);
		tablaLinea.addContainerProperty("Nulo", String.class, null);
		tablaLinea.addContainerProperty("Visible", String.class, null);
		tablaLinea.addContainerProperty("Entrada/salida", String.class, null);
		tablaLinea.addContainerProperty("Tipo", String.class, null);
		tablaLinea.addContainerProperty("Referencia", String.class, null);
		tablaLinea.addContainerProperty("Formula", String.class, null);
		// activamos la capacidad de seleccionar lineas (registros)
		tablaLinea.setSelectable(true);
		// hacemos que cuando tenga mas de 10 registros aparezca la barra
		// de desplazamiento vertical, si tiene menos y no se llena la zona
		// no sale la barra de desplazamiento
		tablaLinea.setPageLength(10);
		
	}
	
	// añade los controles al layout
	private void anyadirControles(AbsoluteLayout tabNuevo){
		
		// se añade label
		tabNuevo.addComponent(errorLabelAdminNuevo, "left: 10px; top: 100px;");

		// boton de reiniciar la cracion de la escala y boton 
		// volver a login
		tabNuevo.addComponent(reiniciarNueveEscala ,"left: 500px; top: 20px;");
		//tabNuevo.addComponent(volverLogin ,"left: 570px; top: 20px;");

		// se añade botones
		tabNuevo.addComponent(anyadirEscala, "left: 500px; top: 130px;");
		//tabNuevo.addComponent(borrarEscala, "left: 650px; top: 130px;");
		// tabNuevo.addComponent(desactivarEscala, "left: 450px; top: 250px;");
		// tabNuevo.addComponent(activarEscala, "left: 650px; top: 250px;");

		// labels de lineas
		tabNuevo.addComponent(idLbl, "left: 10px; top: 350px;");
		tabNuevo.addComponent(idLblLinea ,"left: 80px; top: 374px;");
		
		// label y txt de nombre de linea
		tabNuevo.addComponent(nombreLineaLbl,"left: 150px; top: 350px;");
		tabNuevo.addComponent(nombreLineaTxt,"left: 280px; top: 340px;");
		
		// label y txt de posicion
		tabNuevo.addComponent(posicionLabel,"left: 10px; top: 397px;");
		tabNuevo.addComponent(posTxt,"left: 170px; top: 395px;");
		posTxt.setWidth("60px");
		posTxt.setHeight("35px");
		
		// checksbox nulo
		tabNuevo.addComponent(cbNulo,"left: 10px; top: 460px;");//
		cbNulo.setWidth("200px");
		
		// checksbox visible
		tabNuevo.addComponent(chbVisible,"left: 800px; top: 350px;");
		
		// combo box
		tabNuevo.addComponent(chbEntrada,"left: 275px; top: 400px;");
		
		// combo box
		// tabNuevo.addComponent(tipoLbl,"left: 10px; top: 450px;");
		cbTipo.setWidth("250");
		tabNuevo.addComponent(cbTipo,"left: 500px; top: 340px;");
		tabNuevo.addComponent(cbReferencia,"left: 280px; top: 460px;");
		
		// boton borrar lines
		tabNuevo.addComponent(formulaLbl, "left: 470px; top: 465px;");
		tabNuevo.addComponent(formulaTxt,"left: 545px; top: 458px;");
		formulaTxt.setWidth("332px");
		formulaTxt.setHeight("40px");
		// Label ValorLbl = new Label();

		// boton nueva linea
		tabNuevo.addComponent(bNuevo,"left: 500px; top: 130px;");
		tabNuevo.addComponent(bBorrar,"left: 650px; top: 130px;");
		tabNuevo.addComponent(anyadirLineas,"left: 500px; top: 250px;");
		tabNuevo.addComponent(guardarSinTerminar, "left: 525px; top: 190px;");
		tabNuevo.addComponent(terminarLineas,"left: 650px; top: 250px;");
		
		// label y texto de texto de linea
		tabNuevo.addComponent(LabelTextoLinea,"left: 10px; top: 552px;");
		tabNuevo.addComponent(textoLinea,"left: 100px; top: 520px;");
		textoLinea.setWidth("700px");
		textoLinea.setHeight("40px");
		
		// checkbox
		//tabNuevo.addComponent(chbConfiguracion,"left: 10px; top: 582px;");

	}
}