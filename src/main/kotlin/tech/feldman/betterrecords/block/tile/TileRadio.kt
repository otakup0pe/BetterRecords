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
package tech.feldman.betterrecords.block.tile

import tech.feldman.betterrecords.api.wire.IRecordWire
import tech.feldman.betterrecords.block.tile.delegate.CopyOnSetDelegate
import tech.feldman.betterrecords.helper.ConnectionHelper
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundNBT
import net.minecraft.tileentity.TileEntityType
import tech.feldman.betterrecords.block.ModBlocks

class TileRadio() : SimpleRecordWireHome(ModBlocks.blockRadioType), IRecordWire {

    var crystal by CopyOnSetDelegate()
    override val record: ItemStack
        get() {
            return crystal
        }

    var crystalFloaty = 0F

    var opening = false
    var openAmount = 0F

    override fun getName() = "Radio"

    override val songRadiusIncrease = 30F

    override fun tick() {
        if (opening && openAmount < 0.268F) {
            openAmount += 0.04F
        } else if (!opening && openAmount > 0F) {
            openAmount -= 0.04F
        }

        if (openAmount > 0.268F) {
            openAmount = 0.268F
        } else if (openAmount < 0F) {
            openAmount = 0F
        }

        if (!crystal.isEmpty) {
            crystalFloaty += 0.86F
        }

        super.tick()
    }

    override fun read(compound: CompoundNBT) = compound.run {
        super.read(compound)

        // TODO: HMMM
        // crystal = ItemStack(getCompound("crystal"))
        opening = getBoolean("opening")
        connections = ConnectionHelper.unserializeConnections(getString("connections")).toMutableList()
        wireSystemInfo = ConnectionHelper.unserializeWireSystemInfo(getString("wireSystemInfo"))
        playRadius = getFloat("playRadius")
    }

    override fun write(compound: CompoundNBT) = compound.apply {
        super.write(compound)

        // TODO hmmm.
        // putFloat("rotation", blockMetadata.toFloat())
        put("crystal", getStackTagCompound(crystal))
        putBoolean("opening", opening)
        putString("connections", ConnectionHelper.serializeConnections(connections))
        putString("wireSystemInfo", ConnectionHelper.serializeWireSystemInfo(wireSystemInfo))
        putFloat("playRadius", playRadius)

        return compound
    }

    fun getStackTagCompound(stack: ItemStack?): CompoundNBT {
        val tag = CompoundNBT()
        stack?.write(tag)
        return tag
    }
}
