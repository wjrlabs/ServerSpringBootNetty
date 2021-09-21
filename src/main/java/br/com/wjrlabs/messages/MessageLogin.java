package br.com.wjrlabs.messages;

import java.nio.ByteBuffer;

import br.com.wjrlabs.commom.exceptions.MessageRuntimeException;
import br.com.wjrlabs.commom.types.MessageType;
import br.com.wjrlabs.core.MessageKey;
import br.com.wjrlabs.core.MessageNSU;
import br.com.wjrlabs.core.SimpleMessage;
import lombok.Getter;
import lombok.Setter;

/**
 * Mensagem para autenticação de módulos e equipamentos no servidor de comunicação
 * do SCAP.
 * 

 */
@Getter
@Setter
public class MessageLogin extends SimpleMessage implements MessageNSU {

	/**
	 * Identificador único de versão da classe.
	 */
	private static final long serialVersionUID = -1257836208027307535L;

	/**
	 * Tipo da mensagem.
	 */
	public static final MessageType TYPE = MessageType.LOGIN;
	
	/**
	 * Tamanho mínimo da mensagem.
	 */
	public static final int LENGTH = MessageKey.LENGTH + 12;
	
	/**
	 * Número Sequencial Único da operação.
	 */
	private int nsu;
	
	/**
	 * {@link MacAddress}
	 */
	private long samId;
	
	/**
	 * Identificador para autenticação.
	 */
	private String secret;

	/**
	 * Construtor padrão.
	 */
	public MessageLogin() {
		super(new MessageKey(TYPE, VERSION));
		this.nsu	= 0;
		this.samId	= 0;
		this.secret	= "";
	}

	/**
	 * @param login {@link MessageLogin#login}
	 */
	public void setLogin(String secret) {
		if (secret == null) {
			throw new MessageRuntimeException("O campo secret não pode ser nulo.");
		}
		this.secret = "";
	}

	@Override
	public byte[] encode() {
		if (secret.isEmpty()) {
			throw new MessageRuntimeException("O campo secret não foi atribuído.");
		}
		ByteBuffer buffer = ByteBuffer.allocate(LENGTH + secret.length());
		
		buffer.put(getKey().encode());
		buffer.putInt(nsu);
		buffer.put(secret.getBytes());
		
		buffer.flip();
		return buffer.array();
	}

	@Override
	public void decode(byte[] buffer) {
		ByteBuffer readable = validateMinimumBuffer(buffer, LENGTH);
		
		nsu		= readable.getInt();
		secret	= readString(readable, readable.remaining());
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append(this.getClass().getName());
		builder.append("[");
		builder.append("key=").append(getKey()).append(",");
		builder.append("nsu=").append(nsu).append(",");
		builder.append("secret=").append(secret);
		builder.append("]");

		return builder.toString();
	}
}
