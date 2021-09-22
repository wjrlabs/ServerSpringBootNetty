package br.com.wjrlabs.exceptions;

public class ServerInternalError extends RuntimeException {

	private static final long serialVersionUID = -5624489948612686514L;

	public ServerInternalError(String message) {
		super(message);
	}


}
