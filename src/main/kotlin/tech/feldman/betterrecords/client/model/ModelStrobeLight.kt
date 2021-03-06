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
import net.minecraft.client.model.ModelBase
import net.minecraft.client.model.ModelRenderer
import net.minecraft.entity.Entity
import org.lwjgl.opengl.GL11

class ModelStrobeLight : ModelBase() {
    internal var Base: ModelRenderer
    internal var Light: ModelRenderer
    internal var GlassTop: ModelRenderer

    init {
        textureWidth = 64
        textureHeight = 32

        Base = ModelRenderer(this, 0, 15).apply {
            addBox(-4f, -2f, -4f, 8, 3, 8)
            setRotationPoint(0f, 23f, 0f)
            setTextureSize(64, 32)
            mirror = true
        }
        setRotation(Base, 0f, 0f, 0f)

        Light = ModelRenderer(this, 28, 0).apply {
            addBox(-2f, -6f, -2f, 3, 4, 3)
            setRotationPoint(0.5f, 23f, 0.5f)
            setTextureSize(64, 32)
            mirror = true
        }
        setRotation(Light, 0f, 0f, 0f)

        GlassTop = ModelRenderer(this, 0, 0).apply {
            addBox(-4f, -10f, -4f, 7, 8, 7)
            setRotationPoint(0.5f, 23f, 0.5f)
            setTextureSize(64, 32)
            mirror = true
        }
        setRotation(GlassTop, 0f, 0f, 0f)
    }

    override fun render(entity: Entity?, f: Float, f1: Float, f2: Float, f3: Float, f4: Float, f5: Float) {
        super.render(entity, f, f1, f2, f3, f4, f5)
        setRotationAngles(f, f1, f2, f3, f4, f5, entity)

        Base.render(f5)
        Light.render(f5)

        glMatrix {
            GL11.glDisable(GL11.GL_CULL_FACE)
            GlassTop.render(f5)
            GL11.glEnable(GL11.GL_CULL_FACE)
        }
    }

    private fun setRotation(model: ModelRenderer, x: Float, y: Float, z: Float) {
        model.rotateAngleX = x
        model.rotateAngleY = y
        model.rotateAngleZ = z
    }
}
