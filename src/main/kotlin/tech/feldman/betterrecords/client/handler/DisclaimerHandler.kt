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

import tech.feldman.betterrecords.MOD_ID
import tech.feldman.betterrecords.BetterRecordsConfig
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.text.Style
import net.minecraft.util.text.TranslationTextComponent
import net.minecraft.util.text.TextFormatting
import net.minecraftforge.event.entity.EntityJoinWorldEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.api.distmarker.Dist

/**
 * Handler to warn the player when they log in about flashing lights.
 */
@Mod.EventBusSubscriber(Dist.CLIENT, modid = MOD_ID)
object DisclaimerHandler {

    /**
     * Send a message to the player warning them if it is their first time joining a world
     *
     * We do this by checking if flashMode is -1.
     * If for any reason they set it back to -1, they will be warned again.
     */
    @SubscribeEvent
    fun onFirstJoin(event: EntityJoinWorldEvent) {
        if (event.entity is PlayerEntity && BetterRecordsConfig.CLIENT.flashMode.get() == -1) {

            val message = TranslationTextComponent("betterrecords.warning").apply {
                style = Style().apply {
                    color = TextFormatting.GREEN
                }
            }
            event.entity.sendMessage(message)

            // Update default flash mode, to show we've already warned them
            BetterRecordsConfig.CLIENT.flashMode.set(1)
            BetterRecordsConfig.CLIENT_SPEC.save() // TODO: Is this needed?
        }
    }
}
