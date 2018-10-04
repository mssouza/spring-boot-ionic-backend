package br.com.local.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.local.cursomc.domain.Cliente;
import br.com.local.cursomc.dto.ClienteDTO;
import br.com.local.cursomc.repositories.ClienteRepository;
import br.com.local.cursomc.services.exceptions.DataIntegrityException;
import br.com.local.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
		
		public Cliente find(Integer id) {
			Optional<Cliente> obj = clienteRepository.findById(id);
			return obj.orElseThrow(() -> new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
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
			return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null);
		}
		
		public Cliente updateData(Cliente newCliente, Cliente cliente) {
			newCliente.setNome(cliente.getNome());
			newCliente.setEmail(cliente.getEmail());
			return newCliente;
		}
}

