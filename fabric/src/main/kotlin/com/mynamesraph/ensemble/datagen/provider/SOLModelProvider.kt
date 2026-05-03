package com.mynamesraph.ensemble.datagen.provider

import com.mynamesraph.ensemble.registry.FabricItems
import com.mynamesraph.ensemble.registry.EnsembleItems
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput
import net.minecraft.client.data.models.BlockModelGenerators
import net.minecraft.client.data.models.ItemModelGenerators
import net.minecraft.client.data.models.model.ModelTemplates

class SOLModelProvider(output: FabricPackOutput) : FabricModelProvider(output) {
    override fun generateBlockStateModels(generator: BlockModelGenerators) {
        //generator.createTrivialCube(FabricBlocks.map[ChiseledBlocks.CYANIDE_BLOCK]!!)
        //generator.createTrivialCube(FabricBlocks.map[ChiseledBlocks.CYANIDE_BLEND_BLOCK]!!)
    }

    override fun generateItemModels(generator: ItemModelGenerators) {
        simpleItem(EnsembleItems.EUPHONIUM, generator)
    }

    private fun simpleItem(item: EnsembleItems, generator: ItemModelGenerators) {
        generator.generateFlatItem(FabricItems.map[item]!!, ModelTemplates.FLAT_HANDHELD_ITEM)
    }
}