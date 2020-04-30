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
package tech.feldman.betterrecords.item

import tech.feldman.betterrecords.MOD_ID
import net.minecraft.item.Item
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.eventbus.api.SubscribeEvent
import tech.feldman.betterrecords.BetterRecords

@Mod.EventBusSubscriber(modid = MOD_ID)
object ModItems {

    val itemRecord: ModItem = ItemRecord(defaultBuilder())
    val itemFrequencyCrystal: ModItem = ItemFrequencyCrystal(defaultBuilder())
    val itemWire: ModItem = ItemWire(defaultBuilder())
    val itemWireCutters: ModItem = ItemWireCutter(defaultBuilder())

    fun defaultBuilder(): Item.Properties {
        return Item.Properties().group(BetterRecords.itemGroup)
    }

    @SubscribeEvent
    fun registerItems(event: RegistryEvent.Register<Item>) {
        event.registry.registerAll(
                itemRecord,
                itemFrequencyCrystal,
                itemWire,
                itemWireCutters
        )
    }
}
