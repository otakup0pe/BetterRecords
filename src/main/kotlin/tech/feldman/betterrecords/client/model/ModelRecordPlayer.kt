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

import tech.feldman.betterrecords.extensions.glMatrix
import net.minecraft.client.renderer.model.Model
import net.minecraft.client.renderer.entity.model.RendererModel
import net.minecraft.entity.Entity
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import org.lwjgl.opengl.GL11

@OnlyIn(Dist.CLIENT)
class ModelRecordPlayer : Model() {
    internal var Lid: RendererModel
    internal var Case: RendererModel
    internal var Holder: RendererModel
    internal var Needle: RendererModel
    internal var NeedleStand: RendererModel

    init {
        textureWidth = 64
        textureHeight = 64

        Lid = RendererModel(this, 0, 27).apply {
            addBox(-7f, -3f, -14f, 14, 3, 14)
            setRotationPoint(0f, 12f, 7f)
            setTextureSize(64, 64)
            mirror = true
        }
        setRotation(Lid, 0f, 0f, 0f)

        Case = RendererModel(this, 0, 0).apply {
            addBox(0f, 0f, 0f, 15, 12, 15)
            setRotationPoint(-7.5f, 12f, -7.5f)
            setTextureSize(64, 64)
            mirror = true
        }
        setRotation(Case, 0f, 0f, 0f)

        Holder = RendererModel(this, 0, 0).apply {
            addBox(-0.5f, 0f, -0.5f, 1, 1, 1)
            setRotationPoint(0f, 11f, 0f)
            setTextureSize(64, 64)
            mirror = true
        }
        setRotation(Holder, 0f, 0f, 0f)

        Needle = RendererModel(this, 0, 44).apply {
            addBox(-0.5f, -0.5f, -0.5f, 1, 1, 8)
            setRotationPoint(5f, 11f, 5f)
            setTextureSize(64, 64)
            mirror = true
        }
        setRotation(Needle, 0f, 3f, 0f)

        NeedleStand = RendererModel(this, 0, 44).apply {
            addBox(-0.5f, -0.5f, -0.5f, 1, 1, 1)
            setRotationPoint(5f, 12f, 5f)
            setTextureSize(64, 64)
            mirror = true
        }
        setRotation(NeedleStand, 0f, 0f, 0f)
    }

    fun render(entity: Entity?, f: Float, f1: Float, f2: Float, f3: Float, f4: Float, f5: Float) {

        Lid.rotateAngleX = f
        Needle.rotateAngleY = 3f + f1
        Holder.rotateAngleY = f2

        Needle.rotateAngleX = f2 / 10 % 0.025f

        glMatrix {
            GL11.glDisable(GL11.GL_CULL_FACE)
            Lid.render(f5)
            GL11.glEnable(GL11.GL_CULL_FACE)
        }

        Case.render(f5)
        NeedleStand.render(f5)
        Needle.render(f5)
        Holder.render(f5)
    }

    private fun setRotation(model: RendererModel, x: Float, y: Float, z: Float) {
        model.rotateAngleX = x
        model.rotateAngleY = y
        model.rotateAngleZ = z
    }
}
