package br.com.wjrlabs.test.factory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.wjrlabs.business.Command;
import br.com.wjrlabs.business.CommandFactory;
import br.com.wjrlabs.business.protocol.echo.CommandEcho;
import br.com.wjrlabs.business.protocol.nack.CommandNack;
import br.com.wjrlabs.messages.Message;
import br.com.wjrlabs.messages.MessageFactory;
import br.com.wjrlabs.messages.protocol.echo.MessageEcho;
import br.com.wjrlabs.messages.protocol.nack.MessageNack;
import br.com.wjrlabs.test.buffer.EchoMessageBuffer;
import io.netty.buffer.Unpooled;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application-dev.properties")
@ContextConfiguration(classes = {
		CommandEcho.class,
		CommandNack.class
		})
public class CommandFactoryTest {
	
    @Autowired
	private AutowireCapableBeanFactory beanFactory;
    
    @Autowired
	private ApplicationContext context;

    private MessageFactory messageFactory;
    
    private CommandFactory commandFactory;
	
    @Test
    public void testFactory() {
		messageFactory=new MessageFactory(beanFactory);
		commandFactory=new CommandFactory(context);

		Message message= messageFactory.getMessage(Unpooled.copiedBuffer(EchoMessageBuffer.getBuffer()));
		assertThat(message, instanceOf(MessageEcho.class));
		Command commmand = commandFactory.getCommand(message);
		assertThat(commmand, instanceOf(CommandEcho.class));
		
		message= messageFactory.getMessage(Unpooled.copiedBuffer(new byte[5]));//anything that returns NACK
		assertThat(message, instanceOf(MessageNack.class));
		commmand = commandFactory.getCommand(message);
		assertThat(commmand, instanceOf(CommandNack.class));
		
    }
    
    @AfterEach
    public void afterTest() {
    	beanFactory=null;
    	messageFactory=null;
    	context=null;
    	commandFactory=null;
    }
}
