package br.com.local.cursomc.domain.enuns;

public enum Perfil {

	ADMIN(1,"ROLE_ADMIN"),
	CLIENTE(2,"ROLE_CLIENTE");	
	
	private int codigo;
	private String descricao;
	
	private Perfil(int codigo, String descricao) {
		this.codigo=codigo;
		this.descricao=descricao;
	}
	
	public int getCodico() {
		return codigo;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public static Perfil toEnum(Integer codigo) {
		
		
		if(codigo==null) {
			return null;
		}
		
		for (Perfil perfil : Perfil.values()) {
			if(codigo.equals(perfil.getCodico())) {
				return perfil;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: "+codigo);
	}
}
