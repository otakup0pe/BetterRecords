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

import tech.feldman.betterrecords.api.event.RecordInsertEvent
import tech.feldman.betterrecords.api.sound.Sound
import net.minecraft.network.PacketBuffer
import net.minecraft.util.math.BlockPos
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.network.NetworkEvent
import java.util.function.Supplier

class PacketRecordPlay @JvmOverloads constructor(
        var pos: BlockPos = BlockPos(0, 0, 0),
        var dimension: Int = -1,
        var playRadius: Float = -1F,
        var sounds: MutableList<Sound>,
        var repeat: Boolean = false,
        var shuffle: Boolean = false
) {

    object Code {
        fun encode(msg: PacketRecordPlay, buf: PacketBuffer) {
            buf.writeBlockPos(msg.pos)
            buf.writeInt(msg.dimension)
            buf.writeFloat(msg.playRadius)

            // Write the amount of sounds we're going to send,
            // to rebuild on the other side.
            buf.writeInt(msg.sounds.size)
            msg.sounds.forEach { sound ->
                buf.writeString(sound.url)
                buf.writeString(sound.name)
                buf.writeInt(sound.size)
                buf.writeString(sound.author)
            }

            buf.writeBoolean(msg.repeat)
            buf.writeBoolean(msg.shuffle)
        }

        fun decode(buf: PacketBuffer): PacketRecordPlay {
            return PacketRecordPlay(
                    buf.readBlockPos(),
                    buf.readInt(),
                    buf.readFloat(),
                    {
                        val list = mutableListOf<Sound>()
                        for (i in 1..buf.readInt()) {
                            list.add(Sound(
                                    buf.readString(),
                                    buf.readString(),
                                    buf.readInt(),
                                    buf.readString()
                            ))
                        }
                        list
                    }(),
                    buf.readBoolean(),
                    buf.readBoolean()

            )
        }
    }

    object Handler {

        fun handle(message: PacketRecordPlay, ctx: Supplier<NetworkEvent.Context>) {
            with(message) {
                if (shuffle) {
                    message.sounds.shuffle()
                }

                MinecraftForge.EVENT_BUS.post(RecordInsertEvent(pos, dimension, playRadius, sounds, repeat))
            }
        }
    }
}
