package com.mynamesraph.ensemble.registry

import com.mynamesraph.ensemble.Constants
import com.mynamesraph.ensemble.registry.data.MlItem
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.Identifier
import net.minecraft.world.item.Item

object FabricItems {

    fun register(item: MlItem): Item {

        val location = Identifier.fromNamespaceAndPath(Constants.MOD_ID, item.name)

        val resourceKey = ResourceKey.create(Registries.ITEM,location)
        val item = item.factory.call(*item.arguments,item.properties.get().setId(resourceKey))

        Registry.register(BuiltInRegistries.ITEM, resourceKey, item)

        return item
    }


    val map = enumValues<EnsembleItems>().associateWith { register(it.item) }
}