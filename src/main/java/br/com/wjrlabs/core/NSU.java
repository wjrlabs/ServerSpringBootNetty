package br.com.wjrlabs.core;

import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.slf4j.Slf4j;

/**
 * Gerenciamento de Número Sequencial Único para identificação de processos do
 * comunicador.
 *
 */
@Slf4j
public final class NSU {

	/**
	 * Valor corrente.
	 */
	private static AtomicInteger value = new AtomicInteger();

	/**
	 * Construtor oculto.
	 */
	private NSU() {
	}

	/**
	 * Recupera o próximo valor de NSU
	 * 
	 * @return int
	 */
	public static int next() {
		int result = NSU.value.incrementAndGet();
		if (result < 0) {
			log.warn("NSU reinicializado...");
			result = 0;
			NSU.value.set(result);
		}
		return result;
	}

	/**
	 * Recupera o valor corrente.
	 * 
	 * @return int
	 */
	public static int current() {
		return NSU.value.get();
	}
}
