package layoutsInterfazOperador;

import java.util.ArrayList;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import BBDD.ConexionBBDD;
import clases.PersonaValorada;
import layouts.infoUsuarioActual;
import layouts.layoutVerEscalaHecha;
import myObjects.ClaseObjStaticas;
import myObjects.LineasControl;

public class InterfazOperador {

	ArrayList<LineasControl> arrLC;
	String usuario;
	String escala,dinamicaEscogida,userEscalaNoFinalizada;
	private int idEscalaParaHacer;
	ArrayList<PersonaValorada> alPV=new ArrayList<>();
	ArrayList<String[]> arrLEscalas=new ArrayList<>();
	ComboBox cmbPValoradas,cmbEscalas,cmbDinamicas,cmbXTerminar;
	HorizontalLayout hlayTUsuario,hlayTEscala,hlBotonesContEscala;
	Button bttIEscala,bttback;
	Button bttVEscala,bttContinuarEscala,bttFinalizarEscala3t; // tab visualizar/ terminar
	ConexionBBDD cnx;
	VerticalLayout vlayHacerEscalaOpciones,vlayVerEscala;
	VerticalLayout vlayVisualizarEscalaOpciones; //lay de tab visualizar
	VerticalLayout layVisualizarEscala,vlayHacerEscala,vlayTerminarEscala; //vLayouts del tab REALIZAR 
	//VerticalLayout layVisualizarEscala,vlayHacerEscala;
	
	public InterfazOperador(VerticalLayout layout){
		layout.removeAllComponents();
		layout.setSpacing(true);
		layout.setMargin(new MarginInfo(false,true,true,true));
		
		// cargar el array segun  la dinamica escogida 
		//meter el if segun funcio
		//if mirando la funcio que tiene el usuario / OFTECNICA-AT_DIRECTA
    	
        infoUsuarioActual layInfo=new infoUsuarioActual(layout);

		//lay de tab visualizar 
		layVisualizarEscala=new VerticalLayout();
		layVisualizarEscala.setCaption("Visualizar escala");
		vlayVisualizarEscalaOpciones=new VerticalLayoutTabVisualizar();
		layVisualizarEscala.addComponent(vlayVisualizarEscalaOpciones);
		//layVisualizarEscala.addComponent(new Label ("veer"));

		//lay de tab realizar 
		vlayHacerEscala=new VerticalLayout();	
		vlayHacerEscala.setSpacing(true);		vlayHacerEscala.setMargin(new MarginInfo(true,true,true,true));
		vlayHacerEscala.setCaption("Realizar escala");
		vlayHacerEscalaOpciones=tabRealizar();		

		//atras
		  bttback=new Button("Atrás");
	        bttback.addClickListener( e -> {
	        	vlayHacerEscalaOpciones.setVisible(true);
	        	vlayHacerEscala.removeComponent(vlayVerEscala);
	    		//vlayVerEscala.setVisible(false);//****
	        	bttIEscala.setVisible(false);
	        	bttback.setVisible(false);
	        	

	        });
	        bttback.setVisible(false);
		vlayHacerEscala.addComponents(vlayHacerEscalaOpciones,bttback);
		
		//--vlay terminar escalas
		vlayTerminarEscala=tabTerminar();
		vlayTerminarEscala.setCaption("Escalas por terminar");
		//--
		
		TabSheet tabsheet = new TabSheet();
		tabsheet.addTab(vlayHacerEscala);
		tabsheet.addTab(layVisualizarEscala);//layVisualizarEscala
		tabsheet.addTab(vlayTerminarEscala);//escalas por terminar
		layout.addComponents(layInfo,tabsheet);
	}

	public VerticalLayout tabTerminar(){
		VerticalLayout vlTerminarEscala=new VerticalLayout();
		vlTerminarEscala.setSpacing(true);
		vlTerminarEscala.setMargin(new MarginInfo(true,true,true,true));
		VerticalLayout vlayContinuarEscala;
		if(ClaseObjStaticas.arrPVYEscalasPorTerminar.size()>0){
			cmbXTerminar=new ComboBox();
//			cmbXTerminar.setSizeFull();
			ConexionBBDD.listarPvaloradasEscalasNoTerminadas();
	    	for(String[] s:ClaseObjStaticas.arrPVYEscalasPorTerminar){
	    		cmbXTerminar.addItem(s[2]);
	    	}
			
			cmbXTerminar.addValueChangeListener(new ValueChangeListener() {
				
				@Override
				public void valueChange(ValueChangeEvent event) {
					// TODO Auto-generated method stub
					userEscalaNoFinalizada=(String)event.getProperty().getValue();
					if(userEscalaNoFinalizada!=null){
						//+++continuar
						//vlayContinuarEscala=new VerticalLayoutContinuarEscala();

						bttContinuarEscala.setVisible(true);
					}else{
						bttContinuarEscala.setVisible(false);

					}
				}
			});	
			
			bttContinuarEscala=new Button("Continuar");		bttContinuarEscala.setVisible(false);
			bttContinuarEscala.addClickListener( e -> {
				
				idPVEscalaPorTerminar(vlTerminarEscala);
//				vlTerminarEscala.removeAllComponents();
		        });

			
			
			hlBotonesContEscala=new HorizontalLayout();
			hlBotonesContEscala.setSpacing(true);
			hlBotonesContEscala.addComponents(bttContinuarEscala);
			
			vlTerminarEscala.addComponents(cmbXTerminar,bttContinuarEscala);

		}
		

		
		return vlTerminarEscala;
		
	}
	
	//funcion que devuelve el id de la persona valorada con la escala por terminar
	public void idPVEscalaPorTerminar(VerticalLayout vl){
		int id=0;
		int idEscala=0;
		for(String[] s:ClaseObjStaticas.arrPVYEscalasPorTerminar){
			if(s[2].equals(userEscalaNoFinalizada)){
				idEscala=Integer.parseInt(s[0]);
				id=Integer.parseInt(s[1]);
			}
		}		

		VerticalLayout CEscala=new VerticalLayoutContinuarEscala(id,idEscala);
		vl.addComponents(CEscala);
	}
	
	//
	public VerticalLayout tabRealizar(){
		VerticalLayout layHacerEscala=new VerticalLayout();
		//layHacerEscala.setCaption("Realizar escala");
		
		hlayTUsuario=hLayoutTypeUsuario();
		hlayTEscala=hLayoutTypeEscala();
		
		//boton-Peradejord85
        bttIEscala = new Button("Iniciar Escala");
        bttIEscala.addClickListener( e -> { 
        	
        	// ****  por hacer
        	// iniciar la clase que mostrara la clase para hacer
        	vlayHacerEscalaOpciones.setVisible(false);	
    		vlayVerEscala=new LayoutEscalaParaHacer(idEscalaParaHacer);//****
    		vlayVerEscala.setVisible(true);//****
    		vlayHacerEscala.addComponent(vlayVerEscala);// **** hacer este addcomponent al hacer clic en iniciar
    		cmbDinamicas.setValue(null);
			cmbPValoradas.setValue(null);
			cmbEscalas.removeAllItems();
            bttback.setVisible(true);
//    		vlayVerEscala.setVisible(false);
    			
        });
        bttIEscala.setVisible(false);
        /******************/
      

        
		layHacerEscala.addComponents(hLayoutTypeDinamicas(),hlayTUsuario,hlayTEscala,bttIEscala);
		return layHacerEscala;
		
	}

	
	// === inicio  HLays de tab realizar
	//cmbDinamicas 1 
	public HorizontalLayout hLayoutTypeDinamicas(){
		HorizontalLayout hlay=new HorizontalLayout();
		hlay.setSizeFull();
		hlay.setSpacing(true);
		
    	Label lb=new Label("Dinamicas");
    	cmbDinamicas=new ComboBox();
    	cmbDinamicas.setSizeFull();
    	if(ClaseObjStaticas.funcioUsuariActual.equals("OFTECNICA")){
    		for(String d:ClaseObjStaticas.idDinamicas){
    			cmbDinamicas.addItem(d);
    		}
    	}else{
    		for(String d:ClaseObjStaticas.dinamicas){
    			cmbDinamicas.addItem(d);
    		}
    	}
    		
    	cmbDinamicas.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub
				dinamicaEscogida=(String)event.getProperty().getValue();
				if(dinamicaEscogida!=null){
					ClaseObjStaticas.setValor("");

					//aqui rellenamos el combo de usuarios segun la dinamica escogida
					cmbPValoradas.removeAllItems();
					if(ClaseObjStaticas.funcioUsuariActual.equals("OFTECNICA")){
						alPV=ConexionBBDD.listaPValoradasXDinamica(dinamicaEscogida);
			    	}else{
						alPV=ConexionBBDD.listaPValoradasXDinamica(ClaseObjStaticas.idDinamicas.get(ClaseObjStaticas.dinamicas.indexOf(dinamicaEscogida)));

			    	}
					System.out.println("---"+alPV.size());
					for(PersonaValorada pv:alPV){
						cmbPValoradas.addItem(pv.getNombre());
					}
					hlayTUsuario.setVisible(true);
				}else{
					hlayTUsuario.setVisible(false);
					hlayTEscala.setVisible(false);
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
				
					arrLEscalas=ConexionBBDD.listarEscalas(); //si ha escogido un usuario listamos las escalas disponibles
			    	for(String[] s:arrLEscalas){
			    		cmbEscalas.addItem(s[1]);
			    	}
				}else{
					hlayTEscala.setVisible(false);
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
					//**** ocultar este boton en casos raros   -_- 
					if(iniciarEscalaOk()){
						bttIEscala.setVisible(true);
					}else{
						bttIEscala.setVisible(false);
					}
				}
			}
		});
    	
    	hlay.setVisible(false);
    	hlay.addComponents(lb,cmbEscalas);
  
    	return hlay;
	}
		
	public boolean iniciarEscalaOk(){
		if(cmbDinamicas.isVisible() && hlayTUsuario.isVisible() && hlayTEscala.isVisible()){
			return true;
		}else{return false;}
	}
	//  ========== fin HLay  tab - realizar
	
	
}
