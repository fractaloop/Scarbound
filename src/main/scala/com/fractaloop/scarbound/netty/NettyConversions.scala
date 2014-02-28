package com.fractaloop.scarbound.netty

import org.jboss.netty.buffer.ChannelBuffer

object NettyConversions {
  implicit def channelBufferToByteArray(buffer: ChannelBuffer): Array[Byte] = {
    val result = new Array[Byte](buffer.readableBytes())
    buffer.readBytes(result)
    result
  }
}
