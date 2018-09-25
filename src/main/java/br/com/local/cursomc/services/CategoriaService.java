package br.com.local.cursomc.services;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.local.cursomc.domain.Categoria;
import br.com.local.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaService {
	@Autowired
	private CategoriaRepository categoriaRepository;
		
		public Categoria find(Integer id) {
			Optional<Categoria> obj = categoriaRepository.findById(id);
			return obj.orElse(null);
		}
}
