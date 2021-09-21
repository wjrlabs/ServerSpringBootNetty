package br.com.wjrlabs.commom.types;

import java.text.MessageFormat;

import br.com.wjrlabs.commom.exceptions.TypeRuntimeException;

/**
 * Identificador do tipo de mensagem.
 * 

 */
public enum MessageType {

	/**
	 * Confirmação padrão de sucesso. 
	 */
	ACK(0x00),
	
	/**
	 * Notificação padrão de erro.
	 */
	NACK(0xFF),
	
	/**
	 * Solicita o reenvio da mensagem anterior.
	 */
	REPEAT(0xFE),
	
	/**
	 * Mensagem de autenticação de módulos e equipamentos.
	 */
	LOGIN(0x01),
	
	/**
	 * Mensagem para recuperar informações e parâmetros dos módulos e equipamentos.
	 */
	GET(0x02),
	
	/**
	 * Resposta para a mensagem GET (0x02).
	 */
	GET_ACK(0x03),
	
	/**
	 * Mensagem para atribuir valores aos parâmetros dos módulos e equipamentos.
	 */
	SET(0x04),
	
	/**
	 * Mensagem para informar que o módulo não reconhece um parâmetro.
	 */
	PARAM_INVALID(0x05),
	
	/**
	 * Inicia a negociação de transmissão de arquivos entre o Servidor e os
	 * módulos, oferecendo um arquivo.
	 */
	FILE_OFFER(0x06),
	
	/**
	 * Encaminha uma parte de um arquivo, que tenha sido ofertado anteriormente.
	 */
	FILE_PARTIAL(0x07),
	
	/**
	 * Solicita a confirmação de entrega e integridade do arquivo transmitido.
	 */
	FILE_CHECK(0x08),
	
	/**
	 * Notifica a falta de alguma parte na entrega do arquivo.
	 */
	FILE_PARTIAL_MISSING(0x09),
	
	/**
	 * Notifica uma transação válida de Edmonson.
	 */
	EDMONSON(0x0A),
	
	/**
	 * Notifica uma transação válida de Bilhete Único.
	 */
	BU(0x0B),
	
	/**
	 * Notifica uma transação válida de Bilhete Ônibus Metropolitano.
	 */
	BOM(0xB0),
	
	/**
	 * Notifica a passagem de um usuário pelo equipamento.
	 */
	PASSAGE(0x0C),
	
	/**
	 * Erro operacional com o equipamento.
	 */
	EXCEPTION(0x0D),
	
	/**
	 * Informa a ocorrência de uma falha de hardware.
	 */
	FAILURE(0x0E),
	
	/**
	 * Informa o reestabelecimento do hardarware (fim da falha).
	 */
	FAILURE_RESTORE(0x0F),
	
	/**
	 * Negação de passagem
	 */
	DENIAL(0x10),
	
	/**
	 * Ajuste de relógio.
	 */
	TIMER(0xFD);
	
	/**
	 * Valor de transmissão do tipo de mensagem.
	 */
	private byte value;
	
	/**
	 * Construtor oculto
	 * @param value Valor de transmissão da mensagem.
	 */
	private MessageType(int value) {
		this.value = (byte) value;
	}
	
	/**
	 * Recupera o valor de transmissão da mensagem.
	 * @return byte
	 */
	public byte getValue() {
		return value;
	}
	
	/**
	 * Recupera o tipo de mensagem consultando seu valor de transmissão.
	 * @param value Valor de Transmissão do tipo de mensagem.
	 * @return {@link MessageType}
	 */
	public static MessageType valueOf(byte value) {
		MessageType result = null;
		for (MessageType item : MessageType.values()) {
			if (item.getValue() == value) {
				result = item;
				break;
			}
		}
		if (result == null) {
			throw new TypeRuntimeException(
					MessageFormat.format("Valor {0} não pode ser traduzido para {1}.", 
							value, MessageType.class.getName()));
		}
		return result;
	}
}
