package com.mynamesraph.ensemble.item

import com.mynamesraph.ensemble.ui.screen.InstrumentScreen
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.Component
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.stats.Stats
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.level.Level

class InstrumentItem(properties: Properties) : Item(properties) {
    override fun use(
        level: Level,
        player: Player,
        hand: InteractionHand
    ): InteractionResult {

        if (level.isClientSide) {
            Minecraft.getInstance().setScreenAndShow(
                InstrumentScreen(
                    Component.literal("instrument_screen")
                )
            )
        }

        player.awardStat(Stats.ITEM_USED.get(this))
        return InteractionResult.CONSUME
    }
}