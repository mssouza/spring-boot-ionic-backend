package br.com.local.cursomc.services;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.local.cursomc.domain.Categoria;
import br.com.local.cursomc.domain.Cliente;
import br.com.local.cursomc.dto.CategoriaDTO;
import br.com.local.cursomc.repositories.CategoriaRepository;
import br.com.local.cursomc.services.exceptions.DataIntegrityException;
import br.com.local.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	@Autowired
	private CategoriaRepository categoriaRepository;
		
		public Categoria find(Integer id) {
			Optional<Categoria> obj = categoriaRepository.findById(id);
			return obj.orElseThrow(() -> new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
		}
		
		public Categoria insert(Categoria categoria) {
			categoria.setId(null);
			return 	categoria =	 categoriaRepository.save(categoria);
		}
		
		public Categoria update(Categoria categoria) {
			Categoria newCategoria = find(categoria.getId());
			updateData(newCategoria,categoria);
			return 	categoria =	 categoriaRepository.save(categoria);
		}
		
		public void delete(Integer id) {
			find(id);
			try {
				categoriaRepository.deleteById(id);
			} catch (DataIntegrityViolationException e) {
				throw new DataIntegrityException("Não é possivel excluir uma categoria que possui produtos");
			}
			
		}
		
		public List<Categoria> findAll(){
			return categoriaRepository.findAll();
		}
		
		public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
			PageRequest pageRequest = PageRequest.of(page,linesPerPage, Direction.valueOf(direction),orderBy);
			return categoriaRepository.findAll(pageRequest);
		}
		
		public Categoria fromDTO(CategoriaDTO categoriaDTO) {
			return new Categoria(categoriaDTO.getId(), categoriaDTO.getNome());
		}
		
		public Categoria updateData(Categoria newCategoria, Categoria categoria) {
			newCategoria.setNome(categoria.getNome());
			return newCategoria;
		}
}
