package BBDD;


import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.vaadin.ui.AbsoluteLayout;

import clases.EscalaHecha;
import clases.Escalas;
import clases.LineaLista;
import clases.Lista;
import clases.PersonaValorada;
import clases.Tabla;
import clases.Tablas;
import clases.TipoLista;
import clases.UsuarioApp;
import clases.VersionLineaEntradaEscala;
import myObjects.ClaseObjStaticas;
//import layouts.Inicio;
// import net.ucanaccess.jdbc.UcanaccessDriver;


public class ConexionBBDD  {
	
	//final String urlBBDD="jdbc:ucanaccess:///home/salvador/Escritorio/Vaad_pruebas_int_usuario/src/main/java/BBDD/Database6.mdb";
	//FTallers final String urlBBDD="jdbc:ucanaccess://C:\\Users\\Puig_Castellar\\Desktop\\eclipse\\eclipse\\workspace\\FundacioTallers\\src\\main\\java\\BBDD\\Database6.mdb";
	
	//url windows
	final String urlBBDD="jdbc:ucanaccess://src\\main\\java\\BBDD\\DB_App_FTallers.mdb";	
	private static String urlFT="jdbc:ucanaccess://src\\main\\java\\BBDD\\Db_PiC_b_FT_cp.mdb"; // url Ricardo FTallers			
	private static String url="jdbc:ucanaccess://src\\main\\java\\BBDD\\DB_App_FTallers.mdb"; // url Ricardo App db

	
	//linux
//	final String urlBBDD="jdbc:ucanaccess:///home/usuari/BBDD/DB_App_FTallers.mdb";
//	private static String urlFT="jdbc:ucanaccess:///home/usuari/BBDD/Db_PiC_b_FT_cp.mdb"; // url Ricardo FTallers			
//	private static String url="jdbc:ucanaccess:///home/usuari/BBDD/DB_App_FTallers.mdb"; // url Ricardo App db
	
	
	private ArrayList<Escalas> misEscalas= new ArrayList<Escalas>();
	private ArrayList<Lista> misListas= new ArrayList<Lista>();
	private ArrayList<Tabla> misTablas= new ArrayList<Tabla>();
	public static AbsoluteLayout miAbsLayout;
	public static Connection cnx;


	
	public ConexionBBDD(){ }
	
	public void iniciarTodo(){
		// lee las tablas y las pone en misTablas
		leerTablas();
		// lee las listas y las pone en misListas
		leerListas();
		// lee los usuarios y los pone en misUsuario
		//leerUsuarios(); //****
		// lee las escalas y las pone en misEscalas
		leerEscalas();
		// lee las lineas escalas y las pone en misEsclas dentro del array versionesEscalas y
		leerLineasEscalas();
		
	}
	
	/**********/
	
	/**********/
	
	public void anyadirTablas(Tabla tabla){
		this.misTablas.add(tabla);
	}
	public void anyadirListas(Lista lista){
		this.misListas.add(lista);
	}
	public void anyadirEscala(Escalas escala){
		this.misEscalas.add(escala);
	}

	public ArrayList<Escalas> getMisEscalas() {
		return misEscalas;
	}

	public void setMisEscalas(ArrayList<Escalas> misEscalas) {
		this.misEscalas = misEscalas;
	}

	public ArrayList<Lista> getMisListas() {
		return misListas;
	}

	public void setMisListas(ArrayList<Lista> misListas) {
		this.misListas = misListas;
	}

	public ArrayList<Tabla> getMisTablas() {
		return misTablas;
	}

	public void setMisTablas(ArrayList<Tabla> misTablas) {
		this.misTablas = misTablas;
	}
    
	// lee todas las tablas lss carga
    public void leerTablas(){
    	// limpia la memoria
    	misTablas= null;
    	misTablas= new ArrayList<Tabla>();
        try{			
			
			Connection conexion = DriverManager.getConnection(url,"","");
			Statement st=conexion.createStatement();
			ResultSet rs=st.executeQuery("select * from Tablas");

			// se leen los registros recuperados
			while(rs.next()){
				// campo 1 = id_usuario
				String nombre = (String) rs.getObject(1).toString();
				// se crea el objeto y se memoriza
				misTablas.add(new Tabla(nombre));
				
			}
			// se cierra todo
			rs.close();
        	st.close();
        	conexion.close();
        }catch(SQLException e){
        	e.printStackTrace();
        }
    }
	
	// lee todas las listas y las carga
    public void leerListas(){
    	// reinicia la memoria.
    	misListas= null;
    	misListas=new ArrayList<Lista>();
        try{			
			
			Connection conexion = DriverManager.getConnection(url,"","");
			Statement st=conexion.createStatement();
			ResultSet rs=st.executeQuery("select * from Listas");

			// se leen los registros recuperados
			while(rs.next()){
				// campo 1 = id_usuario
				String nombre=(String)rs.getObject(1).toString();
				// se crea el objeto y se memoriza
				misListas.add(new Lista(nombre));
			}
			// se cierra todo
			rs.close();
        	st.close();
        	conexion.close();
        }catch(SQLException e){
        	e.printStackTrace();
        }
    }
    
	// lee todas las Escalas y las carga
    public void leerEscalas(){
    	// limpia la memoria
    	misEscalas= null;
    	misEscalas=new ArrayList<Escalas>();
        try{			
			
			Connection conexion = DriverManager.getConnection(url,"","");
			Statement st=conexion.createStatement();
			ResultSet rs=st.executeQuery("select * from Escalas");

			// se leen los registros recuperados
			while(rs.next()){
				String nombre=(String)rs.getObject("nombre_escala").toString();
				// booleano que indica si esta activo o no
				int esActivo=(int)rs.getObject("esActivo_escala");
				// id de la escala a la que pertenece
				int id_escala=(int)rs.getObject("id_escala");
				// id del ususario
				String id_usuario=(String)rs.getObject("id_usuario").toString();
				// booleano que indica si esta libre o no
				String tmp=(String)rs.getObject("esta_en_uso").toString();
				Boolean estaUsandose=Boolean.parseBoolean(tmp);
				// int idEscala, String nombre, Date ultimafechaModificacion, String CreadorDeLaVersion, boolean esActiva
				misEscalas.add(new Escalas(id_escala, nombre, id_usuario, esActivo,estaUsandose));

			}
			// se cierra todo
			rs.close();
        	st.close();
        	conexion.close();
        }catch(SQLException e){
        	e.printStackTrace();
        }
    }
    
	// lee todas las Lineas y las carga
    public void leerLineasEscalas(){
    	
    	ArrayList<VersionLineaEntradaEscala> misLineasEscala = new ArrayList<VersionLineaEntradaEscala>();
    	
        try{			
			
			Connection conexion = DriverManager.getConnection(url,"","");
			Statement st=conexion.createStatement();
			ResultSet rs=st.executeQuery("select * from LineaEscala");

			// se leen los registros recuperados
			while(rs.next()){
				// campo 1 = nombre linea
				String nombre_lineaEscala=(String)rs.getObject("nombre").toString();				
				// id linea escala
				int id_linea=(int)rs.getObject("id_lineaEscala");
				// es de entrada o salida
				String tmp=(String)rs.getObject("esEntrada").toString();
				boolean esEntrada=Boolean.parseBoolean(tmp);
				// es de activo
				tmp=(String)rs.getObject("esActivo_lineaEscala").toString();
				boolean esActivo=Boolean.parseBoolean(tmp);
				// es visible
				tmp=(String)rs.getObject("lineaVisible").toString();
				boolean visible=Boolean.parseBoolean(tmp);
				// id escala
				int id_escala=(int)rs.getObject("id_escala");
				// posicion de la linea en la escala
				int pos=(int)rs.getObject("posicion_versionLineaEscala");
				// tipo de la linea en la escala
				int tipo = (int)rs.getObject("tipo");
				// puede ser nulo
				int tipoNulo=(int)rs.getObject("puedeSerNulo");
		    	// texto visible
		    	String texto_visible =(String)rs.getObject("texto_visible").toString();
		    	// referencia
		    	String referencia =(String)rs.getObject("referencia").toString();
		    	String operacion="";
		    	if(tipo==4){
		    		operacion=referencia;
		    		referencia="";
		    	}
		    	// int Escala, int idLinea, Boolean activo, String textoVisible, int pos, int tipo, int puedeSerNulo, boolean lineaVisible, String operaciones, String referencia
		    	misLineasEscala.add(new VersionLineaEntradaEscala(id_escala, id_linea, esActivo, texto_visible, pos, tipo,tipoNulo,visible,operacion, referencia,nombre_lineaEscala,esEntrada));
			}
			// ahora se pasa al array de las lineas de las escalas. 
			// se da por supuesto que las escalas y las versiones estan cargadas
			// 		y ahora se carga las lineas
			// se posiciona en la escala
			for(Escalas e: misEscalas){
					// busca las lineas que le correspondan
					for(VersionLineaEntradaEscala le: misLineasEscala){
						// si le corresponde
						if(e.getIdEscala()==le.getId_escala()){
							// se guarda el objeto version escala dentro de escala
							e.setElemento(le);
						}
					}
			}
			
			// se cierra todo
			rs.close();
        	st.close();
        	conexion.close();
        }catch(SQLException e){
        	e.printStackTrace();
        }
    }
    
    // altera la escala desactivandolas
    public void insertEscala(Escalas escala) {
    	
        try{
           Connection conexion = DriverManager.getConnection(url,"","");
           
           try (PreparedStatement ps = conexion.prepareStatement(
                   "UPDATE Escalas " +
                   "SET esACtivo_escala= " + String.valueOf(escala.getEsActiva()) +
                   " WHERE id_escala= " + escala.getidEscala())) {
               ps.executeUpdate();
               
           }
           conexion.close();
       }catch(SQLException  e){
           e.printStackTrace();
       }
   	         
    }
    
    // inserta un objeto escala en la base de datos.
    public void insertarEscala(Escalas escala){

         try{
            Connection conexion = DriverManager.getConnection(url,"","");

            try (PreparedStatement ps = conexion.prepareStatement(
                    "INSERT INTO Escalas " +
                    "(id_escala, id_usuario,esACtivo_escala,nombre_escala) " +
                    "VALUES " +
                    "(?,?,?,?)"
                    )) {
                ps.setInt(1, escala.getIdEscala());
                ps.setString(2, escala.getCreadorDeLaVersion());
                // ps.setBoolean(3, (boolean)escala.isEsActiva());
                ps.setInt(3, escala.getEsActiva());
                ps.setString(4, escala.getNombre());
                ps.executeUpdate();
                // inserta las lineas
                //insertarArrayVersionesLinea(escala.getMisLineas());
                
            }
            conexion.close();
        }catch(SQLException  e){
            e.printStackTrace();
        }
    	         
    }

    // imprimir el array de escalas
    public void imprimirEscalas(){
    	for(Escalas e: misEscalas){
    		e.imprimirTodo();
    	}
    }
    
    // inserta un array de objetos de lineas de escala dentro de un objeto escala en la base de datos.
    public void insertarArrayVersionesLinea(ArrayList<VersionLineaEntradaEscala> lineasVersionesEscalas){

    	// por cada linea del array
    	for(VersionLineaEntradaEscala le: lineasVersionesEscalas){
    		// inserta una linea
    		insertarLineaEscala(le);
    	}
    	         
    }
    
    // inserta una linea de una escala en la base de datos.
    public void insertarLineaEscala(VersionLineaEntradaEscala escalaLinea){

         try{
            Connection conexion = DriverManager.getConnection(url,"","");

            try (PreparedStatement ps = conexion.prepareStatement(
                    "INSERT INTO LineaEscala " +
                    "(id_escala, id_lineaEscala, " +
                    "esActivo_versionLineaEscala, " +
                    "lineaVisible, referencia, posicion_versionLineaEscala, " +
                    "tipo, textoVisible, puedeSerNulo, nombre) " +
                    "VALUES " +
                    "(?,?,?,?,?,?,?,?,?,?)"
                    )) {
                ps.setInt(1, escalaLinea.getId_escala());
                //ps.setInt(2, escalaLinea.getId_versionEscala());
                ps.setInt(2, escalaLinea.getIdLinea());
                ps.setBoolean(3, (boolean)escalaLinea.getActivo());
                ps.setBoolean(4, (boolean)escalaLinea.getLineaVisible());
                ps.setString(5, escalaLinea.getReferencia());
                //ps.setString(9, escalaLinea.getCreadorDeLaVersion());
                //ps.setInt(6, escalaLinea.getLineaVisible());
                ps.setInt(6, escalaLinea.getPos());
                ps.setInt(7, escalaLinea.getTipo());
                ps.setString(8, escalaLinea.gettextoVisible());
                ps.setInt(9, escalaLinea.getPuedeSerNulo());
                ps.setString(10, escalaLinea.getNombreLinea());
                // el nombre no se graba porque va en la linea no en la version de linea
                ps.executeUpdate();
                
            }
            conexion.close();
        }catch(SQLException  e){
            e.printStackTrace();
        }
    	         
    }
 

    
 
    /****************/
    // borra el contenido de la tabla lineaEscala
	public void borrarLineas(){
        try{
            Connection conexion = DriverManager.getConnection(url,"","");

            try (PreparedStatement ps = conexion.prepareStatement("delete from LineaEscala")) {
                // el nombre no se graba porque va en la linea no en la version de linea
                ps.executeUpdate();
                
            }
            conexion.close();
        }catch(SQLException  e){
            e.printStackTrace();
        }
	}
	
	// borra el contenido de la tabla escalas
	public void borrarEscalas(){
        try{
            Connection conexion = DriverManager.getConnection(url,"","");

            try (PreparedStatement ps = conexion.prepareStatement("delete from Escalas")) {
                // el nombre no se graba porque va en la linea no en la version de linea
                ps.executeUpdate();
                
            }
            conexion.close();
        }catch(SQLException  e){
            e.printStackTrace();
        }
	}

	// borra el contenido de la tabla tablas
	public void borrarTablas(){
        try{
            Connection conexion = DriverManager.getConnection(url,"","");

            try (PreparedStatement ps = conexion.prepareStatement("delete from Tablas")) {
                // el nombre no se graba porque va en la linea no en la version de linea
                ps.executeUpdate();
                
            }
            conexion.close();
        }catch(SQLException  e){
            e.printStackTrace();
        }
	}
	
	// borra el contenido de la tabla lineaTablas
	public void borrarLineaTablas(){
        try{
            Connection conexion = DriverManager.getConnection(url,"","");

            try (PreparedStatement ps = conexion.prepareStatement("delete from LineaTablas")) {
                // el nombre no se graba porque va en la linea no en la version de linea
                ps.executeUpdate();
                
            }
            conexion.close();
        }catch(SQLException  e){
            e.printStackTrace();
        }
	}
    
    // recorre array de versiones de lineas tomadas de nuevas escalas
    // grava lineas y versiones de lineas
    public void gravarLinea(ArrayList<VersionLineaEntradaEscala> escalaLinea){
    	
    	VersionLineaEntradaEscala miLinea=null;
    	for(VersionLineaEntradaEscala le: escalaLinea){
    		miLinea=new VersionLineaEntradaEscala(le.getId_escala(), le.getIdLinea(), 
    				le.getActivo(), le.gettextoVisible(), le.getPos(), le.getTipo(),
    				le.getPuedeSerNulo(), le.getLineaVisible(), le.getOperaciones(), le.getReferencia(),
    				le.getReferencia(), le.isEsEntrada());
    		insertarLineaEscala(miLinea);
    	}
    }
    
    
    // inserta un objeto tabla en la base de datos.
    public void insertarTabla(Tabla tabla){

         try{
            Connection conexion = DriverManager.getConnection(url,"","");

            try (PreparedStatement ps = conexion.prepareStatement(
                    "INSERT INTO Tablas " +
                    "(nombre_tablas) " +
                    "VALUES " +
                    "(?)"
                    )) {
                ps.setString(1, tabla.getNombre());     
                ps.executeUpdate();
            }
            conexion.close();
        }catch(SQLException  e){
            e.printStackTrace();
        }
    	         
    }
    
    // inserta una linea de una tabla en la base de datos.
    public void insertarLineasTabla(Tablas lineasTablas){

         try{
            Connection conexion = DriverManager.getConnection(url,"","");

            try (PreparedStatement ps = conexion.prepareStatement(
                    "INSERT INTO LineaTabla " +
                    "(nombreTabla, idLineaTabla, " +
                    "max, " +
                    "min, valor) " +
                    "VALUES " +
                    "(?,?,?,?,?)"
                    )) {
                ps.setString(1, lineasTablas.getId());
                ps.setInt(2, lineasTablas.getIdLinea());
                ps.setInt(3, lineasTablas.getMax());
                ps.setInt(4, lineasTablas.getMin());
                ps.setInt(5, lineasTablas.getValor());
                ps.executeUpdate();             
            }
            conexion.close();
        }catch(SQLException  e){
            e.printStackTrace();
        }
    	         
    }
    
    
        /******************************************************************/
    /********************** CONSULTAS RICARDO *************************/
    /******************************************************************/
    /******************************************************************/
	//consulta de los usuarios de nuestra APP para su gestión
	public static ArrayList<UsuarioApp> cargarUsuariosGestionApp(){
		ArrayList<UsuarioApp> array=new ArrayList<>();
		try{	
			
			Connection conex = DriverManager.getConnection(url,"","");
			Statement st=conex.createStatement();
			ResultSet rs=st.executeQuery("select * from UsuarioApp");
			while(rs.next()){
				String idNombre=(String)rs.getObject(1).toString();
				boolean activo=Boolean.parseBoolean(rs.getObject(2).toString());
				int tipo=Integer.parseInt(rs.getObject(3).toString());

				UsuarioApp user=new UsuarioApp(idNombre,activo,tipo);
				array.add(user);
			}
			rs.close();
	    	st.close();
	    	conex.close();
	    }catch(SQLException e){
	    	e.printStackTrace();
	    } 		
		return array;
	} 
	
	//de momento no se usa ,es lo mismo que cargarUsuariosGestionApp() sin ACTIVO **** borrar 
	// consulta en login , devuelve todos los usuarios de la app
	public static ArrayList<UsuarioApp> cargarUsuariosApp(){
		//ArrayList<String> array=new ArrayList<>();
		ArrayList<UsuarioApp> arrUApp=new ArrayList<>();

		try{				
			Connection conex = DriverManager.getConnection(url,"","");
			Statement st=conex.createStatement();
			ResultSet rs=st.executeQuery("select nombre_usuario,tipo from UsuarioApp");
			while(rs.next()){
				String idNombre=(String)rs.getObject(1).toString();
				int tipo=Integer.parseInt(rs.getObject(2).toString());
				UsuarioApp uApp=new UsuarioApp(idNombre,tipo);
				//array.add(idNombre);
				arrUApp.add(uApp);

			}			
			rs.close();
	    	st.close();
	    	conex.close();
		}catch(SQLException e){
	    	e.printStackTrace();
	    } 		
		return arrUApp;
	} 
	
	//usa la BBDD FTallers 
	public static ArrayList<String> totalUsuariosFTallers(){		 
		ArrayList<String> arrUsuarioFT=new ArrayList<>();

		try{				 
			
			Connection conex = DriverManager.getConnection(urlFT,"","");
			Statement st=conex.createStatement();
			ResultSet rs=st.executeQuery("select Usuario from Usuarios ");	
		
			while(rs.next()){					
				String idUsuario=(String)rs.getObject(1).toString();
				arrUsuarioFT.add(idUsuario);
			}
			rs.close();
			st.close();			
	    	conex.close();
	    }catch(SQLException  e){
	    	e.printStackTrace();
	    }
		return arrUsuarioFT;
		
	}
	
	//bbdd App /insert en la tabla UsuarioApp de los nuevos usuarios encontrados en Usuarios de la bbdd FTallers
	public static void insertNuevoUsApp(String nUsuario,boolean activo,int tipo){   	
		
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());		
				
				try {
					Connection conex = DriverManager.getConnection(url,"","");
					try (PreparedStatement ps = conex.prepareStatement(
					        "INSERT INTO UsuarioApp " +
					        "(nombre_usuario,esActivo_usuario,tipo,fecha) " +
					        "VALUES " +
					        "(?,?,?,?)"
					        )) {
					    ps.setString(1, nUsuario);
					    ps.setBoolean(2, activo);
					    ps.setInt(3, tipo);
					    ps.setTimestamp(4, timestamp);
					    ps.executeUpdate();
					    
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
					conex.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
	}
	
	// consulta en login ,devuelve el nombre del usuario (que no el id) y la pass de la bbdd
	public static String[] comprobarPass(String user){
		String pass=null;
		String[] nomypass=new String[3];

	try{			
			Connection conex = DriverManager.getConnection(urlFT,"","");
			Statement st=conex.createStatement();
			//Sting[3] , FUNCIO 
			ResultSet rs=st.executeQuery("select User_Personal,Pass,FUNCIO from Usuarios  where Usuario='"+user+"'");
			while(rs.next()){
				nomypass[0]=rs.getString(1);//nom
				nomypass[1]=rs.getString(2);//pass
				nomypass[2]=rs.getString(3);//FUNCIO

			}
			rs.close();
	    	st.close();
	    	conex.close();
	    }catch(SQLException e){
	    	e.printStackTrace();
	    } 
		
	return nomypass;
	}
	

	
	// en interfazoperador cargamos las dinamicas que tenga el monitor logueado ,solo para los que son AT_DIRECTA
	public static void cargarDinamicasMonitor(String monitor){
		ClaseObjStaticas.dinamicas=new ArrayList<>();
		ClaseObjStaticas.idDinamicas=new ArrayList<>();
		
		 try{				        					
				Connection conex = DriverManager.getConnection(urlFT,"","");
				Statement st=conex.createStatement();
				ResultSet rs=st.executeQuery("select dinamica,CODI_DINAMICA from 20_ll_DINAMICA_MONITOR where Monitor='"+monitor+"'");
				while(rs.next()){					
					String dinamica=(String)rs.getObject(1).toString();//10
					String idCodiDinamica=String.valueOf(rs.getObject(2).toString());

					ClaseObjStaticas.dinamicas.add(dinamica); // guardamos
					ClaseObjStaticas.idDinamicas.add(idCodiDinamica); // guardamos	las id de Dinamicas					
				}
				rs.close();				
	        	st.close();
	        	conex.close();
	        }catch(SQLException  e){
	        	e.printStackTrace();
	        }
		
		
	}
	
	//Cargamos todas las dinámicas para los usuarios que son OFTECNICA
	public static void cargarDinamicasOFTecnica(){
		ClaseObjStaticas.dinamicas=new ArrayList<>();
		ClaseObjStaticas.idDinamicas=new ArrayList<>();
		
		 try{				        					
				Connection conex = DriverManager.getConnection(urlFT,"","");
				Statement st=conex.createStatement();
				ResultSet rs=st.executeQuery("select distinct(CODI_DINAMICA),dinamica from 20_ll_DINAMICA_MONITOR where dinamica not like \"FisioTerapia\"");
				while(rs.next()){	
					String idCodiDinamica=rs.getString(1);
					String dinamica=rs.getString(2);
					System.out.println("==Y=> "+idCodiDinamica+" , "+dinamica+" ,,, "+ClaseObjStaticas.idDinamicas.size()+" ,,, "+ClaseObjStaticas.dinamicas.size());

					ClaseObjStaticas.idDinamicas.add(idCodiDinamica); // guardamos	las id de Dinamicas	
					ClaseObjStaticas.dinamicas.add(dinamica);
				}
				rs.close();				
	        	st.close();
	        	conex.close();
	        }catch(SQLException  e){
	        	e.printStackTrace();
	        }
		
	}
	
	//InterfazOperador, devuelve la lista de usuarios que pertenecen a la dinamica que el monitor haya escogido
	public static ArrayList<PersonaValorada> listaPValoradasXDinamica(String d){

		ArrayList<PersonaValorada> arrPV=new ArrayList<>();
		 try{						
				Connection conex = DriverManager.getConnection(urlFT,"","");
				Statement st=conex.createStatement();
				ResultSet rs=st.executeQuery("select dinamica,Id,usuari,CODI_DINAMICA from 20_ll_USUARIS_GRUP_DINAMICA t2 where CODI_DINAMICA=\""+d+ "\"");
				
				while(rs.next()){					
					String dinamica=(String)rs.getObject(1).toString();					
					String idUsuari=String.valueOf(rs.getObject(2).toString());
					String nombreUsuari=String.valueOf(rs.getObject(3).toString());
					String idCodiDinamica=String.valueOf(rs.getObject(4).toString());

					PersonaValorada pv=new PersonaValorada(Integer.parseInt(idUsuari), nombreUsuari,idCodiDinamica);					
					
					System.out.println(">>> "+dinamica+" ,:: "+idUsuari+" ::: "+nombreUsuari);//****
					arrPV.add(pv);
					
				}
			System.out.println(">> "+ClaseObjStaticas.idDinamicas.toString()+"\n >>> "+ClaseObjStaticas.dinamicas.toString());
				rs.close();				
	        	st.close();
	        	conex.close();
	        }catch(SQLException  e){
	        	e.printStackTrace();
	        }		 
		 return arrPV;
	}

	
	//Actualizamos el usuario de la app que el admin haya modificado en ->layGesUsuariosApp
	public static void actualizarUsuarioApp(String usuario,boolean activo,int tipo){
		
		 try{	     			 
				
				Connection conex = DriverManager.getConnection(url,"","");
	        	// idEscala =1  por ejemplo
				Statement st=conex.createStatement();
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				
				String update="UPDATE UsuarioApp SET esActivo_usuario="+activo+",tipo="+tipo+",fecha='"+timestamp+"' WHERE nombre_usuario = '"+usuario+"'";
				st.executeUpdate(update);
			
				st.close();
				conex.close();
	        }catch(SQLException  e){
	        	e.printStackTrace();
	        }
	}

	
	// consulta que devuelve las escalas que hay en la bbdd con su id[0] nombreEscala[1]
	public static ArrayList<String[]> listarEscalas(){
		ArrayList<String[]> arr=new ArrayList<>();
		try{				       			
			Connection conex = DriverManager.getConnection(url,"","");
        	// idEscala =1  por ejemplo
			Statement st=conex.createStatement();
			ResultSet rs=st.executeQuery("select id_escala,nombre_escala from Escalas where esActivo_escala=2 AND esta_en_uso=false");
			
			while(rs.next()){	
				String[] cap=new String[2];
				cap[0]=String.valueOf(rs.getObject(1).toString());
				cap[1]=String.valueOf(rs.getObject(2).toString());		
	
				arr.add(cap);				
			}
			rs.close();				
	    	st.close();
	    	conex.close();
	    }catch(SQLException  e){
	    	e.printStackTrace();
	    }
		
		return arr;
	}
	
	
	//Consulta que carga las escalas / ++++ repasar
	public static ArrayList<EscalaHecha> cargarEscalaParaRellenar(int idEscala){
		ArrayList<EscalaHecha> arrEHechas=new ArrayList<>();
		 try{				 				
				Connection conex = DriverManager.getConnection(url,"","");
	        	// idEscala =1  por ejemplo
				Statement st=conex.createStatement();
//				String select="select   es.id_escala,ve.id_version,le.id_lineaEscala,vle.id_versionLineaEscala,vle.textoVisible,vle.posicion_versionLineaEscala,vle.tipo,vle.puedeSerNulo,vle.lineaVisible,vle.referencia "
//						+ "from Escalas es,Version ve,LineaEscala le,VersionLineaEscala vle "
//						+ "where es.id_escala="+idEscala+" AND ve.id_escala=ve.id_escala AND ve.esActivo_version=true" //	+ "where es.id_escala=ve.id_escala AND ve.esActivo_version=true"
//						+ " AND"
//						+ " ve.id_version=le.id_versionEscala AND  ve.id_escala=le.id_escala"
//						+ " AND "
//						+ "le.esActivo_lineaEscala=true AND le.id_lineaEscala=vle.id_lineaEscala AND le.id_Escala=vle.id_Escala AND le.id_versionEscala=vle.id_versionEscala "
//						+ "AND vle.esActivo_versionLineaEscala=true AND le.id_escala=vle.id_escala AND le.id_versionEscala=vle.id_versionEscala "
//						+ "order by vle.posicion_versionLineaEscala";
				String select="select le.id_escala,id_lineaEscala,textoVisible,posicion_versionLineaEscala,tipo,puedeSerNulo,lineaVisible,referencia,esEntrada "
						+ "from Escalas es,LineaEscala le "
						+ "where  es.id_escala="+idEscala+" AND es.id_escala=le.id_escala AND esActivo_versionLineaEscala=true "
						+ "order by le.posicion_versionLineaEscala";
				ResultSet rs=st.executeQuery(select);	

			
				while(rs.next()){	
					//IDs id_escala
					int id_escala=rs.getInt(1); //
					int id_lineaEscala=rs.getInt(2); //
										
					String textoVisible=rs.getString(3);
					int posicion_versionLineaEscala=rs.getInt(4);
					int tipo=rs.getInt(5);
					int puedeSerNulo=rs.getInt(6);
					boolean lineaVisible=rs.getBoolean(7);
					String referencia=rs.getString(8);
					boolean esEntrada=rs.getBoolean(9);

					EscalaHecha eHecha=new EscalaHecha(id_escala,id_lineaEscala,textoVisible,posicion_versionLineaEscala,tipo,puedeSerNulo,lineaVisible,referencia,esEntrada);
					arrEHechas.add(eHecha);
					
				}
				rs.close();
				st.close();
				conex.close();
	        }catch(SQLException  e){
	        	e.printStackTrace();
	        }	
		 return arrEHechas;
		 
	}
	
	
	//INSERT a la tabla EscalaHecha a partir del array 
	//el boolean que recibe es para asignar si la escala está terminada o NO / repasada
	public static void insertEscalaHecha(ArrayList<EscalaHecha> arrEHecha,boolean b){
		ArrayList<EscalaHecha> arrEh=new ArrayList<>();
		
		 try{		
				Connection conex = DriverManager.getConnection(url,"","");
				Statement st=conex.createStatement();
			 
				//SimpleDateFormat ddmmyyyyFormat = new SimpleDateFormat("dd/MM/yyyy");
				Timestamp ddmmyyyyXmas=null;
				ddmmyyyyXmas = new Timestamp(System.currentTimeMillis());

				//columna -> id_escalaHecha
				for(EscalaHecha n:arrEHecha){
					try (PreparedStatement ps = conex.prepareStatement(
					        "INSERT INTO EscalaHecha " +
					        "(id_escala,id_lineaEscala,textoVisible,posicion_lineaEscala,tipo,puedeSerNulo,lineaVisible,contenido,referencia,id_monitor,id_personaValorada,fechaRealizacionEscala,id_dinamica,id_escalaHecha,Terminado,esEntrada) " +
					        "VALUES " +
					        "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
					        )) {
					    ps.setInt(1, n.getId_escala());
					    ps.setInt(2,n.getId_lineaEscala());
					    ps.setString(3, n.getTextoVisible());
					    ps.setInt(4,n.getPos());
					    ps.setInt(5,n.getTipo());
					    ps.setInt(6,n.getNulo());
					    ps.setBoolean(7, n.getLineaVisible());
					    ps.setString(8,n.getContenido());
					    ps.setString(9,n.getReferencia());
					    ps.setString(10,n.getId_monitor());
					    ps.setInt(11, n.getId_usuario());
					    ps.setTimestamp(12, ddmmyyyyXmas);
					    ps.setString(13, n.getId_dinamica());
					    ps.setInt(14,n.getId_escalaHecha());	//asignar un valor / id_escalaHecha
					    ps.setBoolean(15, b); 
					    ps.setBoolean(16, n.isEsEntrada());
					    
//					    ps.setTimestamp(4, ddmmyyyyXmas);
					    ps.executeUpdate();
					}
				}
				
				conex.close();
	        }catch(SQLException  e){
	        	e.printStackTrace();
	        }
		
		
	}
	
	
	
	//Listar las distintas escalas que ha hecho un usuario (distinta id_escala) / VLayoutTabVisualizar  /id[0] nombreEscala[1]
	public static ArrayList<String[]> listarEscalasHechasPorUsuario(int id){
		ArrayList<String[]> arr=new ArrayList<>();

		try{				       			
			Connection conex = DriverManager.getConnection(url,"","");
			Statement st=conex.createStatement();
			ResultSet rs=st.executeQuery("select distinct(eh.id_escala),es.nombre_escala from EscalaHecha eh,Escalas es "
					+ " where id_personaValorada="+id+" AND eh.id_escala=es.id_escala");
			
			while(rs.next()){	
				String[] cap=new String[2];
				cap[0]=String.valueOf(rs.getObject(1).toString());
				cap[1]=String.valueOf(rs.getObject(2).toString());		
	
				arr.add(cap);				
			}
			rs.close();				
	    	st.close();
	    	conex.close();
	    }catch(SQLException  e){
	    	e.printStackTrace();
	    }
		
		
		return arr;
		
		
	}
	
	//cargamos la escala de una persona valorada para su visualizacion a partir de la escala que se haya escogido
	// carga las escalasHechas ya terminadas=true
	// hay que ver cuando el usuario tenga 2 escalas de la misma escala como listarlas
	// en principio con el order by id_escalaHecha se podria dividir en esas distintas escalas
	public static ArrayList<EscalaHecha> cargarEscalaDeUsuario(int idUsuario,int idEscala,boolean b){
		ArrayList<EscalaHecha> arrEh=new ArrayList<>();
		 try{			  							
				Connection conex = DriverManager.getConnection(url,"","");
				Statement st=conex.createStatement();
				//				String select="select   id_escala,id_versionEscala,id_lineaEscala,id_versionLinea,textoVisible,posicion_lineaEscala,tipo,puedeSerNulo,lineaVisible,operaciones,contenido,referencia,id_monitor,fechaRealizacionEscala "
//				String select="select   textoVisible,posicion_lineaEscala,tipo,puedeSerNulo,lineaVisible,operaciones,contenido,referencia,id_monitor,fechaRealizacionEscala "
//						+ "from EscalaHecha "
//						+ "where Terminado=true AND id_personaValorada='"+idUsuario+"' AND id_escala="+idEscala;
		
				//b=true -> VerticalLayoutVisualizarEscala ,b=false --> VerticalLayoutContinuarEscala
				String select="select * "	
						+ "from EscalaHecha "
						+ "where Terminado="+b+" AND id_personaValorada='"+idUsuario+"' AND id_escala="+idEscala+" "
						+ "order by id_escalaHecha,posicion_lineaEscala ASC";
				
				ResultSet rs=st.executeQuery(select);

				
				while(rs.next()){	
					//IDs id_escala
					int id_escala=rs.getInt(1); //no
					int id_lineaEscala=rs.getInt(2); //no
										
					String textoVisible=rs.getString(3);
					int posicion_versionLineaEscala=rs.getInt(4);
					int tipo=rs.getInt(5);
					int puedeSerNulo=rs.getInt(6);
					boolean lineaVisible=rs.getBoolean(7);
					String contenido=rs.getString(8);
					String referencia=rs.getString(9);
					String id_monitor=rs.getString(10);
					int id_personaValorada=rs.getInt(11);
					Date d=rs.getDate(12);
					String id_dinamica=rs.getString(13);
					int id_escalaHecha=rs.getInt(14);	
					boolean esEntrada=rs.getBoolean(15);
					
					EscalaHecha eHecha=new EscalaHecha(id_escala,id_lineaEscala,id_personaValorada,textoVisible,posicion_versionLineaEscala,tipo,puedeSerNulo,lineaVisible,contenido,referencia,esEntrada,id_monitor,d,id_dinamica,id_escalaHecha);
					arrEh.add(eHecha);
					
				}
				rs.close();
				st.close();
				conex.close();
	        }catch(SQLException  e){
	        	e.printStackTrace();
	        }	
		
		
		return arrEh;
	} 
	
	//cargar las escalas por terminar segun el monitor / VerticalLayoutContinuarEscala()
	public static void listarEscalasPorTerminarSegunMonitor(String idMonitor){
			ClaseObjStaticas.arrPVYEscalasPorTerminar=new ArrayList<>();		
		try{				       			
			Connection conex = DriverManager.getConnection(url,"","");
			Statement st=conex.createStatement();
			ResultSet rs=st.executeQuery("select distinct id_escala,id_personaValorada from EscalaHecha "
					+ " where id_monitor=\""+idMonitor+"\" AND Terminado=false ");
			
			while(rs.next()){	
				System.out.println("escalas pendientes ++");
				String[] cap=new String[3];
				cap[0]=String.valueOf(rs.getInt(1));
				cap[1]=String.valueOf(rs.getInt(2));
				ClaseObjStaticas.arrPVYEscalasPorTerminar.add(cap);
				
			}
			rs.close();				
	    	st.close();
	    	conex.close();
	    }catch(SQLException  e){
	    	e.printStackTrace();
	    }
		
	}
	
	//Listar las personasValoradas de la query listarEscalasPorTerminarSegunMonitor
	public static void listarPvaloradasEscalasNoTerminadas(){
		try{				       			
			Connection conex = DriverManager.getConnection(urlFT,"","");
			Statement st=conex.createStatement();
			
				for(String[] s:ClaseObjStaticas.arrPVYEscalasPorTerminar){
					
					ResultSet rs=st.executeQuery("select usuari from 20_ll_USUARIS_GRUP_DINAMICA "
							+ " where Id="+s[1]+"");
					
					while(rs.next()){	
						s[2]=rs.getString(1);
					}
					rs.close();				
				}			
	    	st.close();
	    	conex.close();
	    }catch(SQLException  e){
	    	e.printStackTrace();
	    }		
	}
	
	//LayoutEscalaParaHacer / Buscamos si la personaValorada ya tiene escalas hechas o no ,y segun el resultado le asignamos una ID a las lineas de la escala que acaba de realizar
	public static int asignarIdAEscalaHecha(int idE,int idPv){
		int id=0;
		 try{		
				Connection conex = DriverManager.getConnection(url,"","");
	        	// idEscala =1  por ejemplo
				Statement st=conex.createStatement();
				String query="select id_escalaHecha from EscalaHecha where id_personaValorada="+idPv+" AND id_escala="+idE+" order by id_escalaHecha DESC limit 1";
				ResultSet rs=st.executeQuery(query);	
				//if que comprueba si la pv tiene escalas 
				if(!rs.next()){
					return 1;
				}else{
					while(rs.next()){					
						id=rs.getInt(1);
					}
				}				
				conex.close();
	        }catch(SQLException  e){
	        	e.printStackTrace();
	        }
			return id+1;		
	}
	
	//Da por finalizada una escala que estaba en modo finalizada=false
	public static void actualizarEscalaPorFinalizar(ArrayList<EscalaHecha> arrEh){
		//+++
		 try{				
				Connection conex = DriverManager.getConnection(url,"","");
				Statement st=conex.createStatement();
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				
				//actualiza el array de escalaHecha que aun no habia sido finalizada
				//añadir -> id_monitor=\""+e.getId_monitor()+"\",  ,en el caso de que lo hagan 2 monitores 
				for(EscalaHecha e:arrEh){
					System.out.println("***> "+e.getContenido());
					String update="UPDATE EscalaHecha SET contenido=\""+e.getContenido()+"\",fechaRealizacionEscala='"+timestamp+"',Terminado=true "
							+ " WHERE id_escala ="+e.getId_escala()+" AND id_lineaEscala="+e.getId_lineaEscala()+" AND id_personaValorada="+e.getId_usuario()+" AND id_escalaHecha="+e.getId_escalaHecha()+"";
					st.executeUpdate(update);					
				}			
			
				st.close();
				conex.close();
	        }catch(SQLException  e){
	        	e.printStackTrace();
	        }		
	} 
	
	
	// Listas /linea listas
	//carga las listas que hayan en la BBDD ordenadas por nombre y posicion de las lineas
	public static ArrayList<TipoLista> cargarListas(){
		ArrayList<TipoLista> arrTL=new ArrayList<>();
		 try{		
				Connection conex = DriverManager.getConnection(url,"","");
				Statement st=conex.createStatement();
				String query="select ll.nombre_lista,idLineaLista,posicion_lineaLista,texto_lineaLista,valor from LineaLista ll,Listas li where ll.nombre_lista=li.nombre_listas order by nombre_lista,posicion_lineaLista";
				ResultSet rs=st.executeQuery(query);				
				
				while(rs.next()){
					TipoLista nLista=new TipoLista(rs.getString(1));
					//a linealista no le damos el nombre de lista +++
					if(arrTL.contains(nLista)){
						LineaLista nLineaLista=new LineaLista(rs.getInt(2),rs.getInt(3),rs.getString(4),rs.getInt(5));
						arrTL.get(arrTL.indexOf(nLista)).addLineaLista(nLineaLista);
					}else{
						LineaLista nLineaLista=new LineaLista(rs.getInt(2),rs.getInt(3),rs.getString(4),rs.getInt(5));
						nLista.addLineaLista(nLineaLista);

						arrTL.add(nLista);
					}					
				}	
				rs.close();
				st.close();				
				conex.close();
	        }catch(SQLException  e){
	        	e.printStackTrace();
	        }	
		 
		return arrTL;		
	}	
	
	//insertamos la nueva lista creada
	public static void insertListas(TipoLista tipoLista){
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());		
		
		try {
			Connection conex = DriverManager.getConnection(url,"","");
			try (PreparedStatement ps = conex.prepareStatement(
			        "INSERT INTO Listas " +
			        "(nombre_listas) " +
			        "VALUES " +
			        "(?)"
			        )) {
			    ps.setString(1, tipoLista.getNombreLista());			    
			    ps.executeUpdate();
			}

			//!!! en el caso de que se pueda guardar una lista con solo el nombre ,hacer el if de array vacia 
			//inserta las lineas de la lista recibida
			for(int i=0;i<tipoLista.getArrTL().size();i++){
				try (PreparedStatement ps = conex.prepareStatement(
				        "INSERT INTO LineaLista " +
				        "(nombre_lista,idLineaLista,posicion_lineaLista,texto_lineaLista,valor) " +
				        "VALUES " +
				        "(?,?,?,?,?)"
				        )) {
				    ps.setString(1, tipoLista.getArrTL().get(i).getNombreLista());//tipoLista.getArrTL().get(i)
				    ps.setInt(2, tipoLista.getArrTL().get(i).getIdLineaLista());
				    ps.setInt(3, tipoLista.getArrTL().get(i).getPos());
				    ps.setString(4, tipoLista.getArrTL().get(i).getTextoLinea());
				    ps.setInt(5, tipoLista.getArrTL().get(i).getValor());

				    ps.executeUpdate();			    
				}	
			}
				
			conex.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	//cargamos las lineas de una lista / al cargar todas las lineas de una escala
	public static ArrayList<LineaLista> cargarLista(String nombreLista){
		ArrayList<LineaLista> arrLista=new ArrayList<>();
		 try{
			Connection conex = DriverManager.getConnection(url,"","");
			Statement st=conex.createStatement();
			String query="select texto_lineaLista,valor from LineaLista ll,Listas li where li.nombre_listas=\""+nombreLista+"\" AND ll.nombre_lista=li.nombre_listas order by posicion_lineaLista";
			ResultSet rs=st.executeQuery(query);				
			
			while(rs.next()){					
				LineaLista linea=new LineaLista(rs.getString(1),rs.getInt(2));
				arrLista.add(linea);				
			}	
			rs.close();
			st.close();				
			conex.close();
	    }catch(SQLException  e){
	    	e.printStackTrace();
	    }		
		
		
		return arrLista;
	}
	
	//
	public static void actualizarNombreLista(String antiguoNombre,String nuevoNombre){
		
		 try{	     			 
				
				Connection conex = DriverManager.getConnection(url,"","");
	        	// idEscala =1  por ejemplo
				Statement st=conex.createStatement();
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());				
			
				String update="UPDATE Listas SET nombre_listas='"+nuevoNombre+"'  WHERE nombre_listas='"+antiguoNombre+"'";
				String update2="UPDATE LineaLista SET nombre_lista='"+nuevoNombre+"'  WHERE nombre_lista='"+antiguoNombre+"'";

				st.executeUpdate(update);
				st.executeUpdate(update2);
			
				st.close();
				conex.close();
	        }catch(SQLException  e){
	        	e.printStackTrace();
	        }
	}
	
	
	
	//no se usa 
	public static void cerrarConexion(){
    	try {
			cnx.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}
	
    
    /**********************************************************/
    
}
