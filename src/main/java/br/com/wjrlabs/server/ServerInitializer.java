package br.com.wjrlabs.server;

import org.springframework.stereotype.Component;

import br.com.wjrlabs.codecs.MessageDecoder;
import br.com.wjrlabs.codecs.MessageEncoder;
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
	
	private static final MessageDecoder MESSAGE_DECODER = new MessageDecoder();
	private static final MessageEncoder MESSAGE_ENCODER = new MessageEncoder();

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		//TODO limitar tamanho do pacote em 1024 bytes ida e volta.
		pipeline.addLast(MESSAGE_DECODER);
		pipeline.addLast(MESSAGE_ENCODER);
		pipeline.addLast(new MessageHandler());


		pipeline.addLast(new MessageHandler());
	}
}
