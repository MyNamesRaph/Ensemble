package com.mynamesraph.ensemble.platform

import com.mynamesraph.ensemble.registry.EnsembleKeyMappings
import net.minecraft.client.KeyMapping
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.block.Block
import net.neoforged.fml.ModList
import net.neoforged.fml.loading.FMLLoader

class NeoForgePlatformHelper : com.mynamesraph.ensemble.platform.services.PlatformHelper {
    override fun getPlatformName(): String {
        return "NeoForge"
    }

    override fun isModLoaded(modId: String?): Boolean {
        return ModList.get().isLoaded(modId)
    }

    override fun isDevelopmentEnvironment(): Boolean {
        return !FMLLoader.getCurrent().isProduction
    }

    override fun keyMap(): Map<EnsembleKeyMappings, KeyMapping> {
        TODO("Not yet implemented")
    }

    override fun sendClientboundPacket(
        player: ServerPlayer,
        payload: CustomPacketPayload
    ) {
        TODO("Not yet implemented")
    }

    override fun sendServerboundPacket(payload: CustomPacketPayload) {
        TODO("Not yet implemented")
    }
}