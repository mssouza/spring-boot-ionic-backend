package br.com.local.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.local.cursomc.domain.Cliente;
import br.com.local.cursomc.domain.enuns.TipoCliente;
import br.com.local.cursomc.dto.ClienteNewDTO;
import br.com.local.cursomc.repositories.ClienteRepository;
import br.com.local.cursomc.resources.exception.FieldMessage;
import br.com.local.cursomc.services.validation.utils.BR;;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO>{

@Autowired	
 private ClienteRepository clienteRepository;	
	
  @Override 
  public void initialize(ClienteInsert ann) {  
	  
  }   
	  
  @Override    
  public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) { 
	 
    List<FieldMessage> list = new ArrayList<>();
    // inclua os testes aqui, inserindo erros na lista
    
    if(objDto.getTipoCliente().equals(TipoCliente.PESSOAFISICA.getCodico())&& !BR.isValidCPF(objDto.getCpfOuCnpj())) {
    	list.add(new FieldMessage("cpfOuCnpj", "CPF Inválido"));
    }
    
    if(objDto.getTipoCliente().equals(TipoCliente.PESSOAJURIDICA.getCodico())&& !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
    	list.add(new FieldMessage("cpfOuCnpj", "CNPJ Inválido"));
    }
    
    Cliente cliente = clienteRepository.findByEmail(objDto.getEmail());
    if(cliente!=null) {
    	list.add(new FieldMessage("email", "Email já existente!"));
    }
    
        for (FieldMessage e : list) 
        {             
        	context.disableDefaultConstraintViolation(); 
        	context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName()).addConstraintViolation();     
        }       
        return list.isEmpty();  
    }
}
