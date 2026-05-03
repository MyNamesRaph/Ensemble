package com.mynamesraph.ensemble.audio

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance
import net.minecraft.client.resources.sounds.SoundInstance
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundSource
import net.minecraft.world.level.block.NoteBlock

class NoteSoundInstance(
    event: SoundEvent,
    val soundSource: NoteSoundSource
) : AbstractTickableSoundInstance(event, SoundSource.RECORDS, SoundInstance.createUnseededRandom()) {

    init {
        //TODO replace with proper implementation
        pitch = NoteBlock.getPitchFromNote(soundSource.note.pitchValue)
    }

    override fun isLooping(): Boolean = true

    override fun tick() {
        val player = this.soundSource.player
        if (player.isRemoved || player.isDeadOrDying) {
            this.stop()
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

}