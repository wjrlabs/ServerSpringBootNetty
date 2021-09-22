package br.com.wjrlabs.server.handler;

import org.springframework.stereotype.Component;

import br.com.wjrlabs.codecs.DecoderMessage;
import br.com.wjrlabs.codecs.EncoderMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Create new {@link ChannelPipeline} to a new {@link Channel}.
 *
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ServerInitializer extends ChannelInitializer<SocketChannel> {
	
	private static final DecoderMessage DECODERMESSAGE = new DecoderMessage();
	
	private static final EncoderMessage ENCODERMESSAGE = new EncoderMessage();

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		// Decoders:
		pipeline.addLast(DECODERMESSAGE);
		// Encoders:
		pipeline.addLast(ENCODERMESSAGE);
		// Regra de negócio:
		pipeline.addLast(new MessageHandler());
	}
}
