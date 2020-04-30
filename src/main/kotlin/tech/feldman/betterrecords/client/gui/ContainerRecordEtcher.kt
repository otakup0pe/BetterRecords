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
package tech.feldman.betterrecords.client.gui

import tech.feldman.betterrecords.block.tile.TileRecordEtcher
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.container.Container
import net.minecraft.inventory.container.Slot
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketBuffer
import tech.feldman.betterrecords.block.ModBlocks

class ContainerRecordEtcher(windowId: Int, inv: PlayerInventory, extraData: PacketBuffer) : Container(ModBlocks.containerRecordEtcherType, windowId) {

    init {
        // Slot 0
        addSlot(SlotValid(inv, 0, 17, 50))

        // Slots 1-27
        for (i in 0..2) {
            for (j in 0..8) {
                addSlot(Slot(inv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18))
            }
        }

        // Slots 28-36
        for (i in 0..8) {
            addSlot(Slot(inv, i, 8 + i * 18, 142))
        }
    }

    // TODO
    override fun canInteractWith(player: PlayerEntity) = true
            // tileEntity.isUsableByPlayer(player)

    override fun transferStackInSlot(player: PlayerEntity?, slotIndex: Int): ItemStack {
        val slot = inventory[slotIndex]

        if (slotIndex > 0) {
            // Player Inventory
            if (inventorySlots[0].isItemValid(slot)) {
                mergeItemStack(slot, 0, 1, false)
            }
        } else {
            // Etcher Inventory
            mergeItemStack(slot, 1, 37, false)
        }

        return ItemStack.EMPTY
    }
}
