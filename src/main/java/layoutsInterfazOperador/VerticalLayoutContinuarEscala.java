package layoutsInterfazOperador;

import java.util.ArrayList;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

import BBDD.ConexionBBDD;
import clases.EscalaHecha;
import myObjects.ControlDeLineasEscalaPorTerminar;

public class VerticalLayoutContinuarEscala extends VerticalLayout{

	private ArrayList<EscalaHecha> arrEH;
	private ArrayList<ControlDeLineasEscalaPorTerminar> arrCLPTerminar=new ArrayList<>();

	//newcesito 2 parametros 
	public VerticalLayoutContinuarEscala(int idUsuario,int idEscala){
		
		this.setSpacing(true);
		this.setMargin(new MarginInfo(true,true,false,true));
		
	
		Button bttFinalizarEscala3t=new Button("Finalizar");		
		bttFinalizarEscala3t.addClickListener( e -> {
			
			asignarContenidoALineasEscala();
			finalizarEscala();
			//actualizar en la bbdd
			
				this.removeAllComponents();
			
	        });
		
		this.addComponent(bttFinalizarEscala3t);
		//cambiar la carga
		arrEH=ConexionBBDD.cargarEscalaDeUsuario(idUsuario, idEscala,false); //idEscala -idUsuario ,false-> xq son las que estan sin finalizar
		System.out.println("idU: "+idUsuario+" , idEscala: "+idEscala);
		//añadir label info de que usuario se esta visualizando la escala. 
		Label lbFechaActual=new Label("Fecha de escala: "+arrEH.get(0).getFechaEscala().toString());
		lbFechaActual.setStyleName("mylabelstyle");
		this.addComponent(lbFechaActual);
		for(EscalaHecha eHecha:arrEH){			
			ControlDeLineasEscalaPorTerminar lh=new ControlDeLineasEscalaPorTerminar(eHecha.getTipo(),eHecha.getTextoVisible(),eHecha.getReferencia(),eHecha.getContenido(),this);
			arrCLPTerminar.add(lh);			
		}
		
	}
	
	
	//asignamos los valores de los HLayout(LineasControl) a las EscalaHecha del array arrEH y los campos restantes de EH
	public void asignarContenidoALineasEscala(){
		for(int i=0;i<arrCLPTerminar.size();i++){
			arrEH.get(i).setContenido(arrCLPTerminar.get(i).getValorDeLinea());
			//arrEH.get(i).setId_monitor(ClaseObjStaticas.idUsuarioActual);	//id_monitor
			System.out.println("VLCEscala ==) "+arrEH.get(i).toString());
		}
		
	}

	//Guardamos las lineas de la escala sin opcion a ninguna modificacion ->MODO terminado TRUE
	public void finalizarEscala(){
		ConexionBBDD.actualizarEscalaPorFinalizar(arrEH);
		Notification.show("Escala guardada con éxito.");
		
	}
	
}
