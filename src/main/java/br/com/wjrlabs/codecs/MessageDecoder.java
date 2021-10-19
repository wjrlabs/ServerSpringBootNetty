package br.com.wjrlabs.codecs;

import br.com.wjrlabs.messages.Message;
import br.com.wjrlabs.util.ByteHelper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

@Sharable
@Slf4j
public class MessageDecoder extends MessageToByteEncoder<Message> {
	
	@Override
	protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out)	throws Exception {
		log.debug("MessageDecoder - Class {}", msg.getClass().getName());
		log.debug("Message to encode --> {}",ByteHelper.hexify(msg.encode()));
		out.writeBytes(msg.encode());

	}

}
