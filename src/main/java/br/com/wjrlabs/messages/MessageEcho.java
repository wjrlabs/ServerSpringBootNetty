package br.com.wjrlabs.messages;

import java.nio.ByteBuffer;

import br.com.wjrlabs.commom.types.MessageType;
import br.com.wjrlabs.core.MessageKey;
import br.com.wjrlabs.core.SimpleMessage;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Wagner Alves
 */
@Getter
@Setter
public class MessageEcho extends SimpleMessage {

	private static final long serialVersionUID = 7480703354066580006L;

	public static final MessageType TYPE = MessageType.ECHO;
	
	public static final int LENGTH = MessageKey.LENGTH + 12;
	

	public MessageEcho() {
		super(new MessageKey(TYPE, VERSION));
	}

	@Override
	public byte[] encode() {
		ByteBuffer buffer = ByteBuffer.allocate(LENGTH);
		buffer.put(getKey().encode());
		buffer.flip();
		return buffer.array();
	}

	@Override
	public void decode(byte[] buffer) {
		ByteBuffer readable = validateMinimumBuffer(buffer, LENGTH);
		
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append(this.getClass().getName());
		builder.append("[");
		builder.append("]");

		return builder.toString();
	}

}
