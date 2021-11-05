package br.com.wjrlabs.test.business.echo;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.wjrlabs.business.CommandFactory;
import br.com.wjrlabs.business.protocol.echo.CommandEcho;
import br.com.wjrlabs.codecs.MessageDecoder;
import br.com.wjrlabs.codecs.MessageEncoder;
import br.com.wjrlabs.messages.MessageFactory;
import br.com.wjrlabs.server.MessageHandler;
import br.com.wjrlabs.test.buffer.EchoMessageBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;


@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application-dev.properties")
@ContextConfiguration(classes = {
		CommandEcho.class
		})
public class CommandEchoTest {
	
    @Autowired
	private AutowireCapableBeanFactory beanFactory;
    
    @Autowired
	private ApplicationContext context;
    
    private MessageFactory messageFactory;
    
    private CommandFactory commandFactory;
	@Test
	public void test() {
		messageFactory=new MessageFactory(beanFactory);
		commandFactory=new CommandFactory(context);

		MessageDecoder encoder = new MessageDecoder(this.messageFactory);
		MessageEncoder decoder=new MessageEncoder();

		EmbeddedChannel channel = new EmbeddedChannel(encoder,decoder,new MessageHandler(commandFactory));
		
		assertThat(channel.writeInbound(Unpooled.copiedBuffer(EchoMessageBuffer.getBuffer())), is(equalTo(Boolean.FALSE)));
		ByteBuf buff = channel.flushOutbound().readOutbound();
		
		assertThat(ByteBufUtil.getBytes(buff), is(equalTo(EchoMessageBuffer.getBuffer())));

		
	}
}
