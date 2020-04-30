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
package tech.feldman.betterrecords.extensions

import net.minecraft.nbt.INBT
import net.minecraft.nbt.CompoundNBT
import java.util.*

operator fun CompoundNBT.set(key: String, value: INBT) = put(key, value)
operator fun CompoundNBT.set(key: String, value: Byte) = putByte(key, value)
operator fun CompoundNBT.set(key: String, value: Short) = putShort(key, value)
operator fun CompoundNBT.set(key: String, value: Int) = putInt(key, value)
operator fun CompoundNBT.set(key: String, value: Long) = putLong(key, value)
operator fun CompoundNBT.set(key: String, value: UUID) = putUniqueId(key, value)
operator fun CompoundNBT.set(key: String, value: Float) = putFloat(key, value)
operator fun CompoundNBT.set(key: String, value: Double) = putDouble(key, value)
operator fun CompoundNBT.set(key: String, value: ByteArray) = putByteArray(key, value)
operator fun CompoundNBT.set(key: String, value: IntArray) = putIntArray(key, value)
operator fun CompoundNBT.set(key: String, value: Boolean) = putBoolean(key, value)
operator fun CompoundNBT.set(key: String, value: String) = putString(key, value)
