package br.com.wjrlabs.core;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.StringUtils;

import br.com.wjrlabs.commom.exceptions.MessageRuntimeException;
import br.com.wjrlabs.util.ByteHelper;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class SimpleMessage implements Serializable, Message {

	private static final long serialVersionUID = 6025068535670247701L;

	private static final int DATE_STRUCT_LENGTH = 6;

	private static final byte MONTH_BASE = 1;

	private static final int YEAR_BASE = 2000;

	private static final int HASH_LENGTH = 20;

	private MessageKey key;

	public SimpleMessage(MessageKey key) {
		this.key = key;
	}

	@Override
	public abstract byte[] encode();

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

	protected String readString(ByteBuffer readable, int length) {
		byte[] buffer = new byte[length];
		for (int i = 0; i < length; i++) {
			buffer[i] = readable.get();
		}
		return new String(buffer).trim();
	}

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

	protected byte[] writeString(String text, int length) {
		return writeString(text, length, " ");
	}

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

	protected ByteBuffer validateBuffer(byte[] buffer, int length) {
		validateBufferNull(buffer);
		if (buffer.length != length) {
			String message = MessageFormat.format("Tamanho da mensagem {0} está incorreto. É esperado {1}.",
					buffer.length, length);
			throw new MessageRuntimeException(message);
		}
		return ByteBuffer.wrap(buffer, MessageKey.LENGTH, length - MessageKey.LENGTH);
	}

	private void validateBufferNull(byte[] buffer) {
		if (buffer == null) {
			String message = "Dados não foram inforamdos corretamente. A mensagem é nula";
			throw new MessageRuntimeException(message);
		}
	}

	protected ByteBuffer validateMinimumBuffer(byte[] buffer, int minLength) {
		validateBufferNull(buffer);
		if (buffer.length < minLength) {
			String message = MessageFormat.format(
					"Tamanho da mensagem {0} está incorreto. É esperado um valor maior que {1}.", buffer.length,
					minLength);
			throw new MessageRuntimeException(message);
		}
		return ByteBuffer.wrap(buffer, MessageKey.LENGTH, buffer.length - MessageKey.LENGTH);
	}

	protected String readHash(ByteBuffer buffer) {
		byte[] hash = getBytes(buffer, HASH_LENGTH);
		return ByteHelper.hexify(hash);
	}

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

	private byte[] getBytes(ByteBuffer buffer, int size) {
		byte[] result = new byte[size];
		buffer.get(result, 0, result.length);
		return result;
	}
}
