package br.com.wjrlabs.exceptions;

/**
 * 
 * @author Wagner Alves
 *
 */
public class MessageRuntimeException extends RuntimeException implements ServerSpringBootNettyExceptions {

	private static final long serialVersionUID = -3522534485486853639L;

	public MessageRuntimeException() {
		super();
	}

	public MessageRuntimeException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MessageRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public MessageRuntimeException(String message) {
		super(message);
	}

	public MessageRuntimeException(Throwable cause) {
		super(cause);
	}
}
