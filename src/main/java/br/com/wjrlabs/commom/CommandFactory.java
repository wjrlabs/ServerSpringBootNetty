package br.com.wjrlabs.commom;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.web.servlet.server.Session;

import br.com.wjrlabs.commom.exceptions.CommandRuntimeException;

/**
 * Fábrica de comandos para processamento da mensagem, baseado em {@link MessageKey}.
 *
 * 
 */
public class CommandFactory {

	/** Log da classe. */
	private static final Logger logger = Logger.getLogger(CommandFactory.class);
	
	/**
	 * Lista de comandos para processamento de mensagens.
	 */
	private static final Map<MessageKey, Command<?,?>> keys = new HashMap<MessageKey, Command<?,?>>();

	/**
	 * Registra um novo {@link Command} para a fábrica.
	 * @param key Chave de Interesse do comando.
	 * @param command {@link Command}
	 */
	public static void register(MessageKey key, Command<?,?> command) {
		if (keys.containsKey(key)) {
			String message = MessageFormat.format("Chave \"{0}\" já está registrada.", key.toString());
			logger.error(message);
			throw new CommandRuntimeException(message);
		}
		if (logger.isDebugEnabled()) {
			logger.debug(MessageFormat.format("Registrando a chave \"{0}\" com o comando \"{1}\".", key.toString(), command));
		}
		keys.put(key, command);		
	}
	
	/**
	 * Registra um {@link Command} para a fábrica.
	 * @param clazz Classe de {@link Command}.
	 */
	public static void register(Class<? extends Command<?,?>> clazz) {
		try {
			Command<?,?> command = clazz.newInstance();
			register(command.getInterest(), command);
		} catch (Exception e) {
			String error = "Erro ao registrar um Command.";
			logger.error(error, e);
			throw new CommandRuntimeException(error, e);
		}
	}
	
	/**
	 * Recupera o {@link Command} associado para processamento da {@link Message}
	 * @param message
	 * @return {@link Command}
	 */
	@SuppressWarnings("unchecked")
	public static <E extends Message, U extends Session> Command<E,U> getInstance(E message) {
		if (message == null) {
			throw new CommandRuntimeException("Não foi informada uma mensagem...");
		}
		Command<E,U> result = (Command<E,U>) keys.get(message.getKey());
		if (logger.isTraceEnabled()) {
			logger.trace(MessageFormat.format("Recuperado o Command: {0}.", result));
		}
		return result;
	}
}
