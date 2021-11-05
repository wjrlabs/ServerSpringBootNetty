package br.com.wjrlabs.business;

import br.com.wjrlabs.messages.Message;

public interface Command {

	public Message execute(Message message);

}
