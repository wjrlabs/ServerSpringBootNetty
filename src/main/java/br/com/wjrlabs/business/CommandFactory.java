package br.com.wjrlabs.business;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.wjrlabs.business.protocol.echo.CommandEcho;
import br.com.wjrlabs.business.protocol.nack.CommandNack;
import br.com.wjrlabs.messages.Message;
import br.com.wjrlabs.messages.common.MessageType;
import lombok.extern.slf4j.Slf4j;

@Component
@Scope("singleton")
@Slf4j
public class CommandFactory {
	
	private ApplicationContext context;
	
	public CommandFactory(ApplicationContext context) {
		this.context = context;
	}
	
	public Command getCommand(Message message) {
		
		MessageType type = MessageType.get(message.getClass().getName());

		switch (type) {
			case ECHO:
				log.debug("COMMAND_ECHO");
				return context.getBean(CommandEcho.class);
				
			case NACK:
				log.debug("COMMAND_NACK");
				return context.getBean(CommandNack.class);
				
			default:
				return new CommandNack();
		}	
	
	}

}
