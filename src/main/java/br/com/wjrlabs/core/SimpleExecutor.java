package br.com.wjrlabs.core;

import java.text.MessageFormat;

import br.com.wjrlabs.commom.exceptions.ExecuteRuntimeException;
import br.com.wjrlabs.commom.session.DeviceSession;
import br.com.wjrlabs.executor.Executor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Wagner Alves
 */
@Slf4j
public abstract class SimpleExecutor<E extends Message, U extends DeviceSession> implements Executor<E,U>  {

	private MessageKey interest;
	
	public SimpleExecutor(Class<E> clazz) {
		try {
			Message message = clazz.newInstance();
			interest		= message.getKey();
			log.info("Executing {}", interest);
		} catch (Exception e) {
			String error = "Error registering message to execute";
			log.error(error, e);
			throw new ExecuteRuntimeException(error, e);
		}
	}

	@Override
	public MessageKey getInterest() {
		return interest;
	}
	
	@Override
	public abstract Message[] execute(E message, U session);
}
