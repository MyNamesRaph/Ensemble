package com.mynamesraph.ensemble.audio

import com.mynamesraph.ensemble.Notes
import com.mynamesraph.ensemble.networking.ServerBoundNoteSoundPayload
import com.mynamesraph.ensemble.platform.Services
import com.mynamesraph.ensemble.registry.CommonSounds
import com.mynamesraph.ensemble.registry.EnsembleSounds
import net.minecraft.client.Minecraft
import net.minecraft.world.entity.LivingEntity
import kotlin.collections.get

class NoteSoundManager(player: LivingEntity) {
    private val minecraft = Minecraft.getInstance()

    companion object {
        private val instances : HashMap<LivingEntity, NoteSoundManager> = HashMap()

        fun instanceForPlayer(player: LivingEntity) : NoteSoundManager {
            if (!instances.contains(player)) {
                instances.put(player, NoteSoundManager(player))
            }

            return instances[player]!!
        }

        fun clearForPlayer(player: LivingEntity) {
            if (instances.contains(player)) {
                for (note in Notes.entries) {
                    val instance = instances[player]
                    instance?.stopNote(note,false)
                }
                instances.remove(player)
            }
        }
    }



    private val soundSources: HashMap<Notes, NoteSoundSource> = HashMap(
        Notes.entries.associateWith {
            NoteSoundSource(player,it)
        }
    )

    private val soundInstances: HashMap<NoteSoundSource, NoteSoundInstance> = HashMap()


    fun playNote(note: Notes, syncToServer: Boolean) {
        if (!soundInstances.contains(soundSources[note])) {
            soundInstances.put(
                soundSources[note]!!,NoteSoundInstance(
                    //TODO: Get the instrument to play from item
                    if (note.baseOctave == 1)
                        CommonSounds.map[EnsembleSounds.EUPHONIUM_C2]!!
                    else
                        CommonSounds.map[EnsembleSounds.EUPHONIUM_C3]!!,
                    soundSources[note]!!
                )
            )

            val instance = soundInstances[soundSources[note]]

            if (instance != null) {
                minecraft.soundManager.play(instance)
                if (syncToServer) {
                    Services.PLATFORM.sendServerboundPacket(ServerBoundNoteSoundPayload(note.ordinal.toByte(),true))
                }
            }
        }
    }

    fun stopNote(note: Notes, syncToServer: Boolean) {
        if (soundInstances.contains(soundSources[note])) {
            minecraft.soundManager.stop(soundInstances[soundSources[note]]!!)
            soundInstances.remove(soundSources[note])
            if (syncToServer) {
                Services.PLATFORM.sendServerboundPacket(ServerBoundNoteSoundPayload(note.ordinal.toByte(),false))
            }
        }
    }
}