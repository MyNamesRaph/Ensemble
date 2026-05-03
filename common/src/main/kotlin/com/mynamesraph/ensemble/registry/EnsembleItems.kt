package com.mynamesraph.ensemble.registry


import com.mynamesraph.ensemble.item.InstrumentItem
import com.mynamesraph.ensemble.registry.data.MlItem
import net.minecraft.world.item.Item

enum class EnsembleItems(val item: MlItem) {
    EUPHONIUM(
        MlItem(
            "euphonium",
            ::InstrumentItem,
            {
                Item.Properties()
            }
        )
    )
}