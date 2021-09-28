package br.com.wjrlabs.messages;

import java.net.SocketAddress;

import org.springframework.stereotype.Component;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
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
public class MessageHandler extends SimpleChannelInboundHandler<Object> {

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

    private void handleArray(byte[] array, int offset, int length) {
		log.debug("handleArray");
		log.debug("offset {}",offset);
		log.debug("length {}",length);
		log.debug("array:");
		if(log.isDebugEnabled()) {
			for (int i = 0; i < array.length; i++) {
				System.out.print(String.format("%02X", i));
			}
		}
		
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

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

        log.debug("channelRead0: {}.", ctx.toString());

    	ByteBuf in = (ByteBuf) msg;
    	if (in.hasArray()) {
            log.debug("hasArray true");
    		byte[] array = in.array();
    		int offset = in.arrayOffset() + in.readerIndex();
    		int length = in.readableBytes();
    		handleArray(array, offset, length);
    	}else {
            log.debug("hasArray false");

    		int length = in.readableBytes();
    		byte[] array = new byte[length];
    		in.getBytes(in.readerIndex(), array);
    		handleArray(array, 0, length);
    	}
        ctx.write(in);
    		
	}
}
