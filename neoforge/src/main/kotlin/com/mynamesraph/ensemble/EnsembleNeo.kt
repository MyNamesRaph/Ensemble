package com.mynamesraph.ensemble


import com.mynamesraph.ensemble.registry.NeoCreativeTabs
import com.mynamesraph.ensemble.registry.NeoItems
import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.ModContainer
import net.neoforged.fml.common.Mod

@Mod(Constants.MOD_ID)
class EnsembleNeo(eventBus: IEventBus, modContainer: ModContainer) {
    init {
        EnsembleCommon.init()
        NeoItems.register(eventBus)
        NeoCreativeTabs.register(eventBus)
    }
}