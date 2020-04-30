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

import tech.feldman.betterrecords.api.connection.RecordConnection
import tech.feldman.betterrecords.api.wire.IRecordWire
import tech.feldman.betterrecords.api.wire.IRecordWireHome
import tech.feldman.betterrecords.helper.ConnectionHelper
import net.minecraft.network.PacketBuffer
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.network.NetworkEvent
import java.util.function.Supplier

class PacketWireConnection @JvmOverloads constructor(
        var connection: RecordConnection = RecordConnection(0, 0, 0, true)
) {
    object Code {
        fun encode(msg: PacketWireConnection, buf: PacketBuffer) {
            buf.writeString(msg.connection.toString())
        }

        fun decode(buf: PacketBuffer): PacketWireConnection {
            return PacketWireConnection(
                    RecordConnection(buf.readString())
            )
        }
    }

    object Handler {

        fun handle(message: PacketWireConnection, ctx: Supplier<NetworkEvent.Context>) {
            val player = ctx.get().sender!!

            with(message) {
                val te1 = player.world.getTileEntity(BlockPos(connection.x1, connection.y1, connection.z1))
                val te2 = player.world.getTileEntity(BlockPos(connection.x2, connection.y2, connection.z2))

                if (te1 is IRecordWire && te2 is IRecordWire) {
                    if (!(te1 is IRecordWireHome && te2 is IRecordWireHome)) {
                        ConnectionHelper.addConnection(player.world, te1, connection, player.world.getBlockState(te1.pos))
                        ConnectionHelper.addConnection(player.world, te2, connection, player.world.getBlockState(te2.pos))
                    }
                }
            }
        }
    }
}
