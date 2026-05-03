package com.mynamesraph.ensemble.registry

import com.mynamesraph.ensemble.Constants
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.Identifier
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Supplier

object NeoItems {
    val ITEMS: DeferredRegister.Items = DeferredRegister.createItems(
        Constants.MOD_ID
    )

    val map = enumValues<EnsembleItems>().associateWith { ITEMS.register(
        it.item.name,
        Supplier {
            val resourceKey = ResourceKey.create(
                Registries.ITEM,
                Identifier.fromNamespaceAndPath(
                    Constants.MOD_ID,
                    it.item.name
                )
            )
            return@Supplier it.item.factory.call(*it.item.arguments,it.item.properties.get().setId(resourceKey))
        }
    ) }


    fun register(eventBus: IEventBus) {
        ITEMS.register(eventBus)
    }
}


