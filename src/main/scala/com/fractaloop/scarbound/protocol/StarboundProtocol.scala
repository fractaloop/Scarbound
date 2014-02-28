package com.fractaloop.scarbound.protocol

import com.fractaloop.scarbound.protocol.DataTypes._
import com.fractaloop.scarbound.server._
import sbinary.{Output, Input, DefaultProtocol, Format}
import sbinary.Operations._
import com.fractaloop.scarbound.server.NotImplemented
import org.slf4j.LoggerFactory

object StarboundProtocol extends DefaultProtocol {
  import DataTypesProtocol._

  val log = LoggerFactory.getLogger(this.getClass)

  implicit object PayloadFormat extends Format[Payload] {
    def reads(in: Input) = {
      val length = read[SignedVLQ](in)
      val bytes = new Array[Byte](Math.abs(length.value))
      in.readFully(bytes)
      Payload(bytes)
    }

    def writes(out: Output, payload: Payload) {
      write[SignedVLQ](out, new SignedVLQ(payload.data.length))
      payload.data.foreach(write[Byte](out, _))
    }
  }

  implicit object NotImplementedFormat extends Format[NotImplemented] {
    def writes(out: Output, value: NotImplemented) = ???
    def reads(in: Input) = {
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

      val totalBytes: Int = 1 + count + Math.abs(length)
      log.trace("Protocol: " + NotImplemented.getClass.getSimpleName + ": " + Math.abs(length) + " bytes " + (if (length < 0) "compressed " else "") + "payload (" + totalBytes + "b total)")
      val target: Array[Byte] = new Array[Byte](Math.abs(length))
      in.readTo(target)
      NotImplemented(Payload(target))
    }
  }

  implicit val StarboundFormat: Format[StarboundMessage] = asUnion[StarboundMessage](
//    wrap[ProtocolVersion, UInt32](_.version, ProtocolVersion), // 0
    wrap[NotImplemented, Payload](_.content, NotImplemented), // 0
    wrap[NotImplemented, Payload](_.content, NotImplemented), // 1
    wrap[NotImplemented, Payload](_.content, NotImplemented), // 2
    wrap[NotImplemented, Payload](_.content, NotImplemented), // 3
    wrap[NotImplemented, Payload](_.content, NotImplemented), // 4
    wrap[NotImplemented, Payload](_.content, NotImplemented), // 5
    wrap[NotImplemented, Payload](_.content, NotImplemented), // 6
    wrap[NotImplemented, Payload](_.content, NotImplemented), // 7
    wrap[NotImplemented, Payload](_.content, NotImplemented), // 8
    wrap[NotImplemented, Payload](_.content, NotImplemented), // 9
    wrap[NotImplemented, Payload](_.content, NotImplemented), // 10
    wrap[NotImplemented, Payload](_.content, NotImplemented), // 11
    wrap[NotImplemented, Payload](_.content, NotImplemented), // 12
    wrap[NotImplemented, Payload](_.content, NotImplemented), // 13
    wrap[NotImplemented, Payload](_.content, NotImplemented), // 14
    wrap[NotImplemented, Payload](_.content, NotImplemented), // 15
    wrap[NotImplemented, Payload](_.content, NotImplemented), // 16
    wrap[NotImplemented, Payload](_.content, NotImplemented), // 17
    wrap[NotImplemented, Payload](_.content, NotImplemented), // 18
    wrap[NotImplemented, Payload](_.content, NotImplemented), // 19
    wrap[NotImplemented, Payload](_.content, NotImplemented), // 20
    wrap[NotImplemented, Payload](_.content, NotImplemented), // 21
    wrap[NotImplemented, Payload](_.content, NotImplemented), // 22
    wrap[NotImplemented, Payload](_.content, NotImplemented), // 23
    wrap[NotImplemented, Payload](_.content, NotImplemented), // 24
    wrap[NotImplemented, Payload](_.content, NotImplemented), // 25
    wrap[NotImplemented, Payload](_.content, NotImplemented), // 26
    wrap[NotImplemented, Payload](_.content, NotImplemented), // 27
    wrap[NotImplemented, Payload](_.content, NotImplemented), // 28
    wrap[NotImplemented, Payload](_.content, NotImplemented), // 29
    wrap[NotImplemented, Payload](_.content, NotImplemented), // 30
    wrap[NotImplemented, Payload](_.content, NotImplemented), // 31
    wrap[NotImplemented, Payload](_.content, NotImplemented), // 32
    wrap[NotImplemented, Payload](_.content, NotImplemented), // 33
    wrap[NotImplemented, Payload](_.content, NotImplemented), // 34
    wrap[NotImplemented, Payload](_.content, NotImplemented), // 35
    wrap[NotImplemented, Payload](_.content, NotImplemented), // 36
    wrap[NotImplemented, Payload](_.content, NotImplemented), // 37
    wrap[NotImplemented, Payload](_.content, NotImplemented), // 38
    wrap[NotImplemented, Payload](_.content, NotImplemented), // 39
    wrap[NotImplemented, Payload](_.content, NotImplemented), // 40
    wrap[NotImplemented, Payload](_.content, NotImplemented), // 41
    wrap[NotImplemented, Payload](_.content, NotImplemented), // 42
    wrap[NotImplemented, Payload](_.content, NotImplemented), // 43
    wrap[NotImplemented, Payload](_.content, NotImplemented), // 44
    wrap[NotImplemented, Payload](_.content, NotImplemented), // 45
    wrap[NotImplemented, Payload](_.content, NotImplemented), // 46
    wrap[NotImplemented, Payload](_.content, NotImplemented), // 47
    wrap[NotImplemented, Payload](_.content, NotImplemented) // 48
//    asProduct3(ConnectResponse)(ConnectResponse.unapply(_).get), // 1
//    wrap[ServerDisconnect, Byte](_.unknown, ServerDisconnect(_)), // 2
//    asProductX(HandshakeChallenge)(HandshakeChallenge.unapply(_).get), // 3
//    asProductX(ChatReceived)(ChatReceived.unapply(_).get), // 4
//    asProductX(UniverseTimeUpdate)(UniverseTimeUpdate.unapply(_).get), // 5
//    asProductX(CelestialResponse)(CelestialResponse.unapply(_).get), // 6
//    asProductX(ClientConnect)(ClientConnect.unapply(_).get), // 7
//    asProductX(ClientDisconnect)(ClientDisconnect.unapply(_).get), //
//    asProductX(HandshakeResponse)(HandshakeResponse.unapply(_).get), // 9
//    asProductX(WarpCommand)(WarpCommand.unapply(_).get), // 10
//    asProductX(ChatSent)(ChatSent.unapply(_).get), // 11
//    asProductX(CelestialRequest)(CelestialRequest.unapply(_).get), // 12
//    asProductX(ClientContextUpdate)(ClientContextUpdate.unapply(_).get), // 13
//    asProductX(WorldStart)(WorldStart.unapply(_).get), // 14
//    asProductX(WorldStop)(WorldStop.unapply(_).get), // 15
//    asProductX(TileArrayUpdate)(TileArrayUpdate.unapply(_).get), // 16
//    asProductX(TileUpdate)(TileUpdate.unapply(_).get), // 17
//    asProductX(TileLiquidUpdate)(TileLiquidUpdate.unapply(_).get), // 18
//    asProductX(TileDamageUpdate)(TileDamageUpdate.unapply(_).get), // 19
//    asProductX(TileModificationFailure)(TileModificationFailure.unapply(_).get), // 20
//    asProductX(GiveItem)(GiveItem.unapply(_).get), // 21
//    asProductX(SwapInContainerResult)(SwapInContainerResult.unapply(_).get), // 22
//    asProductX(EnvironmentUpdate)(EnvironmentUpdate.unapply(_).get), // 23
//    asProductX(EntityInteractResult)(EntityInteractResult.unapply(_).get), // 24
//    asProductX(ModifyTileList)(ModifyTileList.unapply(_).get), // 25
//    asProductX(DamageTile)(DamageTile.unapply(_).get), // 26
//    asProductX(DamageTileGroup)(DamageTileGroup.unapply(_).get), // 27
//    asProductX(RequestDrop)(RequestDrop.unapply(_).get), // 28
//    asProductX(SpawnEntity)(SpawnEntity.unapply(_).get), // 29
//    asProductX(EntityInteract)(EntityInteract.unapply(_).get), // 30
//    asProductX(ConnectWire)(ConnectWire.unapply(_).get), // 31
//    asProductX(DisconnectAllWires)(DisconnectAllWires.unapply(_).get), // 32
//    asProductX(OpenContainer)(OpenContainer.unapply(_).get), // 33
//    asProductX(CloseContainer)(CloseContainer.unapply(_).get), // 34
//    asProductX(SwapInContainer)(SwapInContainer.unapply(_).get), // 35
//    asProductX(ItemApplyInContainer)(ItemApplyInContainer.unapply(_).get), // 36
//    asProductX(StartCraftingInContainer)(StartCraftingInContainer.unapply(_).get), // 37
//    asProductX(StopCraftingInContainer)(StopCraftingInContainer.unapply(_).get), // 38
//    asProductX(BurnContainer)(BurnContainer.unapply(_).get), // 39
//    asProductX(CleanContainer)(CleanContainer.unapply(_).get), // 40
//    asProductX(WorldUpdate)(WorldUpdate.unapply(_).get), // 41
//    asProductX(EntityCreate)(EntityCreate.unapply(_).get), // 42
//    asProductX(EntityUpdate)(EntityUpdate.unapply(_).get), // 43
//    asProductX(EntityDestroy)(EntityDestroy.unapply(_).get), // 44
//    asProductX(DamageNotification)(DamageNotification.unapply(_).get), // 45
//    asProductX(StatusEffectRequest)(StatusEffectRequest.unapply(_).get), // 46
//    asProductX(UpdateWorldProperties)(UpdateWorldProperties.unapply(_).get), // 47
//    asProductX(Heartbeat)(Heartbeat.unapply(_).get) // 48
  )
}
