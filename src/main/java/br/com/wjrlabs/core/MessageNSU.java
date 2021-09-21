package br.com.wjrlabs.core;

/**
 * Interface para auxiliar o gerenciamento de reconciliação.
 *
 */
public interface MessageNSU {

	/**
	 * Recupera o Número Sequencial Único de identificação da operação.
	 * @return int
	 */
	public abstract int getNsu();
}
