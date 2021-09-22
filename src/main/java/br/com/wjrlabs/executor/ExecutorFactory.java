package br.com.wjrlabs.executor;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import br.com.wjrlabs.commom.exceptions.CommandRuntimeException;
import br.com.wjrlabs.commom.session.DeviceSession;
import br.com.wjrlabs.core.Message;
import br.com.wjrlabs.core.MessageKey;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Wagner Alves
 * Executor Factory to {@link MessageKey}.
 */
@Slf4j
public class ExecutorFactory {

	private static final Map<MessageKey, Executor<?,?>> keys = new HashMap<MessageKey, Executor<?,?>>();

	public static void register(MessageKey key, Executor<?,?> command) {
		if (keys.containsKey(key)) {
			String message = MessageFormat.format("Chave \"{0}\" já está registrada.", key.toString());
			log.error(message);
			throw new CommandRuntimeException(message);
		}
		if (log.isDebugEnabled()) {
			log.debug(MessageFormat.format("Registrando a chave \"{0}\" com o comando \"{1}\".", key.toString(), command));
		}
		keys.put(key, command);		
	}
	
	public static void register(Class<? extends Executor<?,?>> clazz) {
		try {
			Executor<?,?> command = clazz.newInstance();
			register(command.getInterest(), command);
		} catch (Exception e) {
			String error = "Erro ao registrar um Command.";
			log.error(error, e);
			throw new CommandRuntimeException(error, e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <E extends Message, U extends DeviceSession> Executor<E,U> getInstance(E message) {
		if (message == null) {
			throw new CommandRuntimeException("Não foi informada uma mensagem...");
		}
		Executor<E,U> result = (Executor<E,U>) keys.get(message.getKey());
		if (log.isTraceEnabled()) {
			log.trace(MessageFormat.format("Recuperado o Command: {0}.", result));
		}
		return result;
	}
}
