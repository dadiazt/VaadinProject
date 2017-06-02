package validacion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class validacionGeneral {
	
	// se pasa una fecha como estring y se valida. 
	// la fecha debe ser posterior a 1900
	public boolean esFecha(String fecha){
		
		int maxDias=0;
		
		String [] campos = fecha.split("/");
		int dia=Integer.parseInt(campos[0]);
		int mes=Integer.parseInt(campos[1]);
		int anyo = Integer.parseInt(campos[2]);
		
        try{
            if (anyo < 1900) { return false; }

            Calendar calendar = Calendar.getInstance();
            calendar.setLenient(false);
        	calendar.set(Calendar.YEAR, anyo);
        	calendar.set(Calendar.MONTH, mes - 1); // [0,...,11]
        	calendar.set(Calendar.DAY_OF_MONTH, dia);
            Date date = calendar.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            fecha=sdf.format(date); // 01/01/2016
            return true;
        }catch(IllegalArgumentException e){
        	return false;
        }
		
	}
	
	// recibimos un date y retornamos una fecha con el formato dd/mm/yyyy
	public Date dameFechaDMY(Date fecha){
		
		Date date=null;
		
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String fecha2=sdf.format(fecha); // 01/01/2016
            date=sdf.parse(fecha2);
        }catch(IllegalArgumentException e){
        	
        }catch(ParseException e){
        	
        }
        return date;
	}
	
	// comprueba que un numero esta detnro de un margen y retorna true o false
	public boolean validarEntero(int min, int max, int num){
		
		if(min<=num && num<=max) return true;
		else return false;
		
	}
	
	// comprueba que un numero contenido en un string
	// esta detnro de un margen y retorna true o false
	public boolean validarStringEntero(int min, int max, String num){
		
		int numero=0;
		
		try{
			numero=Integer.parseInt(num);
			return validarEntero(min,max,numero);
		}catch(Exception e){
			return false;
		}
			
	}
	
	// convierte una fecha a un string
	public String dameFechaCad(Date fecha){
		
		SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
		return formatoDeFecha.format(fecha);
		
	}
	
	public String dameFechaActualCadena(){
		
		Calendar calendario = GregorianCalendar.getInstance();
		Date fecha = calendario.getTime();
		SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
		return formatoDeFecha.format(fecha);
		
	}

}
