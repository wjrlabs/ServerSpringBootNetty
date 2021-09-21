package br.com.wjrlabs.codecs;

import java.text.MessageFormat;

import org.springframework.boot.web.servlet.server.Session;

import br.com.wjrlabs.commom.Message;
import br.com.wjrlabs.commom.session.ModuleSession;
import br.com.wjrlabs.commom.session.ModuleSessionManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * Transforma um objeto de {@link Message} para uma matriz de bytes.
 *
 */
@Slf4j
@Sharable
public class EncoderMessage extends MessageToByteEncoder<Message> {
	
	
	/**
	 * Gerenciamento de Sessão do SCAP.
	 */
	private ModuleSessionManager manager;

	/**
	 * Construtor padrão.
	 */
	public EncoderMessage() {
		manager = ModuleSessionManager.getInstance();
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
		if (log.isTraceEnabled()) {
			// Recuperando a sessão do módulo:
			Session session = manager.getSessionID(ctx.channel().hashCode());
			log.trace(MessageFormat.format("Module: {0} ({1} - {2}) --> ({3}): {4}", 						
												session.getParam(ModuleSession.MODULE_ID),
												session.getParam(ModuleSession.SYMBOL),
												session.getParam(ModuleSession.MODULE_TYPE),
												ctx.channel().remoteAddress(),
												msg));
		}

		out.writeBytes(msg.encode());
	}
}
