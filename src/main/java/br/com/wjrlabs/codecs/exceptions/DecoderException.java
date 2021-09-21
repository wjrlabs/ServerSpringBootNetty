package br.com.digicon.scap.comm.codecs.exceptions;

/**
 * Informa um erro de decodificação de mensagem para tratamento de mensagens recebidas.
 * 

 */
public class DecoderException extends RuntimeException {

	/** Identificador único de versão da classe. */
	private static final long serialVersionUID = -8277039623310924389L;
	
	/**
	 * Bytes recebidos no decodificador.
	 */
	protected byte[] frame;

	/**
	 * Construtor padrão.
	 * @param frame {@link #frame()}
	 */
	public DecoderException(byte[] frame) {
		super();
		this.frame = frame;
	}
	
	/**
	 * Construtor padrão.
	 * @param frame {@link #frame}
	 * @param cause Origem do erro.
	 */
	public DecoderException(byte[] frame, Throwable cause) {
		super(cause);
		this.frame	= frame;
	}

	/**
	 * @return O {@link #frame}.
	 */
	public byte[] getFrame() {
		return frame;
	}
}