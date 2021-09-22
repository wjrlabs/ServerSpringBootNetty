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
			String message = MessageFormat.format("Key \"{0}\" already registered.", key.toString());
			log.error(message);
			throw new MessageRuntimeException(message);
		}
		if (log.isDebugEnabled()) {
			log.debug("Key \"{}\" to Message \"{}\".", key.toString(), clazz.getName());
		}
		keys.put(key, clazz);
	}
	
	public static void register(Class<? extends Message> clazz) {
		Message message;
		try {
			message = clazz.newInstance();
			MessageFactory.register(message.getKey(), message.getClass());
		} catch (Exception e) {
			String error = "Error registering message";
			log.error(error, e);
			throw new MessageRuntimeException(error, e);
		}
	}
	
	public static Message newInstance(byte[] buffer) {
		return MessageFactory.newInstance(buffer, true);
	}
	
	public static Message newInstance(byte[] buffer, boolean decode) {
		if ((buffer == null) || (buffer.length < MessageKey.LENGTH)) {
			throw new MessageRuntimeException("Invalid buffer - message cannot be found");
		}
		Message result 		= null;
		ByteBuffer readable = ByteBuffer.wrap(buffer, 0, MessageKey.LENGTH);
		MessageType type	= MessageType.valueOf(readable.get());
		byte version		= readable.get();
		MessageKey key 		= new MessageKey(type, version);
		
		if (log.isTraceEnabled()) {
			log.trace("key found {}.", key.toString());
		}
		
		if (keys.containsKey(key)) {
			Class<? extends Message> clazz = keys.get(key);
			try {
				result = clazz.newInstance();
				if (decode) {
					result.decode(buffer);
				}
			} catch (InstantiationException e) {
				String message = MessageFormat.format("Class {0} cannot be initialized.", clazz.getName());
				log.error(message, e);
				throw new MessageRuntimeException(message, e);
			} catch (IllegalAccessException e) {
				String message = MessageFormat.format("Class {0} - invalid format", clazz.getName());
				log.error(message, e);
				throw new MessageRuntimeException(message, e);
			}
			
			if (log.isTraceEnabled()) {
				log.trace("Message assembled: {}.", result.toString());
			}
		}
		
		return result;
	}
}
