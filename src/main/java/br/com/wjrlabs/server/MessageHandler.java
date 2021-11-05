package br.com.wjrlabs.server;

import java.net.SocketAddress;

import org.springframework.stereotype.Component;

import br.com.wjrlabs.business.Command;
import br.com.wjrlabs.business.CommandFactory;
import br.com.wjrlabs.messages.Message;
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
@RequiredArgsConstructor
@Component
@Slf4j
@ChannelHandler.Sharable
public class MessageHandler extends SimpleChannelInboundHandler<Message> {
	
	private final CommandFactory commandFactory;
	
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
    	log.debug("channelRegistered");
        ctx.fireChannelRegistered();
    }
    
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    	ctx.fireChannelActive();
        SocketAddress address = ctx.channel().remoteAddress();
        log.info("Connecting with {}.", address);
    
    }
    
    @Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    	ctx.disconnect();
		if (log.isDebugEnabled()) {
			log.debug("channelInactive");
		}
        log.info("{} disconnected!", ctx.channel().remoteAddress());

	}


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    	ctx.fireExceptionCaught(cause);
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Message message) throws Exception {

		//DeviceSession session = SessionManager.getSessionID(ctx.channel().hashCode());

        Command command = commandFactory.getCommand(message);
   
        Message messageReturn = command.execute(message);
        
        ctx.write(messageReturn);
	}
	
	@Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
        ctx.fireChannelReadComplete();
    }
	

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
    	log.debug("channelUnregistered");
        ctx.fireChannelUnregistered();
    }

}
