package br.com.wjrlabs.exceptions;

/**
 * 
 * @author Wagner Alves
 *
 */
public class InternalRuntimeException extends RuntimeException implements ServerSpringBootNettyExceptions {

	private static final long serialVersionUID = 2704805164748365844L;

	public InternalRuntimeException() {
	}

	public InternalRuntimeException(String message) {
		super(message);
	}

	public InternalRuntimeException(Throwable cause) {
		super(cause);
	}

	public InternalRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public InternalRuntimeException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
