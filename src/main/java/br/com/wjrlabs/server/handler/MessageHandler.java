package br.com.wjrlabs.server.handler;

import java.net.SocketAddress;
import java.util.Arrays;
import java.util.stream.Collectors;

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
        log.debug("ChannelHandlerContext: {}.", ctx.toString());

        //ExecutorFactory.getInstance(msg);
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
        if (log.isDebugEnabled()) {
            log.debug("Received Message: {}.", msg.toString());
			for (int i = 0; i < in.capacity(); i++) {
				byte b = in.getByte(i);
				System.out.print((char) b);
			}
        }
        ctx.write(in);
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
}
