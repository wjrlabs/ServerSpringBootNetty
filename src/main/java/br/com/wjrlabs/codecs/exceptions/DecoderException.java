package br.com.wjrlabs.codecs.exceptions;

import lombok.Getter;


/**
 * 
 * @author Wagner Alves
 * Handle decoding excpetions
 *
 */
@Getter
public class DecoderException extends RuntimeException {

	private static final long serialVersionUID = 6647786818318923798L;
	protected byte[] frame;

	public DecoderException(byte[] frame) {
		super();
		this.frame = frame;
	}
	
	public DecoderException(byte[] frame, Throwable cause) {
		super(cause);
		this.frame	= frame;
	}

	public byte[] getFrame() {
		return frame;
	}
}