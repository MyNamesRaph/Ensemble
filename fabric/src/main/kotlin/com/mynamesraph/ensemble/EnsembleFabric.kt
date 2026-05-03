package com.mynamesraph.ensemble


import com.mynamesraph.ensemble.audio.NoteSoundManager
import com.mynamesraph.ensemble.networking.NoteSoundPayload
import com.mynamesraph.ensemble.registry.FabricCreativeTabs
import com.mynamesraph.ensemble.registry.FabricItems
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking


object EnsembleFabric : ModInitializer {

    override fun onInitialize() {
        EnsembleCommon.init()

        FabricItems
        FabricCreativeTabs

        PayloadTypeRegistry.clientboundPlay().register(NoteSoundPayload.TYPE, NoteSoundPayload.STREAM_CODEC)
        PayloadTypeRegistry.serverboundPlay().register(NoteSoundPayload.TYPE, NoteSoundPayload.STREAM_CODEC)

        ServerPlayNetworking.registerGlobalReceiver(NoteSoundPayload.TYPE, ::onServerboundNoteSoundPayload)
        ClientPlayNetworking.registerGlobalReceiver(NoteSoundPayload.TYPE, ::onClientboundNoteSoundPayload)
    }

    fun onServerboundNoteSoundPayload(payload: NoteSoundPayload, context: ServerPlayNetworking.Context) {
        val sourcePlayer = context.player()

        if (sourcePlayer.isRemoved || sourcePlayer.isDeadOrDying) {
            return
        }

        val server = context.server()


        for (player in server.playerList.players) {
            if (player != sourcePlayer) {
                if (player.level() == sourcePlayer.level()) {
                    if (player.closerThan(sourcePlayer,128.0)) {
                        ServerPlayNetworking.send(
                            player,
                            NoteSoundPayload(payload.noteOrdinal, payload.playing)
                        )
                    }
                }
            }
        }
    }

    fun onClientboundNoteSoundPayload(payload: NoteSoundPayload,context: ClientPlayNetworking.Context) {
        val sourcePlayer = context.player()

        if (payload.noteOrdinal >= Notes.entries.size) {
            Constants.LOG.warn("Received malformed packet from server : Note ${payload.noteOrdinal} does not exist!")
            return
        }

        if (payload.playing) {
            NoteSoundManager.instanceForPlayer(sourcePlayer).playNote(Notes.entries[payload.noteOrdinal.toInt()],false)
        }
        else {
            NoteSoundManager.instanceForPlayer(sourcePlayer).stopNote(Notes.entries[payload.noteOrdinal.toInt()],false)
        }
    }
}