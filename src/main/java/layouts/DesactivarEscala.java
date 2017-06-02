package layouts;

//import java.util.ArrayList;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
//import com.vaadin.ui.TextField;

import BBDD.ConexionBBDD;
import clases.Escalas;
//import myObjects.ClaseObjStaticas;

public class DesactivarEscala {
	
	private static final String anchoPeque="1000"; 
	private static final String altoPeque="1000";
	public static Table tablaCrear;
	
	Button bBorrar= new Button("Desactivar");
	//Label nomEscDesacLabel = new Label();
	Label errorLabelDesacNuevo = new Label();
	Label LabelTextoLinea = new Label();
	//TextField nonEscDesacText=new TextField();
	
	Label errorLabelAdminNuevo = new Label();
	
	String escalaDesactivar="";
	
	// se inicia la conexion a la bbDD esta bacia no contine nada
	ConexionBBDD miBBDD  = new ConexionBBDD();
	
	public DesactivarEscala(AbsoluteLayout DesacEscala){
		
		DesacEscala.setWidth(anchoPeque);
		DesacEscala.setHeight(altoPeque);
		DesacEscala.setCaption("Desactivar Escala");
		
		miBBDD.leerEscalas(); // leo las escalas que hay
		
		DesacEscala.addComponent(tablaCrear=new Table(), "left: 10px; top: 50px;");
		// configuramos la table
		iniciarTablaEscalas();
		// iniciamos el contenido de table
		cargarEscalaExterior();
		
		// caputa la seleccion de un elemento de la tabla
		tablaCrear.addItemClickListener(e -> {
			
			int filaSelecionada=(int)e.getItemId();
			
			// obtengo el nombre a borrar
			if(filaSelecionada>0) escalaDesactivar=(String)tablaCrear.getItem(filaSelecionada ).getItemProperty("Nombre:").getValue();
			errorLabelAdminNuevo.setCaption("Error: no especificado el elemento a borrar."+escalaDesactivar);

		});
		
		// captura lo que se haga al pulsar el boton borrar linea
		bBorrar.addClickListener( e -> { queHacerPulsadoDesactivarEscala(); });
		
		// caputa de seleccion de tab
		
		
		anyadirControles(DesacEscala);
		
	}
	
	// añade los controles al layout
	private void anyadirControles(AbsoluteLayout tabNuevo){
		
		// se añade label
		tabNuevo.addComponent(errorLabelAdminNuevo, "left: 10px; top: 30px;");
		// label
		errorLabelAdminNuevo.setCaption("Mensajes de error");

		// boton nueva linea
		tabNuevo.addComponent(bBorrar,"left: 500px; top: 130px;");

	}
	
	// pone el tabla el contenido en memoria
	// pone las escalas en una alista
	public void cargarEscalaExterior(){

		ConexionBBDD miBaseDatos = new ConexionBBDD();
		miBaseDatos.leerEscalas();

		int contador=1;
		tablaCrear.removeAllItems();
		for(Escalas e: miBaseDatos.getMisEscalas()){
			if(e.getEsActiva()==2){
				String nombreEscala = e.getNombre();
				String autor=e.getCreadorDeLaVersion();
				tablaCrear.addItem(new Object[] { nombreEscala,autor }, new Integer(contador++));
			}
		}
	}
	
//	// pone el tabla el contenido en memoria
//	// pone las escalas en una alista
//	public void cargarEscala(){
//
//		int contador=1;
//		tablaCrear.removeAllItems();
//		for(Escalas e: miBBDD.getMisEscalas()){
//			if(e.getEsActiva()==2){
//				String nombreEscala = e.getNombre();
//				String autor=e.getCreadorDeLaVersion();
//				tablaCrear.addItem(new Object[] { nombreEscala,autor }, new Integer(contador++));
//			}
//		}
//	}

	private void queHacerPulsadoDesactivarEscala(){
		
		int borrar=-1;

		//recorre todos los datos de memoria y busca cual borrar
		for(int i=0;i<miBBDD.getMisEscalas().size();i++){
			if(miBBDD.getMisEscalas().get(i).getNombre().equals(escalaDesactivar)){	borrar=i; }
		}
		// si ha encontrado el elemento a borrar ...
		if(borrar>-1) {
			// desactiva la escala
			miBBDD.getMisEscalas().get(borrar).setEsActiva(3);
			// lo grava en la base de datos
			miBBDD.insertEscala(miBBDD.getMisEscalas().get(borrar));
			// reinicia el indicador de elemento a borrar
			borrar=-1;
		}

		// actualiza la tabla con los datos en memoria
		cargarEscalaExterior();
		escalaDesactivar="";
		
	}
	
	private void iniciarTablaEscalas(){
		// determino la altura de la lista de escalas
		tablaCrear.setHeight("200");
		// cracion columna nombre y fecha
		tablaCrear.addContainerProperty("Nombre:", String.class, null);
		//tablaCrear.addContainerProperty("Fecha", String.class, null);
		tablaCrear.addContainerProperty("Autor", String.class, null);
		// activamos la capacidad de seleccionar lineas (registros)
		tablaCrear.setSelectable(true);
		// hacemos que cuando tenga mas de 2 registros aparezca la barra
		// de desplazamiento vertical, si tiene menos y no se llena la zona
		// no sale la barra de desplazamiento
		tablaCrear.setPageLength(2);

	}

}