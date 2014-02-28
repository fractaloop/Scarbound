package com.fractaloop.scarbound.protocol

object DataTypes {
  type UInt32 = Long
  type UByte = Short

  case class SignedVLQ(value: Int)

  class VLQ

  class Variant

  class Coordinate

  class NetTile

  trait WarpType {
    def value: Int
  }

  object WarpType {
    def fromInt(value: Int) = value match {
      case 1 => MoveShip
      case 2 => WarpToOwnShip
      case 3 => WarpToPlayerShip
      case 4 => WarpToOrbitedPlanet
      case 5 => WarpToHomePlanet
      case failed => throw new ClassCastException("Unknown warp type " + value)
    }
  }

  case object MoveShip extends WarpType { val value = 1 }
  case object WarpToOwnShip extends WarpType { val value = 2 }
  case object WarpToPlayerShip extends WarpType { val value = 3 }
  case object WarpToOrbitedPlanet extends WarpType { val value = 4 }
  case object WarpToHomePlanet extends WarpType { val value = 5 }
}

