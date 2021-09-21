package br.com.wjrlabs.core;

/**
 * Informação transmitida ou recebida pelo comunicador do SCAP, em uma estrutura
 * de bytes formatada.
 *
 */
public interface Message {
	
	/**
	 * Versão base das mensagens do SCAP.
	 */
	public static final byte VERSION = 1;
	
	/**
	 * @return {@link MessageKey}
	 */
	public abstract MessageKey getKey();

	/**
	 * Formata uma mensagem para uma matriz de bytes, para comunicação entre
	 * dispositivos e o servidor.
	 * @return Mensagem formatada como byte[].
	 */
	public abstract byte[] encode();

	/**
	 * Decompõem uma matriz de bytes para o formato interno da mensagem.
	 * @param buffer Matriz de bytes (byte[]).
	 */
	public abstract void decode(byte[] buffer);
}