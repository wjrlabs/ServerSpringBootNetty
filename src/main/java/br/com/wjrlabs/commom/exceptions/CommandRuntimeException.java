package br.com.wjrlabs.commom.exceptions;

/**
 * Informa um erro no processamento de mensagens.
 *
 */
public class CommandRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -7334576237766612895L;

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
