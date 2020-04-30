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

import tech.feldman.betterrecords.block.tile.delegate.CopyOnSetDelegate
import tech.feldman.betterrecords.helper.nbt.getSounds
import tech.feldman.betterrecords.item.ItemFrequencyCrystal
import net.minecraft.inventory.IInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundNBT
import net.minecraft.tileentity.ITickableTileEntity
import net.minecraft.tileentity.TileEntityType
import net.minecraftforge.registries.ObjectHolder
import tech.feldman.betterrecords.block.ModBlocks

class TileFrequencyTuner() : ModInventoryTile(ModBlocks.blockFrequencyTunerType), IInventory, ITickableTileEntity {

    var crystal by CopyOnSetDelegate()

    var crystalFloaty = 0F

    override fun getSizeInventory() = 1
    override fun getInventoryStackLimit() = 1
    override fun isEmpty() = crystal.isEmpty

    override fun getStackInSlot(index: Int) = crystal

    override fun isItemValidForSlot(index: Int, stack: ItemStack) =
            stack.item is ItemFrequencyCrystal && getSounds(stack).isEmpty()

    override fun setInventorySlotContents(index: Int, stack: ItemStack) {
        crystal = stack
    }

    override fun tick() {
        crystal.let {
            crystalFloaty += 0.86F
        }
    }

    override fun read(compound: CompoundNBT) = compound.run {
        super.read(compound)

        // TODO hmmm
//        crystal = ItemStack(getCompound("crystal"))
    }

    override fun write(compound: CompoundNBT) = compound.apply {
        super.write(compound)

        put("crystal", getStackTagCompound(crystal))
    }

    fun getStackTagCompound(stack: ItemStack?): CompoundNBT {
        val tag = CompoundNBT()
        stack?.write(tag)
        return tag
    }

}
