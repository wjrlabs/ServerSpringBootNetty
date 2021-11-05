package br.com.wjrlabs.test.buffer;

import java.nio.ByteBuffer;

import br.com.wjrlabs.messages.common.MessageType;
import io.netty.buffer.ByteBufUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EchoMessageBuffer {

	private static final byte[] REQUEST_KEY = {MessageType.ECHO.getRequestId()};
	
	private static byte[] MESSAGE_BUFFER =ByteBuffer.allocate(4)
													.put(REQUEST_KEY)
													.put(ByteBufUtil.decodeHexDump("010203"))
													.array();

	public static byte[] getBuffer() {
		log.debug("MESSAGE_BUFFER");
		log.debug(ByteBufUtil.hexDump(MESSAGE_BUFFER));
		return MESSAGE_BUFFER;
	}

}
