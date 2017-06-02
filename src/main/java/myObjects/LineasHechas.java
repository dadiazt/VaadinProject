package myObjects;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class LineasHechas {

	
	
	public LineasHechas(int tipo,String label,String contenido,VerticalLayout vl){
		
		HorizontalLayout hl=new HorizontalLayout();
		hl.setSizeFull();
		hl.setSpacing(true);
		Label lb=new Label(label);
		lb.setStyleName("mylabelstyle");

		hl.addComponent(lb);		

		if(tipo==1){
			DateFormat readDF = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",Locale.US);
			DateFormat outputDF = new SimpleDateFormat("dd-MM-yyyy"); 

			Date startDate;
			try {
			    startDate = readDF.parse(contenido);
			    String newDateString = outputDF.format(startDate);
			    Label lbContenido=new Label(newDateString);
				hl.addComponent(lbContenido);
			    System.out.println(newDateString);
			} catch (ParseException e) {
			    e.printStackTrace();
			}

		}else if(tipo!=9 && tipo!=1){
			Label lbContenido=new Label(contenido);
			hl.addComponent(lbContenido);
		}
		/*
		 * 		if(tipo==1){
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy"); 
			Date startDate;
			try {
			    startDate = df.parse(contenido);
			    String newDateString = df.format(startDate);
			    Label lbContenido=new Label(newDateString);
				hl.addComponent(lbContenido);
			    System.out.println(newDateString);
			} catch (ParseException e) {
			    e.printStackTrace();
			}

		}else if(tipo!=9 && tipo!=1){
			Label lbContenido=new Label(contenido);
			hl.addComponent(lbContenido);
		}
		 */
		
		vl.addComponent(hl);
		
		
	}
}
