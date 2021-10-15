package br.com.wjrlabs.commom.exceptions;

/**
 * 
 * @author Wagner Alves
 *
 */
public class MessageRuntimeException extends RuntimeException {

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
