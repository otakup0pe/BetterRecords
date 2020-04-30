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
package tech.feldman.betterrecords.helper.nbt

import tech.feldman.betterrecords.api.sound.IShufflableSoundHolder
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundNBT

private const val SHUFFLABLE_TAG = "shufflable"

fun isShufflable(stack: ItemStack): Boolean {
    if (stack.item !is IShufflableSoundHolder) {
        return false
    }

    if (stack.hasTag()) {
        val tag = stack.tag!!

        return tag.getBoolean(SHUFFLABLE_TAG)
    }

    return false
}

fun setShufflable(stack: ItemStack, shufflable: Boolean) {
    if (stack.item !is IShufflableSoundHolder) {
        return
    }

    val tag = if (stack.hasTag()) {
        stack.tag!!
    } else {
        CompoundNBT()
    }


    tag.putBoolean(SHUFFLABLE_TAG, shufflable)

    stack.tag = tag
}