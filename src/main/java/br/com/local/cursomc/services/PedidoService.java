package br.com.local.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.local.cursomc.domain.ItemPedido;
import br.com.local.cursomc.domain.PagamentoComBoleto;
import br.com.local.cursomc.domain.Pedido;
import br.com.local.cursomc.domain.enuns.EstadoPagamento;
import br.com.local.cursomc.repositories.ItemPedidoRepository;
import br.com.local.cursomc.repositories.PagamentoRepository;
import br.com.local.cursomc.repositories.PedidoRepository;
import br.com.local.cursomc.repositories.ProdutoRepository;
import br.com.local.cursomc.services.exceptions.ObjectNotFoundException;
@Service
public class PedidoService {
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private EmailService emailService;
		
		public Pedido find(Integer id) {
			Optional<Pedido> obj = pedidoRepository.findById(id);
			return obj.orElseThrow(() -> new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
		}
		
		@Transactional
		public Pedido insert(Pedido pedido) {
			pedido.setId(null);
			pedido.setInstante(new Date());
			pedido.setCliente(clienteService.find(pedido.getCliente().getId()));
			pedido.getPagamento().setEstadoPagamento(EstadoPagamento.PENDENTE);
			pedido.getPagamento().setPedido(pedido);
			if(pedido.getPagamento() instanceof PagamentoComBoleto) {
				PagamentoComBoleto pagamentoComBoleto = (PagamentoComBoleto) pedido.getPagamento();
				boletoService.preencherPagamentoComBoleto(pagamentoComBoleto, pedido.getInstante());
			}
			pedido = pedidoRepository.save(pedido);
			pagamentoRepository.save(pedido.getPagamento());
			
			for (ItemPedido itemPedido : pedido.getItemPedidos()) {
				itemPedido.setDesconto(0.0);
				itemPedido.setProduto(produtoService.find(itemPedido.getProduto().getId()));
				itemPedido.setPreco(itemPedido.getProduto().getPreco());
				itemPedido.setPedido(pedido);
			}
			 itemPedidoRepository.saveAll(pedido.getItemPedidos());
			 emailService.sendOrderConfirmationEmail(pedido);
			// System.out.println(pedido);
			return pedido;
		}
}
