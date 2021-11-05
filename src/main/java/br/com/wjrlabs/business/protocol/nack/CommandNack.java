package br.com.wjrlabs.business.protocol.nack;

import org.springframework.stereotype.Component;

import br.com.wjrlabs.business.Command;
import br.com.wjrlabs.messages.Message;
import br.com.wjrlabs.messages.common.MessageType;
import io.netty.buffer.ByteBufUtil;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CommandNack implements Command{

	private static final byte[] NACK=new byte[] {MessageType.NACK.getRequestId()};
	@Override
	public Message execute(Message message) {
		if(log.isDebugEnabled()) {
			byte[] bytes=message.encode();
			log.debug("--CommandNak--");
			log.debug("Request {}",ByteBufUtil.hexDump(bytes));
			log.debug("Response {}",ByteBufUtil.hexDump(NACK));
		}
		message.decode(NACK);
		
		return message;
	}


}
