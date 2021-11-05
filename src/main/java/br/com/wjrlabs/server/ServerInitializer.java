package br.com.wjrlabs.server;

import org.springframework.stereotype.Component;

import br.com.wjrlabs.codecs.MessageDecoder;
import br.com.wjrlabs.codecs.MessageEncoder;
import br.com.wjrlabs.messages.MessageFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import lombok.RequiredArgsConstructor;

/**
 * Create new {@link ChannelPipeline} to a new {@link Channel}.
 *
 */
@Component
@RequiredArgsConstructor
public class ServerInitializer extends ChannelInitializer<SocketChannel> {
	
	private static MessageEncoder MESSAGE_DECODER=new MessageEncoder();
	private final MessageHandler messageHandler;
	private final MessageFactory messageFactory;
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(MESSAGE_DECODER);
		pipeline.addLast(new MessageDecoder(messageFactory));
		pipeline.addLast(messageHandler);
	}
}
