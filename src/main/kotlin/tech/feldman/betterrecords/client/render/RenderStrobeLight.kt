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
import tech.feldman.betterrecords.block.tile.TileStrobeLight
import tech.feldman.betterrecords.client.model.ModelStrobeLight
import net.minecraft.client.Minecraft
import com.mojang.blaze3d.platform.GlStateManager.*;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer
import net.minecraft.util.ResourceLocation
import org.lwjgl.opengl.GL11

class RenderStrobeLight : TileEntityRenderer<TileStrobeLight>() {

    val MODEL = ModelStrobeLight()
    val TEXTURE = ResourceLocation(MOD_ID, "textures/models/strobelight.png")

    override fun render(te: TileStrobeLight?, x: Double, y: Double, z: Double, scale: Float, destroyStage: Int) {

        pushMatrix()

        translatef(x.toFloat() + 0.5f, y.toFloat() + 1.5f, z.toFloat() + 0.5f)
        rotatef(180f, 0.0f, 0.0f, 1.0f)

        bindTexture(TEXTURE)
        MODEL.render(null, 0f, 0f, 0f, 0.0f, 0.0f, 0.0625f)

        translatef(0.0f, 1.0f, 0.0f)

        te?.let {

            if (te.bass != 0F && BetterRecordsConfig.CLIENT.flashMode.get() > 0) {
                val incr = (2 * Math.PI / 10).toFloat()

                pushMatrix()

                rotatef(Minecraft.getInstance().renderManager.playerViewY - 180f, 0f, 1f, 0f)
                rotatef(Minecraft.getInstance().renderManager.playerViewX, 1f, 0f, 0f)

                disableDepthTest()
                disableLighting()
                disableTexture()
                enableBlend()

                var trans = .2f
                while (trans < .6f) {
                    scalef(.9f, .9f, 1f)
                    rotatef(20f, 0f, 0f, 1f)
                    begin(GL11.GL_TRIANGLE_FAN)

                    color4f(1f, 1f, 1f, trans / if (BetterRecordsConfig.CLIENT.flashMode.get() == 1) 3f else 1f)
                    GL11.glVertex2f(0f, 0f)

                    for (i in 0..9) {
                        val angle = incr * i
                        val xx = Math.cos(angle.toDouble()).toFloat() * te.bass
                        val yy = Math.sin(angle.toDouble()).toFloat() * te.bass

                        GL11.glVertex2f(xx, yy)
                    }

                    GL11.glVertex2f(te.bass, 0f)

                    end()
                    trans += .2f
                }

                disableBlend()
                enableTexture()
                enableLighting()
                enableDepthTest()

                popMatrix()

                color4f(1f, 1f, 1f, 1f)

                if (BetterRecordsConfig.CLIENT.flashMode.get() > 1) {
                    val mc = Minecraft.getInstance()
                    val dist = Math.sqrt(Math.pow(te.pos.x - mc.player.posX, 2.0) + Math.pow(te.pos.y - mc.player.posY, 2.0) + Math.pow(te.pos.z - mc.player.posZ, 2.0)).toFloat()
                    if (dist < 4 * te.bass) {
                        val newStrobe = Math.abs(dist - 4f * te.bass) / 100f
                        // TODO if (newStrobe > 0f && BetterEventHandler.strobeLinger < newStrobe)
                        //BetterEventHandler.strobeLinger = newStrobe
                    }
                }
            }
        }

        popMatrix()
    }
}
