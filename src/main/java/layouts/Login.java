package layouts;


import java.io.File;
import java.util.ArrayList;

import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import BBDD.ConexionBBDD;
import clases.UsuarioApp;
import layouts.LayoutAdministrador;
import layoutsInterfazOperador.InterfazOperador;
import myObjects.ClaseObjStaticas;

public class Login {
	
	static String currentUser;
	private String user;
	private String psswd;
	ArrayList<String> alUsusarios;//
	ArrayList<String> alContraseñas=new ArrayList<>();
	ArrayList<UsuarioApp> alUsers=new ArrayList<>();
	static UsuarioApp cUsuario;
	String[] nomPassFunc;
	HorizontalLayout hlayTipo1;
	
	
	public Login(VerticalLayout vl,UI ctx){		
        alUsers=ConexionBBDD.cargarUsuariosGestionApp();
        ClaseObjStaticas.totalUsuariosApp=alUsers.size();
		vl.removeAllComponents();
		vl.setSpacing(true);
		vl.setMargin(new MarginInfo(true,true,false,true));
		hlayTipo1=hLayoutUsuarioTipo1(vl,ctx);
		/*************/
		String basepath = VaadinService.getCurrent()
                .getBaseDirectory().getAbsolutePath();

		//Cargamos la imagen
		FileResource resource = new FileResource(new File(basepath +
		                      "\\img_ftallers.jpg"));		
		//Creamos el componente imagen
		Image image = new Image("", resource);
		image.setWidth("85%");
		
		/*************************/
        Button button1 = new Button("Entrar");
        button1.addClickListener( e -> {  
            nomPassFunc=ConexionBBDD.comprobarPass(user);

            if(psswd!=null){
            	if(user!= null && psswd.equals(nomPassFunc[1])){
            		ClaseObjStaticas.nombreUsuarioActual=nomPassFunc[0];
            		ClaseObjStaticas.idUsuarioActual=user;
            		ClaseObjStaticas.funcioUsuariActual=nomPassFunc[2];//una vez hecho el login guardamos la funcio(en Tabla Usuarios de la db FTallers)
            		// de momento solo admin a ENRICH
            		
            		if(ClaseObjStaticas.funcioUsuariActual.equals("OFTECNICA")){
                		ConexionBBDD.cargarDinamicasOFTecnica();

                	}else{

                		ConexionBBDD.cargarDinamicasMonitor(ClaseObjStaticas.idUsuarioActual);

                	}
            		if(alUsers.get(alUsers.indexOf(new UsuarioApp(user))).getTipo()==1){        			
            			hlayTipo1.setVisible(true);//Cargamos la opcion para escoger la interfaz de Op o Admin
            		}else{
            			InterfazOperador iOp=new InterfazOperador(vl);
//            			InterfazOperador iOp=new InterfazOperador(vl);
            		}        		
            	}else if(user==null ){
            		new Notification(	 
        		    		"!",
        	    		    "Escoja un usuario.",
        	    		    Notification.Type.WARNING_MESSAGE).show(Page.getCurrent());;
            		System.out.println("Notificacion de escoger usuario.");
            	}
            	else{
            		new Notification(	 
        		    		"!",
        	    		    "Contraseña incorrecta.",
        	    		    Notification.Type.WARNING_MESSAGE).show(Page.getCurrent());;
            		System.out.println("Notificacion de escoger usuario.");
            	}
            }else{
            	new Notification(	 
    		    		"!",
    	    		    "Contraseña incorrecta.",
    	    		    Notification.Type.WARNING_MESSAGE).show(Page.getCurrent());       		
            	
            }
        });
		
    	vl.addComponents(cargarUsuarios(),hLayoutPassword(),button1,hlayTipo1,image);
    	vl.setComponentAlignment(image, Alignment.TOP_CENTER);
	}	

	
	//

	public HorizontalLayout hLayoutPassword(){
		HorizontalLayout hlay=new HorizontalLayout();
		hlay.setSizeFull();
		hlay.setSpacing(true);
		
    	Label lb=new Label("Contraseña:");
		PasswordField pf=new PasswordField();
    	hlay.addComponents(lb,pf);
    	
    	pf.addValueChangeListener(event ->
        // Do something with the value
        psswd=(String)event.getProperty().getValue());
    	
    	return hlay;
		
	}
	
	public HorizontalLayout cargarUsuarios(){
		HorizontalLayout hlay=new HorizontalLayout();
		hlay.setSizeFull();
		hlay.setSpacing(true);
		
    	Label lb=new Label("Usuario:");
    	ComboBox cmb=new ComboBox();
    	System.out.println("---> "+alUsers.size());
    	for(UsuarioApp user:alUsers){
    		if(user.isEsActivo()){
            	cmb.addItem(user.getNomUsuario());
    		}
    	}    	
    	
    	cmb.addValueChangeListener(event ->
        	user=(String)event.getProperty().getValue());
    	hlay.addComponents(lb,cmb);

    	return hlay;
		
	}
	

	//
	public HorizontalLayout hLayoutUsuarioTipo1(VerticalLayout vlay,UI context){
			HorizontalLayout hlay=new HorizontalLayout();
			hlay.setSizeFull();
			hlay.setSpacing(true);
			
	    	Label lb=new Label("Usuario:");
	    	 Button bttModoAdmin = new Button("Modo Administrador");
	    	 bttModoAdmin.addClickListener( e -> {  
	       			AbsoluteLayout absLayout = new AbsoluteLayout();
	       			context.setContent(absLayout);
        			LayoutAdministrador lyAdmin=new LayoutAdministrador(absLayout);	    		 
	         });
	    	 Button bttModoOperador = new Button("Modo Operador");
	    	 bttModoOperador.addClickListener( e -> {  
     			InterfazOperador iOp=new InterfazOperador(vlay);
	         });
	 		hlay.setVisible(false);

	    	 hlay.addComponents(bttModoAdmin,bttModoOperador);
	    	return hlay;			
		}
		


}
