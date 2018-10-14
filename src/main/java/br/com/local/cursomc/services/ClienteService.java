package br.com.local.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.local.cursomc.domain.Categoria;
import br.com.local.cursomc.domain.Cidade;
import br.com.local.cursomc.domain.Cliente;
import br.com.local.cursomc.domain.Endereco;
import br.com.local.cursomc.domain.enuns.Perfil;
import br.com.local.cursomc.domain.enuns.TipoCliente;
import br.com.local.cursomc.dto.ClienteDTO;
import br.com.local.cursomc.dto.ClienteNewDTO;
import br.com.local.cursomc.repositories.CidadeRepository;
import br.com.local.cursomc.repositories.ClienteRepository;
import br.com.local.cursomc.repositories.EnderecoRepository;
import br.com.local.cursomc.security.UserSS;
import br.com.local.cursomc.services.exceptions.AuthorizationException;
import br.com.local.cursomc.services.exceptions.DataIntegrityException;
import br.com.local.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
		
		
		public Cliente find(Integer id) {
			UserSS user = UserService.authenticated();
			
			if(user == null || ! user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
				throw new AuthorizationException("Acesso Negado");
			}
			
			Optional<Cliente> obj = clienteRepository.findById(id);
			return obj.orElseThrow(() -> new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
		}
		
		@Transactional
		public Cliente insert(Cliente cliente) {
			cliente.setId(null);
				cliente = clienteRepository.save(cliente);
				enderecoRepository.saveAll(cliente.getEnderecos());
				return cliente;
		}
		
		public Cliente update(Cliente cliente) {
			Cliente newCliente = find(cliente.getId());
			updateData(newCliente,cliente);
			return 	cliente =	 clienteRepository.save(cliente);
		}
		
		public void delete(Integer id) {
			find(id);
			try {
				clienteRepository.deleteById(id);
			} catch (DataIntegrityViolationException e) {
				throw new DataIntegrityException("Não é possivel excluir porque há entidades relacionadas");
			}
			
		}
		
		public List<Cliente> findAll(){
			return clienteRepository.findAll();
		}
		
		public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
			PageRequest pageRequest = PageRequest.of(page,linesPerPage, Direction.valueOf(direction),orderBy);
			return clienteRepository.findAll(pageRequest);
		}
		
		public Cliente fromDTO(ClienteDTO clienteDTO) {
			return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null,null);
		}
		
		public Cliente fromDTO(ClienteNewDTO clienteDTO) {
			Cliente cliente =  new Cliente(null, clienteDTO.getNome(), clienteDTO.getEmail(), clienteDTO.getCpfOuCnpj(),TipoCliente.toEnum(clienteDTO.getCidadeId()), passwordEncoder.encode(clienteDTO.getSenha()));
			Cidade cidade = new Cidade(clienteDTO.getCidadeId(), null, null);
			Endereco endereco = new Endereco(null, clienteDTO.getLogradouro(), clienteDTO.getNumero(), clienteDTO.getComplemento(), clienteDTO.getBairro() , clienteDTO.getCep(),cliente, cidade);
			cliente.getEnderecos().add(endereco);
			cliente.getTelefones().add(clienteDTO.getTelefone1());
			
			if(clienteDTO.getTelefone2()!=null) {
				cliente.getTelefones().add(clienteDTO.getTelefone2());
			}
			
			if(clienteDTO.getTelefone3()!=null) {
				cliente.getTelefones().add(clienteDTO.getTelefone3());
			}
			
			return cliente;
		}
		
		public Cliente updateData(Cliente newCliente, Cliente cliente) {
			newCliente.setNome(cliente.getNome());
			newCliente.setEmail(cliente.getEmail());
			return newCliente;
		}
}

