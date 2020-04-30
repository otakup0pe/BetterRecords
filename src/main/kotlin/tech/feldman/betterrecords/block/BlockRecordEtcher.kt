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

import tech.feldman.betterrecords.block.tile.TileRecordEtcher
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.tileentity.TileEntity

import net.minecraft.util.Hand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.world.IBlockReader
import net.minecraft.world.World

class BlockRecordEtcher(properties: Properties) : ModBlock(properties.hardnessAndResistance(1.5F, 5.5F)) {

    override fun createNewTileEntity(worldIn: IBlockReader): TileEntity? {
        return TileRecordEtcher()
    }

    override fun onBlockActivated(state: BlockState, world: World, pos: BlockPos,  player: PlayerEntity, hand: Hand, hit: BlockRayTraceResult): Boolean {
        (world.getTileEntity(pos) as? TileRecordEtcher)?.let {
            // TODO
            // player.openContainer(BetterRecords, 0, world, pos.x, pos.y, pos.z)
            return true
        }
        return false
    }
}
