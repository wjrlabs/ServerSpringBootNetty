package br.com.wjrlabs.test.factory;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.wjrlabs.messages.Message;
import br.com.wjrlabs.messages.MessageFactory;
import br.com.wjrlabs.messages.protocol.echo.MessageEcho;
import br.com.wjrlabs.test.buffer.EchoMessageBuffer;
import io.netty.buffer.Unpooled;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application-dev.properties")
public class MessageFactoryTest {
	
    @Autowired
	private AutowireCapableBeanFactory beanFactory;
    
    private MessageFactory messageFactory;
	
    @Test
    public void testFactory() {
		messageFactory=new MessageFactory(beanFactory);

		Message message= messageFactory.getMessage(Unpooled.copiedBuffer(EchoMessageBuffer.getBuffer()));
		assertThat(message, instanceOf(MessageEcho.class));
    }
    
    @AfterEach
    public void afterTest() {
    	beanFactory=null;
    	messageFactory=null;
    }
}
