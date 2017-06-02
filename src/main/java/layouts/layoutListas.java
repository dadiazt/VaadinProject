package layouts;

import java.util.ArrayList;

import com.vaadin.data.Validator;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.converter.Converter.ConversionException;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import BBDD.ConexionBBDD;
import clases.TipoLista;
import clases.LineaLista;



/**
 * 
 * @author ricale
 * 
 * COSAS QUE FALTAN
 * 
 * falta validacion de tipo en los textfield
 * falta validacion(?) de valor a retornar
 * ---> si hacemos esto metemos el INSERT en la bbdd
 * falta algorithmo de editar las lineas de una lista 
 * 
 *
 */


public class layoutListas {

	ArrayList<TipoLista> arrListas=new ArrayList<>(); // array de listas 
	LineaLista tLista;// linea de una lista
	TipoLista lista=new TipoLista(); // una lista

	int i=1,idLineaLista=1; // i->en nuevoItem()
	int newItemTListas=0;// valor de las filas de la tabla de listas
	//Buttons
	Button buttonNL,bttEdit,buttonAdd,bttCancelar,buttonGuardar,bttEditarNombre,bttConfirmarCambioNLista,bttCancelarCambioNombreLista;
	//tables 
	Table tablaListas,tablaItems;
	int filaTListas,vPosicion,vValor;
	// TF y Labels de Nueva lista o editar
	Label NombreLista,NuevoItem,Posicion,Valor,NuevoNomLista,infoIdLineaLista,lbInfoNombreLista;
    TextField tfNombreLista,tfNuevoItem,tfPosicion,tfValor,tfNuevoNomLista;
    //Valor de los TF de NL/Edit
    String vNombreLista,vNuevoItem,vNuevoNomLista,nombrelistaEdit;
    boolean modoEditar=false,boolvPos=false,boolvValor=false;
    HorizontalLayout hlayCambiarNombre;
   
	public layoutListas(VerticalLayout vl){
		vl.setCaption("Listas");
		vl.setSpacing(true);
		vl.setMargin(new MarginInfo(true,true,false,true));
		HorizontalLayout hlay=new HorizontalLayout(); 	hlay.setSpacing(true);// btt NL, Edit		
		HorizontalLayout hlay1=new HorizontalLayout();	hlay1.setSpacing(true);//Nombre lista		
		HorizontalLayout hlay2=new HorizontalLayout();	hlay2.setSpacing(true);// vista de NL Ed		
		HorizontalLayout hlay3=new HorizontalLayout();	hlay3.setSpacing(true);// btt addItem, guardarLista, Cancelar		
		hlayCambiarNombre=hLayCambiarNombre();	//  editar nombre lista 
		
		
		//inflar array listas
		//arrListas=inflarArrListas();
		arrListas=ConexionBBDD.cargarListas();
		//System.out.println(":: "+arrListas.get(1).comprobarOrden());
    	Label lb=new Label("Listas");
		
    	// INICIO tabla
        tablaListas = new Table();
        tablaListas.setSelectable(true);        
        tablaListas.addContainerProperty("Nombre listas", String.class, null);
        tablaListas.setPageLength(tablaListas.size());	
        tablaListas.addItemClickListener( e -> {
        	filaTListas= (int) e.getItemId();
        	//uso listaEdit para hacer el update 
        	nombrelistaEdit=(String)tablaListas.getItem(filaTListas).getItemProperty("Nombre listas").getValue();

        	
        });
    	for(int j=0;j<arrListas.size();j++){
    		tablaListas.addItem(new Object[]{arrListas.get(j).getNombreLista()},new Integer(j+1));

    	}
    	newItemTListas=arrListas.size()+1;
        // FIN tabla
        
        buttonNL = new Button("Nueva lista");
        buttonNL.addClickListener( e -> {
        	habilitarVistaNLista(1);      
        });
		
        bttEdit = new Button("Editar lista");
        bttEdit.addClickListener( e -> {
        	
        	//
        	
        	if(filaTListas>0){
        		System.out.println("----> "+tablaListas.getItem(filaTListas).getItemProperty("Nombre listas").getValue());
            	System.out.println(">>> "+arrListas.get(filaTListas-1));
        		lbInfoNombreLista.setVisible(false);
        		lista=arrListas.get(filaTListas-1);
            	habilitarVistaNLista(4);             	
            	cargarListaParaEditar(filaTListas-1);
        	}else{
        		lbInfoNombreLista.setVisible(true);
        	}
        	
        	
        
        });
        bttEditarNombre = new Button("Cambiar nombre");
        bttEditarNombre.addClickListener( e -> { 
        	
        	if(filaTListas>0){
            	hlayCambiarNombre.setVisible(true); 
        		lbInfoNombreLista.setVisible(false);
            	habilitarVistaNLista(5);      

        	}else{
        		lbInfoNombreLista.setVisible(true);
        	}
        	
        });
        
		
        //V2* vista que se muestra con btt nueva lista o editar
    	NombreLista=new Label("Nombre lista: ");     
    	tfNombreLista=new TextField();
    	tfNombreLista.addValueChangeListener(event ->
        vNombreLista=(String)event.getProperty().getValue());
    	hlay1.addComponents(NombreLista,tfNombreLista);
    	
    	infoIdLineaLista=new Label("idLinea: "+idLineaLista);	infoIdLineaLista.setVisible(false);

    	NuevoItem=new Label("Nuevo item: ");
    	tfNuevoItem=new TextField();		
    	tfNuevoItem.addValueChangeListener(event ->
        vNuevoItem=(String)event.getProperty().getValue());	
    	
    	
    	//+++ controlar que los campos sean correctos ,tfValor
    	Posicion=new Label("Posición en la lista: ");
    	tfPosicion=new TextField();	
    	tfPosicion.setConverter(Integer.class);
    	tfPosicion.setInputPrompt("1");
    	tfPosicion.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub
				String valor=(String)event.getProperty().getValue();
				System.out.println("--> "+valor);
				if(valor!=null){
					try {
			            vPosicion = (Integer) tfPosicion
			                    .getConvertedValue();
			           
			        } catch (ConversionException e) {
			            Notification.show(
			                    "Este valor NO es válido");
			        }
					boolvPos=true;

				}else{
					boolvPos=false;
					System.out.println("no hay dato valido");
				}
				
			}
		});   	
    	
    	
    	
    	Valor=new Label("Valor a retornar: ");
    	tfValor=new TextField();
    	tfValor.setConverter(Integer.class);
    	tfValor.setInputPrompt("0");

    	tfValor.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub
				String valor=(String)event.getProperty().getValue();
				if(valor!=null){
					try {
			            vValor = (Integer) tfValor
			                    .getConvertedValue();
			           
			        } catch (ConversionException e) {
			            Notification.show(
			                    "Este valor NO es válido");
			        }
					boolvValor=true;
				}else{
					boolvValor=true;
					System.out.println("no hay dato valido");
				}
				
				
			}
		}); 
    	
    	hlay2.addComponents(infoIdLineaLista,NuevoItem,tfNuevoItem,Posicion,tfPosicion,Valor,tfValor);
        
    	
    	//tabla de items
        tablaItems = new Table();
        tablaItems.addContainerProperty("Texto línea", String.class, null);
        tablaItems.addContainerProperty("Posicion de línea", Integer.class, null);
        tablaItems.addContainerProperty("Valor de retorno", Integer.class, null); 

        tablaItems.setPageLength(tablaItems.size());	
    	
    	//botones de nueva lista/ editar
        buttonAdd = new Button("Añadir item");
        buttonAdd.addClickListener( e -> {
        	NuevoItem();
        	System.out.println("## > "+lista.getArrTL().toString());

        });
	 	
        buttonGuardar = new Button("Guardar Lista");
        buttonGuardar.addClickListener( e -> {  
        	System.out.println("====> "+lista.toString());
        	//infoIdLineaLista=1;
        	
        		//ConexionBBDD.insertListas(); //insertamos en la BBDD
        		Guardar();
        	
        	 
        	//guardarEditado();
        });
        
        bttCancelar = new Button("Cancelar");
        bttCancelar.addClickListener( e -> {        	
    		habilitarVistaNLista(2);
    		//provisional
    		tablaItems.removeAllItems();
        });
        
    	//fin V2*
        hlay3.addComponents(buttonAdd,buttonGuardar,bttCancelar);

    	habilitarVistaNLista(2);      
//    	habilitarVistaNLista(6);      

        hlay.addComponents(buttonNL,bttEdit,bttEditarNombre);
        lbInfoNombreLista=new Label("Escoja una lista");	      lbInfoNombreLista.setVisible(false);

        vl.addComponents(lb,tablaListas,hlay,lbInfoNombreLista,hlay1,hlay2,hlay3,hlayCambiarNombre,tablaItems);
		
	}
	
	// cargamos los items que tiene la lista que ha sido seleccionada
	public void cargarListaParaEditar(int f){
		tablaItems.setCaption(arrListas.get(f).getNombreLista());
		for(int n=0;n<arrListas.get(f).getArrTL().size();n++){
			tablaItems.addItem(new Object[]{arrListas.get(f).getArrTL().get(n).getTextoLinea(),arrListas.get(f).getArrTL().get(n).getPos(),arrListas.get(f).getArrTL().get(n).getValor()},new Integer(n+1));
			//Valor de retorno Posicion de línea
		}
		
	}
	
	public void guardarEditado(){
		
		
	}
	
	public void habilitarVistaNLista(int n){
		/*
		 * 	Button buttonNL,bttEdit,buttonAdd,bttCancelar;
	//tables
	Table tablaListas,tablaItems;
	// TF y Labels de Nueva lista o editar
	Label NombreLista,NuevoItem,Posicion,Valor;
    TextField tfNombreLista,tfNuevoItem,tfPosicion,tfValor;
		 */
		
		//1- habilitar todo 2- deshabilitar la vista de Nueva linea ,3- deshabilitar Nombre de nueva lista
		//1- vista NUEVA LISTA
		switch (n){
		case 1:	
			lista=new TipoLista();
			tablaItems.setCaption("");
			buttonNL.setVisible(false);
			bttEdit.setVisible(false);
			bttEditarNombre.setVisible(false);
			buttonAdd.setVisible(true);
			bttCancelar.setVisible(true);
			buttonGuardar.setVisible(false);
			tablaItems.setVisible(true);
			NombreLista.setVisible(true);
			infoIdLineaLista.setVisible(true);
			NuevoItem.setVisible(true);
			Posicion.setVisible(true);
			Valor.setVisible(true);
			tfNombreLista.setVisible(true);
			tfNuevoItem.setVisible(true);
			tfPosicion.setVisible(true);
			tfValor.setVisible(true);
			lbInfoNombreLista.setVisible(false);
			idLineaLista=1;
			infoIdLineaLista.setValue("idLinea: "+idLineaLista);

			break;
			//cancelar o GuardarLista / al iniciar este layout 
		case 2:
			modoEditar=false;
			buttonNL.setVisible(true);
			bttEdit.setVisible(true);
			bttEditarNombre.setVisible(true);
			buttonAdd.setVisible(false);
			bttCancelar.setVisible(false);
			buttonGuardar.setVisible(false);
			tablaListas.setSelectable(true);
			tablaItems.setVisible(false);
			NombreLista.setVisible(false);
			NuevoItem.setVisible(false);
			Posicion.setVisible(false);
			Valor.setVisible(false);
			NuevoNomLista.setVisible(false);
			infoIdLineaLista.setVisible(false);
			tfNombreLista.setVisible(false);
			tfNuevoItem.setVisible(false);
			tfPosicion.setVisible(false);
			tfValor.setVisible(false);
			tfNuevoNomLista.setVisible(false);
			//clears
			tfNuevoItem.clear();
			tfPosicion.clear();
			tfValor.clear();
			tfNombreLista.clear();
			tfNuevoNomLista.clear();
			break;
		//añadir item/linea
		case 3:
			NombreLista.setVisible(false);
			tfNombreLista.setVisible(false);
			buttonGuardar.setVisible(true);
			tfNuevoItem.clear();
			tfPosicion.clear();
			tfValor.clear();
			break;
			
		//editar
		case 4:
			modoEditar=true;
			buttonNL.setVisible(false);
			bttEdit.setVisible(false);
			bttEditarNombre.setVisible(false);
			buttonAdd.setVisible(true);
			bttCancelar.setVisible(true);
			buttonGuardar.setVisible(false);
			bttConfirmarCambioNLista.setVisible(true);
			tablaListas.setSelectable(false);
			tablaItems.setVisible(true);
			NombreLista.setVisible(false);
			infoIdLineaLista.setVisible(false);
			NuevoItem.setVisible(true);
			Posicion.setVisible(true);
			Valor.setVisible(true);
			NuevoNomLista.setVisible(true);
			tfNombreLista.setVisible(false);
			tfNuevoItem.setVisible(true);
			tfPosicion.setVisible(true);
			tfValor.setVisible(true);
			tfNuevoNomLista.setVisible(true);
			break;
		// cambiar nombre lista	/NuevoNomLista,tfNuevoNomLista,bttConfirmarCambioNLista
		case 5:
			NuevoNomLista.setVisible(true);
			tfNuevoNomLista.setVisible(true);
			bttConfirmarCambioNLista.setVisible(true);
			buttonNL.setVisible(false);
			bttEdit.setVisible(false);
			bttEditarNombre.setVisible(false);
			tablaListas.setSelectable(false);
			break;
			
		//cancelar cambiar nombre lista	
		case 6:
			hlayCambiarNombre.setVisible(false);
			buttonNL.setVisible(true);
			bttEdit.setVisible(true);
			bttEditarNombre.setVisible(true);
			tfNuevoNomLista.clear();
			tablaListas.setSelectable(true);

			break;

		}		
		
	}
	
	// poner aqui  -> cargarListaParaEditar()

	
	/* - NO vistas */
	
	public void Guardar(){
		
		//si viene del modo editar  no hay que comprobar el nombre de la lista
    	if(comprobarValidez()==0){
    		arrListas.add(lista);
    		tablaItems.removeAllItems();    	
    		tablaListas.addItem(new Object[]{lista.getNombreLista()},new Integer(newItemTListas));
    		System.out.println("lista a bbdd: "+lista.toString());
    		ConexionBBDD.insertListas(lista);
    		lista=new TipoLista();
    		tablaItems.setCaption("");
			tfNombreLista.clear();
			tfNuevoItem.clear();
			tfPosicion.clear();
			tfValor.clear();
    		habilitarVistaNLista(2);
			newItemTListas++;
    		
    	}else if(comprobarValidez()==1){
			NombreLista.setVisible(true);
			tfNombreLista.setVisible(true);
    		notify(comprobarValidez()).show(Page.getCurrent());
    		
    	}else{   
    		notify(comprobarValidez()).show(Page.getCurrent());
    	}
	}
	
	public boolean valorYposValido(){
		
		if(boolvPos && boolvValor){
			return true;
		}else{return false;}
		
	}
	//aqui comprobamos 
	public void NuevoItem(){
		//cada vez que clica en añadir item ,vamos a controlar si esta introduciendo una linea o valor repetido
		if(verificarCampos()==0 && valorYposValido()){
			//if aqui dentro
			if( lista.getArrTL().contains(new LineaLista(vNuevoItem))){
				System.out.println(":::> ya existe una linea con este valor");
				notify(2).show(Page.getCurrent());;
			}else{
				lista.setNombreLista(vNombreLista);  
		    	tLista=new LineaLista();
		    	tLista.setNombreLista(vNombreLista);
		    	tLista.setIdLineaLista(idLineaLista);
		    	tLista.setTextoLinea(vNuevoItem);
		    	tLista.setPos(vPosicion);
		    	tLista.setValor(vValor);
		    	lista.addLineaLista(tLista);
		    	

		         tablaItems.setCaption(vNombreLista);
		         tablaItems.addItem(new Object[]{vNuevoItem,vPosicion,vValor},new Integer(i));
		         habilitarVistaNLista(3);
		         i++;
		         idLineaLista++;
				 infoIdLineaLista.setValue("idLinea: "+idLineaLista);
			}
		}else{
			notify(verificarCampos()).show(Page.getCurrent());
		}

		
	}
	
	//comprueba que los campos de nueva linea y nombre de lista No esten vacios o el Nombre de la lista NO sea repetida
	public int verificarCampos(){
		if((tfNombreLista.getValue().isEmpty() && lista.getNombreLista()==null) || tfNuevoItem.getValue().isEmpty() || tfPosicion.getValue()==null || tfValor.getValue()==null ){
			return 3;//si los campos de nueva linea estan vacios			
		}else if(lista.getArrTL()!=null && lista.getArrTL().contains(new LineaLista(vNuevoItem))){
			return 2;// si el array de lineas de la lista ya contiene esa nueva linea que intenta introducir el usuario.			
		}		
		return 0;		
	}
	
	
	   public int comprobarValidez(){		   
		   if(arrListas.contains(lista)){
			   //retorna 1 si hay una lista con el mismo nombre
			   return 1;			   
		   }else if(!lista.comprobarOrden()){
			   //comprueba el orden de la posicion
			   //comprobamos si el array de lineas de la lista contiene valores repetidos o el caracter ','
			   return 5;
		   }		   
		   return 0;		   
	   }
	
	   public HorizontalLayout hLayCambiarNombre(){
			HorizontalLayout hl=new HorizontalLayout();	hl.setSpacing(true);// btt addItem, guardarLista, Cancelar		

	        NuevoNomLista=new Label("Nuevo nombre de la Lista: ");
	        tfNuevoNomLista=new TextField();
	        tfNuevoNomLista.addValueChangeListener(event ->
	        vNuevoNomLista=(String)event.getProperty().getValue());	        
	        bttConfirmarCambioNLista= new Button("Confirmar ");
	        bttConfirmarCambioNLista.addClickListener( e -> {
	        	System.out.println("--> "+vNuevoNomLista);
	        	if(vNuevoNomLista==null || vNuevoNomLista.equals("")){
	        		notify(4).show(Page.getCurrent());
	        		
	        	}else{
	        		ConexionBBDD.actualizarNombreLista(nombrelistaEdit, vNuevoNomLista);
		    		tablaListas.getItem(filaTListas).getItemProperty("Nombre listas").setValue(vNuevoNomLista);

		    		arrListas.get(arrListas.indexOf(new TipoLista(nombrelistaEdit))).setNombreLista(vNuevoNomLista);
		    		habilitarVistaNLista(6);

	        	}
	    		//vNuevoNomLista

	    		//provisional
	    		//tablaItems.removeAllItems();
	        });
	        
	        bttCancelarCambioNombreLista=new Button("Cancelar");
	        bttCancelarCambioNombreLista.addClickListener( e -> {        	
	    		habilitarVistaNLista(6);
	        });
	        hl.setVisible(false);
	        hl.addComponents(NuevoNomLista,tfNuevoNomLista,bttConfirmarCambioNLista,bttCancelarCambioNombreLista);
	        return hl;
	   }
	   

	   public  Notification notify(int tn){
	    	Notification notif =new Notification("");	    	
	    	switch(tn){
	    	case 1:
	    		notif= new Notification(	 
		    		"Atención",
	    		    "Ya hay una lista con ese nombre.",
	    		    Notification.Type.WARNING_MESSAGE);

	    		// Customize it
	    		notif.setDelayMsec(800);
	    		notif.setPosition(Position.BOTTOM_RIGHT);
	    		break;
	    	case 2:    	
	    		notif= new Notification(
	    		    "Warning",
	    		    "Esta intentando introducir valores repetidos .\n ",
	    		    Notification.Type.WARNING_MESSAGE,true);

	    		// Customize it
	    		notif.setDelayMsec(800);
	    		notif.setPosition(Position.BOTTOM_RIGHT);
	    		break;
	    	case 3:
	    		notif= new Notification(
		    		    "Atento",
		    		    "Hay campos vacíos.\n "
		    		    + "Rellene los campos.",
		    		    Notification.Type.WARNING_MESSAGE,true);

		    		// Customize it
		    		notif.setDelayMsec(800);
		    		notif.setPosition(Position.BOTTOM_RIGHT);
		    		break;
		    		
	    	case 4: 
	    		notif= new Notification(
		    		    "Atención",
		    		    "Especifique un nombre.\n ",		    		   
		    		    Notification.Type.WARNING_MESSAGE,true);

		    		// Customize it
		    		notif.setDelayMsec(800);
		    		notif.setPosition(Position.BOTTOM_RIGHT);
		    		break;
	    	case 5:
	    		notif= new Notification(
		    		    "Atención",
		    		    "Las posiciones de las líneas no son coherentes .\n ",		    		   
		    		    Notification.Type.WARNING_MESSAGE,true);

		    		// Customize it
		    		notif.setDelayMsec(800);
		    		notif.setPosition(Position.BOTTOM_RIGHT);
		    		break;
	    		
	    	case 0:
	    		notif= new Notification(	    		    
		    		    "La lista se añadio satisfactoriamente.",
		    		    Notification.Type.WARNING_MESSAGE);

		    		// Customize it
		    		notif.setDelayMsec(800);
		    		notif.setPosition(Position.BOTTOM_RIGHT);
		    		break;
	    	}
	    	return notif;	    	
	    }
	   
	 //Validator Integer
	   class MyValidatorInt implements Validator {
	        @Override
	        public void validate(Object value) throws InvalidValueException {
	        	if (value.equals("")){
	        		
	        	}else{
	             try {
	                    new Integer(value.toString());
	                } catch (NumberFormatException e) {
	                  throw new InvalidValueException("Este valor NO es un entero.");
	                }
                 //vTfTipo=Integer.parseInt(value.toString());
	        	}
	        }
	    }
	
	   public boolean esEntero(String s)
	   {
	       try
	       {
	           int v=Integer.parseInt(s);
	           if(v>0 && v<3){
		           return true;
	           }else{
	        	   return false;
	           }
	           
	       } catch (NumberFormatException ex)
	       {
	           return false;
	       }
	   }
	 
	
}
