package com.fractaloop.scarbound.netty

import org.jboss.netty.handler.codec.oneone.{OneToOneDecoder, OneToOneEncoder}
import org.jboss.netty.channel._
import org.jboss.netty.buffer.{ChannelBuffers, ChannelBuffer}
import com.fractaloop.scarbound.server.StarboundMessage
import org.slf4j.LoggerFactory

class StarboundServerEncoder extends SimpleChannelUpstreamHandler {
  val log = LoggerFactory.getLogger(classOf[StarboundServerEncoder])

  override def messageReceived(ctx: ChannelHandlerContext, e: MessageEvent) = {
    log.trace("encode for server: {}", e.getMessage)
    log.trace("{}", ChannelBuffers.hexDump(e.getMessage.asInstanceOf[(ChannelBuffer, _)]._1))
    Channels.fireMessageReceived(ctx, e.getMessage.asInstanceOf[(ChannelBuffer, _)]._1)
  }
}
