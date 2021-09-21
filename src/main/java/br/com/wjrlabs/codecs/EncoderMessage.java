package br.com.wjrlabs.codecs;

import java.text.MessageFormat;

import org.springframework.boot.web.servlet.server.Session;

import br.com.wjrlabs.commom.session.DeviceSession;
import br.com.wjrlabs.commom.session.DeviceSessionManager;
import br.com.wjrlabs.core.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * Transform {@link Message} to byte array
 */
@Slf4j
@Sharable
public class EncoderMessage extends MessageToByteEncoder<Message> {

	private DeviceSessionManager manager;

	public EncoderMessage() {
		manager = DeviceSessionManager.getInstance();
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
		if (log.isTraceEnabled()) {
			// Recuperando a sessão do módulo:
			/*
			 * Session session = manager.getSessionID(ctx.channel().hashCode());
			 * log.trace(MessageFormat.format("Module: {0} ({1} - {2}) --> ({3}): {4}",
			 * session.getParam(ModuleSession.MODULE_ID),
			 * session.getParam(ModuleSession.SYMBOL),
			 * session.getParam(ModuleSession.MODULE_TYPE), ctx.channel().remoteAddress(),
			 * msg));
			 */
		}

		out.writeBytes(msg.encode());
	}
}
