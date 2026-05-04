package com.mynamesraph.ensemble.networking

import com.mynamesraph.ensemble.Constants
import io.netty.buffer.ByteBuf
import net.minecraft.core.UUIDUtil
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.resources.Identifier
import java.util.UUID

@JvmRecord
data class ClientBoundNoteSoundPayload(val noteOrdinal: Byte, val playing: Boolean, val player: UUID) : CustomPacketPayload {

    override fun type(): CustomPacketPayload.Type<out CustomPacketPayload> = TYPE


    companion object {
        val TYPE = CustomPacketPayload.Type<ClientBoundNoteSoundPayload>(
            Identifier.fromNamespaceAndPath(
                Constants.MOD_ID,
                "note_payload"
            )
        )

        val STREAM_CODEC: StreamCodec<ByteBuf, ClientBoundNoteSoundPayload> = StreamCodec.composite(
            ByteBufCodecs.BYTE,
            ClientBoundNoteSoundPayload::noteOrdinal,
            ByteBufCodecs.BOOL,
            ClientBoundNoteSoundPayload::playing,
            UUIDUtil.STREAM_CODEC,
            ClientBoundNoteSoundPayload::player,
            ::ClientBoundNoteSoundPayload
        )
    }
}
