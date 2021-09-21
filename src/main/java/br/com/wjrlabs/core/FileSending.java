package br.com.wjrlabs.core;

import java.nio.ByteBuffer;
import java.util.Date;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

/**
 * Objeto para gerenciar o envio de arquivos entre servidor e os módulos.
 *
 */

@Getter
@Setter
public class FileSending {
	
	/**
	 * Número Sequencial Único associado a operação.
	 */
	private int nsu;
	
	/**
	 * Identificador único do controle de envio do arquivo.
	 */
	private UUID uuid;
	
	/**
	 * Gerenciamento dos bytes
	 */
	private ByteBuffer buffer;
	
	/**
	 * Tamanho das partes em bytes
	 */
	private int partialLength;
	
	/**
	 * Marca o horário de início da transmissão do arquivo.
	 */
	private Date start;
	
	/**
	 * Construtor padrão.
	 * @param nsu Número Sequencial Único da operação.
	 * @param uuid Identificador único do controle de envio do arquivo.
	 * @param buffer Bytes do arquivo.
	 * @param partialLenght Tamanho de cada parte.
	 */
	public FileSending(int nsu, UUID uuid, byte[] buffer, int partialLength) {
		this.nsu			= nsu;
		this.uuid			= uuid;
		this.buffer			= ByteBuffer.wrap(buffer);
		this.partialLength	= partialLength;
		this.start			= new Date();
	}
	
	
	/**
	 * Recupera o tamanho do arquivo em bytes.
	 * @return int
	 */
	public int getLength() {
		return buffer.limit();
	}
	
	/**
	 * Calcula o número de partes para transmissão.
	 * @return Número de partes.
	 */
	public int getPartialCount() {
		int result = buffer.limit() / partialLength;
		if ((buffer.limit() % partialLength) != 0) {
			result++;
		}
		return result;
	}
	
	/**
	 * Recupera o índice da parte de leitura corrente.
	 * @return Índice de parte.
	 */
	public short getPartialIndex() {
		short result = (short) ((buffer.position() / partialLength) + 1);
		return result;
	}
	
	/**
	 * Recupera a próxima parte do arquivo.
	 * @return byte[]
	 */
	public byte[] nextPartial() {
		int length = partialLength;
		if ((buffer.limit() - buffer.position()) < partialLength) {
			length = buffer.limit() - buffer.position();
		}
		byte[] partial = new byte[length];
		buffer.get(partial, 0, partial.length);
		return partial;
	}
	
	/**
	 * Recupera uma parte específica do arquivo.
	 * @param index Índice da parte.
	 * @return byte[]
	 */
	public byte[] getPartial(int index) {
		// Retorno padrão:
		byte[] result = null;
		
		// Verificando se o índice não ultrapassa o limite:
		if (index <= getPartialCount()) {
			// Guardando o bloco corrente. O ponteiro deverá ser reposicionado 
			// no final do processo.
			int current = getPartialIndex();

			// Procurando pelo bloco solicitado...
			reset();
			for (int i = 1; i < index; i++) {
				nextPartial();
			}
			result = nextPartial();
			
			// Reposicionando...
			reset();
			for (int i = 1; i < current; i++) {
				nextPartial();
			}
		}
		
		return result;
	}
	
	/**
	 * Reinicia o processo de gerenciamento das partes.
	 */
	public void reset() {
		buffer.rewind();
	}
	
	/**
	 * Informa se ainda há o que ser transmitido do arquivo.
	 * @return boolean
	 */
	public boolean hasNext() {
		return buffer.remaining() > 0;
	}
	
	/**
	 * Calcula o HASH do arquivo.
	 * @return HASH na base 64.
	 */
	public String getHash() {
		//TODO implement
		return null;
	}
	
}
