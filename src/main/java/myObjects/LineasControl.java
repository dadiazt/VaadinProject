package myObjects;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.vaadin.data.Validator;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.server.Page;
import com.vaadin.server.Page.Styles;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import BBDD.ConexionBBDD;
import clases.LineaLista;


/**
 * 
 * @author ricale
 *
 *
 *textfield con su notificacion y control del tipo de  dato que -> es/recibe/y guarda
 */


/*
    // 1 fecha
	// 2 texto a introducir o modificar.  --- nombre:
	// 3 booleano
*	// 4 formula 
  	// 5 elegir varios en una lista
	// 6 elegir uno en una lista
	// 7 entero
	// 8 float
	// 9 texto no modificable.
 * */
public class LineasControl {
	
	private int type; //
	String cabecera;
	private String valorDeLinea;
	HorizontalLayout hl;
	
	/*getters  setters */
	public String getValorDeLinea() {
		return valorDeLinea;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setValorDeLinea(String valorDeLinea) {
		this.valorDeLinea = valorDeLinea;
	}
	
	
	/*constructor 1- carga la escala para rellenar */
	
	// hay que aumentar parametros, como la referencia 
	public LineasControl(int tipo, String label,String ref,VerticalLayout lay){
		//	public LineasControl(int tipo, String label,VerticalLayout lay,String ref){

		
    	switch(tipo){
    	
    	//fechas
    	case 1:  	lay.addComponent(hLayoutType1(label));
    	break;
    	
    	//texto que recibe un valor ej: nombre
    	case 2:    	lay.addComponent(hLayoutType2(label));    	
    	break;
    		
    	//boolean
    	case 3:    	lay.addComponent(hLayoutType3(label));    	
    	break;
    	
    	case 4:;
    	break;
    	//combobox multiple
    	case 5:    	lay.addComponent(hLayoutType5(label,ref));
    	break;
    	//combobox un valor
    	case 6:		lay.addComponent(hLayoutType6(label,ref));
    	break;
    	
    	//integer
    	case 7:   	lay.addComponent(hLayoutType7(label));
    	break;
    	
    	//float
    	case 8:    	lay.addComponent(hLayoutType8(label));    	
    	break; 
    	
    	//texto no modificable
    	case 9:    	lay.addComponent(hLayoutType9(label));
    	break;
    		
    		default:;
    		break;      	
    	}	
	}
	
	/**
	 * ITEMS
	 * **/
	
	/* ............. */
	
	public String getCabecera() {
		return cabecera;
	}

	public void setCabecera(String cabecera) {
		this.cabecera = cabecera;
	}

	/**
	 * Horizontal Layout	 
	 */
	
	public HorizontalLayout hLayoutType1(String txt){
		HorizontalLayout hlay=new HorizontalLayout();
		hlay.setSizeFull();
		hlay.setSpacing(true);
		
		DateField df=new DateField();
    	df.setDateFormat("dd-MM-yyyy");
    	df.setDescription("dd-MM-yyyy");
    	df.addValidator(new MyValidatorDate());
    	Label lb=new Label(txt);
		//valorDeLinea=String.valueOf(df.getValue());
    	hlay.addComponents(lb,df);
    	
		return hlay;
		
	}
	
	public HorizontalLayout hLayoutType2(String txt){
		HorizontalLayout hlay=new HorizontalLayout();
		hlay.setSizeFull();
		hlay.setSpacing(true);
		
    	Label lb=new Label(txt);
		TextField tf=new TextField();
    	hlay.addComponents(lb,tf);
    	
    	tf.addValueChangeListener(event ->
        // Do something with the value
        valorDeLinea=(String)event.getProperty().getValue());
    	
    	return hlay;
		
	}
	
	//checkbox
	public HorizontalLayout hLayoutType3(String label){
		HorizontalLayout hlay=new HorizontalLayout();

		CheckBox chb=new CheckBox(label);
    	chb.addValueChangeListener(event -> 
    	valorDeLinea=String.valueOf(chb.getValue()));

    	hlay.addComponent(chb);
    	
    	return hlay;
    	
	}	
	
	//combobox multiseleccion
	public HorizontalLayout hLayoutType5(String txt,String ref){
		HorizontalLayout hlay=new HorizontalLayout();
		hlay.setSizeFull();
		hlay.setSpacing(true);
		
    	Label lb=new Label(txt);
   
    	ArrayList<LineaLista> arr=ConexionBBDD.cargarLista(ref);
    	ListSelect select = new ListSelect();
    	for(LineaLista s:arr){
        	select.addItem(s.getTextoLinea());
    	}
     	
    	select.setMultiSelect(true);
    	// Show 4 items and a scrollbar if there are more
    	select.setRows(4);

    	select.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub
				Set<String> valueSel=(Set<String>) event.getProperty().getValue();
				if(valueSel!=null){
					
					ArrayList<String> arrValors=new ArrayList<String>();	
					//valorDeLinea=valueSel.toString();
					
					for(String s:valueSel){
						System.out.println("==> -> "+s);
						arrValors.add(String.valueOf(arr.get(arr.indexOf(new LineaLista(s))).getValor()));
						valorDeLinea=arrValors.toString();

					}
				}else{
				/*if(valorDeLinea==null){
        			notify(2).show(Page.getCurrent());}
				 */
				}
			}
		});    	
    	
    	hlay.addComponents(lb,select);
    	
    	
    	return hlay;
		
	}
	
	//combobox 
	public HorizontalLayout hLayoutType6(String txt,String ref){
		HorizontalLayout hlay=new HorizontalLayout();
		hlay.setSizeFull();
		hlay.setSpacing(true);
    	ArrayList<LineaLista> arr=ConexionBBDD.cargarLista(ref);

    	Label lb=new Label(txt);
    	ComboBox cmb=new ComboBox();
    	for(LineaLista s:arr){
    		cmb.addItem(s.getTextoLinea());
    	}
//    	cmb.addValueChangeListener(event->
//    	valorDeLinea=(String)event.getProperty().getValue());
    	//
    	cmb.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub
				String valueCMB=(String)event.getProperty().getValue();
				if(valueCMB!=null){
					//arr.get(arr.indexOf(new LineaLista(valueCMB))).getValor();
					valorDeLinea=String.valueOf(arr.get(arr.indexOf(new LineaLista(valueCMB))).getValor());
				}else{
				
				}
			}
		});    	
    	
    	
    	hlay.addComponents(lb,cmb);
    	
    	cmb.addValueChangeListener(event ->
        // Do something with the value
        valorDeLinea=(String)event.getProperty().getValue());
    	
    	return hlay;
	}
	
	
	//tipo7  entero
	public HorizontalLayout hLayoutType7(String txt){
		HorizontalLayout hlay=new HorizontalLayout();
		hlay.setSizeFull();
		hlay.setSpacing(true);
		
    	Label lb=new Label(txt);
		TextField tf=new TextField();
    	tf.setInputPrompt("0");
    	tf.addValidator(new MyValidatorInt());
    	hlay.addComponents(lb,tf);
    	
//    	tf.addValueChangeListener(event ->
//        // Do something with the value
//        valorDeLinea=(String)event.getProperty().getValue());
    	
    	return hlay;
		
	}
	
	public HorizontalLayout hLayoutType8(String txt){
		HorizontalLayout hlay=new HorizontalLayout();
		hlay.setSizeFull();
		hlay.setSpacing(true);
		
    	Label lb=new Label(txt);
		TextField tf=new TextField();
		tf.setInputPrompt("0.00");
    	tf.addValidator(new MyValidatorFloat()); 
		
    	hlay.addComponents(lb,tf);
    	    	
    	return hlay;		
	}
	
	//Texto no modificable
	public HorizontalLayout hLayoutType9(String label){
		HorizontalLayout hlay=new HorizontalLayout();

		Label lb=new Label(label);
    	hlay.addComponent(lb);
    	hlay.setComponentAlignment(lb, Alignment.MIDDLE_RIGHT);
    	
    	return hlay;
		
		
	}
	
	
	/**
	 * VALIDATORS
	 * **/
	
	//Validator Integer
	   class MyValidatorInt implements Validator {
	        @Override
	        public void validate(Object value) throws InvalidValueException {
	        	if (value.equals("")){
	        		
	        	}else{
	             try {
	                  // new Integer(text);
	                    new Integer(value.toString());
	                    valorDeLinea=value.toString();
	                } catch (NumberFormatException e) {
	                  throw new InvalidValueException("Este valor NO es un entero.");
	                }
	        	}
	        }
	    }
	   
		//Validator Float
	   class MyValidatorFloat implements Validator {
	        @Override
	        public void validate(Object value) throws InvalidValueException {
	        	if (value.equals("")){
	        		
	        	}else{
	             try {
	                  // new Integer(text);
	                    new Float(value.toString());
	                    valorDeLinea=value.toString();

	                } catch (NumberFormatException e) {
	                  throw new InvalidValueException("Este valor NO es un decimal.");
	                }
	        	}
	        }
	    }
	
		//Validator String  (?)
	   class MyValidatorString implements Validator {
	        @Override
	        public void validate(Object value) throws InvalidValueException {
	        	if (value.equals("")){
	        		
	        	}else{
	             try {
	                  // new Integer(text);
	                    new String(value.toString());
	                } catch (NumberFormatException e) {
	                  throw new InvalidValueException("Este valor NO es un texto.");
	                }
	        	}
	        }
	    }
	   
	   //validator date
		//Validator String  (?)
	   class MyValidatorDate implements Validator {
	        @Override
	        public void validate(Object value) throws InvalidValueException {
	        	
	             try {
	                  // new Integer(text);
	                    //new String(value.toString());
	                    
	             	   String fields[] = String.valueOf(value).split("/");
	                   if (fields.length >= 3) {
	                           int year  = Integer.parseInt(fields[2]);
	                           int month = Integer.parseInt(fields[1])-1;
	                           int day   = Integer.parseInt(fields[0]);
	                   }
	                    
	                   valorDeLinea=String.valueOf(value);
	                } catch (NumberFormatException e) {
	                  throw new InvalidValueException("Este valor NO es una fecha valida.");
	                }
	        	
	        }
	    }
	   


	
}
