package br.com.wjrlabs.core;

import java.nio.ByteBuffer;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import br.com.wjrlabs.commom.exceptions.MessageRuntimeException;
import br.com.wjrlabs.commom.types.MessageType;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Wagner Alves
 * Message Factory, identified by key {@link MessageKey}.
 *
 */
@Slf4j
public abstract class MessageFactory {

	private static final Map<MessageKey, Class<? extends Message>> keys = new HashMap<MessageKey, Class<? extends Message>>();
	
	public static void register(MessageKey key, Class<? extends Message> clazz) {
		if (keys.containsKey(key)) {
			String message = MessageFormat.format("Chave \"{0}\" já está registrada.", key.toString());
			log.error(message);
			throw new MessageRuntimeException(message);
		}
		if (log.isTraceEnabled()) {
			log.trace(MessageFormat.format("Registrando a chave \"{0}\" com a classe \"{1}\".", key.toString(), clazz.getName()));
		}
		keys.put(key, clazz);
	}
	
	public static void register(Class<? extends Message> clazz) {
		Message message;
		try {
			message = clazz.newInstance();
			MessageFactory.register(message.getKey(), message.getClass());
		} catch (Exception e) {
			String error = "Erro ao registrar a mensagem.";
			log.error(error, e);
			throw new MessageRuntimeException(error, e);
		}
	}
	
	public static Message newInstance(byte[] buffer) {
		return MessageFactory.newInstance(buffer, true);
	}
	
	public static Message newInstance(byte[] buffer, boolean decode) {
		if ((buffer == null) || (buffer.length < MessageKey.LENGTH)) {
			throw new MessageRuntimeException("Tamanho do buffer inválido para encontrar uma Mensagem.");
		}
		Message result 		= null;
		ByteBuffer readable = ByteBuffer.wrap(buffer, 0, MessageKey.LENGTH);
		MessageType type	= MessageType.valueOf(readable.get());
		byte version		= readable.get();
		MessageKey key 		= new MessageKey(type, version);
		
		if (log.isTraceEnabled()) {
			log.trace(MessageFormat.format("Encontrada a chave {0}.", key.toString()));
		}
		
		if (keys.containsKey(key)) {
			Class<? extends Message> clazz = keys.get(key);
			try {
				result = clazz.newInstance();
				if (decode) {
					result.decode(buffer);
				}
			} catch (InstantiationException e) {
				String message = MessageFormat.format("Classe {0} não pode ser inicializada.", clazz.getName());
				log.error(message, e);
				throw new MessageRuntimeException(message, e);
			} catch (IllegalAccessException e) {
				String message = MessageFormat.format("Classe {0} não possui o formato correto.", clazz.getName());
				log.error(message, e);
				throw new MessageRuntimeException(message, e);
			}
			
			if (log.isTraceEnabled()) {
				log.trace(MessageFormat.format("Mensagem fabricada: {0}.", result.toString()));
			}
		}
		
		return result;
	}
}
