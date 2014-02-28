package com.fractaloop.scarbound.server

import com.fractaloop.scarbound.protocol.DataTypes._
import java.util.UUID

sealed trait StarboundMessage

// A placeholder class
case class Payload(data: Array[Byte])
case class NotImplemented(content: Payload) extends StarboundMessage

case class ContentBlock(size: Int, data: Array[Byte]) extends StarboundMessage

case class ProtocolVersion(version: UInt32) extends StarboundMessage
case class ConnectResponse(success: Boolean, clientId: VLQ, rejectionReason: String) extends StarboundMessage
case class ServerDisconnect(unknown: Byte) extends StarboundMessage
case class HandshakeChallenge(claim: String, salt: String, rounds: Int) extends StarboundMessage
case class ChatReceived(channel: Byte, world: String, clientId: UInt32, name: String, msg: String) extends StarboundMessage
case class UniverseTimeUpdate(time: SignedVLQ) extends StarboundMessage
case class CelestialResponse() extends StarboundMessage
case class ClientConnect(digest: String, claim: Variant, uuid: Option[UUID], name: String, species: String, shipworld: Array[Byte]) extends StarboundMessage
case class ClientDisconnect(unknown: Byte) extends StarboundMessage
case class HandshakeResponse(claimResponse: String, passwordHash: String) extends StarboundMessage
// TODO Make this polymorphic to subtypes
case class WarpCommand(`type`: WarpType, coords: Option[Coordinate], name: Option[String]) extends StarboundMessage
case class ChatSent(msg: String, channel: UByte) extends StarboundMessage
case class CelestialRequest() extends StarboundMessage
case class ClientContextUpdate(context: Array[Byte]) extends StarboundMessage
case class WorldStart(planet: Variant, world: Variant, sky: Array[Byte], weather: Array[Byte], spawnX: Float, spawnY: Float, worldProperties: Variant, clientId: UInt32, local: Boolean) extends StarboundMessage
case class WorldStop(status: String) extends StarboundMessage
case class TileArrayUpdate(tileX: SignedVLQ, tileY: SignedVLQ, width: VLQ, height: VLQ, tiles: Array[NetTile]) extends StarboundMessage
case class TileUpdate(tileX: SignedVLQ, tileY: SignedVLQ, tile: NetTile) extends StarboundMessage
case class TileLiquidUpdate(tileX: SignedVLQ, tileY: SignedVLQ, unknown1: Byte, unknown2: Byte) extends StarboundMessage
case class TileDamageUpdate(tileX: Int, tileY: Int, unknown: Byte, damage: Float, damageEffect: Float, sourceX: Float, sourceY: Float, `type`: Byte) extends StarboundMessage
case class TileModificationFailure() extends StarboundMessage
case class GiveItem(name: String, count: VLQ, properties: Variant) extends StarboundMessage
case class SwapInContainerResult() extends StarboundMessage
case class EnvironmentUpdate() extends StarboundMessage
case class EntityInteractResult(clientId: UInt32, entityId: Int, results: Variant) extends StarboundMessage
case class ModifyTileList() extends StarboundMessage
case class DamageTile() extends StarboundMessage
case class DamageTileGroup() extends StarboundMessage
case class RequestDrop(entityId: SignedVLQ) extends StarboundMessage
case class SpawnEntity() extends StarboundMessage
case class EntityInteract() extends StarboundMessage
case class ConnectWire() extends StarboundMessage
case class DisconnectAllWires() extends StarboundMessage
case class OpenContainer(entityId: SignedVLQ) extends StarboundMessage
case class CloseContainer(entityId: SignedVLQ) extends StarboundMessage
case class SwapInContainer() extends StarboundMessage
case class ItemApplyInContainer() extends StarboundMessage
case class StartCraftingInContainer(entityId: SignedVLQ) extends StarboundMessage
case class StopCraftingInContainer(entityId: SignedVLQ) extends StarboundMessage
case class BurnContainer(entityId: SignedVLQ) extends StarboundMessage
case class CleanContainer(entityId: SignedVLQ) extends StarboundMessage
case class WorldUpdate(delta: Array[Byte]) extends StarboundMessage
case class EntityCreate(`type`: Byte, storeData: Array[Byte], entityId: SignedVLQ) extends StarboundMessage
case class EntityUpdate(entityId: SignedVLQ) extends StarboundMessage
case class EntityDestroy(entityId: SignedVLQ) extends StarboundMessage
case class DamageNotification(causerId: SignedVLQ, targetId: SignedVLQ, x: SignedVLQ, y: SignedVLQ, damage: SignedVLQ, `kind`: Byte, sourceKind: String, targetMaterial: String, hitResult: Byte) extends StarboundMessage
case class StatusEffectRequest(unknown1: SignedVLQ, name: String, unknown2: Variant, multiplier: Float) extends StarboundMessage
case class UpdateWorldProperties(properties: Map[String, Variant]) extends StarboundMessage
case class Heartbeat(currentStep: VLQ) extends StarboundMessage

