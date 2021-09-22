package br.com.wjrlabs.executor;

import br.com.wjrlabs.commom.session.DeviceSession;
import br.com.wjrlabs.core.Message;
import br.com.wjrlabs.core.MessageKey;

/**
 * @author Wagner Alves
 * Contract to express interest in execute a {@link Message}.
 * 
 * @param <E> class from {@link Message}
 * @param <U> class from {@link DeviceSession}
 */
public interface Executor<E extends Message, U extends DeviceSession> {

	public abstract MessageKey getInterest();

	public abstract Message[] execute(E message, U session);
}