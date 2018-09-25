package br.com.local.cursomc.domain;

import java.util.Date;

import javax.persistence.Entity;

import br.com.local.cursomc.domain.enuns.EstadoPagamento;
@Entity
public class PagamentoComBoleto extends Pagamento{

	private static final long serialVersionUID = 1L;
	private Date dataVencimento;
	
	private Date dataPagamento;
	
	public PagamentoComBoleto() {
		
	}

	public PagamentoComBoleto(Integer id, EstadoPagamento estadoPagamento, Pedido pedido,Date dateVenciento, Date dataPagamento) {
		super(id, estadoPagamento, pedido);
		this.dataPagamento=dataPagamento;
		this.dataVencimento=dateVenciento;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	
}
