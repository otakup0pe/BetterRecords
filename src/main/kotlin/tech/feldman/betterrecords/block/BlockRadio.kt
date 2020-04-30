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
package tech.feldman.betterrecords.block

import tech.feldman.betterrecords.api.wire.IRecordWire
import tech.feldman.betterrecords.api.wire.IRecordWireManipulator
import tech.feldman.betterrecords.block.tile.TileRadio
import tech.feldman.betterrecords.helper.ConnectionHelper
import tech.feldman.betterrecords.helper.nbt.getSounds
import tech.feldman.betterrecords.item.ModItems
import net.minecraft.block.BlockState
import net.minecraft.entity.item.ItemEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.*
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.world.IBlockReader
import net.minecraft.world.World
import tech.feldman.betterrecords.block.tile.TileRecordPlayer
import java.util.*

class BlockRadio(properties: Properties) : ModBlock(properties.hardnessAndResistance(2F, 6.3F)) {

    override fun onBlockActivated(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockRayTraceResult): Boolean {
        if (player.heldItemMainhand.item is IRecordWireManipulator) return false

        (world.getTileEntity(pos) as? TileRadio)?.let { te ->
            if (player.isSneaking) {
                te.opening = !te.opening
                world.notifyBlockUpdate(pos, state, state, 3)
                world.playSound(pos.x.toDouble(), pos.y.toDouble() + 0.5, pos.z.toDouble(), SoundEvents.BLOCK_CHEST_CLOSE, SoundCategory.NEUTRAL, 0.2f, world.rand.nextFloat() * 0.2f + 3f, false)
            } else if (te.opening) {
                if (!te.crystal.isEmpty) {
                    if (!world.isRemote) dropItem(world, pos)
                    te.crystal = ItemStack.EMPTY
                    world.notifyBlockUpdate(pos, state, state, 3)
                } else if (player.heldItemMainhand.item == ModItems.itemFrequencyCrystal && getSounds(player.heldItemMainhand).isNotEmpty()) {
                    te.crystal = player.heldItemMainhand
                    world.notifyBlockUpdate(pos, state, state, 3)
                    player.heldItemMainhand.count--
                    // TODO dimension
//                    if (!world.isRemote) {
//                        PacketHandler.sendToAll(PacketRadioPlay(
//                                pos,
//                                world.dimension,
//                                te.songRadius,
//                                getSounds(te.crystal).first().name,
//                                getSounds(te.crystal).first().url
//                        ))
//                    }
                }
            }
            return true
        }
        return false
    }

    private fun dropItem(world: World, pos: BlockPos) {
        (world.getTileEntity(pos) as? TileRadio)?.let { te ->
            if (!te.crystal.isEmpty) {
                val random = Random()
                val rx = random.nextDouble() * 0.8F + 0.1F
                val ry = random.nextDouble() * 0.8F + 0.1F
                val rz = random.nextDouble() * 0.8F + 0.1F

                val itemEntity = ItemEntity(world, pos.x + rx, pos.y + ry, pos.z + rz, ItemStack(te.crystal.item, te.crystal.count))
                if (te.crystal.hasTag()) itemEntity.item.tag = te.crystal.tag!!.copy()

                itemEntity.setMotion(
                        random.nextGaussian() * 0.05F,
                        random.nextGaussian() * 0.05F + 0.2F,
                        random.nextGaussian() * 0.05F
                )

                world.addEntity(itemEntity)
                te.crystal.count = 0
                te.crystal = ItemStack.EMPTY
                // TODO dimension
                // PacketHandler.sendToAll(PacketSoundStop(te.pos, world.dimension))
            }
        }
    }

    override fun createNewTileEntity(worldIn: IBlockReader): TileEntity? {
        return TileRecordPlayer()
    }
}
