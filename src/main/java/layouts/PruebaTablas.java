package layouts;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;


// al llenar una tabla poner como new integer a partir de 1 SIEMPRE
public class PruebaTablas {

	
	Table table;
	Button bttActualizar,bttEditar,bttBorrar,bttFEditar;
	CheckBox cbActivo;
	TextField tfTipo;
	VerticalLayout vlEdit;
	Boolean activoEdit,vTfActivo;
	int fila,tipoEdit,vTfTipo;
 	
	public PruebaTablas(VerticalLayout vl){
		
		vl.setSpacing(true);
		
		table=new Table();
		table.setSizeFull();
		table.setSelectable(true);
		table.addContainerProperty("Nombre Usuario", String.class, null);
		table.addContainerProperty("Es Activo", Boolean.class, null);
		table.addContainerProperty("Tipo", Integer.class, null);
		table.setPageLength(table.size());
		//llenar
		table.addItem(new Object[]{"paco",true,2},new Integer(1));
		table.addItem(new Object[]{"Messi",true,1},new Integer(2));
		table.addItem(new Object[]{"Nuria",false,1},new Integer(3));
		table.addItem(new Object[]{"Enriiiic",false,2},new Integer(4));

		
        table.addItemClickListener( e -> {
        	fila= (int) e.getItemId();
        	activoEdit=(boolean)table.getItem(fila).getItemProperty("Es Activo").getValue();
        	tipoEdit=(int)table.getItem(fila).getItemProperty("Tipo").getValue();
        	
        });
    	bttEditar=new Button("Editar Usuarios");
    	bttEditar.addClickListener( e -> {
    		//table.setEditable(true);
    		System.out.println("::: "+fila);
    		if(fila>0){
    			cbActivo.setValue(activoEdit);
    	    	tfTipo.setValue(String.valueOf(tipoEdit));
    			vlEdit.setVisible(true);
    		}
		 });
        
    	bttBorrar=new Button("Borrar Usuario");
    	bttBorrar.addClickListener( e -> {
    		table.removeItem(fila);    		
		 });
    	
    	bttActualizar=new Button("Actualizar Usuarios");
    	bttActualizar.addClickListener( e -> {
    		table.setEditable(false);
    		System.out.println(">> fila clico : "+fila);
    		for(int i=0;i<table.size();i++){
        		//int maxedit=(int)table.getItem(fila).getItemProperty("Máximo").getValue();

    			System.out.println(">>> "+table.getItem(i).getItemProperty("Nombre Usuario").getValue()+" -- "+table.getItem(i).getItemProperty("Es Activo").getValue()+" -- "+table.getItem(i).getItemProperty("Tipo").getValue());
    		}
    		
		 });
    	
    	bttFEditar=new Button("Finalizar edicion");
    	bttFEditar.addClickListener( e -> {
    		//table.setEditable(true);
    		//table.getItem(fila).getItemProperty("Mínimo").
    		table.getItem(fila).getItemProperty("Es Activo").setValue(vTfActivo);
    		table.getItem(fila).getItemProperty("Tipo").setValue(vTfTipo);

    			vlEdit.setVisible(false);
    		
		 });
    	
    	vlEdit=hLayoutTypeEscala();
        vl.addComponents(table,bttEditar,bttBorrar,bttActualizar,bttFEditar,vlEdit);
	}
	
	
	public VerticalLayout hLayoutTypeEscala(){
		VerticalLayout vlay=new VerticalLayout();
		HorizontalLayout hlay=new HorizontalLayout();
		hlay.setSizeFull();
		hlay.setSpacing(true);
		
    	Label lb=new Label("ACTIVO SI/NO Tipo:[1,2]");
    	cbActivo=new CheckBox("Es Activo: ");
    	cbActivo.addValueChangeListener(e -> {
    		vTfActivo=Boolean.parseBoolean(e.getProperty().getValue().toString());
    	});
    	tfTipo=new TextField("Tipo: ");
    	tfTipo.addValueChangeListener(e -> {
    		vTfTipo=Integer.parseInt(e.getProperty().getValue().toString());
    	});
    	hlay.addComponents(cbActivo,tfTipo);
    	vlay.setVisible(false);
    	vlay.addComponents(lb,hlay);
    	return vlay;
	}
	
	
}
