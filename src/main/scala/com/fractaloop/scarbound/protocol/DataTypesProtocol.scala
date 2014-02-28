package com.fractaloop.scarbound.protocol

import sbinary.{Output, Input, Format, DefaultProtocol}
import com.fractaloop.scarbound.protocol.DataTypes.SignedVLQ

object DataTypesProtocol extends DefaultProtocol {
  implicit object SignedVLQFormat extends Format[SignedVLQ] {
    override def writes(out: Output, value: SignedVLQ): Unit = ???
    override def reads(in: Input): SignedVLQ = {
      val msb: Short = 0x80
      var value = 0
      var tmp = 0
      var count = 0
      do {
        //
        tmp = in.readByte & 0xFF
        value = (value << 7) | (tmp & 0x7f)
        // Escape if we run out of data
        count += 1
      } while ((tmp & 0x80) != 0)

      val length = value & 1 match {
        case 0 => value >> 1
        case 1 => -((value >> 1) + 1)
      }
      SignedVLQ(length)
    }
  }
}
