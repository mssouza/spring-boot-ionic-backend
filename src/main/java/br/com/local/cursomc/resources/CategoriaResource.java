package br.com.local.cursomc.resources;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.local.cursomc.domain.Categoria;
import br.com.local.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService service;
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {
		
		Categoria categoria = service.find(id);
		return ResponseEntity.ok().body(categoria);
		
//		Categoria cat = new Categoria(1,"Informática");
//		Categoria cat2 = new Categoria(2,"Escritório");
//		List<Categoria> lista = new ArrayList<Categoria>();
//		lista.add(cat);
//		lista.add(cat2);
//		return lista;
	}
}
