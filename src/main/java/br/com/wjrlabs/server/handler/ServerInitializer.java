package br.com.wjrlabs.server.handler;

import org.springframework.stereotype.Component;

import br.com.wjrlabs.messages.MessageHandler;
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
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
      //  pipeline.addLast(new DelimiterBasedFrameDecoder(1024 * 1024, Delimiters.lineDelimiter()));

	//	pipeline.addLast(DECODERMESSAGE);
		// Encoders:
	//	pipeline.addLast(ENCODERMESSAGE);
		// Regra de negócio:
		pipeline.addLast(new MessageHandler());
	}
}
