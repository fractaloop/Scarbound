package com.fractaloop.scarbound.netty

import org.jboss.netty.channel.{ChannelPipeline, ChannelPipelineFactory, Channels}
import org.jboss.netty.channel.socket.ClientSocketChannelFactory
import org.jboss.netty.handler.logging.LoggingHandler

class ScarboundPipelineFactory(cf: ClientSocketChannelFactory, remoteHost: String, remotePort: Int) extends ChannelPipelineFactory {
  override def getPipeline: ChannelPipeline = {
    val p = Channels.pipeline
    p.addLast("client_decoder", new StarboundClientDecoder) // client -> server
    p.addLast("client_encoder", new StarboundClientEncoder) // server -> client
    p.addLast("handler", new ScarboundHandler) // any -> any
    p.addLast("server_encoder", new StarboundServerEncoder) // client -> server
    p.addLast("server_decoder", new StarboundServerDecoder) // server -> client
    p.addLast("proxy", new ProxyHandler(cf, remoteHost, remotePort))
    p
  }
}
