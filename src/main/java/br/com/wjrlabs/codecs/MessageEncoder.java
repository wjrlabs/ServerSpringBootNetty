package br.com.wjrlabs.codecs;

import org.springframework.stereotype.Component;

import br.com.wjrlabs.messages.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

@Component
@Sharable
@Slf4j
public class MessageEncoder extends MessageToByteEncoder<Message> {
	
	@Override
	protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out)	throws Exception {
		log.debug("MessageDecoder - Class {}", msg.getClass().getName());
		log.debug("Message to encode --> {}",ByteBufUtil.hexDump(msg.encode()));
		out.writeBytes(msg.encode());
	}

}
