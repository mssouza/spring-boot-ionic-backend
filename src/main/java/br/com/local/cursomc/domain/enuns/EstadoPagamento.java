package br.com.local.cursomc.domain.enuns;

public enum EstadoPagamento {

	PENDENTE(1,"Pendente"),
	QUITADO(2,"Quitado"),
	CANCELADO(3,"Cancelado");
	
	private int codigo;
	private String descricao;
	
	private EstadoPagamento(int codigo, String descricao) {
		this.codigo=codigo;
		this.descricao=descricao;
	}
	
	public int getCodico() {
		return codigo;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public static EstadoPagamento toEnum(Integer codigo) {
		
		
		if(codigo==null) {
			return null;
		}
		
		for (EstadoPagamento estadoPagamento : EstadoPagamento.values()) {
			if(codigo.equals(estadoPagamento.getCodico())) {
				return estadoPagamento;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: "+codigo);
	}
}
