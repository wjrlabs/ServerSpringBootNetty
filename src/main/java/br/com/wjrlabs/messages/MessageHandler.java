package br.com.wjrlabs.messages;

import java.net.SocketAddress;

import org.springframework.stereotype.Component;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
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
public class MessageHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketAddress address = ctx.channel().remoteAddress();
        log.info("Connecting with {}.", address);
    
    }

    @Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("channelInactive");
		}
        log.info("{} disconnected!", ctx.channel().remoteAddress());

	}


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    	log.error("exceptionCaught {}",cause);
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {

		ByteBuf newBF=Unpooled.copiedBuffer(msg);
        log.debug("ChannelHandlerContext channelRead0: {}.", ctx.toString());

        log.debug("ByteBuf channelRead0: {}	", msg.toString(CharsetUtil.UTF_8));
        
        ctx.writeAndFlush(newBF);    

        
	}
}
