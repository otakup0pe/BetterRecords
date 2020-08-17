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
package tech.feldman.betterrecords.handler

import tech.feldman.betterrecords.BetterRecords
import tech.feldman.betterrecords.helper.nbt.getSounds
import tech.feldman.betterrecords.api.event.RadioInsertEvent
import tech.feldman.betterrecords.api.event.SoundStopEvent
import net.minecraft.util.math.BlockPos
import net.minecraft.entity.player.EntityPlayerMP
import tech.feldman.betterrecords.block.tile.TileRadio
import tech.feldman.betterrecords.network.PacketHandler
import tech.feldman.betterrecords.network.PacketRadioPlay
import tech.feldman.betterrecords.network.PacketSoundStop
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent

open class ResumeHandler {
    private val streams = hashMapOf<EntityPlayerMP, List<Pair<BlockPos, Int>>>()

    @SubscribeEvent
    fun onPlayerMoved(event: LivingUpdateEvent) {
        if (event.entity !is EntityPlayerMP) return;
        if (event.entity.world.isRemote) return;
        val world = event.entity.getEntityWorld()
        val tiles = world.loadedTileEntityList
        tiles.forEach {
            if ( it is TileRadio ) {
                val radioDistance = event.entity.getDistance(it.pos.x.toDouble(),
                                                             it.pos.y.toDouble(),
                                                             it.pos.z.toDouble())
                if ( radioDistance > it.songRadius) {
                    val player = event.entity as EntityPlayerMP
                    val radioPacket = PacketSoundStop(it.pos, player.dimension)
                    PacketHandler.sendToPlayer(radioPacket, player)
                } else {
                    val streamKey = Pair(it.pos, player.dimension)
                    if (player in streams) {
                        val playerStreams = player[streams] as List<Pair<BlockPos, Int>>
                        if (playerStreams.contains(streamKey)) return
                        streams[player] = playerStreams += streamKey
                    } else {
                        streams[player] = listOf(streamKey)
                    }
                    if (it.crystal != null) {
                        val player = event.entity as EntityPlayerMP
                        val crystalSounds = getSounds(it.crystal)
                        if (crystalSounds.size == 0) return
                        val radioSounds = crystalSounds.first()
                        val radioPacket = PacketRadioPlay(
                            it.pos,
                            player.dimension,
                            it.songRadius,
                            radioSounds.name,
                            radioSounds.url)
                        PacketHandler.sendToPlayer(radioPacket,
                                                   player)
                    }
                }
            }
        }
    }
}
