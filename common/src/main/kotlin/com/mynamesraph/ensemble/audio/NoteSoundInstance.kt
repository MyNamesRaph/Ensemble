package com.mynamesraph.ensemble.audio

import com.mojang.authlib.minecraft.client.MinecraftClient
import com.mynamesraph.ensemble.Constants
import net.minecraft.client.Minecraft
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance
import net.minecraft.client.resources.sounds.SoundInstance
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundSource
import net.minecraft.world.level.block.NoteBlock
import net.minecraft.world.phys.Vec3


class NoteSoundInstance(
    event: SoundEvent,
    val soundSource: NoteSoundSource
) : AbstractTickableSoundInstance(event, SoundSource.RECORDS, SoundInstance.createUnseededRandom()) {

    val localPlayer = Minecraft.getInstance().player!!
    val hearingDistance = 64

    init {
        //TODO replace with proper implementation
        pitch = NoteBlock.getPitchFromNote(soundSource.note.pitchValue)
        volume = 1f
        attenuation = SoundInstance.Attenuation.NONE
    }

    override fun isLooping(): Boolean = true

    override fun tick() {
        val player = this.soundSource.player
        if (player.isRemoved || player.isDeadOrDying || !player.closerThan(player,70.0)) {
            this.stop()
            NoteSoundManager.clearForPlayer(player)
            return
        }
        updatePosition()
    }

    private fun updatePosition() {
        val player = this.soundSource.player
        this.x = player.position().x
        this.y = player.position().y
        this.z = player.position().z
    }

    override fun getVolume(): Float {
        val defaultVolume = super.getVolume()
        return defaultVolume * linearFalloff()
    }

    private fun linearFalloff(): Float {
        val soundPosition: Vec3 = this.soundSource.player.position()
        val distanceToPlayer = localPlayer.position().distanceTo(soundPosition)
        return 1 - (distanceToPlayer / hearingDistance).toFloat()
    }
}