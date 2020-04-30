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

import tech.feldman.betterrecords.api.wire.IRecordWireManipulator
import tech.feldman.betterrecords.block.tile.TileRecordPlayer
import tech.feldman.betterrecords.helper.nbt.getSounds
import tech.feldman.betterrecords.item.ItemRecord
import net.minecraft.block.BlockState
import net.minecraft.entity.item.ItemEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.block.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.*
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.world.IBlockReader
import net.minecraft.world.World
import java.util.*

class BlockRecordPlayer(properties: Properties) : ModBlock(properties.hardnessAndResistance(1F, 5F)) {

    override fun createNewTileEntity(worldIn: IBlockReader): TileEntity? {
        return TileRecordPlayer()
    }

    override fun onBlockActivated(state: BlockState?, world: World?, pos: BlockPos?, player: PlayerEntity, hand: Hand?, hit: BlockRayTraceResult): Boolean {
        if (player.heldItemMainhand.isEmpty && player.heldItemMainhand.item is IRecordWireManipulator) return false
        val tileEntity = world!!.getTileEntity(pos!!)
        if (tileEntity == null || tileEntity !is TileRecordPlayer) return false
        val tileRecordPlayer = tileEntity as TileRecordPlayer?
        if (player.isSneaking) {
            if (world.getBlockState(pos.add(0, 1, 0)).block === Blocks.AIR) {
                if (!world.isRemote) {
                    tileRecordPlayer!!.opening = !tileRecordPlayer.opening
                }
                world.notifyBlockUpdate(pos, state!!, state, 3)
                if (tileRecordPlayer!!.opening)

                    world.playSound(pos.x.toDouble(), pos.y.toDouble() + 0.5, pos.z.toDouble(), SoundEvents.BLOCK_CHEST_OPEN, SoundCategory.NEUTRAL, 0.2f, world.rand.nextFloat() * 0.2f + 3f, false)
                else
                    world.playSound(pos.x.toDouble(), pos.y.toDouble() + 0.5, pos.z.toDouble(), SoundEvents.BLOCK_CHEST_OPEN, SoundCategory.NEUTRAL, 0.2f, world.rand.nextFloat() * 0.2f + 3f, false)
            }
        } else if (tileRecordPlayer!!.opening) {
            if (tileRecordPlayer.record.isEmpty) {
                if (player.heldItemMainhand.item is ItemRecord && getSounds(player.heldItemMainhand).isNotEmpty()) {
                    tileRecordPlayer.record = player.heldItemMainhand
                    world.notifyBlockUpdate(pos, state!!, state, 3)
                    player.heldItemMainhand.count--

                    // TODO: With dimension
//                    if (!world.isRemote) {
//                        PacketHandler.sendToAll(PacketRecordPlay(
//                                tileRecordPlayer.pos,
//                                tileRecordPlayer.world.dimension,
//                                tileRecordPlayer.songRadius,
//                                isRepeatable(tileEntity.record),
//                                isShufflable(tileEntity.record),
//                                tileEntity.record
//                        ))
//                    }
                }
            } else { // There is a record in the player
                if (!world.isRemote) dropItem(world, pos)
                tileRecordPlayer.record = ItemStack.EMPTY
                world.notifyBlockUpdate(pos, state!!, state, 3)
            }
        }
        return true
    }

    private fun dropItem(world: World, pos: BlockPos) {
        val tileEntity = world.getTileEntity(pos)
        if (tileEntity == null || tileEntity !is TileRecordPlayer)
            return

        val item = tileEntity.record

        if (!item.isEmpty) {
            val rand = Random()

            val rx = rand.nextFloat() * 0.8f + 0.1f
            val ry = rand.nextFloat() * 0.8f + 0.1f
            val rz = rand.nextFloat() * 0.8f + 0.1f

            val itemEntity = ItemEntity(world, (pos.x + rx).toDouble(), (pos.y + ry).toDouble(), (pos.z + rz).toDouble(), ItemStack(item.item, item.count))

            if (item.hasTag())
                itemEntity.item.tag = item.tag!!.copy()

            itemEntity.setMotion(rand.nextGaussian() * 0.05f,
                rand.nextGaussian() * 0.05f + 0.2f,
                rand.nextGaussian() * 0.05f
            )
            world.addEntity(itemEntity)
            item.count = 0

            tileEntity.record = ItemStack.EMPTY
            // TODO dimension
            // PacketHandler.sendToAll(PacketSoundStop(tileEntity.pos, world.dimension))
        }
    }
}
