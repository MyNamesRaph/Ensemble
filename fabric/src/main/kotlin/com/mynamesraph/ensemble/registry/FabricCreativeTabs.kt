package com.mynamesraph.ensemble.registry

import com.mynamesraph.ensemble.Constants
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTabOutput
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.Identifier
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack

object FabricCreativeTabs {
    val ENSEMBLE_CREATIVE_TAB_KEY: ResourceKey<CreativeModeTab> = ResourceKey.create(
        Registries.CREATIVE_MODE_TAB,
        Identifier.fromNamespaceAndPath(
            Constants.MOD_ID,
            "item_group_all"
        )
    )

    val ENSEMBLE_CREATIVE_TAB: CreativeModeTab = FabricCreativeModeTab.builder()
        .icon { ItemStack(FabricItems.map[EnsembleItems.EUPHONIUM]!!) }
        .title(Component.translatable("itemGroup.${Constants.MOD_ID}.all"))
        .displayItems {
            parameters, output ->
            FabricItems.map.entries.forEach { item -> output.accept(item.value) }
        }
        .build()

    init {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB,ENSEMBLE_CREATIVE_TAB_KEY,ENSEMBLE_CREATIVE_TAB)
    }
}