package layouts;

import java.util.ArrayList;

import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;

import BBDD.ConexionBBDD;
import clases.Escalas;

public class ActivarEscala {
	
	private static final String anchoPeque="1000"; 
	private static final String altoPeque="1000";
	
	Button bBorrar= new Button("Activar");
	//Label nomEscDesacLabel = new Label();
	Label errorLabelDesacNuevo = new Label();
	Label LabelTextoLinea = new Label();
	//TextField nonEscDesacText=new TextField();
	
	Label errorLabelAdminNuevo = new Label();
	
	String escalaDesactivar="";
	
	// se inicia la conexion a la bbDD esta bacia no contine nada
	ConexionBBDD miBBDD  = new ConexionBBDD();
	
	/*  Temporal contiene las escalas  */
	// ArrayList<Escalas> misEscalas=new ArrayList<Escalas>();
	Table tablaCrear;
	
	public ActivarEscala(AbsoluteLayout DesacEscala){
		
		DesacEscala.setWidth(anchoPeque);
		DesacEscala.setHeight(altoPeque);
		DesacEscala.setCaption("Activar Escala");
		
		miBBDD.leerEscalas(); // leo las escalas que hay
		
		DesacEscala.addComponent(tablaCrear=new Table(), "left: 10px; top: 50px;");
		iniciarTablaEscalas(tablaCrear);
		// iniciamos el contenido de table
		cargarEscalaExterior();
		
		// caputa la seleccion de un elemento de la tabla
		tablaCrear.addItemClickListener(e -> {
			
			int filaSelecionada=(int)e.getItemId();
			
			// obtengo el nombre a borrar
			if(filaSelecionada>0) {
				escalaDesactivar=(String)tablaCrear.getItem(filaSelecionada ).getItemProperty("Nombre:").getValue();
			}else{
				errorLabelAdminNuevo.setCaption("Error: no especificado el elemento a borrar."+escalaDesactivar);
			}

		});
		
		// captura lo que se haga al pulsar el boton borrar linea
		bBorrar.addClickListener( e -> { queHacerPulsadoDesactivarEscala(tablaCrear); });
		
		anyadirControles(DesacEscala);
		
	}
	
	// añade los controles al layout
	private void anyadirControles(AbsoluteLayout tabNuevo){
		
		// se añade label
		tabNuevo.addComponent(errorLabelAdminNuevo, "left: 10px; top: 30px;");
		// label
		//nomEscDesacLabel.setCaption("Ponga el nombre de la escala");
		//tabNuevo.addComponent(nomEscDesacLabel, "left: 10px; top: 50px;");
		//tabNuevo.addComponent(nonEscDesacText, "left: 230px; top: 20px;");
		errorLabelAdminNuevo.setCaption("Mensajes de error");

		// boton nueva linea
		tabNuevo.addComponent(bBorrar,"left: 450px; top: 130px;");


	}
	
	// pone el tabla el contenido en memoria
	// pone las escalas en una alista
	public void cargarEscalaExterior(){

		ConexionBBDD miBaseDatos = new ConexionBBDD();
		miBaseDatos.leerEscalas();

		int contador=1;
		tablaCrear.removeAllItems();
		for(Escalas e: miBaseDatos.getMisEscalas()){
			if(e.getEsActiva()==3){
				String nombreEscala = e.getNombre();
				//String fecha = e.getUltimafechaModificacion().toString();
				String autor=e.getCreadorDeLaVersion();
				tablaCrear.addItem(new Object[] { nombreEscala,autor }, new Integer(contador++));
			}
		}
	}
//	// pone el tabla el contenido en memoria
//	// pone las escalas en una lista
//	private void cargarEscala(Table tabla){
//
//		int contador=1;
//		tablaCrear.removeAllItems();
//		for(Escalas e: miBBDD.getMisEscalas()){
//			if(e.getEsActiva()==3){
//				String nombreEscala = e.getNombre();
//				//String fecha = e.getUltimafechaModificacion().toString();
//				String autor=e.getCreadorDeLaVersion();
//				tablaCrear.addItem(new Object[] { nombreEscala,autor }, new Integer(contador++));
//			}
//		}
//	}
//	
	private void queHacerPulsadoDesactivarEscala(Table tablaLinea){
		
		int borrar=-1;

		//recorre todos los datos de memoria y busca cual borrar
		for(int i=0;i<miBBDD.getMisEscalas().size();i++){
			if(miBBDD.getMisEscalas().get(i).getNombre().equals(escalaDesactivar)){	borrar=i; }
		}
		// si ha encontrado el elemento a borrar ...
		if(borrar>-1) {
			// hace que el elemento se active
			miBBDD.getMisEscalas().get(borrar).setEsActiva(2);
			// lo grava en la base de datos
			miBBDD.insertEscala(miBBDD.getMisEscalas().get(borrar));
			// reinicia el indicador de elemento a borrar
			borrar=-1;
		}

		// actualiza la tabla con los datos en memoria
		cargarEscalaExterior();
		escalaDesactivar="";
		
	}
	
	private void iniciarTablaEscalas(Table tabla){
		// determino la altura de la lista de escalas
		tabla.setHeight("200");
		// cracion columna nombre y fecha
		tabla.addContainerProperty("Nombre:", String.class, null);
		// tabla.addContainerProperty("Fecha", String.class, null);
		tabla.addContainerProperty("Autor", String.class, null);
		// activamos la capacidad de seleccionar lineas (registros)
		tabla.setSelectable(true);
		// hacemos que cuando tenga mas de 2 registros aparezca la barra
		// de desplazamiento vertical, si tiene menos y no se llena la zona
		// no sale la barra de desplazamiento
		tabla.setPageLength(2);
		
	}

}