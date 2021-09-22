package br.com.wjrlabs.server.handler;

import java.net.SocketAddress;

import org.springframework.stereotype.Component;

import br.com.wjrlabs.commom.session.DeviceSessionManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Wagner Alves
 * not to be <b>Sharable</b>. 
 *
 */
@Component
@Slf4j
@RequiredArgsConstructor
@ChannelHandler.Sharable
public class MessageHandler extends ChannelInboundHandlerAdapter {

    private DeviceSessionManager manager;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketAddress address = ctx.channel().remoteAddress();
        log.info("Conectado com {}.", address);
    }

    @Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("channelInactive");
		}
	}
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    	ByteBuf in = (ByteBuf) msg;
        if (log.isDebugEnabled()) {
            log.debug("Received Message: {}.", msg.toString());
			for (int i = 0; i < in.capacity(); i++) {
				byte b = in.getByte(i);
				System.out.print((char) b);
			}
        }
        ctx.write(in);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("channelReadComplete {}", ctx);
        }
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    	log.error("exceptionCaught {}",cause);
    }
}
