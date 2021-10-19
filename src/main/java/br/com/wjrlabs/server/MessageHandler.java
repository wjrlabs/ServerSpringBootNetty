package br.com.wjrlabs.server;

import org.springframework.stereotype.Component;

import br.com.wjrlabs.command.Command;
import br.com.wjrlabs.command.CommandFactory;
import br.com.wjrlabs.messages.Message;
import br.com.wjrlabs.session.DeviceSession;
import br.com.wjrlabs.session.SessionManager;
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
public class MessageHandler extends SimpleChannelInboundHandler<Message> {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    	ctx.fireExceptionCaught(cause);
    	log.error("exceptionCaught {}",cause);
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Message message) throws Exception {

        DeviceSession session = SessionManager.getSessionID(ctx.channel().hashCode());

        Command command = CommandFactory.getInstance(message);
   
        Message messageReturn = command.execute(message,session);
        
        ctx.write(messageReturn);
	}
	
	@Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
        ctx.fireChannelReadComplete();
    }

}
