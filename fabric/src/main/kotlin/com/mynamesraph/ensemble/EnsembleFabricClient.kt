package com.mynamesraph.ensemble

import com.mynamesraph.ensemble.EnsembleCommon.ENSEMBLE_KEYS_CATEGORY
import com.mynamesraph.ensemble.registry.EnsembleKeyMappings
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper
import net.minecraft.client.KeyMapping
import net.minecraft.resources.Identifier


@Environment(EnvType.CLIENT)
object EnsembleFabricClient: ClientModInitializer {

    lateinit var keyMap: Map<EnsembleKeyMappings, KeyMapping>

    override fun onInitializeClient() {
        ENSEMBLE_KEYS_CATEGORY = KeyMapping.Category.register(
            Identifier.fromNamespaceAndPath(Constants.MOD_ID,"keys_category")
        )

        keyMap = enumValues<EnsembleKeyMappings>().associateWith {
            KeyMappingHelper.registerKeyMapping(it.keyMapping)
        }
    }
}