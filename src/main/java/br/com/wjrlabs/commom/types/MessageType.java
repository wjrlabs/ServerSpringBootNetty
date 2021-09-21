package br.com.wjrlabs.commom.types;

import java.text.MessageFormat;

import br.com.wjrlabs.commom.exceptions.InternalRuntimeException;
import lombok.Getter;


@Getter
public enum MessageType {
	/**
	 * Login message
	 */
	LOGIN(0x01);
	

	private byte value;
	

	private MessageType(int value) {
		this.value = (byte) value;
	}
	
	

	public static MessageType valueOf(byte value) {
		MessageType result = null;
		for (MessageType item : MessageType.values()) {
			if (item.getValue() == value) {
				result = item;
				break;
			}
		}
		if (result == null) {
			throw new InternalRuntimeException(
					MessageFormat.format("Value {0} cannot be translate to {1}.", 
							value, MessageType.class.getName()));
		}
		return result;
	}
}
