package br.com.wjrlabs.messages.protocol.nack;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import br.com.wjrlabs.messages.common.MessageType;
import br.com.wjrlabs.messages.protocol.SimpleMessage;
import lombok.Getter;

@Component
@Getter
public class MessageNack extends SimpleMessage implements Serializable {

	public MessageNack() {
		this.type = MessageType.NACK;
	}
	private static final long serialVersionUID = 9148098646537480364L;

	private static final byte RETORNO = MessageType.NACK.getResponseId();

	@Override
	public byte[] encode() {
		return new byte[]{RETORNO};
	}

	@Override
	public void decode(byte[] buffer) {
	}

}
