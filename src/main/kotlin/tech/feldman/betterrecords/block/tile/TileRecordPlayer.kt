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
import tech.feldman.betterrecords.helper.ConnectionHelper
import net.minecraft.entity.item.ItemEntity
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundNBT
import net.minecraft.tileentity.TileEntityType
import tech.feldman.betterrecords.block.ModBlocks

class TileRecordPlayer() : SimpleRecordWireHome(ModBlocks.blockRecordPlayerType), IRecordWire {

    override fun getName() = "Record Player"

    override val songRadiusIncrease = 40F

    override var record = ItemStack.EMPTY
        set(value) {
            field = value.copy()
            recordEntity = ItemEntity(world, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), field)
            recordEntity?.rotationPitch = 0F
            recordEntity?.rotationYaw = 0F
            recordRotation = 0F
        }

    var recordEntity: ItemEntity? = null

    var opening = false
    var openAmount = 0f

    var needleLocation = 0f
    var recordRotation = 0f

    override fun tick() {
        if (opening) {
            if (openAmount > -0.8f) {
                openAmount -= 0.08f
            }
        } else if (openAmount < 0f) {
            openAmount += 0.12f
        }

        if (openAmount < -0.8f) {
            openAmount = -0.8f
        } else if (openAmount > 0f) {
            openAmount = 0f
        }

        if (!record.isEmpty) {
            recordRotation += 0.08f
            if (needleLocation < .3f) {
                needleLocation += 0.01f
            } else {
                needleLocation = .3f
            }
        } else if (needleLocation > 0f) {
            needleLocation -= 0.01f
        } else {
            needleLocation = 0f
        }

        super.tick()
    }

    override fun read(compound: CompoundNBT) = compound.run {
        super.read(compound)

        // TODO: HMMMM
        // record = ItemStack(get("record"))
        opening = getBoolean("opening")

        connections = ConnectionHelper.unserializeConnections(getString("connections")).toMutableList()
        wireSystemInfo = ConnectionHelper.unserializeWireSystemInfo(compound.getString("wireSystemInfo"))

        playRadius = getFloat("playRadius")
    }

    override fun write(compound: CompoundNBT) = compound.apply {
        super.write(compound)

        put("record", getStackTagCompound(record))
        putBoolean("opening", opening)

        putString("connections", ConnectionHelper.serializeConnections(connections))
        putString("wireSystemInfo", ConnectionHelper.serializeWireSystemInfo(wireSystemInfo))

        putFloat("playRadius", playRadius)
    }

    fun getStackTagCompound(stack: ItemStack?): CompoundNBT {
        val tag = CompoundNBT()
        stack?.write(tag)
        return tag
    }
}
