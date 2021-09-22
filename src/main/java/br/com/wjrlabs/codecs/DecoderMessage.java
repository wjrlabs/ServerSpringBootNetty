package br.com.wjrlabs.codecs;

import java.util.List;

import br.com.wjrlabs.codecs.exceptions.DecoderException;
import br.com.wjrlabs.commom.session.DeviceSessionManager;
import br.com.wjrlabs.core.Message;
import br.com.wjrlabs.core.MessageFactory;
import br.com.wjrlabs.messages.MessageEcho;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Wagner Alves 
 * Decode byte array to {@link Message}
 */
@Slf4j
@Sharable
public class DecoderMessage extends MessageToMessageDecoder<byte[]> {

	private DeviceSessionManager manager;

	public DecoderMessage() {
		manager = DeviceSessionManager.getInstance();

		MessageFactory.register(MessageEcho.class);
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, byte[] msg, List<Object> out) throws Exception {
		if (log.isTraceEnabled()) {
			// Recuperando a sessão do módulo:
//			Session session = manager.getSessionID(ctx.channel().hashCode());
//			log.trace(MessageFormat.format("Module: {0} ({1} - {2}) <-- ({3}): {4}", 						
//												session.getParam(ModuleSession.MODULE_ID),
//												session.getParam(ModuleSession.SYMBOL),
//												session.getParam(ModuleSession.MODULE_TYPE),
//												ctx.channel().remoteAddress(),
//												StringUtil.toHexSpaced(msg)));
		}

		Message message = null;
		try {
			message = MessageFactory.newInstance(msg);
		} catch (Exception e) {
			// Recuperando a sessão do módulo:
//			Session session = manager.getSessionID(ctx.channel().hashCode());
//			log.error(MessageFormat.format("Module: {0} ({1} - {2}) <-- ({3}): {4}", 						
//												session.getParam(ModuleSession.MODULE_ID),
//												session.getParam(ModuleSession.SYMBOL),
//												session.getParam(ModuleSession.MODULE_TYPE),
//												ctx.channel().remoteAddress(),
//												e.getMessage()));
			throw new DecoderException(msg, e);
		}

		if (log.isTraceEnabled()) {
			// Recuperando a sessão do módulo:
//			Session session = manager.getSessionID(ctx.channel().hashCode());
//			log.trace(MessageFormat.format("Module: {0} ({1} - {2})  <-- ({3}) Encontrado: {4}", 						
//												session.getParam(ModuleSession.MODULE_ID),
//												session.getParam(ModuleSession.SYMBOL),
//												session.getParam(ModuleSession.MODULE_TYPE),
//												ctx.channel().remoteAddress(),
//												message));
		}

		out.add(message);
	}
}
