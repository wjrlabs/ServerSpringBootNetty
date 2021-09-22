package br.com.wjrlabs.commom.exceptions;

/**
 * 
 * @author Wagner Alves
 *
 */
public class HashRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -5333017386175268502L;

	public HashRuntimeException() {
		super();
	}

	public HashRuntimeException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public HashRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public HashRuntimeException(String message) {
		super(message);
	}

	public HashRuntimeException(Throwable cause) {
		super(cause);
	}
}
