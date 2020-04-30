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
import org.lwjgl.opengl.GL11
import java.awt.Color

@OnlyIn(Dist.CLIENT)
class ModelRadio : Model() {
    internal var Crystal_1: RendererModel
    internal var Crystal_2: RendererModel
    internal var Crystal_3: RendererModel
    internal var Crystal_4: RendererModel
    internal var body1: RendererModel
    internal var body2: RendererModel
    internal var body3: RendererModel
    internal var body4: RendererModel
    internal var body5: RendererModel
    internal var body6: RendererModel
    internal var body7: RendererModel
    internal var body8: RendererModel
    internal var body9: RendererModel
    internal var a1: RendererModel
    internal var a2: RendererModel
    internal var a3: RendererModel
    internal var a4: RendererModel
    internal var a5: RendererModel
    internal var a6: RendererModel
    internal var a7: RendererModel
    internal var a8: RendererModel
    internal var Button1: RendererModel
    internal var Button1m1: RendererModel
    internal var Button2: RendererModel
    internal var Button2m1: RendererModel
    internal var Glass: RendererModel

    init {
        textureWidth = 128
        textureHeight = 128

        this.Crystal_3 = RendererModel(this, 1, 0).apply {
            setRotationPoint(0.0F, 18.0F, -3.0F)
            addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2)
        }
        this.Button1 = RendererModel(this, 15, 15).apply {
            setRotationPoint(-4.8F, 20.5F, -6.0F)
            addBox(-1.0F, -1.0F, -0.5F, 2, 2, 1)
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
        this.Glass = RendererModel(this, 13, 20).apply {
            setRotationPoint(0.0F, 18.0F, -6.0F)
            addBox(-2.5F, -1.5F, 0.0F, 5, 3, 0)
        }
        this.body2 = RendererModel(this, 76, 41).apply {
            setRotationPoint(0.0F, 8.5F, 0.0F)
            addBox(-7.0F, -1.0F, -6.0F, 14, 2, 12)
        }
        this.body3 = RendererModel(this, 80, 27).apply {
            setRotationPoint(0.0F, 6.5F, 0.0F)
            addBox(-6.0F, -1.0F, -6.0F, 12, 2, 12)
        }
        this.body4 = RendererModel(this, 85, 13).apply {
            setRotationPoint(0.0F, 4.5F, 0.0F)
            addBox(-4.5F, -1.0F, -6.0F, 9, 2, 12)
        }
        this.body5 = RendererModel(this, 91, 0).apply {
            setRotationPoint(0.0F, 3.0F, 0.0F)
            addBox(-2.5F, -0.5F, -6.0F, 5, 1, 12)
        }
        this.body6 = RendererModel(this, 78, 74).apply {
            setRotationPoint(0.0F, 20.0F, 2.0F)
            addBox(-7.5F, -3.5F, -4.0F, 15, 7, 8)
        }
        this.body7 = RendererModel(this, 29, 36).apply {
            setRotationPoint(-5.0F, 20.0F, -4.0F)
            addBox(-2.5F, -3.5F, -2.0F, 5, 7, 4)
        }
        this.body8 = RendererModel(this, 47, 36).apply {
            setRotationPoint(5.0F, 20.0F, -4.0F)
            addBox(-2.5F, -3.5F, -2.0F, 5, 7, 4)
        }
        this.body9 = RendererModel(this, 37, 50).apply {
            setRotationPoint(0.0F, 21.5F, -4.0F)
            addBox(-2.5F, -2.0F, -2.0F, 5, 4, 4)
        }
        this.body1 = RendererModel(this, 74, 55).apply {
            setRotationPoint(0.0F, 13.0F, 0.0F)
            addBox(-7.5F, -3.5F, -6.0F, 15, 7, 12)
        }
        this.Button1m1 = RendererModel(this, 16, 12).apply {
            setRotationPoint(-4.8F, 20.5F, -6.5F)
            addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1)
        }
        this.Button2m1 = RendererModel(this, 16, 12).apply {
            setRotationPoint(5.0F, 20.5F, -6.5F)
            addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1)
        }
        this.a1 = RendererModel(this, 60, 15).apply {
            setRotationPoint(-7.0F, 13.0F, 0.0F)
            addBox(-1.0F, -1.0F, -6.5F, 2, 2, 13)
        }
        this.a2 = RendererModel(this, 60, 15).apply {
            setRotationPoint(7.0F, 13.0F, 0.0F)
            addBox(-1.0F, -1.0F, -6.5F, 2, 2, 13)
        }
        this.a3 = RendererModel(this, 61, 0).apply {
            setRotationPoint(7.0F, 12.0F, 0.0F)
            addBox(-1.5F, -0.5F, -7.0F, 3, 1, 14)
        }
        this.a4 = RendererModel(this, 61, 0).apply {
            setRotationPoint(-7.0F, 12.0F, 0.0F)
            addBox(-1.5F, -0.5F, -7.0F, 3, 1, 14)
        }
        this.a5 = RendererModel(this, 60, 15).apply {
            setRotationPoint(-7.0F, 22.5F, 0.0F)
            addBox(-1.0F, -1.0F, -6.5F, 2, 2, 13)
        }
        this.a6 = RendererModel(this, 61, 0).apply {
            setRotationPoint(-7.0F, 23.5F, 0.0F)
            addBox(-1.5F, -0.5F, -7.0F, 3, 1, 14)
        }
        this.a7 = RendererModel(this, 60, 15).apply {
            setRotationPoint(7.0F, 22.5F, 0.0F)
            addBox(-1.0F, -1.0F, -6.5F, 2, 2, 13)
        }
        this.a8 = RendererModel(this, 61, 0).apply {
            setRotationPoint(7.0F, 23.5F, 0.0F)
            addBox(-1.5F, -0.5F, -7.0F, 3, 1, 14)
        }
        this.Button2 = RendererModel(this, 15, 15).apply {
            setRotationPoint(4.8F, 20.5F, -6.0F)
            addBox(-1.0F, -1.0F, -0.5F, 2, 2, 1)
        }
    }


    fun render(entity: Entity?, f: Float, f1: Float, f2: Float, f3: Float, f4: Float, f5: Float, crystal: ItemStack) {

        GlStateManager.pushMatrix()

        GlStateManager.translatef(0f, .42f, 0f)
        GL11.glScalef(.7f, .715f, .7f)
        if (!crystal.isEmpty) {
            GlStateManager.pushMatrix()

            GlStateManager.enableBlend()
            if (crystal.tag!!.contains("color")) {
                val color = Color(crystal.tag!!.getInt("color"))
                GlStateManager.color4f(color.red / 255f, color.green / 255f, color.blue / 255f, .6f)
            } else
                GlStateManager.color4f(1f, 1f, 1f, .6f)
            GlStateManager.translatef(0f, 0f, -.18f)
            GlStateManager.rotatef(f1, 0f, 1f, 0f)
            GlStateManager.translatef(0f, 0f, .18f)
            Crystal_1.render(f5)
            Crystal_2.render(f5)
            Crystal_3.render(f5)
            Crystal_4.render(f5)
            GlStateManager.color3f(1.0f, 1.0f, 1.0f)
            GlStateManager.disableBlend()

            GlStateManager.popMatrix()
        }

        body1.render(f5)
        body2.render(f5)
        body3.render(f5)
        body4.render(f5)
        body5.render(f5)
        body6.render(f5)
        body7.render(f5)
        body8.render(f5)
        body9.render(f5)

        a1.render(f5)
        a4.render(f5)
        a2.render(f5)
        a3.render(f5)
        a7.render(f5)
        a8.render(f5)
        a5.render(f5)
        a6.render(f5)

        Button1.render(f5)
        Button1m1.render(f5)

        Button2.render(f5)
        Button2m1.render(f5)

        GlStateManager.pushMatrix()

        GlStateManager.translatef(f, 0f, 0.01f)
        Glass.render(f5)

        GlStateManager.popMatrix()

        GlStateManager.popMatrix()
    }

    private fun setRotationAngles(model: RendererModel, x: Float, y: Float, z: Float) {
        model.rotateAngleX = x
        model.rotateAngleY = y
        model.rotateAngleZ = z
    }
}
