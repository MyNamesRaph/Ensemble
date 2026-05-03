package com.mynamesraph.ensemble.registry

import com.mynamesraph.ensemble.Constants
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Supplier

@Suppress("unused")
object NeoCreativeTabs {
    val CREATIVE_TABS: DeferredRegister<CreativeModeTab> = DeferredRegister.create(
        Registries.CREATIVE_MODE_TAB,
        Constants.MOD_ID
    )

    /*val SOL_CREATIVE_TAB: DeferredHolder<CreativeModeTab, CreativeModeTab> = CREATIVE_TABS.register(
        "item_group_all",
        Supplier {
            CreativeModeTab.builder()
                .title(Component.translatable("itemGroup.${Constants.MOD_ID}.all"))
                .icon { ItemStack(NeoItems.map[SOLItems.OAK_LEGENDARY_SWORD]!!.value()) }
                .displayItems { _ , output ->
                    NeoItems.map.entries.forEach { output.accept { it.value.get() } }
                }
                .build()
        }
    )*/

    fun register(eventBus: IEventBus) {
        CREATIVE_TABS.register(eventBus)
    }
}