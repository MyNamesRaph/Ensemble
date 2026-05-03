package com.mynamesraph.ensemble.ui.screen

import com.mynamesraph.ensemble.Constants
import com.mynamesraph.ensemble.Notes
import com.mynamesraph.ensemble.ui.widget.InstrumentButtonWidget
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.input.KeyEvent
import net.minecraft.network.chat.Component
import net.minecraft.resources.Identifier

//TODO: Add soundEvent data component to item and pass into this constructor
class InstrumentScreen(title: Component) : Screen(title){
    override fun isPauseScreen(): Boolean = false
    override fun isFocused(): Boolean = true
    override fun extractBackground(graphics: GuiGraphicsExtractor, mouseX: Int, mouseY: Int, a: Float) {}

    companion object {
        var CENTER_X = 0
        const val TX_X = 32
        const val TX_Y = 32
    }

    private val instrumentButtons : MutableList<InstrumentButtonWidget> = mutableListOf()

    override fun init() {
        CENTER_X = width/2

        var texture = Identifier.fromNamespaceAndPath(
            Constants.MOD_ID,
            "textures/gui/instrument_button.png"
        )

        var texturePressed = Identifier.fromNamespaceAndPath(
            Constants.MOD_ID,
            "textures/gui/instrument_button_pressed.png"
        )

        for (i in 0..6) {
            val x = CENTER_X - (TX_X * 3.5).toInt() + (i*TX_X)
            val y = (height - (height/8)) - TX_Y

            val widget = InstrumentButtonWidget(x,y,Notes.entries[i],font,texture,texturePressed)
            instrumentButtons.add(widget)
            addRenderableWidget(widget)
        }

        texture = Identifier.fromNamespaceAndPath(
            Constants.MOD_ID,
            "textures/gui/instrument_button_high.png"
        )

        texturePressed = Identifier.fromNamespaceAndPath(
            Constants.MOD_ID,
            "textures/gui/instrument_button_high_pressed.png"
        )

        for (i in 0..7) {
            val x = CENTER_X - (TX_X * 4) + (i*TX_X)
            val y = height - (height/8) - (TX_Y*2) - font.lineHeight

            if (i == 7) {
                texture = Identifier.fromNamespaceAndPath(
                    Constants.MOD_ID,
                    "textures/gui/instrument_button_higher.png"
                )
                texturePressed = Identifier.fromNamespaceAndPath(
                    Constants.MOD_ID,
                    "textures/gui/instrument_button_higher_pressed.png"
                )
            }
            val widget = InstrumentButtonWidget(x,y,Notes.entries[i+7],font,texture,texturePressed)
            instrumentButtons.add(widget)
            addRenderableWidget(widget)
        }
    }

    override fun keyPressed(event: KeyEvent): Boolean {
        for (widget in instrumentButtons) {
            widget.screenKeyPressed(event)
        }
        return super.keyPressed(event)
    }

    override fun keyReleased(event: KeyEvent): Boolean {
        for (widget in instrumentButtons) {
            widget.screenKeyReleased(event)
        }
        return super.keyReleased(event)
    }
}