package br.com.wjrlabs.codecs;

import java.util.List;

import br.com.wjrlabs.exceptions.MessageRuntimeException;
import br.com.wjrlabs.messages.Message;
import br.com.wjrlabs.messages.MessageFactory;
import br.com.wjrlabs.messages.protocol.nack.MessageNack;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

@Sharable
@Slf4j
public class MessageEncoder extends MessageToMessageDecoder<ByteBuf> {
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg,List<Object> out) throws Exception {
		Message message = null;
		try {
			message = MessageFactory.newInstance(msg);
			log.debug("MessageEncoder - Class {}",	message.getClass().getName());
		} catch (Exception e) {
			out.add(new MessageNack());
			throw new MessageRuntimeException(e);
		}
		out.add(message);
	}

}
