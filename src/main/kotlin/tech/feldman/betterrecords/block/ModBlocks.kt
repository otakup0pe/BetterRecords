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

import tech.feldman.betterrecords.MOD_ID
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.inventory.container.ContainerType
import net.minecraft.item.Item
import net.minecraft.item.BlockItem
import net.minecraft.tileentity.TileEntityType
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.common.extensions.IForgeContainerType
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.eventbus.api.SubscribeEvent
import tech.feldman.betterrecords.block.tile.*
import tech.feldman.betterrecords.client.gui.ContainerFrequencyTuner
import tech.feldman.betterrecords.client.gui.ContainerRecordEtcher
import tech.feldman.betterrecords.item.ModItems
import java.util.function.Supplier
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.javaConstructor

@Mod.EventBusSubscriber(modid = MOD_ID)
object ModBlocks {

    val blockRecordEtcher = BlockRecordEtcher(defaultBuilder())
    val blockRecordPlayer = BlockRecordPlayer(defaultBuilder())
    val blockFrequencyTuner = BlockFrequencyTuner(defaultBuilder())
    val blockRadio = BlockRadio(defaultBuilder())
    val blockSpeakerSmall = BlockSpeaker(BlockSpeaker.SpeakerSize.SMALL, defaultBuilder())
    val blockSpeakerMedium = BlockSpeaker(BlockSpeaker.SpeakerSize.MEDIUM, defaultBuilder())
    val blockSpeakerLarge = BlockSpeaker(BlockSpeaker.SpeakerSize.LARGE, defaultBuilder())
    val blockStrobeLight = BlockStrobeLight(defaultBuilder())
    val blockLaser = BlockLaser(defaultBuilder())
    val blockLaserCluster = BlockLaserCluster(defaultBuilder())

    private fun defaultBuilder(): Block.Properties {
        return Block.Properties.create(Material.WOOD)
    }

    @SubscribeEvent
    fun registerBlocks(event: RegistryEvent.Register<Block>) {
        event.registry.registerAll(
                blockRecordEtcher,
                blockRecordPlayer,
                blockFrequencyTuner,
                blockRadio,
                blockSpeakerSmall,
                blockSpeakerMedium,
                blockSpeakerLarge,
                blockStrobeLight,
                blockLaser,
                blockLaserCluster
        )
    }

    @SubscribeEvent
    fun registerItems(event: RegistryEvent.Register<Item>) {
        arrayOf<ModBlock>(
                blockRecordEtcher,
                blockRecordPlayer,
                blockFrequencyTuner,
                blockRadio,
                blockSpeakerSmall,
                blockSpeakerMedium,
                blockSpeakerLarge,
                blockStrobeLight,
                blockLaser,
                blockLaserCluster
        ).map {
            BlockItem(it, ModItems.defaultBuilder())
        }.forEach(event.registry::register)

        // TODO?
//        Block.REGISTRY
//                .filterIsInstance<ModBlock>()
//                .forEach {
//                    if (it is TileEntityProvider<*>) {
//                        it.registerTileEntity(it)
//                    }
//                }
    }

    val blockRecordEtcherType = TileEntityType.Builder.create(Supplier { TileRecordEtcher() }, blockRecordEtcher).build(null)
    val blockRecordPlayerType = TileEntityType.Builder.create(Supplier { TileRecordPlayer() }, blockRecordPlayer).build(null)
    val blockFrequencyTunerType = TileEntityType.Builder.create(Supplier { TileFrequencyTuner() }, blockFrequencyTuner).build(null)
    val blockRadioType = TileEntityType.Builder.create(Supplier { TileRadio() }, blockRadio).build(null)
    val blockSpeakerType = TileEntityType.Builder.create(Supplier { TileSpeaker() }, blockSpeakerSmall, blockSpeakerMedium, blockSpeakerLarge).build(null)
    val blockStrobeLightType = TileEntityType.Builder.create(Supplier { TileStrobeLight() }, blockStrobeLight).build(null)
    val blockLaserType = TileEntityType.Builder.create(Supplier { TileLaser() }, blockLaser).build(null)
    val blockLaserClusterType = TileEntityType.Builder.create(Supplier { TileLaserCluster() }, blockLaserCluster).build(null)

    @SubscribeEvent
    fun registerTiles(event: RegistryEvent.Register<TileEntityType<*>>) {
        arrayOf(
                blockRecordEtcherType,
                blockRecordPlayerType,
                blockFrequencyTunerType,
                blockRadioType,
                blockSpeakerType,
                blockStrobeLightType,
                blockLaserType,
                blockLaserClusterType
        ).forEach(event.registry::register)
    }

    val containerRecordEtcherType = IForgeContainerType.create(::ContainerRecordEtcher)
    val containerFrequencyTunerType = IForgeContainerType.create(::ContainerFrequencyTuner)

    @SubscribeEvent
    fun registerContainers(event: RegistryEvent.Register<ContainerType<*>>) {
        arrayOf(
                containerRecordEtcherType,
                containerFrequencyTunerType
        ).forEach(event.registry::register)
    }

    // TODO?
//    @SubscribeEvent
//    fun registerModels(event: ModelRegistryEvent) {
//        Block.REGISTRY
//                .filterIsInstance<ModBlock>()
//                .forEach {
//                    it.setStateMapper()
//
//                    if (it is ItemModelProvider) {
//                        it.registerItemModel(it)
//                    }
//
//                    if (it is TESRProvider<*>) {
//                        it.bindTESR()
//                        it.registerTESRItemStacks(it)
//                    }
//                }
//    }
}
