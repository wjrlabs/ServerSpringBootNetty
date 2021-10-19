package br.com.wjrlabs.messages;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import br.com.wjrlabs.messages.protocol.echo.MessageEcho;
import br.com.wjrlabs.messages.protocol.nack.MessageNack;

public enum MessageType {

	NACK(0xEE,0xEE, MessageNack.class.getName()),
	ECHO(0x01,0x01,MessageEcho.class.getName());


	private short idRequest;
	private short idResponse;
	private String refClassname;
	private static final Map<String, MessageType> ENUM_MAP;

	MessageType(int idRequest,int idResponse,String refClassname) {
		this.idRequest=(byte)idRequest;
		this.idResponse=(byte)idResponse;
		this.refClassname = refClassname;
	}

	static {
		Map<String, MessageType> map = new ConcurrentHashMap<String, MessageType>();
		for (MessageType instance : MessageType.values()) {
			map.put(instance.refClassname, instance);
		}
		ENUM_MAP = Collections.unmodifiableMap(map);
	}

	public static MessageType get(String name) {
		return ENUM_MAP.get(name);
	}

	public static MessageType valueOf(byte value) {
		MessageType result = null;
		for (MessageType item : MessageType.values()) {
			if (item.getIdRequest() == value) {
				result = item;
				break;
			}
		}
		return result;
	}

	public byte getIdRequest() {
		return (byte) (idRequest & 0xFF);
	}

	public byte getIdResponse() {
		return (byte) (idResponse & 0xFF);
	}
}
