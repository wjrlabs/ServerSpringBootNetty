package br.com.wjrlabs.commom.exceptions;

/**
 * Informa um erro de conversão de tipos para as mensagens.
 */
public class TypeRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 9135011502887989510L;

	public TypeRuntimeException() {
	}

	public TypeRuntimeException(String message) {
		super(message);
	}

	public TypeRuntimeException(Throwable cause) {
		super(cause);
	}

	public TypeRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public TypeRuntimeException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
