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
import com.mojang.blaze3d.platform.GlStateManager.*
import net.minecraft.entity.Entity
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

@OnlyIn(Dist.CLIENT)
class ModelLaser : Model() {
    var Stand: RendererModel
    var Front: RendererModel
    var Box: RendererModel
    var Base: RendererModel

    init {
        textureWidth = 64
        textureHeight = 32

        this.Stand = RendererModel(this, 0, 16).apply {
            setRotationPoint(0.0F, 21.0F, 0.0F)
            addBox(-1.5F, -6.0F, -1.5F, 3, 6, 3)
        }
        this.Front = RendererModel(this, 0, 25).apply {
            setRotationPoint(0.0F, 15.0F, 0.0F)
            addBox(-2.0F, -2.0F, -6.0F, 4, 4, 1)
        }
        this.Box = RendererModel(this, 0, 0).apply {
            setRotationPoint(0.0F, 15.0F, 0.0F)
            addBox(-3.0F, -3.0F, -5.0F, 6, 6, 10)
        }
        this.Base = RendererModel(this, 12, 16).apply {
            setRotationPoint(0.0F, 21.0F, 0.0F)
            addBox(-4.0F, 0.0F, -4.0F, 8, 3, 8)
        }
    }

    fun render(entity: Entity?, pitch: Float, yaw: Float, f2: Float, f3: Float, f4: Float, scale: Float) {

        Stand.render(scale)
        Base.render(scale)

        pushMatrix()

        rotatef(yaw, 0F, 1F, 0F)
        translatef(0F, .95F, 0F)
        rotatef(-pitch, 1F, 0F, 0F)
        translatef(0F, -.95F, 0F)

        Front.render(scale)
        Box.render(scale)

        popMatrix()
    }

    fun setRotationAngles(modelRenderer: RendererModel, x: Float, y: Float, z: Float) {
        modelRenderer.rotateAngleX = x
        modelRenderer.rotateAngleY = y
        modelRenderer.rotateAngleZ = z
    }
}
