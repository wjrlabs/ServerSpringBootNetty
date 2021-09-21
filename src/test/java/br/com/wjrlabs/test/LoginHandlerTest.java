/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.com.wjrlabs.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.Queue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.wjrlabs.domain.ChannelRepository;
import br.com.wjrlabs.server.tcp.handler.LoginHandler;
import io.netty.channel.embedded.EmbeddedChannel;

/**
 * Test for  LoginHandler.java
 *
 */
class LoginHandlerTest {

    ChannelRepository channelRepository;

    @BeforeEach
    void setup() {
        channelRepository = mock(ChannelRepository.class);
    }

    @Test
    @DisplayName("login handler test")
    void testLogin() {
        // given
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new LoginHandler(this.channelRepository));

        // when
        embeddedChannel.writeInbound("login aaa\r\n");

        // then
        Queue<Object> outboundMessages = embeddedChannel.outboundMessages();
        assertThat(outboundMessages.poll()).isEqualTo("Successfully logged in as aaa. \r\n");
    }
}