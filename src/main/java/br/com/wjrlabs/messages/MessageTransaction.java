package br.com.wjrlabs.messages;

import java.nio.ByteBuffer;
import java.util.Date;

import br.com.wjrlabs.core.MessageKey;
import br.com.wjrlabs.core.SimpleMessage;

/**
 * Classe comum para registro de transações online.
 * 

 */
public class MessageTransaction extends SimpleMessage{


	/**
	 * Class unique identifier
	 */
	private static final long serialVersionUID = -1434216823216168541L;

	/**
	 * Tamanho da mensagem.
	 */
	public static final int LENGTH = MessageKey.LENGTH + 15;
	
	/**
	 * Data, hora, minuto e segundo da transação.
	 */
	private Date date;
	
	/**
	 * Construtor padrão.
	 * @param key Chave de identificação da mensagem.
	 */
	public MessageTransaction(MessageKey key) {
		super(key);
		this.date		= new Date();
	}


	@Override
	public byte[] encode() {
		ByteBuffer buffer = ByteBuffer.allocate(LENGTH);
		
		buffer.put(getKey().encode());
		buffer.put(writeDate(date));
		
		buffer.flip();
		return buffer.array();
	}

	@Override
	public void decode(byte[] buffer) {
		ByteBuffer readable = validateMinimumBuffer(buffer, LENGTH);
		date		= readDate(readable);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getName());
		builder.append("[");
		builder.append("date=").append(date);
		builder.append("]");

		return builder.toString();
	}

}
