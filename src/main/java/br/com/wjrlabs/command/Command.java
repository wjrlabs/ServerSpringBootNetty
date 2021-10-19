package br.com.wjrlabs.command;

import br.com.wjrlabs.messages.Message;
import br.com.wjrlabs.session.DeviceSession;

public interface Command {

	public Message execute(Message message,DeviceSession session);

}
