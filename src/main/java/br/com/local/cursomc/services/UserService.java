package br.com.local.cursomc.services;

import org.springframework.security.core.context.SecurityContextHolder;

import br.com.local.cursomc.security.UserSS;

public class UserService {
	
	public static UserSS authenticated() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}

}
