package layoutsInterfazOperador;

import java.util.ArrayList;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

import BBDD.ConexionBBDD;
import clases.EscalaHecha;
import myObjects.ClaseObjStaticas;
import myObjects.LineasControl;

public class LayoutEscalaParaHacer extends VerticalLayout {

	ArrayList<EscalaHecha> arrEH=new ArrayList<>();
	ArrayList<LineasControl> arrLC=new ArrayList<>();
	
	public LayoutEscalaParaHacer(int idEscala){
		//ConexionBBDD.cerrarConexion();
		this.setSpacing(true);
		this.setMargin(new MarginInfo(true,true,false,true));
		
		
	    // boton cargar datos
        Button buttonGuardar = new Button("Guardar Escala");
        buttonGuardar.addClickListener( e -> {

        	asignarContenidoALineasEscala();
        	guardarEscala();
        	this.removeAllComponents();

        });

        //boton finalizar
        Button botonFinalizar = new Button("Finalizar Escala");
        botonFinalizar.addClickListener( e -> {
            //tb.removeItem(2);
        	asignarContenidoALineasEscala();
        	finalizarEscala(); 
        	this.removeAllComponents();

        });
		HorizontalLayout hlBotones=new HorizontalLayout();
		hlBotones.setSizeFull();
		hlBotones.setSpacing(true);
		hlBotones.addComponents(buttonGuardar,botonFinalizar);
		//ConexionBBDD.cerrarConexion();
		this.addComponents(hlBotones);
		
		// cargarEscalaParaRellenar
		arrEH=ConexionBBDD.cargarEscalaParaRellenar(idEscala);
		for(EscalaHecha eHecha:arrEH){
			System.out.println(eHecha.toString());
	        LineasControl lc=new LineasControl(eHecha.getTipo(),eHecha.getTextoVisible(),eHecha.getReferencia(),this);
	        arrLC.add(lc);
		}
		
    
	}
	
	//asignamos los valores de los HLayout(LineasControl) a las EscalaHecha del array arrEH y los campos restantes de EH
	public void asignarContenidoALineasEscala(){
		System.out.println("xx: "+arrEH.get(0).toString());
		int id_escalaHecha=ConexionBBDD.asignarIdAEscalaHecha(arrEH.get(0).getId_escala(), ClaseObjStaticas.pvEscala.getId());
		for(int i=0;i<arrLC.size();i++){
			arrEH.get(i).setContenido(arrLC.get(i).getValorDeLinea());
			//arrEH.get(i).setFechaEscala(fechaEscala); // Hacerlo mejor en el INSERT directamente
			arrEH.get(i).setId_usuario(ClaseObjStaticas.pvEscala.getId());	//id_usuario
			arrEH.get(i).setId_monitor(ClaseObjStaticas.idUsuarioActual);	//id_monitor
			arrEH.get(i).setId_dinamica(ClaseObjStaticas.pvEscala.getidDinamica());	//id_dinamica
			arrEH.get(i).setId_escalaHecha(id_escalaHecha);
			//arrEH.get(i).setFinalizada(b);	//asignar esto segun los metodos guardar o finalizar
			System.out.println("==) "+arrEH.get(i).toString());
		}
		
	}
	
	//Guardamos las lineas de la escala pero en --> MODO NO terminado <-- 
	public void guardarEscala(){
		ConexionBBDD.insertEscalaHecha(arrEH, false);
		Notification.show("Escala guardada con éxito.");
	}
	
	//Guardamos las lineas de la escala sin opcion a ninguna modificacion ->MODO terminado TRUE
	public void finalizarEscala(){
		ConexionBBDD.insertEscalaHecha(arrEH, true);
		Notification.show("Escala guardada con éxito.");
		
	}
	
	/* ==  id_usuario,id_monitor, fechaEscala,id_dinamica,id_EscalaHecha,Finalizada,  ==  YA esta hecho :)*/
	
	
	/**
	 ==) EscalaHecha [id_escala=1, id_versionEscala=1, id_lineaEscala=1, id_versionLinea=1, id_usuario=null,
	 *  textoVisible=INICO, pos=1, tipo=9, nulo=0, lineaVisible=true, operaciones=0, contenido=null, referencia=null, 
	 *  id_monitor=null, fechaEscala=null, id_dinamica=null, id_escalaHecha=0, Finalizada=false]
	==) EscalaHecha [id_escala=1, id_versionEscala=1, id_lineaEscala=2, id_versionLinea=1, id_usuario=null, 
	 * textoVisible=Nombre:, pos=2, tipo=2, nulo=3, lineaVisible=true, operaciones=0, contenido=Ricard, referencia=null,
	 * id_monitor=null, fechaEscala=null, id_dinamica=null, id_escalaHecha=0, Finalizada=false]
	==) EscalaHecha [id_escala=1, id_versionEscala=1, id_lineaEscala=3, id_versionLinea=1, id_usuario=null, 
	 * textoVisible=Edad:, pos=3, tipo=7, nulo=3, lineaVisible=true, operaciones=0, contenido=23, referencia=null, 
	 * id_monitor=null, fechaEscala=null, id_dinamica=null, id_escalaHecha=0, Finalizada=false]
	 */
	
}
