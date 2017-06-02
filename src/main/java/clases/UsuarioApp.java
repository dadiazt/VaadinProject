package clases;



public class UsuarioApp {
	
	private String nomUsuario;//es el id
	private boolean esActivo;
	private int tipo;
	
	public UsuarioApp(){ }
	

	//constructor para guardar en array desde la bbdd
	public UsuarioApp(String nomUsuario, boolean esActivo, int tipo) {
		super();
		this.nomUsuario = nomUsuario;
		this.esActivo = esActivo;
		this.tipo = tipo;
	}

	public UsuarioApp(String nomUsuario, int tipo) {
		this.nomUsuario = nomUsuario;
		this.tipo = tipo;
	}

	//constructor para el indexof
	public UsuarioApp(String nomUsuario) {
		this.nomUsuario = nomUsuario;
	}
	
	public UsuarioApp(String nomUsuario,int t,String clave) {
		this.nomUsuario = nomUsuario;
		this.tipo=t;
		
	}

	public String getNomUsuario() {
		return nomUsuario;
	}

	public void setNomUsuario(String nomUsuario) {
		this.nomUsuario = nomUsuario;
	}

	public boolean isEsActivo() {
		return esActivo;
	}

	public void setEsActivo(boolean esActivo) {
		this.esActivo = esActivo;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return "UsuarioApp [nomUsuario=" + nomUsuario + ", esActivo=" + esActivo + ", tipo=" + tipo + "]";
	}

	
	   @Override
	    public boolean equals(Object object)
	    {
	        boolean sameSame = false;

	        if (object != null && object instanceof UsuarioApp)
	        {
	            sameSame = this.nomUsuario.equals(((UsuarioApp) object).nomUsuario);
	        }

	        return sameSame;
	    }
	
}
