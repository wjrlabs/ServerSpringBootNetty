package br.com.wjrlabs.commom;

import org.springframework.boot.web.servlet.server.Session;

/**
 * Contrato para informar o interesse e executar o processmento de uma {@link Message}.
 *
 * 
 * @param <E> Uma classe de {@link Message}
 * @param <U> Uma classe de {@link Session}
 */
public interface Command<E extends Message, U extends Session> {

	/**
	 * Recupera a chave de um tipo de {@link Message} para informar o interesse
	 * de processamento.
	 * @return Chave de tipo de Mensagem
	 */
	public abstract MessageKey getInterest();

	/**
	 * Método de processamento para o tipo de mensagem especificado no interesse
	 * da classe.
	 * @param message {@link Message} para ser processada, conforme o interesse informado.
	 * @param session {@link Session} Sessão do objeto.
	 * @return Lista de {@link Message}
	 */
	public abstract Message[] execute(E message, U session);
}