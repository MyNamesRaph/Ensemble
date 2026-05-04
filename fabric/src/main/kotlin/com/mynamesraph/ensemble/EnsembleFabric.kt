package com.mynamesraph.ensemble


import com.mynamesraph.ensemble.audio.NoteSoundManager
import com.mynamesraph.ensemble.networking.ClientBoundNoteSoundPayload
import com.mynamesraph.ensemble.networking.ServerBoundNoteSoundPayload
import com.mynamesraph.ensemble.registry.FabricCreativeTabs
import com.mynamesraph.ensemble.registry.FabricItems
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.client.player.AbstractClientPlayer
import net.minecraft.world.entity.player.Player


object EnsembleFabric : ModInitializer {

    override fun onInitialize() {
        EnsembleCommon.init()

        FabricItems
        FabricCreativeTabs

        PayloadTypeRegistry.clientboundPlay().register(ClientBoundNoteSoundPayload.TYPE, ClientBoundNoteSoundPayload.STREAM_CODEC)
        PayloadTypeRegistry.serverboundPlay().register(ServerBoundNoteSoundPayload.TYPE, ServerBoundNoteSoundPayload.STREAM_CODEC)

        ServerPlayNetworking.registerGlobalReceiver(ServerBoundNoteSoundPayload.TYPE, ::onServerboundNoteSoundPayload)
        ClientPlayNetworking.registerGlobalReceiver(ClientBoundNoteSoundPayload.TYPE, ::onClientboundNoteSoundPayload)
    }

    fun onServerboundNoteSoundPayload(payload: ServerBoundNoteSoundPayload, context: ServerPlayNetworking.Context) {
        val sourcePlayer = context.player()

        if (sourcePlayer.isRemoved || sourcePlayer.isDeadOrDying) {
            return
        }

        val server = context.server()


        for (player in server.playerList.players) {
            if (player != sourcePlayer) {
                if (player.level() == sourcePlayer.level()) {
                    if (player.closerThan(sourcePlayer,70.0)) {
                        ServerPlayNetworking.send(
                            player,
                            ClientBoundNoteSoundPayload(payload.noteOrdinal, payload.playing,sourcePlayer.uuid)
                        )
                    }
                }
            }
        }
    }

    fun onClientboundNoteSoundPayload(payload: ClientBoundNoteSoundPayload, context: ClientPlayNetworking.Context) {

        val players = context.client().level!!.players()

        var sourcePlayer : AbstractClientPlayer? = null

        for (player in players) {
            if (player.uuid == payload.player) {
                sourcePlayer = player
                Constants.LOG.info("SourcePlayer: ${player.name}")
            }
        }

        if (sourcePlayer == null) {
            Constants.LOG.warn("Received malformed packet from server : Player with UUID: ${payload.player} does not exist!")
            return
        }

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