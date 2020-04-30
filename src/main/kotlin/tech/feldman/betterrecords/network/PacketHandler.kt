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
package tech.feldman.betterrecords.network

import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.util.ResourceLocation

import net.minecraftforge.fml.network.NetworkDirection
import net.minecraftforge.fml.network.NetworkRegistry
import net.minecraftforge.fml.network.PacketDistributor

import tech.feldman.betterrecords.MOD_ID

object PacketHandler {

    private val PROTOCOL = "5"
    private val HANDLER = NetworkRegistry.newSimpleChannel(
            ResourceLocation(MOD_ID, "chan"),
            { PROTOCOL },
            PROTOCOL::equals,
            PROTOCOL::equals
    )

    fun init() {
        var id = 0
        HANDLER.registerMessage(id++, PacketRecordPlay::class.java, PacketRecordPlay.Code::encode, PacketRecordPlay.Code::decode, PacketRecordPlay.Handler::handle)
        HANDLER.registerMessage(id++, PacketRadioPlay::class.java, PacketRadioPlay.Code::encode, PacketRadioPlay.Code::decode, PacketRadioPlay.Handler::handle)
        HANDLER.registerMessage(id++, PacketSoundStop::class.java, PacketSoundStop.Code::encode, PacketSoundStop.Code::decode, PacketSoundStop.Handler::handle)
        HANDLER.registerMessage(id++, PacketWireConnection::class.java, PacketWireConnection.Code::encode, PacketWireConnection.Code::decode, PacketWireConnection.Handler::handle)
        HANDLER.registerMessage(id++, PacketURLWrite::class.java, PacketURLWrite.Code::encode, PacketURLWrite.Code::decode, PacketURLWrite.Handler::handle)
        HANDLER.registerMessage(id++, PacketSendLibrary::class.java, PacketSendLibrary.Code::encode, PacketSendLibrary.Code::decode, PacketSendLibrary.Handler::handle)
    }

    fun sendToAll(msg: Any) {
        HANDLER.send(PacketDistributor.ALL.noArg(), msg)
    }

    fun sendToServer(msg: Any) {
        HANDLER.sendToServer(msg)
    }

    fun sendToPlayer(player: ServerPlayerEntity, msg: Any) {
        HANDLER.sendTo(msg, player.connection.networkManager, NetworkDirection.PLAY_TO_CLIENT)
    }
}