package layouts;

import java.util.ArrayList;

// import com.healthmarketscience.jackcess.Database;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

//import com.example.PruebaAdmin.MyUI;

/*
 * esta clase contiene el layout de presentacion de la plicacion
 * es donde se identifica el usuario, se pone la contraseña
 * y se hacede a los otros layaut
 */

import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

public class Inicio {
	
    TextField name = new TextField();
    Label miLabel = new Label();
    Button button = new Button("Click Me");
    ComboBox miCombo = new ComboBox();
    String valorElegido="";
/*
    public void anyadirMonitor(Usuario usuario){
    	miCombo.addItem(usuario.getNomUsuario());
    }
    
    public Inicio(AbsoluteLayout layout){
    	
        layout.setWidth("300px");
        layout.setHeight("300px");
        name.setCaption("Type your name here:");
        
        layout.addComponent(name, "left: 10px; top: 20px;");
        layout.addComponent(button, "left: 10px; top: 75px;");
        layout.addComponent(miLabel, "left: 10px; top: 150px;");
        layout.addComponent(miCombo, "left: 10px; top: 225px;");


        button.addClickListener( e -> {
        	layoutAdministrador layAdmin = new layoutAdministrador(MyUI.miAbsLayout);
//        	miLabel.setValue("Thanks " + valorElegido // name.getValue() 
//                    + ", it works!");
        });
        
        miCombo.addValueChangeListener(new Property.ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub
				valorElegido=(String)miCombo.getValue();
			}
		});

    }
    
    
*/
}
