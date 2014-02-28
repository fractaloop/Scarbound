package com.fractaloop.scarbound.netty

import org.jboss.netty.buffer.{ChannelBuffers, ChannelBuffer}
import org.jboss.netty.channel._
import org.jboss.netty.handler.codec.frame.FrameDecoder
import com.fractaloop.scarbound.protocol.StarboundProtocol
import com.fractaloop.scarbound.server.StarboundMessage
import org.slf4j.LoggerFactory
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder

/**
 * Decodes Starbound frames
 *
 * See: Base Packet @ http://starbound-dev.org/networking/
 */
class StarboundClientDecoder extends FrameDecoder {
  import sbinary._
  import Operations._
  import StarboundProtocol._
  import NettyConversions._

  type StarboundFrame = (ChannelBuffer, StarboundMessage)
  val log = LoggerFactory.getLogger(classOf[StarboundClientDecoder])

  override def decode(ctx: ChannelHandlerContext, channel: Channel, buffer: ChannelBuffer): Object = {
    // We need 1 byte for the msg type and at least 1 byte to determine length
    if (buffer.readableBytes() < 2)
      return null

    // Mark this position
    buffer.markReaderIndex()

    val objectId: Short = buffer.readUnsignedByte()

    // Decode Signed VLQ
    val msb: Short = 0x80
    var value: Int = 0
    var tmp: Short = 0
    var count = 0
    do {
      tmp = buffer.readUnsignedByte()
      value = (value << 7) | (tmp & 0x7f)
      // Escape if we run out of data
      if (buffer.readableBytes() < 1) {
        buffer.resetReaderIndex()
        return null
      }
      count += 1
    } while ((tmp & 0x80) != 0)

    val length = value & 1 match {
      case 0 => value >> 1
      case 1 => -((value >> 1) + 1)
    }

    if (Math.abs(length) > buffer.readableBytes()) {
      buffer.resetReaderIndex()
      return null
    }
    val totalBytes: Int = 1 + count + Math.abs(length)
    log.debug("Decoded: " + objectId + " with " + Math.abs(length) + " byte " + (if (length < 0) "compressed " else "") + "payload (" + totalBytes + "b total)")
    buffer.resetReaderIndex()

    val bytes = buffer.readBytes(totalBytes).array()
    buffer.resetReaderIndex()
    (buffer.readBytes(totalBytes), fromByteArray[StarboundMessage](bytes))
  }
}
