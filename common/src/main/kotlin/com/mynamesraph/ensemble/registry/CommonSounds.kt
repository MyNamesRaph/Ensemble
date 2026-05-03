package com.mynamesraph.ensemble.registry

import com.mynamesraph.ensemble.Constants
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.Identifier
import net.minecraft.sounds.SoundEvent

object CommonSounds {

    val map = enumValues<EnsembleSounds>().associateWith {
        Registry.register(
            BuiltInRegistries.SOUND_EVENT,
            Identifier.fromNamespaceAndPath(
                Constants.MOD_ID,
                it.identifier
            ),
            SoundEvent.createVariableRangeEvent(
                Identifier.fromNamespaceAndPath(
                    Constants.MOD_ID,
                    it.identifier
                )
            )
        )
    }
}