package br.com.wjrlabs.messages.common;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import br.com.wjrlabs.messages.protocol.echo.MessageEcho;
import br.com.wjrlabs.messages.protocol.nack.MessageNack;

public enum MessageType {

	NACK(0xEE,0xEE, MessageNack.class.getName()),
	ECHO(0x01,0x01,MessageEcho.class.getName());
	
	private short requestId;
	private short responseId;
	private String refClassname;
	private static final Map<String, MessageType> ENUM_MAP;

	MessageType(int requestId,int responseId,String refClassname) {
		this.requestId=(byte)requestId;
		this.responseId=(byte)responseId;
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
			if (item.getRequestId() == value) {
				result = item;
				break;
			}
		}
		return result;
	}

	public byte getRequestId() {
		return (byte) (requestId & 0xFF);
	}

	public byte getResponseId() {
		return (byte) (responseId & 0xFF);
	}
	public String getClassName() {
		return this.refClassname;
	}
}
