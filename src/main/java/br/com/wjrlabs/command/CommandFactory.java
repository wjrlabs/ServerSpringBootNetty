package br.com.wjrlabs.command;

import br.com.wjrlabs.command.protocol.echo.CommandEcho;
import br.com.wjrlabs.command.protocol.nack.CommandNack;
import br.com.wjrlabs.messages.Message;
import br.com.wjrlabs.messages.MessageType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommandFactory {
	
	public static Command getInstance(Message message) {
		
		MessageType type = MessageType.get(message.getClass().getName());

		switch (type) {
			case ECHO:
				log.debug("COMMAND_ECHO");
				return new CommandEcho();
				
			case NACK:
				log.debug("COMMAND_NACK");
				return new CommandNack();
				
			
			default:
				return new CommandNack();
		}	
	
	}

}
