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
import tech.feldman.betterrecords.BetterRecordsConfig
import tech.feldman.betterrecords.block.tile.TileLaser
import tech.feldman.betterrecords.client.model.ModelLaser
import com.mojang.blaze3d.platform.GlStateManager.*
import net.minecraft.client.renderer.tileentity.TileEntityRenderer
import net.minecraft.util.ResourceLocation
import org.lwjgl.opengl.GL11

class RenderLaser : TileEntityRenderer<TileLaser>() {

    val MODEL = ModelLaser()
    val TEXTURE = ResourceLocation(MOD_ID, "textures/models/laser.png")

    override fun render(te: TileLaser?, x: Double, y: Double, z: Double, scale: Float, destroyStage: Int) {

        pushMatrix()

        translatef(x.toFloat() + 0.5f, y.toFloat() + 1.5f, z.toFloat() + 0.5f)
        rotatef(180f, 0.0f, 0.0f, 1.0f)

        bindTexture(TEXTURE)

        val yaw = te?.yaw ?: 0F
        val pitch = te?.pitch ?: 0F
        MODEL.render(null, pitch, yaw, 0.0f, 0.0f, 0.0f, 0.0625f)

        rotatef(-180f, 0.0f, 0.0f, 1.0f)
        translatef(0.0f, -.926f, 0.0f)

        te?.let {
            if (te.bass != 0F && BetterRecordsConfig.CLIENT.flashMode.get() > 0) {
                pushMatrix()

                disableTexture()
                enableBlend()
                disableCull()

                rotatef(-te.yaw + 180f, 0f, 1f, 0f)
                rotatef(-te.pitch + 90f, 1f, 0f, 0f)

                val length = te.length
                val width = te.bass / 400f

                begin(GL11.GL_QUADS)

                color4f(te.r, te.g, te.b, if (BetterRecordsConfig.CLIENT.flashMode.get() == 1) .3f else .8f)

                vertex3f(width, 0f, -width)
                vertex3f(-width, 0f, -width)
                vertex3f(-width, length.toFloat(), -width)
                vertex3f(width, length.toFloat(), -width)

                vertex3f(-width, 0f, width)
                vertex3f(width, 0f, width)
                vertex3f(width, length.toFloat(), width)
                vertex3f(-width, length.toFloat(), width)

                vertex3f(width, 0f, width)
                vertex3f(width, 0f, -width)
                vertex3f(width, length.toFloat(), -width)
                vertex3f(width, length.toFloat(), width)

                vertex3f(-width, 0f, -width)
                vertex3f(-width, 0f, width)
                vertex3f(-width, length.toFloat(), width)
                vertex3f(-width, length.toFloat(), -width)

                end()

                enableCull()
                disableBlend()
                enableTexture()

                GL11.glPopMatrix()

                color4f(1f, 1f, 1f, 1f)
            }
        }

        popMatrix()
    }
}
