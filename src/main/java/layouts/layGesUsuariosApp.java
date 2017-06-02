package layouts;

import java.util.ArrayList;

import com.vaadin.data.Validator;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import BBDD.ConexionBBDD;
import clases.UsuarioApp;


/**
 * 
 * @author ricale
 *
 * como modificar
 * ya que los nombre no se modifican a no ser que se eliminen 
 *  guardamos el usuario seleccionado y cuando le de al boton guardar edicion 
 *  hacemos el update de ese usuario en la bbdd
 *  
 */

// Editamos la lista de usuarios de la App
//**** en principio finalizada
public class layGesUsuariosApp {

	
	Table tablaUsuarios;
	int filaTUsuarios,newItemTUsuarios,vTfTipo,tipoEdit;
	Boolean activoEdit,vTfActivo,vTipoValido=false;
	String usuarioEdit;
	ArrayList<UsuarioApp> arrUApp;
	ArrayList<String> arrUFT;
	CheckBox cbActivo;
	TextField tfTipo;
	Button bttActualizar,bttEditar,bttConfirmarEd,bttCancelar;
	VerticalLayout vlBtt,vlEdicion;
	Label lbInfo,infoLbTipo;
	
	public  layGesUsuariosApp(VerticalLayout lay){
		lay.setCaption("Gestionar Usuarios");
		arrUFT=ConexionBBDD.totalUsuariosFTallers();
		arrUApp=ConexionBBDD.cargarUsuariosGestionApp();
		HorizontalLayout hlay=new HorizontalLayout();
		hlay.setSpacing(true);
		hlay.setMargin(new MarginInfo(true,true,false,true));

    	// INICIO tabla
		tablaUsuarios = new Table();
		tablaUsuarios.setSelectable(true);        
		tablaUsuarios.addContainerProperty("Nombre Usuario", String.class, null);
		tablaUsuarios.addContainerProperty("Es Activo", Boolean.class, null);
		tablaUsuarios.addContainerProperty("Tipo", Integer.class, null);
		tablaUsuarios.setPageLength(tablaUsuarios.size());
		
		tablaUsuarios.addItemClickListener( e -> {
        	filaTUsuarios= (int) e.getItemId();
        	usuarioEdit=(String)tablaUsuarios.getItem(filaTUsuarios).getItemProperty("Nombre Usuario").getValue();
        	activoEdit=(boolean)tablaUsuarios.getItem(filaTUsuarios).getItemProperty("Es Activo").getValue();
        	tipoEdit=(int)tablaUsuarios.getItem(filaTUsuarios).getItemProperty("Tipo").getValue();
        	
        });
    	for(int j=0;j<arrUApp.size();j++){
    		System.out.println("()"+j+" :"+arrUApp.get(j).toString());
    		tablaUsuarios.addItem(new Object[]{arrUApp.get(j).getNomUsuario(),arrUApp.get(j).isEsActivo(),arrUApp.get(j).getTipo()},new Integer(j+1));

    	}
    	newItemTUsuarios=arrUApp.size()+1;
        // FIN tabla
    
    	bttActualizar=new Button("Actualizar Usuarios");
    	bttActualizar.addClickListener( e -> {
    		//comprobarActualizacionUsuarios()
    		actualizarUsuariosApp();
    		
		 });
    	if(!comprobarActualizacionUsuarios()){
        	bttActualizar.setVisible(true);    		
    	}else{	bttActualizar.setVisible(false);}
    	
    	bttEditar=new Button("Editar usuario");
    	bttEditar.addClickListener( e -> {
    		if(filaTUsuarios>0){
    			cbActivo.setValue(activoEdit);
    	    	tfTipo.setValue(String.valueOf(tipoEdit));
        		vlEdicion.setVisible(true);
        		bttActualizar.setVisible(false);
        		tablaUsuarios.setSelectable(false);
        		bttEditar.setVisible(false);
        		lbInfo.setVisible(false);
    		}else{
    			lbInfo.setVisible(true);
    		}
    		

		 });
    	
    	lbInfo=new Label("Escoja un usuario."); lbInfo.setVisible(false);
    	vlEdicion=hLayoutEdicion();
    	vlBtt=new VerticalLayout();
    	vlBtt.setSpacing(true);
    	vlBtt.addComponents(bttActualizar,bttEditar,vlEdicion,lbInfo);    	
    	
    	hlay.addComponents(tablaUsuarios,vlBtt);    	
    	
    	lay.addComponents(hlay);
    	
	}
	
	//Aqui comprobamos hay algun usuario nuevo en la bbdd de la FTallers respecto a la de la App
	public boolean comprobarActualizacionUsuarios(){
		ArrayList<String> array=new ArrayList<>();
		
		for(int j=0;j<arrUFT.size();j++){
			
			if(!arrUApp.contains(new UsuarioApp(arrUFT.get(j)))){
				System.out.println("---> "+arrUFT.get(j));
				return false;
			}			
		}
		
	
	return true;
	}
	
	// Aqui actualizaremos la lista de usuarios de nuestra app si fuera necesario	
	public void actualizarUsuariosApp(){
		// Añadir texto informativo de cuantos usuarios se añadieron
		for(int j=0;j<arrUFT.size();j++){
					
			if(!arrUApp.contains(new UsuarioApp(arrUFT.get(j)))){
				System.out.println("---> "+arrUFT.get(j));
				arrUApp.add(new UsuarioApp(arrUFT.get(j),true,2));// NO es necesario
				//INSERT
				ConexionBBDD.insertNuevoUsApp(arrUFT.get(j),true,2);

				tablaUsuarios.addItem(new Object[]{arrUFT.get(j),true,2},new Integer(newItemTUsuarios));
				newItemTUsuarios=newItemTUsuarios+1;
			}				
		}
		bttActualizar.setVisible(false);
		
	}
	
	
	//hl botones edicion
	public VerticalLayout hLayoutEdicion(){
		VerticalLayout vlay=new VerticalLayout();

		HorizontalLayout hlay=new HorizontalLayout();
		hlay.setSizeFull();
		hlay.setSpacing(true);
		HorizontalLayout hlay2=new HorizontalLayout();
		hlay2.setSizeFull();
		hlay2.setSpacing(true);
		
		Label info=new Label("Seleccione un usuario de la lista para editarlo.");
		
    	cbActivo=new CheckBox("Es Activo: ");
    	cbActivo.addValueChangeListener(e -> {
    		vTfActivo=Boolean.parseBoolean(e.getProperty().getValue().toString());
    	});
    	tfTipo=new TextField("Tipo: ");
    	tfTipo.addValidator(new MyValidatorInt());
//    	tfTipo.addValueChangeListener(e -> {
//    		vTfTipo=Integer.parseInt(e.getProperty().getValue().toString());
//    	});
    	//alternativa  por si pone un texto
    	tfTipo.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub
				String valor=(String)event.getProperty().getValue();
				System.out.println("==) "+valor);

				if(valor!=null && !valor.equals("") && esEntero(valor)){
		    		vTfTipo=Integer.parseInt(valor);
		    		vTipoValido=true;
				}else{
		    		vTipoValido=false;				
				}
			}
		});   	
		
		
		bttConfirmarEd=new Button("Confirmar Edicion");
		bttConfirmarEd.addClickListener( e -> {
			// comprobamos si el tipo que ha introducido es válido o NO
			if(vTipoValido){
				//update de ConexionBBDD
				ConexionBBDD.actualizarUsuarioApp(usuarioEdit,vTfActivo,vTfTipo);
				
				tablaUsuarios.getItem(filaTUsuarios).getItemProperty("Es Activo").setValue(vTfActivo);
				tablaUsuarios.getItem(filaTUsuarios).getItemProperty("Tipo").setValue(vTfTipo);

				vlEdicion.setVisible(false);
				bttEditar.setVisible(true);
				tablaUsuarios.setSelectable(true);
				//actualizo los datos del CB y TF porque si confirma y vuelve a darle a editar sin haber clicado en otra fila ,los datos que carga son los anteriores
	        	activoEdit=(boolean)tablaUsuarios.getItem(filaTUsuarios).getItemProperty("Es Activo").getValue();
	        	tipoEdit=(int)tablaUsuarios.getItem(filaTUsuarios).getItemProperty("Tipo").getValue();
				infoLbTipo.setVisible(false);

			}else{
				//Mostrar notificacion 
				infoLbTipo.setVisible(true);

			}       	

		 });
		 
		
		bttCancelar=new Button("Cancelar");
		bttCancelar.addClickListener( e -> {
			vlEdicion.setVisible(false);
			bttEditar.setVisible(true);
			tablaUsuarios.setSelectable(true);
		 });
		
		hlay.addComponents(cbActivo,tfTipo);
		hlay2.addComponents(bttConfirmarEd,bttCancelar);
    	vlay.setVisible(false);

    	infoLbTipo=new Label("El tipo que esta intentando introducir NO es válido.");
    	infoLbTipo.setVisible(false);
		vlay.addComponents(info,hlay,hlay2);
		vlay.addComponent(infoLbTipo);				

		 return vlay;
    	
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
