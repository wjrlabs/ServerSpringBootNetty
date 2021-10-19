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
package br.com.wjrlabs.server;

import java.net.InetSocketAddress;

import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

import br.com.wjrlabs.exceptions.InternalRuntimeException;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class TCPServer {

    private final ServerBootstrap serverBootstrap;

    private final InetSocketAddress tcpPort;

    private Channel serverChannel;

    public void start() {
        try {
            ChannelFuture serverChannelFuture = serverBootstrap.bind(tcpPort).sync();
            log.info("Server started : port {}", tcpPort.getPort());
            serverChannel = serverChannelFuture.channel().closeFuture().sync().channel();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error(e.getMessage());
            throw new InternalRuntimeException(e.getMessage());
        }
        catch (Exception e) {
            log.error(e.getMessage());
            throw new InternalRuntimeException(e.getMessage());
		}
    }

    @PreDestroy
    public void stop() {
    	try {
            if ( serverChannel != null ) {
                serverChannel.close();
                serverChannel.parent().close();
            }
		} catch (Exception e) {
            log.error(e.getMessage());
            throw new InternalRuntimeException(e.getMessage());
		}

    }
}
