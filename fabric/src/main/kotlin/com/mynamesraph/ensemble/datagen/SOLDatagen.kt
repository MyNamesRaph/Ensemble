package com.mynamesraph.ensemble.datagen

import com.mynamesraph.ensemble.datagen.provider.SOLModelProvider
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

class SOLDatagen : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(fabricDatagenerator: FabricDataGenerator) {
        val pack = fabricDatagenerator.createPack()

        pack.addProvider(::SOLModelProvider)
    }
}