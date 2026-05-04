package com.mynamesraph.ensemble.networking

import com.mynamesraph.ensemble.Constants
import io.netty.buffer.ByteBuf
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.resources.Identifier

@JvmRecord
data class ServerBoundNoteSoundPayload(val noteOrdinal: Byte, val playing: Boolean) : CustomPacketPayload {

    override fun type(): CustomPacketPayload.Type<out CustomPacketPayload> = TYPE


    companion object {
        val TYPE = CustomPacketPayload.Type<ServerBoundNoteSoundPayload>(
            Identifier.fromNamespaceAndPath(
                Constants.MOD_ID,
                "note_payload"
            )
        )

        val STREAM_CODEC: StreamCodec<ByteBuf, ServerBoundNoteSoundPayload> = StreamCodec.composite(
            ByteBufCodecs.BYTE,
            ServerBoundNoteSoundPayload::noteOrdinal,
            ByteBufCodecs.BOOL,
            ServerBoundNoteSoundPayload::playing,
            ::ServerBoundNoteSoundPayload
        )
    }
}
