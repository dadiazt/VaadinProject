package layouts;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.vaadin.server.Page;
import com.vaadin.server.Page.Styles;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import BBDD.ConexionBBDD;
import myObjects.ClaseObjStaticas;

public class infoUsuarioActual extends VerticalLayout implements ClaseObjStaticas.ValueChangeListener{


    Button bttCSesion;

	Label NombreUsuarioActual,FechaActual;
    Label NPersonaValorada,dinamica;   
    Label lbEscalasPendientes;
    
    public infoUsuarioActual(VerticalLayout lay) {
    	super();
    	
        ClaseObjStaticas.setValueChangeListener(this); // listener

        Styles styles = Page.getCurrent().getStyles();
      	styles.add(".layout-with-border2 {"
      		+ "    border-bottom: thick dotted #696969;"
      		+ "    border-top: thick dotted #696969;"
      		+ "border-top-width: 2px;"      		
      		+ "border-bottom-width: 2px;"
      		+ "padding-left: 20px;"
      		+ "        }"
      		+ ".mylabelstyleInfoPv {"
      		+ "    font-weight:bold;"
      		+ "    line-height:normal; color:#4169E1"
      		+ "}"
      		+ ".infoEscalaPendiente {"
      		+ "    font-weight:bold;"
      		+ "    line-height:normal; color:#99004C"
      		+ "}"
      		+ ".mylabelstyle {"
      		+ "    font-weight:bold;"
      		+ "    line-height:normal;"
      		+ "}");
      	this.setStyleName("layout-with-border2");
    	
    	
    	HorizontalLayout hlay=new HorizontalLayout();
		hlay.setSizeFull();
		hlay.setSpacing(true);
    	HorizontalLayout hlay2=new HorizontalLayout();
		hlay2.setSizeFull();
		hlay2.setSpacing(true);
    	HorizontalLayout hlay3=new HorizontalLayout();
		hlay3.setSizeFull();
		hlay3.setSpacing(true);
    	HorizontalLayout hlay4=new HorizontalLayout();
		hlay4.setSizeFull();
		hlay4.setSpacing(true);
		
		// este horizontal solo es para crear un espacio
		HorizontalLayout hlay5=new HorizontalLayout();
		hlay5.setSizeFull();
		hlay5.setSpacing(true);
		
		String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        bttCSesion = new Button("Cerrar Sesión");
        bttCSesion.addClickListener( e -> {
            
        	VerticalLayout ly=new VerticalLayout();
//        	ly.addComponent(new Label("========="));
        	UI.getCurrent().setContent(ly);//****

        	Login eHechas=new Login(ly,UI.getCurrent());
        });	
		
		NombreUsuarioActual =new Label(ClaseObjStaticas.nombreUsuarioActual);
		NombreUsuarioActual.setStyleName("mylabelstyle");
		FechaActual=new Label(date);
		FechaActual.setStyleName("mylabelstyle");
	
		//hlay3
		NPersonaValorada=new Label();
		NPersonaValorada.setStyleName("mylabelstyleInfoPv");
		//ConexionBBDD.cargarDinamicasMonitor(ClaseObjStaticas.idUsuarioActual);
		Set<String> set = new HashSet<String>(ClaseObjStaticas.dinamicas);
		dinamica=new Label(set.toString());

		dinamica.setStyleName("mylabelstyle");
		hlay.addComponents(bttCSesion);
		hlay.setComponentAlignment(bttCSesion, Alignment.TOP_RIGHT);
        hlay2.addComponents(NombreUsuarioActual,FechaActual);
        hlay3.addComponents(NPersonaValorada,dinamica);
        //Escalas pendientes
        ConexionBBDD.listarEscalasPorTerminarSegunMonitor(ClaseObjStaticas.idUsuarioActual);
        if(ClaseObjStaticas.arrPVYEscalasPorTerminar.size()>0){
            lbEscalasPendientes=new Label("Tiene "+ClaseObjStaticas.arrPVYEscalasPorTerminar.size()+" escalas pendientes por terminar");
            lbEscalasPendientes.setStyleName("infoEscalaPendiente");
            hlay4.addComponents(lbEscalasPendientes);

        }
        //
        hlay5.addComponent(new Label(" "));
        this.addComponents(hlay,hlay2,hlay3,hlay4,hlay5);
    }
 

	public infoUsuarioActual(Component... children) {
		super(children);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void onValueChange(String newValue) {
		// TODO Auto-generated method stub
		NPersonaValorada.setValue(newValue);
		
	}
    

}
