package br.com.wjrlabs.codecs;

import java.util.List;

import org.springframework.stereotype.Component;

import br.com.wjrlabs.messages.Message;
import br.com.wjrlabs.messages.MessageFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Sharable
@Slf4j
@AllArgsConstructor
public class MessageDecoder extends MessageToMessageDecoder<ByteBuf> {
	
	private final MessageFactory messageFactory;
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg,List<Object> out) throws Exception {
		
		Message message = null;
		message = messageFactory.getMessage(msg);
		log.debug("MessageEncoder - Class {}",	message.getClass().getName());
		out.add(message);
	}

}
