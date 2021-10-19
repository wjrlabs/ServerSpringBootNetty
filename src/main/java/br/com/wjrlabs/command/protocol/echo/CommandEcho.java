package br.com.wjrlabs.command.protocol.echo;

import br.com.wjrlabs.command.Command;
import br.com.wjrlabs.messages.Message;
import br.com.wjrlabs.session.DeviceSession;
import br.com.wjrlabs.util.ByteHelper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommandEcho implements Command{

	@Override
	public Message execute(Message message, DeviceSession session) {
		if(log.isDebugEnabled()) {
			log.debug("--CommandEcho--");
			log.debug("Request {}",ByteHelper.hexify(message.encode()));
			log.debug("Response {}",ByteHelper.hexify(message.encode()));
		}
		
		message.decode(message.encode());

		return message;
	}


}
