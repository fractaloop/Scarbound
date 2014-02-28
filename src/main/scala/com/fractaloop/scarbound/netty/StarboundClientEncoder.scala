package com.fractaloop.scarbound.netty

import org.jboss.netty.buffer.{ChannelBuffers, ChannelBuffer}
import org.jboss.netty.channel._
import org.slf4j.LoggerFactory
import com.fractaloop.scarbound.server.StarboundMessage

class StarboundClientEncoder extends SimpleChannelDownstreamHandler {
  val log = LoggerFactory.getLogger(classOf[StarboundClientEncoder])

  override def writeRequested(ctx: ChannelHandlerContext, e: MessageEvent) = {
    val tuple = e.getMessage.asInstanceOf[(ChannelBuffer, StarboundMessage)]
    log.trace("encode for client: {}", ChannelBuffers.hexDump(tuple._1))
    Channels.write(ctx, e.getFuture, tuple._1)
  }
}
