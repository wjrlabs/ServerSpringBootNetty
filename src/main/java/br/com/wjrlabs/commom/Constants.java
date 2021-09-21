package br.com.wjrlabs.commom;


/**
 * Constantes compartilhadas no sistema.
 *
 */
public class Constants {

	/**
	 * Número máximo de tentativas de comunicação.
	 */
	public static final int MAX_RETRIES = 5;
	
	/**
	 * Tempo de expera por uma resposta antes de tentar reencaminhá-la.
	 */
	public static final int TIMEOUT_SECONDS = 20;
	
	/**
	 * Tamanho de cada parte de arquivo para transmissão.
	 */
	public static final short PARTIAL_LENGTH = 512;
}
