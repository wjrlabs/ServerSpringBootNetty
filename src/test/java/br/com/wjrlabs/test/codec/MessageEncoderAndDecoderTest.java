package br.com.wjrlabs.test.codec;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.wjrlabs.codecs.MessageDecoder;
import br.com.wjrlabs.codecs.MessageEncoder;
import br.com.wjrlabs.messages.Message;
import br.com.wjrlabs.messages.MessageFactory;
import br.com.wjrlabs.test.buffer.EchoMessageBuffer;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;


@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application-dev.properties")
public class MessageEncoderAndDecoderTest {
	
	
    @Autowired
	private AutowireCapableBeanFactory beanFactory;
    
    private MessageFactory messageFactory;
	@Test
	public void testEncoderAndDecoder() {
		messageFactory=new MessageFactory(beanFactory);

		MessageDecoder encoder = new MessageDecoder(this.messageFactory);
		MessageEncoder decoder=new MessageEncoder();

		EmbeddedChannel channel = new EmbeddedChannel(encoder,decoder);
		
		assertThat(channel.writeInbound(Unpooled.copiedBuffer(EchoMessageBuffer.getBuffer())), is(equalTo(Boolean.TRUE)));
		Message message = channel.flushInbound().readInbound();
		assertThat(message,instanceOf(Message.class));
		
		assertThat(channel.writeInbound(Unpooled.copiedBuffer(new byte[5])), is(equalTo(Boolean.TRUE)));
		assertThat(channel.flushInbound().readInbound(),instanceOf(Message.class));
		
		assertThat(channel.writeOutbound(message.encode()), is(equalTo(Boolean.TRUE)));
		assertThat(channel.flushOutbound().readOutbound(),is(equalTo(EchoMessageBuffer.getBuffer())));
				
	}

}
