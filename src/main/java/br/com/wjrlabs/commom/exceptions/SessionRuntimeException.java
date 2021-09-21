package br.com.wjrlabs.commom.exceptions;

/**
 * Informa um erro no processamento de dados da sessão.
 *
 */
public class SessionRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -8298451617157140337L;

	public SessionRuntimeException() {
		super();
	}

	public SessionRuntimeException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SessionRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public SessionRuntimeException(String message) {
		super(message);
	}

	public SessionRuntimeException(Throwable cause) {
		super(cause);
	}
}
