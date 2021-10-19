package br.com.wjrlabs.messages;

import br.com.wjrlabs.messages.protocol.echo.MessageEcho;
import br.com.wjrlabs.messages.protocol.nack.MessageNack;
import br.com.wjrlabs.util.ByteHelper;
import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageFactory {

	public static Message newInstance(ByteBuf in) {
		
		byte[] readableBytes =ByteHelper.getReadableBytes(in);
		
		if (readableBytes==null) {
			return new MessageNack();

		}
		MessageType messageType= MessageType.valueOf(readableBytes[0]);
		if(messageType==null) {
			return new MessageNack();
		}
		switch (MessageType.valueOf(readableBytes[0])) {
		case ECHO:
			log.debug("MessageType ECHO");
			return new MessageEcho(readableBytes);
			
		case NACK:
			log.debug("MessageType NACK");
			return new MessageNack();
			
		default:
			return new MessageNack();
		}
	}
	

}
