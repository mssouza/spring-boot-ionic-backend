package br.com.local.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import br.com.local.cursomc.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido pedido);
	void sendEmail(SimpleMailMessage msg);
}
