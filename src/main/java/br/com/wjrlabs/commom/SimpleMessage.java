package br.com.wjrlabs.commom;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.StringUtils;

import br.com.wjrlabs.commom.exceptions.MessageRuntimeException;
import br.com.wjrlabs.util.ByteHelper;

/**
 * Mensagem transmitida pelo comunicador do SCAP. 
 * <p>
 * Todas as mensagens transmitidas devem herdar essa classe e implementar os
 * métodos <>encode</b> e <b>decode</b>, necessários para transformar o objeto
 * em uma matriz de bytes e permitir a transmissão da informação.
 * 
 */
public abstract class SimpleMessage implements Serializable, Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5757632109318623084L;

	/**
	 * Identificador único de versão da classe.
	 */
	
	/**
	 * Tamanho em bytes da estrutura de dados para {@link Date}.
	 */
	private static final int DATE_STRUCT_LENGTH = 6;

    /**
     * Mês de referência para cálculo das classes Java e conversão para o formato
     * da mensagem, já que o mês para o Java inicia-se em 0 e para a mensagem
     * o início é 1.
     */
    private static final byte MONTH_BASE = 1;

    /**
     * Ano base de referência para o ano 0. Os anos serão representados
     * a partir do ano 2000:
     * <p>
     * 0 = 2000<br>
     * 1 = 2001<br>
     * ...
     */
	private static final int YEAR_BASE = 2000;

    /**
     * Tamanho do HASH de validação.
     */
	private static final int HASH_LENGTH = 20;

	/**
	 * Identificador da Mensagem, com tipo e versão.
	 */
	private MessageKey key;
	
	/**
	 * Construtor da mensagem.
	 * @param key Identifidador da mensagem.
	 */
	public SimpleMessage(MessageKey key) {
		this.key	= key;
	}

	/* (non-Javadoc)
	 * @see br.com.digicon.scap.comm.common.Message#getKey()
	 */
	@Override
	public MessageKey getKey() {
		return key;
	}

	/* (non-Javadoc)
	 * @see br.com.digicon.scap.comm.common.Message#encode()
	 */
	@Override
	public abstract byte[] encode();
	
	/* (non-Javadoc)
	 * @see br.com.digicon.scap.comm.common.Message#decode(byte[])
	 */
	@Override
	public abstract void decode(byte[] buffer);
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
	
		result.append(this.getClass().getName());
		result.append("[");
		result.append("key=").append(key.toString());
		result.append("]");
		
		return result.toString();
	}
	
	/**
	 * Lê uma sequencia de caracteres com tamanho definido.
	 * @param readable {@link ByteBuffer}
	 * @param length Tamanho da String
	 * @return String
	 */
	protected String readString(ByteBuffer readable, int length) {
		byte[] buffer = new byte[length];
		for (int i = 0; i < length; i++) {
			buffer[i] = readable.get();
		}
		return new String(buffer).trim();
	}

	/**
	 * Lê uma data e hora do buffer de leitura, baseado na estrutura:
	 * <table border="1">
	 * 	<th>
	 * 		<tr>
	 * 			<td>
	 * 				<b>Campo:</b>
	 * 			</td>
	 * 			<td>
	 * 				<b>Tamanho (bytes):</b>
	 * 			</td>
	 * 			<td>
	 * 				<b>Descrição:</b>
	 * 			</td>
	 * 		</tr>
	 * 	</th>
	 * 	<tb>
	 * 		<tr>
	 * 			<td>dia</td>
	 * 			<td>1</td>
	 * 			<td>Dia do mês, de 1 a 31.
	 * 		</tr>
	 * 		<tr>
	 * 			<td>mês</td>
	 * 			<td>1</td>
	 * 			<td>Mês, de 1 a 12.
	 * 		</tr>
	 * 		<tr>
	 * 			<td>ano</td>
	 * 			<td>1</td>
	 * 			<td>Possui como base o ano 2000. (0 = 2000, 1 = 2001...).
	 * 		</tr>
	 * 		<tr>
	 * 			<td>hora</td>
	 * 			<td>1</td>
	 * 			<td>Hora do dia, de 0 a 23
	 * 		</tr>
	 * 		<tr>
	 * 			<td>minuto</td>
	 * 			<td>1</td>
	 * 			<td>Minutos de 0 a 59
	 * 		</tr>
	 * 		<tr>
	 * 			<td>segundo</td>
	 * 			<td>1</td>
	 * 			<td>Segundos de 0 a 59
	 * 		</tr>
	 * 	</tb>
	 * </table>
	 * @param readable {@link ByteBuffer}
	 * @return {@link Date}
	 */
	protected Date readDate(ByteBuffer readable) {
		if (readable.remaining() < DATE_STRUCT_LENGTH) {
			throw new MessageRuntimeException("Tamanho da estrutura de data é inválido.");
		}
		
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DATE, readable.get());
		calendar.set(Calendar.MONTH, (readable.get() - MONTH_BASE));
		calendar.set(Calendar.YEAR, (readable.get() + YEAR_BASE));
		calendar.set(Calendar.HOUR_OF_DAY, readable.get());
		calendar.set(Calendar.MINUTE, readable.get());
		calendar.set(Calendar.SECOND, readable.get());
		calendar.set(Calendar.MILLISECOND, 0);
		
		return calendar.getTime();
	}

	/**
	 * Transforma um sequencia de caracteres em uma matriz de bytes.
	 * @param text Texto para ser codificado.
	 * @param length Tamanho final do texto codificado. 
	 * @return byte[]
	 */
	protected byte[] writeString(String text, int length) {
		return writeString(text, length, " ");
	}
	
	/**
	 * Transforma um sequencia de caracteres em uma matriz de bytes.
	 * <p>
	 * Por segurança, os caracteres especiais (acentos e demais caracteres que
	 * não sejam letras, números ou espaço em branco) são removidos.
	 * 
	 * @param text Texto para ser codificado.
	 * @param length Tamanho final do texto codificado.
	 * @param stuff Texto para ser adicionado no final da sequencia.
	 * @return byte[]
	 */
	protected byte[] writeString(String text, int length, String stuff) {
		String parsed = text;
		if (parsed == null) {
			parsed = "";
		}
		parsed = StringUtils.trim(parsed);
		if (parsed.length() < length) {
			parsed = StringUtils.rightPad(parsed, length, stuff);
		}
		if (parsed.length() > length) {
			parsed = parsed.substring(0, length);
		}
		return parsed.getBytes();
	}

	/**
	 * Codifica uma data para o formato da mensagem, baseado na estrutura:
	 * <table border="1">
	 * 	<th>
	 * 		<tr>
	 * 			<td>
	 * 				<b>Campo:</b>
	 * 			</td>
	 * 			<td>
	 * 				<b>Tamanho (bytes):</b>
	 * 			</td>
	 * 			<td>
	 * 				<b>Descrição:</b>
	 * 			</td>
	 * 		</tr>
	 * 	</th>
	 * 	<tb>
	 * 		<tr>
	 * 			<td>dia</td>
	 * 			<td>1</td>
	 * 			<td>Dia do mês, de 1 a 31.
	 * 		</tr>
	 * 		<tr>
	 * 			<td>mês</td>
	 * 			<td>1</td>
	 * 			<td>Mês, de 1 a 12.
	 * 		</tr>
	 * 		<tr>
	 * 			<td>ano</td>
	 * 			<td>1</td>
	 * 			<td>Possui como base o ano 2000. (0 = 2000, 1 = 2001...).
	 * 		</tr>
	 * 		<tr>
	 * 			<td>hora</td>
	 * 			<td>1</td>
	 * 			<td>Hora do dia, de 0 a 23
	 * 		</tr>
	 * 		<tr>
	 * 			<td>minuto</td>
	 * 			<td>1</td>
	 * 			<td>Minutos de 0 a 59
	 * 		</tr>
	 * 		<tr>
	 * 			<td>segundo</td>
	 * 			<td>1</td>
	 * 			<td>Segundos de 0 a 59
	 * 		</tr>
	 * 	</tb>
	 * </table>
	 * @param date {@link Date}
	 * @return byte[]
	 */
	protected byte[] writeDate(Date date) {
		byte[] result = new byte[DATE_STRUCT_LENGTH];
		
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		
		result[0] = (byte) calendar.get(Calendar.DATE);
		result[1] = (byte) (calendar.get(Calendar.MONTH) + MONTH_BASE);
		result[2] = (byte) (calendar.get(Calendar.YEAR) - YEAR_BASE);
		result[3] = (byte) calendar.get(Calendar.HOUR_OF_DAY);
		result[4] = (byte) calendar.get(Calendar.MINUTE);
		result[5] = (byte) calendar.get(Calendar.SECOND);
		
		return result;
	}

	/**
	 * Verifica se os parâmetros podem ser decompostos para o formato da mensagem e
	 * retorna um {@link ByteBuffer} para leitura dos campos, já excluindo o 
	 * tamanho da chave ({@link MessageKey} da mensagem.
	 * @param buffer Matriz de bytes (byte[]).
	 * @param length Tamanho da mensagem codificada.
	 * @return {@link ByteBuffer}
	 * @throws {@link MessageRuntimeException}
	 */
	protected ByteBuffer validateBuffer(byte[] buffer, int length) {
		validateBufferNull(buffer);
		if (buffer.length != length) {
			String message = MessageFormat.format("Tamanho da mensagem {0} está incorreto. É esperado {1}.", buffer.length, length);
			throw new MessageRuntimeException(message);
		}
		return ByteBuffer.wrap(buffer, MessageKey.LENGTH, length - MessageKey.LENGTH);
	}

	/**
	 * Verifica se a mensagem codificada foi realmente informada.
	 * @param buffer Matriz de bytes (byte[]).
	 * @throws {@link MessageRuntimeException}
	 */
	private void validateBufferNull(byte[] buffer) {
		if (buffer == null) {
			String message = "Dados não foram inforamdos corretamente. A mensagem é nula";
			throw new MessageRuntimeException(message);
		}
	}
	
	/**
	 * Verifica se os parâmetros podem ser decompostos para o formato da mensagem e
	 * retorna um {@link ByteBuffer} para leitura dos campos, já excluindo o 
	 * tamanho da chave ({@link MessageKey} da mensagem.
	 * @param buffer Matriz de bytes (byte[]).
	 * @param minLength Tamanho mínimo para a mensagem codificada.
	 * @return {@link ByteBuffer}
	 * @throws {@link MessageRuntimeException}
	 */
	protected ByteBuffer validateMinimumBuffer(byte[] buffer, int minLength) {
		validateBufferNull(buffer);
		if (buffer.length < minLength) {
			String message = MessageFormat.format("Tamanho da mensagem {0} está incorreto. É esperado um valor maior que {1}.", buffer.length, minLength);
			throw new MessageRuntimeException(message);
		}
		return ByteBuffer.wrap(buffer, MessageKey.LENGTH, buffer.length - MessageKey.LENGTH);
	}
	
	/**
	 * Lê um HASH de uma estrutura de bytes.
	 * @param buffer {@link ByteBuffer}
	 * @return HASH em sua representação hexadecimal.
	 */
	protected String readHash(ByteBuffer buffer) {
		byte[] hash = getBytes(buffer, HASH_LENGTH);
		return ByteHelper.hexify(hash);
	}
	
	/**
	 * Escreve uma expressão de HASH em formato hexadecimal para o formato 
	 * utilizado nas mensagems.
	 * @param hash HASH hexadecimal.
	 * @return byte[]
	 */
	protected byte[] writeHash(String hash) {
		ByteBuffer buffer = ByteBuffer.allocate(HASH_LENGTH);
		byte[] aux = ByteHelper.parseHexString(hash);
		if (aux.length < HASH_LENGTH) {
			byte[] stuff = new byte[HASH_LENGTH - aux.length];
			buffer.put(stuff);
		}
		buffer.put(aux);
		buffer.flip();
		return buffer.array();
	}
	
	/**
	 * Drena uma matriz de bytes.
	 * @param buffer Matriz de bytes para ser drenada
	 * @param size Tamanho da rajada para drenar.
	 * @return byte[]
	 */
	private byte[] getBytes(ByteBuffer buffer, int size) {
		byte[] result = new byte[size];
		buffer.get(result, 0, result.length);
		return result;
	}	
}
