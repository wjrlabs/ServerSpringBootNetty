package br.com.wjrlabs.test.echo;

import static org.assertj.core.api.Assertions.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.SocketAddress;
import java.util.Queue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.wjrlabs.messages.MessageType;
import br.com.wjrlabs.messages.protocol.echo.MessageEcho;
import br.com.wjrlabs.server.MessageHandler;
import br.com.wjrlabs.util.ByteHelper;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.embedded.EmbeddedChannel;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-dev.properties")
public class EchoMessageTest {
	private MessageHandler messageHandler;
	
    private ChannelHandlerContext channelHandlerContext;

    private Channel channel;

    private SocketAddress remoteAddress;
    
    private final byte[] REQUEST_KEY= {MessageType.ECHO.getIdRequest()};
    private final byte[] MESSAGE_BUFFER=ByteHelper.concatenate(REQUEST_KEY,ByteHelper.parseHexString("01 02 03"));
    
	@Before
	@DisplayName("setUp")
	public void setUp() {
		log.info("Seting Up {}",this.getClass().getName());
		messageHandler=new MessageHandler();
        channelHandlerContext = mock(ChannelHandlerContext.class);
        channel = mock(Channel.class);
        remoteAddress = mock(SocketAddress.class);
	}
	
    @Test
	@DisplayName("testChannelActive")
    public void testChannelActive() throws Exception {
       	try {
            when(channelHandlerContext.channel()).thenReturn(channel);
            when(channelHandlerContext.channel().remoteAddress()).thenReturn(remoteAddress);
            messageHandler.channelActive(channelHandlerContext);
		} catch (Exception e) {
			fail(e.getMessage(), e);
		}
    }

    @Test
	@DisplayName("testChannelRead")
    public void testChannelRead() {
    	try {
    	  	log.debug("MESSAGE_BUFFER");
        	log.debug(ByteHelper.hexify(MESSAGE_BUFFER));
            when(channelHandlerContext.channel()).thenReturn(channel);
            EmbeddedChannel embeddedChannel = new EmbeddedChannel(messageHandler);
            embeddedChannel.writeOneInbound(new MessageEcho(MESSAGE_BUFFER));
            embeddedChannel.flush();
            Queue<Object> outboundMessages = embeddedChannel.outboundMessages();
            
            if(outboundMessages==null || outboundMessages.isEmpty()) {
            	fail("Echo não teve retorno.");
            }
            assertThat(((MessageEcho)outboundMessages.poll()).encode(), is(equalTo(MESSAGE_BUFFER)));

		} catch (Exception e) {
			fail(e.getMessage(), e);
		}
    }

    @After
	@DisplayName("finishingUP")
    public void finishingUP	() {
		log.info("Finishing up {}",this.getClass().getName());


    }
}
