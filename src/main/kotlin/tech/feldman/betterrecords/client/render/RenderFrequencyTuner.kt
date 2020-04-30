/**
 * The MIT License
 *
 * Copyright (c) 2019 Nicholas Feldman
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package tech.feldman.betterrecords.client.render

import tech.feldman.betterrecords.MOD_ID
import tech.feldman.betterrecords.block.tile.TileFrequencyTuner
import tech.feldman.betterrecords.client.model.ModelFrequencyTuner
import com.mojang.blaze3d.platform.GlStateManager.*
import net.minecraft.client.renderer.tileentity.TileEntityRenderer
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation

class RenderFrequencyTuner : TileEntityRenderer<TileFrequencyTuner>() {

    val MODEL = ModelFrequencyTuner()
    val TEXTURE = ResourceLocation(MOD_ID, "textures/models/frequencytuner.png")

    override fun render(te: TileFrequencyTuner?, x: Double, y: Double, z: Double, partialTicks: Float, destroyStage: Int) {

        pushMatrix()

        translated(x + 0.5, y + 1.5, z + 0.5)
        rotatef(180F, 0F, 0F, 1F)

        // TODO rotate
        // te?.let {
        //     rotatef(te.blockMetadata * 90 + 180.toFloat(), 0F, 1F, 0F)
        // }

        bindTexture(TEXTURE)

        val crystalFloat = te?.crystalFloaty ?: 0F
        val crystal = te?.crystal ?: ItemStack.EMPTY
        MODEL.render(null, crystalFloat, 0F, 0F, 0F, 0F, 0.0625F, crystal)

        popMatrix()
    }
}
