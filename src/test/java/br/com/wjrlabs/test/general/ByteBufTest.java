package br.com.wjrlabs.test.general;

import java.nio.charset.Charset;

import org.junit.Test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ByteBufTest {

	@Test
	public void test() {
		Charset utf8 = Charset.forName("UTF-8");
		ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
		log.debug("getByte 1 {}", (char) buf.getByte(0));
		int readerIndex = buf.readerIndex();
		int writerIndex = buf.writerIndex();
		buf.setByte(0, (byte) 'B');
		log.debug("getByte 2 {}", (char) buf.getByte(0));
		assert readerIndex == buf.readerIndex();
		assert writerIndex == buf.writerIndex();
	}

}
