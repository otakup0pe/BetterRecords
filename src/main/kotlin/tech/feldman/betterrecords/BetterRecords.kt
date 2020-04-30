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
package tech.feldman.betterrecords

import net.alexwells.kottle.FMLKotlinModLoadingContext
import tech.feldman.betterrecords.item.ModItems
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.DistExecutor
import net.minecraftforge.fml.ModLoadingContext
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.config.ModConfig
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import tech.feldman.betterrecords.proxy.ClientProxy
import tech.feldman.betterrecords.proxy.CommonProxy
import tech.feldman.betterrecords.proxy.ServerProxy
import java.util.function.Supplier

const val MOD_ID = "betterrecords"

@Mod(MOD_ID)
object BetterRecords {

    val logger: Logger = LogManager.getLogger(MOD_ID)

    @Suppress("RemoveExplicitTypeArguments")
    var proxy = DistExecutor.runForDist<CommonProxy>(
            { Supplier { ClientProxy() }},
            { Supplier { ServerProxy() }}
    )

    val itemGroup = object: ItemGroup(MOD_ID) {
        override fun createIcon() = ItemStack(ModItems.itemRecord)
    }

    // And we're off to the races
    init {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, BetterRecordsConfig.CLIENT_SPEC)
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, BetterRecordsConfig.COMMON_SPEC)

        FMLKotlinModLoadingContext.get().modEventBus.register(proxy)
        MinecraftForge.EVENT_BUS.register(proxy)
    }
}
