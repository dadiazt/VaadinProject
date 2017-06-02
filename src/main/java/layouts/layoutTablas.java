package layouts;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;


import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import BBDD.ConexionBBDD;
import clases.Tabla;
import clases.Tablas;
import clases.TipoLista;

public class layoutTablas {
	
	// arraylist que contiene las lineas de las tablas
	ArrayList<Tablas> arrLineaTabla=new ArrayList<Tablas>();
	// array list que contine la tabla
	ArrayList<Tabla> arrTablas=new ArrayList<Tabla>(); 
	int min=0;
	int max=0;
	int valor=0;
	int num=0;
	String id;
	Table table;
	boolean numCorrecte;
	Tablas lineasTablas;
	Tabla tablaActual;
	int fila=0;
	String campo;
    TextField textId =new TextField();
    TextField textMin =new TextField();
    TextField textMax =new TextField();
    TextField textValor =new TextField();
    Button anyadir= new Button("añadir");
    Button anyadirBD= new Button("añadir BD");
    Button eliminar= new Button("eliminar");
    Button editar= new Button("editar");
    Button anyadirEditar= new Button("Finalizar Edición");
   
    public layoutTablas( VerticalLayout layout) {

    	HorizontalLayout hl=new HorizontalLayout();
    	VerticalLayout vl=new VerticalLayout();
    	layout.setCaption("Tablas");
        textId.setCaption("Nombre Tabla:");
        
        textMin.setCaption("Mínimo:");
        
        textMax.setCaption("Máximo:"); 
        textValor.setCaption("Valor:");

        // lineas de las tablas
        lineasTablas= new Tablas();
        // tabla actual
        tablaActual=new Tabla();
        // table que contiene las lineas
        table = new Table();
        table.setSelectable(true);
        
        // pulsado el boton añadir
        anyadir.addClickListener( e -> {
        	num++;
        	 numCorrecte = false;
            try {
            	// obtengo el siguiente numero de linea
            	int idLinea = lineasTablas.getArrayTablas().size()+1; 
            	numCorrecte=lineasTablas.anyadirNumero(id=textId.getValue(),min= Integer.parseInt(textMin.getValue()), max= Integer.parseInt(textMax.getValue()),valor= Integer.parseInt(textValor.getValue()),idLinea);
			} catch (NumberFormatException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	if(numCorrecte){
        		table.setId(id);
        		textId.setVisible(false);
        		// una linea de la tabla
        		table.addItem(new Object[] {min, max, valor}, new Integer(num));
        		
        		numCorrecte=false;
        	}
        	// iniciar cajas
            textMin.setValue("");
            textMax.setValue(""); 
            textValor.setValue("");
        	
        });
        
        // añade la tabla a la base de datos.
        anyadirBD.addClickListener( e -> {
        	lineasTablas.Ordenar();
        	try {
				if((!lineasTablas.comprobarOrden())){
					layout.addComponent(new Label("Error Datos Mal Introducidos"));
				}else{
					ConexionBBDD conexion = new ConexionBBDD();
					// aqui se añade a la base de datos.
					tablaActual = new Tabla(id); // textId.getValue().toString());
					conexion.insertarTabla(tablaActual);
					// añadir tabla a la base de datos
					for(Tablas t: lineasTablas.getArrayTablas()){
						conexion.insertarLineasTabla(t);
					}
					// añadir lineas a la tabla lineas tablas
					layout.addComponent(new Label("Añadido a Base de Datos"));
					iniciarControles();
				}
				
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	
        });
        
        // boton editar
        editar.addClickListener( e -> {
        	table.setEditable(true);
        	int minFilaClick=(int)table.getItem(fila).getItemProperty("Mínimo").getValue();
        	for(int i=0;i<lineasTablas.getArrayTablas().size();i++){
        		if(lineasTablas.getArrayTablas().get(i).getMin()==(minFilaClick)){
        			lineasTablas.getArrayTablas().remove(i);
        			System.out.println(lineasTablas.getArrayTablas().toString());
        			break;
        		}
        	}
        	
        });
        
        // boton finalizar edicion. 
        anyadirEditar.addClickListener( e -> {
        	try {
        		System.out.println("--> "+lineasTablas.getArrayTablas().toString());
        		int minedit=(int)table.getItem(fila).getItemProperty("Mínimo").getValue();
        		
        		int maxedit=(int)table.getItem(fila).getItemProperty("Máximo").getValue();
        		int valedit=(int) table.getItem(fila).getItemProperty("Valor").getValue();
        		table.setEditable(false);        	
        		System.out.println("xxx  "+lineasTablas.getArrayTablas());
        		lineasTablas.getArrayTablas().add(new Tablas(id,minedit,maxedit,valedit,fila));
        		System.out.println("yyy "+lineasTablas.getArrayTablas());


        		
				if((!lineasTablas.comprobarOrden())){
					layout.addComponent(new Label("Error Datos Mal Introducidos"));
				}else{
					// se añade el nombre a la tabla tablas.
					// se añade las lineas a la tabla lineas tablas
					layout.addComponent(new Label("Añadido a Base de Datos"));
				}
				
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	
        });
        
        // el boton eliminar elimina una tabla
        eliminar.addClickListener( e -> {
        	//intentar añadir una fila editable
        	//System.out.println(lineasTablas.getArrayTablas().toString());
        	int minFilaClick=(int)table.getItem(fila).getItemProperty("Mínimo").getValue();
        	for(int i=0;i<lineasTablas.getArrayTablas().size();i++){
        		if(lineasTablas.getArrayTablas().get(i).getMin()==(minFilaClick)){
        			lineasTablas.getArrayTablas().remove(i);
        			//System.out.println(lineasTablas.getArrayTablas().toString());
        			break;
        		}
        	}
        	table.removeItem(fila);
        });
        
        table.addItemClickListener( e -> {
        	fila= (int) e.getItemId();
        	
        });
        
        // añade columnas a table para contener las lineas
        table.addContainerProperty("Mínimo",Integer.class,  null);
    	table.addContainerProperty("Máximo",Integer.class,  null);
    	table.addContainerProperty("Valor",Integer.class, null);
    	
    	hl.setSizeFull();
    	hl.setSpacing(true);
    	
        vl.addComponents(textId,textMin,textMax,textValor,anyadir,anyadirBD,eliminar,editar,anyadirEditar);
        hl.addComponents(vl,table);
        layout.addComponent(hl);
        
//        context.setContent(layout);
        
    }

  
    public void iniciarControles(){
    	
        textId.setVisible(true);
        textId.setValue("");
        textId.setEnabled(true);
        textMin.setVisible(true);
        textMin.setValue("");
        textMin.setEnabled(true);
        textMax.setVisible(true);
        textMax.setValue("");
        textMax.setEnabled(true);
        textValor.setVisible(true);
        textValor.setValue("");
        textValor.setEnabled(true);
        anyadir.setVisible(true);
        anyadirBD.setVisible(true);
        eliminar.setVisible(true);
        editar.setVisible(true);
        anyadirEditar.setVisible(true);
        table.removeAllItems();
        lineasTablas = new Tablas();
    	tablaActual=new Tabla();
    	min=max=valor=num=fila=0;
    	id="";
    	numCorrecte=false;
        
    }
}
