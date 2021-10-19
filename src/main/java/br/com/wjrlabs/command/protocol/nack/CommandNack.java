package br.com.wjrlabs.command.protocol.nack;

import br.com.wjrlabs.command.Command;
import br.com.wjrlabs.messages.Message;
import br.com.wjrlabs.messages.MessageType;
import br.com.wjrlabs.session.DeviceSession;
import br.com.wjrlabs.util.ByteHelper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommandNack implements Command{

	private static final byte[] NACK=new byte[] {MessageType.NACK.getIdRequest()};
	@Override
	public Message execute(Message message, DeviceSession session) {
		if(log.isDebugEnabled()) {
			byte[] bytes=message.encode();
			log.debug("--CommandNak--");
			log.debug("Request {}",ByteHelper.hexify(bytes));
			log.debug("Response {}",ByteHelper.hexify(NACK));
		}
		message.decode(NACK);
		
		return message;
	}


}
