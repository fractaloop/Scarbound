package com.fractaloop.scarbound.netty

import org.jboss.netty.channel.{WriteCompletionEvent, ChannelHandlerContext, MessageEvent, SimpleChannelHandler}
import com.fractaloop.scarbound.protocol.StarboundProtocol
import com.fractaloop.scarbound.server.{ProtocolVersion, StarboundMessage}
import sbinary.Operations._
import com.fractaloop.scarbound.server.ProtocolVersion
import org.jboss.netty.buffer.{ChannelBuffers, ChannelBuffer}
import org.slf4j.LoggerFactory

class ScarboundHandler extends SimpleChannelHandler {
  val log = LoggerFactory.getLogger(classOf[ScarboundHandler])

  type StarboundFrame = (ChannelBuffer, StarboundMessage)

  /**
   * Invoked when [[org.jboss.netty.channel.Channel#write(Object)]] is called.
   */
  override def writeRequested(ctx: ChannelHandlerContext, e: MessageEvent) = {
    log.trace("REMOTE message to local: {}", e.getMessage)
    ctx.sendDownstream(e)
//    super.writeRequested(ctx, e)
  }

  /**
   * Invoked for upstream requests (a client sent a message to the server)
   */
  override def messageReceived(ctx: ChannelHandlerContext, e: MessageEvent) = {
    log.trace("LOCAL message to remote: {}", e.getMessage)
    val tuple = e.getMessage.asInstanceOf[StarboundFrame]
    // Process normally
    log.trace("{}", ChannelBuffers.hexDump(tuple._1))
    // We are losing the channel buffer before here!
    ctx.sendUpstream(e)
  }
}
