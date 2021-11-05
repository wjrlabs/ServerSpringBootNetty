package br.com.wjrlabs.messages;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.wjrlabs.messages.common.MessageType;
import br.com.wjrlabs.messages.protocol.echo.MessageEcho;
import br.com.wjrlabs.messages.protocol.nack.MessageNack;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import lombok.extern.slf4j.Slf4j;

@Component
@Scope("singleton")
@Slf4j
public class MessageFactory {

	private final AutowireCapableBeanFactory beanFactory;
	
	public MessageFactory(AutowireCapableBeanFactory beanFactory ) {
		this.beanFactory=beanFactory;
	}
	 
	public Message getMessage(ByteBuf in) {
		
		byte[] readableBytes =ByteBufUtil.getBytes(in);
		
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
			MessageEcho messageEcho= new MessageEcho(readableBytes);
			beanFactory.autowireBean(messageEcho);
			return messageEcho;
			
		case NACK:
			log.debug("MessageType ECHO");
			MessageNack messageNack= new MessageNack();
			beanFactory.autowireBean(messageNack);
			return messageNack;
			
		default:
			return new MessageNack();
		}
	}
	

}
