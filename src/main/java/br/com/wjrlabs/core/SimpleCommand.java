package br.com.wjrlabs.core;

import java.text.MessageFormat;

import javax.naming.NamingException;

import org.springframework.boot.web.servlet.server.Session;

import br.com.wjrlabs.commom.exceptions.CommandRuntimeException;
import lombok.extern.slf4j.Slf4j;

/**
 * Classe para tratamento de mensagens recebidas pelo SCAP.
 * <p>
 * Cada mensagem recebida deverá ter uma classe de Comando associada.
 *

 */
@Slf4j
public abstract class SimpleCommand<E extends Message, U extends Session> implements Command<E,U>  {

	
	/**
	 * Registro da mensagem de interesse para processamento.
	 */
	private MessageKey interest;
	
	/**
	 * Gerenciamento de serviços do SCAP.
	 */
	//private ServiceLocator locator;
	
	/**
	 * Construtor padrão.
	 * @param clazz Mensagem de interesse de processamento.
	 */
	public SimpleCommand(Class<E> clazz) {
		try {
			Message message = clazz.newInstance();
			interest		= message.getKey();
			log.info(MessageFormat.format("Comando para processamento de {0}.", interest));
		} catch (Exception e) {
			String error = "Erro ao registrar a mensagem de interesse do comando.";
			log.error(error, e);
			throw new CommandRuntimeException(error, e);
		}
	//	this.locator	= ServiceLocator.getInstance();
	}

	/* (non-Javadoc)
	 * @see br.com.digicon.scap.comm.common.Command#getInterest()
	 */
	@Override
	public MessageKey getInterest() {
		return interest;
	}
	
	/**
	 * @param remoteClass
	 * @return Objeto de acesso remoto do SCAP.
	 * @throws NamingException
	 * @see br.com.digicon.scap.client.ServiceLocator#getService(java.lang.Class)
	 */
	public <T> T getService(Class<T> remoteClass) throws NamingException {
		return null;//locator.getService(remoteClass);
	}

	/* (non-Javadoc)
	 * @see br.com.digicon.scap.comm.common.Command#execute(E)
	 */
	@Override
	public abstract Message[] execute(E message, U session);
}
