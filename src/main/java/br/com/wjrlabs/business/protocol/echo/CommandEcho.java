package br.com.wjrlabs.business.protocol.echo;

import org.springframework.stereotype.Component;

import br.com.wjrlabs.business.Command;
import br.com.wjrlabs.messages.Message;
import io.netty.buffer.ByteBufUtil;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CommandEcho implements Command{

	@Override
	public Message execute(Message message) {
		if(log.isDebugEnabled()) {
			log.debug("--CommandEcho--");
			log.debug("Request {}",ByteBufUtil.hexDump(message.encode()));
			log.debug("Response {}",ByteBufUtil.hexDump(message.encode()));
		}
		
		message.decode(message.encode());

		return message;
	}


}
