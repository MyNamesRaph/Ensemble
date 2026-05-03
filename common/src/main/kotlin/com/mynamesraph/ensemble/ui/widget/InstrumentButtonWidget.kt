package com.mynamesraph.ensemble.ui.widget

import com.mynamesraph.ensemble.Notes
import com.mynamesraph.ensemble.audio.NoteSoundManager
import com.mynamesraph.ensemble.platform.Services
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Font
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.components.AbstractWidget
import net.minecraft.client.gui.narration.NarrationElementOutput
import net.minecraft.client.input.KeyEvent
import net.minecraft.client.renderer.RenderPipelines
import net.minecraft.network.chat.Component
import net.minecraft.resources.Identifier
import net.minecraft.util.ARGB
import net.minecraft.util.CommonColors

class InstrumentButtonWidget(
    x: Int,
    y: Int,
    val note: Notes,
    val font: Font,
    val texture: Identifier,
    val texturePressed: Identifier,
    ) : AbstractWidget(x, y, 32, 32, Component.empty()) {

    val keyMapping = Services.PLATFORM.keyMap()[note.keyMapping]!!

    val textX = x + (width/2)
    val textY = y + (height/2)
    val keyTextY = y + (height/2) + (font.lineHeight*1.5).toInt() + 2

    var isPressed = false

    override fun extractWidgetRenderState(
        graphics: GuiGraphicsExtractor,
        mouseX: Int,
        mouseY: Int,
        delta: Float
    ) {
        graphics.blit(
            RenderPipelines.GUI_TEXTURED,
            if (isPressed) texturePressed else texture,
            x,
            y,
            0.0f,
            0.0f,
            width,
            height,
            width,
            height
        )

        graphics.centeredText(font,note.simpleNoteName.uppercase(),textX,textY, if (isPressed) ARGB.color(112,112,112) else CommonColors.TEXT_GRAY)

        val bgOffset = (font.width(keyMapping.translatedKeyMessage)/2)+2

        graphics.fill(textX-bgOffset,keyTextY-2,textX+bgOffset,keyTextY+font.lineHeight, 1325400064)
        graphics.centeredText(font,keyMapping.translatedKeyMessage,textX,keyTextY, CommonColors.TEXT_GRAY)
    }

    override fun updateWidgetNarration(p0: NarrationElementOutput) {
        return
    }

    fun screenKeyPressed(event: KeyEvent) {
        if (keyMapping.matches(event)) {
            isPressed = true
            NoteSoundManager.instanceForPlayer(Minecraft.getInstance().player!!).playNote(note,true)
        }
    }

    fun screenKeyReleased(event: KeyEvent) {
        if (keyMapping.matches(event)) {
            isPressed = false
            NoteSoundManager.instanceForPlayer(Minecraft.getInstance().player!!).stopNote(note,true)
        }
    }

}