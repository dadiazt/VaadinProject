package clases;

public class TipoFormula {
	
	int codi_formula=0;
	String formula="";
	String textoAlUsuario="";
	public int getCodi_formula() {
		return codi_formula;
	}
	public void setCodi_formula(int codi_formula) {
		this.codi_formula = codi_formula;
	}
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
	}
	public String getTextoAlUsuario() {
		return textoAlUsuario;
	}
	public void setTextoAlUsuario(String textoAlUsuario) {
		this.textoAlUsuario = textoAlUsuario;
	}
	@Override
	public String toString() {
		return "TipoFormula [codi_formula=" + codi_formula + ", formula=" + formula + ", textoAlUsuario="
				+ textoAlUsuario + "]";
	}
	public TipoFormula(int codi_formula, String formula, String textoAlUsuario) {
		super();
		this.codi_formula = codi_formula;
		this.formula = formula;
		this.textoAlUsuario = textoAlUsuario;
	}
	
	public TipoFormula(){}

}
