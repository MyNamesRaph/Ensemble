package com.mynamesraph.ensemble.audio

import com.mynamesraph.ensemble.Notes
import net.minecraft.world.entity.LivingEntity

data class NoteSoundSource(val player: LivingEntity, val note: Notes)