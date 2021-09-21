package br.com.wjrlabs.core;

import java.io.Serializable;
import java.nio.ByteBuffer;

import br.com.wjrlabs.commom.types.MessageType;
import lombok.Getter;
import lombok.Setter;

/**
 * Identificador de mensagem, que é composto por:
 * 
 * <p>
 * <table border="1">
 * 	<th>
 * 		<tr>
 * 			<td><b>Tamanho (bytes)</b></td>
 * 			<td><b>Descrição</b></td>
 * 		</tr>
 * 	</th>
 * 	<tb>
 * 		<tr>
 * 			<td>1</td>
 * 			<td>Identificação da Mensagem.</td>
 * 		</tr>
 * 		<tr>
 * 			<td>1</td>
 * 			<td>Versão do protocolo.</td>
 * 		</tr>
 * 	</tb>
 * </table>
 * <p>
 * 
 */
@Getter
@Setter
public class MessageKey implements Serializable {

	/**
	 * Identificador único a classe
	 */
	private static final long serialVersionUID = -563556088778195033L;


	/**
	 * Tamanho da chave, em bytes.
	 */
	public static final int LENGTH = 2;
	
	/**
	 * Tipo de Mensagem.
	 */
	private MessageType type;
	
	/**
	 * Versão da Mensagem.
	 */
	private byte version;
	
	/**
	 * Construtor da Chave de Mensagem.
	 * @param type Tipo de Mensagem.
	 * @param version Versão da Mensgaem.
	 */
	public MessageKey(MessageType type, byte version) {
		this.type		= type;
		this.version	= version;
	}

	
	/**
	 * Codifica a chave em uma matriz de bytes para o formato das mensagens.
	 * @return byte[]
	 */
	public byte[] encode() {
		ByteBuffer buffer = ByteBuffer.allocate(LENGTH);

		buffer.put(type.getValue());
		buffer.put(version);
		
		buffer.flip();
		return buffer.array();
	}	

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + version;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MessageKey other = (MessageKey) obj;
		if (type != other.type)
			return false;
		if (version != other.version)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		result.append(this.getClass().getName());
		result.append("[");
		result.append("type=").append(type).append(",");
		result.append("version=").append(version);
		result.append("]");
		
		return result.toString();
	}
}
