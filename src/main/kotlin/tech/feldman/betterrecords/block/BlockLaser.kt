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

import tech.feldman.betterrecords.block.tile.TileLaser
import net.minecraft.block.BlockState
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.Hand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.util.text.TranslationTextComponent
import net.minecraft.world.IBlockReader
import net.minecraft.world.World

class BlockLaser(properties: Properties) : ModBlock(properties.hardnessAndResistance(3.2F, 4.3F)) {

    override fun createNewTileEntity(worldIn: IBlockReader): TileEntity? {
        return TileLaser()
    }

    override fun onBlockPlacedBy(world: World, pos: BlockPos, state: BlockState, entityLiving: LivingEntity?, itemStack: ItemStack) {
        (world.getTileEntity(pos) as? TileLaser)?.let { te ->
            te.pitch = entityLiving!!.rotationPitch
            te.yaw = entityLiving.rotationYaw
        }
    }

    override fun onBlockActivated(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockRayTraceResult): Boolean {
        (world.getTileEntity(pos) as? TileLaser)?.let { te ->
            val length = te.length

            if (player.isSneaking && te.length > 0) {
                te.length--
            } else if (!player.isSneaking && te.length < 25) {
                te.length++
            }

            if (te.length != length && !world.isRemote) {
                val adjustment = if (te.length > length) "increase" else "decrease"
                player.sendMessage(TranslationTextComponent("tile.betterrecords:laser.msg.$adjustment", te.length))
            }
            return true
        }
        return false
    }
}
