package br.com.wjrlabs.commom.exceptions;

/**
 * 
 * @author Wagner Alves
 *
 */
public class ExecuteRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -5333017386175268502L;

	public ExecuteRuntimeException() {
		super();
	}

	public ExecuteRuntimeException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ExecuteRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExecuteRuntimeException(String message) {
		super(message);
	}

	public ExecuteRuntimeException(Throwable cause) {
		super(cause);
	}
}
