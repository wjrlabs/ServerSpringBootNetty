package br.com.wjrlabs.messages;

import java.io.Serializable;
import java.text.MessageFormat;

import br.com.wjrlabs.exceptions.MessageRuntimeException;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public abstract class SimpleMessage implements Message,Serializable {

	private static final long serialVersionUID = -947368175278640115L;

	protected MessageType type;
	
	protected byte[] message;
	
	protected byte protocolVersion;
	
		@Override
	public abstract byte[] encode();
	
	@Override
	public abstract void decode(byte[] buffer);
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
	
		result.append(this.getClass().getName());
		result.append("[");
		result.append("type=").append(type.toString());
		result.append("]");
		
		return result.toString();
	}
	
	
	public ByteBuf validateMinimumBuffer(byte[] buffer,	int tamanhoRequest, Class<? extends Message> clss) {
		Integer bufferSize = buffer!=null?buffer.length:null;
		if(bufferSize==null || bufferSize<tamanhoRequest) {
			throw new MessageRuntimeException(MessageFormat.format("Buffer inválido na mensagem {0}. Esperava {1}, recebeu {2}",clss.getName(),tamanhoRequest,bufferSize));
		}
		return Unpooled.copiedBuffer(buffer);
	}
}
