package br.com.wjrlabs.commom.exceptions;

/**
 * 
 * @author Wagner Alves
 *
 */
public class CommandRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -5333017386175268502L;

	public CommandRuntimeException() {
		super();
	}

	public CommandRuntimeException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CommandRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public CommandRuntimeException(String message) {
		super(message);
	}

	public CommandRuntimeException(Throwable cause) {
		super(cause);
	}
}
