package br.com.wjrlabs.messages;

import lombok.Getter;

@Getter
public enum MessageResponseType {

	NOT_OK(0x00),
	OK(0x01),
	ERRO(0x255);

	private byte codigo;

	MessageResponseType(int codigo) {
		this.codigo=(byte)codigo;
	}

}
