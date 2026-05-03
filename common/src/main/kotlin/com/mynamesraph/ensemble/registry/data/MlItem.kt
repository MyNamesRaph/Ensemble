package com.mynamesraph.ensemble.registry.data

import net.minecraft.world.item.Item
import java.util.function.Supplier
import kotlin.reflect.KFunction

class MlItem(val name: String, val factory: KFunction<Item>, val properties: Supplier<Item.Properties>, vararg args: Any) {
    val arguments = args.toList().toTypedArray()
}