package br.com.wjrlabs.messages.protocol.echo;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import br.com.wjrlabs.messages.common.MessageType;
import br.com.wjrlabs.messages.protocol.SimpleMessage;
import lombok.Getter;

@Component
@Getter
public class MessageEcho extends SimpleMessage implements Serializable{

	public MessageEcho(byte[] message) {
		this.message=message;
		this.type=MessageType.ECHO;
	}
	private static final long serialVersionUID = 9148098646537480364L;
	
	@Override
	public byte[] encode() {
		return this.message;
	}

	@Override
	public void decode(byte[] buffer) {
		this.message=buffer;
	}

}
