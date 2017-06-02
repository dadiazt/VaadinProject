package layoutsInterfazOperador;

import java.util.ArrayList;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import BBDD.ConexionBBDD;
import clases.PersonaValorada;
import myObjects.ClaseObjStaticas;


// Este VLayout se llama en el tab visualizar en interfazOperador
public class VerticalLayoutTabVisualizar extends VerticalLayout{

	
	private ComboBox cmbDinamicas,cmbPValoradas,cmbEscalas;
	private String dinamicaEscogida,usuario,escala;
	private ArrayList<PersonaValorada> alPV=new ArrayList<>();
	private HorizontalLayout hlayTDinamicas,hlayTUsuario,hlayTEscala;
	private ArrayList<String[]> arrLEscalas=new ArrayList<>(); // [0]=id_escala , [1]=nombreEscala
	private int idEscalaParaHacer;
	private Button bttVisualizar,bttAtras;	//bttAtras=> volver a las opciones des escoger dinamica,etc
	private VerticalLayout vlayCBoxOpciones,vlayEscalaCargada; // el vertical que contiene los HL de los combobox
	private Label lbInfo2; // lbInfo2=> label que informa si el usuario escogido tiene o no escalas hechas
	
	
	public VerticalLayoutTabVisualizar(){
		
		this.setSpacing(true);
		this.setMargin(new MarginInfo(true,true,false,true));
		vlayCBoxOpciones=new VerticalLayout();
		
		//iniciamos los 3 hl con los combobox
		hlayTDinamicas=hLayoutTypeDinamicas();
		hlayTUsuario=hLayoutTypeUsuario();
		hlayTEscala=hLayoutTypeEscala();
		vlayCBoxOpciones.addComponents(hlayTDinamicas,hlayTUsuario,hlayTEscala);
		bttVisualizar=new Button("Visualizar");
		bttVisualizar.addClickListener( e -> { 
        	
        	// iniciar la clase que mostrara la escala para visualizar
			vlayCBoxOpciones.setVisible(false);	
			vlayEscalaCargada=new VerticalLayoutVisualizarEscala(alPV.get(alPV.indexOf(new PersonaValorada(usuario))).getId(),idEscalaParaHacer);//**** cambiar este new ()
			vlayEscalaCargada.setVisible(true);//****
    		this.addComponent(vlayEscalaCargada);// **** hacer este addcomponent al hacer clic en iniciar
    		bttVisualizar.setVisible(false);
    		bttAtras.setVisible(true);
//    		vlayVerEscala.setVisible(false);
    			
        });
		bttVisualizar.setVisible(false);
		bttAtras=new Button("Atrás");
		bttAtras.addClickListener( e -> {         	
			ocultoVisibleItems(1);
    			
        });
		bttAtras.setVisible(false);
		lbInfo2=new Label("Este usuario no tiene Escalas Hechas.");	lbInfo2.setVisible(false);
		
		this.addComponents(vlayCBoxOpciones,lbInfo2,bttVisualizar,bttAtras);
	}
	
	//setVisible() setValue() ,remove()
	public void ocultoVisibleItems(int option){
		
		switch(option){
		//boton Atras
		case 1: 
			vlayCBoxOpciones.setVisible(true);	// hacemos visible los combobox
			vlayEscalaCargada.setVisible(false);	// ocultamos la escala que estaba visualizando
			cmbDinamicas.setValue(null);
			cmbPValoradas.setValue(null);
			cmbEscalas.removeAllItems();
			bttAtras.setVisible(false);		
		}		
	}
	
	
	public HorizontalLayout hLayoutTypeDinamicas(){
		HorizontalLayout hlay=new HorizontalLayout();
		hlay.setSizeFull();
		hlay.setSpacing(true);
		
    	Label lb=new Label("Dinamicas");
    	cmbDinamicas=new ComboBox();
    	cmbDinamicas.setSizeFull();
    		for(String d:ClaseObjStaticas.dinamicas){
    			cmbDinamicas.addItem(d);
    		}
    	cmbDinamicas.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub
				dinamicaEscogida=(String)event.getProperty().getValue();

				if(dinamicaEscogida!=null){
					ClaseObjStaticas.setValor("");

					System.out.println("__ Aqui"+dinamicaEscogida+"\n>>>>> "+ClaseObjStaticas.idDinamicas.get(ClaseObjStaticas.dinamicas.indexOf(dinamicaEscogida)));
					//aqui rellenamos el combo de usuarios segun la dinamica escogida
					cmbPValoradas.removeAllItems();
					alPV=ConexionBBDD.listaPValoradasXDinamica(ClaseObjStaticas.idDinamicas.get(ClaseObjStaticas.dinamicas.indexOf(dinamicaEscogida)));
					System.out.println("---"+alPV.size());
					for(PersonaValorada pv:alPV){
						cmbPValoradas.addItem(pv.getNombre());
					}
					hlayTUsuario.setVisible(true);
				}else{
					hlayTUsuario.setVisible(false);
					hlayTEscala.setVisible(false);
					lbInfo2.setVisible(false);
				}
			}
		});
    	
    	hlay.addComponents(lb,cmbDinamicas);
  
    	return hlay;
	}
	
	
	
	// carga un combobox con las posibles personas valoradas por este monitor
	// usuarios 2
	public HorizontalLayout hLayoutTypeUsuario(){
		HorizontalLayout hlay=new HorizontalLayout();
		hlay.setSizeFull();
		hlay.setSpacing(true);
		
    	Label lb=new Label("Usuarios");
    	cmbPValoradas=new ComboBox();
    	cmbPValoradas.setSizeFull();
    	System.out.println(">>"+alPV.size());
    	//comentar este for

		cmbPValoradas.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub
				usuario=(String)event.getProperty().getValue();
				if(usuario!=null){
					ClaseObjStaticas.setValor(usuario);

//					System.out.println("aquiii"+alPV.size());
					System.out.println("==> "+alPV.get(alPV.indexOf(new PersonaValorada(usuario))).getId());
					ClaseObjStaticas.pvEscala=alPV.get(alPV.indexOf(new PersonaValorada(usuario)));
					hlayTEscala.setVisible(true);
					//cambiar el contenido de este array ,****
					arrLEscalas=ConexionBBDD.listarEscalasHechasPorUsuario(alPV.get(alPV.indexOf(new PersonaValorada(usuario))).getId());
					//arrLEscalas=ConexionBBDD.listarEscalas(); //si ha escogido un usuario listamos las escalas disponibles
					if(arrLEscalas.size()<1){
						lbInfo2.setVisible(true);
						bttVisualizar.setVisible(false);
						hlayTEscala.setVisible(false);

					}else{
						lbInfo2.setVisible(false);
						for(String[] s:arrLEscalas){
				    		cmbEscalas.addItem(s[1]);
				    	}
					}
			    	
				}else{
					hlayTEscala.setVisible(false);
					lbInfo2.setVisible(false);
				}
			}
		});
    	
    	
		hlay.setVisible(false);
    	hlay.addComponents(lb,cmbPValoradas);
  
    	return hlay;
	}
	
	// escalas disponibles 3
	public HorizontalLayout hLayoutTypeEscala(){
		HorizontalLayout hlay=new HorizontalLayout();
		hlay.setSizeFull();
		hlay.setSpacing(true);
		
    	Label lb=new Label("Escalas");
    	cmbEscalas=new ComboBox();
    	cmbEscalas.setSizeFull();
    	cmbEscalas.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub
				escala=(String)event.getProperty().getValue();
				if(escala!=null){
					// guardamos en algun sitio el id de la escala que se escogio
					for(String[] s:arrLEscalas){
						if(s[1].equals(escala)){
							idEscalaParaHacer=Integer.parseInt(s[0]);
						}
					}
					bttVisualizar.setVisible(true);
					if(visualizarEscalaOk()){
						bttVisualizar.setVisible(true);
					}else{
						bttVisualizar.setVisible(false);
					}
				}
			}
		});
    	
    	hlay.setVisible(false);
    	hlay.addComponents(lb,cmbEscalas);
  
    	return hlay;
	}
	
	//mostrar boton visualizar
	public boolean visualizarEscalaOk(){
		if(cmbDinamicas.isVisible() && hlayTUsuario.isVisible() && hlayTEscala.isVisible()){
			return true;
		}else{return false;}
	}
	
	
}
