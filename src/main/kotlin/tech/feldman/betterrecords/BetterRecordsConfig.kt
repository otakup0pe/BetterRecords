package tech.feldman.betterrecords

import net.minecraftforge.common.ForgeConfigSpec

object BetterRecordsConfig {

    var CLIENT: Client
    var CLIENT_SPEC: ForgeConfigSpec

    var COMMON: Common
    var COMMON_SPEC: ForgeConfigSpec

    init {
        val clientSpecPair = ForgeConfigSpec.Builder().configure { builder: ForgeConfigSpec.Builder -> Client(builder) }
        CLIENT = clientSpecPair.left
        CLIENT_SPEC = clientSpecPair.right

        val commonSpecPair = ForgeConfigSpec.Builder().configure { builder: ForgeConfigSpec.Builder -> Common(builder) }
        COMMON = commonSpecPair.left
        COMMON_SPEC = commonSpecPair.right
    }

    class Client(builder: ForgeConfigSpec.Builder) {
        val devMode: ForgeConfigSpec.BooleanValue = builder
                .comment("Enable developer mode")
                .define("devmode", true)

        val downloadMax: ForgeConfigSpec.IntValue = builder
                .comment("Max file size to download (in megabytes)")
                .defineInRange("downloadmax", 10, 0, Int.MAX_VALUE)

        val playWhileDownloading: ForgeConfigSpec.BooleanValue = builder
                .comment("Play songs while downloading", "For those with fast internet!")
                .define("playwhiledownloading", false)

        val streamBuffer: ForgeConfigSpec.IntValue = builder
                .comment("Size of buffer for streaming songs.", "Only touch this if you know what that means.")
                .defineInRange("streambuffer", 1024, 256, 2048)

        val streamRadio: ForgeConfigSpec.BooleanValue = builder
                .comment("Should radio be streamed?")
                .define("streamradio", true)

        val flashMode: ForgeConfigSpec.IntValue = builder
                .comment("Intensity of lights")
                .defineInRange("flashmode", -1, -1, 3)

    }

    class Common(builder: ForgeConfigSpec.Builder) {

        val maxSpeakerRadius: ForgeConfigSpec.IntValue = builder
                .comment("Maximum speaker radius")
                .defineInRange("maxspeakerradius", -1, -1, 1000)

        val useRemoteLibraries: ForgeConfigSpec.BooleanValue = builder
                .comment("Should the mod download libraries from the internet or the server?")
                .define("useremotelibraries", true)

        val loadDefaultLibraries: ForgeConfigSpec.BooleanValue = builder
                .comment("Should default libraries be loaded")
                .define("loaddefaultlibraries", true)

    }
}