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
package tech.feldman.betterrecords.client.handler

import tech.feldman.betterrecords.ID
import tech.feldman.betterrecords.ModConfig
import tech.feldman.betterrecords.block.tile.TileRadio
import tech.feldman.betterrecords.client.sound.SoundManager
import tech.feldman.betterrecords.api.sound.Sound
import tech.feldman.betterrecords.helper.nbt.getSounds
import tech.feldman.betterrecords.network.PacketRadioPlay
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.text.Style
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.TextComponentTranslation
import net.minecraft.util.text.TextFormatting
import net.minecraftforge.event.entity.EntityJoinWorldEvent
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher.Side

/**
 * Handler to warn the player when they log in about flashing lights.
 */
@Mod.EventBusSubscriber(modid = ID, value = [Side.CLIENT])
object DisclaimerHandler {

    /**
     * Send a message to the player warning them if it is their first time joining a world
     *
     * We do this by checking if flashMode is -1.
     * If for any reason they set it back to -1, they will be warned again.
     */
    @SubscribeEvent
    fun onFirstJoin(event: EntityJoinWorldEvent) {
        if (event.entity is EntityPlayer && ModConfig.client.flashMode == -1) {

            val message = TextComponentTranslation("betterrecords.warning").apply {
                style = Style().apply {
                    color = TextFormatting.GREEN
                }
            }
            event.entity.sendMessage(message)

            // Update default flash mode, to show we've already warned them
            ModConfig.client.flashMode = 1
            ModConfig.update()
        }
    }

    @SubscribeEvent
    fun loggedInEvent(event: PlayerLoggedInEvent) {
        val world = event.player.getEntityWorld()
        val tiles = world.loadedTileEntityList
        tiles.forEach {
            if ( it is TileRadio ) {
                if (it.crystal != null) {
                   SoundManager.queueStreamAt(it.pos, 0, getSounds(it.crystal).first())
                }
            }
        }
    }
}
