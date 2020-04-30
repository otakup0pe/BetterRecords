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
import tech.feldman.betterrecords.item.ItemRecord
import net.minecraft.entity.item.ItemEntity
import net.minecraft.inventory.IInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundNBT
import net.minecraft.tileentity.ITickableTileEntity
import net.minecraft.tileentity.TileEntityType
import tech.feldman.betterrecords.block.ModBlocks

class TileRecordEtcher() : ModInventoryTile(ModBlocks.blockRecordEtcherType), IInventory, ITickableTileEntity {

    var record by CopyOnSetDelegate()

    var recordEntity: ItemEntity? = null
        get() {
            if (!record.isEmpty) {
                return ItemEntity(world, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), record)
            }
            return null
        }

    var recordRotation = 0F
    var needleLocation = 0F
    var needleOut = true

    override fun tick() {
        if (!record.isEmpty) {
            recordRotation += .08F

            if (needleOut) {
                when {
                    needleLocation < .3F -> needleLocation += .001F
                    else -> needleOut = true
                }
                return
            }
        }

        needleLocation = when {
            needleLocation > 0F -> needleLocation - .005F
            else -> 0F
        }
    }

    override fun getSizeInventory() = 1
    override fun getInventoryStackLimit() = 1
    override fun isEmpty() = record.isEmpty

    override fun getStackInSlot(index: Int) = record
    override fun setInventorySlotContents(index: Int, stack: ItemStack) {
        record = stack
    }

    override fun isItemValidForSlot(index: Int, stack: ItemStack): Boolean {
        return stack.item is ItemRecord
    }

    override fun read(compound: CompoundNBT) = compound.run {
        super.read(compound)

        // TODO hmmmm
        // record = ItemStack(getStackTagCompound("record"))
    }

    override fun write(compound: CompoundNBT) = compound.apply {
        super.write(compound)

        put("record", getStackTagCompound(record))
    }

    fun getStackTagCompound(stack: ItemStack?): CompoundNBT {
        val tag = CompoundNBT()
        stack?.write(tag)
        return tag
    }
}
