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
package tech.feldman.betterrecords.item

import tech.feldman.betterrecords.api.sound.IColorableSoundHolder
import tech.feldman.betterrecords.api.sound.ISoundHolder
import tech.feldman.betterrecords.helper.nbt.getSounds
import net.minecraft.item.ItemStack
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.StringTextComponent

class ItemFrequencyCrystal(properties: Properties) : ModItem(properties.maxStackSize(1)), ISoundHolder, IColorableSoundHolder {

    override val maxSounds = 1

    override fun getTranslationKey(stack: ItemStack): String {
        // if there's a song tuned to it, it will be set in [getItemStackDisplayName]
        return "item.betterrecords:frequencycrystal.blank"
    }

    override fun getDisplayName(stack: ItemStack): ITextComponent {
        val sounds = getSounds(stack)

        // Use the name of the sound on the crystal if it is tuned
        if (sounds.count() == 1) {
            return StringTextComponent(sounds.first().name)
        }

        // If it has no sounds, we fall back on localizing it.
        return super.getDisplayName(stack)
    }
}
