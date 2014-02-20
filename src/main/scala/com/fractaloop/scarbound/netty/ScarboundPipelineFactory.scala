package com.fractaloop.scarbound.netty

import org.jboss.netty.channel.{ChannelPipeline, ChannelPipelineFactory, Channels}
import org.jboss.netty.channel.socket.ClientSocketChannelFactory

class ScarboundPipelineFactory(cf: ClientSocketChannelFactory, remoteHost: String, remotePort: Int) extends ChannelPipelineFactory {
  override def getPipeline: ChannelPipeline = {
    val p = Channels.pipeline
    p.addLast("decoder", new StarboundDecoder)
    p.addLast("handler", new ScarboundInboundHandler(cf, remoteHost, remotePort))
    p
  }
}
