package tech.feldman.betterrecords.proxy

import net.minecraft.block.Block
import net.minecraft.inventory.container.ContainerType
import net.minecraft.item.Item
import net.minecraft.tileentity.TileEntityType
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import tech.feldman.betterrecords.library.Libraries
import tech.feldman.betterrecords.network.PacketHandler

abstract class CommonProxy {

    @SubscribeEvent
    fun commonSetup(event: FMLCommonSetupEvent) {
        PacketHandler.init()
        Libraries.init()
    }

    @SubscribeEvent
    fun registerBlocks(event: RegistryEvent.Register<Block>) {

    }

    @SubscribeEvent
    fun registerTiles(event: RegistryEvent.Register<TileEntityType<*>>) {

    }

    @SubscribeEvent
    fun registerItems(event: RegistryEvent.Register<Item>) {

    }

    @SubscribeEvent
    fun registerContainers(event: RegistryEvent.Register<ContainerType<*>>) {

    }
}
