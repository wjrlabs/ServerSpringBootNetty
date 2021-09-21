package br.com.wjrlabs.commom.session;

/**
 * Identificador do estado do Equipamento.
 *
 */
public enum Status {

	/**
	 * Indica que o equipamento está desligado.
	 */
	OFF,
	
	/**
	 * Indica que o equipamento está ligado e aguardando por processamentos.
	 */
	IDLE,
	
	/**
	 * Indica que o servidor está aguardando uma resposta do equipamento.
	 */
	ACK_WAITING,
	
	/**
	 * Indica que o servidor está aguardando por uma resporta do equipamento 
	 * sobre informações de algum parâmetro.
	 */
	PARAM_WAITING,
	
	/**
	 * Recebendo arquivo.
	 */
	FILE_RECEIVING,
	
	/**
	 * Executando alguma rotina de processamento. Ao final, retornará para
	 * o estado anterior.
	 */
	WORKING;
}
