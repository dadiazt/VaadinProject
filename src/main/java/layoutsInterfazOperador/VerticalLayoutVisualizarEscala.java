package layoutsInterfazOperador;

import java.util.ArrayList;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import BBDD.ConexionBBDD;
import clases.EscalaHecha;
import myObjects.LineasHechas;

public class VerticalLayoutVisualizarEscala extends VerticalLayout{

	private ArrayList<EscalaHecha> arrEH;
	
	//newcesito 2 parametros 
	public VerticalLayoutVisualizarEscala(int idUsuario,int idEscala){
		
		this.setSpacing(true);
		this.setMargin(new MarginInfo(true,true,false,true));
		
		arrEH=ConexionBBDD.cargarEscalaDeUsuario(idUsuario, idEscala,true); //idEscala -idUsuario
		System.out.println("idU: "+idUsuario+" , idEscala: "+idEscala);
		//añadir label info de que usuario se esta visualizando la escala. 
		Label lbFechaActual=new Label("Fecha de escala: "+arrEH.get(0).getFechaEscala().toString());
		lbFechaActual.setStyleName("mylabelstyle");
		this.addComponent(lbFechaActual);
		for(EscalaHecha eHecha:arrEH){			
			LineasHechas lh=new LineasHechas(eHecha.getTipo(),eHecha.getTextoVisible(),eHecha.getContenido(),this);
						
			
		}
		
		
	}
}

