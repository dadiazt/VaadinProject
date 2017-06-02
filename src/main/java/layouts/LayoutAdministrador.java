package layouts;

import java.io.Serializable;
// import java.sql.Date;
import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.GregorianCalendar;

//import com.vaadin.server.ThemeResource;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Component;
//import com.vaadin.ui.Button;
//import com.vaadin.ui.CheckBox;
//import com.vaadin.ui.ComboBox;
//import com.vaadin.ui.Component;
//import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
//import com.vaadin.ui.ListSelect;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import BBDD.ConexionBBDD;
import clases.Escalas;
import myObjects.ClaseObjStaticas;
//import com.vaadin.ui.TextField;
//import com.vaadin.ui.VerticalLayout;
//import com.vaadin.data.Item;
//import com.vaadin.data.Property.ValueChangeEvent;
//import com.vaadin.data.Property.ValueChangeListener;

//import clases.Escalas;
//import clases.VersionLineaEntradaEscala;
//import validacion.validacionGeneral;

/*
  	// tab 1 gestion de escalas 
	// comprobar si el administrador actual tiene escalas sin terminar
		// gestionar la escala sin terminar
	// especificar escala
		// especificar version
			// especificar linea
				// especificar version linea
					// graficos
						// guardar escala/version/lineas/versionesLineas
	// tab 2 gestion de tablas 
	// crear tabla
	// tab 3 gestion de listas 
	// crear lista
	// tab 4 gestion de administradores 
	// crear administrador
	// tab 4 gestion de ver historial 
	// ver historial???
	
	// lista de escalas
	// crear tabla
 */
 
public class LayoutAdministrador implements Serializable {
	
	private static final String anchoGrande="1100";
	private static final String altoGrande="2000";//1500 
	private static final String anchoPeque="1000"; 
	private static final String altoPeque="1000";
	
	int fila=0;
	String escalaBorrar="";
	
	// lista de tablas y listas que existen.
	ArrayList<String> listaTablas = new ArrayList<String>();
	
	// tab general
	TabSheet tabsheet = new TabSheet();
	// de administrador
	TabSheet tabSheetAdmin = new TabSheet();
//	// creamos un objeto para validar datos
//	validacionGeneral validacion = new validacionGeneral();
	
	// creamos el controlador para listar las escalas
	//private Component view;

	Label nomAdmin2 = new Label();
	Label nomAdmin3 = new Label();
	Label nomAdmin4 = new Label();
	Label nomAdmin5 = new Label();
	Label nomAdmin6 = new Label();
	Label nomAdmin7 = new Label();
	
//	// parte de administrador escalas
//	// Labels
	Label nomAdmin1 = new Label();
	
	Label errorLabelAdminNuevoEdit = new Label();
	Label LabelTextoLineaEdit = new Label();
		
	// tabla de las lineas
	Table tablaLinea;
	
	// constructor
	public LayoutAdministrador(AbsoluteLayout layout){
		
		
		// lo vacío  completamente
		layout.removeAllComponents();
		//cabecera info /cerrar sesion
		VerticalLayout vl=new VerticalLayout();
		VerticalLayout layInfo=new infoUsuarioActual(vl);
		layout.addComponent(layInfo);// ,"left: 10px; top: 10px;"


		// ancho
		layout.setWidth(anchoGrande);
		// alto
		layout.setHeight(altoGrande);
		
		// añado un tabsheet.
		layout.addComponent(tabsheet,"left: 10px; top: 150px;");
		// Create the first tab
		AbsoluteLayout tabAdministrador = new AbsoluteLayout();
		crearLayoutAdministrador(tabAdministrador);
		// este tab se encarga de gestionar las tablas
		VerticalLayout tabTablas=new VerticalLayout();
		layoutTablas lTablas=new layoutTablas(tabTablas);
		// este tab se encarga de gestionar las listas
		VerticalLayout tabListas = new VerticalLayout();
		layoutListas lListas=new layoutListas(tabListas);
		// este tab se encarga de los graficos
		AbsoluteLayout tabGraficos = new AbsoluteLayout();
		crearLayoutGeneral(tabGraficos,"Graficos",nomAdmin4);
		// Este tab se encarga de gestionar los administradores
		//AbsoluteLayout tabGesUsuarios = new AbsoluteLayout();
		VerticalLayout tabGesAdmin=new VerticalLayout();
		layGesUsuariosApp tabGesUsuarios=new layGesUsuariosApp(tabGesAdmin);
		//crearLayoutGeneral(tabGesUsuarios,"Gestionar Usuarios",nomAdmin7);
		// Este tab se encarga del historial de las escalas
		AbsoluteLayout tabVerHistorial = new AbsoluteLayout();
		crearLayoutGeneral(tabVerHistorial,"Historial",nomAdmin5);
		// ver escalas ya hechas (historial)
		AbsoluteLayout tabVerEscalaHecha = new AbsoluteLayout();
		crearLayoutGeneral(tabVerEscalaHecha,"Escalas hechas",nomAdmin6);

		
		// falta otro mas no me acuerdo de cual es
		tabsheet.addTab(tabAdministrador);
		tabsheet.addTab(tabListas);
		tabsheet.addTab(tabTablas);
		tabsheet.addTab(tabGesAdmin);
		tabsheet.addTab(tabGraficos);
		tabsheet.addTab(tabVerHistorial);
		tabsheet.addTab(tabVerEscalaHecha);
		
	}
	
	// tab de admin segun opciones de listselect ------------------------------------------------------
	private void crearAdmin(AbsoluteLayout tabAdministrador){

		// dimensionamos el tab
		// ancho
		tabAdministrador.setWidth(anchoPeque);
		// alto
		tabAdministrador.setHeight(altoPeque);
		
		// añado un tabsheet.
		tabAdministrador.addComponent(tabSheetAdmin,"left: 100px; top: 20px;");
		// Create tab de crear nueva escala
		AbsoluteLayout tabCrearNuevaEscala = new AbsoluteLayout();
		CrearNueveEscala miEscala = new CrearNueveEscala(tabCrearNuevaEscala);
		//crearNuevaEscala(tabCrearNuevaEscala);
		// este tab se encarga de desactivar escalas
		AbsoluteLayout DesacEscala = new AbsoluteLayout();
		DesactivarEscala miDesacEscala = new DesactivarEscala(DesacEscala);
		//desacEscala(DesacEscala);
		// este tab se encarga de activar escalas
		AbsoluteLayout ActEscala = new AbsoluteLayout();
		ActivarEscala miActiEscala = new ActivarEscala(ActEscala);
		// este tab se encarga de modificar esclas (crea versiones)
		AbsoluteLayout modEscalaLayout = new AbsoluteLayout();
		modEscala(modEscalaLayout);
		
		// se añade el tab de administrador 
		tabSheetAdmin.addComponent(tabCrearNuevaEscala);
		tabSheetAdmin.addComponent(DesacEscala);
		tabSheetAdmin.addComponent(ActEscala);
		//tabSheetAdmin.addComponent(modEscalaLayout);

		tabSheetAdmin.addListener(new SelectedTabChangeListener() {
						
			public void selectedTabChange(SelectedTabChangeEvent event) {

				miActiEscala.cargarEscalaExterior();
				miDesacEscala.cargarEscalaExterior();
				miEscala.cargarEscalaExterior();
			}

		});



	}
	
	private void modEscala(AbsoluteLayout tabNuevo){
		
		iniciarLayoutMod(tabNuevo);
		
		// tabla de edicion
		Table tablaEdicion;
		
		tabNuevo.addComponent(tablaEdicion=new Table(), "left: 10px; top: 10px;");
		iniciarTablaEdicion(tablaEdicion);
		
	}
	
	private void iniciarTablaEdicion(Table tablaEdicion){
		
		// determino la altura de la lista de escalas
		tablaEdicion.setHeight("400");
		// cracion columna nombre y fecha
		tablaEdicion.addContainerProperty("Nombre:", String.class, null);
		tablaEdicion.addContainerProperty("Fecha", String.class, null);
		tablaEdicion.addContainerProperty("Autor", String.class, null);
		// activamos la capacidad de seleccionar lineas (registros)
		tablaEdicion.setSelectable(true);
		// hacemos que cuando tenga mas de 2 registros aparezca la barra
		// de desplazamiento vertical, si tiene menos y no se llena la zona
		// no sale la barra de desplazamiento
		tablaEdicion.setPageLength(2);
		
		// pone los controles visible o no segun el estado inicial
		//controlesVisibles(false);

	}
	
	private void iniciarLayoutMod(AbsoluteLayout tabMod){
		
		// ancho
		tabMod.setWidth(anchoPeque);
		// alto
		tabMod.setHeight(altoPeque);
		tabMod.setCaption("Editar escala");	
		
		// label
		errorLabelAdminNuevoEdit.setCaption("Mensajes de error");
		// se marca por defecto que sea visible la linea
		
	}
	
	private void actEscala(AbsoluteLayout tabCrearNuevaEscala){
		
		tabCrearNuevaEscala.setWidth(anchoPeque);
		tabCrearNuevaEscala.setHeight(altoPeque);
		tabCrearNuevaEscala.setCaption("Activar escala");
		
	}
	
	// fin tab de admin segun opciones de listselect ------------------------------------------------------

	// tab general ---------------------------------------------------------------------------------------
	private void crearLayoutAdministrador(AbsoluteLayout tabAdministrador){
		
		tabAdministrador.setWidth(anchoGrande);
		tabAdministrador.setHeight(altoGrande);
		// ponemos titulo al tab
		tabAdministrador.setCaption("Escalas");

		// labels
		nomAdmin1.setCaption("Administrador actual: "+ClaseObjStaticas.idUsuarioActual);
		
		// se crea tab de admin
		AbsoluteLayout tabAdmin = new AbsoluteLayout();
		crearAdmin(tabAdmin);
		// añadimos un nombre de administrador
		// añade labels
		tabAdministrador.addComponent(nomAdmin1, "left: 10px; top: 20px;");
		// añade el listselec
		tabAdministrador.addComponent(tabAdmin, "left: 30px; top: 10px;");
		
	}
	
	private void crearLayoutGeneral(AbsoluteLayout tabAdministrador,String titulo, Label label){
		tabAdministrador.setWidth(anchoGrande);
		tabAdministrador.setHeight(altoGrande);
		// pone nombre al tab actual
		tabAdministrador.setCaption(titulo);
		label.setCaption("Administrador actual: "+ClaseObjStaticas.idUsuarioActual);
		// añadimos un nombre de administrador
		tabAdministrador.addComponent(label, "left: 10px; top: 20px;");
	}
	// fin tab general ---------------------------------------------------------------------------------------
	
}
