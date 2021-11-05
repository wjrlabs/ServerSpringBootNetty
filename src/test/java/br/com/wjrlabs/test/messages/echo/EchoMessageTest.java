package br.com.wjrlabs.test.messages.echo;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.wjrlabs.business.protocol.echo.CommandEcho;
import br.com.wjrlabs.messages.protocol.echo.MessageEcho;
import br.com.wjrlabs.test.buffer.EchoMessageBuffer;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application-dev.properties")
@ContextConfiguration(classes = CommandEcho.class)
public class EchoMessageTest {

	private MessageEcho messageEcho;

	@Test
	public void testMessageEcho() {
		try {
			messageEcho = new MessageEcho(EchoMessageBuffer.getBuffer());

			assertThat(messageEcho.encode(), is(equalTo(EchoMessageBuffer.getBuffer())));

		} catch (Exception e) {
		}
	}

}
