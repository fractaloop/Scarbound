package com.fractaloop.scarbound.netty

import org.jboss.netty.buffer.ChannelBuffer
import org.jboss.netty.channel.{Channel, ChannelHandlerContext}
import org.jboss.netty.handler.codec.frame.FrameDecoder

/**
 * Decodes Starbound frames
 *
 * See: Base Packet @ http://starbound-dev.org/networking/
 */
class StarboundDecoder extends FrameDecoder {
  override def decode(ctx: ChannelHandlerContext, channel: Channel, buffer: ChannelBuffer): Object = {
    // We need 1 byte for the msg type and at least 1 byte to determine length
    if (buffer.readableBytes() < 2)
      return null;

    // Mark this position
    buffer.markReaderIndex();

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
    println("Decode: " + objectId + " with " + length + " byte payload")
    buffer.resetReaderIndex()
    buffer.readBytes(1 + count + Math.abs(length))
  }
}
