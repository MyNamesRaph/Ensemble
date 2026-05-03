package com.mynamesraph.ensemble.platform

import com.mynamesraph.ensemble.EnsembleFabricClient
import com.mynamesraph.ensemble.registry.EnsembleKeyMappings
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.client.KeyMapping
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.server.level.ServerPlayer

class FabricPlatformHelper() : com.mynamesraph.ensemble.platform.services.PlatformHelper {
    override fun getPlatformName(): String {
        return "Fabric"
    }

    override fun isModLoaded(modId: String?): Boolean {
        return FabricLoader.getInstance().isModLoaded(modId)
    }

    override fun isDevelopmentEnvironment(): Boolean {
        return FabricLoader.getInstance().isDevelopmentEnvironment
    }

    override fun keyMap(): Map<EnsembleKeyMappings, KeyMapping> {
        return EnsembleFabricClient.keyMap
    }

    override fun sendClientboundPacket(
        player: ServerPlayer,
        payload: CustomPacketPayload
    ) {
        ServerPlayNetworking.send(player,payload)
    }

    override fun sendServerboundPacket(
        payload: CustomPacketPayload
    ) {
        ClientPlayNetworking.send(payload)
    }
}