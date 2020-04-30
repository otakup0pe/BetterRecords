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
package tech.feldman.betterrecords.client.model

import net.minecraft.client.renderer.model.Model
import net.minecraft.client.renderer.entity.model.RendererModel
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.entity.Entity
import net.minecraft.item.ItemStack
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import java.awt.Color

@OnlyIn(Dist.CLIENT)
class ModelFrequencyTuner : Model() {
    var Stand: RendererModel
    var Crystal_3: RendererModel
    var Platform: RendererModel
    var TunerBridge: RendererModel
    var TunerWeight: RendererModel
    var Crystal_4: RendererModel
    var Crystal_1: RendererModel
    var Crystal_2: RendererModel
    var Tuner: RendererModel
    var Base: RendererModel

    init {
        textureWidth = 64
        textureHeight = 32

        this.Stand = RendererModel(this, 44, 0).apply {
            setRotationPoint(0.0F, 15.0F, 0.0F)
            addBox(-0.5F, -2.5F, 4.0F, 1, 3, 1)
        }
        this.Crystal_3 = RendererModel(this, 1, 0).apply {
            setRotationPoint(0.0F, 18.0F, -3.0F)
            addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2)
        }
        this.Platform = RendererModel(this, 0, 21).apply {
            setRotationPoint(0.0F, 15.0F, 0.0F)
            addBox(-4.0F, -1.0F, -5.0F, 8, 1, 8)
        }
        this.TunerBridge = RendererModel(this, 44, 4).apply {
            setRotationPoint(0.0F, 15.0F, 0.0F)
            addBox(-0.5F, -1.0F, 4.0F, 1, 6, 1)
        }
        this.setRotationAngles(this.TunerBridge, 1.0410009745512285F, 0.0F, 0.0F)
        this.TunerWeight = RendererModel(this, 48, 0).apply {
            setRotationPoint(0.0F, 15.0F, 0.0F)
            addBox(-1.5F, -2.0F, 5.5F, 3, 1, 2)
        }
        this.Crystal_4 = RendererModel(this, 1, 0).apply {
            setRotationPoint(0.0F, 18.0F, -3.0F)
            addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2)
        }
        this.Crystal_1 = RendererModel(this, 1, 0).apply {
            setRotationPoint(0.0F, 18.0F, -3.0F)
            addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2)
        }
        this.Crystal_2 = RendererModel(this, 1, 0).apply {
            setRotationPoint(0.0F, 18.0F, -3.0F)
            addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2)
        }
        this.setRotationAngles(this.Crystal_2, 0.0F, -0.7853981633974483F, 0.0F)
        this.Tuner = RendererModel(this, 48, 3).apply {
            setRotationPoint(0.0F, 15.0F, 0.0F)
            addBox(-0.5F, -2.0F, 3.0F, 1, 1, 2)
        }
        this.setRotationAngles(this.Tuner, 1.0297440589395728F, 0.0F, 0.0F)
        this.Base = RendererModel(this, 0, 0).apply {
            setRotationPoint(0.0F, 15.0F, 0.0F)
            addBox(-5.0F, 0.0F, -6.0F, 10, 9, 12)
        }
    }

    fun render(entity: Entity?, f: Float, f1: Float, f2: Float, f3: Float, f4: Float, f5: Float, crystal: ItemStack) {

        if (!crystal.isEmpty) {
            GlStateManager.pushMatrix()

            GlStateManager.enableBlend()

            if (crystal.hasTag() && crystal.tag!!.contains("color")) {
                val color = Color(crystal.tag!!.getInt("color"))
                GlStateManager.color4f(color.red / 255f, color.green / 255f, color.blue / 255f, .6f)
            } else
                GlStateManager.color4f(1f, 1f, 1f, .6f)

            GlStateManager.scalef(.7f, .7f, .7f)
            GlStateManager.translatef(0f, .05f, -.1f)
            GlStateManager.rotatef(f, 0f, 1f, 0f)
            GlStateManager.translatef(0f, 0f, .18f)

            Crystal_1.render(f5)
            Crystal_2.render(f5)
            Crystal_3.render(f5)
            Crystal_4.render(f5)

            GlStateManager.color3f(1.0f, 1.0f, 1.0f)
            GlStateManager.disableBlend()

            GlStateManager.popMatrix()
        }

        Base.render(f5)
        Platform.render(f5)
        Stand.render(f5)
        TunerBridge.render(f5)
        TunerWeight.render(f5)
        Tuner.render(f5)
    }

    private fun setRotationAngles(model: RendererModel, x: Float, y: Float, z: Float) {
        model.rotateAngleX = x
        model.rotateAngleY = y
        model.rotateAngleZ = z
    }
}
