package myObjects;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;


/**
 * 
 * @author ricardo
 * 
 * Aqui se muestra una escala ya finalizada sin opcion a modificar
 *
 */
public class EscalasHechasNoMod {

	ArrayList<LineasControl> arrLC;

	
	public EscalasHechasNoMod(VerticalLayout vl){
		
		vl.removeAllComponents();

		readFile(vl);
		
		
	}
	
	
	//leer los datos de una escala hecha
	   public void readFile(VerticalLayout layout){
	    	String content;	    	
	    	
	    	try {
				FileReader fr= new FileReader(new File("C:\\Users\\ricardo\\WS_F_Tallers\\Vaad_pruebas_int_usuario\\lineasEscala_0_data.csv"));
				BufferedReader br=new BufferedReader(fr);
				
				while((content=br.readLine())!=null){
					
					System.out.println("--> "+content);	
					StringTokenizer stk=new StringTokenizer(content,",");
					
					while(stk.hasMoreElements()){
						//LineasHechas lc=new LineasHechas(layout,stk.nextToken(),stk.nextToken()); //****
						//arrLC.add(lc);				        
					}							
				}
				br.close();				
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    }
	   
}
